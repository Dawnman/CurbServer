package us.curb.maven_.crawscholar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import net.sf.json.JSONArray;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.curb.maven_.crawscholar.pipeline.CrawScholarPipeline;
import us.curb.maven_.dao.CourseDao;
import us.curb.maven_.dao.UnfinishDao;
import us.curb.maven_.dao.UserCourseKeyDao;
import us.curb.maven_.dao.UserDao;
import us.curb.maven_.pojo.Unfinish;

/*
 * 这是用户非第一次登录 的爬虫
 */
@Component
public class CrawSchUnfinish implements PageProcessor{

	@Qualifier("crawScholarPipeline")
	@Autowired
	private CrawScholarPipeline crawScholarPipeline;
	@Autowired
	CourseDao courseDao;
	@Autowired
	UserDao userDao;
	@Autowired
	UnfinishDao unfinishDao;
	@Autowired
	UserCourseKeyDao userCourseKeyDao;
	
	//登陆时帐号或密码错误时的状态码，1表示错误
	private int errCode = 1;
	
	private Login login;
	
	private Site site;
		
	private Map<String, Object> formMap = new HashMap<String,Object>();

	private List<Unfinish> unfinishList;//用户的未提交作业的表数据
	
	private Map<Integer,String> courseidMap;//用户的未提交作业的课程id
	
	public void init(){
		
		login=new Login(1,"lifumin","lg5684902");
		login.loginSetCookie();
		setSite(login.getSite());
//		this.values[0] = new BasicNameValuePair("urlBeforeLogin", "");
//		this.values[1] = new BasicNameValuePair("j_username", login.account);
//		this.values[2] = new BasicNameValuePair("j_password_ext", login.psw);
//		this.values[3] = new BasicNameValuePair("j_passdec", login.getEncod());
//		
//		this.nameValuePair.put("nameValuePair", values);
		formMap.put("urlBeforeLogin", "");
		formMap.put("j_username", login.account);
		formMap.put("j_password_ext", login.psw);
		formMap.put("j_passdec", login.getEncod());
	}
	
	@Override
	public void process(Page page) {
		//System.out.println(page.getHtml());
		//登陆帐号或者密码正确
		
		if(page.getUrl().regex(".*Auth.*").match()){
			if((page.getHtml().xpath("//input[@id='referrer']")).toString() != null){
				System.out.println("process -> 登陆信息错误！！！");			
			}else{
				setErrCode(0);//登录信息正确
				/*
				 * 查看验证用户未提交作业的表
				 * unfinishList=null说明作业都提交了，此时需要检查是否有新作业（即每个作业列表的第一个作业是否为未提交）
				 * unfinishList!=null说明还有未提交作业，需要检查是否有新作业，并且要对每个未提交作业状态进行验证
				 */
				unfinishList = unfinishDao.listWUser(login.id);
				//url模板:http://www.scholat.com/course/S_homeworkList.html?courseId=508
				String urlTemp = "http://www.scholat.com/course/S_homeworkList.html?courseId=%d";
				if(unfinishList.size()>0){
					courseidMap = new HashMap<Integer,String>();
					for(Unfinish unfinish:unfinishList){
						courseidMap.put(unfinish.getCourseId(), "");
						
					}
					for(int courseid : courseidMap.keySet()){
						urlTemp = String.format(urlTemp, courseid);
						page.addTargetRequest(urlTemp);
					}
				}else{
//					/*done
//					 * 检查是否有新作业
//					 * 将全部课程url爬取检查是否有新作业
//					 */
//					List<Integer> cidList = userCourseKeyDao.getCId(login.id);
//					for(int cid:cidList){
//						urlTemp = String.format(urlTemp, cid);
//						page.addTargetRequest(urlTemp);
//					}
					return; //结束爬虫
				}
			}
			
		}
//		//爬取课程列表
//		else if(page.getUrl().regex(".*getCourseList.*").match()){
//			JSONArray jsonArray = JSONArray.fromObject(page.getRawText());
//			int userId = login.id;
//			
//			//检查是否在数据库存在这些课程
//			List<Integer> listid = courseDao.listid();
//			if(listid != null && listid.size()>0){
//				for(int i = 0;i < jsonArray.size();i++){
//					if(listid.contains((Integer)jsonArray.getJSONObject(i).get("id"))){
//						jsonArray.remove(i);
//						//移除了集合里面的数据后，集合长度也随之变短
//						i--;
//					}
//				}
//			}
//			
//			//如果集合里还有数据，则继续爬虫
//			if(jsonArray.size()>0){
//				List<String> idList = new ArrayList<>();		
//				for(int i = 0;i < jsonArray.size();i++){
//					idList.add("http://www.scholat.com/course/S_homeworkList.html?courseId="+String.valueOf(jsonArray.getJSONObject(i).get("id")));
//				}	
//				//jsonArray里面有id，title，creatorName
//				page.putField("userId", userId);
//				page.putField("jsonArray", jsonArray);
//				page.addTargetRequests(idList);
//			}
//			
//		}
		//爬取某一门课程的作业链接
		else if(page.getUrl().regex(".*S_homeworkList.html.*").match()){
//			List<String> homeworkUrlList;
//			//这里要处理分页的情况
//			String urlFormat = page.getUrl().toString()+"&cpage=%d";
//			/*
//			 * mistake：
//			 * 分页的处理逻辑有错误，会进入死循环
//			 */
//			for(int i=2;;i++){
//				if(unfinishList == null){//首先判断用户是否有未提交作业
//					//如果用户的未提交作业表为空，则全部作业已提交,因为这不是第一次登录的爬虫								
//				}
//				else{
//					for(int courseid : courseidMap.keySet()){
//						for(Unfinish unfinish : unfinishList){
//							if(courseid == unfinish.getCourseId()){
//								int homeworkid = unfinish.getHomeworkId();
//								String tdid = "time"+String.valueOf(homeworkid);
//								String finish = page.getHtml().xpath("//td[id='"+tdid+"']//text()").toString();
//								System.out.println(finish);
//								if(finish != "未提交"){
//									//将未提交表中的已提交的作业删除
//									unfinishDao.deleteWHid(homeworkid,login.id);
//								}
//								unfinishList.remove(unfinish);								
//							}
//						}
//					}
//				}
//				
//				
//			}
			
			if(unfinishList != null && unfinishList.size()>0){
				String url = page.getUrl().toString();
				int courseid = Integer.parseInt(url.substring(url.indexOf("=")+1));
				String tdid = "time";
				
				for(Unfinish unfinish:unfinishList){
					if(courseid == unfinish.getCourseId()){
						String tdid2 = tdid + unfinish.getHomeworkId();
						String finish = page.getHtml().xpath("//td[id='"+tdid2+"']//text()").toString();
						if(finish!=null&&finish != "未提交"){
							unfinishDao.deleteWHid(unfinish);
						}
						
					}
				}
			}
			
		}
		//爬取作业详情页面
		else if(page.getUrl().regex(".*S_oneHomework.html?.*homeworkId.*").match()){
			//System.out.println("homework");
			String content = page.getHtml().xpath("//div[@class='notice_content']").replace(" ","").toString();
			String title = page.getHtml().xpath("//div[@class='notice_title']/text()").toString();	
			//System.out.println(title);
			//System.out.println(content);
			page.putField("title", title);
			page.putField("content", content);
		}
			
		
		
	}
	
	public CrawScholarPipeline getCrawScholarPipeline() {
		return this.crawScholarPipeline;
	}
	@Override
	public Site getSite() {
		
		return this.site;
		
	}
	
	public Login getLogin(){
		return this.login;
	}
	public Map<String, Object> getFormMap() {
		return formMap;
	}

	public void setFormMap(Map<String, Object> formMap) {
		this.formMap = formMap;
	}

	
	
	public void setSite(Site site){
		
		this.site = site;

	}

	public int getErrCode() {
		return errCode;
	}

	public void setErrCode(int errCode) {
		this.errCode = errCode;
	}


}

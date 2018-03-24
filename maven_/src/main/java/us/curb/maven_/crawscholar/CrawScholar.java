package us.curb.maven_.crawscholar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;

import net.sf.json.JSONArray;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.curb.maven_.crawscholar.pipeline.CrawScholarPipeline;
import us.curb.maven_.dao.CourseDao;
import us.curb.maven_.dao.HomeworkDao;
import us.curb.maven_.dao.UnfinishDao;
import us.curb.maven_.pojo.Unfinish;


/*
 * 用户第一次登录时的爬虫
 */
@Component
public class CrawScholar implements PageProcessor{
	
	@Qualifier("crawScholarPipeline")
	@Autowired
	private CrawScholarPipeline crawScholarPipeline;
	@Autowired
	CourseDao courseDao;

	@Autowired
	UnfinishDao unfinishDao;
	
	@Autowired
	HomeworkDao homeworkDao;
	
	//登陆时帐号或密码错误时的状态码，0表示错误
	private int errCode = 0;
	
	private Login login;
	
	private Site site;
		
	private Map<String, Object> formMap = new HashMap<String,Object>();

	private List<Integer> unfinishIdList;//用户的未提交作业的表数据
	
	private List<Integer> homeworkidList;
	
	//private Map<Integer,String> courseidMap;//用户的未提交作业的课程id
	
	
	public void init(int id,String username, String psw){
		
		login=new Login(id,username,psw);
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
				setErrCode(1);				
				page.addTargetRequest("http://www.scholat.com/getCourseList.html?type=3");//获取用户的课程信息				
			}
			
		}
		//爬取课程列表
		else if(page.getUrl().regex(".*getCourseList.*").match()){
			JSONArray jsonArray = JSONArray.fromObject(page.getRawText());
			jsonArray = jsonArray.getJSONArray(0);
			int userId = login.id;
			
			/**
			 * 下面代码在3.15晚注释
			 * lifumin
			 
			//检查是否在数据库存在这些课程
			List<Integer> listid = courseDao.listIdWUser(login.getId());
			if(listid != null && listid.size()>0){
				for(int i = 0;i < jsonArray.size();i++){
					if(listid.contains((Integer)jsonArray.getJSONObject(i).get("id"))){
						jsonArray.remove(i);
						//移除了集合里面的数据后，集合长度也随之变短
						i--;
					}
				}
			}
			*/
			//如果集合里还有数据，则继续爬虫
			if(jsonArray != null && jsonArray.size()>0){
				List<String> idList = new ArrayList<>();		
				for(int i = 0;i < jsonArray.size();i++){
					
					idList.add("http://www.scholat.com/course/S_homeworkList.html?courseId="+String.valueOf(jsonArray.getJSONObject(i).get("id")));
					
				}	
				/**
				 * 下面代码于3.15注释
				 * lifumin
				 
				//jsonArray里面有id，title，creatorName
				page.putField("userId", userId);
				page.putField("jsonArray", jsonArray);
				**/

				page.addTargetRequests(idList);
				idList = null;
			}
			
			jsonArray = null;

			
		}
		//爬取某一门课程的作业链接
		else if(page.getUrl().regex(".*S_homeworkList.html.*").match()){
			homeworkidList = homeworkDao.listHomeworkid();
			String tdid="time";
			String tdid2=null;
			String finish=null;
			unfinishIdList = unfinishDao.listHId(login.id);
			String pageUrl = page.getUrl().toString();
			//int courseId = Integer.parseInt(pageUrl.substring(pageUrl.indexOf("=")+1,pageUrl.indexOf("&")));
//			System.out.println(page.getHtml().xpath("//td[@id='time9648']/span/text()").toString());
			List<String> homeworkUrlList = page.getHtml().xpath("//tr[@class='altrow']//td[1]").links().all();
			if(homeworkUrlList!=null && homeworkUrlList.size()>0){
				JSONArray jsonArray = new JSONArray();
				int courseid = Integer.parseInt(pageUrl.substring(pageUrl.indexOf("=")+1));
				for(int i = 0;i < homeworkUrlList.size();i++){
					String homeworkUrl = homeworkUrlList.get(i);
					int homeworkId = Integer.parseInt(homeworkUrl.substring(homeworkUrl.lastIndexOf("=")+1));
					tdid2 = tdid+String.valueOf(homeworkId);
					finish = page.getHtml().xpath("//td[@id='"+tdid2+"']/span/text()").toString();
					
					if(finish!=null && finish.equals("未提交")){
						
						/**
						 * start
						 * 2018-03-20
						 * lifumin
						 * 如果数据库此人的未完成作业表已有数据，将跳过。
						 */
						if(!unfinishIdList.contains(homeworkId)){
							JSONObject jsonobject = new JSONObject();
							jsonobject.put("userid", login.id);
							jsonobject.put("courseid",courseid );
							jsonobject.put("homeworkid", homeworkId);
							
							jsonArray.add(jsonobject);
						}
						/**
						 * end
						 */
						
						if(homeworkidList != null && homeworkidList.size() > 0){
							if(homeworkidList.contains(homeworkId)){
								homeworkUrlList.remove(i);
								i--;
							}
						}
					}else{
						homeworkUrlList.remove(i);
						i--;
					}
					
					
				}
				
				if(jsonArray != null && jsonArray.size()>0){
					page.putField("unfinish", jsonArray);
				}
//				System.out.println("homework list:"+homeworkUrlList);
//				System.out.println(homeworkUrlList.size());
				page.addTargetRequests(homeworkUrlList);
				jsonArray = null;
			}			
			homeworkUrlList = null;
			
			//List<String> homeworkTitleList = page.getHtml().xpath("//tr[@class='altrow']//td[1]//a//text()").all();												
		}
		//爬取作业详情页面
		else if(page.getUrl().regex(".*S_oneHomework.html?.*homeworkId.*").match()){
			String content = page.getHtml().xpath("//div[@class='notice_content']").replace(" ","").toString();
			String title = page.getHtml().xpath("//div[@class='notice_title']/text()").toString();	
			System.out.println(title);
			System.out.println(content);
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

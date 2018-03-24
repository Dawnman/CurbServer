package us.curb.maven_.serviceimpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import net.sf.json.JSONArray;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.curb.maven_.crawscholar.Login;
import us.curb.maven_.crawscholar.pipeline.CrawScholarPipeline;
import us.curb.maven_.dao.CourseDao;
import us.curb.maven_.dao.UserDao;

@Service("crawScholarImpl")
public class CrawScholarImpl implements PageProcessor {

	@Qualifier("crawScholarPipeline")
	@Autowired
	private CrawScholarPipeline crawScholarPipeline;
	@Autowired
	CourseDao courseDao;
	@Autowired
	UserDao userDao;
	
	private int errCode=0;
	
	private Login login;
	
	private Site site;
		
	private Map<String, Object> formMap = new HashMap<String,Object>();

	
	@Override
	public void process(Page page) {
		
		//登陆帐号或者密码错误
		if(page.getUrl().regex(".*Auth.*").match()){
			if((page.getHtml().xpath("//input[@id='referrer']")).toString() != null){
				System.out.println("process -> 登陆信息错误！！！");			
			}else{
				setErrCode(1);				
				page.addTargetRequest("http://www.scholat.com/getCourseList.html?type=3");//获取用户的课程信息				
			}
			
		}
		//爬取课程列表
		if(page.getUrl().regex(".*getCourseList.*").match()){
			JSONArray jsonArray = JSONArray.fromObject(page.getRawText());
			int userId = login.id;
			
			//检查是否在数据库存在这些课程
			List<Integer> listid = courseDao.listid();
			if(listid != null && listid.size()>0){
				for(int i = 0;i < jsonArray.size();i++){
					if(listid.contains((Integer)jsonArray.getJSONArray(0).getJSONObject(i).get("id"))){
						jsonArray.remove(i);
						//移除了集合里面的数据后，集合长度也随之变短
						i--;
					}
				}
			}
			
			//如果集合里还有数据，则继续爬虫
			if(jsonArray.size()>0){
				List<String> idList = new ArrayList<>();		
				for(int i = 0;i < jsonArray.size();i++){
					idList.add("http://www.scholat.com/course/S_homeworkList.html?courseId="+String.valueOf(jsonArray.getJSONArray(0).getJSONObject(i).get("id")));
				}	
				//jsonArray里面有id，title，creatorName
				page.putField("userId", userId);
				page.putField("jsonArray", jsonArray);
				page.addTargetRequests(idList);
			}
			
		}
		//爬取某一门课程的作业链接
		else if(page.getUrl().regex(".*S_homeworkList.html.*").match()){
		
			List<String> homeworkUrlList = page.getHtml().xpath("//tr[@class='altrow']//td[1]").links().all();
			//List<String> homeworkTitleList = page.getHtml().xpath("//tr[@class='altrow']//td[1]//a//text()").all();
			page.addTargetRequests(homeworkUrlList);
			
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
	
	public int getErrCode() {
		return errCode;
	}

	public void setErrCode(int errCode) {
		this.errCode = errCode;
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

	public void init(int id,String acc, String psw){
		
		login=new Login(id,acc,psw);
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
		System.out.println(formMap);
	}
	
	public void setSite(Site site){
		
		this.site = site;

	}

}

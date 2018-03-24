package us.curb.maven_.crawscholar;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.curb.maven_.crawscholar.pipeline.CrawScholarPipeline;
import us.curb.maven_.dao.CourseDao;
import us.curb.maven_.dao.UnfinishDao;
import us.curb.maven_.pojo.Unfinish;

@Component
public class CrawLogin implements PageProcessor{

	@Qualifier("crawScholarPipeline")
	@Autowired
	private CrawScholarPipeline crawScholarPipeline;
	@Autowired
	CourseDao courseDao;

	@Autowired
	UnfinishDao unfinishDao;
	
	//登陆时帐号或密码错误时的状态码，0表示错误
	private int errCode = 0;
	
	private Login login;
	
	private Site site;
		
	private Map<String, Object> formMap = new HashMap<String,Object>();

	private List<Unfinish> unfinishList;//用户的未提交作业的表数据
	
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
				//page.addTargetRequest("http://www.scholat.com/getCourseList.html?type=3");//获取用户的课程信息				
			}
			
		}
	}

	public CrawScholarPipeline getCrawScholarPipeline() {
		return crawScholarPipeline;
	}

	public void setCrawScholarPipeline(CrawScholarPipeline crawScholarPipeline) {
		this.crawScholarPipeline = crawScholarPipeline;
	}

	public CourseDao getCourseDao() {
		return courseDao;
	}

	public void setCourseDao(CourseDao courseDao) {
		this.courseDao = courseDao;
	}

	public UnfinishDao getUnfinishDao() {
		return unfinishDao;
	}

	public void setUnfinishDao(UnfinishDao unfinishDao) {
		this.unfinishDao = unfinishDao;
	}

	public int getErrCode() {
		return errCode;
	}

	public void setErrCode(int errCode) {
		this.errCode = errCode;
	}

	public Login getLogin() {
		return login;
	}

	public void setLogin(Login login) {
		this.login = login;
	}

	public Site getSite() {
		return site;
	}

	public void setSite(Site site) {
		this.site = site;
	}

	public Map<String, Object> getFormMap() {
		return formMap;
	}

	public void setFormMap(Map<String, Object> formMap) {
		this.formMap = formMap;
	}

	public List<Unfinish> getUnfinishList() {
		return unfinishList;
	}

	public void setUnfinishList(List<Unfinish> unfinishList) {
		this.unfinishList = unfinishList;
	}
	
	
}

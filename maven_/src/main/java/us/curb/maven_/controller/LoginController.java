package us.curb.maven_.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.model.HttpRequestBody;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.utils.HttpConstant;
import us.curb.maven_.crawscholar.CrawLogin;
import us.curb.maven_.crawscholar.CrawScholar;
import us.curb.maven_.dao.CourseDao;
import us.curb.maven_.dao.HomeworkDao;
import us.curb.maven_.dao.UserDao;
import us.curb.maven_.pojo.Course;
import us.curb.maven_.serviceimpl.CrawScholarImpl;
import us.curb.maven_.serviceimpl.UserServiceImpl;

@RequestMapping("")
@RestController
public class LoginController {

	@Autowired
	CourseDao courseDao;
	@Autowired
	UserServiceImpl userServiceImpl;
	@Autowired
	CrawScholar crawScholar;
	@Autowired
	UserDao userDao;
	@Resource
	HomeworkDao homeworkDao;
	@Autowired 
	CrawLogin crawLogin;
	
	@Resource
	CrawScholarImpl crawScholarImpl;
	
//	//用户用学者网帐号密码获取信息
//	//@RequestMapping(value="/logintest",method=RequestMethod.POST)
//	public @ResponseBody String curbLogin(HttpServletRequest request, HttpServletResponse response, @RequestParam("id")int userKey,
//			@RequestParam("userName")String userName, @RequestParam("password")String password){
//		int userid = 0;
//		userid = userDao.getId(userName);
//		if(userid == 0){
//			/*
//			 * addUserFromClient要改成仅仅是添加用户的功能
//			 */
//			userServiceImpl.addUserFromClient(userKey, userName, password);//retun CourseList
//			/*
//			 * 抓取课程和作业信息并将未提交的作业id添加到unfinish表中
//			 */
//			
//			/*
//			 * 根据unfinish表中的courseid和homeworkid将作业信息返回给客户端
//			 */
//		}else{
//			/*
//			 *检查未提交作业的表的信息
//			 */
//			
//			/*
//			 * 根据unfinish表中的courseid和homeworkid将作业信息返回给客户端
//			 */
//		}
//		
//		return null;
//	}
	
	@RequestMapping(value="/logintest",method=RequestMethod.POST)
	public @ResponseBody String testLogin(HttpServletRequest request, HttpServletResponse response, @RequestParam("id")int userKey,
			@RequestParam("userName")String userName, @RequestParam("password")String password){
		//这里有个日志输出比较好
		
		//由于是新用户，所以要调用爬虫爬取
		//ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:/spring/applicationContext*.xml");
		//CrawScholar crawScholar = applicationContext.getBean(CrawScholar.class);
		crawLogin.init(userKey,userName,password);
		Request crawRequest = new Request("http://www.scholat.com/Auth.html");
		crawRequest.setMethod(HttpConstant.Method.POST);
		//request.setExtras(crawScholar.nameValuePair);
		crawRequest.addCookie("JSESSIONID",crawLogin.getLogin().cookie);
		//下面的一句才真正实现登录
		crawRequest.setRequestBody(HttpRequestBody.form(crawLogin.getFormMap(), "utf-8"));
		Spider spider = Spider.create(crawLogin);
		spider.addRequest(crawRequest);
		spider.addPipeline(crawLogin.getCrawScholarPipeline()).addPipeline(new ConsolePipeline());
		//spider.addUrl("http://www.scholat.com/getCourseList.html?type=3");
		spider.thread(1).run();
		//登陆错误时的返回信息
		if(crawLogin.getErrCode()==0){
			
			
			//此处返回帐号密码错误信息的代码
			return "000";
		}
		else{
			if(userDao.getId(userName) == userKey){
				return "111";
			}
			userServiceImpl.addUserFromClient(userKey, userName, password);
			return "111";
		}
		
	}
	

//	@RequestMapping(value="/crawScholar",method = RequestMethod.GET)
//	public @ResponseBody CourseAndHomework(){
//		
//	}
	
	
	
}

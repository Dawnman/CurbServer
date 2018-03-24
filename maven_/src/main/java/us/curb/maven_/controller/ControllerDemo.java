package us.curb.maven_.controller;

import java.util.List;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.model.HttpRequestBody;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.utils.HttpConstant;
import us.curb.maven_.crawscholar.CrawScholar;
import us.curb.maven_.dao.CourseDao;
import us.curb.maven_.dao.HomeworkDao;
import us.curb.maven_.dao.UserDao;
import us.curb.maven_.pojo.Course;
import us.curb.maven_.pojo.Homework;
import us.curb.maven_.pojo.User;
import us.curb.maven_.serviceimpl.UserServiceImpl;

@RequestMapping("")
@RestController
public class ControllerDemo {

	@Autowired
	CrawScholar crawScholar;
	@Autowired
	CourseDao courseDao;
	@Autowired
	UserServiceImpl userServiceImpl;
	@Autowired
	UserDao userDao;
	@Autowired
	HomeworkDao homeworkDao;
	
	@RequestMapping(value="/showJson",method = RequestMethod.GET)
	public @ResponseBody List<Course> show(){
		return courseDao.listCourse();
	}
	
	/**
	 * 返回用户未完成作业详情
	 * @param userid
	 * @return
	 */
	@RequestMapping(value="/showUnfinishHomework/{userid}",method = RequestMethod.GET)
	public @ResponseBody List<Homework> show(@PathVariable("userid")int userid){
		User user = userDao.getUser(userid);
		
		crawScholar.init(user.getUserId(),user.getAccount(),user.getPassword());
		Request crawRequest = new Request("http://www.scholat.com/Auth.html");
		crawRequest.setMethod(HttpConstant.Method.POST);
		//request.setExtras(crawScholar.nameValuePair);
		crawRequest.addCookie("JSESSIONID",crawScholar.getLogin().cookie);
		//下面的一句才真正实现登录
		crawRequest.setRequestBody(HttpRequestBody.form(crawScholar.getFormMap(), "utf-8"));
		Spider spider = Spider.create(crawScholar);
		spider.addRequest(crawRequest);
		spider.addPipeline(crawScholar.getCrawScholarPipeline()).addPipeline(new ConsolePipeline());
		//spider.addUrl("http://www.scholat.com/getCourseList.html?type=3");
		spider.thread(1).run();
		List<Homework> homeworkList = homeworkDao.listUnfinish(userid);
		if(homeworkList != null && homeworkList.size() > 0){
			return homeworkList;
		}else
			return null;
	}
	//用户用学者网帐号密码获取信息
	@RequestMapping(value="/curbScholat",method=RequestMethod.POST)
	public @ResponseBody String curbLogin(HttpServletRequest request, HttpServletResponse response, int userKey,
			String userName, String password){
		int userid = 0;
		userid = userDao.getId(userName);
		if(userid == 0){
			/*
			 * addUserFromClient要改成仅仅是添加用户的功能
			 */
			userServiceImpl.addUserFromClient(userKey, userName, password);//retun CourseList
			/*
			 * 抓取课程和作业信息并将未提交的作业id添加到unfinish表中
			 */
			
			/*
			 * 根据unfinish表中的courseid和homeworkid将作业信息返回给客户端
			 */
		}else{
			/*
			 *检查未提交作业的表的信息
			 */
			
			/*
			 * 根据unfinish表中的courseid和homeworkid将作业信息返回给客户端
			 */
		}
		
		return null;
	}
	
	
//	@RequestMapping(value="/crawScholar",method = RequestMethod.GET)
//	public @ResponseBody CourseAndHomework(){
//		
//	}
	
	
	
}

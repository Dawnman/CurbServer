package us.curb.maven_.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestParam;

import us.curb.maven_.pojo.Homework;

public interface UserService {

	//通过用学者网用户名和密码来验证登陆,还要储存每个用户特定的ID
	
	//用户查看学者网信息，包括了第一次登陆后
	public void addUserFromClient(int userKey, String userName,String password);
	
	//用户获取课程列表
	//public List<Course> getCourseList(HttpServletRequest request, HttpServletResponse response, @RequestParam("userKey") int userKey, @RequestParam("userName") String userName,@RequestParam("password") String password); 

	//用户获取某课程的作业详情
	public List<Homework> getHomeworkList(HttpServletRequest request, HttpServletResponse response, @RequestParam("courseId") int courseId);
}

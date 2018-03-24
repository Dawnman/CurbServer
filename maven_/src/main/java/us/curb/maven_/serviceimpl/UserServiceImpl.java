package us.curb.maven_.serviceimpl;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.springframework.stereotype.Service;


import us.curb.maven_.dao.CourseDao;
import us.curb.maven_.dao.HomeworkDao;
import us.curb.maven_.dao.UserDao;
import us.curb.maven_.pojo.Homework;
import us.curb.maven_.pojo.User;
import us.curb.maven_.service.UserService;

@Service("userServiceImpl")
public class UserServiceImpl implements UserService{
	
	@Resource
	UserDao userDao;
	
	@Resource
	CourseDao courseDao;
	
	@Resource
	HomeworkDao homeworkDao;
	
	@Resource
	CrawScholarImpl crawScholarImpl;
	@Override
	public void addUserFromClient(int userKey,String userName, String password) {
		
		User user = new User(userKey,userName,password);
		userDao.insertUser(user);
			
}
	
//
//	@Override
//	public List<Course> getCourseList(HttpServletRequest request, HttpServletResponse response, int userKey,
//			String userName, String password) {
//				
//		return courseDao.listCourseWId(userKey);
//	}

	@Override
	public List<Homework> getHomeworkList(HttpServletRequest request, HttpServletResponse response, int courseId) {

		return homeworkDao.listHomework(courseId);
	}

}

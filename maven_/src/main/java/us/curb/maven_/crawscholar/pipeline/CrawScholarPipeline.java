package us.curb.maven_.crawscholar.pipeline;

import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;		
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.curb.maven_.dao.CourseDao;
import us.curb.maven_.dao.HomeworkDao;
import us.curb.maven_.dao.UnfinishDao;
import us.curb.maven_.dao.UserCourseKeyDao;
import us.curb.maven_.pojo.Course;
import us.curb.maven_.pojo.Homework;
import us.curb.maven_.pojo.Unfinish;
import us.curb.maven_.pojo.UserCourseKey;



@Component("crawScholarPipeline")
public class CrawScholarPipeline implements Pipeline{

	@Resource
	CourseDao courseDao;

	@Resource
	HomeworkDao homeworkDao;
	
	@Resource
	UserCourseKeyDao userCourseKeyDao;
	
	@Resource
	UnfinishDao unfinishDao;
	
	@Override
	public void process(ResultItems resultItems, Task task) {
		String pattern1 = ".*getCourseList.*";
		String pattern2 = ".*S_oneHomework.html.*courseId.*homeworkId.*";
		String pattern3 = ".*S_homeworkList.html.*";
		
		if(Pattern.matches(pattern1, resultItems.getRequest().getUrl())){
			
			JSONArray jsonArray = resultItems.get("jsonArray");
			Integer userId = (Integer)resultItems.get("userId");
			
			if(jsonArray!=null && jsonArray.size() > 0){
				for(int i = 0;i < jsonArray.size();i++){
					Course course = new Course();
					UserCourseKey userCourseKey = new UserCourseKey();
					
					JSONObject object = jsonArray.getJSONObject(i);
					course.setTitle((String)object.get("title"));
					course.setTeacher((String)object.get("creatorName"));
					course.setCourseId((int)object.get("id"));
					course.setUrl("http://www.scholat.com/course/S_homeworkList.html?courseId="+String.valueOf(object.get("id")));
					course.setUserId(userId);
					
					userCourseKey.setUserId((int)userId);
					userCourseKey.setCourseId((int)object.get("id"));
					
					courseDao.add(course);
					userCourseKeyDao.add(userCourseKey);
					
					System.out.println("insert.");
					course = null;
					userCourseKey = null;
				}
			}
			
		}
		if(Pattern.matches(pattern2, resultItems.getRequest().getUrl())){
			Homework homework = new Homework();
			String url = resultItems.getRequest().getUrl();
			int homeworkId = Integer.parseInt(url.substring(url.lastIndexOf("=")+1));
			int courseId = Integer.parseInt(url.substring(url.indexOf("=")+1, url.indexOf("&")));
			String content = resultItems.get("content");
			String title = resultItems.get("title");
			
			homework.setHomeworkId(homeworkId );
			homework.setTitle(title);
			homework.setContent(content);
			homework.setImgurl(null);
			homework.setCourseId(courseId);
			
			homeworkDao.add(homework);
			System.out.println("insert homework!");
			homework = null;
			
		}
		if(Pattern.matches(pattern3, resultItems.getRequest().getUrl())){
			JSONArray jsonArray = resultItems.get("unfinish");
			if(jsonArray!=null && jsonArray.size()>0){
				for(int i=0;i<jsonArray.size();i++){
					Unfinish unfinish = new Unfinish();
					unfinish.setUserId(jsonArray.getJSONObject(i).getInt("userid"));
					unfinish.setCourseId(jsonArray.getJSONObject(i).getInt("courseid"));
					unfinish.setHomeworkId(jsonArray.getJSONObject(i).getInt("homeworkid"));
					System.out.println("插入未提交作业表");
					unfinishDao.add(unfinish);
				}
				jsonArray = null;
			}
		}
		else{
			System.out.println("no match!");
		}

	}

}

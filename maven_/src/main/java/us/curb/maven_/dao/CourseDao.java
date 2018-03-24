package us.curb.maven_.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import us.curb.maven_.pojo.Course;




public interface CourseDao {
	@Insert("insert into course values (#{courseId},#{title},#{teacher},#{url},#{userId})")
	public int add(Course course);
	
	@Select("select course_id from course")
	public List<Integer> listid();
	
	@Select("select * from course")
	@Results({
	        @Result(property = "courseId", column = "course_id"),
	        @Result(property = "title", column = "title"),
	        @Result(property = "teacher", column = "teacher"),
	        @Result(property = "title", column = "title"),
	        @Result(property = "url", column = "url")
	})
	public List<Course> listCourse();
	
	@Select("select course_id from course where user_id=#{userid}")
	public List<Integer> listIdWUser(int userid);

//	@Select("select * from course where ")
//	@Results({
//	        @Result(property = "courseId", column = "course_id"),
//	        @Result(property = "title", column = "title"),
//	        @Result(property = "teacher", column = "teacher"),
//	        @Result(property = "title", column = "title"),
//	        @Result(property = "url", column = "url")
//	})
//	public List<Course> listCourseWId(int userId);
	
}

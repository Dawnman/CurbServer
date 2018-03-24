package us.curb.maven_.dao;
import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import us.curb.maven_.pojo.UserCourseKey;

public interface UserCourseKeyDao {

	@Insert("insert into user_course values (#{userId},#{courseId})")
	public int add(UserCourseKey userCourseKey);
	
	@Select("select course_id from user_course where user_id=#{userId}")
	public List<Integer> getCId(int userId);
	
}

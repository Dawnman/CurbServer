package us.curb.maven_.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import us.curb.maven_.pojo.Homework;


public interface HomeworkDao {

	@Insert("insert into homework(homework_id,title,content,imgurl,course_id) values (#{homeworkId},#{title},#{content},null,#{courseId})")
	public int add(Homework homeWork);
	
	@Select("select * from homework where course_id = #{courseId}")
	public List<Homework> listHomework(int courseId);
	
	@Select("select * from homework where homework_id in (select homework_id from unfinish where user_id = #{userid})")
	public List<Homework> listUnfinish(int userid);
	
	@Select("select homework_id from homework;")
	public List<Integer> listHomeworkid();
}

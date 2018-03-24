package us.curb.maven_.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import us.curb.maven_.pojo.Unfinish;

public interface UnfinishDao {
	
	@Insert("insert ignore into unfinish values(#{userId},#{homeworkId},#{courseId})")
	public int add(Unfinish unfinish);
	
	@Select("select * from unfinish where user_id=#{userId}")
	public List<Unfinish> listWUser(int userId);
	
	@Select("select homework_id from unfinish where user_id=#{userId}")
	public List<Integer> listHId(int userId);
	
	@Delete("delete from unfinish where homework_id=#{homeworkId} and user_id=#{userId}")
	public void deleteWHid(Unfinish unfinish);
	
	
	
}

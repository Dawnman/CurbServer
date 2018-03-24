package us.curb.maven_.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import us.curb.maven_.pojo.User;

public interface UserDao {

	@Insert("insert into user(account,password) values (#{account},#{password})")
	public int add(User user);
	
	@Select("select user_id from user")
	public List<Integer> listId();
	
	@Select("select user_id from user where account=#{account}")
	public int getId(String account);
	
	@Select("select * from user where user_id=#{userId}")
	public User getUser(int userId);
	
	//@Insert("Insert into user values (#{userId},#{account},#{password})")
	//public void insertUser(int userId, String account, String password);

	@Insert("Insert into user values (#{userId},#{account},#{password})")
	public void insertUser(User user);
}
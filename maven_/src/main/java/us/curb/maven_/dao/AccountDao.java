package us.curb.maven_.dao;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import us.curb.maven_.pojo.Account;


public interface AccountDao {
	
	@Insert("insert ignore into account values(#{id},#{account},#{name},#{pwd},#{type},#{createTime},#{updateTime},#{signatur},#{email})")
	public int add(Account account);
	
	@Select("select id from account where name=#{accountName} and pwd=#{password}")
	public String selectId(String accountName, String password);
	
	/**
	 * 2018-03-24
	 * lifumin
	 * @param registerId
	 * @return
	 * 检查id的合法性，查找数据库是否已经有了相同的id
	 */
	@Select("select id from account where id=#{registerId}")
	public String selectOneId(String registerId);
	
	/**
	 * 2018-03-24
	 * lifumin
	 * @param registerName
	 * @return name
	 * 检查注册帐户的合法性，查找数据库是否已经有了注册的用户名
	 */
	@Select("select name from account where name=#{registerName};")
	public String selectOneName(String registerName);
	
	@Insert("insert into account values(#{registerId},#{registerName},#{password}")
	public int insertInAccount(String registerId,String registerName,String password);
}

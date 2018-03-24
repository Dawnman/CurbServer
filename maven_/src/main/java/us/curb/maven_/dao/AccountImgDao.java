package us.curb.maven_.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import us.curb.maven_.pojo.AccountImg;

public interface AccountImgDao {

	@Insert("insert ignore into accountimg(account_id,img) values(#{accountId},#{img})")
	public int add(AccountImg accountimg);
	
	@Select("select img from accountimg where account_id=#{accountId}")
	public String selectImgUrl(String accountId);
}

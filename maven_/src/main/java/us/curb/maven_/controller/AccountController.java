package us.curb.maven_.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import us.curb.maven_.dao.AccountDao;
import us.curb.maven_.dao.AccountImgDao;
import us.curb.maven_.pojo.AccountImg;
import us.curb.maven_.serviceimpl.UploadService;

@RequestMapping("curb")
@RestController
public class AccountController {

	@Autowired
	UploadService uploadService;
	@Autowired
	AccountDao accountDao;
	@Autowired
	AccountImgDao accountImgDao;
	
	/**
	 * 处理用户上传的头像
	 * @param req
	 * @param resp
	 * @param accountId
	 * @return AccountImg的json数据
	 */
	@RequestMapping(value="account/img",method=RequestMethod.POST)
	public @ResponseBody AccountImg uploadAccountImg(HttpServletRequest req, HttpServletResponse resp
														,@RequestParam("accountId")String accountId){
		
		
		return null;
	}
	
	/**
	 * 用户在客户端登录
	 * @param req
	 * @param resp
	 * @param accountName
	 * @param password
	 * @return 登陆id 表示成功
	 * 			null 表示失败
	 */
	@RequestMapping(value="account/login",method=RequestMethod.POST)
	public @ResponseBody String loginAccount(HttpServletRequest req, HttpServletResponse resp
											,@RequestParam("account")String accountName
											,@RequestParam("password")String password){
		
		String accountId = accountDao.selectId(accountName, password);
		
		if(accountId != null)
			return accountId;
		else
			return null;
	}
	
	
	/**
	 * 用户在客户端注册
	 * @param req
	 * @param resp
	 * @param accountId
	 * @param accountName
	 * @param password
	 * @return id 表示成功
	 * 		   null 表示失败
	 */
	@RequestMapping(value="account/register",method=RequestMethod.POST)
	public @ResponseBody String registerAccount(HttpServletRequest req, HttpServletResponse resp
											,@RequestParam("id")String accountId,@RequestParam("account")String accountName
											,@RequestParam("password")String password){
		
		String isId = accountDao.selectOneId(accountId);
		String isName = accountDao.selectOneName(accountName);
		if(isId != null || isName != null){
			//返回id或者帐号重复
			return "000";
		}else{
			accountDao.insertInAccount(accountId, accountName, password);
			return "111";
		}
		
	}
}

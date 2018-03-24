package us.curb.maven_.crawscholar;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;

import us.codecraft.webmagic.Site;

public class Login{
	public String cookie;
	
	public String sesion_token;
	
	public String client_url="http://www.scholat.com/Auth.html";
	
	public int id;
	
	public String account;
	
	public String psw;
	
	public Login(int id ,String account, String psw){
		this.id = id;
		this.account = account;
		this.psw = psw;
	}
	
	public Site getSite(){
		if(this.cookie != null){
			Site site = Site.me().setRetryTimes(3).setSleepTime(300).setCharset("utf-8")
					.setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.62 Safari/537.36")
					.setDomain("www.scholat.com")
					.addCookie("JSESSIONID",this.cookie);
			return site;
		}
		else
			return null;
		
		
	}
	
	public void loginSetCookie(){
		
		Connection conn = Jsoup.connect(client_url);
		conn.method(Method.GET);
		conn.followRedirects(false);
		Response response;
		try {
			response = conn.execute();
			System.out.println(response.cookies());
			this.cookie = response.cookies().get("JSESSIONID");
		} catch (IOException e) {
			e.printStackTrace();
		}     
		 
	}
	
	public String getEncod(){
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine scriptEngine = manager.getEngineByName("javascript");
		
		String jsFilename = "C:/SSM/strEnc.js";
		try {
			FileReader reader = new FileReader(jsFilename);
			scriptEngine.eval(reader);
			
			if(scriptEngine instanceof Invocable){
				Invocable invoke = (Invocable)scriptEngine;
				String key1=this.cookie;
				String key2="userc";
				String key3="pfir";
				String strcode = (String)invoke.invokeFunction("strEnc", psw, key1, key2, key3);
				return strcode;
			}
			
		} catch (FileNotFoundException | ScriptException | NoSuchMethodException e) {
			e.printStackTrace();
		}
		return null;

			
	}
	
	public String getCookie() {
		return cookie;
	}

	public String getSesion_token() {
		return sesion_token;
	}

	public void setSesion_token(String sesion_token) {
		this.sesion_token = sesion_token;
	}

	public String getClient_url() {
		return client_url;
	}

	public void setClient_url(String client_url) {
		this.client_url = client_url;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPsw() {
		return psw;
	}

	public void setPsw(String psw) {
		this.psw = psw;
	}

	public void setCookie(String cookie) {
		this.cookie = cookie;
	}
	
	
}
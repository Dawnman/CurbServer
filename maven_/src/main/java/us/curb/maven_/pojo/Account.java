package us.curb.maven_.pojo;

import java.util.Date;

public class Account {

	private String id;
	
	private String account;
	
	private String name;
	
	private String pwd;
	
	private String type;
	
	private Date createTime;
	
	private Date updateTime;
	
	private String signature;
	
	private String email;
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	private String isDel;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getIsDel() {
		return isDel;
	}

	public void setIsDel(String isDel) {
		this.isDel = isDel;
	}

	@Override
	public String toString() {
		return "Account [id=" + id + ", account=" + account + ", name=" + name + ", pwd=" + pwd + ", type=" + type
				+ ", createTime=" + createTime + ", updateTime=" + updateTime + ", signature=" + signature + ", email="
				+ email + ", isDel=" + isDel + "]";
	}

	
	
	
	
}

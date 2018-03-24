package us.curb.maven_.pojo;

import java.util.Date;

public class UserCreate {

	private String id;
	
	private String title;
	
	private String userId;
	
	private String descrip;
	
	private Date createTime;
	
	private Date updateTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getDescrip() {
		return descrip;
	}

	public void setDescrip(String descrip) {
		this.descrip = descrip;
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

	@Override
	public String toString() {
		return "UserCreate [id=" + id + ", title=" + title + ", userId=" + userId + ", descrip=" + descrip
				+ ", createTime=" + createTime + ", updateTime=" + updateTime + "]";
	}
	
	
}

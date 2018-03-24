package us.curb.maven_.pojo;

import java.util.Date;

public class Smalldata {
	
	private String id;
	private String title;
	private String descrip;
	private String img;
	private Date createTime;
	private String creator;
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
	public String getDescrip() {
		return descrip;
	}
	public void setDescrip(String descrip) {
		this.descrip = descrip;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	@Override
	public String toString() {
		return "Smalldata [id=" + id + ", title=" + title + ", descrip=" + descrip + ", img=" + img + ", createTime="
				+ createTime + ", creator=" + creator + "]";
	}
	
	
}

package us.curb.maven_.pojo;

import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

public class AccountImg {

	private int id;
	
	private String accountId;
	
	private String img;
	
	private Date createTime;
	
	private Date updateTime;
	
	private MultipartFile imgFile;

	public MultipartFile getImgFile() {
		return imgFile;
	}

	public void setImgFile(MultipartFile imgFile) {
		this.imgFile = imgFile;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
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

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Override
	public String toString() {
		return "AccountImg [id=" + id + ", accountId=" + accountId + ", img=" + img + ", createTime=" + createTime
				+ ", updateTime=" + updateTime + ", imgFile=" + imgFile + "]";
	}


	
	
}

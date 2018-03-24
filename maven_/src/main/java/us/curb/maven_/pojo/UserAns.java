package us.curb.maven_.pojo;

public class UserAns  {
    
	private String id;
	
	private String userId;

    private String sdId;

    private String questionNum;
    
	private String answer;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getSdId() {
		return sdId;
	}

	public void setSdId(String sdId) {
		this.sdId = sdId;
	}

	public String getQuestionNum() {
		return questionNum;
	}

	public void setQuestionNum(String questionNum) {
		this.questionNum = questionNum;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	@Override
	public String toString() {
		return "UserAns [id=" + id + ", userId=" + userId + ", sdId=" + sdId + ", questionNum=" + questionNum
				+ ", answer=" + answer + "]";
	}
	
	
	
    
}
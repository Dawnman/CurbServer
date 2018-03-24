package us.curb.maven_.pojo;

public class CreateDetail {

	private Integer id;
	
	private String createId;
	
	private String question;
	
	private Integer questionNum;
	
	private String option1;
	
	private String option2;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCreateId() {
		return createId;
	}

	public void setCreateId(String createId) {
		this.createId = createId;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public Integer getQuestionNum() {
		return questionNum;
	}

	public void setQuestionNum(Integer questionNum) {
		this.questionNum = questionNum;
	}

	public String getOption1() {
		return option1;
	}

	public void setOption1(String option1) {
		this.option1 = option1;
	}

	public String getOption2() {
		return option2;
	}

	public void setOption2(String option2) {
		this.option2 = option2;
	}

	@Override
	public String toString() {
		return "CreateDetail [id=" + id + ", createId=" + createId + ", question=" + question + ", questionNum="
				+ questionNum + ", option1=" + option1 + ", option2=" + option2 + "]";
	}
	
	
}

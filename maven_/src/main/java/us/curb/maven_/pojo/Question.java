package us.curb.maven_.pojo;

public class Question{
   
	private String questionNum;

	private String sdId;
	
	private String question;

    private String option1;

    private String option2;

    
    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question == null ? null : question.trim();
    }

    public String getOption1() {
        return option1;
    }

    public void setOption1(String option1) {
        this.option1 = option1 == null ? null : option1.trim();
    }

    public String getOption2() {
        return option2;
    }

    public void setOption2(String option2) {
        this.option2 = option2 == null ? null : option2.trim();
    }
    
    public String getQuestionNum() {
        return questionNum;
    }

    public void setQuestionNum(String questionNum) {
        this.questionNum = questionNum;
    }

    public String getSdId() {
        return sdId;
    }

    public void setSdId(String sdId) {
        this.sdId = sdId;
    }

	@Override
	public String toString() {
		return "Question [questionNum=" + questionNum + ", sdId=" + sdId + ", question=" + question + ", option1="
				+ option1 + ", option2=" + option2 + "]";
	}


    
    
    
}
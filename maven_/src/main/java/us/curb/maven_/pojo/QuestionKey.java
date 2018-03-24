package us.curb.maven_.pojo;

public class QuestionKey {
    private Byte questionNum;

    private Integer sdId;

    public Byte getQuestionNum() {
        return questionNum;
    }

    public void setQuestionNum(Byte questionNum) {
        this.questionNum = questionNum;
    }

    public Integer getSdId() {
        return sdId;
    }

    public void setSdId(Integer sdId) {
        this.sdId = sdId;
    }

	@Override
	public String toString() {
		return "QuestionKey [questionNum=" + questionNum + ", sdId=" + sdId + "]";
	}
    
}
package us.curb.maven_.pojo;

public class Homework {

	private int homeworkId;

    private String content;

    private String imgurl;

    private String title;
    
    private int courseId;
    
    public int getCourseId() {
		return courseId;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getHomeworkId() {
        return homeworkId;
    }

    public void setHomeworkId(int homeworkId) {
        this.homeworkId = homeworkId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl == null ? null : imgurl.trim();
    }

	@Override
	public String toString() {
		return "Homework [homeworkId=" + homeworkId + ", content=" + content + ", imgurl=" + imgurl + "]";
	}
}

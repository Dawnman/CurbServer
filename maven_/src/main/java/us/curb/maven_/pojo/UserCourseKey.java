package us.curb.maven_.pojo;

public class UserCourseKey {

	private int userId;

    private int courseId;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

	@Override
	public String toString() {
		return "UserCourseKey [userId=" + userId + ", courseId=" + courseId + "]";
	}
}

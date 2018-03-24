package us.curb.maven_.pojo;

public class Course {

	 private int courseId;

	    private String title;

	    private String teacher;

	    private String url;

	    private int userId;
	    
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

		public String getTeacher() {
			return teacher;
		}

		public void setTeacher(String teacher) {
			this.teacher = teacher;
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public int getUserId() {
			return userId;
		}

		public void setUserId(int userId) {
			this.userId = userId;
		}

		@Override
		public String toString() {
			return "Course [courseId=" + courseId + ", title=" + title + ", teacher=" + teacher + ", url=" + url
					+ ", userId=" + userId + "]";
		}
		
		
		

	    
	
}

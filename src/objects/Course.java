package objects;

public class Course {
    private int courseId;
    private String courseName;
    private int courseCredit;
    private int maxStudent;
    private int currentStudent;
    private String courseTeacherId;

    public Course(int courseId, String courseName, int courseCredit, int maxStudent, int currentStudent,
            String courseTeacherId) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.courseCredit = courseCredit;
        this.maxStudent = maxStudent;
        this.currentStudent = currentStudent;
        this.courseTeacherId = courseTeacherId;
    }

    public int getCourseId() {
        return courseId;
    }
    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }
    public String getCourseName() {
        return courseName;
    }
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
    public int getCourseCredit() {
        return courseCredit;
    }
    public void setCourseCredit(int courseCredit) {
        this.courseCredit = courseCredit;
    }
    public int getMaxStudent() {
        return maxStudent;
    }
    public void setMaxStudent(int maxStudent) {
        this.maxStudent = maxStudent;
    }
    public int getCurrentStudent() {
        return currentStudent;
    }
    public void setCurrentStudent(int currentStudent) {
        this.currentStudent = currentStudent;
    }
    public String getCourseTeacherId() {
        return courseTeacherId;
    }
    public void setCourseTeacherId(String courseTeacherId) {
        this.courseTeacherId = courseTeacherId;
    }

    @Override
    public String toString() {
        return "Course [courseId=" + courseId + ", courseName=" + courseName + ", courseCredit=" + courseCredit
                + ", maxStudent=" + maxStudent + ", currentStudent=" + currentStudent + ", courseTeacherId="
                + courseTeacherId + "]";
    }

    
    
}

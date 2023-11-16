package objects;

public class Registration {
    private int regId;
    private int courseId;
    private String studentId;

    public Registration(int regId, String studentId, int courseId) {
        this.regId = regId;
        this.courseId = courseId;
        this.studentId = studentId;
    }

    public int getRegId() {
        return regId;
    }

    public void setRegId(int regId) {
        this.regId = regId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    @Override
    public String toString() {
        return "Registaion [regId=" + regId + ", courseId=" + courseId + ", studentId=" + studentId + "]";
    }

    
    
}

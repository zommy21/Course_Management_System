package objects;

public class RegistrationTeacher {
    private int regId;
    private int courseId;
    private String teacherId;

    public RegistrationTeacher(int regId, String teacherId, int courseId) {
        this.regId = regId;
        this.courseId = courseId;
        this.teacherId = teacherId;
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

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    @Override
    public String toString() {
        return "Registaion [regId=" + regId + ", courseId=" + courseId + ", teacherId=" + teacherId + "]";
    }

    
    
}

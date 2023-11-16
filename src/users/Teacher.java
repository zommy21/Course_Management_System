package users;

public class Teacher {
    private String TeacherId;
    private String TeacherName;
    private String TeacherAddress;
    private String TeacherPhone;
    private String TeacherEmail;
    private String TeacherDepartment;

    public Teacher(String TeacherId, String TeacherName, String TeacherAddress, String TeacherPhone, String TeacherEmail, String TeacherDepartment){
        this.TeacherId = TeacherId;
        this.TeacherName = TeacherName;
        this.TeacherAddress = TeacherAddress;
        this.TeacherPhone = TeacherPhone;
        this.TeacherEmail = TeacherEmail;
        this.TeacherDepartment = TeacherDepartment;
    }

    public String getTeacherId() {
        return TeacherId;
    }

    public void setTeacherId(String teacherId) {
        TeacherId = teacherId;
    }

    public String getTeacherName() {
        return TeacherName;
    }

    public void setTeacherName(String teacherName) {
        TeacherName = teacherName;
    }

    public String getTeacherAddress() {
        return TeacherAddress;
    }

    public void setTeacherAddress(String teacherAddress) {
        TeacherAddress = teacherAddress;
    }

    public String getTeacherPhone() {
        return TeacherPhone;
    }

    public void setTeacherPhone(String teacherPhone) {
        TeacherPhone = teacherPhone;
    }

    public String getTeacherEmail() {
        return TeacherEmail;
    }

    public void setTeacherEmail(String teacherEmail) {
        TeacherEmail = teacherEmail;
    }

    public String getTeacherDepartment() {
        return TeacherDepartment;
    }

    public void setTeacherDepartment(String teacherDepartment) {
        TeacherDepartment = teacherDepartment;
    }

    @Override
    public String toString() {
        return "Teacher [TeacherId=" + TeacherId + ", TeacherName=" + TeacherName + ", TeacherAddress=" + TeacherAddress
                + ", TeacherPhone=" + TeacherPhone + ", TeacherEmail=" + TeacherEmail + ", TeacherDepartment="
                + TeacherDepartment + "]";
    }

}

package users;

public class Student {
    private String StudentId;
    private String StudentName;
    private String StudentAddress;
    private String StudentPhone;
    private String StudentEmail;
    private String StudentClass;

    public Student(String studentId, String studentName, String studentAddress, String studentPhone,
            String studentEmail, String studentClass) {
        StudentId = studentId;
        StudentName = studentName;
        StudentAddress = studentAddress;
        StudentPhone = studentPhone;
        StudentEmail = studentEmail;
        StudentClass = studentClass;
    }

    public String getStudentId() {
        return StudentId;
    }

    public void setStudentId(String studentId) {
        StudentId = studentId;
    }

    public String getStudentName() {
        return StudentName;
    }

    public void setStudentName(String studentName) {
        StudentName = studentName;
    }

    public String getStudentAddress() {
        return StudentAddress;
    }

    public void setStudentAddress(String studentAddress) {
        StudentAddress = studentAddress;
    }

    public String getStudentPhone() {
        return StudentPhone;
    }

    public void setStudentPhone(String studentPhone) {
        StudentPhone = studentPhone;
    }

    public String getStudentEmail() {
        return StudentEmail;
    }

    public void setStudentEmail(String studentEmail) {
        StudentEmail = studentEmail;
    }

    public String getStudentClass() {
        return StudentClass;
    }

    public void setStudentClass(String studentClass) {
        StudentClass = studentClass;
    }

    @Override
    public String toString() {
        return "Student [StudentId=" + StudentId + ", StudentName=" + StudentName + ", StudentAddress=" + StudentAddress
                + ", StudentPhone=" + StudentPhone + ", StudentEmail=" + StudentEmail + ", StudentClass=" + StudentClass
                + "]";
    }

    

}

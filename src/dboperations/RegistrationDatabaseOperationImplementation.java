package dboperations;

import dbconnection.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import objects.Course;
import objects.Registration;
import objects.dbinterfaces.RegistrationDatabaseOperation;
import users.Student;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class RegistrationDatabaseOperationImplementation implements RegistrationDatabaseOperation {
    @Override
    public boolean insertRegistrationEntry(Registration registration) {
        String insertQuery = String.format("INSERT INTO registration VALUES(%d, '%s', %d)",
                                            registration.getRegId(),
                                            registration.getStudentId(),
                                            registration.getCourseId());

        Connection connection = DBConnection.getConnection();
        try{
            Statement statement = connection.createStatement();
            statement.executeUpdate(insertQuery);
        } catch (SQLException sqle){
            sqle.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean exists(Course course, Student student) throws SQLException {
        String getQuery = String.format("SELECT course.id\n" +
                        "FROM course, registration\n" +
                        "WHERE registration.student_id='%s' AND course.id='%s' AND registration.course_id=course.id",
                        student.getStudentId(),
                        course.getCourseId());
        Connection connection = DBConnection.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(getQuery);
        return resultSet.next(); // if resultSet has element, the course is already registered for this particular student
    }

    // currently this method is implemented for manual auto-increment purpose
    @Override
    public int getLastPrimaryKey() throws SQLException {
        String getMaxPrimaryKey = String.format("SELECT MAX(id) FROM registration");
        Connection connection = DBConnection.getConnection();
        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery(getMaxPrimaryKey);
        int lastKey = 0;
        if (resultSet.next()){
            lastKey = resultSet.getInt(1);
        }
        return lastKey;
    }

    @Override
    public boolean removeRegistration(Course course, Student student) {
        String deleteQuery = String.format("DELETE FROM registration WHERE student_id='%s' AND course_id=%d",
                                            student.getStudentId(),
                                            course.getCourseId());
        Connection connection = DBConnection.getConnection();
        try{
            Statement statement = connection.createStatement();
            statement.executeUpdate(deleteQuery);
            return true;
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        return false;
    }

    @Override
    public ObservableList<Course> getAllRegisteredCourses(Student student) throws SQLException {
        String getQuery = String.format("SELECT course.id, course.name, course.name, course.credit,course.max_student,course.current_sutdent,course.teacher_id\n" +
                                        "FROM course, registration\n" +
                                        "WHERE registration.student_id='%s' AND registration.course_id=course.id",
                                        student.getStudentId());
        Connection connection = DBConnection.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(getQuery);

        ObservableList <Course> courseList = FXCollections.observableArrayList();
        while(resultSet.next()){
            int courseId = resultSet.getInt("id");
            String courseName = resultSet.getString("name");
            int courseCredit = resultSet.getInt("credit");
            int maxStudent = resultSet.getInt("max_student");
            int currentStudent = resultSet.getInt("current_sutdent");
            String courseTeacherId = resultSet.getString("teacher_id");
            Course course = new Course(courseId, courseName, courseCredit, maxStudent,currentStudent,courseTeacherId);
            courseList.add(course);
        }
        return courseList;
    }

    @Override
    public ObservableList <Student> getAllRegisteredStudents(Course course) throws SQLException {
        String query = String.format("SELECT student.id, student.name, student.address, student.phone, student.email, student.class " +
                                    "FROM registration, student " +
                                    "WHERE registration.course_id=%d AND student.id=registration.student_id",
                                    course.getCourseId());

        Connection connection = DBConnection.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        ObservableList <Student> studentList = FXCollections.observableArrayList();

        while(resultSet.next()){
            String StudentId = resultSet.getString("id");
            String StudentName = resultSet.getString("name");
            String StudentAddress = resultSet.getString("address");
            String StudentPhone = resultSet.getString("phone");
            String StudentEmail = resultSet.getString("email");
            String StudentClass = resultSet.getString("class");

            studentList.add(new Student(StudentId, StudentName, StudentAddress, StudentPhone, StudentEmail, StudentClass));
        }
        return studentList;
    }

    public ObservableList<Course> getAllRegisteredCourseswithStudenId(String studentid) throws SQLException {
        String getQuery = String.format("SELECT course.id, course.name, course.name, course.credit,course.max_student,course.current_sutdent,course.teacher_id\n" +
                                        "FROM course, registration\n" +
                                        "WHERE registration.student_id='%s' AND registration.course_id=course.id",
                                        studentid);
        Connection connection = DBConnection.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(getQuery);

        ObservableList <Course> courseList = FXCollections.observableArrayList();
        while(resultSet.next()){
            int courseId = resultSet.getInt("id");
            String courseName = resultSet.getString("name");
            int courseCredit = resultSet.getInt("credit");
            int maxStudent = resultSet.getInt("max_student");
            int currentStudent = resultSet.getInt("current_sutdent");
            String courseTeacherId = resultSet.getString("teacher_id");
            Course course = new Course(courseId, courseName, courseCredit, maxStudent,currentStudent,courseTeacherId);
            courseList.add(course);
        }
        return courseList;
    }

    @Override
    public void removeAll(Course pickedCourse) throws SQLException {

        Connection connection = DBConnection.getConnection();

        String query = String.format("DELETE FROM registration WHERE course_id=%d",pickedCourse.getCourseId());

        Statement statement = connection.createStatement();
        int rowsAffected = statement.executeUpdate(query);
        if (rowsAffected > 0){
            System.out.println("All registrations for this course has been removed");
        }
    }

    @Override
    public ObservableList<Student> getAllRegistereStudents(int courseid) throws SQLException {
        String query = String.format("SELECT student.id, student.name, student.address, student.phone, student.email, student.class " +
                                    "FROM registration, student " +
                                    "WHERE registration.course_id=%d AND student.id=registration.student_id",
                                    courseid);
        Connection connection = DBConnection.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        ObservableList <Student> studentList = FXCollections.observableArrayList();

        while(resultSet.next()){
            String StudentId = resultSet.getString("id");
            String StudentName = resultSet.getString("name");
            String StudentAddress = resultSet.getString("address");
            String StudentPhone = resultSet.getString("phone");
            String StudentEmail = resultSet.getString("email");
            String StudentClass = resultSet.getString("class");

            studentList.add(new Student(StudentId, StudentName, StudentAddress, StudentPhone, StudentEmail, StudentClass));
        }
        return studentList;
    }


}

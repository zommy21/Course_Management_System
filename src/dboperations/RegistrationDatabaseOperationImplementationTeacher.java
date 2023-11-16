package dboperations;

import dbconnection.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import objects.Course;
import objects.RegistrationTeacher;
import objects.dbinterfaces.RegistrationDatabaseOperationTeacher;
import users.Teacher;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class RegistrationDatabaseOperationImplementationTeacher implements RegistrationDatabaseOperationTeacher {

    @Override
    public boolean insertRegistrationEntry(RegistrationTeacher registrationteacher) {
        String insertQuery = String.format("INSERT INTO registrationteacher VALUES(%d, '%s', %d)",
                                            registrationteacher.getRegId(),
                                            registrationteacher.getTeacherId(),
                                            registrationteacher.getCourseId());

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
    public boolean exists(Course course, Teacher teacher) throws SQLException {
        String getQuery = String.format("SELECT course.id\n" +
                        "FROM course, registrationteacher\n" +
                        "WHERE registrationteacher.teacher_id='%s' AND course.id='%s' AND registrationteacher.course_id=course.id",
                        teacher.getTeacherId(),
                        course.getCourseId());
        Connection connection = DBConnection.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(getQuery);
        return resultSet.next(); // if resultSet has element, the course is already registered for this particular student
    }

    // currently this method is implemented for manual auto-increment purpose
    @Override
    public int getLastPrimaryKey() throws SQLException {
        String getMaxPrimaryKey = String.format("SELECT MAX(id) FROM registrationteacher");
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
    public boolean removeRegistration(Course course, Teacher teacher) {
        String deleteQuery = String.format("DELETE FROM registrationteacher WHERE teacher_id='%s' AND course_id=%d",
                                            teacher.getTeacherId(),
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
    public ObservableList<Course> getAllRegisteredCourses(Teacher teacher) throws SQLException {
        String getQuery = String.format("SELECT course.id, course.name, course.name, course.credit,course.max_student,course.current_sutdent,course.teacher_id\n" +
                                        "FROM course, registrationteacher\n" +
                                        "WHERE registrationteacher.teacher_id='%s' AND registrationteacher.course_id=course.id",
                                        teacher.getTeacherId());
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
    public ObservableList <Teacher> getAllRegisteredTeachers(Course course) throws SQLException {
        String query = String.format("SELECT teacher.id, teacher.name, teacher.address, teacher.phone, teacher.email, teacher.department " +
                                    "FROM registrationteacher, teacher " +
                                    "WHERE registrationteacher.course_id=%d AND teacher.id=registrationteacher.teacher_id",
                                    course.getCourseId());

        Connection connection = DBConnection.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        ObservableList <Teacher> teacherlist = FXCollections.observableArrayList();

        while(resultSet.next()){
            String TeacherId = resultSet.getString("id");
            String TeacherName = resultSet.getString("name");
            String TeacherAddress = resultSet.getString("address");
            String TeacherPhone = resultSet.getString("phone");
            String TeacherEmail = resultSet.getString("email");
            String TeacherDepartment = resultSet.getString("department");

            teacherlist.add(new Teacher(TeacherId, TeacherName, TeacherAddress, TeacherPhone, TeacherEmail, TeacherDepartment));
        }
        return teacherlist;
    }
}

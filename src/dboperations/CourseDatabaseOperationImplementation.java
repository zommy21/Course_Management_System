package dboperations;

import dbconnection.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import objects.Course;
import objects.dbinterfaces.CourseDatabaseOperation;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CourseDatabaseOperationImplementation implements CourseDatabaseOperation {
    @Override
    public ObservableList <Course> getAllCourses() throws SQLException {
        Connection connection = DBConnection.getConnection();
        String query = String.format("SELECT * FROM course");
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        ObservableList < Course > courseList = FXCollections.observableArrayList();
        while(resultSet.next()){
            int courseId = resultSet.getInt("id");
            String courseName = resultSet.getString("name");
            int courseCredit = resultSet.getInt("credit");
            int maxStudent = resultSet.getInt("max_student");
            int currentStudent = resultSet.getInt("current_sutdent");
            String courseTeacherId = resultSet.getString("teacher_id");
            Course course = new Course(courseId, courseName, courseCredit, maxStudent,currentStudent, courseTeacherId);
            courseList.add(course);
        }
        return courseList;
    }

    @Override
    public Course getCourse(int courseId) throws SQLException {
        Connection connection = DBConnection.getConnection();
        String query = String.format("SELECT * FROM course WHERE id=%d",
                                    courseId);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        if(resultSet.next()){
            String courseName = resultSet.getString("name");
            int courseCredit = resultSet.getInt("credit");
            int maxStudent = resultSet.getInt("max_student");
            int currentStudent = resultSet.getInt("current_sutdent");
            String courseTeacherId = resultSet.getString("teacher_id");
            return new Course(courseId, courseName, courseCredit, maxStudent,currentStudent, courseTeacherId);
        }
        return null;
    }

    

    @Override
    public boolean addCourse(Course course) throws SQLException {
        Connection connection = DBConnection.getConnection();
        String query = String.format("INSERT INTO course VALUES (%d, '%s', %d, %d, %d, '%s')",
                                     course.getCourseId(), course.getCourseName(), course.getCourseCredit(), course.getMaxStudent(), course.getCurrentStudent(), course.getCourseTeacherId());
        
        Statement statement = connection.createStatement();
        int rowsAffected = statement.executeUpdate(query);
        if(rowsAffected > 0)
            return true;
        return false;
    }    

    @Override
    public boolean removeCourse(Course pickedCourse) throws SQLException {

        Connection connection = DBConnection.getConnection();

        String query = String.format("DELETE FROM course WHERE id=%d",pickedCourse.getCourseId());

        Statement statement = connection.createStatement();
        int rowsAffected = statement.executeUpdate(query);
        if(rowsAffected > 0)
            return true;
        return false;
    }

    @Override
    public ObservableList<String> getAllCourseId() throws SQLException {
        Connection connection = DBConnection.getConnection();
        String query = "SELECT * FROM course";
        ObservableList<String> courseIdlList = FXCollections.observableArrayList();
        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery(query);

        while(resultSet.next()){
            String courseId = resultSet.getString("id");
            courseIdlList.add(courseId);
        }

        return courseIdlList;
    }

    @Override
    public boolean updateCourseCredit(Integer courseId, Integer credit) throws SQLException {
        Connection connection = DBConnection.getConnection();

        String query = String.format("UPDATE course SET credit=%d WHERE id=%s", credit, courseId);

        Statement statement = connection.createStatement();
        int rowsAffected = statement.executeUpdate(query);
        if(rowsAffected > 0)
            return true;
        return false;

    }

    @Override
    public boolean updateCourseMaxStudent(Integer courseId, Integer maxStudent) throws SQLException {
        Connection connection = DBConnection.getConnection();

        String query = String.format("UPDATE course SET max_student=%d WHERE id=%s", maxStudent, courseId);

        Statement statement = connection.createStatement();
        int rowsAffected = statement.executeUpdate(query);
        if(rowsAffected > 0)
            return true;
        return false;
    }

    @Override
    public boolean updateCourseTeacherId(Integer courseId, String teacherId) throws SQLException {
        Connection connection = DBConnection.getConnection();

        String query = String.format("UPDATE course SET teacher_id='%s' WHERE id=%s", teacherId, courseId);

        Statement statement = connection.createStatement();
        int rowsAffected = statement.executeUpdate(query);
        if(rowsAffected > 0)
            return true;
        return false;
    }

    @Override
    public ObservableList<Course> getFullCourses() throws SQLException {
        Connection connection = DBConnection.getConnection();

        String query = String.format("SELECT * FROM course WHERE current_sutdent = max_student");

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        ObservableList < Course > courseList = FXCollections.observableArrayList();
        while(resultSet.next()){
            int courseId = resultSet.getInt("id");
            String courseName = resultSet.getString("name");
            int courseCredit = resultSet.getInt("credit");
            int maxStudent = resultSet.getInt("max_student");
            int currentStudent = resultSet.getInt("current_sutdent");
            String courseTeacherId = resultSet.getString("teacher_id");
            Course course = new Course(courseId, courseName, courseCredit, maxStudent,currentStudent, courseTeacherId);
            courseList.add(course);
        }
        return courseList;
        
    }

}

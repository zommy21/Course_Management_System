package objects.dbinterfaces;

import javafx.collections.ObservableList;
import objects.Course;

import java.sql.SQLException;

public interface CourseDatabaseOperation {
    ObservableList <Course> getAllCourses() throws SQLException;
    Course getCourse(int courseId) throws SQLException;
    boolean addCourse(Course course) throws SQLException;
    boolean removeCourse(Course course) throws SQLException;
    ObservableList<String> getAllCourseId() throws SQLException;
    boolean updateCourseCredit(Integer courseId, Integer credit) throws SQLException;
    boolean updateCourseMaxStudent(Integer courseId, Integer maxStudent) throws SQLException;
    boolean updateCourseTeacherId(Integer courseId, String teacherId) throws SQLException;
    boolean updateCourseCurrentStudent(Integer courseId, Integer currentStudent) throws SQLException;
    ObservableList <Course> getFullCourses() throws SQLException;
}
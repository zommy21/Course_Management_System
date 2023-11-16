package objects.dbinterfaces;

import javafx.collections.ObservableList;
import objects.Course;
import objects.RegistrationTeacher;
import users.Teacher;

import java.sql.SQLException;

public interface RegistrationDatabaseOperationTeacher {
    boolean insertRegistrationEntry(RegistrationTeacher registrationTeacher);
    boolean exists(Course course, Teacher teacher) throws SQLException;
    int getLastPrimaryKey() throws SQLException;
    boolean removeRegistration(Course course, Teacher teacher);
    ObservableList <Course> getAllRegisteredCourses(Teacher teacher) throws SQLException;
    ObservableList <Teacher> getAllRegisteredTeachers(Course course) throws SQLException;
    ObservableList<String> getAllTeacherId(Integer courseId) throws SQLException ;
}
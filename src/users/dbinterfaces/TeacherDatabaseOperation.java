package users.dbinterfaces;

import users.Teacher;

import java.sql.SQLException;

import javafx.collections.ObservableList;

public interface TeacherDatabaseOperation {
    //Mangem
    boolean updateTeacherAddress(String teacherId, String address);
    boolean updateTeacherPhone(String teacherId, String phone);
    boolean updateTeacherEmail(String teacherId, String email);
    boolean updateTeacherDepartment(String teacherId, String classes);
    ObservableList<String> getAllTeacherId() throws SQLException;
    Teacher getTeacher(String teacherId) throws SQLException;
}
package users.dbinterfaces;

import users.Student;

import java.sql.SQLException;

import javafx.collections.ObservableList;

/*
* This interface holds all the methods necessary for Student dashboard
* */
public interface StudentDatabaseOperation {
    // All methods from "Student Information" page
    boolean updateStudentAddress(String studentId, String email);
    boolean updateStudentPhone(String studentId, String phone);
    boolean updateStudentEmail(String studentId, String email);
    boolean updateStudentClass(String studentId, String classes);
    Student getStudent(String studentId) throws SQLException;
    ObservableList<String> getAllStudentId() throws SQLException;
    
}
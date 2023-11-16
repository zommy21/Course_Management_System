package dboperations;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import dbconnection.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import users.Teacher;
import users.dbinterfaces.TeacherDatabaseOperation;

public class TeacherDatabaseOperationImplementation implements TeacherDatabaseOperation {

    @Override
    public boolean updateTeacherAddress(String teacherId, String address) {
        String queuryforAdressUpdate = String.format("UPDATE teacher SET address='%s' WHERE id='%s'", address,
                teacherId);
        Connection connection = DBConnection.getConnection();
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(queuryforAdressUpdate);
            return true;

        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateTeacherPhone(String teacherId, String phone) {
        String queryForPhoneUpdate = String.format("UPDATE teacher SET phone='%s' WHERE id='%s'", teacherId);
        Connection connection = DBConnection.getConnection();
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(queryForPhoneUpdate);
            return true;

        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateTeacherEmail(String teacherId, String email) {
        String queryForEmailUpdate = String.format("UPDATE teacher SET email='%s' WHERE id='%s'", email, teacherId);
        Connection connection = DBConnection.getConnection();
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(queryForEmailUpdate);
            return true;

        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateTeacherDepartment(String teacherId, String classes) {
        String queryForDepartmentUpdate = String.format("UPDATE teacher SET department='%s' WHERE id='%s'", classes,
                teacherId);
        Connection connection = DBConnection.getConnection();
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(queryForDepartmentUpdate);
            return true;

        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        return false;
    }

    @Override
    public Teacher getTeacher(String TeacherId) throws SQLException {
        Connection connection = DBConnection.getConnection();
        String query = String.format("SELECT * FROM teacher WHERE id='%s'", TeacherId);
        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery(query);

        if (!resultSet.next())
            return null;

        String TeacherName = resultSet.getString("name");
        String TeacherAddress = resultSet.getString("address");
        String TeacherPhone = resultSet.getString("phone");
        String TeacherEmail = resultSet.getString("email");
        String TeacherDepartment = resultSet.getString("department");

        return new Teacher(TeacherId, TeacherName, TeacherAddress, TeacherPhone, TeacherEmail, TeacherDepartment);
    }

    @Override
    public ObservableList<String> getAllTeacherId() throws SQLException {
        Connection connection = DBConnection.getConnection();
        String query = "SELECT * FROM teacher";
        ObservableList<String> teacherIdlList = FXCollections.observableArrayList();
        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery(query);

        while(resultSet.next()){
            String teacherId = resultSet.getString("id");
            teacherIdlList.add(teacherId);
        }

        return teacherIdlList;
    }
}

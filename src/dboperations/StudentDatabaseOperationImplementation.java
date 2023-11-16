package dboperations;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import dbconnection.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import users.Student;
import users.dbinterfaces.StudentDatabaseOperation;

public class StudentDatabaseOperationImplementation implements StudentDatabaseOperation {

    @Override
    public boolean updateStudentAddress(String studentId, String address) {
        String queuryforAdressUpdate =  String.format("UPDATE student SET address='%s' WHERE id='%s'",address,studentId);

        Connection connection = DBConnection.getConnection();
        try{
            Statement statement = connection.createStatement();
            statement.executeUpdate(queuryforAdressUpdate);
            return true;

        } catch (SQLException sqle){
            sqle.printStackTrace();
        }
        return false;
    }


    @Override
    public boolean updateStudentPhone(String studentId, String phone) {
        String queryForPhoneUpdate = String.format("UPDATE student SET phone='%s' WHERE id='%s'",phone,studentId);
        Connection connection = DBConnection.getConnection();
        try{
            Statement statement = connection.createStatement();
            statement.executeUpdate(queryForPhoneUpdate);
            return true;

        } catch (SQLException sqle){
            sqle.printStackTrace();
        }
        return false;
    }



    @Override
    public boolean updateStudentEmail(String studentId, String email) {
        String queryForEmailUpdate = String.format("UPDATE student SET email='%s' WHERE id='%s'",email,studentId);
        Connection connection = DBConnection.getConnection();
        try{
            Statement statement = connection.createStatement();
            statement.executeUpdate(queryForEmailUpdate);
            return true;

        } catch (SQLException sqle){
            sqle.printStackTrace();
        }
        return false;
    }



    @Override
    public boolean updateStudentClass(String studentId,String classes) {
        String queryForClassUpdate = String.format("UPDATE student SET class='%s' WHERE id='%s'",classes,studentId);
        Connection connection = DBConnection.getConnection();
        try{
            Statement statement = connection.createStatement();
            statement.executeUpdate(queryForClassUpdate);
            return true;

        } catch (SQLException sqle){
            sqle.printStackTrace();
        }
        return false;
    }


    @Override
    public Student getStudent(String StudentId) throws SQLException {
        Connection connection = DBConnection.getConnection();
        String query = String.format("SELECT * FROM student WHERE id='%s'", StudentId);
        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery(query);

        if (!resultSet.next())
            return null;

        String StudentName = resultSet.getString("name");
        String StudentAddress = resultSet.getString("address");
        String StudentPhone = resultSet.getString("phone");
        String StudentEmail = resultSet.getString("email");
        String StudentClass = resultSet.getString("class");

        return new Student(StudentId, StudentName, StudentAddress, StudentPhone, StudentEmail, StudentClass);
    }


    @Override
    public ObservableList<String> getAllStudentId() throws SQLException {
        Connection connection = DBConnection.getConnection();
        String query = "SELECT * FROM student";
        ObservableList<String> studentIdList = FXCollections.observableArrayList();
        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery(query);

        while(resultSet.next()){
            String studentId = resultSet.getString("id");
            studentIdList.add(studentId);
        }

        return studentIdList;
    }

}

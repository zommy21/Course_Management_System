package test;

import java.sql.SQLException;
import dboperations.CourseDatabaseOperationImplementation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import objects.dbinterfaces.CourseDatabaseOperation;

public class Test {



    public static void main(String[] args) throws SQLException{
        ObservableList<String> courseList = FXCollections.observableArrayList();
        CourseDatabaseOperation courseOp = new CourseDatabaseOperationImplementation();
        courseList = courseOp.getAllCourseId();

        for (String course : courseList) {
            System.out.println(course);
        }
    }

}

package controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import dboperations.CourseDatabaseOperationImplementation;
import dboperations.RegistrationDatabaseOperationImplementation;
import dboperations.StudentDatabaseOperationImplementation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import objects.Course;
import objects.dbinterfaces.CourseDatabaseOperation;
import users.Student;
import users.Teacher;
import users.dbinterfaces.StudentDatabaseOperation;
import javafx.scene.Node;
import objects.Registration;

public class StudentDashboardController implements Initializable {

    @FXML
    private Label studentNameLabel;

    @FXML
    private Label studentIdLabel;

    @FXML
    private AnchorPane studentDashboardHomePane;

    @FXML
    private AnchorPane studentInformationPane;

    @FXML
    private TextField studentInformationEmailField;

    @FXML
    private TextField studentInformationClassField;

    @FXML
    private TextField studentInformationPhoneField;

    @FXML
    private TextField studentInformationAddressField;

    /*
     * Following are the data of the student fetched from database
     */

    private Student student;
    private int registrationId;
    
    @FXML
    private Label studentInformationUpdationStatusLabel;

    @FXML
    private Label studentInformationNameLabel;

    @FXML
    private Label studentInformationIdLabel;

    @FXML
    private Label studentInformationProgramLabel;

    private String studentId;

    @FXML
    private AnchorPane studentRegistrationPane;

    @FXML
    private ObservableList <Teacher> teacherList;

    // Course picker table
    @FXML
    private ObservableList<Course> courseList;

    @FXML
    private TableView<Course> registrationTableView;

    @FXML
    private Label registrationStatusLabel;

    // Registered courses table
    @FXML
    private ObservableList<Course> registeredCourseList;

    @FXML
    private TableView<Course> registeredCoursesTableView;

    @FXML
    private Label removeCourseStatusLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        navigateToStudentDashboard(null); // setting "Dashboard" as default home page

        try {
            // clearing labels and fields

            // fetching necessary information from database
            fetchStudentInformationFromDatabase();
            fetchCourseInformationFromDatabase();
            fetchRegistrationInformationFromDatabase();

            // initializing all pages using fetched information

            populateRegistrationTableView();
            populateRegisteredCoursesTableView();
            // populateResearchTeamInformation();

        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }

    @FXML
    private void navigateToStudentDashboard(ActionEvent actionEvent) {
        studentInformationPane.setVisible(false);
        studentRegistrationPane.setVisible(false);
        studentDashboardHomePane.setVisible(true);
    }

    @FXML
    private void navigateToStudentInformation(ActionEvent actionEvent) {
        studentDashboardHomePane.setVisible(false);
        studentRegistrationPane.setVisible(false);;
        studentInformationPane.setVisible(true);
        studentInformationUpdationStatusLabel.setText(null);
    }

    @FXML
    private void navigateToRegistration(ActionEvent actionEvent) {
        studentDashboardHomePane.setVisible(false);
        studentInformationPane.setVisible(false);
        registrationStatusLabel.setText(null);
        removeCourseStatusLabel.setText(null);
        studentRegistrationPane.setVisible(true);
    }

    private void fetchStudentInformationFromDatabase() throws SQLException {

        // Getting username passed from LoginUI
        studentId = LoginUIController.getUsername();
        System.out.println("User is " + studentId);
        StudentDatabaseOperation studentOp = new StudentDatabaseOperationImplementation();
        student = studentOp.getStudent(studentId);

        studentInformationNameLabel.setText(student.getStudentName());
        studentInformationIdLabel.setText(student.getStudentId());

        studentInformationEmailField.setText(student.getStudentEmail());
        studentInformationPhoneField.setText(student.getStudentPhone());
        studentInformationClassField.setText(student.getStudentClass());
        studentInformationAddressField.setText(student.getStudentAddress());
    }

    @FXML
    private void handleDashboardSignout(ActionEvent actionEvent) throws IOException {
        /*
        * Signing out will take user to login page
        * */
        Parent LoginUIParent = FXMLLoader.load(getClass().getResource("/gui/LoginUI.fxml"));
        Scene LoginUIScene = new Scene(LoginUIParent);

        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        window.setScene(LoginUIScene);
        window.show();
    }

    /*******************************************
    *  Student Information Page related methods
    * ******************************************/
    @FXML
    private void handleStudentInformationUpdate(ActionEvent actionEvent) {
        String newEmail = studentInformationEmailField.getText();
        String newClass = studentInformationClassField.getText();
        String newPhone = studentInformationPhoneField.getText();
        String newAddress = studentInformationAddressField.getText();

        StudentDatabaseOperation studentOp = new StudentDatabaseOperationImplementation();

        // flag variables to check if update operations were successful
        boolean emailStatus ;
        boolean classstatus;
        boolean phonestatus;
        boolean addressStatus;

        emailStatus = studentOp.updateStudentEmail(studentId, newEmail);
        // If the database operation is performed, we update the student object
        if (emailStatus)
            this.student.setStudentEmail(newEmail);

        classstatus = studentOp.updateStudentClass(studentId, newClass);
        // If the database operation is performed, we update the student object
        if (classstatus)
            this.student.setStudentClass(newClass);

        phonestatus = studentOp.updateStudentPhone(studentId, newPhone);
        // If the database operation is performed, we update the student object
        if (phonestatus)
            this.student.setStudentPhone(newPhone);


        addressStatus = studentOp.updateStudentAddress(studentId, newAddress);
        // If the database operation is performed, we update the student object
        if (addressStatus)
            this.student.setStudentAddress(newAddress);


        // if all status flags are true then we conclude that the update operations are successful
        if (emailStatus && classstatus && phonestatus && addressStatus) {
            //System.out.println("Email updated successfully.");
            studentInformationUpdationStatusLabel.setText("Information updated successfully.");
        } else {
            //System.out.println("Updation error!");
            studentInformationUpdationStatusLabel.setText("Error updating information. Please recheck!");
        }
    }


    /*******************************************
     *  Registration Page related methods
     * ******************************************/

    private void fetchCourseInformationFromDatabase() throws SQLException{
        courseList = FXCollections.observableArrayList();
        CourseDatabaseOperation courseOp = new CourseDatabaseOperationImplementation();
        courseList = courseOp.getAllCourses();

        //        for (Object course : courseList)
        //            System.out.println(course.toString());
    }

    private void fetchRegistrationInformationFromDatabase() throws SQLException{
        RegistrationDatabaseOperationImplementation regOp = new RegistrationDatabaseOperationImplementation();
        registrationId = regOp.getLastPrimaryKey();
        System.out.println(registrationId);
        registrationId = registrationId + 1; // next primary key

        registeredCourseList = regOp.getAllRegisteredCourses(student);
    }

    private void populateRegistrationTableView(){
        TableColumn<Course, String> courseId = new TableColumn<>("ID");
        courseId.setCellValueFactory(new PropertyValueFactory("courseId"));
        // Course Title Column
        TableColumn<Course, String> courseName = new TableColumn<>("Course Name");
        courseName.setCellValueFactory(new PropertyValueFactory("courseName"));

        TableColumn<Course, Integer> courseCredit = new TableColumn<>("Course Credit");
        courseCredit.setCellValueFactory(new PropertyValueFactory("courseCredit"));

        TableColumn<Course, Integer> maxStudent = new TableColumn<>("Course Max Student");
        maxStudent.setCellValueFactory(new PropertyValueFactory("maxStudent"));

        TableColumn<Course, Integer> currentStudent = new TableColumn<>("Course Current Student");
        currentStudent.setCellValueFactory(new PropertyValueFactory("currentStudent"));

        TableColumn<Course, Integer> courseTeacherId = new TableColumn<>("Teacher Id");
        courseTeacherId.setCellValueFactory(new PropertyValueFactory("courseTeacherId"));

        registrationTableView.setItems(courseList);
        registrationTableView.getColumns().addAll(courseId, courseName, courseCredit, maxStudent,currentStudent,courseTeacherId);
    }

    private void populateRegisteredCoursesTableView(){
        TableColumn<Course, String> courseId = new TableColumn<>("ID");
        courseId.setCellValueFactory(new PropertyValueFactory("courseId"));
        // Course Title Column
        TableColumn<Course, String> courseName = new TableColumn<>("Course Name");
        courseName.setCellValueFactory(new PropertyValueFactory("courseName"));

        TableColumn<Course, Integer> courseCredit = new TableColumn<>("Course Credit");
        courseCredit.setCellValueFactory(new PropertyValueFactory("courseCredit"));

        TableColumn<Course, Integer> maxStudent = new TableColumn<>("Course Max Student");
        maxStudent.setCellValueFactory(new PropertyValueFactory("maxStudent"));

        TableColumn<Course, Integer> currentStudent = new TableColumn<>("Course Current Student");
        currentStudent.setCellValueFactory(new PropertyValueFactory("currentStudent"));

        TableColumn<Course, Integer> courseTeacherId = new TableColumn<>("Teacher Id");
        courseTeacherId.setCellValueFactory(new PropertyValueFactory("courseTeacherId"));

        registeredCoursesTableView.setItems(registeredCourseList);
        registeredCoursesTableView.getColumns().addAll(courseId, courseName, courseCredit, maxStudent,currentStudent,courseTeacherId);
    }

    @FXML
    private void handleCourseRegistration(ActionEvent actionEvent) throws SQLException {
        removeCourseStatusLabel.setText(null); // clearing other fields

        Course pickedCourse = registrationTableView.getSelectionModel().getSelectedItem();
        if (pickedCourse == null){
            registrationStatusLabel.setText("Select a course first!");
            return;
        }


        //System.out.println("Register for " + pickedCourse.toString());

        if(pickedCourse.getCurrentStudent() >= pickedCourse.getMaxStudent()){
            registrationStatusLabel.setText("Course is full!");
            return;
        }

        Registration registration = new Registration(registrationId, studentId, pickedCourse.getCourseId());
        //System.out.println(registration.toString());
        RegistrationDatabaseOperationImplementation regOp = new RegistrationDatabaseOperationImplementation();
        boolean exists = regOp.exists(pickedCourse, student);

        if (exists){
            registrationStatusLabel.setText("Already registered for " + pickedCourse.getCourseName());
            return;
        }

        boolean regStatus = regOp.insertRegistrationEntry(registration);
        if (regStatus){
            registrationId++; // increment registrationId to be primary key for the next entry
            CourseDatabaseOperationImplementation coOp = new CourseDatabaseOperationImplementation();
            Integer currentStudent = pickedCourse.getCurrentStudent();
            Integer pickedCourseId = pickedCourse.getCourseId();
            boolean updateStatus = coOp.updateCourseCurrentStudent(pickedCourseId, currentStudent+1);
            if (updateStatus){
                pickedCourse.setCurrentStudent(currentStudent+1);
                //System.out.println("Current student updated successfully.");
            } else{
                //System.out.println("Current student updation error!");
            }
            registrationTableView.getColumns().clear();
            populateRegistrationTableView();
            registeredCoursesTableView.getColumns().clear();
            populateRegisteredCoursesTableView();
            registeredCourseList.add(pickedCourse);
            //System.out.println(registration.toString() + " is done!");
            registrationStatusLabel.setText("Registration for " + pickedCourse.getCourseName() + " successful.");
        } else{
            registrationStatusLabel.setText("Failed to add " + pickedCourse.getCourseName() + "!");
        }

    }

    @FXML
    private void handleCourseRemoval(ActionEvent actionEvent) throws SQLException{
        registrationStatusLabel.setText(null); // clearing other fields

        Course pickedCourse = registeredCoursesTableView.getSelectionModel().getSelectedItem();
        if (pickedCourse == null){
            removeCourseStatusLabel.setText("Select a course first!");
            return;
        }

        RegistrationDatabaseOperationImplementation regOp = new RegistrationDatabaseOperationImplementation();
        boolean removeStatus = regOp.removeRegistration(pickedCourse, student);
        if (removeStatus){
            CourseDatabaseOperationImplementation coOp = new CourseDatabaseOperationImplementation();
            Integer currentStudent = pickedCourse.getCurrentStudent();
            Integer pickedCourseId = pickedCourse.getCourseId();
            boolean updateStatus = coOp.updateCourseCurrentStudent(pickedCourseId, currentStudent-1);
            if (updateStatus){
                pickedCourse.setCurrentStudent(currentStudent-1);
                //System.out.println("Current student updated successfully.");
            } else{
                //System.out.println("Current student updation error!");
            }
            registrationTableView.getColumns().clear();
            populateRegistrationTableView();
            registeredCoursesTableView.getColumns().clear();
            populateRegisteredCoursesTableView();
            registeredCourseList.remove(pickedCourse);
            //System.out.println("Registration cancelled for " + pickedCourse.toString());
            removeCourseStatusLabel.setText("Removed " + pickedCourse.getCourseName() + " successfully.");
        } else{
            removeCourseStatusLabel.setText("Failed to remove " + pickedCourse.getCourseName() + "!");
        }
    }

}

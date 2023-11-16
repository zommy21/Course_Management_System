package controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import dboperations.CourseDatabaseOperationImplementation;
import dboperations.RegistrationDatabaseOperationImplementation;
import dboperations.RegistrationDatabaseOperationImplementationTeacher;
import dboperations.StudentDatabaseOperationImplementation;
import dboperations.TeacherDatabaseOperationImplementation;
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
import users.dbinterfaces.TeacherDatabaseOperation;
import javafx.scene.Node;
import objects.Registration;
import objects.RegistrationTeacher;

public class TeacherDashboardController implements Initializable {

    @FXML
    private Label teacherNameLabel;

    @FXML
    private Label teacherIdLabel;

    @FXML
    private AnchorPane teacherDashboardHomePane;

    @FXML
    private AnchorPane teacherInformationPane;

    @FXML
    private TextField teacherInformationEmailField;

    @FXML
    private TextField teacherInformationDepartmentField;

    @FXML
    private TextField teacherInformationPhoneField;

    @FXML
    private TextField teacherInformationAddressField;

    /*
     * Following are the data of the teacher fetched from database
     */

    private Teacher teacher;
    private int registrationId;
    
    @FXML
    private Label teacherInformationUpdationStatusLabel;

    @FXML
    private Label teacherInformationNameLabel;

    @FXML
    private Label teacherInformationIdLabel;

    @FXML
    private Label teacherInformationProgramLabel;

    private String teacherId;

    @FXML
    private AnchorPane teacherRegistrationPane;

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
        navigateToteacherDashboard(null); // setting "Dashboard" as default home page

        try {
            // clearing labels and fields

            // fetching necessary information from database
            fetchTeacherInformationFromDatabase();
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
    private void navigateToteacherDashboard(ActionEvent actionEvent) {
        teacherInformationPane.setVisible(false);
        teacherRegistrationPane.setVisible(false);
        teacherDashboardHomePane.setVisible(true);
    }

    @FXML
    private void navigateToteacherInformation(ActionEvent actionEvent) {
        teacherDashboardHomePane.setVisible(false);
        teacherRegistrationPane.setVisible(false);;
        teacherInformationPane.setVisible(true);
        teacherInformationUpdationStatusLabel.setText(null);
    }

    @FXML
    private void navigateToRegistration(ActionEvent actionEvent) {
        teacherDashboardHomePane.setVisible(false);
        teacherInformationPane.setVisible(false);
        registrationStatusLabel.setText(null);
        removeCourseStatusLabel.setText(null);
        teacherRegistrationPane.setVisible(true);
    }

    private void fetchTeacherInformationFromDatabase() throws SQLException {

        // Getting username passed from LoginUI
        teacherId = LoginUIController.getUsername();
        System.out.println("User is " + teacherId);
        TeacherDatabaseOperation teacherOp = new TeacherDatabaseOperationImplementation();
        teacher = teacherOp.getTeacher(teacherId);
        System.out.println(teacher.toString());

        teacherInformationNameLabel.setText(teacher.getTeacherName());
        teacherInformationIdLabel.setText(teacher.getTeacherId());

        teacherInformationEmailField.setText(teacher.getTeacherEmail());
        teacherInformationPhoneField.setText(teacher.getTeacherPhone());
        teacherInformationDepartmentField.setText(teacher.getTeacherDepartment());
        teacherInformationAddressField.setText(teacher.getTeacherAddress());
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
    *  teacher Information Page related methods
    * ******************************************/
    @FXML
    private void handleTeacherInformationUpdate(ActionEvent actionEvent) {
        String newEmail = teacherInformationEmailField.getText();
        String newDeparment = teacherInformationDepartmentField.getText();
        String newPhone = teacherInformationPhoneField.getText();
        String newAddress = teacherInformationAddressField.getText();

        TeacherDatabaseOperation teacherOp = new TeacherDatabaseOperationImplementation();

        // flag variables to check if update operations were successful
        boolean emailStatus ;
        boolean dedpartmentstatus;
        boolean phonestatus;
        boolean addressStatus;

        emailStatus = teacherOp.updateTeacherEmail(teacherId, newEmail);
        // If the database operation is performed, we update the student object
        if (emailStatus)
            this.teacher.setTeacherEmail(newEmail);

        dedpartmentstatus = teacherOp.updateTeacherDepartment(teacherId, newDeparment);
        // If the database operation is performed, we update the student object
        if (dedpartmentstatus)
            this.teacher.setTeacherDepartment(newDeparment);

        phonestatus = teacherOp.updateTeacherPhone(teacherId, newPhone);
        // If the database operation is performed, we update the student object
        if (phonestatus)
            this.teacher.setTeacherPhone(newPhone);


        addressStatus = teacherOp.updateTeacherAddress(teacherId, newAddress);
        // If the database operation is performed, we update the student object
        if (addressStatus)
            this.teacher.setTeacherAddress(newAddress);


        // if all status flags are true then we conclude that the update operations are successful
        if (emailStatus && dedpartmentstatus && phonestatus && addressStatus) {
            //System.out.println("Email updated successfully.");
            teacherInformationUpdationStatusLabel.setText("Information updated successfully.");
        } else {
            //System.out.println("Updation error!");
            teacherInformationUpdationStatusLabel.setText("Error updating information. Please recheck!");
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
        RegistrationDatabaseOperationImplementationTeacher regOp = new RegistrationDatabaseOperationImplementationTeacher();
        registrationId = regOp.getLastPrimaryKey();
        System.out.println(registrationId);
        registrationId = registrationId + 1; // next primary key

        registeredCourseList = regOp.getAllRegisteredCourses(teacher);
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


        RegistrationTeacher registrationTeacher = new RegistrationTeacher(registrationId, teacherId, pickedCourse.getCourseId());
        //System.out.println(registration.toString());
        RegistrationDatabaseOperationImplementationTeacher regOp = new RegistrationDatabaseOperationImplementationTeacher();
        boolean exists = regOp.exists(pickedCourse, teacher);

        if (exists){
            registrationStatusLabel.setText("Already registered for " + pickedCourse.getCourseName());
            return;
        }

        boolean regStatus = regOp.insertRegistrationEntry(registrationTeacher);
        if (regStatus){
            registrationId++; // increment registrationId to be primary key for the next entry
            registeredCourseList.add(pickedCourse);
            //System.out.println(registration.toString() + " is done!");
            registrationStatusLabel.setText("Registration for " + pickedCourse.getCourseName() + " successful.");
        } else{
            registrationStatusLabel.setText("Failed to add " + pickedCourse.getCourseName() + "!");
        }

    }

    @FXML
    private void handleCourseRemoval(ActionEvent actionEvent) {
        registrationStatusLabel.setText(null); // clearing other fields

        Course pickedCourse = registeredCoursesTableView.getSelectionModel().getSelectedItem();
        if (pickedCourse == null){
            removeCourseStatusLabel.setText("Select a course first!");
            return;
        }

        RegistrationDatabaseOperationImplementationTeacher regOp = new RegistrationDatabaseOperationImplementationTeacher();
        boolean removeStatus = regOp.removeRegistration(pickedCourse, teacher);
        if (removeStatus){
            registeredCourseList.remove(pickedCourse);
            //System.out.println("Registration cancelled for " + pickedCourse.toString());
            removeCourseStatusLabel.setText("Removed " + pickedCourse.getCourseName() + " successfully.");
        } else{
            removeCourseStatusLabel.setText("Failed to remove " + pickedCourse.getCourseName() + "!");
        }
    }

}

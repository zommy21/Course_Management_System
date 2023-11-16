/*
     * @Author: NhatMinh-zommy21
     */

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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
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

public class AdminDashboardController implements Initializable {

    /*
     * Create a new course
     */

    private int courseId;

    @FXML
    private AnchorPane adminCourseManagementPage;

    @FXML
    private TextField createcourseIdField;

    @FXML
    private TextField createcourseNameField;

    @FXML
    private TextField createcourseCreditField;

    @FXML
    private TextField createcourseMaxStudentField;

    @FXML
    private TextField createcourseCurrentStudentField;

    @FXML
    private ComboBox<String> createcourseTeacherIdComboBox;

    /*
     * Update
     */
    private Course course;

    @FXML
    private ComboBox<String> updateCourseComboBox;

    @FXML
    private Label updateCourseIdLabel;

    @FXML
    private Label updateCourseNameLabel;

    @FXML
    private TextField updateCourseCreditField;

    @FXML
    private TextField updateCourseMaxStudentField;

    @FXML
    private Label updateCourseCurrentStudentLabel;

    @FXML
    private Label updateCourseTeacherIdLabel;

    @FXML
    private ComboBox<String> updateCourseTeacherIdComboBox;

    @FXML
    private Label myCreatLabel1;

    /*
     * Remove
     */

    @FXML
    private TableView<Course> createCourseTableView;

    @FXML
    private Label myRemoveLabel;

    @FXML
    private AnchorPane adminDashboardHomePane;

    @FXML
    private Label myCreatLabel;

    /*
     * Following are the data of the admin fetched from database
     */

    private Student student;
    private int registrationId;

    private String studentId;

    @FXML
    private AnchorPane adminRegistrationPane;

    @FXML
    private ObservableList<Teacher> teacherList;

    // Course picker table

    @FXML
    private ComboBox<String> pickupStudentComboBox;

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

    /*
     * Report
     */

    @FXML
    private AnchorPane adminReportPane;

    @FXML
    private TableView<Course> adminReportTableView;

    @FXML
    private TableView<Course> adminReportFullTableView;

    @FXML
    private ComboBox<String> reportViewRegistedStudentComboBox;

    @FXML
    private TableView<Student> adminReportStudentRegistedView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        navigateToAdminDashboard(null); // setting "Dashboard" as default home page

        try {

            // fetching necessary information from database
            fetchCourseInformationFromDatabase();
            fetchRegistrationInformationFromDatabase();
            fetchCreateCourseInformationFromDatabase();
            fetchReportInformationFromDatabase();

            // initializing all pages using fetched information

        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }

    @FXML
    private void handleCreateCourseResetButton(ActionEvent actionEvent) {
        createcourseCreditField.clear();
        createcourseMaxStudentField.clear();
        createcourseTeacherIdComboBox.getSelectionModel().selectLast();
    }

    private void fetchCreateCourseInformationFromDatabase() throws SQLException {
        createcourseTeacherIdComboBox.setItems(getTeacherList());
        myCreatLabel.setText(null);
        myCreatLabel1.setText(null);
        updateCourseComboBox.setItems(getCourseList());
        updateCourseComboBox.getSelectionModel().selectLast();
        updateCourseIdLabel.setText(null);
        updateCourseNameLabel.setText(null);
        updateCourseCurrentStudentLabel.setText(null);
        updateCourseTeacherIdLabel.setText(null);
        populateCourseTableView();
    }

    @FXML
    private void navigateToAdminDashboard(ActionEvent actionEvent) {
        adminRegistrationPane.setVisible(false);
        adminDashboardHomePane.setVisible(true);
        adminCourseManagementPage.setVisible(false);
        adminReportPane.setVisible(false);
    }

    @FXML
    private void navigateToRegistration(ActionEvent actionEvent) {
        adminDashboardHomePane.setVisible(false);
        registrationStatusLabel.setText(null);
        removeCourseStatusLabel.setText(null);
        adminRegistrationPane.setVisible(true);
        adminReportPane.setVisible(false);
        adminCourseManagementPage.setVisible(false);
    }

    @FXML
    private void navigateToMangementCourse(ActionEvent actionEvent) {
        adminDashboardHomePane.setVisible(false);
        adminRegistrationPane.setVisible(false);
        adminCourseManagementPage.setVisible(true);
        myRemoveLabel.setText(null);
        adminReportPane.setVisible(false);
    }

    @FXML
    private void navigateToReport(ActionEvent actionEvent) {
        adminDashboardHomePane.setVisible(false);
        adminRegistrationPane.setVisible(false);
        adminCourseManagementPage.setVisible(false);
        adminReportPane.setVisible(true);
    }

    @FXML
    private void handleDashboardSignout(ActionEvent actionEvent) throws IOException {
        /*
         * Signing out will take user to login page
         */
        Parent LoginUIParent = FXMLLoader.load(getClass().getResource("/gui/LoginUI.fxml"));
        Scene LoginUIScene = new Scene(LoginUIParent);

        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(LoginUIScene);
        window.show();
    }

    @FXML
    private void handleDashboardReload(ActionEvent actionEvent) throws SQLException, IOException {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();

        stage.setOnCloseRequest(event -> {
            event.consume();
            logout(stage);
        });

        Scene Scene = new Scene(FXMLLoader.load(getClass().getResource("/gui/AdminDashboard.fxml")));
        Stage newstage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        newstage.setScene(Scene);
        newstage.show();
        newstage.setFullScreen(false);
        newstage.setOnCloseRequest(event -> {
            event.consume();
            logout(newstage);
        });
    }

    /*******************************************
     * Admin Information Page related methods
     ******************************************/
    private void fetchCourseInformationFromDatabase() throws SQLException {
        courseList = FXCollections.observableArrayList();
        CourseDatabaseOperation courseOp = new CourseDatabaseOperationImplementation();
        courseList = courseOp.getAllCourses();

        // for (Object course : courseList)
        // System.out.println(course.toString());
    }

    /*
     * Create Course
     */

    @FXML
    private void handleCreateCourseButton(ActionEvent actionEvent) throws SQLException {
        /*
         * Getting all the values from textfields and combobox if "Signup" page
         */
        courseId = Integer.parseInt(createcourseIdField.getText());
        String courseName = createcourseNameField.getText();
        int courseCredit = Integer.parseInt(createcourseCreditField.getText());
        int maxStudent = Integer.parseInt(createcourseMaxStudentField.getText());
        int currentStudent = 0;
        createcourseTeacherIdComboBox.setItems(getTeacherList());
        String courseTeacherId = createcourseTeacherIdComboBox.getValue();

        Course course = new Course(courseId, courseName, courseCredit, maxStudent, currentStudent, courseTeacherId);
        CourseDatabaseOperation courseOp = new CourseDatabaseOperationImplementation();
        boolean courseStatus = courseOp.addCourse(course);
        if (courseStatus) {
            myCreatLabel.setText("Course is added!");
        } else {
            myCreatLabel.setText("Failed to add course!");
        }
    }

    /*
     * Update courses
     */

    @FXML
    private void handleUpdateCourseButton(ActionEvent actionEvent) throws SQLException {
        courseId = Integer.parseInt(updateCourseIdLabel.getText());
        course = new CourseDatabaseOperationImplementation().getCourse(courseId);
        Integer newCourseCredit = Integer.parseInt(updateCourseCreditField.getText());
        Integer newMaxStudent = Integer.parseInt(updateCourseMaxStudentField.getText());
        String newTeacherId = updateCourseTeacherIdLabel.getText();

        CourseDatabaseOperation courseOp = new CourseDatabaseOperationImplementation();

        boolean creditstatus;
        boolean maxstudentstatus;
        boolean teacheridstatus;

        creditstatus = courseOp.updateCourseCredit(courseId, newCourseCredit);
        if (creditstatus)
            this.course.setCourseCredit(newCourseCredit);

        maxstudentstatus = courseOp.updateCourseMaxStudent(courseId, newMaxStudent);
        if (maxstudentstatus)
            this.course.setMaxStudent(newMaxStudent);

        teacheridstatus = courseOp.updateCourseTeacherId(courseId, newTeacherId);
        if (teacheridstatus)
            this.course.setCourseTeacherId(newTeacherId);

        if (creditstatus && maxstudentstatus && teacheridstatus) {
            myCreatLabel1.setText("Course is updated!");
        } else {
            myCreatLabel1.setText("Failed to update course!");
        }

    }

    @FXML
    private void pickupupdateCourseComboBoxAction(ActionEvent actionEvent) throws SQLException {
        Integer courseid = Integer.parseInt(updateCourseComboBox.getValue());
        course = new CourseDatabaseOperationImplementation().getCourse(courseid);
        updateCourseIdLabel.setText(String.valueOf(courseid));
        updateCourseNameLabel.setText(course.getCourseName());
        updateCourseCreditField.setText(String.valueOf(course.getCourseCredit()));
        updateCourseMaxStudentField.setText(String.valueOf(course.getMaxStudent()));
        updateCourseCurrentStudentLabel.setText(String.valueOf(course.getCurrentStudent()));
        updateCourseTeacherIdLabel.setText(course.getCourseTeacherId());
        updateCourseTeacherIdComboBox.setItems(getRegistrationTeacherList(courseid));
    }

    @FXML
    private void pickupupdateCourseTeacherIdComboBox(ActionEvent event) throws SQLException {
        updateCourseTeacherIdLabel.setText(updateCourseTeacherIdComboBox.getValue());
    }

    @FXML
    private void handleUpdateCourseResetButton(ActionEvent actionEvent) throws SQLException {
        updateCourseCreditField.clear();
        updateCourseMaxStudentField.clear();
        updateCourseTeacherIdComboBox.getSelectionModel().clearSelection();
    }

    /*
     * Remove courses
     */
    private void populateCourseTableView() {
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

        createCourseTableView.setItems(courseList);
        createCourseTableView.getColumns().addAll(courseId, courseName, courseCredit, maxStudent, currentStudent,
                courseTeacherId);
    }

    @FXML
    private void handleCourseReload(ActionEvent actionEvent) throws SQLException {
        createCourseTableView.getColumns().clear();
        fetchCourseInformationFromDatabase();
        populateCourseTableView();
    }

    @FXML
    private void handleCourseRemove(ActionEvent actionEvent) throws SQLException {
        myRemoveLabel.setText(null); // clearing other fields

        Course pickedCourse = createCourseTableView.getSelectionModel().getSelectedItem();
        if (pickedCourse == null) {
            myRemoveLabel.setText("Select a course first!");
            return;
        }

        CourseDatabaseOperation regOp = new CourseDatabaseOperationImplementation();
        RegistrationDatabaseOperationImplementation op = new RegistrationDatabaseOperationImplementation();
        op.removeAll(pickedCourse);
        boolean removeStatus = regOp.removeCourse(pickedCourse);
        if (removeStatus) {
            courseList.remove(pickedCourse);
            // System.out.println("Registration cancelled for " + pickedCourse.toString());
            myRemoveLabel.setText("Removed " + pickedCourse.getCourseName() + " successfully.");
        } else {
            myRemoveLabel.setText("Failed to remove " + pickedCourse.getCourseName() + "!");
        }
    }

    /*
     * Register courses for student
     */

    private void fetchRegistrationInformationFromDatabase() throws SQLException {
        pickupStudentComboBox.setItems(getStudentList());
        pickupStudentComboBox.getSelectionModel().selectLast();
        String studentid = pickupStudentComboBox.getValue();

        RegistrationDatabaseOperationImplementation regOp = new RegistrationDatabaseOperationImplementation();
        registrationId = regOp.getLastPrimaryKey();
        System.out.println(registrationId);
        registrationId = registrationId + 1; // next primary key

        student = new StudentDatabaseOperationImplementation().getStudent(studentid);

        registeredCourseList = regOp.getAllRegisteredCourses(student);

        populateRegistrationTableView();
        // populateResearchTeamInformation();

    }

    private void populateRegistrationTableView() {
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
        registrationTableView.getColumns().addAll(courseId, courseName, courseCredit, maxStudent, currentStudent,
                courseTeacherId);
    }

    private void populateRegisteredCoursesTableView() {
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
        registeredCoursesTableView.getColumns().addAll(courseId, courseName, courseCredit, maxStudent, currentStudent,
                courseTeacherId);
    }

    @FXML
    private void handleCourseRegistration(ActionEvent actionEvent) throws SQLException {
        removeCourseStatusLabel.setText(null); // clearing other fields

        Course pickedCourse = registrationTableView.getSelectionModel().getSelectedItem();
        if (pickedCourse == null) {
            registrationStatusLabel.setText("Select a course first!");
            return;
        }

        // System.out.println("Register for " + pickedCourse.toString());

        if (pickedCourse.getCurrentStudent() >= pickedCourse.getMaxStudent()) {
            registrationStatusLabel.setText("Course is full!");
            return;
        }

        Registration registration = new Registration(registrationId, studentId, pickedCourse.getCourseId());
        // System.out.println(registration.toString());
        RegistrationDatabaseOperationImplementation regOp = new RegistrationDatabaseOperationImplementation();
        boolean exists = regOp.exists(pickedCourse, student);

        if (exists) {
            registrationStatusLabel.setText("Already registered for " + pickedCourse.getCourseName());
            return;
        }

        boolean regStatus = regOp.insertRegistrationEntry(registration);
        if (regStatus) {
            registrationId++; // increment registrationId to be primary key for the next entry
            CourseDatabaseOperationImplementation coOp = new CourseDatabaseOperationImplementation();
            Integer currentStudent = pickedCourse.getCurrentStudent();
            Integer pickedCourseId = pickedCourse.getCourseId();
            boolean updateStatus = coOp.updateCourseCurrentStudent(pickedCourseId, currentStudent + 1);
            if (updateStatus) {
                pickedCourse.setCurrentStudent(currentStudent + 1);
                // System.out.println("Current student updated successfully.");
            } else {
                // System.out.println("Current student updation error!");
            }
            registrationTableView.getColumns().clear();
            populateRegistrationTableView();
            registeredCoursesTableView.getColumns().clear();
            populateRegisteredCoursesTableView();
            registeredCourseList.add(pickedCourse);
            // System.out.println(registration.toString() + " is done!");
            registrationStatusLabel.setText("Registration for " + pickedCourse.getCourseName() + " successful.");
        } else {
            registrationStatusLabel.setText("Failed to add " + pickedCourse.getCourseName() + "!");
        }

    }

    @FXML
    private void handleCourseRemoval(ActionEvent actionEvent) throws SQLException {
        registrationStatusLabel.setText(null); // clearing other fields

        Course pickedCourse = registeredCoursesTableView.getSelectionModel().getSelectedItem();
        if (pickedCourse == null) {
            removeCourseStatusLabel.setText("Select a course first!");
            return;
        }

        RegistrationDatabaseOperationImplementation regOp = new RegistrationDatabaseOperationImplementation();
        boolean removeStatus = regOp.removeRegistration(pickedCourse, student);
        if (removeStatus) {
            CourseDatabaseOperationImplementation coOp = new CourseDatabaseOperationImplementation();
            Integer currentStudent = pickedCourse.getCurrentStudent();
            Integer pickedCourseId = pickedCourse.getCourseId();
            boolean updateStatus = coOp.updateCourseCurrentStudent(pickedCourseId, currentStudent - 1);
            if (updateStatus) {
                pickedCourse.setCurrentStudent(currentStudent - 1);
                // System.out.println("Current student updated successfully.");
            } else {
                // System.out.println("Current student updation error!");
            }
            registrationTableView.getColumns().clear();
            populateRegistrationTableView();
            registeredCoursesTableView.getColumns().clear();
            populateRegisteredCoursesTableView();
            registeredCourseList.remove(pickedCourse);
            // System.out.println("Registration cancelled for " + pickedCourse.toString());
            removeCourseStatusLabel.setText("Removed " + pickedCourse.getCourseName() + " successfully.");
        } else {
            removeCourseStatusLabel.setText("Failed to remove " + pickedCourse.getCourseName() + "!");
        }
    }

    @FXML
    private void pickupStudentComboBoxAction(ActionEvent actionEvent) throws SQLException {
        String studentid = pickupStudentComboBox.getValue();
        studentId = studentid;
        student = new StudentDatabaseOperationImplementation().getStudent(studentid);

        registeredCourseList = new RegistrationDatabaseOperationImplementation().getAllRegisteredCourses(student);
        populateRegisteredCoursesTableView();
    }

    /*
     * Report
     */

    private void fetchReportInformationFromDatabase() throws SQLException {
        populateAdminReportTableView();
        populateAdminReportFullTableView();
        reportViewRegistedStudentComboBox.setItems(getCourseList());
    }

    private void populateAdminReportTableView() {
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

        adminReportTableView.setItems(courseList);
        adminReportTableView.getColumns().addAll(courseId, courseName, courseCredit, maxStudent, currentStudent,
                courseTeacherId);
    }

    private void populateAdminReportFullTableView() throws SQLException {
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

        adminReportFullTableView.setItems(getFullCourses());
        adminReportFullTableView.getColumns().addAll(courseId, courseName, courseCredit, maxStudent, currentStudent,
                courseTeacherId);
    }

    @FXML
    private void pickupCourseComboBoxAction(ActionEvent actionEvent) throws SQLException {
        adminReportStudentRegistedView.getColumns().clear();
        populateadminReportStudentRegistedView();
    }

    private void populateadminReportStudentRegistedView() throws SQLException {
        TableColumn<Student, String> studentId = new TableColumn<>("ID");
        studentId.setCellValueFactory(new PropertyValueFactory("studentId"));
        // Course Title Column
        TableColumn<Student, String> studentName = new TableColumn<>("Student Name");
        studentName.setCellValueFactory(new PropertyValueFactory("studentName"));

        TableColumn<Student, String> studentEmail = new TableColumn<>("Student Email");
        studentEmail.setCellValueFactory(new PropertyValueFactory("studentEmail"));

        TableColumn<Student, String> studentPhone = new TableColumn<>("Student Phone");
        studentPhone.setCellValueFactory(new PropertyValueFactory("studentPhone"));

        TableColumn<Student, String> studentAddress = new TableColumn<>("Student Address");
        studentAddress.setCellValueFactory(new PropertyValueFactory("studentAddress"));

        TableColumn<Student, String> studentClass = new TableColumn<>("Student Class");
        studentClass.setCellValueFactory(new PropertyValueFactory("studentClass"));

        adminReportStudentRegistedView.setItems(getStudentRegisted());
        adminReportStudentRegistedView.getColumns().addAll(studentId, studentName, studentEmail, studentPhone,
                studentAddress, studentClass);
    }

    private ObservableList<String> getStudentList() throws SQLException {
        ObservableList<String> studentList = FXCollections.observableArrayList();
        StudentDatabaseOperation studentOp = new StudentDatabaseOperationImplementation();
        studentList = studentOp.getAllStudentId();
        return studentList;
    }

    private ObservableList<String> getTeacherList() throws SQLException {
        ObservableList<String> teacherList = FXCollections.observableArrayList();
        TeacherDatabaseOperation courseOp = new TeacherDatabaseOperationImplementation();
        teacherList = courseOp.getAllTeacherId();
        return teacherList;
    }

    private ObservableList<String> getRegistrationTeacherList(Integer courseId) throws SQLException {
        ObservableList<String> teacherList = FXCollections.observableArrayList();
        RegistrationDatabaseOperationImplementationTeacher courseOp = new RegistrationDatabaseOperationImplementationTeacher();
        teacherList = courseOp.getAllTeacherId(courseId);
        return teacherList;
    }

    private ObservableList<String> getCourseList() throws SQLException {
        ObservableList<String> courseList = FXCollections.observableArrayList();
        CourseDatabaseOperation courseOp = new CourseDatabaseOperationImplementation();
        courseList = courseOp.getAllCourseId();
        return courseList;
    }

    private ObservableList<Course> getFullCourses() throws SQLException {
        ObservableList<Course> courseList = FXCollections.observableArrayList();
        CourseDatabaseOperation courseOp = new CourseDatabaseOperationImplementation();
        courseList = courseOp.getFullCourses();
        return courseList;
    }

    private ObservableList<Student> getStudentRegisted() throws SQLException {
        int courseid = Integer.parseInt(reportViewRegistedStudentComboBox.getValue());
        ObservableList<Student> studentList = FXCollections.observableArrayList();
        RegistrationDatabaseOperationImplementation regOp = new RegistrationDatabaseOperationImplementation();
        studentList = regOp.getAllRegistereStudents(courseid);
        return studentList;
    }

    public void logout(Stage stage) {

        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setHeaderText("Are you sure you want to logout?");
        alert.setContentText("Do you want to save before exiting?");

        if (alert.showAndWait().get().getText().equals("OK")) {
            System.out.println("Logout button clicked");
            stage.close();
        }
    }
    
}

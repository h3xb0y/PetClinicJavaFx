package sample.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import sample.entity.Doctor;
import sample.entity.Patient;

import java.io.IOException;

public class MainController {

    @FXML
    private StackPane mainStackPane;

    @FXML
    public void initialize(){
        loadMenuScreen();
    }

    public void loadMenuScreen() {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../resources/MenuScreen.fxml"));
        Pane pane = null;
        try {
            pane = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        MenuController menuController = loader.getController();
        menuController.setMainController(this);
        setScreen(pane);
    }

    public void loadDoctorScreen(Doctor doctor) {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../resources/Doctor.fxml"));
        Pane pane = null;
        try {
            pane = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        DoctorController doctorController = loader.getController();
        doctorController.setDoctor(doctor);
        doctorController.Refresh();
        doctorController.setMainController(this);
        setScreen(pane);
    }

    public void loadPatientScreen(Patient patient) {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../resources/Patient.fxml"));
        Pane pane = null;
        try {
            pane = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        PatientController patientController = loader.getController();
        patientController.setPatient(patient);
        patientController.Refresh();
        patientController.setMainController(this);
        setScreen(pane);
    }
    public void setScreen(Pane pane) {
        mainStackPane.getChildren().clear();
        mainStackPane.getChildren().add(pane);
    }
}


package sample.controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import sample.entity.Doctor;
import sample.entity.Patient;
import sample.db.SqlControl;

import java.io.IOException;

public class LoginController {
    private MainController mainController;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
    @FXML JFXTextField loginField;
    @FXML
    JFXPasswordField passwordField;
    @FXML Text error;

    @FXML
    public void login(){
        String query = SqlControl.Login(loginField.getText(), passwordField.getText());
        if (Integer.valueOf(query)==0){
            error.setVisible(true);
        } else {
            if (Integer.valueOf(SqlControl.isAccountEnabled(loginField.getText())) != 0)
                LoadCabinet(SqlControl.loginRole(loginField.getText(), passwordField.getText()));
            else
                error.setVisible(true);
        }
    }



    @FXML
    public void backToMenu(){
        mainController.loadMenuScreen();
    }

    private void LoadCabinet(String role){

        FXMLLoader loader; Pane pane;
        if (role.equals("admin")){
            Doctor doctor = new SqlControl().getDoctorByLogin(loginField.getText());
            loader = new FXMLLoader(this.getClass().getResource("../resources/Doctor.fxml"));
            pane = null;
            try {
                pane = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            DoctorController doctorController = loader.getController();
            doctorController.setDoctor(doctor);
            doctorController.Refresh();
            doctorController.setMainController(mainController);
            mainController.setScreen(pane);
        } else
            if (role.equals("user"))
            {
                Patient patient = new SqlControl().getPatientByLogin(loginField.getText());
            loader = new FXMLLoader(this.getClass().getResource("../resources/Patient.fxml"));
            pane = null;
            try {
                pane = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            PatientController patientController = loader.getController();
            patientController.setMainController(mainController);
            patientController.setPatient(patient);
            patientController.Refresh();
            mainController.setScreen(pane);
        }
    }
}

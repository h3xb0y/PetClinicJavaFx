package sample.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class MenuController {

    private MainController mainController;
    public void setMainController(MainController mainController) {this.mainController = mainController;}

    @FXML
    public void exit(){
        Platform.exit();
    }

    @FXML
    public void login(){
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../resources/Login.fxml"));

        Pane pane = null;
        try {
            pane = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        LoginController loginController = loader.getController();
        loginController.setMainController(mainController);
        mainController.setScreen(pane);
    }

    @FXML
    public void registration(){
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../resources/Registration.fxml"));

        Pane pane = null;
        try {
            pane = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        RegistrationController registrationController = loader.getController();
        registrationController.setMainController(mainController);
        mainController.setScreen(pane);
    }
}

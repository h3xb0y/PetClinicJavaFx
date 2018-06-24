package sample.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import sample.db.SqlControl;

public class RegistrationController {
    private MainController mainController;
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
    @FXML TextField loginTextField;
    @FXML TextField passwordTextField;
    @FXML Text error;
    private String[] genders = new String[]{
            "Мужчина","Женщина"
    };
    @FXML private ComboBox<String> genderComboBox;
    @FXML private TextField nameTextField;
    @FXML private TextField numTextField;
    @FXML private TextField surnameTextField;
    @FXML
    public void registration(){
        if (Valide()){
            SqlControl.addUser(loginTextField.getText(), passwordTextField.getText(), "user");
            SqlControl.addOwner(nameTextField.getText(), surnameTextField.getText(), numTextField.getText()
                    , genderComboBox.getSelectionModel()
                                    .getSelectedItem(), loginTextField.getText());
            error.setText("Вы успешно зарегестрированы.");
            error.setFill(Color.GREEN);
            error.setVisible(true);
        } else
            error.setText("Заполните все поля!");
            error.setFill(Color.RED);
            error.setVisible(true);
    }

    @FXML public void initialize(){
        genderComboBox.setItems(FXCollections.observableArrayList(genders));
    }

    @FXML
    public void backToMenu(){
        mainController.loadMenuScreen();
    }

    private boolean Valide(){
        return !nameTextField.getText().isEmpty() && !numTextField.getText().isEmpty() && !surnameTextField.getText().isEmpty() &&
                !genderComboBox.getSelectionModel().isEmpty() && !loginTextField.getText().isEmpty() && !passwordTextField.getText().isEmpty();
    }
}

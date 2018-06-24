package sample.controller;

import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import sample.entity.Patient;
import sample.db.SqlControl;
import sample.entity.Animal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AddPetController {

    private MainController mainController;
    private static String ownerid;
    private Patient patient;
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
    public void setOwnerid(String ownerids){ownerid = ownerids;}
    public void setPatient(Patient patient) {this.patient = patient;}
    @FXML private JFXTextField animalName;
    @FXML private Text error;
    @FXML private Text message;
    @FXML private JFXSlider animalAge;
    @FXML private JFXSlider animalWeight;
    @FXML private ComboBox<String> animalComboBox;
    @FXML private ComboBox<Animal> animalDeleteComboBox;
    private static ArrayList<Animal> pets = new ArrayList<>();

    private String[] animalsTypes = new String[]{
            "Кот","Собака","Хомяк","Птица","Кролик","Хорек","Морская свинка","Черепаха"
    };
    @FXML
    public void initialize(){
        animalComboBox.setItems(FXCollections.observableArrayList(animalsTypes));
    }




    @FXML
    public void backToMenu(){
        mainController.loadPatientScreen(patient);
    }

    @FXML
    public void addPet(){
        String animalTypeText = animalComboBox.getSelectionModel()
                                              .getSelectedItem();
        String animalNameText = animalName.getText();
        String animalAgeText = String.valueOf(animalAge.getValue());
        String animalWeightText = String.valueOf(animalWeight.getValue());
        if (animalTypeText.isEmpty() || animalNameText.isEmpty() || animalAgeText.isEmpty() || animalWeightText.isEmpty()){
            error.setText("Заполните все поля!");
            error.setFill(Color.RED);
            error.setVisible(true);
        } else {
            error.setText("Питомец зарегистрирован!");
            error.setFill(Color.GREEN);
            error.setVisible(true);
            SqlControl.addAnimal(animalNameText, animalAgeText, animalWeightText, ownerid, String.valueOf(animalComboBox.getSelectionModel().getSelectedIndex()+1));

        }
    }

    @FXML
    public void deletePet(){
        int id = animalDeleteComboBox.getSelectionModel().getSelectedIndex();
        new SqlControl().deleteAnimalById(pets.get(id).getID());
        message.setFill(Color.RED);
        message.setText("Питомец удален!");
        message.setVisible(true);

    }

    public void Refresh(){
        pets.clear();
            HashMap<String, Animal> petsql= new SqlControl().getAnimalByOwnerId(ownerid);
            for (Map.Entry<String,Animal> entry: petsql.entrySet()) {
                    pets.add(new Animal(entry.getValue().getID(),entry.getValue().getName(),
                            entry.getValue().getAge(),entry.getValue().getWeight(),entry.getValue().getStatus(),entry.getValue().getKind_id(),entry.getValue().getOwner_id()));
            }

        animalDeleteComboBox.setItems(FXCollections.observableArrayList(pets));

    }

}

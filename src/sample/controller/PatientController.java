package sample.controller;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.util.Callback;
import sample.Data;
import sample.entity.Patient;
import sample.db.SqlControl;
import sample.entity.Pet;
import sample.entity.Ticket;
import sample.resources.PatientControllers.MakeAppController;

import java.io.IOException;

public class PatientController {

    private MainController mainController;
    private Patient patient;
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
    public void setPatient(Patient patient){this.patient = patient;}
    @FXML Text patientName;
    @FXML JFXTreeTableView<Pet> pets;
    @FXML JFXTreeTableView<Ticket> tickets;
    @FXML JFXTextField name;
    @FXML JFXTextField lastname;
    @FXML JFXTextField num;
    @FXML
    JFXComboBox<String> gender;
    private String[] genders = new String[]{
            "Мужчина","Женщина"
    };

    @FXML
    public void initialize(){
        if (patient != null) patientName.setText(patient.getName()+" "+patient.getLastName());

    }

    public void Refresh(){
        Data.updateData();
        RefreshPets();
        RefreshTickets();
        name.setText(patient.getName());
        lastname.setText(patient.getLastName());
        num.setText(patient.getNum());
        gender.setItems(FXCollections.observableArrayList(genders));
        setGender(patient.getGender(), gender.getSelectionModel());
    }

    static void setGender (String gender, SingleSelectionModel selectionModel) {
        switch (gender)
        {
            case "Мужчина":
                selectionModel.select(0);
                break;
            case "Женщина":
                selectionModel.select(1);
                break;
        }
    }

    private void RefreshPets(){
        patientName.setText(patient.getName()+" "+patient.getLastName());
        JFXTreeTableColumn name = new JFXTreeTableColumn("Кличка");
        name.setPrefWidth(150);

        name.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Pet, String>, ObservableValue<String>>() {

            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Pet, String> param) {
                return param.getValue().getValue().name;
            }
        });

        JFXTreeTableColumn age = new JFXTreeTableColumn("Возраст");
        age.setPrefWidth(140);

        age.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Pet, String>, ObservableValue<String>>() {

            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Pet, String> param) {
                return param.getValue().getValue().age;
            }
        });
        ObservableList<Pet> petlist = new SqlControl().getPetsForTableView(patient.getID());
        final TreeItem<Pet> root = new RecursiveTreeItem<Pet>(petlist, RecursiveTreeObject::getChildren);
        pets.getColumns().setAll(name,age);
        pets.setRoot(root);
        pets.setShowRoot(false);
    }
    private void RefreshTickets(){
        JFXTreeTableColumn time = new JFXTreeTableColumn("Время");
        time.setPrefWidth(130);

        time.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Ticket, String>, ObservableValue<String>>() {

            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Ticket, String> param) {
                return param.getValue().getValue().time;
            }
        });

        JFXTreeTableColumn doctor = new JFXTreeTableColumn("Доктор");
        doctor.setPrefWidth(140);

        doctor.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Ticket, String>, ObservableValue<String>>() {

            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Ticket, String> param) {
                return param.getValue().getValue().doctor;
            }
        });
        ObservableList<Ticket> ticketlist = new SqlControl().getTicketsForTableView(patient.getID());
        final TreeItem<Ticket> root = new RecursiveTreeItem<Ticket>(ticketlist, RecursiveTreeObject::getChildren);
        tickets.getColumns().setAll(time,doctor);
        tickets.setRoot(root);
        tickets.setShowRoot(false);
    }

    @FXML
    public void makeApp(){
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../resources/MakeApp.fxml"));
        Pane pane = null;
        try {
            pane = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        MakeAppController makeAppController = loader.getController();
        makeAppController.setMainController(mainController);
        makeAppController.setPatient(patient);
        makeAppController.Refresh();
        mainController.setScreen(pane);
    }

    @FXML
    public void backToMenu(){
        mainController.loadMenuScreen();
    }



    @FXML
    public void addPet(){
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../resources/AddPet.fxml"));
        Pane pane = null;
        try {
            pane = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        AddPetController addPetController = loader.getController();
        addPetController.setMainController(mainController);
        addPetController.setOwnerid(patient.getID());
        addPetController.setPatient(patient);
        addPetController.Refresh();
        mainController.setScreen(pane);
    }

    @FXML
    public void deleteProfile()
    {
        SqlControl.deleteOwner(patient);
        backToMenu();
    }
    @FXML
    public void saveChanges()
    {
        patient.setName(name.getText());
        patient.setLastName(lastname.getText());
        patient.setNum(num.getText());
        patient.setGender(gender.getSelectionModel()
                                .getSelectedItem());
        SqlControl.updateOwner(patient);
    }
}


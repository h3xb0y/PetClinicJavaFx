package sample.resources.DoctorControllers;

import com.jfoenix.controls.*;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.fxml.FXML;
import sample.controller.MainController;
import sample.db.SqlControl;
import sample.entity.Animal;
import sample.entity.Doctor;
import sample.entity.Patient;
import sample.entity.TicketEntity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EditAppController {

    private MainController mainController;
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    private Doctor doctor;
    @FXML private ComboBox<TicketEntity> ticketComboBox;
    @FXML private StackPane pane;
    @FXML private JFXDatePicker datePicker;
    @FXML private JFXTimePicker timePicker;
    @FXML private JFXButton patientInfo;
    @FXML private JFXButton animalInfo;
    @FXML private JFXButton deleteTicket;
    @FXML private JFXButton updateTicket;
    @FXML private Text patientName;
    @FXML private Text animalName;
    private static ArrayList<TicketEntity> tickets = new ArrayList<>();

    private String owner_id;
    private String animal_id;

    public void setDoctor(Doctor doctor){this.doctor = doctor;}
    @FXML public void initialize(){
        disableAll();
    }

    @FXML public void updateTicket(){
        SqlControl.updateTicket(timePicker.getValue().toString(), datePicker.getValue().toString(), tickets.get(ticketComboBox.getSelectionModel().getSelectedIndex()).getTicket_id());
    }
    @FXML public void deleteTicket(){
        SqlControl.deleteTicket(tickets.get(ticketComboBox.getSelectionModel().getSelectedIndex()).getTicket_id());
        Refresh();
    }
    public void Refresh(){
        tickets.clear();
        HashMap<String, TicketEntity> ticketsql = new SqlControl().getTicketsByDoctorid(doctor.getID());
        for (Map.Entry<String,TicketEntity> entry: ticketsql.entrySet()) {
            tickets.add(new TicketEntity(entry.getValue().getTicket_id(),entry.getValue().getTicket_date(),
                    entry.getValue().getTicket_time(),entry.getValue().getTicket_status(),entry.getValue().getOwner_id(),entry.getValue().getAnimal_id(),entry.getValue().getDoctor_id()));
        }

        ticketComboBox.setItems(FXCollections.observableArrayList(tickets));
    }

    @FXML public void backToMenu(){
        mainController.loadDoctorScreen(doctor);
    }

    @FXML public void someTicketSelected(){
        disableAll();
    }

    private void disableAll()
    {
        if (!ticketComboBox.getSelectionModel().isEmpty()) {
            TicketEntity value = tickets.get(ticketComboBox.getSelectionModel().getSelectedIndex());
            owner_id = value.getOwner_id();
            animal_id = value.getAnimal_id();
            //пациент и питомец
            patientInfo.setVisible(true);
            animalInfo.setVisible(true);
            patientName.setVisible(true);
            patientName.setText(SqlControl.getOwnerNameById(owner_id));
            animalName.setVisible(true);
            animalName.setText(SqlControl.getKindByAnimalId(animal_id));
            //кнопки управления
            deleteTicket.setDisable(false);
            updateTicket.setDisable(false);
            //слайдеры
            datePicker.setDisable(false);
            datePicker.setValue(LocalDate.parse(value.getTicket_date()));
            timePicker.setDisable(false);
            timePicker.setValue(LocalTime.parse(value.getTicket_time()));
        } else {
            //пациент и питомец
            patientInfo.setVisible(false);
            animalInfo.setVisible(false);
            patientName.setVisible(false);
            animalName.setVisible(false);
            //кнопки управления
            deleteTicket.setDisable(true);
            updateTicket.setDisable(true);
            //слайдеры
            datePicker.setDisable(true);
            timePicker.setDisable(true);
        }
    }

    @FXML
    public void patientInfoDialog()
    {
        JFXDialogLayout content = new JFXDialogLayout();
        content.setHeading(new Text("Информация о пациенте"));
        content.setBody(new Text(
                SqlControl.getPatientByPatientId(owner_id).toString()
        ));
        JFXDialog dialog = new JFXDialog(pane, content, JFXDialog.DialogTransition.CENTER);
        JFXButton button=new JFXButton("Закрыть");
        button.setOnAction(event -> dialog.close());
        content.setActions(button);
        dialog.show();

    }

    @FXML
    public void animalInfoDialog()
    {
        JFXDialogLayout content = new JFXDialogLayout();
        content.setHeading(new Text("Информация о питомце"));
        content.setBody(new Text(
                SqlControl.getAnimalByAnimal_id(animal_id).toString()
        ));
        JFXDialog dialog = new JFXDialog(pane, content, JFXDialog.DialogTransition.CENTER);
        JFXButton button=new JFXButton("Закрыть");
        button.setOnAction(event -> dialog.close());
        content.setActions(button);
        dialog.show();

    }
}

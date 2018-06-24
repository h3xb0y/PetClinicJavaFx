package sample.resources.PatientControllers;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTimePicker;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import sample.entity.Doctor;
import sample.DoctorSpecialization;
import sample.entity.Patient;
import sample.controller.MainController;
import sample.db.SqlControl;
import sample.entity.Animal;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class MakeAppController {
    private MainController mainController;
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    private Patient owner;
    @FXML
    ComboBox<Doctor> doctorComboBox;
    @FXML JFXDatePicker datePicker;
    @FXML private ComboBox<Animal> animalComboBox;
    @FXML private JFXTimePicker timePicker;

    private static ArrayList<Animal> pets = new ArrayList<>();

    public void setPatient(Patient patient){owner = patient;}

    private static ArrayList<Doctor> doctors = new ArrayList<>();
    static {
        HashMap<String, Doctor> doctorssql= new SqlControl().getDoctor();
        for (Map.Entry<String,Doctor> entry: doctorssql.entrySet()) {
            doctors.add(new Doctor(DoctorSpecialization.Ветеринар,entry.getValue().getID(),entry.getValue().getName(),
                    entry.getValue().getLastName(),entry.getValue().getNum(),entry.getValue().getGender(),entry.getValue().getCabinet_id()));
        }
    }


    @FXML private TextArea resultTextArea;

    @FXML public void initialize(){
        doctorComboBox.setItems(FXCollections.observableArrayList(doctors));
        animalComboBox.setItems(FXCollections.observableArrayList(pets));

        timePicker.setIs24HourView(true);

    }

    public void confirm() {
        LocalDate date = datePicker.getValue();
        int id = animalComboBox.getSelectionModel().getSelectedIndex();
        String time = timePicker.getValue().getHour()+":"+timePicker.getValue().getMinute()+":"+timePicker.getValue().getSecond();
        if (doctorComboBox.getSelectionModel().getSelectedIndex() == 0)
            SqlControl.addTicket(date.toString(), time, pets.get(id).getID(), owner.getID(), "1");
        else
            SqlControl.addTicket(date.toString(), time, pets.get(id).getID(), owner.getID(), "2");
        resultTextArea.setText(owner.getName() + ", Вы записаны на прием " + date.toString() + " числа на "+time+"\n" + "Информация о враче:"+'\n' + doctorComboBox.getSelectionModel().getSelectedItem().toString());
    }

    @FXML
    public void backToMenu(){
        mainController.loadPatientScreen(owner);
    }

    public void Refresh(){
        pets.clear();
        HashMap<String, Animal> petsql= new SqlControl().getAnimalByOwnerId(owner.getID());
        for (Map.Entry<String,Animal> entry: petsql.entrySet()) {
            pets.add(new Animal(entry.getValue().getID(),entry.getValue().getName(),
                    entry.getValue().getAge(),entry.getValue().getWeight(),entry.getValue().getStatus(),entry.getValue().getKind_id(),entry.getValue().getOwner_id()));
        }

        animalComboBox.setItems(FXCollections.observableArrayList(pets));

    }
}

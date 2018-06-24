package sample.controller;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.chart.PieChart;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.layout.Pane;
import java.io.IOException;
import java.util.Map;

import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.util.Callback;
import sample.Data;
import sample.entity.Doctor;
import sample.db.SqlControl;
import sample.entity.Animal;
import sample.entity.Ticket;
import sample.resources.DoctorControllers.*;


public class DoctorController {

    private Doctor doctor;

    private MainController mainController;
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
    @FXML Text name;
    @FXML StackPane pane;
    @FXML JFXTreeTableView<Ticket> tickets;
    @FXML private PieChart chart;
    private int[] petsArray;
    private ObservableList<PieChart.Data> pieChartData;
    @FXML JFXTextField doctorName;
    @FXML JFXTextField doctorLastName;
    @FXML JFXTextField doctorNum;
    @FXML
    JFXComboBox<String> gender;
    private String[] genders = new String[]{
            "Мужчина","Женщина"
    };
    @FXML
    public void searchPatient(){
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../resources/SearchPatient.fxml"));
        Pane pane = null;
        try {
            pane = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        EditAppController editAppController = loader.getController();

        editAppController.setMainController(mainController);
        editAppController.setDoctor(doctor);
        editAppController.Refresh();
        mainController.setScreen(pane);
    }

    public void Refresh(){
        // Статистика клиники
        Data.updateData();
        Map<String,Animal> animslSql= Data.animals;
        petsArray = new int[] {0, 0, 0, 0, 0, 0, 0, 0};
        pieChartData = FXCollections.observableArrayList();
        for (Map.Entry<String,Animal> entry: animslSql.entrySet()) {
            pets(Integer.valueOf(entry.getValue().getKind_id()));
        }
        name.setText(doctor.getName()+' '+doctor.getLastName());
        RefreshTickets();
        for (int i = 0;i<petsArray.length;i++)
        {
            if (petsArray[i] != 0) createData(i);
        }
        chart.setData(pieChartData);
        chart.setTitle("Статистика животных");

        doctorName.setText(doctor.getName());
        doctorLastName.setText(doctor.getLastName());
        doctorNum.setText(doctor.getNum());
        gender.setItems(FXCollections.observableArrayList(genders));
        PatientController.setGender(doctor.getGender(), gender.getSelectionModel());
    }
   private void RefreshTickets(){
       JFXTreeTableColumn<Ticket,String> time = new JFXTreeTableColumn<>("Время");
       time.setPrefWidth(150);

       time.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Ticket, String>, ObservableValue<String>>() {

           @Override
           public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Ticket, String> param) {
               return param.getValue().getValue().time;
           }
       });

       JFXTreeTableColumn<Ticket,String> patient = new JFXTreeTableColumn<>("Животное");
       patient.setPrefWidth(340);

       patient.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Ticket, String>, ObservableValue<String>>() {

           @Override
           public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Ticket, String> param) {
               return param.getValue().getValue().doctor;
           }
       });

       ObservableList<Ticket> ticketlist = new SqlControl().getTicketsForTableViewForDoctor(doctor.getID());
       final TreeItem<Ticket> root = new RecursiveTreeItem<Ticket>(ticketlist, RecursiveTreeObject::getChildren);
       tickets.getColumns().setAll(time,patient);
       tickets.setRoot(root);
       tickets.setShowRoot(false);
   }
   public void setDoctor(Doctor doctor){
        this.doctor = doctor;
   }


    @FXML
    public void backToMenu(){
        mainController.loadMenuScreen();
    }

    private void pets(int ind)
    {
        petsArray[ind-1]++;
    }

    private void createData(int index)
    {
        switch (index)
        {
            case 0:
                pieChartData.add(new PieChart.Data("Коты", petsArray[0]*10));
                break;
            case 1:
                pieChartData.add(new PieChart.Data("Собаки", petsArray[1]*10));
                break;
            case 2:
                pieChartData.add(new PieChart.Data("Хомяки", petsArray[2]*10));
                break;
            case 3:
                pieChartData.add(new PieChart.Data("Птиці", petsArray[3]*10));
                break;
            case 4:
                pieChartData.add(new PieChart.Data("Кролики", petsArray[4]*10));
                break;
            case 5:
                pieChartData.add(new PieChart.Data("Хорьки", petsArray[5]*10));
                break;
            case 6:
                pieChartData.add(new PieChart.Data("Свинки", petsArray[6]*10));
                break;
            case 7:
                pieChartData.add(new PieChart.Data("Черепахи", petsArray[7]*10));
                break;
        }
    }

    @FXML
    public void saveChanges()
    {
        doctor.setName(doctorName.getText());
        doctor.setLastName(doctorLastName.getText());
        doctor.setNum(doctorNum.getText());
        doctor.setGender(gender.getSelectionModel()
                               .getSelectedItem());
        SqlControl.updateDoctor(doctor);
    }
}
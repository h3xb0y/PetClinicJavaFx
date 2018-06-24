package sample.entity;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Ticket extends RecursiveTreeObject<Ticket> {
    public StringProperty time,doctor;
    public Ticket(String time,String doctor){
        this.time = new SimpleStringProperty(time);
        this.doctor = new SimpleStringProperty(doctor);
    }
}
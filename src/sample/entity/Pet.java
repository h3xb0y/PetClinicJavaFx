package sample.entity;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Pet extends RecursiveTreeObject<Pet> {
    public StringProperty name,age;
    public Pet(String name,String age){
        this.name = new SimpleStringProperty(name);
        this.age = new SimpleStringProperty(age);
    }
}

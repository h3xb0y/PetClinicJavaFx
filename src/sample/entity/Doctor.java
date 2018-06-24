package sample.entity;
import sample.Data;
import sample.DoctorSpecialization;
import sample.Person;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Doctor extends Person {

    public DoctorSpecialization Specialization;

    private String cabinet_id;
    public Doctor(DoctorSpecialization specialization, String ID, String Name, String lastname,String num,String gender,String cabinet_id)  {
        super(ID,Name,lastname,num,gender);
        Specialization = specialization;
        this.cabinet_id = cabinet_id;
    }

    public String getCabinet_id() {
        return cabinet_id;
    }

    public void setCabinet_id(String cabinet_id) {
        this.cabinet_id = cabinet_id;
    }

    @Override
    public String toString() {
        return
                "Имя: " + name + '\n' +
                "Фамилия: " + lastname + '\n' +
                "Специализация: " + Specialization + '\n'+
                "Кабинет №"+cabinetInfo()+'\n';
    }
    public String cabinetInfo(){
        return
                Data.cabinets.get(ID).getCabinet_num()+
                        "("+Data.cabinets.get(ID).getCabinet_type() +","+
                        Data.cabinets.get(ID).getCabinet_level()+" этаж)";

    }
}

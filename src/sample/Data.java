package sample;

import sample.db.SqlControl;
import sample.entity.*;


import java.util.Map;

public class Data {

    public static Map<String, Doctor> doctors = new SqlControl().getDoctor();

    private static Map<String, Patient> patients = new SqlControl().getOwner();

    public static Map<String, Animal> animals = new SqlControl().getAnimal();


    public static Map<String, Cabinet> cabinets = new SqlControl().getCabinet();

    private static Map<String, Kind> kinds = new SqlControl().getKind();

    public static void updateData()
    {
        doctors = new SqlControl().getDoctor();
        patients = new SqlControl().getOwner();
        animals = new SqlControl().getAnimal();
        cabinets = new SqlControl().getCabinet();
        kinds = new SqlControl().getKind();
    }

}

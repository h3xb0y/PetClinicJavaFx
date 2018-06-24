package sample.db;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import sample.entity.Doctor;
import sample.DoctorSpecialization;
import sample.entity.Patient;
import sample.entity.*;
import sample.*;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class SqlControl {
    private static Statement stmt;
    private static ResultSet rs;
    //region #hashmaps
    private HashMap<String,Doctor> doctors = new HashMap<>();
    private HashMap<String,Patient> owners = new HashMap<>();
    private HashMap<String,Animal> animals = new HashMap<>();
    private HashMap<String,TicketEntity> tickets = new HashMap<>();
    private HashMap<String,Kind> kinds = new HashMap<>();
    private HashMap<String,Cabinet> cabinets = new HashMap<>();
    //endregion
    //region #colors for console
    private static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    private static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    private static final String ANSI_PURPLE = "\u001B[35m";
    private static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";
    //endregion

    /**
     * Создаем процедуры при старте
     * @see Main#start(Stage)
     */
    public static void prepare()
    {
        connectToDatabase();
        System.out.println(ANSI_CYAN+"Создание процедур..."+ANSI_RESET);
        createProcedure(Query.createDoctorProcedure);
        createProcedure(Query.createAnimalProcedure);
        createProcedure(Query.createCabinetProcedure);
        createProcedure(Query.createOwnerProcedure);
        createProcedure(Query.createKindProcedure);
        System.out.println(ANSI_CYAN+"Создание процедур завершено ✔"+ANSI_RESET);
    }

    private static void connectToDatabase()
    {
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/PetClinic", "h3x", "pass");
            stmt = con.createStatement();
        } catch (Exception e){
            System.out.println(ANSI_RED+e.toString()+ANSI_RESET);
        }
    }

    public static void addOwner(String name,String lastname,String num,String gender,String login) {
        UpdateQuery("insert into `owner` values "+"(NULL,'"+name+"','"+lastname+"','"+num+"','"+gender+"',"+"TRUE,'"+login+"');");
    }

    public static void addUser(String username,String password,String role){
        UpdateQuery("insert into `user` values "+"('"+username+"','"+password+"','"+role+"');");
    }

    public static void addAnimal(String animalNameText, String animalAgeText,String animalWeightText,String owner_id,String kind_id){
        UpdateQuery("insert into `animal` values "+"(NULL,'"+animalNameText+"','"+animalAgeText+"','"+animalWeightText+"',TRUE,'"+kind_id+"','"+owner_id+"');");
    }

    public HashMap<String, Doctor> getDoctor() {

        ExecuteQuery(Query.callDoctorProcedure);
        try {
            while (rs.next()) {
                doctors.put(rs.getString("doctor_id"),
                        new Doctor(DoctorSpecialization.Ветеринар,rs.getString("doctor_id"),
                                    rs.getString("doctor_name"),rs.getString("doctor_lastname"),rs.getString("doctor_num"),
                                    rs.getString("doctor_gender"),rs.getString("cabinet_id")));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
            return doctors;

    }
    public HashMap<String, Kind> getKind() {
        ExecuteQuery(Query.callKindProcedure);
        try {
            while (rs.next()) {
                kinds.put(rs.getString("kind_id"),
                        new Kind(rs.getString("kind_id"),rs.getString("kind_name")));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return kinds;

    }
    public  HashMap<String, Patient> getOwner(){
        ExecuteQuery(Query.callOwnerProcedure);
        try {
            while (rs.next()) {
                owners.put(rs.getString("owner_id"),
                        new Patient(rs.getString("owner_id"),rs.getString("owner_name"),
                                rs.getString("owner_lastname"),rs.getString("owner_num"),
                                rs.getString("owner_gender"),rs.getString("user_login")));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
            return owners;
    }
    public HashMap<String, Animal>  getAnimal(){
        ExecuteQuery(Query.callAnimalProcedure);
        try {
            while (rs.next()) {
                animals.put(rs.getString("animal_id"),
                        new Animal(rs.getString("animal_id"),rs.getString("animal_name"),
                                rs.getString("animal_age"),
                                rs.getString("animal_weight"),rs.getString("animal_status"),
                                rs.getString("kind_id"),rs.getString("owner_id")));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return animals;
    }
    public HashMap<String, Cabinet> getCabinet(){
        ExecuteQuery(Query.callCabinetProcedure);
        try {
            while (rs.next()) {
                cabinets.put(rs.getString("cabinet_id"),
                        new Cabinet(rs.getString("cabinet_id"),rs.getString("cabinet_num"),
                                rs.getString("cabinet_type"),rs.getString("cabinet_level")));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return cabinets;
    }
    public static void addTicket(String date_order, String time, String animal_id, String owner_id, String doctor_id){
        UpdateQuery("insert into `ticket` values "+"(NULL,'"+date_order+"','"+time+"',1,'"+owner_id+"','"+animal_id+"',"+doctor_id+");");

    }

    public static void RemoveOwnerAndAnimal(String id,String petid){
        UpdateQuery("delete from ticket where owner_id='"+id+"';");
        UpdateQuery("delete from animal where animal_id ='"+petid+"';");
        UpdateQuery("delete from owner where owner_id ='"+id+"';");
    }

    private static void UpdateQuery(String query){
        try {
            stmt.executeUpdate(query);
        } catch (Exception e){
            System.out.println(ANSI_RED+e.toString()+ANSI_RESET);
        } finally {
            System.out.println(ANSI_BLUE+"Запрос на обновление: "+ANSI_CYAN+query+"    "+ANSI_BLUE+"✔"+ANSI_RESET);
        }
    }

    private static void createProcedure(String query){
        try {
            stmt.executeUpdate(query);
        } catch (Exception e){
            System.out.println(ANSI_RED+e.toString()+ANSI_RESET);
        } finally {
            System.out.println(ANSI_PURPLE+query+ANSI_RESET);
        }
    }

    private static void ExecuteQuery(String query){
        try {
            rs = stmt.executeQuery(query);
        } catch (Exception e){
            System.out.println(ANSI_RED+e.toString()+ANSI_RESET);
        } finally {
            System.out.println(ANSI_PURPLE+"Запрос на получение: "+ANSI_BLUE+query+"     "+ANSI_PURPLE+"✔"+ANSI_RESET);
        }
    }

    public static String Login(String login, String password){
        String count = "";
        ExecuteQuery("select count(*) from user where user_login='"+login+"' and user_password='"+password+"';");
        try {
            while (rs.next()) {
                count = rs.getString("count(*)");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return count;
    }
    public static String loginRole(String login, String password){
        String count = "";
        ExecuteQuery("select user_role from user where user_login='"+login+"' and user_password='"+password+"';");
        try {
            while (rs.next()) {
                count = rs.getString("user_role");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return count;
    }

    public Patient getPatientByLogin(String login){
        Patient patient = null;
        ExecuteQuery("select * from owner where user_login='"+login+"';");
        try {
            while (rs.next()) {
                patient = new Patient(rs.getString("owner_id"),rs.getString("owner_name"),
                                rs.getString("owner_lastname"),rs.getString("owner_num"),
                                rs.getString("owner_gender"),rs.getString("user_login"));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return patient;
    }
    public Doctor getDoctorByLogin(String login){
        Doctor doctor = new Doctor(null,null,null,null,null,null,null);
        ExecuteQuery("select * from doctor where user_login='"+login+"';");
        try {
            while (rs.next()) {
                doctor = new Doctor(DoctorSpecialization.Ветеринар,rs.getString("doctor_id"),rs.getString("doctor_name"),
                        rs.getString("doctor_lastname"),rs.getString("doctor_num"),
                        rs.getString("doctor_gender"),rs.getString("cabinet_id"));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return doctor;
    }

    public ObservableList<Pet> getPetsForTableView(String owner_id){
        ObservableList<Pet> petlist = FXCollections.observableArrayList();
        ExecuteQuery("select * from animal where owner_id='"+owner_id+"' and animal_status = 1;");
        try {
            while (rs.next()) {
                petlist.add(new Pet(rs.getString("animal_name"),rs.getString("animal_age")));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return petlist;
    }

    public ObservableList<Ticket> getTicketsForTableView(String owner_id){
        ObservableList<Ticket> ticketlist = FXCollections.observableArrayList();
        ExecuteQuery("select * from ticket where owner_id='"+owner_id+"' and ticket_status = 1;");
        try {
            while (rs.next()) {
                ticketlist.add(new Ticket(rs.getString("ticket_date")+'\n'+rs.getString("ticket_time"),
                        getDoctorNameForTableView(rs.getString("doctor_id"))));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return ticketlist;
    }

    public ObservableList<Ticket> getTicketsForTableViewForDoctor(String doctor_id){
        ObservableList<Ticket> ticketlist = FXCollections.observableArrayList();
        ExecuteQuery("select * from ticket join animal,kind where doctor_id ='"+doctor_id+"' and ticket.animal_id = animal.animal_id and animal.kind_id = kind.kind_id" +
                " and ticket_status = 1;");
        try {
            while (rs.next()) {
                ticketlist.add(new Ticket(rs.getString("ticket_date")+'\n'+rs.getString("ticket_time"),
                        rs.getString("kind_name")));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return ticketlist;
    }


    private String getDoctorNameForTableView(String doctor_id){
        String name = "";
        Map<String, Doctor> doctorssql= Data.doctors;
            for (Map.Entry<String,Doctor> entry: doctorssql.entrySet())
                if (entry.getValue().getID().equals(doctor_id))
                    name = entry.getValue().getName() + ' ' + entry.getValue().getLastName();

        return name;
    }


    public HashMap<String, Animal> getAnimalByOwnerId(String owner_id){
        ExecuteQuery("select * from animal where owner_id='"+owner_id+"' and animal_status = 1;");
        try {
            while (rs.next()) {
                animals.put(rs.getString("animal_id"),
                        new Animal(rs.getString("animal_id"),rs.getString("animal_name"),
                                rs.getString("animal_age"),
                                rs.getString("animal_weight"),rs.getString("animal_status"),
                                rs.getString("kind_id"),rs.getString("owner_id")));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return animals;
    }
    public HashMap<String, TicketEntity> getTicketsByDoctorid(String doctor_id){
        ExecuteQuery("select * from ticket where doctor_id='"+doctor_id+"' and ticket_status = 1;");
        try {
            while (rs.next()) {
                tickets.put(rs.getString("ticket_id"),
                        new TicketEntity(rs.getString("ticket_id"),rs.getString("ticket_date"),
                                rs.getString("ticket_time"),
                                rs.getString("ticket_status"),rs.getString("owner_id"),
                                rs.getString("animal_id"),rs.getString("doctor_id")));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return tickets;
    }

    public static String getNameOfAnimalById(String animal_id)
    {
        String out = "";
        ExecuteQuery("select animal_name from animal where animal_id='"+animal_id+"' and animal_status = 1;");
        try {
            while (rs.next()) {
                out = rs.getString("animal_name");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return out;
    }

    public static String getOwnerNameById(String owner_id)
    {
        String out = "";
        ExecuteQuery("select owner_name,owner_lastname from owner where owner_id='"+owner_id+"' and owner_status = 1;");
        try {
            while (rs.next()) {
                out = rs.getString("owner_name");
                out += " " + rs.getString("owner_lastname");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return out;
    }
    public static String getKindByAnimalId(String animal_id)
    {
        String out = "";
        ExecuteQuery("select kind_name from kind join animal where animal.kind_id = kind.kind_id and animal_id = '"+animal_id+"';");
        try {
            while (rs.next()) {
                out = rs.getString("kind_name");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return out;
    }

    public static Patient getPatientByPatientId(String owner_id)
    {
        Patient p = null;
        ExecuteQuery("select * from owner where owner_id = '"+owner_id+"' and owner_status = 1;");
        try {
            while (rs.next()) {
                p = new Patient(rs.getString("owner_id"),rs.getString("owner_name"),
                        rs.getString("owner_lastname"),rs.getString("owner_num"),
                        rs.getString("owner_gender"),rs.getString("user_login"));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return p;
    }

    public static Animal getAnimalByAnimal_id(String animal_id)
    {
        Animal a = null;
        ExecuteQuery("select * from animal where animal_id = '"+animal_id+"' and animal_status= 1;");
        try {
            while (rs.next()) {
                a = new Animal(rs.getString("animal_id"),rs.getString("animal_name"),
                        rs.getString("animal_age"),
                        rs.getString("animal_weight"),rs.getString("animal_status"),
                        rs.getString("kind_id"),rs.getString("owner_id"));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return a;
    }

    public static void deleteTicket(String ticket_id)
    {
        UpdateQuery("update ticket set ticket_status='0' where ticket_id ='"+ticket_id+"';");
    }

    public static void updateTicket(String time,String date,String ticket_id)
    {
        UpdateQuery("update ticket set ticket_time='"+time+"', ticket_date='"+date+"' where ticket_id ='"+ticket_id+"';");
    }

    public void deleteAnimalById(String animal_id){
        UpdateQuery("update animal set animal_status='0' where animal_id ='"+animal_id+"';");
        UpdateQuery("update ticket set ticket_status='0' where animal_id ='"+animal_id+"';");
    }

    public static void updateOwner(Patient p){
        UpdateQuery("update owner set owner_name = '"+p.getName()+"',owner_lastname = '"+p.getLastName()+"',owner_gender ='"+
                p.getGender()+"',owner_num = '"+p.getNum()+"' where owner_id ='"+p.getID()+"';");
    }

    public static void deleteOwner(Patient p)
    {
        UpdateQuery("update owner set owner_status = 0 where owner_id ='"+p.getID()+"';");
    }

    public static String isAccountEnabled(String login){
        String count = "";
        ExecuteQuery("select count(*) from owner,doctor where owner.user_login = '"+login+"' and owner.owner_status = 1 or" +
                " doctor.user_login = '"+login+"';");
        try {
            while (rs.next()) {
                count = rs.getString("count(*)");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return count;
    }

    public static void updateDoctor(Doctor d){
        UpdateQuery("update doctor set doctor_name= '"+d.getName()+"',doctor_lastname= '"+d.getLastName()+"',doctor_gender='"+
                d.getGender()+"',doctor_num= '"+d.getNum()+"' where doctor_id='"+d.getID()+"';");
    }

}

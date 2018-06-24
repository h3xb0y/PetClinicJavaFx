package sample.entity;

import sample.db.SqlControl;

public class TicketEntity {
    private String ticket_id;
    private String ticket_date;
    private String ticket_time;
    private String ticket_status;
    private String owner_id;
    private String animal_id;
    private String doctor_id;

    public TicketEntity(String ticket_id, String ticket_date, String ticket_time, String ticket_status, String owner_id, String animal_id, String doctor_id) {
        this.ticket_id = ticket_id;
        this.ticket_date = ticket_date;
        this.ticket_time = ticket_time;
        this.ticket_status = ticket_status;
        this.owner_id = owner_id;
        this.animal_id = animal_id;
        this.doctor_id = doctor_id;
    }

    public String getTicket_id() {
        return ticket_id;
    }

    public void setTicket_id(String ticket_id) {
        this.ticket_id = ticket_id;
    }

    public String getTicket_date() {
        return ticket_date;
    }

    public void setTicket_date(String ticket_date) {
        this.ticket_date = ticket_date;
    }

    public String getTicket_time() {
        return ticket_time;
    }

    public void setTicket_time(String ticket_time) {
        this.ticket_time = ticket_time;
    }

    public String getTicket_status() {
        return ticket_status;
    }

    public void setTicket_status(String ticket_status) {
        this.ticket_status = ticket_status;
    }

    public String getOwner_id() {
        return owner_id;
    }

    public void setOwner_id(String owner_id) {
        this.owner_id = owner_id;
    }

    public String getAnimal_id() {
        return animal_id;
    }

    public void setAnimal_id(String animal_id) {
        this.animal_id = animal_id;
    }

    public String getDoctor_id() {
        return doctor_id;
    }

    public void setDoctor_id(String doctor_id) {
        this.doctor_id = doctor_id;
    }

    @Override
    public String toString() {
        return

                "Дата: " + ticket_date + '\n' +
                        "Время: " + ticket_time + '\n' +
                        "Питомец: " + SqlControl.getNameOfAnimalById(animal_id) + '\n';
    }
}


package sample.entity;

import sample.Person;

public class Patient extends Person {

    private String animalid;
    private String login;
    public Patient(String ID, String name, String lastname, String num,String gender, String login) {
        super(ID, name, lastname,num,gender);
        this.login = login;
    }

    @Override
    public String toString() {
        return
                "************************" + '\n' +
                "*Имя: " + name + '\n' +
                "*Фамилия: " + lastname + '\n' +
                "*Номер телефона: " + num + '\n' +
                "*Пол: " + gender +'\n' +
                "************************" + '\n';
    }
}


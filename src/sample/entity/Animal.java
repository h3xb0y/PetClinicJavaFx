package sample.entity;

import sample.db.SqlControl;

public class Animal {


    private String ID;
    private String name;
    private String kind_id;
    private String age;
    private String weight;
    private String status;

    public String getOwner_id() {
        return owner_id;
    }

    public void setOwner_id(String owner_id) {
        this.owner_id = owner_id;
    }

    String owner_id;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getWeight() {
        return weight;
    }

    public String getKind_id() {
        return kind_id;
    }

    public void setKind_id(String kind_id) {
        this.kind_id = kind_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public Animal(String ID, String name, String age, String weight,String status, String kind_id,String owner_id) {
        this.ID = ID;
        this.name = name;
        this.kind_id = kind_id;
        this.age = age;
        this.weight = weight;
        this.status = status;
    }

    @Override
        public String toString() {
            return

                            "Кличка: " + name + '\n' +
                            "Тип: " + SqlControl.getKindByAnimalId(ID) + '\n' +
                            "Возраст: " + age + '\n' +
                            "Вес: " + weight +'\n';
        }
    }


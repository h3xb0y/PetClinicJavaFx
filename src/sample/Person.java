package sample;
public abstract class  Person {

    protected String ID;
    protected String name;
    protected String lastname;
    protected String num;



    protected String gender;

    public Person(String ID, String name, String lastname,String num, String gender) {
        this.ID = ID;
        this.name = name;
        this.lastname = lastname;
        this.num = num;
        this.gender = gender;
    }

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

    public String getLastName() {
        return lastname;
    }

    public void setLastName(String lastname) {
        this.lastname = lastname;
    }
    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }
    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

}



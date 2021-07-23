package mz.unilurio.solidermed.model;

public class EmergencyMedicalPersonnel {
    private String name;
    private String surname;
    private String contact;
    private int id;


    public EmergencyMedicalPersonnel(String name, String surname, String contact, int id) {
        this.name = name;
        this.surname = surname;
        this.contact = contact;
        this.id=id;
    }

    public EmergencyMedicalPersonnel() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}

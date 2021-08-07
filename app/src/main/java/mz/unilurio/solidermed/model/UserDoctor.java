package mz.unilurio.solidermed.model;

public class UserDoctor {
             private  String emailUser;
             private  String passwordUser;
             private  String contacto;
             private  int idUser;
             private  Privilegios privilegios;


    public UserDoctor(String emailUser, String passwordUser, int idUser,String contacto) {
        this.emailUser = emailUser;
        this.passwordUser = passwordUser;
        this.idUser = idUser;
        this.contacto=contacto;
    }

    public String getEmailUser() {
        return emailUser;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public void setEmailUser(String emailUser) {
        this.emailUser = emailUser;
    }

    public String getPasswordUser() {
        return passwordUser;
    }

    public void setPasswordUser(String passwordUser) {
        this.passwordUser = passwordUser;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }
}

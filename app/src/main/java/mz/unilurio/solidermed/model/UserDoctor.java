package mz.unilurio.solidermed.model;

public class UserDoctor {
             private  String fullName;
             private  String userLogin;
             private  String passwordUser;
             private  String contacto;
             private  int idUser;
             private  Privilegios privilegios;


    public UserDoctor(String fullName,String userLogin, String passwordUser, int idUser, String contacto) {
        this.fullName = fullName;
        this.passwordUser = passwordUser;
        this.idUser = idUser;
        this.contacto=contacto;
        this.userLogin=userLogin;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public String getFullName() {
        return fullName;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
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

package mz.unilurio.solidermed.model;

public class UserNurse {
       private  String fullName;
       private  String passworNurse;
       private  String idNurse;
       private  String contacto;
       private  String userNurse;
       private  Privilegios privilegios;


    public UserNurse(String nomeNurse,String userNurse, String passworNurse, String idNurse,String contacto) {
        this.fullName = nomeNurse;
        this.passworNurse = passworNurse;
        this.idNurse = idNurse;
        this.contacto=contacto;
        this.userNurse =userNurse;
    }




    public String getUserNurse() {
        return userNurse;
    }

    public void setUserNurse(String userNurse) {
        this.userNurse = userNurse;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPassworNurse() {
        return passworNurse;
    }

    public void setPassworNurse(String passworNurse) {
        this.passworNurse = passworNurse;
    }

    public String getIdNurse() {
        return idNurse;
    }

    public void setIdNurse(String idNurse) {
        this.idNurse = idNurse;
    }
}


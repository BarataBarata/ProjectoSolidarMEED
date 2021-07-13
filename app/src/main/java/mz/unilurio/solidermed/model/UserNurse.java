package mz.unilurio.solidermed.model;

public class UserNurse {
       private  String nomeNurse;
       private  String passworNurse;
       private  String idNurse;
       private  Privilegios privilegios;


    public UserNurse(String nomeNurse, String passworNurse, String idNurse) {
        this.nomeNurse = nomeNurse;
        this.passworNurse = passworNurse;
        this.idNurse = idNurse;
    }


    public String getNomeNurse() {
        return nomeNurse;
    }

    public void setNomeNurse(String nomeNurse) {
        this.nomeNurse = nomeNurse;
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


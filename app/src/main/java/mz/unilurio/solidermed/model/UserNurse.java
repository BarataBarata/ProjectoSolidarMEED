package mz.unilurio.solidermed.model;

public class UserNurse {
       private  String nomeNurse;
       private  String passworNurse;
       private  String idNurse;
       private  String contacto;
       private  String apelido;
       private  Privilegios privilegios;


    public UserNurse(String nomeNurse,String apelido, String passworNurse, String idNurse,String contacto) {
        this.nomeNurse = nomeNurse;
        this.passworNurse = passworNurse;
        this.idNurse = idNurse;
        this.contacto=contacto;
        this.apelido=apelido;
    }


    public String getApelido() {
        return apelido;
    }

    public void setApelido(String apelido) {
        this.apelido = apelido;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
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


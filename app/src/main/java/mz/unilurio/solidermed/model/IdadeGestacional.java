package mz.unilurio.solidermed.model;

public class IdadeGestacional {

       private String idadeGestacional;
       private  String id;

    public IdadeGestacional(String idadeGestacional, String id) {
        this.idadeGestacional = idadeGestacional;
        this.id = id;
    }

    public String getIdadeGestacional() {
        return idadeGestacional;
    }

    public void setIdadeGestacional(String idadeGestacional) {
        this.idadeGestacional = idadeGestacional;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

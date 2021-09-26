package mz.unilurio.solidermed.model;

public class Hospitais {
    private String nomeHospital;
    private  String id;
    private   static String titleHospital="";

    public Hospitais(String nomeHospital, String id) {
        this.nomeHospital = nomeHospital;
        this.id = id;
    }

    public String getTitleHospital() {
        return titleHospital;
    }

    public void setTitleHospital(String titleHospital) {
        this.titleHospital = titleHospital;
    }

    public Hospitais() {
    }

    public String getNomeHospital() {
        return nomeHospital;
    }

    public void setNomeHospital(String nomeHospital) {
        this.nomeHospital = nomeHospital;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return  nomeHospital+"";
    }
}

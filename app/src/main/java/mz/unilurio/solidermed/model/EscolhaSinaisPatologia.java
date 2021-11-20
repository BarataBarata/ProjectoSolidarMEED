package mz.unilurio.solidermed.model;

public class EscolhaSinaisPatologia {

    private String nomeSinal;
    private boolean isSelected;
    private int idSelect;


    public EscolhaSinaisPatologia(String nomeSinal, boolean isSelected, int idSelect) {
        this.nomeSinal = nomeSinal;
        this.isSelected = isSelected;
        this.idSelect=idSelect;
    }

    public String getNomeSinal() {
        return nomeSinal;
    }

    public void setNomeSinal(String nomeSinal) {
        this.nomeSinal = nomeSinal;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public int getIdSelect() {
        return idSelect;
    }

    public void setIdSelect(int idSelect) {
        this.idSelect = idSelect;
    }

    @Override
    public String toString() {
        return "{" +
                ", idSelect=" + idSelect +
                '}';
    }
}

package mz.unilurio.solidermed.model;

public class Patologia {
    static int  ordem =1;
    String id;
    String patologia;
    boolean isSelected;



    public Patologia(){
        ordem =1;
    }

    public String getId() {
        return id;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPatologia() {
        return patologia;
    }

    public void setPatologia(String patologia) {
        this.patologia = patologia;
    }

//    @Override
//    public String toString() {
//        return "Patologia{" +
//                "id='" + id + '\'' +
//                ", patologia='" + patologia + '\'' +
//                ", isSelected=" + isSelected +
//                '}';
//    }


    @Override
    public String toString() {
        return
                '\n'+""+ ordem++ +"-"+ patologia +'\n' ;

    }
}

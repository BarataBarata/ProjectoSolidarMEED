package mz.unilurio.solidermed.model;

public class DilatationAndTimer {
    private  String numberDilatation;
    private  String timerDilatation;
    private  int idDilatation;

    public DilatationAndTimer(String numberDilatation, String timerDilatation, int idDilatation) {
        this.numberDilatation = numberDilatation;
        this.timerDilatation = timerDilatation;
        this.idDilatation = idDilatation;
    }

    public String getNumberDilatation() {
        return numberDilatation;
    }

    public void setNumberDilatation(String numberDilatation) {
        this.numberDilatation = numberDilatation;
    }

    public String getTimerDilatation() {
        return timerDilatation;
    }

    public void setTimerDilatation(String timerDilatation) {
        this.timerDilatation = timerDilatation;
    }

    public int getIdDilatation() {
        return idDilatation;
    }

    public void setIdDilatation(int idDilatation) {
        this.idDilatation = idDilatation;
    }
}

package mz.unilurio.solidermed.model;

public class DilatationAndTimer {
    private  int numberDilatation;
    private  int timerDilatationHours;
    private  int timerDilatationMinutes;
    private  int fullTimerDilatationHours;
    private  int idDilatation;


    public DilatationAndTimer(int numberDilatation, int timerDilatationHours, int timerDilatationMinutes,int id) {
        this.fullTimerDilatationHours=timerDilatationHours*3600+timerDilatationMinutes*60;
        this.numberDilatation = numberDilatation;
        this.timerDilatationHours = timerDilatationHours;
        this.timerDilatationMinutes = timerDilatationMinutes;
        this.idDilatation=id;
    }

    public int getFullTimerDilatationHours() {
        return fullTimerDilatationHours;
    }

    public void setFullTimerDilatationHours(int fullTimerDilatationHours) {
        this.fullTimerDilatationHours = fullTimerDilatationHours;
    }

    public int getTimerDilatationMinutes() {
        return timerDilatationMinutes;
    }

    public void setTimerDilatationMinutes(int timerDilatationMinutes) {
        this.timerDilatationMinutes = timerDilatationMinutes;
    }

    public int getNumberDilatation() {
        return numberDilatation;
    }

    public void setNumberDilatation(int numberDilatation) {
        this.numberDilatation = numberDilatation;
    }

    public int getTimerDilatationHours() {
        return timerDilatationHours;
    }

    public void setTimerDilatationHours(int timerDilatationHours) {
        this.timerDilatationHours = timerDilatationHours;
    }

    public int getIdDilatation() {
        return idDilatation;
    }

    public void setIdDilatation(int idDilatation) {
        this.idDilatation = idDilatation;
    }
}

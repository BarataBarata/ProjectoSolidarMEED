package mz.unilurio.solidermed.model;

import java.util.Calendar;
import java.util.Date;

public class Measures {
    private Date initialHour;
    private int initialDilatation;
    private Date predictedExpulsionHour;
    private int predictedDilatation;

    public Measures(Date initialHour, int initialDilatation) {
        this.initialHour = initialHour;
        this.initialDilatation = initialDilatation;
        predictDilatationAndTime();
    }

    public Date getInitialHour() {
        return initialHour;
    }

    public void setInitialHour(Date initialHour) {
        this.initialHour = initialHour;
    }

    public int getInitialDilatation() {
        return initialDilatation;
    }

    public void setInitialDilatation(int initialDilatation) {
        this.initialDilatation = initialDilatation;
    }

    public Date getPredictedExpulsionHour() {
        return predictedExpulsionHour;
    }

    public void setPredictedExpulsionHour(Date predictedExpulsionHour) {
        this.predictedExpulsionHour = predictedExpulsionHour;
    }

    public int getPredictedDilatation() {
        return predictedDilatation;
    }

    public void setPredictedDilatation(int predictedDilatation) {
        this.predictedDilatation = predictedDilatation;
    }

    public void predictDilatationAndTime(){
        this.predictedDilatation = this.initialDilatation + 5;
        this.predictedExpulsionHour = addHoursToJavaUtilDate(this.initialHour, 5);
    }

    public Date addHoursToJavaUtilDate(Date date, int hours) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR_OF_DAY, hours);
        return calendar.getTime();
    }
}

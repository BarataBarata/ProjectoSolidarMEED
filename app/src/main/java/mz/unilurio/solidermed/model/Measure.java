package mz.unilurio.solidermed.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Measure {
    private Date initialHour;
    private int initialDilatation;
    private Date predictedExpulsionHour;
    private int predictedDilatation;

    public Measure(Date initialHour, int initialDilatation) {
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
//        this.predictedExpulsionHour = addHoursToJavaUtilDate(this.initialHour, 5);
        this.predictedExpulsionHour = addHorasToJavaUtilDate(this.initialHour, 1);
    }

    public Date addHoursToJavaUtilDate(Date date, int hours) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR_OF_DAY, hours);
        return calendar.getTime();
    }

    public Date addHorasToJavaUtilDate(Date date, int horas) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, horas);
        return calendar.getTime();
    }

    @Override
    public String toString() {
        return "Measure{" +
                "initialHour=" + format(initialHour) +
                ", initialDilatation=" + initialDilatation +
                ", predictedExpulsionHour=" + format(predictedExpulsionHour) +
                ", predictedDilatation=" + predictedDilatation +
                '}';
    }

    private String format(Date date){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return dateFormat.format(date);
    }

    public int retornaHoraFinal(int initialDilatation,int horaPredeninida){
               if(((horaPredeninida-initialDilatation)==0) || (horaPredeninida-initialDilatation)<0){
                   return 0;
               }
               return horaPredeninida-initialDilatation;
    }
}

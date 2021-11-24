package mz.unilurio.solidermed.model;

import android.os.Parcel;

import java.util.Date;

public final class Notification {
    public static final int TWO_INTERVAL = 1;
    private boolean inProcess;
    private String id;
    private String idAuxParturiente;
    private int horas=0;
    private int allSegundos=0;
    private int minutos=0;
    private boolean isAtendido;
    String yearDayMonthNotification;
    private int segundo=0;
    private int color;
    private String viewTimerTwo;
    private String message;
    private String time;
    private boolean isOpen;
    private Date nextNotifier;

    public Notification(){

    }

    protected Notification(Parcel in) {
        id = in.readString();
        message = in.readString();
        isOpen = in.readByte() != 0;
    }

    public String getIdAuxParturiente() {
        return idAuxParturiente;
    }

    public void setIdAuxParturiente(String idAuxParturiente) {
        this.idAuxParturiente = idAuxParturiente;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setViewTimerTwo(String viewTimerTwo) {
        this.viewTimerTwo = viewTimerTwo;
    }
    public String getViewTimerTwo() {
        return viewTimerTwo;
    }

    public int getAllSegundos() {
        return allSegundos;
    }

    public void setAllSegundos(int allSegundos) {
        this.allSegundos = allSegundos;
    }

    public int getColour() {
        return color;
    }

    public void setColour(int color) {
        this.color = color;
    }

    public boolean isInProcess() {
        return inProcess;
    }

    public String getYearDayMonthNotification() {
        return yearDayMonthNotification;
    }

    public void setYearDayMonthNotification(String yearDayMonthNotification) {
        this.yearDayMonthNotification = yearDayMonthNotification;
    }

    public boolean isAtendido() {
        return isAtendido;
    }

    public void setAtendido(boolean atendido) {
        isAtendido = atendido;
    }

    public void setInProcess(boolean inProcess) {
        this.inProcess = inProcess;
    }

    public String getMessage() {
        return message;
    }

    public void setNome(String message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public String getId() {
        return this.id;
    }

    public int getHoras() {
        return horas;
    }

    public void setHoras(int horas) {
        this.horas = horas;
    }

    public int getMinutos() {
        return minutos;
    }

    public void setMinutos(int minutos) {
        this.minutos = minutos;
    }

    public int getSegundo() {
        return segundo;
    }

    public void setSegundo(int segundo) {
        this.segundo = segundo;
    }
}

package mz.unilurio.solidermed.model;

import android.os.Parcel;

import java.util.Date;

public final class Notificacao {
    private boolean inProcess;
    private String id;
    private String idAuxParturiente;
    private String horaDoEnvioDaMensagem="";
    private int minutos=0;
    private boolean isAtendido;
    private int color;
    public  String viewTimerTwo="";
    private String message;
    private String time;
    private boolean isOpen;
    private  boolean alertaDisparado;

    public Notificacao(){ }


    public String getHoraDoEnvioDaMensagem() {
        return horaDoEnvioDaMensagem;
    }

    public void setHoraDoEnvioDaMensagem(String horaDoEnvioDaMensagem) {
        this.horaDoEnvioDaMensagem = horaDoEnvioDaMensagem; }

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
    public int getColour() {
        return color;
    }

    public void setColour(int color) {
        this.color = color;
    }

    public boolean isInProcess() {
        return inProcess;
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


    public boolean getAlertaDisparada() {
        return alertaDisparado;
    }
    public void setAlertaDisparada(boolean alertaDisparado) {
        this.alertaDisparado=alertaDisparado;
    }
}

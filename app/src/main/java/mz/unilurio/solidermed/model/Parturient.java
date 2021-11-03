package mz.unilurio.solidermed.model;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.os.Parcel;
import android.os.Parcelable;
import android.telephony.SmsManager;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import mz.unilurio.solidermed.AddParturientActivity;
import mz.unilurio.solidermed.MainActivity;

public class Parturient implements Parcelable {

   //............horaparte..........//
   private  int horaParte;
   private  int minutoParte;
   private  int segundoParte;
   private  static String idPassAll;
   ///.............................///
    private Notification notifications  = new Notification();
    public boolean isEditDilatation;
    private boolean trasferirParturiente;
    private boolean isTrasferidoParaForaDoHospital;
    private int id;
    private String fullName;
    DBService dbService;
    int timerAux;
    public boolean startCountDownTimer;
    public boolean cancelCountDownTimer;
    private boolean inProcess;
    private static boolean isAlertaTwo;
    private static int timerEmergence;
    private String horaEntrada;
    private String horaAlerta;
    private String horaAtendimento;
    private String name;
    private int initializeTimerAlert;
    private String surname;
    private int age;
    private int para;
    private String gestatinalRange;
    private Date time;
    private boolean isTransfered;
    private boolean disparo;
    private String reason;
    private int numeroCama;
    private boolean isStartCont;
    private String origemTransferencia;
    private String motivosDaTrasferencia;
    private String destinoTrasferencia;
    private String motivosDestinoDaTrasferencia;
    private String viewTempo;
    private String tipoAtendimento="";
    private Notification notification= new Notification();;
    private Object context;

    public Parturient() {
    }

    public Parturient(int id, String name, String surname, int age) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.age = age;
    }

    public int getHoraParte() {
        return horaParte;
    }

    public void setHoraParte(int horaParte) {
        this.horaParte = horaParte;
    }

    public int getMinutoParte() {
        return minutoParte;
    }

    public void setMinutoParte(int minutoParte) {
        this.minutoParte = minutoParte;
    }

    public int getSegundoParte() {
        return segundoParte;
    }

    public void setSegundoParte(int segundoParte) {
        this.segundoParte = segundoParte;
    }

    public static int getTimerEmergence() {
        return timerEmergence;
    }

    public static void setTimerEmergence(int timerEmergence) {
        Parturient.timerEmergence = timerEmergence;
    }

    public String getTipoAtendimento() {
        return tipoAtendimento;
    }

    public void setTipoAtendimento(String tipoAtendimento) {
        this.tipoAtendimento = tipoAtendimento;
    }


    public static String getIdPassAll() {
        return idPassAll;
    }

    public static void setIdPassAll(String idPassAll) {
        Parturient.idPassAll = idPassAll;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public boolean isTrasferirParturiente() {
        return trasferirParturiente;
    }

    public void setTrasferirParturiente(boolean trasferirParturiente) {
        this.trasferirParturiente = trasferirParturiente;
    }

    public String getMotivosDestinoDaTrasferencia() {
        return motivosDestinoDaTrasferencia;
    }

    public void setMotivosDestinoDaTrasferencia(String motivosDestinoDaTrasferencia) {
        this.motivosDestinoDaTrasferencia = motivosDestinoDaTrasferencia;
    }

    public boolean isStartCont() {
        return isStartCont;
    }

    public void setIsRun(boolean startCont) {
        isStartCont = startCont;
    }

    public String getHoraAtendimento() {
        return horaAtendimento;
    }

    public Notification getNotifications() {
        return notifications;
    }

    public void setHoraAtendimento(String horaAtendimento) {
        this.horaAtendimento = horaAtendimento;
    }

    public boolean isRun() {
        return disparo;
    }

    public void setRun(boolean disparo) {
        this.disparo = disparo;
    }

    public Parturient(String name, String surname, int age) {
        this.name = name;
        this.surname = surname;
        this.age = age;
    }

    public boolean isInProcess() {
        return inProcess;
    }

    public void setInProcess(boolean inProcess) {
        this.inProcess = inProcess;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Parturient(String name, String surname, int age, boolean isTransfered, String reason, Date time, int numeroCama) {
        this.name = name;
        this.time = time;
        this.surname = surname;
        this.age = age;
        this.isTransfered = isTransfered;
        this.reason = reason;
        this.numeroCama = numeroCama;
    }

    public boolean isTrasferidoParaForaDoHospital() {
        return isTrasferidoParaForaDoHospital;
    }

    public void setTrasferidoParaForaDoHospital(boolean trasferidoParaForaDoHospital) {
        isTrasferidoParaForaDoHospital = trasferidoParaForaDoHospital;
    }

    public String getDestinoTrasferencia() {
        return destinoTrasferencia;
    }

    public void setDestinoTrasferencia(String destinoTrasferencia) {
        this.destinoTrasferencia = destinoTrasferencia;
    }

    public void setInitializeTimerAlert(int tempoRestante) {
        this.initializeTimerAlert = tempoRestante;
    }

    public String getHoraEntrada() {
        return horaEntrada;
    }

    public void setHoraEntrada(String horaEntrada) {
        this.horaEntrada = horaEntrada;
    }

    public String getHoraAlerta() {
        return horaAlerta;
    }

    public void setHoraAlerta(String horaAlerta) {
        this.horaAlerta = horaAlerta;
    }

    public int getNumeroCama() {
        return numeroCama;
    }

    public void setNumeroCama(int numeroCama) {
        this.numeroCama = numeroCama;
    }

    protected Parturient(Parcel in) {
        id = in.readInt();
        name = in.readString();
        surname = in.readString();
        age = in.readInt();
        isTransfered = in.readByte() != 0;
        reason = in.readString();
    }

    public static final Creator<Parturient> CREATOR = new Creator<Parturient>() {
        @Override
        public Parturient createFromParcel(Parcel in) {
            return new Parturient(in);
        }

        @Override
        public Parturient[] newArray(int size) {
            return new Parturient[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(surname);
        dest.writeInt(age);
        dest.writeByte((byte) (isTransfered ? 1 : 0));
        dest.writeString(reason);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isTransfered() {
        return isTransfered;
    }

    public void setTransfered(boolean transfered) {
        isTransfered = transfered;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public int getPara() {
        return para;
    }

    public void setPara(int para) {
        this.para = para;
    }
    public String getGestatinalRange() {
        return gestatinalRange;
    }

    public void setGestatinalRange(String gestatinalRange) {
        this.gestatinalRange = gestatinalRange;
    }

    public String getOrigemTransferencia() {
        return origemTransferencia;
    }

    public void setOrigemTransferencia(String origemTransferencia) {
        this.origemTransferencia = origemTransferencia;
    }

    public String getMotivosDaTrasferencia() {
        return motivosDaTrasferencia;
    }

    public void setMotivosDaTrasferencia(String motivosDaTrasferencia) {
        this.motivosDaTrasferencia = motivosDaTrasferencia;
    }



    public void setStartCountDownTimer(){
        if(startCountDownTimer){
            startCountDownTimer=false;
            //initializeCountDownTimer(timerAux);
        }

    }

    public String getTempoRest() {
        return viewTempo;
    }

    public void setTempoRes(String tempoRestante) {
        viewTempo = tempoRestante;
    }

    private String formatMinuto(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("mm");
        return dateFormat.format(date);
    }

    private String formatSegundos(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("ss");
        return dateFormat.format(date);
    }

    private String formatHoras(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("hh");
        return dateFormat.format(date);
    }

    private String format(Date date){
        DateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");
        return dateFormat.format(date);
    }

    public  String oUpperFirstCase(String string){
        String auxString=(string.charAt(0)+"").toUpperCase()+""+string.substring(1)+"";
        return  auxString;
    }



    public boolean isAlertaTwo() {
        return isAlertaTwo;
    }

    public void setAlertaTwo(boolean alertaTwo) {
        isAlertaTwo = alertaTwo;
    }
}

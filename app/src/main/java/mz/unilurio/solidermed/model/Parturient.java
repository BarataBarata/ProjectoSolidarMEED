package mz.unilurio.solidermed.model;

import android.content.Context;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.os.Parcel;
import android.os.Parcelable;
import android.telephony.SmsManager;
import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import mz.unilurio.solidermed.AddParturientActivity;
import mz.unilurio.solidermed.MainActivity;

public class Parturient implements Parcelable {
    private Notification notifications  = new Notification();
    public boolean isEditDilatation;
    private boolean trasferirParturiente;
    private boolean isTrasferidoParaForaDoHospital;
    private int id;
    private String fullName;
    int timerAux;
    public boolean startCountDownTimer;
    public boolean cancelCountDownTimer;
    private boolean inProcess;
    private DBService dbService;
    private static int timerEmergence;
    private Date horaEntrada;
    private Date horaAlerta;
    private Date horaAtendimento;
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
    private String tipoAtendimento;
    private Notification notification= new Notification();;

    public Parturient() {
    }

    public Parturient(int id, String name, String surname, int age) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.age = age;
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

    public Date getHoraAtendimento() {
        return horaAtendimento;
    }

    public Notification getNotifications() {
        return notifications;
    }

    public void setHoraAtendimento(Date horaAtendimento) {
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

    public Date getHoraEntrada() {
        return horaEntrada;
    }

    public void setHoraEntrada(Date horaEntrada) {
        this.horaEntrada = horaEntrada;
    }

    public Date getHoraAlerta() {
        return horaAlerta;
    }

    public void setHoraAlerta(Date horaAlerta) {
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

//    public void setAge(int age) throws IllegalArgumentException{
//        if ((age < 12) || (age > 50)){
//            throw new IllegalArgumentException("A idade deve estar compreendida entre os 12 e os 50 anos");
//        }else{
//            this.age = age;
//        }
//    }

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

//    public void setPara(int para) throws IllegalArgumentException{
//        if((para < 0) || (para > 20)){
//            throw new IllegalArgumentException("A opção de paridade (PARA) deve estar compreendida entre 0 e 20");
//        }else {
//            this.para = para;
//        }
//    }

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


    public void initializeCountDownTimer(int seconds) {

        new CountDownTimer(seconds*1000+1000, 1000) {

            public void onTick(long millisUntilFinished) {
                int seconds = (int) (millisUntilFinished / 1000);

                int hours = seconds / (60 * 60);
                int tempMint = (seconds - (hours * 60 * 60));
                int minutes = tempMint / 60;
                seconds = tempMint - (minutes * 60);

                setTempoRes("Tempo Restante : " + String.format("%02d", hours)
                        + ":" + String.format("%02d", minutes)
                        + ":" + String.format("%02d", seconds));
                timerAux=minutes*60+hours*3600+seconds;
                if(cancelCountDownTimer){
                    startCountDownTimer=true;
                    cancelCountDownTimer=false;
                    cancel();
                    setTempoRes("Cancelado...");

                }
                if(isEditDilatation){
                    isEditDilatation=false;
                    cancel();
                    initializeCountDownTimer(initializeTimerAlert);
                }else {
                }
               }

            public void onFinish() {
                alertaEmergence(timerEmergence);
                setTempoRes("Alerta Disparado");
                sendNotification();
            }
        }.start();
    }

    public void setStartCountDownTimer(){
        if(startCountDownTimer){
            startCountDownTimer=false;
            initializeCountDownTimer(timerAux);
        }

    }
    public void alertaEmergence(int seconds) {

        new CountDownTimer(seconds * 1000 + 1000, 1000) {

            public void onTick(long millisUntilFinished) {
                int seconds = (int) (millisUntilFinished / 1000);

                int hours = seconds / (60 * 60);
                int tempMint = (seconds - (hours * 60 * 60));
                int minutes = tempMint / 60;
                seconds = tempMint - (minutes * 60);

//                setTempoRes("Tempo Restante : " + String.format("%02d", hours)
//                        + ":" + String.format("%02d", minutes)
//                        + ":" + String.format("%02d", seconds));
//
            }

            public void onFinish() {
                sendMensageEmergence();

            }
        }.start();
    }

    private void sendMensageEmergence() {
     if(!this.inProcess){
         notification.setColour(Color.rgb(248, 215,218));
         List<UserDoctor> list= new AddParturientActivity().getListUserDoctor();
         String mensagem=fullName+" necessita  de cuidados medicos";
         System.out.println(mensagem);
         for(UserDoctor userDoctor: list){
             sendSMS(userDoctor.getContacto(),mensagem);
         }
     }
    }
    private void sendSMS(String phoneNumber, String message) {
        phoneNumber = phoneNumber.trim();
        message = message.trim();

        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, message, null, null);

        } catch (Exception e) {
            Log.i("EXPECTION SMS", e.getMessage());
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


    void sendNotification(){
        notification.setColour(Color.YELLOW+Color.BLACK);
        notification.setMessage(fullName);
        notification.setId(id+"");
        notification.setInProcess(inProcess);
        notification.setTime( Calendar.getInstance().getTime());
        notification.setOpen(true);
        DBManager.getInstance().addNewNotification(notification);
    }


    public  String oUpperFirstCase(String string){
        String auxString=(string.charAt(0)+"").toUpperCase()+""+string.substring(1)+"";
        return  auxString;
    }

}

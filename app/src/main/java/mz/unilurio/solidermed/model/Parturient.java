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
    private int id;
    private boolean inProcess;
    private DBService dbService;
    private static int timerEmergence=30;
    private Date horaEntrada;
    private Date horaAlerta;
    private Date horaAtendimento;
    private String name;
    private int tempoAlerta;
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
    private String viewTempo;
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

    public void setTempoAlerta(int tempoRestante) {
        this.tempoAlerta = tempoRestante;
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


    public void alertParturiente(int seconds) {

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
               }

            public void onFinish() {
                alertaEmergence(timerEmergence);
                setTempoRes("Alerta Disparado");
                sendNotification();

            }
        }.start();
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
                System.out.println(seconds);
            }

            public void onFinish() {
                sendMensageEmergence();

            }
        }.start();
    }

    private void sendMensageEmergence() {
     if(!this.inProcess){
         notification.setColour(Color.rgb(248, 215,218));
         sendSMS(name,surname);
         List<UserDoctor> list= new AddParturientActivity().getListUserDoctor();
         String mensagem=oUpperFirstCase(name) +" "+oUpperFirstCase(surname)+" necessita  de cuidados medicos";
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
        notification.setMessage(name+" "+surname);
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

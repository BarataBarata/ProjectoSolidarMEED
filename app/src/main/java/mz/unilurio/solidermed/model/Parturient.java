package mz.unilurio.solidermed.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.provider.ContactsContract;

import java.util.Date;
import java.util.Timer;

public class Parturient implements Parcelable {
    private int id;
    private Date horaEntrada;
    private Date horaAlerta;
    private Date horaAtendimento;
    private String name;
    private String surname;
    private int age;
    private int para;
    private String gestatinalRange;
    private Date time;
    private boolean isTransfered;
    private boolean disparo;
    private String reason;
    private int numeroCama;
    private  String origemTransferencia;
    private  String motivosDaTrasferencia;





    public Parturient(){}

    public Parturient(int id, String name, String surname, int age) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.age = age;
    }

    public Date getHoraAtendimento() {
        return horaAtendimento;
    }

    public void setHoraAtendimento(Date horaAtendimento) {
        this.horaAtendimento = horaAtendimento;
    }

    public boolean isDisparo() {
        return disparo;
    }

    public void setDisparo(boolean disparo) {
        this.disparo = disparo;
    }

    public Parturient(String name, String surname, int age) {
        this.name = name;
        this.surname = surname;
        this.age = age;
    }
    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Parturient(String name, String surname, int age, boolean isTransfered, String reason, Date time,int numeroCama) {
        this.name = name;
        this.time = time;
        this.surname = surname;
        this.age = age;
        this.isTransfered = isTransfered;
        this.reason = reason;
        this.numeroCama=numeroCama;
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

    public void setAge(int age){ this.age = age; }

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

    public int getPara() { return para; }

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

    public String getGestatinalRange() { return gestatinalRange; }

    public void setGestatinalRange(String gestatinalRange) { this.gestatinalRange = gestatinalRange; }

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
}

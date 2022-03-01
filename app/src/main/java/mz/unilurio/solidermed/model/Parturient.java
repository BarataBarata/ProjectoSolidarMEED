package mz.unilurio.solidermed.model;

import java.util.Date;

public class Parturient {

    private String horaExpulsoDoFeto;
    private String sinaisDePatologia;




    //............horaparte..........//
    private String idAuxParturiente;
    private int segundoParte;
    private  int allSegundos;
    private boolean isDisparo;
    ///.............................///
    public boolean isEditDilatation;
    private boolean trasferirParturiente;
    private boolean isTrasferidoParaForaDoHospital;
    private int id;
    private String fullName;
    public boolean startCountDownTimer;
    public boolean cancelCountDownTimer;
    private boolean inProcess;
    private static int timerEmergence;
    private String horaEntrada;
    private String horaAtendimento;
    private String name;
    private String surname;
    private int age;
    private int para;
    private String gestatinalRange="";
    private Date time;
    private boolean isTransfered;
    private boolean isAtendimento;
    private String setYearDayMonthNotification="";
    private int reason;
    private String origemTransferencia="";
    private String motivosDaTrasferencia="";
    private String destinoTrasferencia="";
    private String motivosDestinoDaTrasferencia="";
    private String viewTempo="";
    private String tipoAtendimento = "";


    public Parturient() {
    }

    public Parturient(int id, String name, String surname, int age) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.age = age;
    }


    public String getSetYearDayMonthNotification() {
        return setYearDayMonthNotification;
    }

    public void setSetYearDayMonthNotification(String setYearDayMonthNotification) {
        this.setYearDayMonthNotification = setYearDayMonthNotification;
    }

    public String getHoraExpulsoDoFeto() {
        return horaExpulsoDoFeto;
    }

    public void setHoraExpulsoDoFeto(String horaExpulsoDoFeto) {
        this.horaExpulsoDoFeto = horaExpulsoDoFeto;
    }

    public String getSinaisDePatologia() {
        return sinaisDePatologia;
    }

    public void setSinaisDePatologia(String sinaisDePatologia) {
        this.sinaisDePatologia = sinaisDePatologia;
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
    public static void setTimerEmergence(int timerEmergence) { Parturient.timerEmergence = timerEmergence; }

    public int getAllSegundos() {
        return allSegundos;
    }

    public void setAllSegundos(int allSegundos) {
        this.allSegundos = allSegundos;
    }

    public boolean isEditDilatation() {
        return isEditDilatation;
    }

    public void setEditDilatation(boolean editDilatation) {
        isEditDilatation = editDilatation;
    }

    public String getTipoAtendimento() {
        return tipoAtendimento;
    }
    public void setTipoAtendimento(String tipoAtendimento) {
        this.tipoAtendimento = tipoAtendimento;
    }

    public boolean isAtendimento() {
        return isAtendimento;
    }

    public void setAtendimento(boolean isAtendido) {
        isAtendimento = isAtendido;
    }

    public boolean isDisparoAlerta() {
        return isDisparo;
    }

    public void setDisparoAlerta(boolean disparo) {
        isDisparo = disparo;
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

    public String getIdAuxParturiente() {
        return idAuxParturiente;
    }

    public void setIdAuxParturiente(String idAuxParturiente) {
        this.idAuxParturiente = idAuxParturiente;
    }

    public String getHoraAtendimento() {
        return horaAtendimento;
    }


    public void setHoraAtendimento(String horaAtendimento) {
        this.horaAtendimento = horaAtendimento;
    }

    public boolean isAtendido() {
        return isAtendimento;
    }

    public void setAtendido(boolean disparo) {
        this.isAtendimento = disparo;
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
        // this.reason = reason;
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


    public String getHoraEntrada() {
        return horaEntrada;
    }

    public void setHoraEntrada(String horaEntrada) {
        this.horaEntrada = horaEntrada;
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

    public int getReason() {
        return reason;
    }

    public void setReason(int reason) {
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


    public void setStartCountDownTimer() {
        if (startCountDownTimer) {
            startCountDownTimer = false;
            //initializeCountDownTimer(timerAux);
        }

    }

    public String getTempoRest() {
        return viewTempo;
    }

    public void setTempoRes(String tempoRestante) {
        viewTempo = tempoRestante;
    }


}

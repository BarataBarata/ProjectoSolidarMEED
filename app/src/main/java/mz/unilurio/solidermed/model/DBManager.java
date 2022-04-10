 package mz.unilurio.solidermed.model;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.List;

import mz.unilurio.solidermed.R;

public class DBManager {

    private  static  int idDilatation=6;

    private static DBManager ourInstance = null;
    private static List<UserDoctor> listDoctor=new ArrayList<>();
    private List<Parturient>listaTransferidos=new ArrayList<>();
    private List<String> listIdParturiente=new ArrayList<>();
    private List<Parturient> auxlistNotificationParturients=new ArrayList<>();
    private List<DilatationAndTimer> dilatationAndTimerList=new ArrayList<>();
    private List<Integer> integerListLimitDilatation=new ArrayList<>();
    private List<Integer>integerListInitDilatation=new ArrayList<>();
    private List<Integer> integerListOpcoesParidade=new ArrayList<>();
    private List<Integer> integerListTimer=new ArrayList<>();
    private List<Integer> integerListDilatationCervical=new ArrayList<>();
    private List<AlertParutient> alertParutientList=new ArrayList<>();
    private List<SeacherUser> listSeacherUser=new ArrayList<>();
    private List<Parturient>listParturientesAtendidos=new ArrayList<>();
    private List<UserDoctor> userDoctorList=new ArrayList<>();
    private List<Settings> settingsList=new ArrayList<>();
    private static ArrayList<Patologia> sinaisPatologiaList=new ArrayList<>();
    private List<Notificacao> initNotificacaos = new ArrayList<>();
    private List<String> listIdadeGestacional=new ArrayList<>();
    private List<Hospitais> hospitais= new ArrayList<>();
    private List<Notificacao> notificacaos = new ArrayList<>();
    private List<Notificacao> auxListNotificacaos = new ArrayList<>();
    private List<Parturient> parturients = new ArrayList<>();
    private List<String> listMotivosTrasferencia = new ArrayList<>();
    private List<String> listOpcoesUnidadeSanitaria = new ArrayList<>();
    private List<UserNurse>userNurseList=new ArrayList<>();
    static private List<Integer> colors = new ArrayList<Integer>();
    static private List<Integer> idades = new ArrayList<Integer>();
    static private List<Integer> camas = new ArrayList<Integer>();
    private static int idEmergencyMedicalPersonnel=5;

    private List<EmergencyMedicalPersonnel> emergencyMedicalPersonnels = new ArrayList<>();
    private  static int idNurse=1;

    public static DBManager getInstance() {
        if(ourInstance == null) {
            ourInstance = new DBManager();
            ourInstance.initializeColor();
            ourInstance.initalizeListMotivosTrasferencia();
            ourInstance.initalizeListOpcoesUnidadeSanitaria ();
            ourInstance.initializeIdade();
            ourInstance.initializeCamas();
            ourInstance.initializeNotifications();
            ourInstance.initializeSettings();
            ourInstance.initializeEmergencyPersonnels();
            ourInstance.initHospitais();
            ourInstance.initializeInfermeira();
            ourInstance.initializeIdadeGestacional();
            ourInstance.initializeInitDilatation();
            ourInstance.initializeInitDilatationLimit();
            ourInstance.initializeDilatationCervical();
            ourInstance.initializeOpcoesParidade();
            ourInstance.initializeTimer();
            ourInstance.intDilatationAndTimer();
            ///ourInstance.initializeParturientesAtendidos();
        }
        return ourInstance;
    }


    public List<Parturient> getListaTransferidos() {
        return listaTransferidos;
    }

    public List<UserDoctor> getUserDoctorList() {
        return userDoctorList;
    }

    private void intDilatationAndTimer() {
       // dilatationAndTimerList.add(new DilatationAndTimer(4,0,1));
    }

    public List<String> getListIdParturiente() {
        return listIdParturiente;
    }

    public List<DilatationAndTimer> getDilatationAndTimerList() {
        return dilatationAndTimerList;
    }

    private void initializeTimer(){
        this.integerListTimer.add(1);
        this.integerListTimer.add(2);
        this.integerListTimer.add(3);
        this.integerListTimer.add(4);
        this.integerListTimer.add(5);
        this.integerListTimer.add(6);
    }

    public List<Patologia> getSinaisPatologiaList() {
        return sinaisPatologiaList;
    }

    public void  addPatologia(Patologia patologia){
        int cont=0;
           for(Patologia patologia1: sinaisPatologiaList){
               if(patologia1.getId().equalsIgnoreCase(patologia.getId())){
                  cont++;
               }
           }
        if(cont==0){
            sinaisPatologiaList.add(patologia);
            cont=0;
        }
    }




    public List<Integer> getIntegerListTimer() {
        return integerListTimer;
    }

    private void initializeDilatationCervical() {
        this.integerListDilatationCervical.add(6);
        this.integerListDilatationCervical.add(7);
        this.integerListDilatationCervical.add(8);
        this.integerListDilatationCervical.add(9);
        this.integerListDilatationCervical.add(10);
        this.integerListDilatationCervical.add(11);
        this.integerListDilatationCervical.add(12);
    }

    public List<Integer> getIntegerListDilatationCervical() {
        return integerListDilatationCervical;
    }

    public List<Parturient> getAuxlistNotificationParturients() {
        return auxlistNotificationParturients;
    }
    public void addAuxListNotificationParturient(Parturient parturient){
                this.auxlistNotificationParturients.add(parturient);
    }


    public void addNewAuxPartEndNot(Parturient parturient){
        int cont =0;
        for(int i=0;i<auxlistNotificationParturients.size();i++){
            if(auxlistNotificationParturients.get(i).getIdAuxParturiente().equalsIgnoreCase(parturient.getIdAuxParturiente())){
                cont++;
            }
        }
        if(cont==0){
            cont=0;
            auxlistNotificationParturients.add(parturient);
        }

    }


    private void initializeOpcoesParidade() {
        this.integerListOpcoesParidade.add(4);
        this.integerListOpcoesParidade.add(5);
        this.integerListOpcoesParidade.add(6);
        this.integerListOpcoesParidade.add(7);
        this.integerListOpcoesParidade.add(8);
    }

    private void initializeInitDilatationLimit() {
        this.integerListLimitDilatation.add(8);
        this.integerListLimitDilatation.add(9);
        this.integerListLimitDilatation.add(10);
        this.integerListLimitDilatation.add(11);
        this.integerListLimitDilatation.add(12);
        this.integerListLimitDilatation.add(13);
    }

    public List<Integer> getIntegerListLimitDilatation() {
        return integerListLimitDilatation;
    }

    private void initializeInitDilatation() {
        this.integerListInitDilatation.add(2);
        this.integerListInitDilatation.add(3);
        this.integerListInitDilatation.add(4);
        this.integerListInitDilatation.add(5);
        this.integerListInitDilatation.add(6);
        this.integerListInitDilatation.add(7);
    }

    public List<Integer> getIntegerListInitDilatation() {
        return integerListInitDilatation;
    }

    public List<AlertParutient> getAlertParutientList() {
        return alertParutientList;
    }

    public void addListAlertParturiente(AlertParutient alertParutients){
           this.alertParutientList.add(alertParutients);
    }

    private void initializeSettings() {
        this.settingsList.add(new Settings("Editar, Eliminar e Adicionar Contacto",1, R.drawable.notification_setting));
        this.settingsList.add(new Settings("Definicoes de Tempo de Alerta",2,R.drawable.timer_settings));
        this.settingsList.add(new Settings("Enfermeiras",3,R.drawable.nurses));
    }


    public List<Parturient> getListParturientesAtendidos() {
        return listParturientesAtendidos;
    }

    public void addParturienteAtendido(Parturient parturient){
        this.listParturientesAtendidos.add(parturient);

    }


    private void initializeIdadeGestacional() {

            this.listIdadeGestacional.removeAll(listIdadeGestacional);

             this.listIdadeGestacional.add("28 Semanas");
             this.listIdadeGestacional.add("entre 28 a 31 Semanas");
             this.listIdadeGestacional.add("entre 32 a 36 Semanas");
             this.listIdadeGestacional.add("36 Semanas");
    }

    public List<String> getListIdadeGestacional() {
        return listIdadeGestacional;
    }

    private void initializeInfermeira() {
    }
    public void addAuxListNotification(Notificacao notificacao){
           this.auxListNotificacaos.add(notificacao);
    }

    public List<Notificacao> getAuxListNotifications() {
        return auxListNotificacaos;
    }

    public List<UserNurse> getUserNurseList() {
        return userNurseList;
    }

    private void initHospitais() {
        this.hospitais.add(new Hospitais("Hospital Distrital de Chiúre","1"));
        this.hospitais.add(new Hospitais("Centro de saúde de Catapua","2"));
        this.hospitais.add(new Hospitais("Centro de saúde de Ocua","3"));
        this.hospitais.add(new Hospitais("Centro de saúde de Chiúre Velho","4"));
        this.hospitais.add(new Hospitais("Centro de saúde de Mmala","5"));
        this.hospitais.add(new Hospitais("Centro de saúde de Marera","6"));
        this.hospitais.add(new Hospitais("Centro de saúde de Mazeze","7"));
        this.hospitais.add(new Hospitais("Centro de saúde de Muege","8"));
        this.hospitais.add(new Hospitais("Centro de saúde de Nakoto","9"));
        this.hospitais.add(new Hospitais("Centro de saúde de Namogeliua","10"));
        this.hospitais.add(new Hospitais("Centro de saúde de Samora Machel","11"));
        this.hospitais.add(new Hospitais("Centro de saúde de Bilibiza","12"));
    }

    public List<Hospitais> getHospitais() {
        return hospitais;
    }

    private void initalizeListMotivosTrasferencia() {
        this.listMotivosTrasferencia.add("Hemorragia anteparto, com suspeita de placenta previa");
        this.listMotivosTrasferencia.add("Hemorragia anteparto, com suspeita de deslocamento prematuro da placenta normalmente inserida");
        this.listMotivosTrasferencia.add("Hemorragia anteparto, com suspeita de ruptura uterina");
        this.listMotivosTrasferencia.add("Suspeita de ruptura uterina pré-termo de membranas");
        this.listMotivosTrasferencia.add("Trabalho de parto arrastado");
        this.listMotivosTrasferencia.add("Pre-eclampsia");
        this.listMotivosTrasferencia.add("Eclampsia");
        this.listMotivosTrasferencia.add("Parto pre-termo");
        this.listMotivosTrasferencia.add("Sofrimento fetal");
        this.listMotivosTrasferencia.add("Homorragia anteparto, com suspeita de placenta previa");
        this.listMotivosTrasferencia.add("Outros");
    }

    private void initalizeListOpcoesUnidadeSanitaria() {
        this.listOpcoesUnidadeSanitaria.add("Hospital Distrital de Chiúre");
        this.listOpcoesUnidadeSanitaria.add("Centro de saúde de Catapua");
        this.listOpcoesUnidadeSanitaria.add("Centro de saúde de Ocua");
        this.listOpcoesUnidadeSanitaria.add("Centro de saúde de Chiúre Velho");
        this.listOpcoesUnidadeSanitaria.add("Centro de saúde de Mmala");
        this.listOpcoesUnidadeSanitaria.add("Centro de saúde de Marera");
        this.listOpcoesUnidadeSanitaria.add("Centro de saúde de Mazeze");
        this.listOpcoesUnidadeSanitaria.add("Centro de saúde de Muege");
        this.listOpcoesUnidadeSanitaria.add("Centro de saúde de Nakoto");
        this.listOpcoesUnidadeSanitaria.add("Centro de saúde de Namogeliua");
        this.listOpcoesUnidadeSanitaria.add("Centro de saúde de Samora Machel");
        this.listOpcoesUnidadeSanitaria.add("Centro de saúde de Bilibiza");


    }

    public List<String> getListOpcoesUnidadeSanitaria() {
        return listOpcoesUnidadeSanitaria;
    }

    public List<String> getListMotivosTrasferencia() {
        return listMotivosTrasferencia;
    }

    private void initializeCamas() {
        for(int i=1;i<=100;i++){
            this.camas.add(i);
        }
    }

    private void initializeIdade() {
       for(int i=18;i<=50;i++){
           this.idades.add(i);
       }
    }

    public static List<Integer> getCamas() {
        return camas;
    }

    public static List<Integer> getIdades() {
        return idades;
    }

    private void initializeColor() {
        this.colors.add(Color.TRANSPARENT);
        this.colors.add(Color.CYAN);
    }

    public static List<Integer> getColors() {
        return colors;
    }

    public List<EmergencyMedicalPersonnel> getEmergencyMedicalPersonnels() {
        return emergencyMedicalPersonnels;
    }
    public void addEmergencyMedicalPersonnels(EmergencyMedicalPersonnel emergencyMedicalPersonnel){
        emergencyMedicalPersonnel.setId(++idEmergencyMedicalPersonnel);
        this.emergencyMedicalPersonnels.add(emergencyMedicalPersonnel);
}


    private void initializeEmergencyPersonnels(){ emergencyMedicalPersonnels.add(initializeEmergencyPersonnel1());
        emergencyMedicalPersonnels.add(initializeEmergencyPersonnel2());
        emergencyMedicalPersonnels.add(initializeEmergencyPersonnel3());
        emergencyMedicalPersonnels.add(initializeEmergencyPersonnel4());
    }

    public void addContact(EmergencyMedicalPersonnel emergencyMedicalPersonnel){
           this.emergencyMedicalPersonnels.add(emergencyMedicalPersonnel);
    }
    private EmergencyMedicalPersonnel initializeEmergencyPersonnel5(){
        return new EmergencyMedicalPersonnel("Rosario", "Ap", "845740722",1);
    }
    private EmergencyMedicalPersonnel initializeEmergencyPersonnel1(){
        return new EmergencyMedicalPersonnel("Saide", "Nilfero", "8492888772",2);
    }

    private EmergencyMedicalPersonnel initializeEmergencyPersonnel2(){
        return new EmergencyMedicalPersonnel("Felermino", "Rumbi", "8466896372",3);
    }

    private EmergencyMedicalPersonnel initializeEmergencyPersonnel3(){
        return new EmergencyMedicalPersonnel("Felermino", "Ali", "8642936522",4);
    }




    private EmergencyMedicalPersonnel initializeEmergencyPersonnel4(){

        return new EmergencyMedicalPersonnel("Ussimane", "Killer Wazy", "8477594222",5);
    }

//
//    private DeliveryService InitializeDeliveryService2() {
//        Parturient p = new Parturient("Ds", "Two", 20);
//        p.setId(2);
//        Calendar calendar = Calendar.getInstance();
//
//        Date current = calendar.getTime();
//        Measure measure = new Measure(current, 4);
//
//        calendar.add(Calendar.MINUTE, 5);
//        Date after = calendar.getTime();
//
//        DeliveryService ds = new DeliveryService(p,measure);
//
//        ds.setFireble(new FireMockAlert(after, ds));
//
//        return ds;
//    }

    public void initializeNotifications() {
//        initNotifications.add(initializeNotification1());
//        initNotifications.add(initializeNotification2());
    }

    public void addNewNotification(Notificacao notificacao){
        int cont =0;
        for(int i=0;i<notificacaos.size();i++){
            if(notificacaos.get(i).getIdAuxParturiente().equalsIgnoreCase(notificacao.getIdAuxParturiente())){
                cont++;
            }
        }
        if(cont==0){
            cont=0;
            notificacaos.add(notificacao);
        }

    }
    public void addNewParturiente(Parturient parturient){
        int cont =0;
        for(int i=0;i<parturients.size();i++){
            if(parturients.get(i).getIdAuxParturiente().equalsIgnoreCase(parturient.getIdAuxParturiente())){
                cont++;
            }
        }
        if(cont==0){
            cont=0;
            parturients.add(parturient);
        }

    }
    public List<Notificacao> getNotifications() {
        return notificacaos;
    }
    public List<Notificacao> getInitNotifications() {
        return initNotificacaos;
    }

    public List<Parturient> getParturients() {
        return parturients;
    }


}

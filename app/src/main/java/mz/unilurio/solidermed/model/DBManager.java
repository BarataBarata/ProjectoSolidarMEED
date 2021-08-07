package mz.unilurio.solidermed.model;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import mz.unilurio.solidermed.R;

public class DBManager {


    private static DBManager ourInstance = null;
    private List<SeacherUser> listSeacherUser=new ArrayList<>();
    private List<Parturient>listParturientesAtendidos=new ArrayList<>();
    private List<UserDoctor> userDoctorList=new ArrayList<>();
    private List<Settings> settingsList=new ArrayList<>();
    private List<Notification> initNotifications = new ArrayList<>();
    private List<String> listIdadeGestacional=new ArrayList<>();
    private List<Hospitais> hospitais= new ArrayList<>();
    private List<Notification> notifications = new ArrayList<>();
    private List<Parturient> parturients = new ArrayList<>();
    private List<String> listMotivosTrasferencia = new ArrayList<>();
    private List<String> listOpcoesUnidadeSanitaria = new ArrayList<>();
    private List<UserNurse>userNurseList=new ArrayList<>();
    static private List<Integer> colors = new ArrayList<Integer>();
    static private List<Integer> idades = new ArrayList<Integer>();
    static private List<Integer> camas = new ArrayList<Integer>();
    private static int idEmergencyMedicalPersonnel=5;

    private Queue queue=new Queue();
    private List<EmergencyMedicalPersonnel> emergencyMedicalPersonnels = new ArrayList<>();
    public int totalPaturient=0;
    private List<GestatinalRange> ranges;
    private List<DeliveryService> deliveryServices = new ArrayList<>();

    public static DBManager getInstance() {
        if(ourInstance == null) {
            ourInstance = new DBManager();
            ourInstance.initializeColor();
            ourInstance.initalizeListMotivosTrasferencia();
            ourInstance.initalizeListOpcoesUnidadeSanitaria ();
            ourInstance.initializeDoctor();
            ourInstance.initializeIdade();
            ourInstance.initializeCamas();
            ourInstance.initializeNotifications();
            //ourInstance.initializeParurientes();
            //ourInstance.initializeQueue();
            ourInstance.initializeSettings();
            ourInstance.initializeEmergencyPersonnels();
            ourInstance.initializeGestationalRanre();
            ourInstance.initHospitais();
            ourInstance.initializeInfermeira();
            ourInstance.initializeIdadeGestacional();
            ///ourInstance.initializeParturientesAtendidos();
        }
        return ourInstance;
    }

    private void initializeSettings() {
        this.settingsList.add(new Settings("Editar, Eliminar e Adicionar Contacto",1, R.drawable.notification_setting));
        this.settingsList.add(new Settings("Definicoes de Tempo de Alerta",2,R.drawable.timer_settings));
        this.settingsList.add(new Settings("Enfermeiras",3,R.drawable.nurses));
    }

    public List<Settings> getSettingsList() {
        return settingsList;
    }

    private void initializeParturientesAtendidos() {
        Parturient parturient=new Parturient();
        parturient.setName("Catia");
        parturient.setSurname("Fatima");
        parturient.setTime(new Date());
        parturient.setReason("4");
        parturient.setOrigemTransferencia("Centro de Chiure");
        parturient.setMotivosDaTrasferencia("Problema com o Feto");
        parturient.setPara(8);
        parturient.setId(1);
        parturient.setTransfered(true);
        parturient.setAge(35);
        addParturienteAtendido(parturient);
        Parturient parturient2=new Parturient();
        parturient2.setName("Julia");
        parturient2.setSurname("Amelia");
        parturient2.setTime(new Date());
        parturient2.setReason("8");
        parturient2.setPara(9);
        parturient2.setId(2);
        parturient2.setTransfered(false);
        parturient2.setAge(45);
        addParturienteAtendido(parturient2);
    }


    public List<SeacherUser> getListSeacherUser() {
        return listSeacherUser;
    }

    public void addSeacherUser(SeacherUser seacherUser){
        this.listSeacherUser.add(seacherUser);
    }

    public List<Parturient> getListParturientesAtendidos() {
        return listParturientesAtendidos;
    }

    public void addParturienteAtendido(Parturient parturient){
        this.listParturientesAtendidos.add(parturient);

    }

    private void initializeDoctor() {
        this.userDoctorList.add(new UserDoctor("Barata Estevao Mario","123",1,"845740722"));
        this.userDoctorList.add(new UserDoctor("Ussimane Killer Wazy","129",2,"847759422"));
        this.userDoctorList.add(new UserDoctor("Felermino Ali","128",3,"864293652"));
        this.userDoctorList.add(new UserDoctor("Felermino Rumbi","122",4,"846689637"));
        this.userDoctorList.add(new UserDoctor("Saide Nilfero","138",5,"849288877"));

    }

    private void initializeIdadeGestacional() {
             this.listIdadeGestacional.add("28 Semanas");
             this.listIdadeGestacional.add("entre 28 a 31 Semanas");
             this.listIdadeGestacional.add("entre 32 a 36 Semanas");
             this.listIdadeGestacional.add("36 Semanas");
    }

    public List<UserDoctor> getUserDoctorList() {
        return userDoctorList;
    }

    public List<String> getListIdadeGestacional() {
        return listIdadeGestacional;
    }

    private void initializeInfermeira() {
        this.userNurseList.add(new UserNurse("Salima","Sara","123","1","84574072"));
        this.userNurseList.add(new UserNurse("Fatima","Joao","124","2","84574072"));
        this.userNurseList.add(new UserNurse("Maria","Fito","124","3","84574072"));
        this.userNurseList.add(new UserNurse("Carla","Dario","128","4","84574072"));
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

    private void initializeParurientes() {
        Parturient parturient=new Parturient();
        parturient.setName("Catia");
        parturient.setSurname("Fatima");
        parturient.setTime(new Date());
        parturient.setReason("4");
        parturient.setOrigemTransferencia("Centro de Chiure");
        parturient.setMotivosDaTrasferencia("Mal posicao do Feto");
        parturient.setPara(8);
        parturient.setId(1);
        parturient.setTransfered(true);
        parturient.setAge(35);
        addQueueAndDeliveryService(parturient);

        //addQueueAndDeliveryService(parturient);
//        Parturient parturient2=new Parturient();
//        parturient2.setName("Julia");
//        parturient2.setSurname("Amelia");
//        parturient2.setTime(new Date());
//        parturient2.setReason("8");
//        parturient2.setPara(9);
//        parturient2.setHoraEntrada(new Date());
//        parturient2.setHoraAlerta(new Date());
//        parturient2.setId(2);
//        parturient2.setTransfered(false);
//        parturient2.setAge(45);
//        addParturiente(parturient2);
//        Parturient parturient3=new Parturient();
//        parturient3.setName("kusia");
//        parturient3.setSurname("Mira");
//        parturient3.setTime(new Date());
//        parturient3.setReason("8");
//        parturient3.setPara(9);
//        parturient3.setHoraEntrada(new Date());
//        parturient3.setHoraAlerta(new Date());
//        parturient3.setId(3);
//        parturient3.setTransfered(false);
//        parturient3.setAge(45);
//        addParturiente(parturient3);
//        Parturient parturient4=new Parturient();
//        parturient4.setName("Maria");
//        parturient4.setSurname("Mira");
//        parturient4.setTime(new Date());
//        parturient4.setReason("8");
//        parturient4.setPara(9);
//        parturient4.setHoraEntrada(new Date());
//        parturient4.setHoraAlerta(new Date());
//        parturient4.setId(4);
//        parturient4.setTransfered(false);
//        parturient4.setAge(45);
//        addParturiente(parturient4);
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
//        this.colors.add(Color.DKGRAY);
//        this.colors.add(Color.GREEN);
//        this.colors.add(Color.YELLOW);
//        this.colors.add(Color.LTGRAY);
//        this.colors.add(Color.RED);
//        this.colors.add(Color.BLACK);
//        this.colors.add(Color.MAGENTA);
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
        return new EmergencyMedicalPersonnel("Saide", "Nilfero", "849288877",2);
    }

    private EmergencyMedicalPersonnel initializeEmergencyPersonnel2(){
        return new EmergencyMedicalPersonnel("Felermino", "Rumbi", "846689637",3);
    }

    private EmergencyMedicalPersonnel initializeEmergencyPersonnel3(){
        return new EmergencyMedicalPersonnel("Felermino", "Ali", "864293652",4);
    }




    private EmergencyMedicalPersonnel initializeEmergencyPersonnel4(){

        return new EmergencyMedicalPersonnel("Ussimane", "Killer Wazy", "847759422",5);
    }

    public  void addQueueAndDeliveryService(Parturient parturient){
        DeliveryService ds;
        ds=InitializeDeliveryService(parturient, (int)Float.parseFloat(parturient.getReason()));
        this.parturients.add(parturient);
        deliveryServices.add(ds);
        ds.setFireble(new FireMockAlert(null, ds));
        queue.register(ds);
    }

    private void initializeQueue(){

        for (Parturient p: getParturients()){
            deliveryServices.add(InitializeDeliveryService(p, Integer.parseInt(p.getReason())));
        }

//        Queue queue = new Queue();
//
//        for (DeliveryService ds: deliveryServices){
////            Calendar calendar = Calendar.getInstance();
////            Date current = calendar.getTime();
////            Measure measure = new Measure(current, 4);
////            calendar.add(Calendar.MINUTE, 5);
////            Date after = calendar.getTime();
////
////            ds.setFireble(new FireMockAlert(after, ds));
//            queue.register(ds);
//        }
//        this.queue = queue;
    }

    public void updateQueue(int dilatation){
//
//        Parturient p = this.getParturients().get(totalPaturient-2);
//        System.out.println(p + "ïndex ---------------------------"+this.totalPaturient);
//        deliveryServices.add(InitializeDeliveryService(p, dilatation));
//        System.out.println();
//
//        Queue queue = new Queue();
//
//        for (DeliveryService ds: deliveryServices){
//            Calendar calendar = Calendar.getInstance();
//            Date current = calendar.getTime();
//            Measure measure = new Measure(current, dilatation);
//            calendar.add(Calendar.MINUTE, 5);
//            Date after = calendar.getTime();
//
//            ds.setFireble(new FireMockAlert(after, ds));
//            queue.register(ds);
//        }
//        this.queue = queue;
    }

    private DeliveryService InitializeDeliveryService(Parturient p, int dilatation) {
        Date current = Calendar.getInstance().getTime();
        Measure measure = new Measure(current, dilatation);
        p.setHoraEntrada(current);
        p.setHoraAlerta(measure.getPredictedExpulsionHour());
        return new DeliveryService(p, measure);
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

    public void addNewNotification(List<Notification >notification){
        notifications.addAll(notification);
    }

    public List<Notification> getNotifications() {
        return notifications;
    }

    public Queue getQueue(){
        return this.queue;
    }
    public List<Notification> getInitNotifications() {
        return initNotifications;
    }

    public List<Notification> getEmptyNotifications() {
        return new ArrayList<>();
    }

    private Notification initializeNotification1(){

        Parturient bibo = new Parturient("Bibo", "Bubu", 20);
        Date current = Calendar.getInstance().getTime();
        Measure measure = new Measure(current, 4);
        DeliveryService ds = new DeliveryService(bibo,measure);

        Notification notification = new Notification(Color.rgb(248, 215,218), "Message ...", Calendar.getInstance().getTime(), true, ds);
        return notification;
    }

    private Notification initializeNotification2(){

        Parturient bibo = new Parturient("Jula", "Xtimeka", 23);
        Date current = Calendar.getInstance().getTime();
        Measure measure = new Measure(current, 2);
        DeliveryService ds = new DeliveryService(bibo,measure);

        Notification notification = new Notification(Color.rgb(248, 215,218), "Message ...", Calendar.getInstance().getTime(), true, ds);
        return notification;
    }

    public List<Parturient> getParturients() {
        return parturients;
    }

    public int getTotalPaturient(){
        return totalPaturient;
    }

    private List<GestatinalRange> initializeGestationalRanre() {
        ranges = new ArrayList<>();
        ranges.add(GestatinalRange.MENORQUE28SEMANAS);
        ranges.add(GestatinalRange.ENTRE28E31SEMANAS);
        ranges.add(GestatinalRange.ENTRE32E36SEMANAS);
        ranges.add(GestatinalRange.MAIORQUE37SEMANAS);
        return ranges;
    }

    public List<GestatinalRange> getGestatinalRange(){
        return this.ranges;
    }
}

package mz.unilurio.solidermed.model;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DBManager {


    private static DBManager ourInstance = null;
    private List<Parturient>listParturientesAtendidos=new ArrayList<>();
    private List<UserDoctor> userDoctorList=new ArrayList<>();
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

    private Queue queue;
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
            ourInstance.initializeParurientes();
            ourInstance.initializeQueue();
            ourInstance.initializeEmergencyPersonnels();
            ourInstance.initializeGestationalRanre();
            ourInstance.initHospitais();
            ourInstance.initializeInfermeira();
            ourInstance.initializeIdadeGestacional();
            ourInstance.initializeParturientesAtendidos();
        }
        return ourInstance;
    }

    private void initializeParturientesAtendidos() {
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
        addParturienteAtendido(parturient);
        Parturient parturient2=new Parturient();
        parturient2.setName("Julia");
        parturient2.setSurname("Amelia");
        parturient2.setTime(new Date());
        parturient2.setReason("8");
        parturient2.setPara(9);
        parturient2.setId(0);
        parturient2.setTransfered(false);
        parturient2.setAge(45);
        addParturienteAtendido(parturient2);
    }

    public List<Parturient> getListParturientesAtendidos() {
        return listParturientesAtendidos;
    }

    public void addParturienteAtendido(Parturient parturient){
        this.listParturientesAtendidos.add(parturient);
    }

    private void initializeDoctor() {
        this.userDoctorList.add(new UserDoctor("mario","1234",1));
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
        this.userNurseList.add(new UserNurse("Salima","123","1"));
        this.userNurseList.add(new UserNurse("Fatima","124","2"));
        this.userNurseList.add(new UserNurse("Maria","124","1"));
        this.userNurseList.add(new UserNurse("Carla","128","2"));
    }

    public List<UserNurse> getUserNurseList() {
        return userNurseList;
    }

    private void initHospitais() {
        Hospitais hospitais=new Hospitais();

        this.hospitais.add(new Hospitais("Centro de Saude de Resta","1"));
        this.hospitais.add(new Hospitais("Centro de Saude de Restawanha","1"));
        this.hospitais.add(new Hospitais("Centro de Saude de Namina","1"));
        this.hospitais.add(new Hospitais("Centro de Saude de Congo","1"));
        this.hospitais.add(new Hospitais("Centro de Saude de Rex","1"));
        this.hospitais.add(new Hospitais("Centro de Saude de Macomia","1"));
        this.hospitais.add(new Hospitais("Centro de Saude de Rast","1"));
        this.hospitais.add(new Hospitais("Centro de Saude de Reslp","1"));

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
       addParturiente(parturient);
        Parturient parturient2=new Parturient();
        parturient2.setName("Julia");
        parturient2.setSurname("Amelia");
        parturient2.setTime(new Date());
        parturient2.setReason("8");
        parturient2.setPara(9);
        parturient2.setId(0);
        parturient2.setTransfered(false);
        parturient2.setAge(45);
        addParturiente(parturient2);
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
        this.colors.add(Color.DKGRAY);
        this.colors.add(Color.GREEN);
        this.colors.add(Color.YELLOW);
        this.colors.add(Color.LTGRAY);
        this.colors.add(Color.RED);
        this.colors.add(Color.BLACK);
        this.colors.add(Color.MAGENTA);
    }

    public static List<Integer> getColors() {
        return colors;
    }

    public List<EmergencyMedicalPersonnel> getEmergencyMedicalPersonnels() {
        return emergencyMedicalPersonnels;
    }

    private void initializeEmergencyPersonnels(){
//        emergencyMedicalPersonnels.add(initializeEmergencyPersonnel1());
        emergencyMedicalPersonnels.add(initializeEmergencyPersonnel2());
        emergencyMedicalPersonnels.add(initializeEmergencyPersonnel3());
//        emergencyMedicalPersonnels.add(initializeEmergencyPersonnel4());
    }

    private EmergencyMedicalPersonnel initializeEmergencyPersonnel5(){
        return new EmergencyMedicalPersonnel("Rosario", "Ap", "845740722");
    }
    private EmergencyMedicalPersonnel initializeEmergencyPersonnel1(){
        return new EmergencyMedicalPersonnel("Saide", "Nilfero", "849288877");
    }

    private EmergencyMedicalPersonnel initializeEmergencyPersonnel2(){
        return new EmergencyMedicalPersonnel("Felermino", "Rumbi", "846689637");
    }

    private EmergencyMedicalPersonnel initializeEmergencyPersonnel3(){
        return new EmergencyMedicalPersonnel("Felermino", "Ali", "864293652");
    }

    private EmergencyMedicalPersonnel initializeEmergencyPersonnel4(){

        return new EmergencyMedicalPersonnel("Ussimane", "Killer Wazy", "847759422");
    }

    private void initializeQueue(){

        for (Parturient p: getParturients()){
            deliveryServices.add(InitializeDeliveryService(p, 6));
        }

        Queue queue = new Queue();

        for (DeliveryService ds: deliveryServices){
//            Calendar calendar = Calendar.getInstance();
//            Date current = calendar.getTime();
//            Measure measure = new Measure(current, 4);
//            calendar.add(Calendar.MINUTE, 5);
//            Date after = calendar.getTime();
//
//            ds.setFireble(new FireMockAlert(after, ds));
            queue.register(ds);
        }
        this.queue = queue;
    }

    public void updateQueue(int dilatation){

        Parturient p = this.getParturients().get(totalPaturient-2);
        System.out.println(p + "ïndex ---------------------------"+this.totalPaturient);
        deliveryServices.add(InitializeDeliveryService(p, dilatation));
        System.out.println();

        Queue queue = new Queue();

        for (DeliveryService ds: deliveryServices){
            Calendar calendar = Calendar.getInstance();
            Date current = calendar.getTime();
            Measure measure = new Measure(current, dilatation);
            calendar.add(Calendar.MINUTE, 5);
            Date after = calendar.getTime();

            ds.setFireble(new FireMockAlert(after, ds));
            queue.register(ds);
        }
        this.queue = queue;
    }

    private DeliveryService InitializeDeliveryService(Parturient p, int dilatation) {
        Date current = Calendar.getInstance().getTime();
        Measure measure = new Measure(current, dilatation);
        return new DeliveryService(p, measure);
    }

    private DeliveryService InitializeDeliveryService2() {
        Parturient p = new Parturient("Ds", "Two", 20);
        p.setId(2);
        Calendar calendar = Calendar.getInstance();

        Date current = calendar.getTime();
        Measure measure = new Measure(current, 4);

        calendar.add(Calendar.MINUTE, 5);
        Date after = calendar.getTime();

        DeliveryService ds = new DeliveryService(p,measure);

        ds.setFireble(new FireMockAlert(after, ds));

        return ds;
    }

    public void initializeNotifications() {
        initNotifications.add(initializeNotification1());
        initNotifications.add(initializeNotification2());
    }

    public void addNewNotification(Notification notification){
        notifications.add(notification);
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

    public void addParturiente(Parturient parturient){
            this.parturients.add(parturient);
            totalPaturient++;
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

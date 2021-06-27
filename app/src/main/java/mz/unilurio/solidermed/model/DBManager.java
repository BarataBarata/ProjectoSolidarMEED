package mz.unilurio.solidermed.model;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DBManager {

    private static DBManager ourInstance = null;

    private List<Notification> notifications = new ArrayList<>();
    static private List<Parturient> parturients = new ArrayList<>();
    static private List<Integer> colors = new ArrayList<Integer>();
    static private List<Integer> idades = new ArrayList<Integer>();
    static private List<Integer> camas = new ArrayList<Integer>();
    private Queue queue;
    private List<EmergencyMedicalPersonnel> emergencyMedicalPersonnels = new ArrayList<>();

    public static DBManager getInstance() {
        if(ourInstance == null) {
            ourInstance = new DBManager();
            ourInstance.initializeColor();
            ourInstance.initializeIdade();
            ourInstance.initializeCamas();
            ourInstance.initializeNotifications();
            ourInstance.initializeQueue();
            ourInstance.initializeEmergencyPersonnels();
        }
        return ourInstance;
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
    }

    private EmergencyMedicalPersonnel initializeEmergencyPersonnel1(){
        return new EmergencyMedicalPersonnel("Saide", "Saide", "849288877");
    }

    private EmergencyMedicalPersonnel initializeEmergencyPersonnel2(){
        return new EmergencyMedicalPersonnel("Felermino", "Ali", "846689637");
    }

    private EmergencyMedicalPersonnel initializeEmergencyPersonnel3(){
        return new EmergencyMedicalPersonnel("Felermino", "Ali", "864293652");
    }

    private void initializeQueue(){
        DeliveryService ds1 = InitializeDeliveryService1();
        DeliveryService ds2 = InitializeDeliveryService2();

        Queue queue = new Queue();
        queue.register(ds1);
        queue.register(ds2);

        this.queue = queue;
    }


    private DeliveryService InitializeDeliveryService1() {
        Parturient p = new Parturient("Ds", "One'", 18);
        p.setId(1);
        Date current = Calendar.getInstance().getTime();
        Measure measure = new Measure(current, 6);
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

    private void initializeNotifications() {
        notifications.add(initializeNotification1());
        notifications.add(initializeNotification2());

    }

    public Queue getQueue(){
        return this.queue;
    }
    public List<Notification> getNotifications() {
        return notifications;
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

    public void addParturiente(String name, String surname, int age, boolean isTransfered, String reason, Date time, int numeroCama){
            this.parturients.add(new Parturient(name,surname,age,isTransfered,reason,time,numeroCama));
    }

    public List<Parturient> getParturients() {
        return parturients;
    }


}

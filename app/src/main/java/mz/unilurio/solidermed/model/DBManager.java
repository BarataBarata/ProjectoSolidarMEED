package mz.unilurio.solidermed.model;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import mz.unilurio.solidermed.CourseInfo;
import mz.unilurio.solidermed.ModuleInfo;

public class DBManager {

    private static DBManager ourInstance = null;

    private List<Notification> notifications = new ArrayList<>();
    private List<Parturient> parturients = new ArrayList<>();
    static private List<Integer> colors = new ArrayList<Integer>();
    static private List<Integer> idades = new ArrayList<Integer>();
    static private List<Integer> camas = new ArrayList<Integer>();
    private Queue queue;
    private List<EmergencyMedicalPersonnel> emergencyMedicalPersonnels = new ArrayList<>();
    private int totalPaturient=1;
    private List<GestatinalRange> ranges;


    public static DBManager getInstance() {
        if(ourInstance == null) {
            ourInstance = new DBManager();
            ourInstance.initializeColor();
            ourInstance.initializeIdade();
            ourInstance.initializeCamas();
            ourInstance.initializeNotifications();
            ourInstance.initializeParurientes();
            ourInstance.initializeQueue();
            ourInstance.initializeEmergencyPersonnels();
            ourInstance.initializeGestationalRanre();
        }
        return ourInstance;
    }

    private void initializeParurientes() {
       addParturiente(new Parturient(totalPaturient, "Ds", "Two", 20));
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
        List<DeliveryService> list = new ArrayList<>();

        for (Parturient p: getParturients()){
            list.add(InitializeDeliveryService(p, 6));
        }

        Queue queue = new Queue();

        for (DeliveryService ds: list){

            Calendar calendar = Calendar.getInstance();
            Date current = calendar.getTime();
            Measure measure = new Measure(current, 4);
            calendar.add(Calendar.MINUTE, 5);
            Date after = calendar.getTime();

            ds.setFireble(new FireMockAlert(after, ds));
            queue.register(ds);
        }
        this.queue = queue;
    }

    public void updateQueue(int dilatation){
        List<DeliveryService> list = new ArrayList<>();

        for (Parturient p: getParturients()){
            list.add(InitializeDeliveryService(p, dilatation));
        }

        Queue queue = new Queue();

        for (DeliveryService ds: list){

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

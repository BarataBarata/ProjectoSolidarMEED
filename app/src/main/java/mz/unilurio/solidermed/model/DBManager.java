package mz.unilurio.solidermed.model;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DBManager {

    private static DBManager ourInstance = null;

    private List<Notification> notifications = new ArrayList<>();
    private Queue queue;
    private List<EmergencyMedicalPersonnel> emergencyMedicalPersonnels = new ArrayList<>();

    public static DBManager getInstance() {
        if(ourInstance == null) {
            ourInstance = new DBManager();
            ourInstance.initializeNotifications();
            ourInstance.initializeQueue();
            ourInstance.initializeEmergencyPersonnels();

        }
        return ourInstance;
    }

    public List<EmergencyMedicalPersonnel> getEmergencyMedicalPersonnels() {
        return emergencyMedicalPersonnels;
    }

    private void initializeEmergencyPersonnels(){
        emergencyMedicalPersonnels.add(initializeEmergencyPersonnel1());
        emergencyMedicalPersonnels.add(initializeEmergencyPersonnel2());
    }

    private EmergencyMedicalPersonnel initializeEmergencyPersonnel1(){
        return new EmergencyMedicalPersonnel("Saide", "Saide", "849288877");
    }

    private EmergencyMedicalPersonnel initializeEmergencyPersonnel2(){
        return new EmergencyMedicalPersonnel("Felermino", "Ali", "846689637");
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
}

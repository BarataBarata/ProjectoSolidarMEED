package mz.unilurio.solidermed.model;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Queue {
    private List<Observer> observers = new ArrayList<>();
    private List<Notification> notifications = new ArrayList<>();

    public void register(Observer observer){
        observers.add(observer);
    }

    public void unregister(Observer observer){
        observers.remove(observer);
    }

    public void nofify(){

        this.notifications = new ArrayList<>();
        for (Observer ob:observers){
            if(ob.fireAlert()){
//                Notification notification = new Notification();
//                notification.setColour(Color.rgb(248, 215,218));
//                Parturient p = ((DeliveryService) ob).getParturient();
//                notification.setNome(p.getName()+" "+p.getSurname());
//                notification.setTime( Calendar.getInstance().getTime());
//                notification.setOpen(true);
//                notification.setDeliveryService((DeliveryService) ob);
//                Calendar calendar = Calendar.getInstance();
////                calendar.add(Calendar.MINUTE, Notification.FIVE_INTERVAL);
//                calendar.add(Calendar.MINUTE, Notification.TWO_INTERVAL);
//                notification.setNextNotifier(calendar.getTime());
////                notification
//                this.notifications.add(notification);
                ((DeliveryService) ob).setExpulsed(true);
            }
        }

    }

    public List<Notification> getNotifications() {
        return notifications;
    }
}

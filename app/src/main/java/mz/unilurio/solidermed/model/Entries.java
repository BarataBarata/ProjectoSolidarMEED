package mz.unilurio.solidermed.model;

import java.util.ArrayList;
import java.util.List;

public class Entries {

    public List<Observer> observers = new ArrayList<>();

    public void register(Observer observer){
        observers.add(observer);
    }

    public void unregister(Observer observer){
        observers.remove(observer);
    }

    public void nofify(){
        for (Observer ob:observers) {
            if(ob.fireAlert()){
                Notification notification = new Notification();
                System.out.println("Disparou alerta!");
            }
        }
    }
}

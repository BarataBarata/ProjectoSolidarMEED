package mz.unilurio.solidermed.model;

import java.util.ArrayList;
import java.util.List;

public class Queue {

    private List<Observer> observers = new ArrayList<>();

    public void register(Observer observer){
        observers.add(observer);
    }

    public void unregister(Observer observer){
        observers.remove(observer);
    }

    public boolean nofify(){

        for (Observer ob:observers) {
//            System.out.println(ob.getMeasure().peek());
            if(ob.fireAlert()){

                Notification notification = new Notification();
                return true;
            }else{
                System.out.println("No notification");
            }
        }
        return false;
    }
}

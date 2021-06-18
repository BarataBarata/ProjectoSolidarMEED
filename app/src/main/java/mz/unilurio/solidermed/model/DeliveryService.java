package mz.unilurio.solidermed.model;

import java.util.Calendar;
import java.util.Date;
import java.util.Stack;

public class DeliveryService implements Observer {
    private int id;
    private Parturient parturient;
    private Stack<Measure> measure;
    private boolean isExpulsed;
    private Fireble fireble;

    public DeliveryService(){}

    public DeliveryService(Parturient parturient, Measure measure) {
        this.parturient = parturient;
        this.measure = new Stack<>();
        this.measure.push(measure);
        fireble = new FireAlert(this);
    }

    public void updateCurrentTime(Date date){
        this.measure.peek().setInitialHour(date);

    }

    @Override
    public boolean fireAlert() {
        return fireble.fire();
    }

    public Fireble getFireble() {
        return fireble;
    }

    public void setFireble(Fireble fireble) {
        this.fireble = fireble;
    }

    public Parturient getParturient() {
        return parturient;
    }

    public void setParturient(Parturient parturient) {
        this.parturient = parturient;
    }

    public Stack<Measure> getMeasure() {
        return measure;
    }

    public void setMeasure(Stack<Measure> measure) {
        this.measure = measure;
    }

    public boolean isExpulsed() {
        return isExpulsed;
    }

    public void setExpulsed(boolean expulsed) {
        isExpulsed = expulsed;
    }
}

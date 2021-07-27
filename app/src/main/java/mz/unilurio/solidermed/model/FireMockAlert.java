package mz.unilurio.solidermed.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class FireMockAlert implements Fireble{
    private Date currentHour;
    private DeliveryService ds;

    public FireMockAlert(Date currentHour, DeliveryService ds){
        this.currentHour = currentHour;
        this.ds = ds;
    }
    @Override
    public boolean fire() {
        Measure measure = this.ds.getMeasure().peek();
        if((Calendar.getInstance().getTime()+"").equals(measure.getPredictedExpulsionHour()+"") && !ds.isExpulsed()){
            return true;
        }
        return false;
    }
    private String format(Date date){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        return dateFormat.format(date);
    }
}

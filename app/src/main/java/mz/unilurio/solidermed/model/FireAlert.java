package mz.unilurio.solidermed.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class FireAlert implements Fireble{
    private DeliveryService ds;

    public FireAlert(DeliveryService ds){
        this.ds = ds;
    }
    @Override
    public boolean fire() {
        Measure measure = this.ds.getMeasure().peek();
        Date currentHour = Calendar.getInstance().getTime();
        // ver igual tambem
        if(currentHour.after(measure.getPredictedExpulsionHour()) && !ds.isExpulsed()){
         return true;
        }
        return false;
    }
}

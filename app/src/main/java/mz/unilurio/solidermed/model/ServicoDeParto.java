package mz.unilurio.solidermed.model;

import java.util.Calendar;
import java.util.Date;
import java.util.Stack;

public class ServicoDeParto implements Observer {
    private int id;
    private Parturiente parturiente;
    private Stack<Measures> measures;
    private boolean isExpulsed;

    @Override
    public boolean fireAlert() {
        Measures measure = this.measures.peek();
        Date currentHour = Calendar.getInstance().getTime();
        // ver igual tambem
        if(currentHour.after(measure.getPredictedExpulsionHour()) && isExpulsed){
            return true;
        }
        return false;
    }
}

package mz.unilurio.solidermed.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Calendar;
import java.util.Date;
import java.util.Stack;

public class DeliveryService implements Observer, Parcelable {
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

    protected DeliveryService(Parcel in) {
        id = in.readInt();
        isExpulsed = in.readByte() != 0;
    }

    public static final Creator<DeliveryService> CREATOR = new Creator<DeliveryService>() {
        @Override
        public DeliveryService createFromParcel(Parcel in) {
            return new DeliveryService(in);
        }

        @Override
        public DeliveryService[] newArray(int size) {
            return new DeliveryService[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(parturient, 0);
        //dest.writeParcelable(fireble, 0);
        //dest.writeParcelable(measure, 0);
        dest.writeInt(id);
        dest.writeByte((byte) (isExpulsed ? 1 : 0));
    }
}

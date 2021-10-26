package mz.unilurio.solidermed.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Calendar;
import java.util.Date;

public final class Notification implements Parcelable {
    public static final int TWO_INTERVAL = 1;
    private boolean inProcess;
    private String id;
    private int color;
    private String message;
    private String time;
    private boolean isOpen;
    private DeliveryService deliveryService;
    private Date nextNotifier;

    public Notification(){

    }

    public Notification(int color, String message, String time, boolean isOpen, DeliveryService deliveryService) {
        this.color = color;
        this.message = message;
        this.time = time;
        this.isOpen = isOpen;
        this.deliveryService = deliveryService;
        this.id = getId();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, Notification.TWO_INTERVAL);
        this.nextNotifier = calendar.getTime();
    }

    protected Notification(Parcel in) {
        id = in.readString();
        message = in.readString();
        isOpen = in.readByte() != 0;
    }

    public void setId(String id) {
        this.id = id;
    }

    public static final Creator<Notification> CREATOR = new Creator<Notification>() {
        @Override
        public Notification createFromParcel(Parcel in) {
            return new Notification(in);
        }

        @Override
        public Notification[] newArray(int size) {
            return new Notification[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    public int getColour() {
        return color;
    }

    public void setColour(int color) {
        this.color = color;
    }

    public boolean isInProcess() {
        return inProcess;
    }

    public void setInProcess(boolean inProcess) {
        this.inProcess = inProcess;
    }

    public String getMessage() {
        return message;
    }

    public void setNome(String message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public DeliveryService getDeliveryService() {
        return deliveryService;
    }

    public void setDeliveryService(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    public String getId() {
        return this.id;
    }

    public Date getNextNotifier() {
        return nextNotifier;
    }

    public void setNextNotifier(Date nextNotifier) {
        this.nextNotifier = nextNotifier;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeParcelable(deliveryService, 0);
        dest.writeString(message);
        dest.writeInt(color);
        dest.writeByte((byte) (isOpen ? 1 : 0));
    }
}

package mz.unilurio.solidermed.model;

import android.graphics.Color;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Calendar;
import java.util.Date;

public final class Notification implements Parcelable {
    public static final int TWO_INTERVAL = 2;
    public static final int FIVE_INTERVAL = 5;
    public static final int TEN_INTERVAL = 10;
    private String id;
    private int color;
    private String message;
    private Date time;
    private boolean isOpen;
    private DeliveryService deliveryService;
    private Date nextNotifier;

    public Notification(){

    }

    public Notification(int color, String message, Date time, boolean isOpen, DeliveryService deliveryService) {
        this.color = color;
        this.message = message;
        this.time = time;
        this.isOpen = isOpen;
        this.deliveryService = deliveryService;
        this.id = getId();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, Notification.FIVE_INTERVAL);
        this.nextNotifier = calendar.getTime();
    }

    protected Notification(Parcel in) {
        id = in.readString();
        message = in.readString();
        isOpen = in.readByte() != 0;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
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
        return this.deliveryService.getParturient().getId() + ""+ (""+color).replace("-", "");
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

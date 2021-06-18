package mz.unilurio.solidermed.model;

import java.util.Date;

public class Notification {
    private Colour colour;
    private String message;
    private Date time;
    private boolean isOpen;
    private DeliveryService deliveryService;

    public Notification(){
        System.out.println(" ---------- Alert Fired! ------------");
    }
}

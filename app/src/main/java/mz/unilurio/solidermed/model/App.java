package mz.unilurio.solidermed.model;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class App extends Application {

    public static final  String CHANNEL_1_ID="channel_1";
    public static final  String CHANNEL_2_ID="channel_2";

    @Override
    public void onCreate() {
        super.onCreate();
        createNotification();
    }

    private void createNotification() {

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel channel=new NotificationChannel(
                    CHANNEL_1_ID,
                    "Channel_1",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel.setDescription(" THIS IS CHANNEL_1");

            NotificationChannel channe2=new NotificationChannel(
                    CHANNEL_2_ID,
                    "Channel_2",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channe2.setDescription(" THIS IS CHANNEL_2");

            NotificationManager manager=getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
            manager.createNotificationChannel(channe2);
        }
    }
}

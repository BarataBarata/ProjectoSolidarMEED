package mz.unilurio.solidermed;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;

public class StartApp extends AppCompatActivity {
    static boolean start=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_app);



        new CountDownTimer(1 * 1000 + 1000, 1000) {

            public void onTick(long millisUntilFinished) {
                int seconds = (int) (millisUntilFinished / 1000);

                int hours = seconds / (60 * 60);
                int tempMint = (seconds - (hours * 60 * 60));
                int minutes = tempMint / 60;
                seconds = tempMint - (minutes * 60);

//                setTempoRes("Tempo Restante : " + String.format("%02d", hours)
//                        + ":" + String.format("%02d", minutes)
//                        + ":" + String.format("%02d", seconds));
                System.out.println(seconds);
            }

            public void onFinish() {
                // tv.setText("Completed");
                startActivity(new Intent(StartApp.this,Login.class));
            }
        }.start();


    }

    @Override
    protected void onResume() {
        super.onResume();
        if(start){
            System.out.println(" ola ola");
            finish();
        }
    }
}
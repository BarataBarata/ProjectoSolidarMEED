package mz.unilurio.solidermed;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Toast;

import mz.unilurio.solidermed.model.DBService;

public class StartApp extends AppCompatActivity {
    static boolean start=false;
    static boolean startDow=false;
    private DBService dbService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_app);

        dbService=new DBService(this);
        dbService.initializeListParturientesAtendidos();
        dbService.initializeListParturiente();
        dbService.initializeListNotification();
        //dbService.updadeListNotification();


        if(start){
            finish();
            start=false;
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(start){
            finish();
            start=false;
            startDow=true;
        }else {
            if(startDow){
                startActivity(new Intent(StartApp.this,Login.class));
            }else {
                new CountDownTimer(1 * 1000 + 1000, 1000) {

                    public void onTick(long millisUntilFinished) {
                        int seconds = (int) (millisUntilFinished / 1000);

                        int hours = seconds / (60 * 60);
                        int tempMint = (seconds - (hours * 60 * 60));
                        int minutes = tempMint / 60;
                        seconds = tempMint - (minutes * 60);
                    }

                    public void onFinish() {
                        // tv.setText("Completed");
                        startActivity(new Intent(StartApp.this,Login.class));
                    }
                }.start();
            }
            }

    }

    @Override
    protected void onPause() {
        SelectHospitalActivity selectHospitalActivity=new SelectHospitalActivity();
        selectHospitalActivity.start=true;
        super.onPause();
    }
}
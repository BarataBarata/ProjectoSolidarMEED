package mz.unilurio.solidermed;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;

import mz.unilurio.solidermed.model.DBService;

public class StartApp extends AppCompatActivity {
    static boolean start=false;
    static boolean startDow=false;
    private DBService dbService;
    ProgressBar progressBar;
    TextView textCont;
    Handler handler=new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_app);
        progressBar=findViewById(R.id.progress);
        textCont=findViewById(R.id.cont);

        dbService=new DBService(this);
        dbService.initializeListParturientesAtendidos();
        try {
            dbService.initializeListParturiente();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            dbService.initializeListNotification();
        } catch (ParseException e) {
            e.printStackTrace();
        }
       // dbService.initializeListParturientesTransferidos();
        //dbService.updadeListNotification();
        if(start){
            finish();
            start=false;
        }
    }

    private void startProgress() throws InterruptedException {
        for(int i=1;i<=100;i++){

            Thread.sleep(10);
            progressBar.setProgress(i);
            int finalI = i;
            handler.post(new Runnable() {
                @Override
                public void run() {
                    textCont.setText(""+ finalI +"%");
                }
            });
        }
        if(dbService.getSessaoTerminada()){
                            startActivity(new Intent(StartApp.this,Login.class));
                        }else{
                            startActivity(new Intent(StartApp.this,MainActivity.class));
                        }

    }


    @Override
    protected void onResume() {
        super.onResume();
        if (start) {
            finish();
            start = false;
            startDow = true;
        } else {

            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        startProgress();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            });
            thread.start();
////            if(startDow){
////                startActivity(new Intent(StartApp.this,Login.class));
////            }else {
//                new CountDownTimer(1 * 1000 + 1000, 1000) {
//
//                    public void onTick(long millisUntilFinished) {
//                        int seconds = (int) (millisUntilFinished / 1000);
//
//                        int hours = seconds / (60 * 60);
//                        int tempMint = (seconds - (hours * 60 * 60));
//                        int minutes = tempMint / 60;
//                        seconds = tempMint - (minutes * 60);
//                    }
//
//                    public void onFinish() {
//                        // tv.setText("Completed");
//
//                    }
//                }.start();
//              }
        }
    }

    @Override
    protected void onPause() {
        SelectHospitalActivity selectHospitalActivity=new SelectHospitalActivity();
        selectHospitalActivity.start=true;
        super.onPause();
    }
}
package mz.unilurio.solidermed;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;

public class StartApp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_app);

        new CountDownTimer(1 * 1000 + 1000, 1000) {

            public void onTick(long millisUntilFinished) {

            }

            public void onFinish() {
                // tv.setText("Completed");
                startActivity(new Intent(StartApp.this,Login.class));
            }
        }.start();


    }
}
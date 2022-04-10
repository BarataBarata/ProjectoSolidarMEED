package mz.unilurio.solidermed;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

import java.security.NoSuchAlgorithmException;
import java.util.Timer;
import java.util.TimerTask;

import mz.unilurio.solidermed.model.DBManager;
import mz.unilurio.solidermed.model.DBService;
import mz.unilurio.solidermed.model.UserDoctor;
import mz.unilurio.solidermed.model.UserNurse;

public class ActivityChengePassword extends AppCompatActivity {
    private TextInputLayout novaSenha;
    private boolean isTrueConfirmation=false;
    private EditText user;
    private TextInputLayout confirmarSenha;
    private TextView textAlertaSenha;
    private Handler handler;
    private TimerTask task;
    private Timer timer;
    private String numberSeacher;
    private String newPassword;
    private String chengeuser;
    private DBService dbService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chenge_password);
        dbService=new DBService(this);
        novaSenha=findViewById(R.id.idNovaSenha);
        confirmarSenha=findViewById(R.id.idConfirmarSenha);


        if(getIntent().getStringExtra("numberSeacher")!=null){
            numberSeacher = getIntent().getStringExtra("numberSeacher");
        }
    }

    public void trocarPassword(View view) {
        if(novaSenha.getEditText().getText().toString().equals("") || confirmarSenha.getEditText().getText().toString().equals("")){
            textAlertaSenha.setText(" campo vazio");
        }else{
            if(isTrueConfirmation){
                progressBar();
            }else {
                confirmarSenha.setError("Diferente ...");
            }

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        update();
    }

    @Override
    protected void onPause() {
        super.onPause();
        task.cancel();
        timer.cancel();
    }

    public void verificationPasswordChenge(){
        if(!novaSenha.getEditText().getText().toString().equals("") && !confirmarSenha.getEditText().getText().toString().equals("")){
            if (novaSenha.getEditText().getText().toString().equals(confirmarSenha.getEditText().getText().toString())) {
                novaSenha.setError("");
                confirmarSenha.setError("");
                isTrueConfirmation=true;
                newPassword = novaSenha.getEditText().getText().toString();
            }else {
                isTrueConfirmation=false;
                confirmarSenha.setError(" Diferente ");
            }
        }else{
           // textAlertaSenha.setText("");
        }
    }


    private void update() {

        handler = new Handler();
        timer = new Timer();

        task = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run(){
                        try {
                            verificationPasswordChenge();
                        } catch (Exception e) {
                            // error, do something
                        }
                    }
                });
            }
        };
        timer.schedule(task, 0, 100);  // interval of one minute
    }




    private void progressBar() {
        ProgressDialog progressBar;
        progressBar=new ProgressDialog(ActivityChengePassword.this);
        progressBar.setTitle("Aguarde");
        progressBar.setMessage("Processando-...");
        progressBar.show();

        new Handler().postDelayed(new Thread() {
            @Override
            public void run() {
                progressBar.dismiss();
                try {
                    trocar(numberSeacher,newPassword);
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
                startActivity(new Intent(ActivityChengePassword.this,Login.class));

            }
        },Long.parseLong("900"));
    }


    public void trocar(String numberSeacher, String newPassword) throws NoSuchAlgorithmException {

        if(dbService.isTellDoctor(numberSeacher)){
            dbService.updadeDoctorUserAndPassword(dbService.getIdDoctor(numberSeacher),newPassword);
        }else {
            if(dbService.isTellNurse(numberSeacher)){
                dbService.updadeNurseUserAndPassword(dbService.getIdNurse(numberSeacher),newPassword);
            }
        }
    }
    public void finish(View view) {
        finish();
    }
}
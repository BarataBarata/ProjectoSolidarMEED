package mz.unilurio.solidermed;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import mz.unilurio.solidermed.model.DBManager;
import mz.unilurio.solidermed.model.UserDoctor;
import mz.unilurio.solidermed.model.UserNurse;

public class ActivityChengePassword extends AppCompatActivity {
    private EditText novaSenha;
    private EditText user;
    private EditText confirmarSenha;
    private TextView textAlertaSenha;
    private Handler handler;
    private TimerTask task;
    private Timer timer;
    private String numberSeacher;
    private String newPassword;
    private String chengeuser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chenge_password);

        novaSenha=findViewById(R.id.idNovaSenha);
        confirmarSenha=findViewById(R.id.idConfirmarSenha);
        textAlertaSenha=findViewById(R.id.idAlerMessage);
        user =findViewById(R.id.idUsuario);

        if(getIntent().getStringExtra("numberSeacher")!=null){
            numberSeacher = getIntent().getStringExtra("numberSeacher");
        }
    }

    public void trocarPassword(View view) {
        if(novaSenha.getText().toString().equals("") || confirmarSenha.getText().toString().equals("") || user.getText().toString().equals("")){
            textAlertaSenha.setText(" campo vazio");
        }else{
            progressBar();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        atualizacao();
    }

    @Override
    protected void onPause() {
        super.onPause();
        task.cancel();
        timer.cancel();
    }

    public void verificationPasswordChenge(){
        if(!novaSenha.getText().toString().equals("") && !confirmarSenha.getText().toString().equals("")){

            if (novaSenha.getText().toString().equals(confirmarSenha.getText().toString())) {
                textAlertaSenha.setText(" senha confirmada");
                newPassword = novaSenha.getText().toString();
                chengeuser = user.getText().toString();
                textAlertaSenha.setTextColor(Color.GREEN);
            }else {
                textAlertaSenha.setText(" diferente..");
                textAlertaSenha.setTextColor(Color.RED);
            }
        }else{
            textAlertaSenha.setText("");
        }
    }


    private void atualizacao() {

        handler = new Handler();
        timer = new Timer();

        task = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        try {
                            verificationPasswordChenge();
                        } catch (Exception e) {
                            // error, do something
                        }
                    }
                });
            }
        };
        timer.schedule(task, 0, 900);  // interval of one minute

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
                trocar(numberSeacher,newPassword,chengeuser);
                startActivity(new Intent(ActivityChengePassword.this,Login.class));
            }
        },Long.parseLong("900"));
    }


    public void trocar(String numberSeacher, String newPassword,String chengeuser){
            boolean isTrue=true;
        System.out.println( numberSeacher +"  === " +newPassword);
            for (UserNurse userNurse: DBManager.getInstance().getUserNurseList()){
                if(userNurse.getContacto().equalsIgnoreCase(newPassword)){
                    userNurse.setPassworNurse(newPassword);
                    userNurse.setNomeNurse(chengeuser);
                    isTrue=false;
                }
            }

            if(isTrue) {
                for (UserDoctor userDoctor : DBManager.getInstance().getUserDoctorList()) {
                    if (userDoctor.getContacto().equalsIgnoreCase(numberSeacher)) {
                        userDoctor.setPasswordUser(newPassword);
                        userDoctor.setEmailUser(chengeuser);
                    }
                }
            }

    }


}
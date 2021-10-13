package mz.unilurio.solidermed;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class ActivitySendMenssage extends AppCompatActivity {

    private TextView textTitle;
    private  String numberSeacher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_menssage);
        textTitle =findViewById(R.id.txtTitle);

        if(getIntent().getStringExtra("numberSeacherUser")!=null){
            numberSeacher = getIntent().getStringExtra("numberSeacherUser");
            textTitle.setText("Sera enviada uma mensagem com o codigo para o numero : " +numberSeacher);
        }
    }

    public void finish(View view) {
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    public void CancelSendMenssage(View view) {
        finish();
    }

    public void sendMenssage(View view) {
          progressBar();
    }

    private void sendSMS(String phoneNumber, String message) {
        phoneNumber = phoneNumber.trim();
        message = message.trim();

        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, message, null, null);
            Toast.makeText(this, "Message is sent", Toast.LENGTH_SHORT);

        } catch (Exception e) {
            Log.i("EXPECTION SMS", e.getMessage());
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT);
        }
    }

    private void progressBar() {
        ProgressDialog progressBar;
        progressBar=new ProgressDialog(ActivitySendMenssage.this);
        progressBar.setTitle("Aguarde");
        progressBar.setMessage("Processando...");
        progressBar.show();

        new Handler().postDelayed(new Thread() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void run() {
                String codigo="";
                progressBar.dismiss();

                Random gerador = new Random();
                for (int i = 0; i <8; i++) {
                    codigo=codigo+gerador.nextInt(10);
                }

                String message=" Condigo de confirmacao da Senha da aplicacao MAMA "+"- Codigo : "+codigo;
                System.out.println(message);
                sendSMS(numberSeacher,message);
                Intent intent=new Intent(ActivitySendMenssage.this, ActivityValitationCode.class);
                intent.putExtra("codigo",codigo);
                intent.putExtra("numberSeacherUser",numberSeacher);

                startActivity(intent);

            }
        },Long.parseLong("900"));
    }
}
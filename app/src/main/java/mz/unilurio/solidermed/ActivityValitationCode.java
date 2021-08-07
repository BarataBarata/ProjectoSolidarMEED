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

public class ActivityValitationCode extends AppCompatActivity {
   private TextView textTitle;
   private EditText valitation;
   private  TextView textAlert;
   private String numberSeacher;
    private String codigoConfirmacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification_code);

        textTitle=findViewById(R.id.idTitleConfirmation);
        valitation=findViewById(R.id.seacherValitation);
        textAlert=findViewById(R.id.txtAlertSeacherValitation);

        if(getIntent().getStringExtra("numberSeacherUser")!=null){
            numberSeacher = getIntent().getStringExtra("numberSeacherUser");
            codigoConfirmacao = getIntent().getStringExtra("codigo");
            textTitle.setText(" Insira o código de confirmação ");
       }
        if(getIntent().getStringExtra("codigo")!=null){
            codigoConfirmacao = getIntent().getStringExtra("codigo");
        }

    }

    public void finish(View view) {
        finish();
    }

    public void valitationUser(View view) {
        progressBar();
    }



    private void progressBar() {
        ProgressDialog progressBar;
        progressBar=new ProgressDialog(ActivityValitationCode.this);
        progressBar.setTitle("Aguarde");
        progressBar.setMessage("Pesquisando...");
        progressBar.show();

        new Handler().postDelayed(new Thread() {
            @Override
            public void run() {
                progressBar.dismiss();

                if(valitation.getText().toString().equals("")){
                    valitation.setError(" campo vazio");
                }else{
                    if(valitation.getText().toString().length()<6 || valitation.getText().toString().length()>6){
                        textAlert.setText("O código tem 6 digitos");
                        textAlert.setTextColor(Color.RED);
                    }else {
                        if(codigoConfirmacao.equalsIgnoreCase(valitation.getText().toString())){
                            Intent intent=new Intent(ActivityValitationCode.this,ActivityChengePassword.class);
                            intent.putExtra("numberSeacher",numberSeacher);
                            startActivity(intent);
                        }else {
                            textAlert.setText("O código esta incorreto");
                            textAlert.setTextColor(Color.RED);
                        }
                    }

                }
            }
        },Long.parseLong("900"));
    }

}
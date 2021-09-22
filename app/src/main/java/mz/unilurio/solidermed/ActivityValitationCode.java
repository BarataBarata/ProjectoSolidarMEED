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

import com.google.android.material.textfield.TextInputLayout;

public class ActivityValitationCode extends AppCompatActivity {
   private TextView textTitle;
   private TextInputLayout valitation;
   private  TextView textAlert;
   private String numberSeacher;
    private String codigoConfirmacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification_code);
        valitation=findViewById(R.id.seacherValitation2);

        if(getIntent().getStringExtra("numberSeacherUser")!=null){
            numberSeacher = getIntent().getStringExtra("numberSeacherUser");
            codigoConfirmacao = getIntent().getStringExtra("codigo");
           // textTitle.setText(" Insira o código de confirmação ");
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

                if(valitation.getEditText().getText().toString().equals("")){
                    valitation.setError(" campo vazio");
                }else{
                    if(valitation.getEditText().getText().toString().length()<8 || valitation.getEditText().getText().toString().length()>8){
                        valitation.setError("Codigo incorreto");
                        // textAlert.setText("O código tem 8 digitos");
                        //textAlert.setTextColor(Color.RED);
                    }else {
                        if(codigoConfirmacao.equalsIgnoreCase(valitation.getEditText().getText().toString())){
                            Intent intent=new Intent(ActivityValitationCode.this,ActivityChengePassword.class);
                            intent.putExtra("numberSeacher",numberSeacher);
                            startActivity(intent);
                        }else {
                            valitation.setError("O codigo esta incorreto");
                            //textAlert.setText("O código esta incorreto");
                           // textAlert.setTextColor(Color.RED);
                        }
                    }

                }
            }
        },Long.parseLong("900"));
    }

}
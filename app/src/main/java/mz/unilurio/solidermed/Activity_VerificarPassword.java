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

import mz.unilurio.solidermed.model.DBManager;
import mz.unilurio.solidermed.model.DBService;
import mz.unilurio.solidermed.model.UserDoctor;
import mz.unilurio.solidermed.model.UserNurse;

public class Activity_VerificarPassword extends AppCompatActivity {
    private  static String seacherUserNumber;
    private  String numberUser;
    private TextView textAlert;
    private TextInputLayout seacherUser;
    private String nomeCompleto;
    private DBService dbService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verificar_password);

        //textAlert = findViewById(R.id.txtAlertSeacherValitation);
        seacherUser=findViewById(R.id.seacherValitation);
        dbService=new DBService(this);
    }

    public void pesquisarUser(View view) {
               progressBar();
    }

    private boolean seacherVerificationUser() {

            if(isSeacherNurse()){
                return true;
            }else {
                if(isSeacherDoctor()){
                    return  true;
                }
            }

            return false;
    }

    private boolean isSeacherDoctor() {
        for(UserDoctor userDoctor: dbService.getListDoctor()){
             if(userDoctor.getContacto().equalsIgnoreCase(seacherUserNumber)){
                 numberUser=userDoctor.getContacto();
                 nomeCompleto =userDoctor.getFullName();
                 return  true;
             }
        }

        return  false;

    }

    private boolean isSeacherNurse() {
        for(UserNurse userNurse: dbService.getListNurse()){
            if(userNurse.getContacto().equalsIgnoreCase(seacherUserNumber)){
                numberUser=userNurse.getContacto();
                nomeCompleto =userNurse.getFullName();
                return  true;
            }
        }
        return  false;
    }

    private void progressBar() {
        ProgressDialog progressBar;
        progressBar=new ProgressDialog(Activity_VerificarPassword.this);
        progressBar.setTitle("Aguarde");
        progressBar.setMessage("Pesquisando...");
        progressBar.show();

        new Handler().postDelayed(new Thread() {
            @Override
            public void run() {
                progressBar.dismiss();
                seacherUserNumber=seacherUser.getEditText().getText().toString();
                if(seacherVerificationUser()){
                    Intent intent=new Intent(Activity_VerificarPassword.this,ActivityViewUserSeacher .class);
                    intent.putExtra("numberSeacherUser",numberUser);
                    intent.putExtra("nomeCompleto", nomeCompleto);
                    startActivity(intent);
                   // textAlert.setText("");
                }else{
                    seacherUser.setError("Nenhum usuario encontrado");
//                    textAlert.setText("Sem resuldados");
                }
            }
        },Long.parseLong("900"));
    }
    public void finish(View view) {
        finish();
    }
}
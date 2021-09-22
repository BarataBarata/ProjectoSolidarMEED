package mz.unilurio.solidermed;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;

import com.google.android.material.textfield.TextInputLayout;

import java.util.Random;

import mz.unilurio.solidermed.model.DBManager;
import mz.unilurio.solidermed.model.DBService;
import mz.unilurio.solidermed.model.UserDoctor;
import mz.unilurio.solidermed.model.UserNurse;

public class Activity_RegistarUsuario extends AppCompatActivity {
    private TextInputLayout contacto;
    private TextInputLayout user;
    private TextInputLayout novaSenha;
    private TextInputLayout confirmarSenha;
    private boolean isExistContacto=false;
    private String userView;
    private DBService dbService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registar_usuario);
        dbService=new DBService(this);

        contacto=findViewById(R.id.id_input_contacto);
        user=findViewById(R.id.id_input_add_user);
        novaSenha=findViewById(R.id.id_input_nova_senha);
        confirmarSenha=findViewById(R.id.id_input_confirmar_senha);


    }
    public void verificationContact(){

        for (UserDoctor userDoctor: dbService.getListDoctor()) {
            if(userDoctor.getContacto().equals(contacto.getEditText().getText().toString())){
                isExistContacto=true;
                userView=userDoctor.getUserLogin();

            }
        }

        if(!isExistContacto){
            for (UserNurse userNurse: dbService.getListNurse()) {
                if(userNurse.getContacto().equals(contacto.getEditText().getText().toString())){
                    isExistContacto=true;
                    userView= userNurse.getUserNurse();
                }
            }
        }

        if(!isExistContacto){
            contacto.setError("Contacto não autorizado");
        }else {
            if(userView.isEmpty()){
                String codigo="";
                Random gerador = new Random();

                for (int i = 0; i <5; i++) {
                    codigo=codigo+gerador.nextInt(30);
                }
                String message=" SISTEMA DE ALERTA. codigo de verificacao do registo: "+codigo;
                sendSMS(contacto.getEditText().getText().toString(),message);

                Intent intent=new Intent(Activity_RegistarUsuario.this,Activity_Confirmar_Registo.class);
                intent.putExtra("codigo",codigo+"");
                intent.putExtra("user",user.getEditText().getText().toString());
                intent.putExtra("senha",novaSenha.getEditText().getText().toString());
                intent.putExtra("contacto",contacto.getEditText().getText().toString());
                startActivity(intent);

            }else {
                contacto.setError("existe uma conta para o contacto");
            }
        }
        isExistContacto=false;



    }

    public boolean verificationIsnull(TextInputLayout textInputLayout){

           if(textInputLayout.getEditText().getText().toString().isEmpty()){
               return true;
           }else {
               return false;
           }

    }
    public void finish(View view) {
        finish();
    }

    public void registar(View view) {

        if(verificationIsnull(contacto)){
            contacto.setError("campo vazio");
        }else {
            contacto.setError("");
            if(verificationIsnull(user)){
                user.setError("campo vazio");
            }else {
               user.setError("");
                if(verificationIsnull(novaSenha)){
                    novaSenha.setError("campo vazio");
                }else {
                 novaSenha.setError("");
                    if(verificationIsnull(confirmarSenha)){
                        confirmarSenha.setError("campo vazio");
                    }else {
                        confirmarSenha.setError("");
                        if(confirmarSenha.getEditText().getText().toString().equals(novaSenha.getEditText().getText().toString())){
                            verificationContact();
                        }else {
                            confirmarSenha.setError("Senha diferente");
                        }

                    }
                }
            }
        }
    }

    private void sendSMS(String phoneNumber, String message) {
        phoneNumber = phoneNumber.trim();
        message = message.trim();

        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, message, null, null);

        } catch (Exception e) {
            Log.i("EXPECTION SMS", e.getMessage());
        }
    }
}
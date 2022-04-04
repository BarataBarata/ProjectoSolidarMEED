package mz.unilurio.solidermed;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import mz.unilurio.solidermed.model.DBService;
import mz.unilurio.solidermed.model.Privilegios;
import mz.unilurio.solidermed.model.UserNurse;

public class Login extends AppCompatActivity {
    private ProgressDialog progressBar;
    private FirebaseAuth auth;
    private TextInputLayout textEmail;
    private TextInputLayout textPassword;
    private TextView textAlerta;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private Privilegios privilegios;
    DBService dbService;
    private ProgressBar  progressBarCirc;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        dbService=new DBService(this);
       // requestPermissions(new String[]{Manifest.permission.CALL_PHONE},1);
        requestPermissions(new String[]{Manifest.permission.SEND_SMS}, 1);


        privilegios = new Privilegios();
        progressBarCirc=findViewById(R.id.progress_circular);
        textEmail= findViewById(R.id.id_input_nome);
        textPassword=findViewById(R.id.id_input_password);
        auth = FirebaseAuth.getInstance();
    }

    public void Entrar(View view) {
        //callPhone();
        String email=textEmail.getEditText().getText().toString();
        String password=textPassword.getEditText().getText().toString();
        verificationUser(email,password);
    }


    private void verificationUser(String email, String password) {
        //privilegios.setViewAll(true);

//        Intent intent= new Intent(Login.this, MainActivity.class);
//        intent.putExtra("nomeUserLogin",fullName);
//        startActivity(intent);
                 if(email.isEmpty()){
                     textEmail.setError("campo vazio");
                 }else {
                     if(password.isEmpty()){
                         textPassword.setError("campo vazio");
                     }else {
                         dbService.updadeSessaoTerminado(false);
                         progressBarCirc.setVisibility(View.VISIBLE);
                         if(isExistUser(email,password)){
                             textPassword.setError("");
                             textEmail.setError("");
                             startActivity( new Intent(Login.this, MainActivity.class));
                         }else{
                             textEmail.setError(" ");
                             progressBarCirc.setVisibility(View.INVISIBLE);
                             textPassword.setError(" senha ou usuario invalido");
                             //textAlerta.setText(" Usuario ou senha incorreto");
                         }
                     }
                 }
    }

    @Override
    public void finish() {
        StartApp startApp =new StartApp();
        startApp.start=true;
        super.finish();
    }

    public boolean isExistUser(String user, String password){
        System.out.println("============================: "+dbService.isDoctorLogin(user,password));
        if(dbService.isDoctorLogin(user,password)){
            dbService.updateSessaoUserLogin(dbService.getFullNameDoctorLogin(user,password));
            dbService.updadeDoctorPrivilegios(true);
            privilegios.setViewAll(true);
            //dbService.apdateAllAcess(true);
            return true;
        }else {
           if(dbService.isNurseLogin(user,password)){
               dbService.updateSessaoUserLogin(dbService.getFullNameNurseLogin(user,password));
               dbService.updadeDoctorPrivilegios(false);
               return true;
           }
        }
        return false;
    }
    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        progressBarCirc.setVisibility(View.INVISIBLE);
        super.onResume();
        textEmail.getEditText().setText("");
        textPassword.getEditText().setText("");
    }

    public void confirmarPassword(View view) {
        startActivity(new Intent(Login.this,Activity_VerificarPassword.class));
    }

    public String removeSpace(String name){
        String newName="";

        for (int i = 0; i <name.length() ; i++) {
             if(name.charAt(i)!=32){
                 newName=newName+name.charAt(i);
             }
        }
        return newName;
    }

    public void callPhone(){

        Uri uri=Uri.parse("tel:"+845740722);
        Intent intent=new Intent(Intent.ACTION_CALL,uri);
//        if (ActivityCompat.checkSelfPermission(Login.this,Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){
//            ActivityCompat.requestPermissions(Login.this,new String[]{Manifest.permission.CALL_PHONE},1);
//            return;
//        }
        startActivity(intent);
    }

    public void registarUsuario(View view) {
        startActivity(new Intent(Login.this,Activity_RegistarUsuario.class));
    }


}
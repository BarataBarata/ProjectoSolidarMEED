package mz.unilurio.solidermed;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import mz.unilurio.solidermed.model.DBManager;
import mz.unilurio.solidermed.model.Privilegios;
import mz.unilurio.solidermed.model.UserDoctor;
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


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        requestPermissions(new String[]{Manifest.permission.SEND_SMS}, 1);
        SmsManager smsManager = SmsManager.getDefault();
        privilegios = new Privilegios();
        textAlerta = findViewById(R.id.idAlertaPassword);
        textEmail= findViewById(R.id.id_input_nome);
        textPassword=findViewById(R.id.id_input_password);
        auth = FirebaseAuth.getInstance();

    }

    public void Entrar(View view) {
        String email=textEmail.getEditText().getText().toString();
        String password=textPassword.getEditText().getText().toString();
        progressBar2(email,password);

        // (intent);
       // auth = FirebaseAuth.getInstance();
       // progressBar();
    }

//    private void progressBar() {
//        progressBar=new ProgressDialog(Login.this);
//        progressBar.setMessage("validando os dados...");
//        progressBar.show();
//
//        new Handler().postDelayed(new Thread() {
//            @Override
//            public void run() {
//                progressBar.dismiss();
//
//                String email=textEmail.getEditText().getText().toString();
//                String password=textPassword.getEditText().getText().toString();
//                System.out.println(email+"         "+password);
//                if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)){
//
//                    auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                        @Override
//                        public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
//                           if(task.isSuccessful()){
//                            Intent intent = new Intent(Login.this, MainActivity.class);
//                            startActivity(intent);
//                            }else {
//                                String erro=task.getException().getMessage();
//                                textAlerta.setText("Email ou Senha Incorreto");
//                                textAlerta.setTextColor(Color.RED);
//                                Toast.makeText(Login.this, erro, Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    });
//
//                }
//            }
//        }, Long.parseLong("900"));
//    }




    private void progressBar2(String email,String password) {
        progressBar=new ProgressDialog(Login.this);
        progressBar.setMessage("validando os dados...");
        progressBar.show();

        new Handler().postDelayed(new Thread() {
            @Override
            public void run() {
                progressBar.dismiss();

                if(isDoctor(email,password)){
                    textAlerta.setText("");
                    startActivity( new Intent(Login.this, MainActivity.class));
                } else {
                    if(isNurse(email,password)){
                        textAlerta.setText("");
                        startActivity( new Intent(Login.this, MainActivity.class));
                    }else{
                        textAlerta.setText(" Usuario ou senha incorreto");
                    }
                }

            }
        }, Long.parseLong("900"));
    }









    public boolean isDoctor(String user,String password){

        for (UserDoctor s:DBManager.getInstance().getUserDoctorList()) {
            if(user.equalsIgnoreCase(s.getEmailUser()) && password.equalsIgnoreCase(s.getPasswordUser())){
                privilegios.setViewAll(true);
                return true;
            }
        }
        return false;
    }


    public boolean isNurse(String user,String password){

        for(UserNurse userNurse:DBManager.getInstance().getUserNurseList()){
            if(userNurse.getNomeNurse().equalsIgnoreCase(user) && userNurse.getPassworNurse().equalsIgnoreCase(password)){
                privilegios.setViewAll(false);
                return true;
            }
        }
        return false;
    }

    @Override
    public void finish() {
        super.finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        textEmail.getEditText().setText("");
        textPassword.getEditText().setText("");
    }

    public void confirmarPassword(View view) {
        startActivity(new Intent(Login.this,Activity_VerificarPassword.class));
    }

}
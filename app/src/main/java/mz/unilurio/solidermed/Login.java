package mz.unilurio.solidermed;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
    private String fullName;


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
                    if(isExistNurse(email,password) || isExistDoctor(email,password)){
                        textAlerta.setText("");
                        Intent intent= new Intent(Login.this, MainActivity.class);
                        intent.putExtra("user",fullName);
                        startActivity(intent);
                    }else{
                        textAlerta.setText(" Usuario ou senha incorreto");
                    }


            }
        }, Long.parseLong("500"));
    }

    public boolean isExistDoctor(String user, String password){

        for (UserDoctor s:DBManager.getInstance().getUserDoctorList()) {
            if(removeSpace(user).equalsIgnoreCase(removeSpace(s.getUserLogin())) && password.equalsIgnoreCase(s.getPasswordUser())){
                fullName=s.getFullName();
                privilegios.setViewAll(true);
                return true;
            }
        }
        return false;
    }


    public boolean isExistNurse(String user, String password){

        for(UserNurse userNurse:DBManager.getInstance().getUserNurseList()){
            if(removeSpace(userNurse.getUserNurse()).equalsIgnoreCase(removeSpace(user)) && userNurse.getPassworNurse().equalsIgnoreCase(password)){
                fullName=userNurse.getFullName();
                privilegios.setViewAll(false);
                return true;
            }
        }
        return false;
    }


    @Override
    public void finish() {
       finish();
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

    public String removeSpace(String name){
        String newName="";

        for (int i = 0; i <name.length() ; i++) {
             if(name.charAt(i)!=32){
                 newName=newName+name.charAt(i);
             }
        }
        return newName;
    }

}
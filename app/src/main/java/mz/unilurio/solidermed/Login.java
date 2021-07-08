package mz.unilurio.solidermed;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AuthenticationRequiredException;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.QuickContactBadge;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;

public class Login extends AppCompatActivity {
    ProgressDialog progressBar;
    FirebaseAuth auth;
    TextInputLayout textEmail;
    TextInputLayout textPassword;
    private TextView textAlerta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        textAlerta = findViewById(R.id.id_alerta_pass_senha);
        textEmail= findViewById(R.id.id_input_nome);
        textPassword=findViewById(R.id.id_input_password);
        auth = FirebaseAuth.getInstance();

    }

    public void Entrar(View view) {
                progressBar();
    }

    private void progressBar() {
        progressBar=new ProgressDialog(Login.this);
        progressBar.setMessage("validando os dados...");
        progressBar.show();

        new Handler().postDelayed(new Thread() {
            @Override
            public void run() {
                progressBar.dismiss();
                String email=textEmail.getEditText().getText().toString();
                String password=textPassword.getEditText().getText().toString();

                if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)){

                    auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Intent intent = new Intent(Login.this, MainActivity.class);
                                startActivity(intent);
                            }else {
                                String erro=task.getException().getMessage();
                                textAlerta.setText("Email ou Senha Incorreto");
                                textAlerta.setTextColor(Color.RED);
                                Toast.makeText(Login.this, erro, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
            }
        },Long.parseLong("900"));
    }
}
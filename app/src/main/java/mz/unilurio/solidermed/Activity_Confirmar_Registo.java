package mz.unilurio.solidermed;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.textfield.TextInputLayout;

import mz.unilurio.solidermed.model.DBService;

public class Activity_Confirmar_Registo extends AppCompatActivity {

    private String codigo="";
    private String contacto="";
    private String user="";
    private String password="";
    private TextInputLayout confirmarCodigo;
    private DBService dbService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmar_registo);

        dbService=new DBService(this);

        confirmarCodigo=findViewById(R.id.idImputConfirmation);

        if(getIntent().getStringExtra("codigo")!=null) {
           codigo=getIntent().getStringExtra("codigo");
            System.out.println(" codigo : "+codigo);
        }
        if(getIntent().getStringExtra("contacto")!=null) {
            contacto=getIntent().getStringExtra("contacto");
        }
        if(getIntent().getStringExtra("user")!=null) {
            user=getIntent().getStringExtra("user");
        }
        if(getIntent().getStringExtra("senha")!=null) {
            password=getIntent().getStringExtra("senha");
        }

    }


    public void finish(View view) {
        finish();
    }

    public void confirmarRegisto(View view) {
           if(!confirmarCodigo.getEditText().getText().toString().isEmpty()) {
               if (codigo.equals(confirmarCodigo.getEditText().getText().toString())) {
                   dbService.updadeDoctorUserAndPassword(dbService.getIdDoctor(contacto),user,password);
                   startActivity(new Intent(Activity_Confirmar_Registo.this, Login.class));
               } else {
                   confirmarCodigo.setError(" codigo incorrecto");
               }
           }else {
               confirmarCodigo.setError("campo vazio");
           }
    }
}
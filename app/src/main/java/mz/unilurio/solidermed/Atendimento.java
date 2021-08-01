package mz.unilurio.solidermed;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

import mz.unilurio.solidermed.model.DBManager;
import mz.unilurio.solidermed.model.Notification;
import mz.unilurio.solidermed.model.Parturient;

public class Atendimento extends AppCompatActivity {
   private int idParturiente;
   private TextView textNomeParturiente;
   private  Parturient newParturient;
    private ProgressDialog progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atendimento);
        textNomeParturiente= findViewById(R.id.idNomeDadosSaida);

        if(getIntent().getStringExtra("idParturiente")!=null){
            idParturiente = Integer.parseInt(getIntent().getStringExtra("idParturiente"));
            for(Parturient parturient: DBManager.getInstance().getParturients()){
                if(parturient.getId()==idParturiente){
                    newParturient=parturient;
                    textNomeParturiente.setText(parturient.getName()+" "+parturient.getSurname());
                    break;
                }
            }

        }


    }

    public void removNotification(){
        for(Notification parturient: DBManager.getInstance().getNotifications()){
            if(Integer.parseInt(parturient.getId())==idParturiente){
                DBManager.getInstance().getNotifications().remove(parturient);
               break;
            }
        }

    }


    public void finish(View view) {
        finish();
    }

    public  void addNewAtendido(View view){
            editParturiente();
    }


    public  void editParturiente(){

        String mensagem="Salvar dados ?";
        String mensagemTitle="SALVAR";;

        AlertDialog.Builder dialog=new AlertDialog.Builder(this);
        dialog.setTitle(mensagemTitle);
        dialog.setMessage(mensagem);
        dialog.setCancelable(false);
        dialog.setIcon(getDrawable(R.drawable.trasferencia));

        dialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                progressBar =new ProgressDialog(Atendimento.this);
                progressBar.setTitle("Aguarde");
                progressBar.setMessage("Registando...");
                progressBar.show();

                new Handler().postDelayed(new Thread() {
                    @Override
                    public void run() {
                        progressBar.dismiss();
                        DBManager.getInstance().addParturienteAtendido(newParturient);
                        removNotification();
                        finish();
                    }
                },Long.parseLong("900"));

                //DBManager.getInstance().updateQueue((int) mSliderDilatation.getValue());
                Toast.makeText(getApplicationContext(), " Dados adicionados com sucesso", Toast.LENGTH_LONG).show();
            }

        });
        dialog.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext()," Operacao Cancelada",Toast.LENGTH_LONG).show();

            }
        });

        dialog.create();
        dialog.show();
    }

}
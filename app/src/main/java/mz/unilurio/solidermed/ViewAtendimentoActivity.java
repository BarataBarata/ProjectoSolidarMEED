package mz.unilurio.solidermed;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

import mz.unilurio.solidermed.model.DBManager;
import mz.unilurio.solidermed.model.Notification;
import mz.unilurio.solidermed.model.Parturient;

public class ViewAtendimentoActivity extends AppCompatActivity {
    private int idParturiente;
    private TextView textNomeParturiente;
    private Parturient newParturient=new Parturient();
    private ProgressDialog progressBar;
    private CheckBox checkBox1;
    private Switch aSwitchProcess;
    private  boolean optionSwitch=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_antendimento);
        textNomeParturiente= findViewById(R.id.nomeParturienteView);

        aSwitchProcess=findViewById(R.id.switchProcess);
        aSwitchProcess.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
             optionSwitch=isChecked;
            }
        });

        checkBox1=findViewById(R.id.txt1);

        if(getIntent().getStringExtra("idParturiente")!=null){
            idParturiente = Integer.parseInt(getIntent().getStringExtra("idParturiente"));
            for(Parturient parturient: DBManager.getInstance().getParturients()){
                if(parturient.getId()==idParturiente){
                    newParturient=parturient;
                    optionSwitch=parturient.isInProcess();
                    aSwitchProcess.setChecked(optionSwitch);
                    textNomeParturiente.setText(oUpperFirstCase(parturient.getName())+" "+oUpperFirstCase(parturient.getSurname()));
                    break;
                }
            }

        }


    }
    public  String oUpperFirstCase(String string){
        String auxString=(string.charAt(0)+"").toUpperCase()+""+string.substring(1)+"";
        return  auxString;
    }
    public void setHoraAtendimento(Parturient parturient){
        parturient.setHoraAtendimento(new Date());
    }
    public void removParturiente(){
        for(Parturient parturient: DBManager.getInstance().getParturients()){
            if(parturient.getId()==idParturiente){
                DBManager.getInstance().getParturients().remove(parturient);
                break;
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
        if(optionSwitch){
            parturienteInProcess();
        }else {
            addNewParturiente();
        }
    }


    public  void addNewParturiente(){

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

                progressBar =new ProgressDialog(ViewAtendimentoActivity.this);
                progressBar.setTitle("Aguarde");
                progressBar.setMessage("Registando...");
                progressBar.show();

                new Handler().postDelayed(new Thread() {
                    @Override
                    public void run() {
                        progressBar.dismiss();
                        setHoraAtendimento(newParturient);
                        System.out.println("trasferencia : " +newParturient.isTransfered());
                        DBManager.getInstance().addParturienteAtendido(newParturient);
                        removParturiente();
                        removNotification();
                        newParturient.setInProcess(false);
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
    public  void parturienteInProcess(){

        String mensagemTitle="Parturiente em processo ?";;

        AlertDialog.Builder dialog=new AlertDialog.Builder(this);
        dialog.setTitle(mensagemTitle);
        dialog.setCancelable(false);
        dialog.setIcon(getDrawable(R.drawable.mulhergravidabom2));

        dialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                progressBar =new ProgressDialog(ViewAtendimentoActivity.this);
                progressBar.setTitle("Aguarde");
                progressBar.setMessage("Inserindo...");
                progressBar.show();

                new Handler().postDelayed(new Thread() {
                    @Override
                    public void run() {
                        progressBar.dismiss();
                        newParturient.setInProcess(true);
                        setProgressNotification(newParturient.getId());
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

    public void setProgressNotification(int id){

        for (Notification notification:DBManager.getInstance().getNotifications()){
            if(Integer.parseInt(notification.getId())==id){
                notification.setInProcess(true);
                break;
            }
        }

    }
}
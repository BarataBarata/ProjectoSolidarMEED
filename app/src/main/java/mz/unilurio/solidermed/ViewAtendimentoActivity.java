package mz.unilurio.solidermed;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import mz.unilurio.solidermed.model.DBManager;
import mz.unilurio.solidermed.model.DBService;
import mz.unilurio.solidermed.model.Notification;
import mz.unilurio.solidermed.model.Parturient;

public class ViewAtendimentoActivity extends AppCompatActivity {
    private static int idParturiente;
    private DBService dbService;


    private TextView textNomeParturiente;
    private Parturient newParturient=new Parturient();
    private ProgressDialog progressBar;
    private CheckBox checkBox1,checkBox2,checkBox5;
    private Switch aSwitchProcess;
    public static String checkBoxTextOption;
    private  boolean optionSwitch=false;
    private  boolean optionCheckBox=false;
    private  boolean optionCheckBoxTrasfered=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_antendimento);
        textNomeParturiente= findViewById(R.id.nomeParturienteView);
        dbService=new DBService(this);
        checkBox1=findViewById(R.id.txt1);
        checkBox1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aSwitchProcess.setChecked(false);
                optionCheckBox=checkBox1.isChecked();
                checkBoxTextOption=checkBox1.getText().toString();
                checkBox2.setChecked(false);
                checkBox5.setChecked(false);
            }
        });

        checkBox2=findViewById(R.id.txt2);
        checkBox2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aSwitchProcess.setChecked(false);
                optionCheckBox=checkBox2.isChecked();
                checkBoxTextOption=checkBox2.getText().toString();
                checkBox5.setChecked(false);
                checkBox1.setChecked(false);
            }
        });

        checkBox5=findViewById(R.id.txt5);
        checkBox5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aSwitchProcess.setChecked(false);
                optionCheckBoxTrasfered=checkBox5.isChecked();
                optionCheckBox=checkBox5.isChecked();
                checkBox2.setChecked(false);
                checkBox1.setChecked(false);
            }
        });

        aSwitchProcess=findViewById(R.id.switchProcess);
        aSwitchProcess.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
             optionSwitch=isChecked;
             if(aSwitchProcess.isChecked()){
                 checkBox2.setChecked(false);
                 checkBox5.setChecked(false);
                 checkBox1.setChecked(false);
             }
            }
        });

        if(getIntent().getStringExtra("idParturiente")!=null){
           idParturiente=Integer.parseInt(getIntent().getStringExtra("idParturiente"));

           for(Parturient parturient: DBManager.getInstance().getAuxlistNotificationParturients()){
               System.out.println(parturient.getId()+" : ============== : "+idParturiente);
               if(parturient.getId()==idParturiente){
                    textNomeParturiente.setText(parturient.getName()+ " "+parturient.getSurname());
                    newParturient=parturient;
                    aSwitchProcess.setChecked(parturient.isInProcess());
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
        parturient.setHoraAtendimento(new Date()+"");
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
        System.out.println(optionCheckBox);
        if(optionSwitch){
            parturienteInProcess();
        }else {
            if(optionCheckBox){
                addNewParturiente();
            }else {
                alertAdd();
            }
        }
    }


    public  void addNewParturiente(){
        String mensagemTitle="";
        String mensagem="";
        if(optionCheckBoxTrasfered){
            mensagemTitle="Transferência";
            mensagem=" Tasferir ?";
        }else {
            mensagemTitle="SALVAR";
            mensagem="Atendimento ?";
        }

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
                progressBar.setMessage("Processando...");
                progressBar.show();

                new Handler().postDelayed(new Thread() {
                    @Override
                    public void run() {
                        progressBar.dismiss();
                        if(optionCheckBoxTrasfered){

                            for(Parturient parturient: DBManager.getInstance().getAuxlistNotificationParturients()){
                                if(parturient.getId()==idParturiente){
                                    setHoraAtendimento(newParturient);
                                    parturient.setHoraAtendimento(format(new Date()));
                                    parturient.setTipoAtendimento(checkBoxTextOption);
                                    parturient.setInProcess(false);
                                    Intent intent =new Intent(ViewAtendimentoActivity.this,TrasferenciaActivity.class);
                                    intent.putExtra("idParturiente",parturient.getId()+"");
                                    startActivity(intent);
                                    break;
                                }
                            }
                        }else {
                            setHoraAtendimento(newParturient);
                            newParturient.setHoraAtendimento(format(new Date()));
                            newParturient.setTipoAtendimento(checkBoxTextOption);
                            newParturient.setInProcess(false);
                            DBManager.getInstance().addParturienteAtendido(newParturient);
                            removNotification();
                            removParturiente();

//
//                              if(!optionSelectParturienteNotification){
//                                  for(Parturient parturient: DBManager.getInstance().getAuxlistNotificationParturients()){
//                                      if(parturient.getId()==idParturiente){
//                                          setHoraAtendimento(newParturient);
//                                          parturient.setHoraAtendimento(format(new Date()));
//                                          parturient.setTipoAtendimento(checkBoxTextOption);
//                                          parturient.setInProcess(false);
//                                          DBManager.getInstance().addParturienteAtendido(parturient);
//                                          removNotification();
//                                          break;
//                                      }
//                                  }
//                              }else {
//
//
//                              }

                        }
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



    public  void alertAdd(){

        //String mensagem="";
        String mensagemTitle="Selecione uma das Opções ";;
        AlertDialog.Builder dialog=new AlertDialog.Builder(this);
        dialog.setTitle(mensagemTitle);
        //dialog.setMessage(mensagem);
        dialog.setCancelable(false);
        dialog.setIcon(getDrawable(R.drawable.error));

        dialog.setPositiveButton("ok", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

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

                progressBar();
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
    private void progressBar() {
        ProgressDialog progressBar;
        progressBar=new ProgressDialog(ViewAtendimentoActivity.this);
        progressBar.setTitle("Aguarde");
        progressBar.setMessage("Pesquisando...");
        progressBar.show();

        new Handler().postDelayed(new Thread() {
            @Override
            public void run() {
                progressBar.dismiss();
                for(Parturient parturient: DBManager.getInstance().getAuxlistNotificationParturients()) {
                    if (parturient.getId() == idParturiente) {
                        parturient.setInProcess(true);
                        setProgressNotification(parturient.getId());
                        finish();
                        break;
                    }
                }


            }
        },Long.parseLong("900"));
    }


    private String format(Date date){
        DateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");
        return dateFormat.format(date);
    }
}
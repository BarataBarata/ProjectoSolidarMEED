package mz.unilurio.solidermed;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLOutput;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import mz.unilurio.solidermed.model.DBManager;
import mz.unilurio.solidermed.model.DBService;
import mz.unilurio.solidermed.model.Notification;
import mz.unilurio.solidermed.model.Parturient;

public class TrasferenciaActivity extends AppCompatActivity {

    private TextView textNome;
    private TextView textFullName;
    private static String fullNameParturiente;
    private Spinner spinnerDestino;
    private Spinner spinnerMotivoDestino;
    private static String idParturiente="";
    private Parturient auxParturiente=new Parturient();
    private DBService dbService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trasferencia);

        dbService=new DBService(this);
        initialize();

        if(getIntent().getStringExtra("idParturiente")!=null){
            idParturiente = getIntent().getStringExtra("idParturiente");
           for(Parturient parturient: DBManager.getInstance().getAuxlistNotificationParturients()){
                if(parturient.getIdAuxParturiente().equals(idParturiente)){
                    auxParturiente=parturient;
                    fullNameParturiente=parturient.getName()+ " "+parturient.getSurname();
                    textFullName.setText(fullNameParturiente);
                    break;
                }
            }
        }

        List<String> listSanitaria = DBManager.getInstance().getListOpcoesUnidadeSanitaria();
        ArrayAdapter<String> adapterSanitaria = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listSanitaria);
        adapterSanitaria.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerDestino.setAdapter( adapterSanitaria);

        List<String> listTrasferencia = DBManager.getInstance().getListMotivosTrasferencia();
        ArrayAdapter<String> adapterTrasferencia = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listTrasferencia);
        adapterTrasferencia.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerMotivoDestino.setAdapter( adapterTrasferencia );

    }

    private void initialize() {
        textFullName = findViewById(R.id.txtNomeParturienteTrasf);
        spinnerDestino = findViewById(R.id.destinoTrasferenciaX);
        spinnerMotivoDestino = findViewById(R.id.motivosTrasferenciaX);
    }

    public void finish(View view) {
        finish();
    }

    public void Trasferir(View view) {
            androidx.appcompat.app.AlertDialog.Builder dialog=new androidx.appcompat.app.AlertDialog.Builder(this);
            dialog.setTitle("Transferência");
            dialog.setMessage(" Transferir "+ auxParturiente.getName()+" "+ auxParturiente.getSurname()+" ?");
            dialog.setCancelable(false);
            dialog.setIcon((R.drawable.mulhergravidabom2));

            dialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    ProgressDialog progressBar;
                    progressBar=new ProgressDialog(TrasferenciaActivity.this);
                    progressBar.setTitle("Aguarde");
                    progressBar.setMessage("processando...");
                    progressBar.show();

                    new Handler().postDelayed(new Thread() {
                        @Override
                        public void run() {
                            progressBar.dismiss();
                            auxParturiente.setHoraAtendimento(format(new Date())+"");
                            auxParturiente.cancelCountDownTimer=true;
                            auxParturiente.setAtendido(true);
                            auxParturiente.setTrasferirParturiente(true);
                            auxParturiente.setTrasferidoParaForaDoHospital(true);
                            auxParturiente.setDestinoTrasferencia(spinnerDestino.getSelectedItem().toString());
                            auxParturiente.setMotivosDestinoDaTrasferencia(spinnerMotivoDestino.getSelectedItem().toString());
                            dbService.addAtendimento(auxParturiente);
                            DBManager.getInstance().getListParturientesAtendidos().add(auxParturiente);
                            DBManager.getInstance().getParturients().remove(auxParturiente);
                            dbService.removeInBD(auxParturiente);
                            removNotification();



                           finish();
                        }
                    },Long.parseLong("900"));
                    Toast.makeText(getApplicationContext(), "Trasferido com sucesso", Toast.LENGTH_LONG).show();
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

    public void removNotification(){
        for(Notification notification: DBManager.getInstance().getNotifications()){
            if(notification.getIdAuxParturiente().equals(idParturiente)){
                notification.setAtendido(true);
                DBManager.getInstance().getNotifications().remove(notification);
                dbService.deleteNotification(notification);
                break;
            }
        }

    }

    private String format(Date date){
        DateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");
        return dateFormat.format(date);
    }
}
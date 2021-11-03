package mz.unilurio.solidermed;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.material.slider.Slider;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;


import org.jetbrains.annotations.NotNull;

import java.sql.SQLOutput;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import mz.unilurio.solidermed.model.AddDoctorClass;
import mz.unilurio.solidermed.model.AlertParutient;
import mz.unilurio.solidermed.model.App;
import mz.unilurio.solidermed.model.DBManager;
import mz.unilurio.solidermed.model.DBService;
import mz.unilurio.solidermed.model.EditDoctorClass;
import mz.unilurio.solidermed.model.IdadeGestacional;
import mz.unilurio.solidermed.model.Notification;
import mz.unilurio.solidermed.model.Parturient;
import mz.unilurio.solidermed.model.SelectSinal;
import mz.unilurio.solidermed.model.SelectTransferencia;
import mz.unilurio.solidermed.model.UserDoctor;

public class AddParturientActivity extends AppCompatActivity{
    public  static boolean isFireAlert=false;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private DBService dbService;
    private Spinner transferencia;
    private Spinner spinnerMotivos;
    private Handler handlerTrans;
    private TimerTask taskTrans;
    private Timer timerTrans;
    private Handler handler;
    private TimerTask task;
    private Timer timer;
    TextView textViewOpcoesTrasf;
    TextView textViewOpcoesSinal;
    private String allSelectSinal;
    private static String origemTransferencia;
    private static String motivosTransferencia;
    public static final  String NOTE_POSITION="mz.unilurio.projecto200.NOTE_INFO";
    private Slider para;
    private Slider mSliderDilatation;
    private Switch aSwitchTransfered;

    @NotEmpty
    @Length(min = 3, max = 10)
    private TextView txtNameParturient;
    private TextView txtApelidoParturient;

    @NotEmpty
    @Length(min = 3, max = 10)
    private TextView textApelido;

    private DatePickerDialog.OnDateSetListener dateSetListener;
    private ProgressDialog progressBar;
    private NumberPicker numberPicker1;
    private NumberPicker numberPicker2;
    private Spinner spinner;
    private TextView textEditAndRegist;
    private NotificationManagerCompat notificationManagerCompat;
    private boolean isTrasferencia;
    private boolean isEdit;
    private static  int newidParturiente=1;// por inicializacao
    private Parturient parturient;
    private CardView cardTransfered;
    private Switch swit;
    private int idParturiente;
    private TextView textContTimer;
    private SharedPreferences sharedPreferences;
    private Notification notification;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_mother);
        firebaseDatabase=FirebaseDatabase.getInstance();

        dbService=new DBService(this);
        DBManager.getInstance().getUserDoctorList().removeAll(DBManager.getInstance().getUserDoctorList());
        DBManager.getInstance().getUserDoctorList().addAll(dbService.getListDoctor());

       // updadeListDoctor();
        aSwitchTransfered=findViewById(R.id.switchTransfered);
        cardTransfered=findViewById(R.id.card7);
        aSwitchTransfered.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(aSwitchTransfered.isChecked()){
                    isTrasferencia=true;
                    cardTransfered.setVisibility(View.VISIBLE);
                }else {
                    isTrasferencia=false;
                    cardTransfered.setVisibility(View.INVISIBLE);
                }
            }
        });




        notificationManagerCompat= NotificationManagerCompat.from(this);
        textEditAndRegist=findViewById(R.id.txtRegisto_Edit);
        initView();
        viewNumber();
        viewnumber2();

        if(getIntent().getStringExtra("idParturiente")!=null){
            idParturiente = Integer.parseInt(getIntent().getStringExtra("idParturiente"));
            for(Parturient parturient: DBManager.getInstance().getAuxlistNotificationParturients()){
                if(parturient.getId()==idParturiente){
                    isEdit=true;
                    textEditAndRegist.setText("Editar Parturiente");
                    editParturiente(parturient);
                    break;
                }
            }

        }

    }



    private void viewnumber2() {
        para.addOnChangeListener(new Slider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull @NotNull Slider slider, float value, boolean fromUser) {
                TextView textNumber=findViewById(R.id.id1);
                textNumber.setText((int)para.getValue()+"");
            }
        });

    }

    @Override
    protected void onResume() {
        verificatioTransferencia();
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        taskTrans.cancel();
        timerTrans.cancel();
    }

    private void viewNumber() {

        mSliderDilatation.addOnChangeListener(new Slider.OnChangeListener(){
            @Override
            public void onValueChange(@NonNull @NotNull Slider slider, float value, boolean fromUser) {
                TextView textNumber=findViewById(R.id.id0);
                textNumber.setText((int)mSliderDilatation.getValue()+"");
            }
        });

    }

    public void editParturiente(Parturient ediParturient){
        int numberPick1;
        int numberPick2;
        String idade="";

        // ENVIANDO 0S DADOS PARA OS CAMPOS//
        txtNameParturient.setText(ediParturient.getName());
        txtApelidoParturient.setText(ediParturient.getSurname());
        idade=String.valueOf(ediParturient.getAge());
        numberPick1=(Integer.parseInt(idade))/10;
        numberPick2=(Integer.parseInt(idade))%10;
        System.out.println(numberPick1+ " ============="+ idade +"==============  "+numberPick2);
        numberPicker1.setValue(numberPick1);
        numberPicker2.setValue(numberPick2);
        para.setValue(ediParturient.getPara());
        mSliderDilatation.setValue((int)Float.parseFloat(ediParturient.getReason()));
        spinner.setSelection(getPositionIdadeGestacional(ediParturient.getGestatinalRange()));
        isTrasferencia =false;

        if(ediParturient.isTransfered()){
            aSwitchTransfered.setChecked(true);
            isTrasferencia =true;
       }

        parturient=ediParturient;
    }


    int getPositionIdadeGestacional(String idadeGestacional){
        int index=0;
        for(String list:DBManager.getInstance().getListIdadeGestacional()){
            if(list.equals(idadeGestacional)){
                return index;
            }
            index++;
        }
        return  0;
    }

    private void initView() {
        transferencia = findViewById(R.id.spinnerOrigem);
        List<String> listSanitaria = DBManager.getInstance().getListOpcoesUnidadeSanitaria();
        ArrayAdapter<String> adapterSanitaria = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listSanitaria);
        adapterSanitaria.setDropDownViewResource(android.R.layout.simple_spinner_item);
        transferencia.setAdapter(adapterSanitaria);

        spinnerMotivos =findViewById(R.id.spinnerMotivos);
        List<String> listTrasferencia = DBManager.getInstance().getListMotivosTrasferencia();
        ArrayAdapter<String> adapterTrasferencia = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listTrasferencia);
        adapterTrasferencia.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerMotivos.setAdapter( adapterTrasferencia );


        textViewOpcoesTrasf=findViewById(R.id.viewOpcoesTransferencia);
        textViewOpcoesSinal=findViewById(R.id.sinal);
        txtApelidoParturient=findViewById(R.id.txtNameApelido);
        numberPicker1 = findViewById(R.id.numberPickerTwo);
        numberPicker2 = findViewById(R.id.numberPickerOne);
        txtNameParturient = findViewById(R.id.txtName);
        numberPicker1 = findViewById(R.id.numberPickerOne);
        numberPicker2 = findViewById(R.id.numberPickerTwo);
        para = findViewById(R.id.para);
        para.setValueTo(dbService.getOpcoesParidadeMaximoValor());
        mSliderDilatation = findViewById(R.id.dilatation);
        swit = findViewById(R.id.switch1);

        spinner = findViewById(R.id.spinner_gestRange);
        List<String> list = getListIdadeGestacional();
        ArrayAdapter<String> adapterGesta = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, list);
        adapterGesta.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapterGesta);
        setUpNumberPickers();
        setUpNumberPickers();

    }

    private List<String> getListIdadeGestacional() {
        ArrayList<String> list=new ArrayList<>();
         for(IdadeGestacional idadeGestacional: dbService.getListIdadeGestacional()){
              list.add(idadeGestacional.getIdadeGestacional());
         }
         return  list;
    }

    @Override
    protected void onPostCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if(savedInstanceState !=null){

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.note, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_send_email) {
//            sendEmail();
//            return true;
//        }



        return super.onOptionsItemSelected(item);
    }

    public void finish(View view) {
        finish();
    }



    public void registar(View view) {

        if(isEdit){
            editParturiente();
        }else{
            addNewParturient();// novo registo
        }
    }

    public  void editParturiente(){

        String mensagem="Editar dados ?";
        String mensagemTitle="EDITAR";;

        AlertDialog.Builder dialog=new AlertDialog.Builder(this);
        dialog.setTitle(mensagemTitle);
        dialog.setMessage(mensagem);
        dialog.setCancelable(false);
        dialog.setIcon(getDrawable(R.drawable.icondilatacao));

        dialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (verificatioNull(txtNameParturient)&& verificatioNull(txtApelidoParturient)&& verificationNumberName(txtApelidoParturient)&& verificationNumberName(txtNameParturient)) {
                    progressBar();
                    parturient.setName(upCaseName(txtNameParturient.getText().toString())+"");
                    parturient.setSurname(upCaseName(txtApelidoParturient.getText().toString()));
                    parturient.setFullName(upCaseName(txtNameParturient.getText().toString())+" "+upCaseName(txtApelidoParturient.getText().toString()));
                    String age = numberPicker1.getValue() + "" + numberPicker2.getValue();
                    parturient.setAge(Integer.parseInt(age));
                    parturient.setTime(new Date());
                    parturient.setReason(mSliderDilatation.getValue()+"");
                    parturient.setGestatinalRange(spinner.getSelectedItem()+"");
                    parturient.setPara((int) para.getValue());
                    if (isTrasferencia) {
                        parturient.setTransfered(true);
                    } else {
                        parturient.setTransfered(false);
                        parturient.setMotivosDaTrasferencia("");
                        parturient.setOrigemTransferencia("");
                    }
                    for (Notification notifica: DBManager.getInstance().getNotifications()){
                        System.out.println(notifica.getId()+" =="+parturient.getId());
                        if(Integer.parseInt(notifica.getId())==idParturiente){
                            notifica.setNome(parturient.getName()+" "+parturient.getSurname());
                        }
                    }
                    verificationDilatation(parturient,String.valueOf(mSliderDilatation.getValue()));

                    //parturient.setReason((int)mSliderDilatation.getValue()+"");
                    //databaseReference = firebaseDatabase.getReference("Parturiente");
                    //databaseReference.child(parturient.getId() + "").setValue(parturient);

                    //DBManager.getInstance().updateQueue((int) mSliderDilatation.getValue());
                    Toast.makeText(getApplicationContext(), " Parturiente Editado com sucesso", Toast.LENGTH_LONG).show();
                }
            }


            private void progressBar() {
                progressBar=new ProgressDialog(AddParturientActivity.this);
                progressBar.setTitle("Aguarde");
                progressBar.setMessage("Registando...");
                progressBar.show();

                new Handler().postDelayed(new Thread() {
                    @Override
                    public void run() {
                        progressBar.dismiss();
                        finish();
                    }
                },Long.parseLong("900"));
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


    public boolean  verificatioNull(TextView textOption){

        if(textOption.getText().toString().equals("")){
            textOption.setError("Preenche o campo");
            return  false;
        }
        return true;
    }

    public void setUpNumberPickers(){

        numberPicker1.setMinValue(1);
        numberPicker1.setMaxValue(5);
        numberPicker1.setOnValueChangedListener(new NumberPicker.OnValueChangeListener(){
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                // tvShowNumbers.setText("Old Value = " + i + " New Value = " + i1);
            }
        });

        numberPicker2.setMinValue(0);
        numberPicker2.setMaxValue(9);
        numberPicker2.setOnValueChangedListener(new NumberPicker.OnValueChangeListener(){
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                // tvShowNumbers.setText("Old Value = " + i + " New Value = " + i1);
            }
        });
    }

    public void addNewParturient() {

        String mensagem="Deseja registar um Parturiente ?";
        String mensagemTitle="REGISTAR";


        AlertDialog.Builder dialog=new AlertDialog.Builder(this);
        dialog.setTitle(mensagemTitle);
        dialog.setMessage(mensagem);
        dialog.setCancelable(false);
        dialog.setIcon(getDrawable(R.drawable.icon_registo));

        dialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                if(verificationNumberName(txtNameParturient) && verificationNumberName(txtApelidoParturient)  && verificatioNull(txtNameParturient)&&  verificatioNull(txtApelidoParturient)) {
                    String age = numberPicker1.getValue() + "" + numberPicker2.getValue();
                    parturient = new Parturient();
                    if (!isExistParturiente(parturient)) {
                        parturient.setTimerEmergence(dbService.getHourasAlert()*3600+dbService.getMinutesAlert()*60);
                        alertaNotification(parturient);
                        parturient.setId(++newidParturiente);
                        parturient.setName(upCaseName(txtNameParturient.getText().toString()));
                        parturient.setSurname(upCaseName(txtApelidoParturient.getText().toString()));
                        parturient.setFullName(upCaseName(txtNameParturient.getText().toString()));
                        parturient.setAge(Integer.parseInt(age));
                        parturient.setReason((int)mSliderDilatation.getValue()+"");
                       if(isTrasferencia){
                            parturient.setTransfered(true);
                            parturient.setOrigemTransferencia(transferencia.getSelectedItem().toString());
                            parturient.setMotivosDaTrasferencia(spinnerMotivos.getSelectedItem().toString());
                        }else {
                            parturient.setOrigemTransferencia("");
                            parturient.setMotivosDaTrasferencia("");
                            parturient.setTransfered(false);
                        }

                        parturient.setPara((int) para.getValue());
                        parturient.setHoraEntrada(format(new Date()));
                        parturient.setGestatinalRange(spinner.getSelectedItem()+"");
                        parturient.setHoraParte(Integer.parseInt(formatHoras(new Date())));
                        parturient.setMinutoParte(Integer.parseInt(formatMinuto(new Date())));
                        parturient.setSegundoParte(Integer.parseInt(formatSegundo(new Date())));
                        parturient.setHoraAtendimento(format(new Date()));
                        parturient.setDestinoTrasferencia(" ");
                        parturient.setMotivosDestinoDaTrasferencia(" ");

                        SelectSinal selectSinal=new SelectSinal();
                        if(!selectSinal.allNameselect.equals("Nenhum")){
                            Notification notification =new Notification();
                            notification.setColour(Color.rgb(248, 215,218));
                            notification.setNome(parturient.getName()+" "+parturient.getSurname());
                            notification.setIdParturiente(newidParturiente+""+new Date());
                            notification.setInProcess(false);
                            notification.setTime(format(new Date()));
                            notification.setOpen(true);
                            notification.setId(newidParturiente+"");
                            notification.setInProcess(false);
                            DBManager.getInstance().getNotifications().add(notification);
                            DBManager.getInstance().getAuxlistNotificationParturients().add(parturient);
                            Toast.makeText(getApplicationContext(), " Parturiente Enviando para lista de Notificoes", Toast.LENGTH_LONG).show();
                            selectSinal.allNameselect="Nenhum";
                            for(UserDoctor userDoctor: DBManager.getInstance().getUserDoctorList()){
                                sendSMS(userDoctor.getContacto(),parturient.getName()+" "+parturient.getSurname()+" Necessita de cuidados medico");
                            }
                            finish();
                        }else{
                            ordeList();
                            initializeCountDownTimer(parturient,dbService.getTimerDilatation(parturient.getReason()));
//                            initializeCountDownTimer(parturient,dbService.getTimerDilatation(parturient.getReason()));
                            DBManager.getInstance().getParturients().add(parturient);
                            DBManager.getInstance().getAuxlistNotificationParturients().add(parturient);
                            ordeList();
                            progressBar();
                            Toast.makeText(getApplicationContext(), " Parturiente Registado com sucesso", Toast.LENGTH_LONG).show();

                        }


                        //dbService.addParturiente(parturient);
                        //dbService.updadeListParturiente();
                   } else {
                           alertaParturienteExist();
                    }
                }else{
                    Toast.makeText(getApplicationContext(), " O Nome não pode conter números", Toast.LENGTH_LONG).show();
                }
            }
            private void progressBar() {
                progressBar=new ProgressDialog(AddParturientActivity.this);
                progressBar.setTitle("Aguarde");
                progressBar.setMessage("Registando...");
                progressBar.show();

                new Handler().postDelayed(new Thread() {
                    @Override
                    public void run() {
                        progressBar.dismiss();
                         finish();
                    }
                },Long.parseLong("900"));
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

    private void popNotification(Parturient parturientNotificatio) {
        String mensagem=oUpperFirstCase(parturientNotificatio.getName()) +" "+oUpperFirstCase(parturientNotificatio.getSurname())+" necessita  de cuidados medicos";
        Intent activitIntent=new Intent(this, MainActivity.class);
        PendingIntent contxtIntent=PendingIntent.getActivity(this,0,activitIntent,0);
        android.app.Notification notification1=new NotificationCompat.Builder(this, App.CHANNEL_1_ID)
                .setSmallIcon(R.drawable.mulhergravidabom2)
                .setContentTitle("Alerta")
                .setColor(Color.GREEN)
                .setAutoCancel(true)
                .setContentText(mensagem)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setContentIntent(contxtIntent)
                .build();
        notificationManagerCompat.notify(Integer.parseInt(parturientNotificatio.getId()+""),notification1);

    }

    void alertaNotification(Parturient parturient){

        TimerTask taskMinutos;
        Handler handlerMinutos;
        handlerMinutos = new Handler();
        Timer timerMinutos = new Timer();
        taskMinutos = new TimerTask() {
            @Override
            public void run() {

                handlerMinutos.post(new Runnable() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    public void run() {
                        try {
                            if(parturient.getTempoRest().equals("Alerta Disparado")){
                                popNotification(parturient);
                                timerMinutos.cancel();
                            }

                        } catch (Exception e) {
                            // error, do something
                        }
                    }
                });
            }
        };

        timerMinutos.schedule(taskMinutos, 0, 1000);  // interval of one minute

    }


    public void alertaParturienteExist(){

        String mensagem="A parturiente "+oUpperFirstCase(parturient.getFullName())+" ja foi registada...";


        AlertDialog.Builder dialog=new AlertDialog.Builder(this);
        dialog.setTitle("Alerta");
        dialog.setMessage(mensagem);
        dialog.setCancelable(false);
        dialog.setIcon(getDrawable(R.drawable.error));

        dialog.setPositiveButton("ok", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        dialog.setNegativeButton("", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        dialog.create();
        dialog.show();
    }

    public boolean isExistParturiente(Parturient newParturient){
           List<Parturient> list=DBManager.getInstance().getParturients();
           for(Parturient parturientSeacher:list){
               if(parturientSeacher.getName().equalsIgnoreCase(newParturient.getName())){
                  return true;
               }
           }
        return false;
    }



    public boolean verificationNumberName(TextView txtnome){

       String nome=txtnome.getText().toString();

           for (int i = 0; i < nome.length(); i++) {
               char i1 = nome.charAt(i);
               if (i1 <= '9' && i1 >= '0') {
                   txtnome.setError("Não pode conter números");
                   return false;
               }
           }

    return true;
    }

    private String format(Date date){
        DateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");
        return dateFormat.format(date);
    }



    private void updadeListDoctor() {

        handler = new Handler();
        timer = new Timer();

        task = new TimerTask() {
            @Override
            public void run() {
                EditDoctorClass e =new EditDoctorClass();
                AddDoctorClass add=new AddDoctorClass();

                handler.post(new Runnable() {
                    public void run() {
                        try {

                           // System.out.println(" lista=============  : "+listDoctor);
                        } catch (Exception e) {
                            // error, do something
                        }
                    }
                });
            }
        };
        timer.schedule(task, 0, 500);  // interval of one minute

    }

    public void verificationDilatation(Parturient parturient, String dilatacao){

      if ((parturient.getReason())!=dilatacao){
          for(Parturient parturient1:DBManager.getInstance().getParturients()){
              if(parturient1.getId()==parturient.getId()){
                   parturient.setReason(dilatacao+"");
                   parturient.setInitializeTimerAlert(dbService.getTimerDilatation(dilatacao+""));
                   parturient.isEditDilatation=true;
              }
          }
     }



    }

    public String upCaseName(String name){

       name=oUpperFirstCase(name);
        String auxStr="";
        int i=0;

        for( i=0;i<name.length();i++){
          if(i<name.length()){
              if(!(name.charAt(i)==' ' && i+1==name.length()))
              if(name.charAt(i)==' ' && name.charAt(i+1)!=' '){
                  auxStr=auxStr+(" ");
                  auxStr=auxStr+(name.charAt(i+1)+"").toUpperCase();
                  i++;
              }else {
                  auxStr=auxStr+(name.charAt(i)+"");
              }
          }
        }
       /// auxStr=auxStr+(name.charAt(i)+"");
        return auxStr;
    }

    public  String oUpperFirstCase(String string){
        String auxString=(string.charAt(0)+"").toUpperCase()+""+string.substring(1)+"";
        return  auxString;
    }

    public void ordeList(){ // o primeiro a entrar fica no topo
           ArrayList<Parturient> list = new ArrayList<>();
           for(int i=(DBManager.getInstance().getParturients().size()-1);i>=0;i--){
               list.add(DBManager.getInstance().getParturients().get(i));
           }
            DBManager.getInstance().getParturients().removeAll(DBManager.getInstance().getParturients());
            DBManager.getInstance().getParturients().addAll(list);
    }
    private String formatMinuto(Date date){
        DateFormat dateFormat = new SimpleDateFormat("mm");
        return dateFormat.format(date);
    }
    private String formatSegundo(Date date){
        DateFormat dateFormat = new SimpleDateFormat("ss");
        return dateFormat.format(date);
    }

    private String formatHoras(Date date){
        DateFormat dateFormat = new SimpleDateFormat("hh");
        return dateFormat.format(date);
    }

    public void selectSinaisEmergencia(View view) {
        SelectSinal selectSinal= new SelectSinal();
        selectSinal.show(getSupportFragmentManager(), "Selecionar");
    }

    public void selectTransferencia(View view) {
        SelectTransferencia selectTransferencia= new SelectTransferencia();
        selectTransferencia.show(getSupportFragmentManager(), "Selecionar Transferencia");
    }


    private void verificatioTransferencia() {

        handlerTrans = new Handler();
        timerTrans = new Timer();

        taskTrans = new TimerTask() {
            @Override
            public void run() {
                SelectTransferencia e =new SelectTransferencia();
                SelectSinal selectSinal=new SelectSinal();

                handlerTrans.post(new Runnable() {
                    public void run() {
                        try {
                         if(e.isAcceptTransference){
                                e.isAcceptTransference=false;
                                origemTransferencia=e.opcaoSanitaria;
                                motivosTransferencia=e.motivoTransferencia;
                                textViewOpcoesTrasf.setText("Origem da transferencia : "+origemTransferencia+ ", Motivos da transferencia : "+motivosTransferencia);
                            }
                         if(selectSinal.isSelectetOptionSanitaria){
                             selectSinal.isSelectetOptionSanitaria=false;
                             allSelectSinal=selectSinal.allNameselect;
                             textViewOpcoesSinal.setText(allSelectSinal);
                         }
                        } catch (Exception e) {
                            // error, do something
                        }
                    }
                });
            }
        };
        timerTrans.schedule(taskTrans, 0, 500);  // interval of one minute

    }
    int getPositionISanitaria(String sanitaria){
        int index=0;
        for(String list:DBManager.getInstance().getListOpcoesUnidadeSanitaria()){
            if(list.equals(sanitaria)){
                return index;
            }
            index++;
        }
        return  0;
    }
    int getPositionMotivosTrasferencia(String origem){
        int index=0;
        for(String list:DBManager.getInstance().getListMotivosTrasferencia()){
            if(list.equals(origem)){
                return index;
            }
            index++;
        }
        return  0;
    }

    private void sendSMS(String phoneNumber, String message) {
        phoneNumber = phoneNumber.trim();
        message = message.trim();
        System.out.println(message);
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, message, null, null);

        } catch (Exception e) {
            Log.i("EXPECTION SMS", e.getMessage());
        }
    }

 // criando notificacao

    void sendNotification(Parturient parturient){
        notification = new Notification();
        notification.setColour(Color.YELLOW+Color.BLACK);
        notification.setNome(parturient.getName()+" "+parturient.getSurname());
        notification.setIdParturiente(parturient.getId()+""+new Date());
        notification.setTime(format(new Date()));
        notification.setOpen(true);
        notification.setId(parturient.getId()+"");
        notification.setInProcess(false);
        DBManager.getInstance().getNotifications().add(notification);
    }

    public void initializeCountDownTimer(Parturient parturient,int seconds) {

        new CountDownTimer(seconds*1000+1000, 1000) {

            public void onTick(long millisUntilFinished) {
                int seconds = (int) (millisUntilFinished / 1000);

                int hours = seconds / (60 * 60);
                int tempMint = (seconds - (hours * 60 * 60));
                int minutes = tempMint / 60;
                seconds = tempMint - (minutes * 60);

                parturient.setTempoRes("Tempo Restante : " + String.format("%02d", hours)
                        + ":" + String.format("%02d", minutes)
                        + ":" + String.format("%02d", seconds));
            }

            public void onFinish() {
                parturient.setTempoRes("Alerta Disparado");
                isFireAlert=true;
                sendNotification(parturient);
                alertaEmergence(parturient,dbService.getTimerAlertEmergenceDilatation());
            }
        }.start();
    }

    public void alertaEmergence(Parturient parturient,int seconds) {

        new CountDownTimer(seconds * 1000 + 1000, 1000) {

            public void onTick(long millisUntilFinished) {
                int seconds = (int) (millisUntilFinished / 1000);

                int hours = seconds / (60 * 60);
                int tempMint = (seconds - (hours * 60 * 60));
                int minutes = tempMint / 60;
                seconds = tempMint - (minutes * 60);

            }

            public void onFinish() {
                isFireAlert=true;
                removParturiente(parturient);
                sendMensageEmergence();
            }
        }.start();
    }

    public void removParturiente(Parturient prt){
        for(Parturient parturient: DBManager.getInstance().getParturients()){
            if(parturient.getId()==prt.getId()){
                DBManager.getInstance().getParturients().remove(parturient);
                break;
            }
        }
        isFireAlert=true;

    }
    private void sendMensageEmergence(){
            notification.setColour(Color.rgb(248, 215,218));
            String mensagem=notification.getMessage() +": Necessita  de cuidados medicos";
            System.out.println(mensagem);
            for(UserDoctor userDoctor: dbService.getListDoctor()){
                sendSMS(userDoctor.getContacto(),mensagem);
            }

    }
}
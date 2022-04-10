package mz.unilurio.solidermed;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.material.slider.Slider;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;


import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import mz.unilurio.solidermed.model.App;
import mz.unilurio.solidermed.model.DBManager;
import mz.unilurio.solidermed.model.DBService;
import mz.unilurio.solidermed.model.EscolhaSinaisPatologia;
import mz.unilurio.solidermed.model.IdadeGestacional;
import mz.unilurio.solidermed.model.Notificacao;
import mz.unilurio.solidermed.model.Parturient;
import mz.unilurio.solidermed.model.Patologia;
import mz.unilurio.solidermed.model.SelectSinal;
import mz.unilurio.solidermed.model.SelectTransferencia;
import mz.unilurio.solidermed.model.UserDoctor;

public class AddParturientActivity extends AppCompatActivity{
    private static int valueIdade1=1;
    private  static boolean isEditNotification;
    private static int valueIdade2=0;
    public static boolean alertFireNotification;
    public static String idParturienteNotification;
    public  static boolean isFireAlert=false;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private DBService dbService;
    TextView textNumber;
    private Vibrator vibrator;
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
    private String allSelectSinal="Nenhum";
    private static String origemTransferencia;
    private static String motivosTransferencia;
    public static final  String NOTE_POSITION="mz.unilurio.projecto200.NOTE_INFO";
    private static Slider para;
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
    Patologia patologia1= new Patologia();
    private  TextView textFase;
    private String idParturiente;
    private TextView textContTimer;
    private SharedPreferences sharedPreferences;
    private Notificacao notificacao;
    private String auxData;
    private String mensagem;
    private SelectSinal selectSinal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_mother);
        firebaseDatabase=FirebaseDatabase.getInstance();
        dbService=new DBService(this);
        parturient=new Parturient();
        initView();

        DBManager.getInstance().getUserDoctorList().removeAll(DBManager.getInstance().getUserDoctorList());
        DBManager.getInstance().getUserDoctorList().addAll(dbService.getListDoctor());
        textViewOpcoesSinal.setText(""+getListPatologia());
        mSliderDilatation.setValue(4);
        textNumber.setText("4");
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

        viewNumber();
        viewnumber2();

        if(getIntent().getStringExtra("idParturiente")!=null){
            idParturiente =(getIntent().getStringExtra("idParturiente"));
            for(Parturient parturient: dbService.getListAuxParturiente()){
                if(parturient.getIdAuxParturiente().equals(idParturiente)){
                    isEdit=true;
                    textEditAndRegist.setText("Editar Parturiente");
                    editParturiente(parturient);
                    break;
                }
            }

        }


        if(getIntent().getStringExtra("idParturienteNotificacao")!=null){
            idParturiente =(getIntent().getStringExtra("idParturienteNotificacao"));
            for(Parturient parturient: dbService.getListAuxParturiente()){
                if(parturient.getIdAuxParturiente().equals(idParturiente)){
                    isEdit=true;
                    isEditNotification=true;
                    textEditAndRegist.setText("Editar Parturiente");
                    editParturiente(parturient);
                    break;
                }
            }

        }

    }




    private void viewnumber2() {
       // selectSinal = new SelectSinal();
       // selectSinal.addOptionSelect(6,"Primigesta com idade inferior a 16 anos");
       // selectSinal.setIsSelected2(true);

        Patologia patologia2= new Patologia();
        para.addOnChangeListener(new Slider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull @NotNull Slider slider, float value, boolean fromUser) {
                TextView textNumber=findViewById(R.id.id1);
                textNumber.setText((int)para.getValue()+"");


                if((int)para.getValue()==0 && Integer.parseInt(valueIdade1+""+valueIdade2)<16){
                    ArrayList<Patologia> patologiaArrayList=new ArrayList<>();
                    patologiaArrayList.addAll(dbService.getListPatologia());

                    for(int i=0;i<patologiaArrayList.size();i++){
                        if(patologiaArrayList.get(i).getPatologia().equalsIgnoreCase("Primigesta com idade inferior a 16 anos")){
                            patologia1.setId(patologiaArrayList.get(i).getId());
                        }
                    }
                    patologia1.setSelected(true);
                    patologia1.setPatologia("Primigesta com idade inferior a 16 anos");
                    System.out.println("entrou-------------------------------------");
                    DBManager.getInstance().addPatologia(patologia1);
                   // DBManager.getInstance().getSinaisPatologiaList().add(patologia1);
                    // allSelectSinal="Primigesta com idade inferior a 16 anos";
                      textViewOpcoesSinal.setText(getListPatologia()+"");
                }else{
                    DBManager.getInstance().getSinaisPatologiaList().remove(patologia1);
                    textViewOpcoesSinal.setText(getListPatologia()+"");
                }

                if((int)para.getValue()>=6){
                    ArrayList<Patologia> patologiaArrayList=new ArrayList<>();
                    patologiaArrayList.addAll(dbService.getListPatologia());

                    for(int i=0;i<patologiaArrayList.size();i++){
                        if(patologiaArrayList.get(i).getPatologia().equalsIgnoreCase("Teve 6 ou mais partos (grande multipar)")){
                            patologia2.setId(patologiaArrayList.get(i).getId());
                        }
                    }
                    patologia2.setSelected(true);
                    patologia2.setPatologia("Teve 6 ou mais partos (grande multipar)");
                    DBManager.getInstance().addPatologia(patologia2);
                    textViewOpcoesSinal.setText(getListPatologia()+"");
                }else {
                    DBManager.getInstance().getSinaisPatologiaList().remove(patologia2);
                    textViewOpcoesSinal.setText(getListPatologia()+"");
                }
            }
        });

    }

    public String getListPatologia(){
           String nomePatologia="";

           for(int i=0;i<DBManager.getInstance().getSinaisPatologiaList().size();i++){
                 nomePatologia=nomePatologia+""+(i+1)+"-"+DBManager.getInstance().getSinaisPatologiaList().get(i).getPatologia()+";"+'\n';
           }
           if(DBManager.getInstance().getSinaisPatologiaList().isEmpty()){
               System.out.println("dddd111 : "+ DBManager.getInstance().getSinaisPatologiaList());

               return "Nenhum";
           }else {
               System.out.println("dddd : "+ DBManager.getInstance().getSinaisPatologiaList());
               return nomePatologia;
           }

    }

    @Override
    protected void onResume() {
        verificatioTransferencia();
        alertaNotification();
        super.onResume();

        if(!DBManager.getInstance().getSinaisPatologiaList().isEmpty()){
            textViewOpcoesSinal.setText(""+getListPatologia());
        }else {

            textViewOpcoesSinal.setText(""+parturient.getSinaisDePatologia());

        }

//        if(!getListPatologia().equalsIgnoreCase("Nenhum")){
//
//        }else {
//           // textViewOpcoesSinal.setText(""+getListPatologia());
//        }

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
                textNumber.setText((int)mSliderDilatation.getValue()+"");
                int fase=(int)mSliderDilatation.getValue();
                if(fase>3){
                    textFase.setTextColor(Color.RED);
                    textFase.setText(" na fase activa");
                }else{
                    textFase.setTextColor(Color.BLACK);
                    textFase.setText(" na fase latente");
                }
 
            }
        });

    }

    public void editParturiente(Parturient ediParturient){
        int numberPick1;
        int numberPick2;
        String idade="";

        // ENVIANDO 0S DADOS PARA OS CAMPOS//
        textViewOpcoesSinal.setText(ediParturient.getSinaisDePatologia());
        txtNameParturient.setText(ediParturient.getName());
        txtApelidoParturient.setText(ediParturient.getSurname());
        idade=String.valueOf(ediParturient.getAge());
        numberPick1=(Integer.parseInt(idade))/10;
        numberPick2=(Integer.parseInt(idade))%10;
        System.out.println(numberPick1+ " ============="+ idade +"==============  "+numberPick2);
        numberPicker1.setValue(numberPick1);
        numberPicker2.setValue(numberPick2);
        para.setValue(ediParturient.getPara());
        mSliderDilatation.setValue((ediParturient.getReason()));
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
        textNumber=findViewById(R.id.id0);
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

        textFase= findViewById(R.id.txtFase);
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
        System.out.println(" dddd ==================: "+DBManager.getInstance().getSinaisPatologiaList());
       //
        //textViewOpcoesSinal.setText(""+getListPatologia());
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
//        if(!getListPatologia().equalsIgnoreCase("Nenhum")){
//            textViewOpcoesSinal.setText(""+getListPatologia());
//        }
    }

    public void registar(View view) {

        if(isEdit){
            editParturiente();
        }else{
            addNewParturient();// novo registo
        }
    }


    public boolean  verificatioNull(TextView textOption){

        if(textOption.getText().toString().equals("")){
            textOption.setError("Preenche o campo");
            return  false;
        }
        return true;
    }

    public void setUpNumberPickers(){
        selectSinal = new SelectSinal();
        numberPicker1.setMinValue(1);
        numberPicker1.setMaxValue(5);
        numberPicker1.setOnValueChangedListener(new NumberPicker.OnValueChangeListener(){
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                // tvShowNumbers.setText("Old Value = " + i + " New Value = " + i1);
                valueIdade1=numberPicker.getValue();
                if(((int)para.getValue()==0) && (Integer.parseInt(valueIdade1+""+valueIdade2)<16)){
                    ArrayList<Patologia> patologiaArrayList=new ArrayList<>();
                    patologiaArrayList.addAll(dbService.getListPatologia());

                    for(int i2=0;i2<patologiaArrayList.size();i2++){
                        if(patologiaArrayList.get(i2).getPatologia().equalsIgnoreCase("Primigesta com idade inferior a 16 anos")){
                            patologia1.setId(patologiaArrayList.get(i2).getId());
                        }
                    }
                    patologia1.setSelected(true);
                    patologia1.setPatologia("Primigesta com idade inferior a 16 anos");
                    System.out.println("entrou-------------------------------------");
                    DBManager.getInstance().addPatologia(patologia1);
                    textViewOpcoesSinal.setText(getListPatologia()+"");
                }else{
                    DBManager.getInstance().getSinaisPatologiaList().remove(patologia1);
                    textViewOpcoesSinal.setText(getListPatologia()+ "");
                }
            }
        });

        numberPicker2.setMinValue(0);
        numberPicker2.setMaxValue(9);
        numberPicker2.setOnValueChangedListener(new NumberPicker.OnValueChangeListener(){
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                // tvShowNumbers.setText("Old Value = " + i + " New Value = " + i1);
                valueIdade2=numberPicker.getValue();
                if(((int)para.getValue()==0) && (Integer.parseInt(valueIdade1+""+valueIdade2)<16)){
                    ArrayList<Patologia> patologiaArrayList=new ArrayList<>();
                    patologiaArrayList.addAll(dbService.getListPatologia());

                    for(int i2=0;i2<patologiaArrayList.size();i2++){
                        if(patologiaArrayList.get(i2).getPatologia().equalsIgnoreCase("Primigesta com idade inferior a 16 anos")){
                            patologia1.setId(patologiaArrayList.get(i2).getId());
                        }
                    }
                    patologia1.setSelected(true);
                    patologia1.setPatologia("Primigesta com idade inferior a 16 anos");
                    System.out.println("entrou-------------------------------------");
                    DBManager.getInstance().addPatologia(patologia1);
                    textViewOpcoesSinal.setText(getListPatologia()+"");
                }else{
                    DBManager.getInstance().getSinaisPatologiaList().remove(patologia1);}
                    textViewOpcoesSinal.setText(getListPatologia()+"");
            }
        });
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
                    parturient.setSinaisDePatologia(getListPatologia());
                    parturient.setTime(new Date());
                    parturient.setTempoRes("Alerta Disparado");
                    int dilatacao=(int)Float.parseFloat(mSliderDilatation.getValue()+"");
                    System.out.println(parturient.getReason()+" :----- :"+((int)Float.parseFloat(mSliderDilatation.getValue()+"")));

                    parturient.setGestatinalRange(spinner.getSelectedItem()+"");
                    parturient.setPara((int) para.getValue());

                    if (isTrasferencia) {
                        parturient.setTransfered(true);
                        parturient.setOrigemTransferencia(transferencia.getSelectedItem().toString());
                        parturient.setMotivosDaTrasferencia(spinnerMotivos.getSelectedItem().toString());
                    } else {
                        parturient.setTransfered(false);
                        parturient.setMotivosDaTrasferencia("");
                        parturient.setOrigemTransferencia("");
                    }
                    try {
                        parturient.setHoraExpulsoDoFeto(getTempoExpulso(dbService.getTimerDilatation(dilatacao+"")));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    if(isEditNotification){
                        if(parturient.getReason()!=dilatacao){
                            parturient.setReason(dilatacao);
                            try {
                                updataDataOfDilatacao(parturient);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }else {
                            parturient.setEditDilatation(false);
                            dbService.updadeAllDadeParturiente(parturient);
                        }
                    }else {
                        if(parturient.getReason()!=dilatacao){
                            parturient.setEditDilatation(true);
                            parturient.setReason(dilatacao);
                            Toast.makeText(getApplicationContext(), " Parturiente Editado com sucesso", Toast.LENGTH_LONG).show();
                            dbService.updadeAllDadeParturiente(parturient);
                            try {
                                dbService.initializeListParturiente();
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }else {
                            parturient.setEditDilatation(false);

                           // dbService.initializeListParturientesAtendidos();
//                            try {
//                             //   dbService.initializeListParturiente();
//                            } catch (ParseException e) {
//                                e.printStackTrace();
//                            }
//                            try {
//                               // dbService.initializeListNotification();
//                            } catch (ParseException e) {
//                                e.printStackTrace();
//                            }
                       }

                    }

                    for (Notificacao notifica: DBManager.getInstance().getNotifications()){
                        System.out.println(notifica.getIdAuxParturiente()+" =="+parturient.getIdAuxParturiente());
                        if((notifica.getIdAuxParturiente()).equals(idParturiente)){
                            notifica.setNome(parturient.getName()+" "+parturient.getSurname());
                        }
                    }
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

    private void updataDataOfDilatacao(Parturient parturient) throws ParseException {
        isEditNotification=false;

        for (int i=0;i<DBManager.getInstance().getNotifications().size();i++){
                 if(DBManager.getInstance().getNotifications().get(i).getIdAuxParturiente().equalsIgnoreCase(parturient.getIdAuxParturiente())){
                     DBManager.getInstance().getNotifications().remove(i);
                 }
        }
        dbService.deleteNotification(parturient.getIdAuxParturiente());
        dbService.deleteParturienteInAuxList(parturient.getIdAuxParturiente());
        dbService.deleteParturiente(parturient.getIdAuxParturiente());
        dbService.addParturiente(parturient);
        dbService.initializeListNotification();

    }





    public void addNewParturient() {
        String mensagem="Deseja registar uma Parturiente ?";
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
                    int dilatacao=(int)Float.parseFloat(mSliderDilatation.getValue()+"");
                    parturient = new Parturient();

                    if (!isExistParturiente(parturient)) {
                        //parturient.setTimerEmergence(dbService.getHourasAlert()*3600+dbService.getMinutesAlert()*60);
                        parturient.setName(upCaseName(txtNameParturient.getText().toString()));
                        parturient.setSurname(upCaseName(txtApelidoParturient.getText().toString()));
                        // parturient.setFullName(upCaseName(txtNameParturient.getText().toString()));
                        parturient.setAge(Integer.parseInt(age));
                        parturient.setReason(dilatacao);
                        if(isTrasferencia){
                            parturient.setTransfered(true);
                            parturient.setOrigemTransferencia(transferencia.getSelectedItem().toString());
                            parturient.setMotivosDaTrasferencia(spinnerMotivos.getSelectedItem().toString());
                        }else {
                            parturient.setOrigemTransferencia("");
                            parturient.setMotivosDaTrasferencia("");
                            parturient.setTransfered(false);
                        }

                        String codigo="";
                        Random gerador = new Random();

                        for (int i = 0; i <6; i++) {
                            codigo=codigo+gerador.nextInt(30);
                        }
                        parturient.setIdAuxParturiente(codigo);

                        parturient.setDisparoAlerta(false);
                        parturient.setPara((int) para.getValue());
                        parturient.setHoraEntrada(formatd(new Date()));
                        parturient.setGestatinalRange(spinner.getSelectedItem()+"");

                        try {
                            parturient.setHoraExpulsoDoFeto(getTempoExpulso(dbService.getTimerDilatation(dilatacao+"")));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        parturient.setHoraAtendimento(format(new Date()));
                        parturient.setAtendimento(false);
                        String auxData=new Date().getYear()+""+new Date().getMonth()+""+new Date().getDay();
                        parturient.setSetYearDayMonthNotification(auxData);
                        parturient.setDestinoTrasferencia(" ");
                        parturient.setMotivosDestinoDaTrasferencia(" ");
                        parturient.setSinaisDePatologia(getListPatologia()+"");

                            ordeList();
                        try {
                            dbService.addParturiente(parturient);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                            ordeList();
                            Toast.makeText(getApplicationContext(), " Parturiente Registado com sucesso", Toast.LENGTH_LONG).show();
                            DBManager.getInstance().getSinaisPatologiaList().removeAll(DBManager.getInstance().getSinaisPatologiaList());
                        finish();
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
                },Long.parseLong("1"));
            }
        });
        dialog.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext()," operação cancelada ",Toast.LENGTH_LONG).show();

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



    public void alertaNotification(){
//        public static boolean alertFireNotification;
//        public static String nameAlerteParturiente="";
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
                            if(alertFireNotification){

                                for(Parturient parturient1: DBManager.getInstance().getAuxlistNotificationParturients()){
                                    if(idParturienteNotification.equals(parturient1.getIdAuxParturiente())){
                                        popNotification(parturient1);
                                        vibrador();
                                        alertFireNotification=false;
                                    }
                                }

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

    public void vibrador() {
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        if (!vibrator.hasVibrator()) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.BASE) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(VibrationEffect.createOneShot(1000, VibrationEffect.DEFAULT_AMPLITUDE)
                );
            }

        }
        long[] patter = {0, 1000, 10, 5000};
        vibrator.vibrate(patter, -1);
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
        List<Parturient> list=dbService.getListAuxParturiente();
        for(Parturient parturientSeacher:list){
            if((parturientSeacher.getName() +" "+parturientSeacher.getSurname()).
                    equalsIgnoreCase(newParturient.getName()+ " "+
                            newParturient.getSurname())){
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


    private String formatd(Date date){
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy-HH:mm:ss");

        return dateFormat.format(date);
    }
    private String format(Date date){
        DateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");
        return dateFormat.format(date);
    }

    public void selectSinaisEmergencia(View view) {

        startActivity(new Intent(AddParturientActivity.this,Activity_SelectPatologia.class));

//        SelectSinal selectSinal= new SelectSinal();
//        selectSinal.show(getSupportFragmentManager(), "Selecionar");
   }
    public  String getTempoExpulso(int seguntos) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy-HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.SECOND,seguntos);
        return formatd(calendar.getTime());
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
                                allSelectSinal="";
                                for(EscolhaSinaisPatologia e: selectSinal.arrayList){
                                    allSelectSinal=allSelectSinal+e.getNomeSinal()+ ", ";
                                }
                                textViewOpcoesSinal.setText(getListPatologia()+"");
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



}
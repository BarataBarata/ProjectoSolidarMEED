package mz.unilurio.solidermed;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
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

import java.util.Date;
import java.util.List;

import mz.unilurio.solidermed.model.DBManager;
import mz.unilurio.solidermed.model.Parturient;

public class AddParturientActivity extends AppCompatActivity implements Validator.ValidationListener {
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    public static final  String NOTE_POSITION="mz.unilurio.projecto200.NOTE_INFO";

    private Slider para;
    private Slider mSliderDilatation;

    @NotEmpty
    @Length(min = 3, max = 10)
    private TextView txtNameParturient;

    @NotEmpty
    @Length(min = 3, max = 10)
    private TextView textApelido;

    private DatePickerDialog.OnDateSetListener dateSetListener;
    private ProgressDialog progressBar;
    private NumberPicker numberPicker1;
    private NumberPicker numberPicker2;

    private Spinner spinner;
    private Spinner spinnerSanitaria;
    private Spinner spinnerTrasferencia;
    private TextView textSanitario;
    private TextView textTrasferencia;

    private boolean isTrasferencia;
    private boolean isEdit;
    private static  int newidParturiente=4;// por inicializacao
    private Parturient parturient;

    private Validator validator;
    private boolean optionRegist;
    private String nome="";
    private Switch swit;
    private int idParturiente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_mother);
        firebaseDatabase=FirebaseDatabase.getInstance();

        textSanitario = (TextView)findViewById(R.id.textSanitario);
        textTrasferencia = (TextView)findViewById(R.id.textTrasferencia);
        initView();
        invisible();
        validator = new Validator(this);
        validator.setValidationListener(this);
        viewNumber();
        viewnumber2();

        if(getIntent().getStringExtra("idParturiente")!=null){
            idParturiente = Integer.parseInt(getIntent().getStringExtra("idParturiente"));
            for(Parturient parturient: DBManager.getInstance().getParturients()){
                if(parturient.getId()==idParturiente){
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
        super.onResume();
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
        isEdit=true;
        txtNameParturient.setText(ediParturient.getName());
        textApelido.setText(ediParturient.getSurname());
        idade=String.valueOf(ediParturient.getAge());
        numberPick1=Integer.parseInt(idade.charAt(0)+"");
        numberPick2=Integer.parseInt(idade.charAt(1)+"");
        numberPicker1.setValue(numberPick1);
        numberPicker2.setValue(numberPick2);
        para.setValue(ediParturient.getPara());
        mSliderDilatation.setValue((int)Float.parseFloat(ediParturient.getReason()));
        spinner.setSelection(getPositionIdadeGestacional(ediParturient.getGestatinalRange()));
        isTrasferencia =false;
        if(ediParturient.isTransfered()){
            isTrasferencia =true;
            swit.setChecked(true);
            invisibleView();
            spinnerSanitaria.setSelection(getPositionISanitaria(ediParturient.getOrigemTransferencia()));
            spinnerTrasferencia.setSelection(getPositionMotivosTrasferencia(ediParturient.getMotivosDaTrasferencia()));
        }
        parturient=ediParturient;
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

    private void initView() {
//        numberPicker1 = findViewById(R.id.numberPickerTwo);
//        numberPicker2 = findViewById(R.id.numberPickerOne);
        textApelido = findViewById(R.id.txtSurname);
        txtNameParturient = findViewById(R.id.txtName);
        numberPicker1 = findViewById(R.id.numberPickerOne);
        numberPicker2 = findViewById(R.id.numberPickerTwo);
        para = findViewById(R.id.para);
        mSliderDilatation = findViewById(R.id.dilatation);
        swit = findViewById(R.id.switch1);

        spinner = findViewById(R.id.spinner_gestRange);
        List<String> list = DBManager.getInstance().getListIdadeGestacional();
        ArrayAdapter<String> adapterGesta = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, list);
        adapterGesta.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapterGesta);


        spinnerSanitaria = findViewById(R.id.spinner_gestRangeSanitaria);
        List<String> listSanitaria = DBManager.getInstance().getListOpcoesUnidadeSanitaria();
        ArrayAdapter<String> adapterSanitaria = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listSanitaria);
        adapterSanitaria.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerSanitaria.setAdapter( adapterSanitaria);
        setUpNumberPickers();

        spinnerTrasferencia = findViewById(R.id.spinner_gestRangeTrasferencia);
        List<String> listTrasferencia = DBManager.getInstance().getListMotivosTrasferencia();
        ArrayAdapter<String> adapterTrasferencia = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listTrasferencia);
        adapterTrasferencia.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerTrasferencia.setAdapter( adapterTrasferencia );
        setUpNumberPickers();

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



    public void invisibleView(){
        spinnerSanitaria.setVisibility(View.VISIBLE);
        spinnerTrasferencia.setVisibility(View.VISIBLE);
        textSanitario.setVisibility(View.VISIBLE);
        textTrasferencia.setVisibility(View.VISIBLE);
    }

    public void transferencia(View view) {

        if(isTrasferencia){
            spinnerSanitaria.setVisibility(View.INVISIBLE);
            spinnerTrasferencia.setVisibility(View.INVISIBLE);
            textSanitario.setVisibility(View.INVISIBLE);
            textTrasferencia.setVisibility(View.INVISIBLE);
            isTrasferencia =false;
        }else{

            spinnerSanitaria.setVisibility(View.VISIBLE);
            spinnerTrasferencia.setVisibility(View.VISIBLE);
            textSanitario.setVisibility(View.VISIBLE);
            textTrasferencia.setVisibility(View.VISIBLE);
            isTrasferencia =true;
        }

    }

    public void invisible(){
        spinnerSanitaria.setVisibility(View.INVISIBLE);
        spinnerTrasferencia.setVisibility(View.INVISIBLE);
        textSanitario.setVisibility(View.INVISIBLE);
        textTrasferencia.setVisibility(View.INVISIBLE);
    }


    public void registar(View view) {
        validator.validate();
    }



    public void validationError() {
        optionRegist = true;
        optionRegist = verificatioNull(optionRegist, txtNameParturient);
        optionRegist = verificatioNull(optionRegist, textApelido);
    }


    public boolean  verificatioNull(boolean option, TextView textOption){

        if(!option){
            return false;
        }
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

    @Override
    public void onValidationSucceeded() {

        String isAlertEdit="Editar dados ?";
        String isSimbleRegister="Deseja registar um Parturiente ?";
        String isEditar="EDITAR";
        String isRegistar="REGISTAR";
        String mensagem;
        String mensagemTitle;

        if(isEdit){
            mensagemTitle=isEditar;
            mensagem=isAlertEdit;
        }else{
            mensagem=isSimbleRegister;
            mensagemTitle=isRegistar;
        }

        AlertDialog.Builder dialog=new AlertDialog.Builder(this);
        dialog.setTitle(mensagemTitle);
        dialog.setMessage(mensagem);
        dialog.setCancelable(false);
        dialog.setIcon(getDrawable(R.drawable.icon_registo));

        dialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                validationError();
                progressBar();
                if(!isEdit){
                    parturient = new Parturient();
                    parturient.setId(++newidParturiente);
                }
                parturient.setName(txtNameParturient.getText().toString());
                parturient.setSurname(textApelido.getText().toString());

                String age = numberPicker1.getValue() + "" + numberPicker2.getValue();
                parturient.setAge(Integer.parseInt(age));
                parturient.setTime(new Date());
                parturient.setHoraAlerta(new Date());
                parturient.setHoraEntrada(new Date());
                parturient.setGestatinalRange(spinner.getSelectedItem()+"");
                parturient.setPara((int) para.getValue());
                parturient.setReason(mSliderDilatation.getValue()+"");

                if(isTrasferencia){
                    parturient.setTransfered(true);
                    parturient.setMotivosDaTrasferencia(spinnerTrasferencia.getSelectedItem().toString());
                    parturient.setOrigemTransferencia(spinnerSanitaria.getSelectedItem().toString());
                }else {
                    parturient.setTransfered(false);
                    parturient.setMotivosDaTrasferencia(null);
                    parturient.setOrigemTransferencia(null);
                }

                if(!isEdit){
                    DBManager.getInstance().addParturiente(parturient);
                    databaseReference=firebaseDatabase.getReference("Parturiente");
                    databaseReference.child(parturient.getId()+"").setValue(parturient);
                }
                if(isEdit){
                    databaseReference=firebaseDatabase.getReference("Parturiente");
                    databaseReference.child(parturient.getId()+"").setValue(parturient);
                }


                DBManager.getInstance().updateQueue((int) mSliderDilatation.getValue());
                swit.setChecked(false);
                Toast.makeText(getApplicationContext(), " Parturiente Registado com sucesso", Toast.LENGTH_LONG).show();
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

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);

            // Display error messages
            if (view instanceof EditText) {
                ((EditText) view).setError("Preenche o Campo");
            } else {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            }
        }
    }
}
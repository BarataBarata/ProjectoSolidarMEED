package mz.unilurio.solidermed;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.material.slider.Slider;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;


import org.jetbrains.annotations.NotNull;

import java.util.List;

import mz.unilurio.solidermed.model.DBManager;
import mz.unilurio.solidermed.model.GestatinalRange;
import mz.unilurio.solidermed.model.Parturient;

public class AddParturientActivity extends AppCompatActivity implements Validator.ValidationListener {


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
    private boolean show;

    private Validator validator;
    private boolean optionRegist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_mother);


        String nome=getIntent().getStringExtra("nome");
        if(!nome.isEmpty()){
            TextView textNome=findViewById(R.id.txtName);
            TextView textApelido=findViewById(R.id.txtSurname);
            textNome.setText( getIntent().getStringExtra("nome"));
            textApelido.setText( getIntent().getStringExtra("apelido"));
        }

        textSanitario = (TextView)findViewById(R.id.textSanitario);
        textTrasferencia = (TextView)findViewById(R.id.textTrasferencia);
        initView();
        invisible();
        validator = new Validator(this);
        validator.setValidationListener(this);
        viewNumber();
        viewnumber2();

}
   public int getBackgroundColor(){
        return Color.WHITE;
   }
    public int getTextColor(){
        return Color.BLACK;
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

    private void viewNumber() {

        mSliderDilatation.addOnChangeListener(new Slider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull @NotNull Slider slider, float value, boolean fromUser) {
                TextView textNumber=findViewById(R.id.id0);
                textNumber.setText((int)mSliderDilatation.getValue()+"");
            }
        });

    }

    private void initView() {
        numberPicker1 = findViewById(R.id.numberPickerTwo);
        numberPicker2 = findViewById(R.id.numberPickerOne);
        textApelido = findViewById(R.id.txtSurname);
        txtNameParturient = findViewById(R.id.txtName);
        numberPicker1 = findViewById(R.id.numberPickerOne);
        numberPicker2 = findViewById(R.id.numberPickerTwo);
        para = findViewById(R.id.para);
        mSliderDilatation = findViewById(R.id.dilatation);

        spinner = findViewById(R.id.spinner_gestRange);
        List<GestatinalRange> list = DBManager.getInstance().getGestatinalRange();
        ArrayAdapter<GestatinalRange> adapterGesta = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, list);
        adapterGesta.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapterGesta);


        spinnerSanitaria = findViewById(R.id.spinner_gestRangeSanitaria);
        List<String> listSanitaria = DBManager.getInstance().getListOpcoesUnidadeSanitaria();
        ArrayAdapter<String> adapterSanitaria = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listSanitaria);
        adapterGesta.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerSanitaria.setAdapter( adapterSanitaria);
        setUpNumberPickers();

        spinnerTrasferencia = findViewById(R.id.spinner_gestRangeTrasferencia);
        List<String> listTrasferencia = DBManager.getInstance().getListOpcoesTrasferencia();
        ArrayAdapter<String> adapterTrasferencia = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listTrasferencia);
        adapterGesta.setDropDownViewResource(android.R.layout.simple_spinner_item);
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

    public void transferencia(View view) {

       if(show){
               spinnerSanitaria.setVisibility(View.INVISIBLE);
               spinnerTrasferencia.setVisibility(View.INVISIBLE);
               textSanitario.setVisibility(View.INVISIBLE);
               textTrasferencia.setVisibility(View.INVISIBLE);
               show =false;
        }else{

           spinnerSanitaria.setVisibility(View.VISIBLE);
           spinnerTrasferencia.setVisibility(View.VISIBLE);
           textSanitario.setVisibility(View.VISIBLE);
           textTrasferencia.setVisibility(View.VISIBLE);
           show=true;
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


        AlertDialog.Builder dialog=new AlertDialog.Builder(this);
        dialog.setTitle("REGISTO");
        dialog.setMessage(" Deseja registar um Parturiente ?");
        dialog.setCancelable(false);
        dialog.setIcon(getDrawable(R.drawable.icon_registo));

        dialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                validationError();
                if(optionRegist ) {
                    progressBar();
                    Parturient parturient = new Parturient();
                    parturient.setId(DBManager.getInstance().getTotalPaturient());
                    parturient.setName(txtNameParturient.getText().toString());
                    parturient.setSurname(textApelido.getText().toString());

                    String age = numberPicker1.getValue() + "" + numberPicker2.getValue();
                    parturient.setAge(Integer.parseInt(age));

                    parturient.setGestatinalRange((GestatinalRange) spinner.getSelectedItem());
                    parturient.setPara((int) para.getValue());

                    DBManager.getInstance().addParturiente(parturient);
                    DBManager.getInstance().updateQueue((int) mSliderDilatation.getValue());


                    Toast.makeText(getApplicationContext(), " Parturiente Registado com sucesso", Toast.LENGTH_LONG).show();
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
                             Intent intent = new Intent(AddParturientActivity.this, MainActivity.class);
                             startActivity(intent);
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
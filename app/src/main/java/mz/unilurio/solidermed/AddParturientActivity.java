package mz.unilurio.solidermed;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

//import com.mobsandgeeks.saripaar.ValidationError;
//import com.mobsandgeeks.saripaar.Validator;
//import com.mobsandgeeks.saripaar.annotation.Length;
//import com.mobsandgeeks.saripaar.annotation.Max;
//import com.mobsandgeeks.saripaar.annotation.Min;
//import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.util.List;
import mz.unilurio.solidermed.model.DBManager;
import mz.unilurio.solidermed.model.GestatinalRange;
import mz.unilurio.solidermed.model.Parturient;

public class AddParturientActivity extends AppCompatActivity {

    public static final  String NOTE_POSITION="mz.unilurio.projecto200.NOTE_INFO";
    public static final int POSITION_NOT_SET = -1;
    private static final String TAG = "Date";
    private Toolbar binding;
    private NoteInfo mNote;
    private boolean isNewnote;
    private int mNotePosition;
    private boolean isCancel;
    private String moriginalNoteCoursesId1;
    private String originalNoteTitle;
    private String originalNoteText;


    private TextView txtNameParturient;

    private TextView textApelido;

    private DatePickerDialog.OnDateSetListener dateSetListener;

    private NumberPicker numberPicker1;
    private NumberPicker numberPicker2;

    private Spinner spinner;
    private Spinner spinnerSanitaria;
    private Spinner spinnerTrasferencia;
    private TextView textSanitario;
    private TextView textTrasferencia;
    private boolean show;

    //private Validator validator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_mother);
        textSanitario = (TextView)findViewById(R.id.textSanitario);
        textTrasferencia = (TextView)findViewById(R.id.textTrasferencia);

        initView();
        invisible();
//
//        validator = new Validator(this);
//        validator.setValidationListener(this);

//        readDisplayStateValues();
//        saveOriginalNoteValues();
}

    private void initView() {

        numberPicker1 = findViewById(R.id.numberPickerTwo);
        numberPicker2 = findViewById(R.id.numberPickerOne);
        textApelido = findViewById(R.id.txtSurname);
        txtNameParturient = findViewById(R.id.txtName);
        numberPicker1 = findViewById(R.id.numberPickerOne);
        numberPicker2 = findViewById(R.id.numberPickerTwo);

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
    private void saveOriginalNoteValues() {
        if(isNewnote){
            return;
        }
        moriginalNoteCoursesId1 = mNote.getCourse().getCourseId();
        originalNoteTitle = mNote.getTitle();
        originalNoteText = mNote.getText();

    }

    private void storePreviousNoteValues() {
        CourseInfo course=DataManager.getInstance().getCourse(moriginalNoteCoursesId1);
        mNote.setCourse(course);
        mNote.setTitle(originalNoteTitle);
        mNote.setText(originalNoteText);
    }

    private void readDisplayStateValues() {
//        Intent intent =getIntent();
//        int position=intent.getIntExtra(NOTE_POSITION,POSITION_NOT_SET);
//        isNewnote =position==POSITION_NOT_SET;
//
//        if (isNewnote){
//            createNewNote();
//        }else {
//            mNote = DataManager.getInstance().getNotes().get(position);
//        }
    }

    private void createNewNote() {
        DataManager dm=DataManager.getInstance();
        mNotePosition = dm.createNewNote();
        mNote=dm.getNotes().get(mNotePosition);
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

//    private void moveNext() {
//        saveNote();
//        ++mNotePosition;
//        mNote=DataManager.getInstance().getNotes().get(mNotePosition);
//        saveOriginalNoteValues();
//        displayNote(spinnerHospitais, text_note_text, textNoteTitle);
//        invalidateOptionsMenu();
//    }

//    private void sendEmail() {
//        CourseInfo course=(CourseInfo) spinnerHospitais.getSelectedItem();
//        String subject=textNoteTitle.getText().toString();
//        String text="checkout what I learned in the pluralsight courses\""+course.getTitle() + "\"\n" +text_note_text.getText();
//        Intent intent =new Intent(Intent.ACTION_SEND);
//        intent.setType("");
//        intent.putExtra(Intent.EXTRA_SUBJECT,subject);
//        intent.putExtra(Intent.EXTRA_TEXT,text);
//        startActivity(intent);
//    }

    public void finish(View view) {
        isCancel = true;
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
        //validator.validate();

//        if(numberPicker1.getValue()<1 || numberPicker1.getValue() > 6){
//            numberPicker1.setError("Error");
//        }

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


    //@Override
    public void onValidationSucceeded() {
        AlertDialog.Builder dialog=new AlertDialog.Builder(this);
        dialog.setTitle("REGISTO");
        dialog.setMessage(" Deseja registar um Parturiente ?");
        dialog.setCancelable(false);
        dialog.setIcon(getDrawable(R.drawable.icon_registo));

        dialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Parturient parturient = new Parturient();
                parturient.setId(DBManager.getInstance().getTotalPaturient());
                parturient.setName(txtNameParturient.getText().toString());
                parturient.setSurname(textApelido.getText().toString());

                String age = numberPicker1.getValue()+ ""+numberPicker2.getValue();
                parturient.setAge(Integer.parseInt(age));
//
//                parturient.setGestatinalRange((GestatinalRange) spinner.getSelectedItem());
//                parturient.setPara(para.getProgress());
//
//                DBManager.getInstance().addParturiente(parturient);
//                DBManager.getInstance().updateQueue(mSliderDilatation.getProgress());

                Intent intent = new Intent(AddParturientActivity.this, MainActivity.class);
                startActivity(intent);
//                DBManager.getInstance().addParturiente(txtNameParturient.getText().toString(),textApelido.getText().toString(), (Integer) spinnerIdade.getSelectedItem(),true,String.valueOf(dilatacaseekBar),new Date(),(Integer) spinnerCama.getSelectedItem());
//                Toast.makeText(getApplicationContext()," Parturiente Registado com sucesso",Toast.LENGTH_LONG).show();


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
//
//    @Override
//    public void onValidationFailed(List<ValidationError> errors) {
//        for (ValidationError error : errors) {
//            View view = error.getView();
//            String message = error.getCollatedErrorMessage(this);
//            // Display error messages
//            if (view instanceof EditText) {
//                ((EditText) view).setError(message);
//            } else {
//                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
//            }
//        }
//    }
}
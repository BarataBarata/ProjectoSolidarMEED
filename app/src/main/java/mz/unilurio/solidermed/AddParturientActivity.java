package mz.unilurio.solidermed;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import hearsilent.discreteslider.Dash;
import hearsilent.discreteslider.DiscreteSlider;
import hearsilent.discreteslider.Dot;
import hearsilent.discreteslider.libs.Utils;
import mz.unilurio.solidermed.model.DBManager;


public class NoteActivity extends AppCompatActivity {

    public static final  String NOTE_POSITION="mz.unilurio.projecto200.NOTE_INFO";
    public static final int POSITION_NOT_SET = -1;
    private static final String TAG = "Date";
    private Toolbar binding;
    private NoteInfo mNote;
    private boolean isNewnote;
    private Spinner spinnerHospitais;
    private Spinner spinnerIdade;
    private Spinner spinnerCama;
    private EditText textNoteTitle;
    private EditText text_note_text;
    private static int dilatacaseekBar;
    private int mNotePosition;
    private boolean isCancel;
    private String originalNoteCoursesId;
    private String moriginalNoteCoursesId1;
    private String originalNoteTitle;
    private String originalNoteText;
    private EditText editText;
    private TextView motivoText;
    private TextView textOrigem;
    private Spinner spinner;
    private Spinner spinner4;
    private boolean  show=true;
    private TextView txtNameParturient;
    private TextView textNumeroCama;
    private TextView textIdade;
    private TextView textDilatacao;
//    private SeekBar seekBar;

//    private TextView textseekBar;
    private TextView textApelido;
    private DatePickerDialog.OnDateSetListener dateSetListener;
    private TextView textIdadeSelect;


    private DiscreteSlider mSlider;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        findView();
        setUpView();



        NumberPicker numberPicker1 = findViewById(R.id.numberPicker1);
        numberPicker1.setMinValue(1);
        numberPicker1.setMaxValue(6);
        numberPicker1.setOnValueChangedListener(new NumberPicker.OnValueChangeListener(){
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
               // tvShowNumbers.setText("Old Value = " + i + " New Value = " + i1);
            }
        });

        NumberPicker numberPicker2 = findViewById(R.id.numberPicker2);
        numberPicker2.setMinValue(1);
        numberPicker2.setMaxValue(9);
        numberPicker2.setOnValueChangedListener(new NumberPicker.OnValueChangeListener(){
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                // tvShowNumbers.setText("Old Value = " + i + " New Value = " + i1);
            }
        });

//        seekBar=findViewById(R.id.seekBar);
//        textseekBar=(TextView)findViewById(R.id.textseekbar);


//        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                textseekBar.setText(progress+"xxx");
//                dilatacaseekBar=progress;
//
//            }
//
//
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) {
//
//            }
//
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar) {
//
//            }
//        });




//        binding = findViewById(R.id.toolbar);
//        setSupportActionBar(binding);
//        motivoText = findViewById(R.id.textView4);
//        textOrigem = findViewById(R.id.textOrigemTrasferencia);
//        spinner = findViewById(R.id.spinner_courses);
//        spinner4 = findViewById(R.id.spinner_courses4);
//        motivoText.setVisibility(View.INVISIBLE);
//        textOrigem.setVisibility(View.INVISIBLE);
//        spinner.setVisibility(View.INVISIBLE);
//        spinner4.setVisibility(View.INVISIBLE);





        ViewModelProvider viewModelProvider=new ViewModelProvider(getViewModelStore(),ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()));
        // mViewModel=viewModelProvider.get();



//        spinnerHospitais = findViewById(R.id.spinner_courses);
//        List<CourseInfo> courses=DataManager.getInstance().getCourses();
//        ArrayAdapter<CourseInfo> adapterCourses=new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,courses);
//        adapterCourses.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinnerHospitais.setAdapter(adapterCourses);
//
//        spinnerHospitais = findViewById(R.id.spinner_courses4);
//        List<CourseInfo> courses2=DataManager.getInstance().getCourses();
//        ArrayAdapter<CourseInfo> adapterCourses2=new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,courses);
//        adapterCourses.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner4.setAdapter(adapterCourses2);
//
//        //spinnerIdade = findViewById(R.id.spinner_courses2);
//        List<Integer>idades= DBManager.getInstance().getIdades();
//        ArrayAdapter<Integer> adapterIdade=new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item,idades);
//        adapterCourses.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        //spinnerIdade.setAdapter(adapterIdade);
//
//        spinnerCama = findViewById(R.id.spinner_courses3);
//        List<Integer>numeroCama= DBManager.getInstance().getCamas();
//        ArrayAdapter<Integer> adaptercama=new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item,numeroCama);
//        adapterCourses.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinnerCama.setAdapter(adaptercama);



        readDisplayStateValues();
        saveOriginalNoteValues();

//        textNoteTitle = findViewById(R.id.text_note_title);
//        text_note_text = findViewById(R.id.text_note_text);

        if(!isNewnote)
            displayNote(spinnerHospitais, text_note_text, textNoteTitle);

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

    @Override
    public boolean onPreparePanel(int featureId, @Nullable @org.jetbrains.annotations.Nullable View view, @NonNull @NotNull Menu menu) {
        //MenuItem item=menu.findItem(R.id.action_next);
        int lastNoteIndex=DataManager.getInstance().getNotes().size()-1;
        //item.setEnabled(mNotePosition<lastNoteIndex);

        return super.onPreparePanel(featureId, view, menu);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(isCancel){
            if(isNewnote) {
                DataManager.getInstance().removeNote(mNotePosition);
            }else {
                storePreviousNoteValues();
            }
        }else {
            saveNote();
        }
    }

    private void storePreviousNoteValues() {
        CourseInfo course=DataManager.getInstance().getCourse(moriginalNoteCoursesId1);
        mNote.setCourse(course);
        mNote.setTitle(originalNoteTitle);
        mNote.setText(originalNoteText);
    }

    private void saveNote() {
        mNote.setCourse((CourseInfo) spinnerHospitais.getSelectedItem());
        mNote.setTitle(textNoteTitle.getText().toString());
        mNote.setText(text_note_text.getText().toString());

    }

    private void displayNote(Spinner spinnerCourses, EditText text_note_text, EditText textNoteTitle) {
        List<CourseInfo> courses=DataManager.getInstance().getCourses();
        text_note_text.setText(mNote.getText());
        textNoteTitle.setText(mNote.getTitle());
        int courseIndex=courses.indexOf(mNote.getCourse());
        spinnerCourses.setSelection(courseIndex);
    }

    private void readDisplayStateValues() {
        Intent intent =getIntent();
        int position=intent.getIntExtra(NOTE_POSITION,POSITION_NOT_SET);
        isNewnote =position==POSITION_NOT_SET;

        if (isNewnote){
            createNewNote();
        }else {
            mNote = DataManager.getInstance().getNotes().get(position);
        }
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

    private void moveNext() {
        saveNote();
        ++mNotePosition;
        mNote=DataManager.getInstance().getNotes().get(mNotePosition);
        saveOriginalNoteValues();
        displayNote(spinnerHospitais, text_note_text, textNoteTitle);
        invalidateOptionsMenu();
    }

    private void sendEmail() {
        CourseInfo course=(CourseInfo) spinnerHospitais.getSelectedItem();
        String subject=textNoteTitle.getText().toString();
        String text="checkout what I learned in the pluralsight courses\""+course.getTitle() + "\"\n" +text_note_text.getText();
        Intent intent =new Intent(Intent.ACTION_SEND);
        intent.setType("");
        intent.putExtra(Intent.EXTRA_SUBJECT,subject);
        intent.putExtra(Intent.EXTRA_TEXT,text);
        startActivity(intent);
    }

    public void finish(View view) {
        isCancel = true;
        finish();
    }

    public void transferencia(View view) {

//        if(show){
//            motivoText = findViewById(R.id.textView4);
//            textOrigem = findViewById(R.id.textOrigemTrasferencia);
//            spinner = findViewById(R.id.spinner_courses);
//            motivoText.setVisibility(View.VISIBLE);
//            textOrigem.setVisibility(View.VISIBLE);
//            spinner.setVisibility(View.VISIBLE);
//            spinner4.setVisibility(View.VISIBLE);
//            show=false;
//        }else{
//            motivoText = findViewById(R.id.textView4);
//            textOrigem = findViewById(R.id.textOrigemTrasferencia);
//            spinner = findViewById(R.id.spinner_courses);
//            editText.setVisibility(View.INVISIBLE);
//            textOrigem.setVisibility(View.INVISIBLE);
//            spinner.setVisibility(View.INVISIBLE);
//            spinner4.setVisibility(View.INVISIBLE);
//            show=true;
//        }

    }

    public void registar(View view) {

        AlertDialog.Builder dialog=new AlertDialog.Builder(this);
        dialog.setTitle("REGISTO");
        dialog.setMessage(" Deseja registar um Parturiente ?");
        dialog.setCancelable(false);
        dialog.setIcon(getDrawable(R.drawable.icon_registo));

        dialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                textApelido = (TextView)findViewById(R.id.idText_nome2);
                textIdade=(TextView)findViewById(R.id.idText_nome2);
                textDilatacao=(TextView)findViewById(R.id.txtDilatacao);
                textNumeroCama=(TextView)findViewById(R.id.id_numeroCamaParturiente);
                txtNameParturient = (TextView)findViewById(R.id.idText_nome);

                DBManager.getInstance().addParturiente(txtNameParturient.getText().toString(),textApelido.getText().toString(), (Integer) spinnerIdade.getSelectedItem(),true,String.valueOf(dilatacaseekBar),new Date(),(Integer) spinnerCama.getSelectedItem());
                Toast.makeText(getApplicationContext()," Parturiente Registado com sucesso",Toast.LENGTH_LONG).show();


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


    private void findView() {
        mSlider = findViewById(R.id.discreteSlider);
    }

    private void setUpView() {
        mSlider.setTrackWidth(Utils.convertDpToPixel(4, this));
//        mSlider.setTrackColor(0xFFD81B60);
//        mSlider.setInactiveTrackColor(0x3DD81B60);

        mSlider.setThumbRadius(Utils.convertDpToPixel(6, this));
//        mSlider.setThumbColor(0xFFD81B60);
//        mSlider.setThumbPressedColor(0x1FD81B60);
//
//        mSlider.setTickMarkColor(0x3DFFFFFF);
//        mSlider.setTickMarkInactiveColor(0x1FD81B60);
        mSlider.setTickMarkPatterns(
                Arrays.asList(new Dot(), new Dash(Utils.convertDpToPixel(1, this))));

        mSlider.setValueLabelTextColor(Color.WHITE);
        mSlider.setValueLabelTextSize(Utils.convertSpToPixel(16, this));
        mSlider.setValueLabelFormatter(new DiscreteSlider.ValueLabelFormatter() {

            @Override
            public String getLabel(int input) {
                return Integer.toString(input);
            }
        });

        mSlider.setCount(11);
        mSlider.setMode(DiscreteSlider.MODE_NORMAL);

        mSlider.setMinProgress(0);

        mSlider.setOnValueChangedListener(new DiscreteSlider.OnValueChangedListener() {

            @Override
            public void onValueChanged(int progress, boolean fromUser) {
                super.onValueChanged(progress, fromUser);
                Log.i("DiscreteSlider", "Progress: " + progress + ", fromUser: " + fromUser);
            }

            @Override
            public void onValueChanged(int minProgress, int maxProgress, boolean fromUser) {
                super.onValueChanged(minProgress, maxProgress, fromUser);
                Log.i("DiscreteSlider",
                        "MinProgress: " + minProgress + ", MaxProgress: " + maxProgress +
                                ", fromUser: " + fromUser);
            }
        });

        mSlider.setClickable(true);
    }


}
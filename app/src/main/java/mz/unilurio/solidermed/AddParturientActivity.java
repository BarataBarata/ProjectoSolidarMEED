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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_mother);
        numberPicker1 = findViewById(R.id.numberPickerTwo);
        numberPicker2 = findViewById(R.id.numberPickerOne);
        setUpNumberPickers();

        readDisplayStateValues();
        saveOriginalNoteValues();

        spinner = findViewById(R.id.spinner_gestRange);
        List<GestatinalRange> list = DBManager.getInstance().getGestatinalRange();
        ArrayAdapter<GestatinalRange> adapterGesta = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, list);
        adapterGesta.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapterGesta);
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

                textApelido = (TextView)findViewById(R.id.txtSurname);
                txtNameParturient = (TextView)findViewById(R.id.txtName);
                numberPicker1 = (NumberPicker) findViewById(R.id.numberPickerOne);
                numberPicker2 = (NumberPicker) findViewById(R.id.numberPickerTwo);

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

//    private void setUpDilationSlider() {
//        mSliderDilatation.setTrackWidth(Utils.convertDpToPixel(4, this));
////        mSlider.setTrackColor(0xFFD81B60);
////        mSlider.setInactiveTrackColor(0x3DD81B60);
//
//        mSliderDilatation.setThumbRadius(Utils.convertDpToPixel(6, this));
////        mSlider.setThumbColor(0xFFD81B60);
////        mSlider.setThumbPressedColor(0x1FD81B60);
////
////        mSlider.setTickMarkColor(0x3DFFFFFF);
////        mSlider.setTickMarkInactiveColor(0x1FD81B60);
//        mSliderDilatation.setTickMarkPatterns(
//                Arrays.asList(new Dot(), new Dash(Utils.convertDpToPixel(1, this))));
//
//        mSliderDilatation.setValueLabelTextColor(Color.WHITE);
//        mSliderDilatation.setValueLabelTextSize(Utils.convertSpToPixel(16, this));
//        mSliderDilatation.setValueLabelFormatter(new DiscreteSlider.ValueLabelFormatter() {
//
//            @Override
//            public String getLabel(int input) {
//                return Integer.toString(input);
//            }
//        });
//
//        mSliderDilatation.setCount(11);
//        mSliderDilatation.setMode(DiscreteSlider.MODE_NORMAL);
//
//        mSliderDilatation.setMinProgress(0);
//
//        mSliderDilatation.setOnValueChangedListener(new DiscreteSlider.OnValueChangedListener() {
//
//            @Override
//            public void onValueChanged(int progress, boolean fromUser) {
//                super.onValueChanged(progress, fromUser);
//                Log.i("DiscreteSlider", "Progress: " + progress + ", fromUser: " + fromUser);
//            }
//
//            @Override
//            public void onValueChanged(int minProgress, int maxProgress, boolean fromUser) {
//                super.onValueChanged(minProgress, maxProgress, fromUser);
//                Log.i("DiscreteSlider",
//                        "MinProgress: " + minProgress + ", MaxProgress: " + maxProgress +
//                                ", fromUser: " + fromUser);
//            }
//        });
//
//        mSliderDilatation.setClickable(true);
//    }
//
    public void setUpNumberPickers(){

        numberPicker2.setMinValue(1);
        numberPicker2.setMaxValue(5);
        numberPicker2.setOnValueChangedListener(new NumberPicker.OnValueChangeListener(){
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                // tvShowNumbers.setText("Old Value = " + i + " New Value = " + i1);
            }
        });

        numberPicker1.setMinValue(0);
        numberPicker1.setMaxValue(9);
        numberPicker1.setOnValueChangedListener(new NumberPicker.OnValueChangeListener(){
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                // tvShowNumbers.setText("Old Value = " + i + " New Value = " + i1);
            }
        });
    }


}
package mz.unilurio.solidermed;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class NoteActivity extends AppCompatActivity {

    public static final  String NOTE_POSITION="mz.unilurio.projecto200.NOTE_INFO";
    public static final int POSITION_NOT_SET = -1;
    private Toolbar binding;
    private NoteInfo mNote;
    private boolean isNewnote;
    private Spinner spinnerCourses;
    private EditText textNoteTitle;
    private EditText text_note_text;
    private int mNotePosition;
    private boolean isCancel;
    private String originalNoteCoursesId;
    private String moriginalNoteCoursesId1;
    private String originalNoteTitle;
    private String originalNoteText;
//    private NoteActivityViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        binding = findViewById(R.id.toolbar);
        setSupportActionBar(binding);

        ViewModelProvider viewModelProvider=new ViewModelProvider(getViewModelStore(),ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()));
        // mViewModel=viewModelProvider.get();



        spinnerCourses = findViewById(R.id.spinner_courses);
        List<CourseInfo> courses=DataManager.getInstance().getCourses();
        ArrayAdapter<CourseInfo> adapterCourses=new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,courses);
        adapterCourses.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCourses.setAdapter(adapterCourses);

        readDisplayStateValues();
        saveOriginalNoteValues();

        textNoteTitle = findViewById(R.id.text_note_title);
        text_note_text = findViewById(R.id.text_note_text);

        if(!isNewnote)
            displayNote(spinnerCourses, text_note_text, textNoteTitle);

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
        MenuItem item=menu.findItem(R.id.action_next);
        int lastNoteIndex=DataManager.getInstance().getNotes().size()-1;
        item.setEnabled(mNotePosition<lastNoteIndex);

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
        mNote.setCourse((CourseInfo) spinnerCourses.getSelectedItem());
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
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_send_email) {
            sendEmail();
            return true;
        }
        if(id == R.id.action_next){
            moveNext();
        }


        return super.onOptionsItemSelected(item);
    }

    private void moveNext() {
        saveNote();
        ++mNotePosition;
        mNote=DataManager.getInstance().getNotes().get(mNotePosition);
        saveOriginalNoteValues();
        displayNote(spinnerCourses, text_note_text, textNoteTitle);
        invalidateOptionsMenu();
    }

    private void sendEmail() {
        CourseInfo course=(CourseInfo)spinnerCourses.getSelectedItem();
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
}
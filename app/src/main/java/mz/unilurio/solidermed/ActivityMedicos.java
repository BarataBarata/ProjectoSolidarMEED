package mz.unilurio.solidermed;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import mz.unilurio.solidermed.model.AddDoctorClass;
import mz.unilurio.solidermed.model.DBService;
import mz.unilurio.solidermed.model.EditDoctorClass;
import mz.unilurio.solidermed.model.EmergencyMedicalPersonnel;
import mz.unilurio.solidermed.model.UserDoctor;

public class ActivityMedicos extends AppCompatActivity {

    private DoctorRecyclerAdpter doctorRecyclerAdpter;
    private Handler handler;
    private TimerTask task;
    private Timer timer;
    private List<EmergencyMedicalPersonnel> auxList;
    private List<UserDoctor> list;
    private int tamanhoList;
    private  String seacher="";
    DBService dbService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        dbService=new DBService(this);
        initializeteDisplayContextDoctor();
        SearchView searchView = (SearchView) findViewById(R.id.seacherDilatacao);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                seacher=newText;
                doctorRecyclerAdpter.getFilter().filter(newText);
                return false;
            }
        });
        FloatingActionButton fab = findViewById(R.id.fabAddContact);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddDoctorClass addDoctorClass = new AddDoctorClass();
                addDoctorClass.show(getSupportFragmentManager(), "Adicionar");
            }
        });


    }

    private void startUpdate() {

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
                            if(e.isOK){
                                initializeteDisplayContextDoctor();
                                doctorRecyclerAdpter.getFilter().filter(seacher);
                                 e.isOK=false;
                            }
                            if(add.isAdd){
                                initializeteDisplayContextDoctor();
                                add.isAdd=false;
                            }

                            if(add.isRemove){
                                initializeteDisplayContextDoctor();
                            }
                        } catch (Exception e) {
                            // error, do something
                        }
                    }
                });
            }
        };
        timer.schedule(task, 0, 500);  // interval of one minute

    }

    @Override
    protected void onResume() {
        super.onResume();
         startUpdate();
    }

    @Override
    protected void onPause() {
        super.onPause();
        task.cancel();
        timer.cancel();
    }

    public void finish(View view) {
        finish();
    }

    public void initializeteDisplayContextDoctor() {
        list=dbService.getListDoctor();
        RecyclerView view;
        view = (RecyclerView) findViewById(R.id.recycler_Contact);
        LinearLayoutManager contactLayoutManager;
        contactLayoutManager = new LinearLayoutManager(this);
        doctorRecyclerAdpter = new DoctorRecyclerAdpter(this, list);
        view.setLayoutManager(contactLayoutManager);
        view.setAdapter(doctorRecyclerAdpter);
    }

}
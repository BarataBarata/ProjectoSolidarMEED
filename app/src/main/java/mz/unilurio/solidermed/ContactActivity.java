package mz.unilurio.solidermed;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import mz.unilurio.solidermed.model.AddContctClass;
import mz.unilurio.solidermed.model.DBManager;
import mz.unilurio.solidermed.model.EditContctClass;
import mz.unilurio.solidermed.model.EmergencyMedicalPersonnel;

public class ContactActivity extends AppCompatActivity {

    private EmergencyMedicalPersonnelRecyclerAdpter emergencyMedicalPersonnelRecyclerAdpter;
    private Handler handler;
    private TimerTask task;
    private Timer timer;
    private List<EmergencyMedicalPersonnel> auxList;
    private List<EmergencyMedicalPersonnel> list;
    private int tamanhoList;
    private  String seacher="";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        SearchView searchView = (SearchView) findViewById(R.id.seacherContact);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                   seacher=newText;
                   buscaAtualizacao(newText);

                return false;
            }
        });
        FloatingActionButton fab = findViewById(R.id.fabAddContact);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddContctClass addContctClass = new AddContctClass();
                addContctClass.show(getSupportFragmentManager(), "Adicionar");
            }
        });


    }


    public void buscaAtualizacao(String seacher){

                if(seacher.isEmpty()){
                    auxList=new ArrayList<>();
                    list = DBManager.getInstance().getEmergencyMedicalPersonnels();
                    auxList.addAll(list);
                }else {
                    auxList=new ArrayList<>();
                    for(EmergencyMedicalPersonnel emergencyMedicalPersonnel: list){
                        if(emergencyMedicalPersonnel.getName().toLowerCase().contains(seacher.toString().toLowerCase())){
                            auxList.add(emergencyMedicalPersonnel);
                        }
                    }
                    System.out.println(auxList.size());
                }


    }

    private void atualizacao() {

        handler = new Handler();
        timer = new Timer();

        task = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        try {
                            initializeteDisplayContextContact();
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
        atualizacao();
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

    public void initializeteDisplayContextContact() {
        list=DBManager.getInstance().getEmergencyMedicalPersonnels();
        buscaAtualizacao(seacher);
        RecyclerView view;
        view = (RecyclerView) findViewById(R.id.recycler_contact);
        LinearLayoutManager contactLayoutManager;
        contactLayoutManager = new LinearLayoutManager(this);
        emergencyMedicalPersonnelRecyclerAdpter = new EmergencyMedicalPersonnelRecyclerAdpter(this, auxList);
        view.setLayoutManager(contactLayoutManager);
        view.setAdapter(emergencyMedicalPersonnelRecyclerAdpter);
    }

}
package mz.unilurio.solidermed;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import mz.unilurio.solidermed.model.AddNursesClass;
import mz.unilurio.solidermed.model.AddPatologiaClass;
import mz.unilurio.solidermed.model.DBManager;
import mz.unilurio.solidermed.model.DBService;
import mz.unilurio.solidermed.model.EditNurseClass;
import mz.unilurio.solidermed.model.EditPatologiaClass;
import mz.unilurio.solidermed.model.Parturient;
import mz.unilurio.solidermed.model.Patologia;

public class ActivityPatologia extends AppCompatActivity {
   DBService dbService;
    private Handler handler;
    private TimerTask task;
    private Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patologia);
        dbService=new DBService(this);
        displayPatologia();




        FloatingActionButton fab = findViewById(R.id.fab2);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddPatologiaClass patologiaClass=new AddPatologiaClass();
                patologiaClass.show(getSupportFragmentManager(),"Adicionar");
            }
        });




    }

    @Override
    protected void onStart() {
        super.onStart();
        updatePatologia();
    }

    @Override
    protected void onPause() {
        super.onPause();
        task.cancel();
        timer.cancel();
    }

    private void updatePatologia() {

        handler = new Handler();
        timer = new Timer();

        AddPatologiaClass addPatologiaClass=new AddPatologiaClass();
        EditPatologiaClass editPatologiaClass=new EditPatologiaClass();


        task = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        try {
                            System.out.println("  ainda ..");

                            if(addPatologiaClass.isAdd){
                                System.out.println(" entrou");
                                displayPatologia();
                                addPatologiaClass.isAdd=false;
                            }

                            if(editPatologiaClass.isEdit){
                                System.out.println("entrou");
                                displayPatologia();
                                editPatologiaClass.isEdit=false;

                            }
                            if(addPatologiaClass.isRemove){
                                System.out.println("entrou");
                                displayPatologia();
                                addPatologiaClass.isRemove=false;
                            }

                        } catch (Exception e) {
                            // error, do something
                        }
                    }
                });
            }
        };
        timer.schedule(task, 0, 900);  // interval of one minute

    }

    private void displayPatologia() {

        RecyclerView recyclerView;
        recyclerView =findViewById(R.id.recycler_sinaisPatologia);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Patologia> patologias= dbService.getListPatologia();
        SinaisDePatologiaRecyclerAdpter  sinaisDePatologiaRecyclerAdpter= new SinaisDePatologiaRecyclerAdpter(this,patologias);
        recyclerView.setAdapter(sinaisDePatologiaRecyclerAdpter);
    }

    public void Selecionar(View view) {

    }

    public void finish(View view) {
        finish();
    }
}
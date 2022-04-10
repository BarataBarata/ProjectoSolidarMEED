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

import mz.unilurio.solidermed.model.AddNursesClass;
import mz.unilurio.solidermed.model.DBManager;
import mz.unilurio.solidermed.model.DBService;
import mz.unilurio.solidermed.model.EditNurseClass;
import mz.unilurio.solidermed.model.UserNurse;

public class NurseActivity extends AppCompatActivity {
     private RecyclerView view;
    private    LinearLayoutManager nurseLinearLayoutManager;
    private NurseRecyclerAdpter nurseRecyclerAdpter1;
    private Handler handler;
    private TimerTask task;
    private Timer timer;
    private List<UserNurse> list;
    private static String seacher="";

    private DBService dbService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nurse);
        dbService=new DBService(this);

        initializeteDisplayContextNurses();

        SearchView searchView=(SearchView)findViewById(R.id.seacherInfermeira);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                seacher=newText;
                nurseRecyclerAdpter1.getFilter().filter(newText);
                return false;
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab2);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddNursesClass addNursesClass=new AddNursesClass();
                addNursesClass.show(getSupportFragmentManager(),"Adicionar");
            }
        });

    }
    private void updadeNurses() {

        handler = new Handler();
        timer = new Timer();

         AddNursesClass addNursesClass=new AddNursesClass();
         EditNurseClass editNurseClass=new EditNurseClass();


        task = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        try {

                            if(addNursesClass.isAdd){
                                initializeteDisplayContextNurses();
                                addNursesClass.isAdd=false;
                            }

                            if(editNurseClass.isEdit){
                                initializeteDisplayContextNurses();
                                nurseRecyclerAdpter1.getFilter().filter(seacher);
                                editNurseClass.isEdit=false;

                            }
                            if(addNursesClass.isRemove){
                                initializeteDisplayContextNurses();
                                addNursesClass.isRemove=false;
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

    @Override
    protected void onPause() {
        super.onPause();
        task.cancel();
        timer.cancel();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updadeNurses();
    }

    public void initializeteDisplayContextNurses(){
        list=dbService.getListNurse();
        view = (RecyclerView)findViewById(R.id.listaInfermeira);
        nurseLinearLayoutManager = new LinearLayoutManager(this);
        nurseRecyclerAdpter1 = new NurseRecyclerAdpter(this,list);
        view.setLayoutManager(nurseLinearLayoutManager);
        view.setAdapter(nurseRecyclerAdpter1);
    }
    public void finish(View view) {
        finish();
    }
}
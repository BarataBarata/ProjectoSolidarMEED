package mz.unilurio.solidermed;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import mz.unilurio.solidermed.model.DBManager;
import mz.unilurio.solidermed.model.EmergencyMedicalPersonnel;
import mz.unilurio.solidermed.model.Hospitais;
import mz.unilurio.solidermed.model.UserNurse;

public class NurseActivity extends AppCompatActivity {
     private RecyclerView view;
    private    LinearLayoutManager nurseLinearLayoutManager;
    private NurseRecyclerAdpter nurseRecyclerAdpter1;
    private Handler handler;
    private TimerTask task;
    private List<UserNurse> auxList;
    private List<UserNurse> list;
    private int tamanhoList;
    private  String seacher="";
    private Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nurse);
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
                buscaAtualizacao(newText);
                //nurseRecyclerAdpter1.getFilter().filter(newText);
                return false;
            }
        });



        FloatingActionButton fab = findViewById(R.id.fab2);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(NurseActivity.this, AddUserNurseActivity.class));
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });

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
                            initializeteDisplayContextNurses();
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
        atualizacao();
    }

    public void initializeteDisplayContextNurses(){
        list=DBManager.getInstance().getUserNurseList();
        buscaAtualizacao(seacher);
        view = (RecyclerView)findViewById(R.id.listaInfermeira);
        nurseLinearLayoutManager = new LinearLayoutManager(this);
        nurseRecyclerAdpter1 = new NurseRecyclerAdpter(this,auxList);
        view.setLayoutManager(nurseLinearLayoutManager);
        view.setAdapter(nurseRecyclerAdpter1);
    }

    public void buscaAtualizacao(String seacher){

        if(seacher.isEmpty()){
            auxList=new ArrayList<>();
            list = DBManager.getInstance().getUserNurseList();
            auxList.addAll(list);
        }else {
            auxList=new ArrayList<>();
            for(UserNurse userNurse: list){
                if(userNurse.getNomeNurse().toLowerCase().contains(seacher.toString().toLowerCase())){
                    auxList.add(userNurse);
                }
            }
            System.out.println(auxList.size());
        }


    }
    public void finish(View view) {
        finish();
    }
}
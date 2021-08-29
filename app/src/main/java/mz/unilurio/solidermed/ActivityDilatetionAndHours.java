package mz.unilurio.solidermed;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.transition.Slide;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.slider.Slider;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import mz.unilurio.solidermed.model.AddDilatationClassDilatation;
import mz.unilurio.solidermed.model.DBManager;
import mz.unilurio.solidermed.model.DilatationAndTimer;
import mz.unilurio.solidermed.model.EditContctClassDilatation;

public class ActivityDilatetionAndHours extends AppCompatActivity {
    private DilatationAndTimerRecyclerAdpter dilatationAndTimerRecyclerAdpter;
    private Handler handler;
    private TimerTask task;
    private Timer timer;
    private List<DilatationAndTimer> auxList;
    private List<DilatationAndTimer> list;
    private int tamanhoList;
    private  String seacher="";
    RecyclerView view;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dilatetion_and_hours);

        fab = findViewById(R.id.fabAddDilatation);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddDilatationClassDilatation editContctClass=new AddDilatationClassDilatation();
                editContctClass.show(getSupportFragmentManager(),"Adicionar");
            }
        });
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

    private void atualizacao() {

        handler = new Handler();
        timer = new Timer();

        task = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        try {
                            initializeteDisplayContextDilatation();
                        } catch (Exception e) {
                            // error, do something
                        }
                    }
                });
            }
        };
        timer.schedule(task, 0, 500);  // interval of one minute

    }

    public void seacherView(String seacher){

        if(seacher.isEmpty()){
            auxList=new ArrayList<>();
            list = DBManager.getInstance().getDilatationAndTimerList();
            auxList.addAll(list);
        }else {
            auxList=new ArrayList<>();
            for(DilatationAndTimer dilatationAndTimer: list){
                if((""+dilatationAndTimer.getNumberDilatation()).toLowerCase().contains(seacher.toString().toLowerCase())){
                    auxList.add(dilatationAndTimer);
                }
            }
            System.out.println(auxList.size());
        }

    }

    public void initializeteDisplayContextDilatation() {
        list=DBManager.getInstance().getDilatationAndTimerList();
        seacherView(seacher);
        view = (RecyclerView) findViewById(R.id.recycler_Dilatation);
        LinearLayoutManager dilatationLayoutManager;
        dilatationLayoutManager = new LinearLayoutManager(this);
        dilatationAndTimerRecyclerAdpter = new DilatationAndTimerRecyclerAdpter(this, auxList);
        view.setLayoutManager(dilatationLayoutManager);
        view.setAdapter(dilatationAndTimerRecyclerAdpter);
    }

    public void finish(View view) {
        finish();
    }
}
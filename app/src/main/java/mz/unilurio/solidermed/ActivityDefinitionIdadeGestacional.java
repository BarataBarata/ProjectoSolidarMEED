package mz.unilurio.solidermed;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import mz.unilurio.solidermed.model.AddDilatation;
import mz.unilurio.solidermed.model.AddIdadeGestacional;
import mz.unilurio.solidermed.model.DBService;
import mz.unilurio.solidermed.model.DilatationAndTimer;
import mz.unilurio.solidermed.model.EditClassDilatation;
import mz.unilurio.solidermed.model.EditIadeGestacional;
import mz.unilurio.solidermed.model.IdadeGestacional;

public class ActivityDefinitionIdadeGestacional extends AppCompatActivity {
    private DBService dbService;
    private FloatingActionButton fab;
    private Handler handler;
    private TimerTask task;
    private Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_definition_idade_gestacional);

        dbService=new DBService(this);
        initializeteDisplayContextDilatation();

        fab = findViewById(R.id.fabAddIdadeGestacional);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddIdadeGestacional editContctClass=new AddIdadeGestacional();
                editContctClass.show(getSupportFragmentManager(),"Adicionar");
            }
        });
    }

    private void update() {

        handler = new Handler();
        timer = new Timer();

        task = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        EditClassDilatation e =new EditClassDilatation();
                        AddIdadeGestacional add=new AddIdadeGestacional();
                        EditIadeGestacional edit=new EditIadeGestacional();

                        handler.post(new Runnable() {
                            public void run() {
                                try {
//                                    if(e.isEdit){
//                                        initializeteDisplayContextDilatation();
//                                        dilatationAndTimerRecyclerAdpter.getFilter().filter(seacher);
//                                        e.isEdit=false;
//                                    }
                                    if(add.isAdd || edit.isAdd){
                                        initializeteDisplayContextDilatation();
                                        add.isAdd=false;
                                        edit.isAdd=false;
                                    }
//                                    if(add.isRemove){
//                                        initializeteDisplayContextDilatation();
//                                    }
                                } catch (Exception e) {
                                    // error, do something
                                }
                            }
                        });
                    }
                });
            }
        };
        timer.schedule(task, 0, 500);  // interval of one minute

    }

    private void initializeteDisplayContextDilatation() {
        List<IdadeGestacional> list = dbService.getListIdadeGestacional();
        //seacherView(seacher);
        RecyclerView view = (RecyclerView) findViewById(R.id.recycler_IdadeGestacional);
        LinearLayoutManager dilatationLayoutManager;
        dilatationLayoutManager = new LinearLayoutManager(this);
        IdadeGestacionalRecyclerAdpter idadeGestacionalRecyclerAdpter= new IdadeGestacionalRecyclerAdpter(this, list);
        view.setLayoutManager(dilatationLayoutManager);
        view.setAdapter(idadeGestacionalRecyclerAdpter);
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
        update();
    }

    public void finish(View view) {
        finish();
    }
}
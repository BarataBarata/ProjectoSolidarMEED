package mz.unilurio.solidermed;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

import java.util.List;

import mz.unilurio.solidermed.model.DBManager;
import mz.unilurio.solidermed.model.DBService;
import mz.unilurio.solidermed.model.Patologia;

public class Activity_SelectPatologia extends AppCompatActivity {
   DBService dbService;
    Switch aSwitch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_patologia);
        dbService=new DBService(this);
        displayPatologia();
        aSwitch=findViewById(R.id.swithPatologia);

        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
               if(aSwitch.isChecked()){
                   DBManager.getInstance().getSinaisPatologiaList().removeAll(DBManager.getInstance().getSinaisPatologiaList());
                   System.out.println( " hhhhhh"+ DBManager.getInstance().getSinaisPatologiaList());
                   finish();
               }
            }
        });
    }
    private void displayPatologia() {

        RecyclerView recyclerView;
        recyclerView =findViewById(R.id.recycler_sinaisPatologia);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Patologia> patologias= dbService.getListPatologia();
        SelectedPatologiaRecyclerAdpter  sinaisDePatologiaRecyclerAdpter= new SelectedPatologiaRecyclerAdpter(this,patologias);
        recyclerView.setAdapter(sinaisDePatologiaRecyclerAdpter);
    }
    public void finish(View view) {
        finish();
    }

    public void Selecionar(View view) {

        finish();
    }
}
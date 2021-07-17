package mz.unilurio.solidermed;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import mz.unilurio.solidermed.model.DBManager;
import mz.unilurio.solidermed.model.Hospitais;
import mz.unilurio.solidermed.model.UserNurse;

public class NurseActivity extends AppCompatActivity {
     private RecyclerView view;
     LinearLayoutManager nurseLinearLayoutManager;
    private NurseRecyclerAdpter nurseRecyclerAdpter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nurse);
        initializeteDisplayContextNurses();


        view = findViewById(R.id.listaInfermeira);

        List<UserNurse> userNurses= DBManager.getInstance().getUserNurseList();
        NurseRecyclerAdpter nurseRecyclerAdpter= new NurseRecyclerAdpter(this,userNurses);
        view.setAdapter(nurseRecyclerAdpter);
        SearchView searchView=(SearchView)findViewById(R.id.seacherInfermeira);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                nurseRecyclerAdpter.getFilter().filter(newText);

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
    public void initializeteDisplayContextNurses(){
        view = (RecyclerView)findViewById(R.id.listaInfermeira);
        nurseLinearLayoutManager = new LinearLayoutManager(this);
        List<UserNurse> userNurses= DBManager.getInstance().getUserNurseList();
        NurseRecyclerAdpter nurseRecyclerAdpter= new NurseRecyclerAdpter(this,userNurses);
        view.setLayoutManager(nurseLinearLayoutManager);
        view.setAdapter(nurseRecyclerAdpter);
    }

    public void finish(View view) {
        finish();
    }
}
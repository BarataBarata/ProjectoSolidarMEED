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
    private    LinearLayoutManager nurseLinearLayoutManager;
    private NurseRecyclerAdpter nurseRecyclerAdpter1;

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
                nurseRecyclerAdpter1.getFilter().filter(newText);
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

    @Override
    protected void onPause() {
        super.onPause();
        nurseRecyclerAdpter1.getFilter().filter("");
    }

    public void initializeteDisplayContextNurses(){
        view = (RecyclerView)findViewById(R.id.listaInfermeira);
        nurseLinearLayoutManager = new LinearLayoutManager(this);
        List<UserNurse> userNurses= DBManager.getInstance().getUserNurseList();
        nurseRecyclerAdpter1 = new NurseRecyclerAdpter(this,userNurses);
        view.setLayoutManager(nurseLinearLayoutManager);
        view.setAdapter(nurseRecyclerAdpter1);
    }

    public void finish(View view) {
        finish();
    }
}
package mz.unilurio.solidermed;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import java.util.List;

import mz.unilurio.solidermed.model.DBManager;
import mz.unilurio.solidermed.model.DBService;
import mz.unilurio.solidermed.model.Hospitais;

public class HospitaisActivity extends AppCompatActivity {

    private RecyclerView view;
    private LinearLayoutManager hospitaisLinearLayoutManager;
    private HospitaisRecyclerAdpter hospitaisRecyclerAdpter;
    private  DBService dbService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospitais);
        initializeteDisplayContextHospitais();

        SearchView searchView =(SearchView) findViewById(R.id.seacherHospitais);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                hospitaisRecyclerAdpter.getFilter().filter(newText);
                return false;
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        hospitaisRecyclerAdpter.getFilter().filter("");
    }

    public void initializeteDisplayContextHospitais() {
        view = (RecyclerView)findViewById(R.id.recyclerVieHospitais);
        hospitaisLinearLayoutManager = new LinearLayoutManager(this);
        List<Hospitais> hospitais= dbService.getListHospitais();
        hospitaisRecyclerAdpter = new  HospitaisRecyclerAdpter(this,hospitais);
        view.setLayoutManager(hospitaisLinearLayoutManager);
        view.setAdapter(hospitaisRecyclerAdpter);
    }

    public void finish(View view) {
        finish();
    }


}
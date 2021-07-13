package mz.unilurio.solidermed;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;

import java.util.List;

import mz.unilurio.solidermed.model.DBManager;
import mz.unilurio.solidermed.model.Hospitais;

public class HospitaisActivity extends AppCompatActivity {

    private RecyclerView view;
    private LinearLayoutManager hospitaisLinearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospitais);

        initializeteDisplayContextHospitais();
    }

        public void initializeteDisplayContextHospitais() {
        view = (RecyclerView)findViewById(R.id.recyclerVieHospitais);
        hospitaisLinearLayoutManager = new LinearLayoutManager(this);
        List<Hospitais> hospitais= DBManager.getInstance().getHospitais();
        HospitaisRecyclerAdpter hospitaisRecyclerAdpter= new  HospitaisRecyclerAdpter(this,hospitais);
        view.setLayoutManager(hospitaisLinearLayoutManager);
        view.setAdapter(hospitaisRecyclerAdpter);
    }

    public void finish(View view) {
        finish();
    }


}
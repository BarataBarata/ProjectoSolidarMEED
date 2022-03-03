package mz.unilurio.solidermed;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import mz.unilurio.solidermed.model.DBManager;
import mz.unilurio.solidermed.model.Parturient;
import mz.unilurio.solidermed.model.Privilegios;
import mz.unilurio.solidermed.model.UserDoctor;

public class ActivityTransferidos extends AppCompatActivity {
    private TransferidosRecyclerAdapter transferidosRecyclerAdapter;
    private TextView viewTxt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transferidos);

         viewTxt=findViewById(R.id.idNullResultAtendimento2);
         initializeteDisplayContextTransferidos();

        if(DBManager.getInstance().getListaTransferidos().isEmpty()){
            viewTxt.setVisibility(View.VISIBLE);
        }else{
            viewTxt.setVisibility(View.INVISIBLE);
        }

        SearchView searchView =(SearchView) findViewById(R.id.seacherValitation);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                transferidosRecyclerAdapter.getFilter().filter(newText);

                return false;
            }
        });

    }


    public void initializeteDisplayContextTransferidos() {
        RecyclerView recyclerView;
        recyclerView = findViewById(R.id.recyclerViewAtendidos);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Parturient> parturients= DBManager.getInstance().getListaTransferidos();
        transferidosRecyclerAdapter = new TransferidosRecyclerAdapter(this,parturients);
        recyclerView.setAdapter(transferidosRecyclerAdapter);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_transferencia, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();

        if(id==R.id.app_bar_search_transferencia){
            Intent intent=new Intent(ActivityTransferidos.this,PesquisaActivity.class);
            intent.putExtra("seacher",3+"");
            startActivity(intent);
        }
        return true;
    }

    public void finish(View view) {
        finish();
    }
}
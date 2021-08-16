package mz.unilurio.solidermed;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import mz.unilurio.solidermed.model.DBManager;
import mz.unilurio.solidermed.model.Notification;
import mz.unilurio.solidermed.model.Parturient;

public class PesquisaActivity extends AppCompatActivity {
     private  ParturienteRecyclerAdpter parturienteRecyclerAdpter;
     private NotificationRecyclerAdpter notificationRecyclerAdpter;
     private AtendidosRecyclerAdpter atendidosRecyclerAdpter;
     private static int optionSeacher;
     private  TextView textViewNullPesquisa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesquisa);


        textViewNullPesquisa=findViewById(R.id.idNullResultSeacher);

        int seacherFragment=Integer.parseInt(getIntent().getStringExtra("seacher"));

        switch (seacherFragment){
            case 1:{ displayNotification();
                optionSeacher=0;break;}
            case 2: displayAtendidos();
                optionSeacher=1;break;
            case 0: displayParturiente();
                optionSeacher=2;break;
        }




        SearchView searchView =(SearchView) findViewById(R.id.seacherValitation);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                switch (optionSeacher){
                    case 0:{ notificationRecyclerAdpter.getFilter().filter(newText);break;}
                    case 1:{ atendidosRecyclerAdpter.getFilter().filter(newText);break;}
                    case 2:{ parturienteRecyclerAdpter.getFilter().filter(newText);break;}

                }

                return false;
            }
        });

    }
    public void viewNullListPesquisa(List list){

        if(list.size()==0) {
            textViewNullPesquisa.setVisibility(View.VISIBLE);
        }else {
            textViewNullPesquisa.setVisibility(View.INVISIBLE);
        }
    }
    public void finish(View view) {
        finish();
    }
    public void displayParturiente(){
        RecyclerView recyclerView;
        recyclerView = findViewById(R.id.recyclerVieParturienteSeacher);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Parturient> parturients= DBManager.getInstance().getParturients();
        viewNullListPesquisa(parturients);
        parturienteRecyclerAdpter=new ParturienteRecyclerAdpter( this,parturients);
        recyclerView.setAdapter(parturienteRecyclerAdpter);
    }

    private void displayAtendidos() {
        RecyclerView recyclerView;
        recyclerView =findViewById(R.id.recyclerVieParturienteSeacher);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Parturient> atendidos= DBManager.getInstance().getListParturientesAtendidos();
        viewNullListPesquisa(atendidos);
        atendidosRecyclerAdpter=new AtendidosRecyclerAdpter( this,atendidos);
        recyclerView.setAdapter(atendidosRecyclerAdpter);
    }

    private void displayNotification() {
        RecyclerView recyclerView;
        recyclerView = findViewById(R.id.recyclerVieParturienteSeacher);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Notification> notifications= DBManager.getInstance().getNotifications();
        viewNullListPesquisa(notifications);
        notificationRecyclerAdpter=new NotificationRecyclerAdpter( this, notifications);
        recyclerView.setAdapter(notificationRecyclerAdpter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        switch (optionSeacher){
            case 0:{ notificationRecyclerAdpter.getFilter().filter("");break;}
            case 1:{ atendidosRecyclerAdpter.getFilter().filter("");break;}
            case 2:{ parturienteRecyclerAdpter.getFilter().filter("");break;}

        }
    }
}
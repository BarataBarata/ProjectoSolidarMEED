package mz.unilurio.solidermed;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import mz.unilurio.solidermed.model.DBManager;
import mz.unilurio.solidermed.model.DBService;
import mz.unilurio.solidermed.model.Notification;
import mz.unilurio.solidermed.model.PageAdapder;
import mz.unilurio.solidermed.model.Parturient;
import mz.unilurio.solidermed.model.Privilegios;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public DBService dbService;
    private String fullNameUserLogin;
    private TabLayout tabLayout;
    private AppBarConfiguration mAppBarConfiguration;
    private RecyclerView recyclerItems;
    private RecyclerView recyclerParturientes;
    private static int currentPositionPager=0;
    private LinearLayoutManager notificationLayoutManager;
    private LinearLayoutManager parturienteLinearLayoutManager;
    private NotificationRecyclerAdpter notificationRecyclerAdapter;
    private AtendidosRecyclerAdpter atendidosRecyclerAdpter;
    private ParturienteRecyclerAdpter  parturienteRecyclerAdpter;
    private HashMap<String, Notification> notificationTriggered = new HashMap<String, Notification>();
    private NotificationManagerCompat notificationManager;
    private TextView textNotificacao;
    private TextView textUserLogin;
    private TextView textVerificationNull;
    private static int id;
    private TextView textSeacher;
    private boolean visible;
    private String userLogin;
    private static int opcoesSeacher=0;
    private ViewPager pager;
    private static List<Notification> notifications=new ArrayList<>();
    private PagerAdapter adapte;
    private TextView textNomeHospital;
    private String nomeHospitalExtra="";
    private NotificationManagerCompat notificationManagerCompat;
    private FloatingActionButton fab;
    //private DBService dbService;
    private DrawerLayout drawer;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbService=new DBService(this);
        dbService.updadeListParturiente();
        dbService.initializeListParturiente();
        dbService.updadeListNotification();

//        if(DBManager.getInstance().getNotifications().isEmpty()){// atualiza se estiver vaziu
//            dbService.updadeListNotification();
//        }

        fab = findViewById(R.id.fab);
        fab.setVisibility(View.VISIBLE);
        notificationManagerCompat=NotificationManagerCompat.from(this);
        textNomeHospital = findViewById(R.id.idCentroSaudeTitle);

        if(getIntent().getStringExtra("nomeUserLogin")!=null) {
            fullNameUserLogin=getIntent().getStringExtra("nomeUserLogin");
        }

        if(getIntent().getStringExtra("nomeHospital")!=null) {
            textNomeHospital.setText(getIntent().getStringExtra("nomeHospital"));
        }else{
            textNomeHospital.setText(dbService.getHospitalSelect());
        }

        tabLayout=(TabLayout)findViewById(R.id.tabLayout);
        pager=findViewById(R.id.viewpager);
        adapte =new PageAdapder(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT,tabLayout.getTabCount());
        pager.setAdapter(adapte);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager.setCurrentItem(tab.getPosition());
                opcoesSeacher=tab.getPosition();
                switch (tab.getPosition()){
                    case 0: {displayParturiente();fab.setVisibility(View.VISIBLE);break; }
                    case 1: {displayNotification(); fab.setVisibility(View.INVISIBLE);break;}
                    case 2: {displayAtendidos();fab.setVisibility(View.INVISIBLE);break;}
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }



        });


        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        notificationManager=NotificationManagerCompat.from(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AddParturientActivity.class));
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });

        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        drawer.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                TextView textView=v.findViewById(R.id.idNameUserLogin);
                textView.setText(fullNameUserLogin);

            }
        });
        verificationNull();
        initializeDisplayContent();
    }

   public void verificationIsNull(){
        textVerificationNull =findViewById(R.id.idNullResultParturiente);
        viewNullListParturiente(textVerificationNull);
        textVerificationNull =findViewById(R.id.idNullResultAtendimento);
        viewNullListAtendidos(textVerificationNull);
        textVerificationNull =findViewById(R.id.idNullResultNotification);
        viewNullListNotification(textVerificationNull);
   }

    public void viewNullListAtendidos(TextView textView){

        if(DBManager.getInstance().getListParturientesAtendidos().size()==0) {
            displayAtendidos();
            textView.setVisibility(View.VISIBLE);
        }else {
            textView.setVisibility(View.INVISIBLE);
        }
    }

    public void viewNullListParturiente(TextView textView){

        if(DBManager.getInstance().getParturients().size()==0) {
            textView.setVisibility(View.VISIBLE);
        }else {
            textView.setVisibility(View.INVISIBLE);
        }
    }
   public void viewNullListNotification(TextView textView){

       if(DBManager.getInstance().getNotifications().size()==0) {
           textView.setVisibility(View.VISIBLE);
       }else {
           textView.setVisibility(View.INVISIBLE);
       }
   }


    private void displayAtendidos() {
        RecyclerView recyclerView;
        recyclerView =findViewById(R.id.recyclerViewAtendidos);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Parturient> atendidos= DBManager.getInstance().getListParturientesAtendidos();
        atendidosRecyclerAdpter=new AtendidosRecyclerAdpter( this,atendidos);
        recyclerView.setAdapter(atendidosRecyclerAdpter);
    }

    public void displayNotification() {
        RecyclerView recyclerView;
        recyclerView = findViewById(R.id.list_notes);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Notification> notifications= DBManager.getInstance().getNotifications();
        notificationRecyclerAdapter=new NotificationRecyclerAdpter( this, notifications);
        recyclerView.setAdapter(notificationRecyclerAdapter);
    }

    public void displayParturiente(){
        RecyclerView recyclerView;
        recyclerView = findViewById(R.id.recyclerVieParturiente);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Parturient> parturients= DBManager.getInstance().getParturients();
        parturienteRecyclerAdpter=new ParturienteRecyclerAdpter( this,parturients);
        recyclerView.setAdapter(parturienteRecyclerAdpter);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
          int id=item.getItemId();

          if(id==R.id.app_bar_search){
             Intent intent=new Intent(MainActivity.this,PesquisaActivity.class);
             intent.putExtra("seacher",opcoesSeacher+"");
             startActivity(intent);
          }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem menuDefinition=menu.findItem(R.id.idDefinicoes);
        //MenuItem m=menu.findItem(R.id.app_bar_search);
        //m.setVisible(visible);
        if(!new Privilegios().isViewAll()) {
            menuDefinition.setVisible(false);
        }

        menuDefinition.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                startActivity(new Intent(MainActivity.this,SettingActivity.class));
                return false;
            }
        });
  return true;
    }


    @Override
    public boolean onNavigationItemSelected(@NotNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


            if (id == R.id.id_out){

                AlertDialog.Builder builder=new AlertDialog.Builder(this);
                builder.setMessage("Logout");
                builder.setMessage("Terminar a sessão ? ");
                builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finishAffinity();
                        System.exit(0);
                    }
                });
                builder.setNegativeButton("Nao", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void initializeDisplayContent() {
        setRepeatingAsyncTask(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }


    @Override
    public void onResume() {
        super.onResume();
        verificationNull();
    }

    private void setRepeatingAsyncTask(MainActivity activity) {

        final Handler handler = new Handler();
        Timer timer = new Timer();

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        try {
                            //displayNotification();
                            displayAtendidos();
                        } catch (Exception e) {
                        }
                    }
                });
            }
        };

        timer.schedule(task, 0, 1000);  // interval of one minute
    }

    public void verificationNull(){
        AddParturientActivity addParturientActivity=new AddParturientActivity();
        final Handler handler = new Handler();
        Timer timer = new Timer();

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        try {
                            if(addParturientActivity.isFireAlert){
                                System.out.println(" ==========atualizou+++++++++++");
                                displayNotification();
                                displayParturiente();
                                addParturientActivity.isFireAlert=false;
                            }
                            verificationIsNull();

                        } catch (Exception e) {
                            // error, do something
                        }
                    }
                });
            }
        };

        timer.schedule(task, 0, 500);  // interval of one minute
    }

    public void setting(MenuItem item) {
        startActivity(new Intent(MainActivity.this,SettingActivity.class));
    }


     public void centroSaude(View view) {
         if(new Privilegios().isViewAll()) {
             ProgressDialog progressBar;
             progressBar = new ProgressDialog(MainActivity.this);
             progressBar.setTitle("Aguarde");
             progressBar.setMessage("processando...");
             progressBar.show();

             new Handler().postDelayed(new Thread() {
                 @Override
                 public void run() {
                     progressBar.dismiss();
                     Intent intent = new Intent(MainActivity.this, HospitaisActivity.class);
                     startActivity(intent);
                 }
             }, Long.parseLong("400"));
         }
    }
}
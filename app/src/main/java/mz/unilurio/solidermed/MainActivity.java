package mz.unilurio.solidermed;

import android.Manifest;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import mz.unilurio.solidermed.model.App;
import mz.unilurio.solidermed.model.DBManager;
import mz.unilurio.solidermed.model.EmergencyMedicalPersonnel;
import mz.unilurio.solidermed.model.Notification;
import mz.unilurio.solidermed.model.PageAdapder;
import mz.unilurio.solidermed.model.Parturient;
import mz.unilurio.solidermed.model.Privilegios;
import mz.unilurio.solidermed.model.Queue;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

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
    private Timer timer;
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
    private SharedPreferences sharedPreferences;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        fab = findViewById(R.id.fab);
        fab.setVisibility(View.VISIBLE);
        notificationManagerCompat=NotificationManagerCompat.from(this);
        textNomeHospital = findViewById(R.id.idCentroSaudeTitle);


        if(getIntent().getStringExtra("nomeHospital")!=null) {
            textNomeHospital.setText(getIntent().getStringExtra("nomeHospital"));
        }else{
            textNomeHospital.setText("Centro de Saude de Chiure");
        }

//        if(getIntent().getStringExtra("user")!=null){
//            System.out.println("gggggggg=================="+getIntent().getStringExtra("user"));
//            textUserLogin.findViewById(R.id.idNameUserLogin);
//            textUserLogin.setText(getIntent().getStringExtra(getIntent().getStringExtra("user")));
//        }

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

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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

    private void displayNotification() {
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
//        MenuItem menuItem=menu.findItem(R.id.app_bar_search);
//        SearchView searchView=(SearchView)menuItem.getActionView();
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                switch (opcoesSeacher){
//                    case 0: notificationRecyclerAdapter.getFilter().filter(newText);break;
//                    case 1: atendidosRecyclerAdpter.getFilter().filter(newText);break;
//                    case 2: parturienteRecyclerAdpter.getFilter().filter(newText);break;
//                }
//                return false;
//            }
//        });
//
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
        //setRepeatingAsyncTask(this);
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
                            //updadeAndSeacherNotifications();
                            displayNotification();
                        } catch (Exception e) {
                            // error, do something
                        }
                    }
                });
            }
        };

        timer.schedule(task, 0, 1000);  // interval of one minute
    }

    public void verificationNull(){

        final Handler handler = new Handler();
        Timer timer = new Timer();

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        try {
                            verificationIsNull();
                        } catch (Exception e) {
                            // error, do something
                        }
                    }
                });
            }
        };

        timer.schedule(task, 0, 100);  // interval of one minute
    }
    public void setting(MenuItem item) {
        startActivity(new Intent(MainActivity.this,SettingActivity.class));
    }

//    public void updadeAndSeacherNotifications(){
//        Queue queue = DBManager.getInstance().getQueue();
//        queue.nofify();
//        notifications = queue.getNotifications();
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            if (checkSelfPermission(Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
//                List<EmergencyMedicalPersonnel> eP = DBManager.getInstance().getEmergencyMedicalPersonnels();
//
//                    for (Notification n : notifications) {
//                        // concatena as menssagens
//                        n.setMessage(n.getMessage()+ " "+sharedPreferences.getString("mensagen","")+"");
//                        System.out.println(" -------------------------------------------- size hash: " + notificationTriggered.size());
//                        if (!notificationTriggered.containsKey(n.getId()) || Calendar.getInstance().getTime().after(n.getNextNotifier())) {
//                            popNotification(n);
//                            //popNotification(n);
//                            for (EmergencyMedicalPersonnel e : eP) {
//                                System.out.println("sending SMS to " + e.getContact() + " Message: " + n.getMessage());
//                                sendSMS(e.getContact(), n.getMessage());
//                            }
//                            DBManager.getInstance().addNewNotification(notifications);
//                            notificationTriggered.put(n.getId(), n);
//                        } else {
//                            System.out.println(" ------------------------------------------------- notification " + n.getId() + " not trrigeer " + (!notificationTriggered.containsKey(n.getId()) || Calendar.getInstance().getTime().after(n.getNextNotifier())));
//                        }
//
//                }
//            } else {
//                requestPermissions(new String[]{Manifest.permission.SEND_SMS}, 1);
//            }
//         }
//
//        }
//
//    private void sendSMS(String phoneNumber, String message) {
//        phoneNumber = phoneNumber.trim();
//        message = message.trim();
//
//        try {
//            SmsManager smsManager = SmsManager.getDefault();
//            smsManager.sendTextMessage(phoneNumber, null, message, null, null);
//            Toast.makeText(this, "Message is sent", Toast.LENGTH_SHORT);
//
//        } catch (Exception e) {
//            Log.i("EXPECTION SMS", e.getMessage());
//            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT);
//        }
//    }

    private void popNotification(Notification notification) {

        Intent activitIntent=new Intent(this,MainActivity.class);
        PendingIntent contxtIntent=PendingIntent.getActivity(this,0,activitIntent,0);
        android.app.Notification notification1=new NotificationCompat.Builder(this, App.CHANNEL_1_ID)
                         .setSmallIcon(R.drawable.mulhergravidabom2)
                         .setContentTitle("Alerta")
                         .setColor(Color.GREEN)
                         .setAutoCancel(true)
                         .setContentText(notification.getMessage())
                         .setPriority(NotificationCompat.PRIORITY_HIGH)
                         .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setContentIntent(contxtIntent)
                  .build();
          notificationManagerCompat.notify(Integer.parseInt(notification.getId()),notification1);

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
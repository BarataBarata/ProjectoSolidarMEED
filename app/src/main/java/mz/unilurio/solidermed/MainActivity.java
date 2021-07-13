package mz.unilurio.solidermed;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import org.jetbrains.annotations.NotNull;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import mz.unilurio.solidermed.model.DBManager;
import mz.unilurio.solidermed.model.EmergencyMedicalPersonnel;
import mz.unilurio.solidermed.model.GestatinalRange;
import mz.unilurio.solidermed.model.Hospitais;
import mz.unilurio.solidermed.model.Notification;
import mz.unilurio.solidermed.model.PageAdapder;
import mz.unilurio.solidermed.model.Parturient;
import mz.unilurio.solidermed.model.Queue;
import mz.unilurio.solidermed.ui.fragments.NotificationFragment;
import mz.unilurio.solidermed.ui.fragments.ParturientesFragment;
import mz.unilurio.solidermed.utils.Helper;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String FRAGMENT_TAG = "bssbbs";
    TabLayout tabLayout;
    private AppBarConfiguration mAppBarConfiguration;
    private RecyclerView recyclerItems;
    private RecyclerView recyclerParturientes;
    private static int currentPositionPager=0;
    private LinearLayoutManager notificationLayoutManager;
    private LinearLayoutManager parturienteLinearLayoutManager;
    private NotificationRecyclerAdpter notificationRecyclerAdapter;
    private ParturienteRecyclerAdpter parturienteRecyclerAdpter;
    private HashMap<String, Notification> notificationTriggered = new HashMap<String, Notification>();
//    private GridLayoutManager coursesLayoutManager;
//    private CourseRecyclerAdapter courseRecyclerAdapter;
    private NotificationManagerCompat notificationManager;
    private TextView textNotificacao;
    private Timer timer;
    private TextView textSeacher;
    private boolean visible;
    private ViewPager pager;
    private static List<Notification> notifications=new ArrayList<>();
    private PagerAdapter adapte;
    private TextView textNomeHospital;
    private String nomeHospitalExtra="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        nomeHospitalExtra = new Hospitais().getTitleHospital();
        textNomeHospital = findViewById(R.id.idCentroSaudeTitle);
        if(!nomeHospitalExtra.equals("")) {
            textNomeHospital.setText(nomeHospitalExtra);
        }else{
            textNomeHospital.setText("Centro de Saude de Chiure");
        }
        recyclerParturientes = findViewById(R.id.recyclerVieParturiente);
        recyclerItems=findViewById(R.id.list_notes);
        textSeacher=findViewById(R.id.app_bar_search);
        tabLayout=(TabLayout)findViewById(R.id.tabLayout);
        //tabLayout.removeTabAt(0);
        pager=findViewById(R.id.viewpager);
        adapte =new PageAdapder(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT,tabLayout.getTabCount());
        pager.setAdapter(adapte);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager.setCurrentItem(tab.getPosition());
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

        FloatingActionButton fab = findViewById(R.id.fab);
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

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem menuDefinition=menu.findItem(R.id.idDefinicoes);
        //MenuItem m=menu.findItem(R.id.app_bar_search);
        //m.setVisible(visible);

        menuDefinition.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                startActivity(new Intent(MainActivity.this,SettingsActivity.class));
                return false;
            }
        });


        MenuItem menuItem=menu.findItem(R.id.app_bar_search);
        SearchView searchView=(SearchView)menuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                parturienteRecyclerAdpter.getFilter().filter(newText);
                //displayParturiente();
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NotNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.id_addInfermeira) {
               startActivity(new Intent(MainActivity.this, NurseActivity.class));
        }
        if (id == R.id.id_centroDeSaude) {
            startActivity(new Intent(MainActivity.this, HospitaisActivity.class));
        }
        if (id == R.id.id_out) {

            if (id == R.id.id_out){

                AlertDialog.Builder builder=new AlertDialog.Builder(this);
                builder.setMessage("Logout");
                builder.setMessage("sair ? ");
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


//    public void initializeteDisplayContextParturientes() {
//        recyclerItems = (RecyclerView)findViewById(R.id.recyclerVieParturiente);
//        parturienteLinearLayoutManager = new LinearLayoutManager(this);
//        List<Parturient> parturients= DBManager.getInstance().getParturients();
//        parturienteRecyclerAdpter = new ParturienteRecyclerAdpter(this,parturients);
//        recyclerItems.setLayoutManager(parturienteLinearLayoutManager);
//        recyclerItems.setAdapter(parturienteRecyclerAdpter);
//    }



    private void initializeDisplayContent() {
//        recyclerItems = findViewById(R.id.list_notes);
//
//        notificationLayoutManager = new LinearLayoutManager(this);
//        List<Notification> notifications = DBManager.getInstance().getEmptyNotifications();
//        notificationRecyclerAdapter = new NotificationRecyclerAdpter(this, notifications);
//
//        displayNotifications();
//        NotificationThread thread = new NotificationThread(this);
//        new Thread(thread).start();
//        ThreadTimer threadTimer = new ThreadTimer(this);
//        timer = new Timer();
//        timer.schedule(threadTimer, 0, 100000); // 1 minute

        setRepeatingAsyncTask(this);
    }

//    private void displayCourses() {
//        recyclerItems.setAdapter(courseRecyclerAdapter);
//        recyclerItems.setLayoutManager(coursesLayoutManager);
//        selectNavigationMenuItem(R.id.nav_courses);
//    }


    @Override
    protected void onPause() {
        super.onPause();

    }

    private void displayNotifications() {
//        notifications=DBManager.getInstance().getNotifications();
//        if(!notifications.isEmpty()) {
//            recyclerItems.findViewById(R.id.list_notes);
//            notificationLayoutManager = new LinearLayoutManager(this);
//            notificationRecyclerAdapter = new NotificationRecyclerAdpter(this, notifications);
//            recyclerItems.setLayoutManager(notificationLayoutManager);
//            recyclerItems.setAdapter(notificationRecyclerAdapter);
//        }
        //selectNavigationMenuItem(R.id.id_Home);
    }




    private void displayParturiente() {
//        recyclerParturientes=findViewById(R.id.recyclerVieParturiente);
//        parturienteLinearLayoutManager = new LinearLayoutManager(this);
//        List<Parturient> parturients= DBManager.getInstance().getParturients();
//        parturienteRecyclerAdpter = new ParturienteRecyclerAdpter(null,parturients);
//        recyclerParturientes.setLayoutManager(parturienteLinearLayoutManager);
//        recyclerParturientes.setAdapter(parturienteRecyclerAdpter);
////        selectNavigationMenuItem(R.id.id_notificacao);
    }

    private void displayParturientes() {
       // recyclerParturientes=findViewById(R.id.recyclerVieParturiente);
         // initializeteDisplayContextParturientes();
       // recyclerParturientes.setAdapter(parturienteRecyclerAdpter);
        //recyclerParturientes.setLayoutManager(parturienteLinearLayoutManager);
        //selectNavigationMenuItem(R.id.id_parturientes);
 }

    private void selectNavigationMenuItem(int id) {

//        menu.findItem(R.id.nav_note).setCheckable(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        setRepeatingAsyncTask(this);
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
                            System.out.println(" ola barata cheguwi...");

                            AsyncTaskNotification task = new AsyncTaskNotification(activity);
                            task.execute();
                        } catch (Exception e) {
                            // error, do something
                        }
                    }
                });
            }
        };

        timer.schedule(task, 0, 60*1000);  // interval of one minute

    }


    public class AsyncTaskNotification extends AsyncTask<String, String, String> {
        final String TAG = "AsyncTaskNotification.java";

        private WeakReference<MainActivity> activityWeakReference;
        private RecyclerView recyclerView;

        AsyncTaskNotification(MainActivity activity) {
            activityWeakReference = new WeakReference<MainActivity>(activity);
            recyclerView = (RecyclerView) activity.findViewById(R.id.list_notes);
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            MainActivity activity = activityWeakReference.get();
            if (activity == null || activity.isFinishing()) {
                return;
            }
            activity.recyclerItems = activity.findViewById(R.id.list_notes);
//            activity.progressBar.setVisibility(View.VISIBLE);

        }
        @Override
        protected String doInBackground(String... integers) {

            return "Finished!";
        }
        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            MainActivity activity = activityWeakReference.get();
            if (activity == null || activity.isFinishing()) {
                return;
            }
//            activity.recyclerItems = activity.findViewById(R.id.list_notes);
//            activity.progressBar.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            MainActivity activity = activityWeakReference.get();
            if (activity == null || activity.isFinishing()) {
                return;
            }
//            recyclerView.findViewById(R.id.list_notes);
//            Toast.makeText(activity, s, Toast.LENGTH_SHORT).show();
//            activity.progressBar.setProgress(0);
//            activity.progressBar.setVisibility(View.INVISIBLE);

            // Stuff that updates the UI



            Queue queue = DBManager.getInstance().getQueue();
            queue.nofify();
            notifications = queue.getNotifications();

            for(Notification notification:notifications){
                System.out.println("------------"+notification.getDeliveryService()+"");
                DBManager.getInstance().addNewNotification(notification);
            }
            //updadeNotifiction();


            // Send SMS
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
                    List<EmergencyMedicalPersonnel> eP = DBManager.getInstance().getEmergencyMedicalPersonnels();

                    String composeMessage = "";
                    for (Notification n : notifications) {

                        Log.i("CompareTime",  Helper.format(Calendar.getInstance().getTime()) + ">" + Helper.format(n.getNextNotifier()));
                        if (!notificationTriggered.containsKey(n.getId()) || Calendar.getInstance().getTime().after(n.getNextNotifier())) {
//                            composeMessage += n.getMessage()+"\n";
                            composeMessage += n.getDeliveryService().getParturient().getName() + " "+n.getDeliveryService().getParturient().getSurname() +", ";
                            notificationTriggered.put(n.getId(), n);
                            popNotification(n);
                        }
                    }

                    if(!composeMessage.isEmpty()){
                        for (EmergencyMedicalPersonnel e : eP) {
                            String message = "ATENÇÃO ALERTA VERMELHO: A(s) parturiente(s) "+composeMessage+" necessita(m) de cuidados médicos";
                            Log.i("AsyncTask", Helper.format(Calendar.getInstance().getTime())+": sending SMS to " + e.getContact() + " Message: " + message);
                            sendSMS(e.getContact(), message);
                        }
                    }

                } else {
                    requestPermissions(new String[]{Manifest.permission.SEND_SMS}, 1);
                }
            }
        }
    }

    private void updadeNotifiction(){
        System.out.println("eee");

        Parturient parturient=new Parturient();
        parturient.setAge(45);
        parturient.setName("ddddddd");
        parturient.setId(4);
        parturient.setPara(8);
        parturient.setSurname("dddddddddddddd");
        parturient.setNumeroCama(4);
        parturient.setReason("5");
        DBManager.getInstance().addParturiente(parturient);
        DBManager.getInstance().initializeNotifications();
           /// adapte = new PageAdapder(getSupportFragmentManager(),3 , 3);
//        ParturientesFragment fragment=new ParturientesFragment();
//        fragment.onPause();
        pager.getAdapter().notifyDataSetChanged();


    }

    private void sendSMS(String phoneNumber, String message) {
        phoneNumber = phoneNumber.trim();
        message = message.trim();

        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, message, null, null);
//            Toast.makeText(this, "Message is sent", Toast.LENGTH_SHORT);
        } catch (Exception e) {
            Log.i("EXPECTION SMS", e.getMessage());
//            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT);
        }
    }

    private void popNotification(Notification notification) {

        NotificationCompat.Builder builder=new NotificationCompat.Builder(MainActivity.this)
                .setSmallIcon(R.drawable.mulhergravidabom2)
                .setContentTitle("Alerta").setColor(Color.GREEN)
                .setContentText(notification.getMessage())
                .setAutoCancel(true);

        Intent intent=new Intent(MainActivity.this,DadosPessoais.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
        intent.putExtra("id",notification.getId());
        PendingIntent pendingIntent=PendingIntent.getActivity(MainActivity.this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        NotificationManager notificationManager=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0,builder.build());


    }



     public void centroSaude(View view) {

         ProgressDialog progressBar;
         progressBar=new ProgressDialog(MainActivity.this);
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
         },Long.parseLong("400"));


    }



}
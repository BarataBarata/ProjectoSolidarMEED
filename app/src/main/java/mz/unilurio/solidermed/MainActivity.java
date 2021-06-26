package mz.unilurio.solidermed;

import android.Manifest;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.view.GravityCompat;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import mz.unilurio.solidermed.model.App;
import mz.unilurio.solidermed.model.DBManager;
import mz.unilurio.solidermed.model.EmergencyMedicalPersonnel;
import mz.unilurio.solidermed.model.Notification;
import mz.unilurio.solidermed.model.Parturient;
import mz.unilurio.solidermed.model.Queue;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private AppBarConfiguration mAppBarConfiguration;
    private RecyclerView recyclerItems;
    private LinearLayoutManager notificationLayoutManager;
    private LinearLayoutManager parturienteLinearLayoutManager;
    private NotificationRecyclerAdpter notificationRecyclerAdapter;
    private ParturienteRecyclerAdpter parturienteRecyclerAdpter;
    private HashMap<String, Notification> notificationTriggered = new HashMap<String, Notification>();
//    private GridLayoutManager coursesLayoutManager;
//    private CourseRecyclerAdapter courseRecyclerAdapter;
    private NotificationManagerCompat notificationManager;
    private TextView textNotificacao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        notificationManager=NotificationManagerCompat.from(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        textNotificacao = findViewById(R.id.titleMenu);
        textNotificacao.setText("Notificações");

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, NoteActivity.class));
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        MenuItem item=menu.findItem(R.id.app_bar_search2);
//        SearchView searchView=(SearchView)item.getActionView();
//        searchView.setQueryHint(" Iniciar a Busca...");
//
//        return super.onCreateOptionsMenu(menu);
//    }

    @Override
    public boolean onNavigationItemSelected(@NotNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        textNotificacao = findViewById(R.id.titleMenu);

        if (id == R.id.id_notificacao) {
            textNotificacao.setText("Notificação");
            displayNotifications();
        } else if (id == R.id.id_parturientes) {
            textNotificacao.setText(" Parturiente ");
              displayParturientes();
//            displayCourses();
        }
        else if (id == R.id.id_out) {

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


    public void initializeteDisplayContextParturientes() {
        recyclerItems = (RecyclerView)findViewById(R.id.list_notes);
        parturienteLinearLayoutManager = new LinearLayoutManager(this);
        List<Parturient> parturients= DBManager.getInstance().getParturients();
        parturienteRecyclerAdpter = new ParturienteRecyclerAdpter(this,parturients);
        recyclerItems.setLayoutManager(parturienteLinearLayoutManager);
        recyclerItems.setAdapter(parturienteRecyclerAdpter);
    }



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

        Timer timer = new Timer();
        timer.schedule(new ThreadTimer(this), 0, 100000); // 1 minute

    }

//    private void displayCourses() {
//        recyclerItems.setAdapter(courseRecyclerAdapter);
//        recyclerItems.setLayoutManager(coursesLayoutManager);
//        selectNavigationMenuItem(R.id.nav_courses);
//    }

    private void displayNotifications() {
        recyclerItems.setAdapter(notificationRecyclerAdapter);
        recyclerItems.setLayoutManager(notificationLayoutManager);
        selectNavigationMenuItem(R.id.id_notificacao);
    }
    private void displayParturientes() {
          initializeteDisplayContextParturientes();
//        recyclerItems.setAdapter(parturienteRecyclerAdpter);
//        recyclerItems.setLayoutManager(parturienteLinearLayoutManager);
//        //selectNavigationMenuItem(R.id.id_parturientes);
    }

    private void selectNavigationMenuItem(int id) {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        Menu menu = navigationView.getMenu();
//        menu.findItem(R.id.nav_note).setCheckable(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (notificationRecyclerAdapter != null) {
            notificationRecyclerAdapter.notifyDataSetChanged();
        }
    }

    class ThreadTimer extends TimerTask {
        private Context context;
        private boolean isloading = true;

        public ThreadTimer(Context context) {
            this.context = context;
        }

        public void run() {

            runOnUiThread(new Runnable() {

                @Override
                public void run() {

                    // Stuff that updates the UI
                    Queue queue = DBManager.getInstance().getQueue();
                    queue.nofify();
                    List<Notification> notifications = queue.getNotifications();
                    notificationRecyclerAdapter = new NotificationRecyclerAdpter(context, notifications);

                    recyclerItems = findViewById(R.id.list_notes);
                    notificationLayoutManager = new LinearLayoutManager(context);
                    recyclerItems.setAdapter(notificationRecyclerAdapter);
                    recyclerItems.setLayoutManager(notificationLayoutManager);
                    selectNavigationMenuItem(R.id.nav_note);
//                    }

                    // Send SMS
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (checkSelfPermission(Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
                            List<EmergencyMedicalPersonnel> eP = DBManager.getInstance().getEmergencyMedicalPersonnels();
                            for (EmergencyMedicalPersonnel e : eP) {
                                for (Notification n : notifications) {
                                    System.out.println(" -------------------------------------------- size hash: " + notificationTriggered.size());
                                    if (!notificationTriggered.containsKey(n.getId()) || Calendar.getInstance().getTime().after(n.getNextNotifier())) {
                                        System.out.println("sending SMS to " + e.getContact() + " Message: " + n.getMessage());
                                        sendSMS(e.getContact(), n.getMessage());
                                        popNotification(n);
                                        notificationTriggered.put(n.getId(), n);
                                    } else {
                                        System.out.println(" ------------------------------------------------- notification " + n.getId() + " not trrigeer " + (!notificationTriggered.containsKey(n.getId()) || Calendar.getInstance().getTime().after(n.getNextNotifier())));
                                    }
                                }
                            }
                        } else {
                            requestPermissions(new String[]{Manifest.permission.SEND_SMS}, 1);
                        }
                    }
                }
            });
        }
    }

    private void sendSMS(String phoneNumber, String message) {
        phoneNumber = phoneNumber.trim();
        message = message.trim();

        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, message, null, null);
            Toast.makeText(this, "Message is sent", Toast.LENGTH_SHORT);
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT);
        }
    }

    private void popNotification(Notification notification) {

        Intent intent=new Intent(this,MainActivity.class);
        PendingIntent contentIntent=PendingIntent.getActivity(this,0,intent,0);

        android.app.Notification noti= new NotificationCompat.Builder(this, App.CHANNEL_1_ID)
                .setSmallIcon(R.drawable.mulhergravidabom2)
                .setContentTitle("Alerta").setColor(Color.GREEN)
                .setContentText(notification.getMessage())
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setContentIntent(contentIntent)
                .build();
        notificationManager.notify(Integer.parseInt(notification.getId()),noti);

    }


}
package mz.unilurio.solidermed;

import android.Manifest;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
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
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
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

import java.lang.ref.WeakReference;
import java.util.Calendar;
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
import mz.unilurio.solidermed.utils.Helper;


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
    private Timer timer;


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
//        notificationRecyclerAdapter = new NotificationRecyclerAdpter(this, threadTimer.getNotifications());
//        if (notificationRecyclerAdapter != null) {
//            notificationRecyclerAdapter.notifyDataSetChanged();
//        }else{
//            timer.cancel();
//            System.out.println("entreiiiiiiiiiiiiiii--------------------");
//            new Timer().schedule(new ThreadTimer(this), 0, 100000); // 1 minute
//        }
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
            System.out.println("entreiiiiiii --------------"+recyclerView+ "activity "+activity);
            System.out.println(Helper.format(Calendar.getInstance().getTime()));
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
//            Toast.makeText(activity, s, Toast.LENGTH_SHORT).show();
//            activity.progressBar.setProgress(0);
//            activity.progressBar.setVisibility(View.INVISIBLE);

            // Stuff that updates the UI
            Queue queue = DBManager.getInstance().getQueue();
            queue.nofify();
            List<Notification> notifications = queue.getNotifications();

            activity.notificationRecyclerAdapter = new NotificationRecyclerAdpter(activity, notifications);

            activity.recyclerItems = recyclerView;
            if(recyclerView == null){
                System.out.println(activity);
                System.out.println("------------------------ null ---------------------------------");
            }

            activity.recyclerItems.setAdapter( new NotificationRecyclerAdpter(activity, notifications));
            activity.notificationLayoutManager = new LinearLayoutManager(activity);

            activity.recyclerItems.setLayoutManager(activity.notificationLayoutManager);
            selectNavigationMenuItem(R.id.nav_note);
//                    }

            // Send SMS
            System.out.println("\n\n\n --------------------------");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
                    List<EmergencyMedicalPersonnel> eP = DBManager.getInstance().getEmergencyMedicalPersonnels();


                    System.out.println(" -------------------------------------------- size hash: " + activity.notificationTriggered.size());
                    for (Notification n : notifications) {

                        if (!activity.notificationTriggered.containsKey(n.getId()) || Calendar.getInstance().getTime().after(n.getNextNotifier())) {
                            for (EmergencyMedicalPersonnel e : eP) {
                                System.out.println("Emergengy person: "+e.getContact());
                                System.out.println(Helper.format(Calendar.getInstance().getTime())+": sending SMS to " + e.getContact() + " Message: " + n.getMessage() +" Próxima mensagem será enviada ás: "+ Helper.format(n.getNextNotifier()));
                                sendSMS(e.getContact(), n.getMessage());
                            }
                            activity.notificationTriggered.put(n.getId(), n);
                            activity.popNotification(n);
                        }
                    }
                } else {
                    requestPermissions(new String[]{Manifest.permission.SEND_SMS}, 1);
                }
            }
        }
    }

    class ThreadTimer extends TimerTask {
        private Context context;
        private List<Notification> notifications;

        public ThreadTimer(Context context) {
            this.context = context;
        }

        public List<Notification> getNotifications() {
            return notifications;
        }

        public void run() {

            runOnUiThread(new Runnable() {

                @Override
                public void run() {

                    // Stuff that updates the UI
                    Queue queue = DBManager.getInstance().getQueue();
                    queue.nofify();
                    notifications = queue.getNotifications();
                    notificationRecyclerAdapter = new NotificationRecyclerAdpter(context, notifications);

                    recyclerItems = findViewById(R.id.list_notes);
                    notificationLayoutManager = new LinearLayoutManager(context);
                    recyclerItems.setAdapter(notificationRecyclerAdapter);
                    recyclerItems.setLayoutManager(notificationLayoutManager);
                    selectNavigationMenuItem(R.id.nav_note);
//                    }

                    // Send SMS
                    System.out.println("\n\n\n --------------------------");
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (checkSelfPermission(Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
                            List<EmergencyMedicalPersonnel> eP = DBManager.getInstance().getEmergencyMedicalPersonnels();


                                System.out.println(" -------------------------------------------- size hash: " + notificationTriggered.size());
                                for (Notification n : notifications) {

                                    if (!notificationTriggered.containsKey(n.getId()) || Calendar.getInstance().getTime().after(n.getNextNotifier())) {
                                        for (EmergencyMedicalPersonnel e : eP) {
                                            System.out.println("Emergengy person: "+e.getContact());
                                            System.out.println(Helper.format(Calendar.getInstance().getTime())+": sending SMS to " + e.getContact() + " Message: " + n.getMessage() +" Próxima mensagem será enviada ás: "+ Helper.format(n.getNextNotifier()));
                                            sendSMS(e.getContact(), n.getMessage());
                                        }
                                        notificationTriggered.put(n.getId(), n);
                                        popNotification(n);
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
//            Toast.makeText(this, "Message is sent", Toast.LENGTH_SHORT);
        } catch (Exception e) {
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
        NotificationManager noti2ficationManager=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0,builder.build());


    }


}
package mz.unilurio.solidermed;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.SearchView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import mz.unilurio.solidermed.model.DBManager;
import mz.unilurio.solidermed.model.Notification;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    private AppBarConfiguration mAppBarConfiguration;

    private RecyclerView recyclerItems;

    private LinearLayoutManager notificationLayoutManager;
    private NotificationRecyclerAdpter notificationRecyclerAdapter;

//    private GridLayoutManager coursesLayoutManager;
//    private CourseRecyclerAdapter courseRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
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

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }

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

        if (id == R.id.nav_note) {
            displayNotifications();
        } else if (id == R.id.nav_courses) {
//            displayCourses();
        }
//        else if (id == R.id.nav_share) {
//            handleSelection("Don't you think you've shared enough");
//        } else if (id == R.id.nav_send) {
//            handleSelection("Send");
//        }

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
        recyclerItems = findViewById(R.id.list_notes);

        notificationLayoutManager = new LinearLayoutManager(this);
        List<Notification> notifications = DBManager.getInstance().getNotifications();
        notificationRecyclerAdapter = new NotificationRecyclerAdpter(this, notifications);

//        coursesLayoutManager = new GridLayoutManager(this, 2);
//        List<CourseInfo> courses = DataManager.getInstance().getCourses();
//        courseRecyclerAdapter = new CourseRecyclerAdapter(this, courses);

        displayNotifications();
    }

//    private void displayCourses() {
//        recyclerItems.setAdapter(courseRecyclerAdapter);
//        recyclerItems.setLayoutManager(coursesLayoutManager);
//        selectNavigationMenuItem(R.id.nav_courses);
//    }

    private void displayNotifications() {
        recyclerItems.setAdapter(notificationRecyclerAdapter);
        recyclerItems.setLayoutManager(notificationLayoutManager);
        selectNavigationMenuItem(R.id.nav_note);
    }

    private void selectNavigationMenuItem(int id) {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        Menu menu = navigationView.getMenu();
        menu.findItem(R.id.nav_note).setCheckable(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        notificationRecyclerAdapter.notifyDataSetChanged();
    }


}
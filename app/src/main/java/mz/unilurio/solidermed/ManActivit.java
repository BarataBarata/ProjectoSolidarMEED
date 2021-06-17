package mz.unilurio.solidermed;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.SearchView;

import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import mz.unilurio.solidermed.databinding.ActivityMainBinding;

public class ManActivit extends AppCompatActivity {
    private NoteRecyclerAdapter noteRecyclerAdapter;

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private NoteInfo mNote;
    private LinearLayoutManager noteLinearLayoutManager;
    private RecyclerView recyclerItems;
    //private CoursesRecyclerAdapter coursesRecyclerAdapter;
    //private GridLayoutManager mCoursesLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.appBarMain.toolbar);

        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                startActivity(new Intent(ManActivit.this, Registo.class));
            }

        });




        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_note, R.id.nav_courses, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        ///initializeteDisplayContext();
    }

    @Override
    public void onPostResume() {
        super.onPostResume();
        //noteRecyclerAdapter.notifyDataSetChanged();
    }

    public void initializeteDisplayContext() {
        recyclerItems = (RecyclerView)findViewById(R.id.list_notes);
        noteLinearLayoutManager = new LinearLayoutManager(this);
        List<NoteInfo> notes= DataManager.getInstance().getNotes();
        noteRecyclerAdapter = new NoteRecyclerAdapter(this,notes);
        recyclerItems.setLayoutManager(noteLinearLayoutManager);
        recyclerItems.setAdapter(noteRecyclerAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem item=menu.findItem(R.id.app_bar_search2);
        SearchView searchView=(SearchView)item.getActionView();
        searchView.setQueryHint(" Iniciar a Busca...");

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();


    }

    public void refresh(MenuItem item) {
            Intent intent =new Intent(ManActivit.this,ManActivit.class);
            startActivity(intent);
    }

    public void seacher(MenuItem item) {
        Intent intent =new Intent(ManActivit.this, Seacher.class);
        startActivity(intent);
    }

//    public void ver(View view) {
//        Spinner spinnerCourses=findViewById(R.id.spinner_courses);
//        List<CourseInfo> course=DataManager.getInstance().getCourses();
//        ArrayAdapter<CourseInfo> adapterCourses=
//                new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,course);
//        adapterCourses.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinnerCourses.setAdapter(adapterCourses);
//
//    }
}
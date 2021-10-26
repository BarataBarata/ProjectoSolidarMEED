package mz.unilurio.solidermed;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity_Users_List extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_users_list);
    }

    public void viewContact(View view) {
        startActivity(new Intent(MainActivity_Users_List.this, ActivityMedicos.class));

    }

    public void viewListEnfermeira(View view) {
        startActivity(new Intent(MainActivity_Users_List.this,NurseActivity.class));

    }

    public void finishs(View view) {
        finish();
    }
}
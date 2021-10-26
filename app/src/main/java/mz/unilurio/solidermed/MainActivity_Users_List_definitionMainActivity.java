package mz.unilurio.solidermed;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity_Users_List_definitionMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_users_list_definition_main);
    }

    public void finishs(View view) {
        finish();
    }

    public void viewActivitMaximoDilatation(View view) {
        startActivity(new Intent(MainActivity_Users_List_definitionMainActivity.this,ActivityDefinirMaximoDeDilatacao.class));
    }

    public void viewListDilatation(View view) {
        startActivity(new Intent(MainActivity_Users_List_definitionMainActivity.this, ActivityDilatetionAndHours.class));

    }
}
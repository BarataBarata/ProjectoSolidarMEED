package mz.unilurio.solidermed;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.List;

import mz.unilurio.solidermed.model.Settings;

public class SettingActivity extends AppCompatActivity {

    private RecyclerView view;
    private LinearLayoutManager settingLayoutManager;
    private List<Settings> settings;
    private SettingsRecyclerAdpter settingsRecyclerAdpter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        //initializeteDisplayContextHospitais();

    }

    public void finish(View view) {
        finish();
    }

    public void contact(View view) {
        startActivity(new Intent(SettingActivity.this, ActivityMedicos.class));
    }
    public void finishs(View view) {
        finish();
    }

    public void viewList(View view) {
        startActivity(new Intent(SettingActivity.this,MainActivity_Users_List.class));
    }
    public void viewDilatationActivity(View view) {
       startActivity(new Intent(SettingActivity.this,MainActivity_Users_List_definitionMainActivity .class));
    }
    public void definirTempoEmergencia(View view) {
           startActivity(new Intent(SettingActivity.this,ActivityDefinitionEmergencTimer.class));
    }
    public void definirIdadeGestacional(View view) {
        startActivity(new Intent(SettingActivity.this,ActivityDefinitionIdadeGestacional.class));
    }
    public void selectOpcoesParidade(View view) {
        startActivity(new Intent(SettingActivity.this,ActivitDefinittionOptionParidade.class));
    }
}
package mz.unilurio.solidermed;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import mz.unilurio.solidermed.model.AddDoctorClass;
import mz.unilurio.solidermed.model.Classselecthospital;
import mz.unilurio.solidermed.model.DBService;
import mz.unilurio.solidermed.model.EditDoctorClass;
import mz.unilurio.solidermed.model.Hospitais;

public class SelectHospitalActivity extends AppCompatActivity {
    static boolean start=false;
    private Handler handler;
    private TimerTask task;
    private Timer timer;
    private DBService dbService;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbService=new DBService(this);

        if(dbService.isHospitalSelect()){
           startActivity(new Intent(SelectHospitalActivity.this,StartApp.class));
        }else {
            setContentView(R.layout.activity_select_hospital);
            spinner= findViewById(R.id.spinner_hospital);
            List<Hospitais> list = dbService.getListHospitais();
            ArrayAdapter<Hospitais> adapterhospital = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, list);
            adapterhospital.setDropDownViewResource(android.R.layout.simple_spinner_item);
            spinner.setAdapter(adapterhospital);
        }
    }


    @Override
    protected void onResume() {
        if(dbService.isHospitalSelect())
            finish();
        super.onResume();
    }


    @Override
    protected void onPause() {
        super.onPause();
    }

    public void proximo(View view) {
        dbService.addHospitalSelect(spinner.getSelectedItem()+"");
        startActivity(new Intent(SelectHospitalActivity.this,StartApp.class));
    }
}
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

        if(!dbService.isHospitalSelect()) {

            dbService.addOpcoesParidade(15+"");

            dbService.addIdadeGestacional("Inferior a 28 semanas");
            dbService.addIdadeGestacional("De 28 a 33 semanas");
            dbService.addIdadeGestacional("De 34 a 36 semanas");
            dbService.addIdadeGestacional("Maior ou igual a 36 semanas");

            //dbService.addDilatation(1, 9, 0);
            dbService.addDilatation(1, 0, 1);
            dbService.addDilatation(2, 8, 0);
            dbService.addDilatation(3, 7, 0);
            dbService.addDilatation(4, 9, 0);
            dbService.addDilatation(5, 7, 30);
            dbService.addDilatation(6, 6, 0);
            dbService.addDilatation(7, 4, 30);
            dbService.addDilatation(8, 3, 0);
            dbService.addDilatation(9, 1, 30);
            dbService.addDilatation(10, 0, 30);
            dbService.addAlertDilatation(0, 1);
            dbService.addDoctor("barata", "123", "845740722", "Barata Estevao Mario Barata");
            dbService.addHospital("Hospital Distrital de Chiúre");
            dbService.addHospital("Centro de saúde de Catapua");
            dbService.addHospital("Centro de saúde de Ocua");
            dbService.addHospital("Centro de saúde de Chiúre Velho");
            dbService.addHospital("Centro de saúde de Mmala");
            dbService.addHospital("Centro de saúde de Marera");
            dbService.addHospital("Centro de saúde de Mazeze");
            dbService.addHospital("Centro de saúde de Muege");
            dbService.addHospital("Centro de saúde de Nakoto");
            dbService.addHospital("Centro de saúde de Namogeliua");
            dbService.addHospital("Centro de saúde de Samora Machel");
            dbService.addHospital("Centro de saúde de Bilibiza");


            setContentView(R.layout.activity_select_hospital);
            spinner= findViewById(R.id.spinner_hospital);
            List<Hospitais> list = dbService.getListHospitais();
            ArrayAdapter<Hospitais> adapterhospital = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, list);
            adapterhospital.setDropDownViewResource(android.R.layout.simple_spinner_item);
            spinner.setAdapter(adapterhospital);
        }else {
            startActivity(new Intent(SelectHospitalActivity.this, StartApp.class));
        }
    }


    @Override
    protected void onResume() {
        if(dbService.isHospitalSelect()) {
            finish();
        }
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
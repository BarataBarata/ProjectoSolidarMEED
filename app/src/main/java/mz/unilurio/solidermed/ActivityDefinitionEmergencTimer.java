package mz.unilurio.solidermed;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.slider.Slider;

import org.jetbrains.annotations.NotNull;

import mz.unilurio.solidermed.model.DBService;
import mz.unilurio.solidermed.model.Parturient;

public class ActivityDefinitionEmergencTimer extends AppCompatActivity {
    private static int numberHours;
    private static int numberMinutes;
    private TextView textHoras;
    private TextView textMinutos;
    private Slider sliderMinutos;
    private Slider sliderHora;
    private DBService dbService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_definition_emergenc_timer);

        dbService=new DBService(this);
        textHoras = findViewById(R.id.idViewHorasEmergence);
        textMinutos =findViewById(R.id.idViewMinutosEmergence);
        sliderHora =findViewById(R.id.sliderHorasEmergence);
        sliderMinutos = findViewById(R.id.sliderMinutosEmergence);

        sliderHora.setValue(dbService.getHourasAlert());
        sliderMinutos.setValue(dbService.getMinutesAlert());
        textHoras.setText(dbService.getHourasAlert()+" : ");
        textMinutos.setText(dbService.getMinutesAlert()+"");

        sliderHora.addOnChangeListener(new Slider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull @NotNull Slider slider, float value, boolean fromUser) {
                textHoras.setText((int) sliderHora.getValue() + " : ");
                numberHours = (int) sliderHora.getValue();
            }
        });

        sliderMinutos.addOnChangeListener(new Slider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull @NotNull Slider slider, float value, boolean fromUser) {
                textMinutos.setText((int) sliderMinutos.getValue() + " ");
                numberMinutes = (int) sliderMinutos.getValue();
            }
        });
    }

    public void finish(View view) {
        finish();
    }

    public void saveTempoEmergenca(View view) {
        alerte();
    }


    public  void alerte(){

        androidx.appcompat.app.AlertDialog.Builder dialog=new androidx.appcompat.app.AlertDialog.Builder(this);
        dialog.setMessage(" Salvar?");
        dialog.setCancelable(false);
        dialog.setIcon((R.drawable.icondilatacao));

        dialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                ProgressDialog progressBar;
                progressBar=new ProgressDialog(ActivityDefinitionEmergencTimer.this);
                progressBar.setTitle("Aguarde...");
               // progressBar.setIcon(R.drawable.tempo_acabado);
                progressBar.show();

                new Handler().postDelayed(new Thread() {
                    @Override
                    public void run() {
                        progressBar.dismiss();
                        Parturient parturient=new Parturient();
                        parturient.setTimerEmergence(numberHours*3600+numberMinutes*60);
                        dbService.updadeAlertDilatation(dbService.getIdAlertDilatation(),numberHours,numberMinutes);
                        finish();
                    }
                },Long.parseLong("400"));
                //Toast.makeText(getApplicationContext(), " Selecionado com sucesso", Toast.LENGTH_LONG).show();
            }

        });
        dialog.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext()," Operacao Cancelada",Toast.LENGTH_LONG).show();

            }
        });

        dialog.create();
        dialog.show();
    }

}
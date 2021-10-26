package mz.unilurio.solidermed;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.slider.Slider;

import org.jetbrains.annotations.NotNull;

import java.util.Calendar;

import mz.unilurio.solidermed.model.DBService;

public class ActivitDefinittionOptionParidade extends AppCompatActivity {

    private Slider sliderMaximoOpcoesParidade;
    private TextView textViewNumber;
    private  DBService dbService;
    private int valor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activit_definittion_option_paridade);

        dbService=new DBService(this);
        textViewNumber=findViewById(R.id.idViewMaximoIdadeGest);
        textViewNumber.setText(dbService.getOpcoesParidadeMaximoValor()+".0");
        sliderMaximoOpcoesParidade =findViewById(R.id.sliderMaximoIdadeGestacional);
        sliderMaximoOpcoesParidade.setValue(dbService.getOpcoesParidadeMaximoValor());
        sliderMaximoOpcoesParidade.addOnChangeListener(new Slider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull @NotNull Slider slider, float value, boolean fromUser) {
                textViewNumber.setText(value+"");
                valor=(int)value;
            }
        });
    }
    public void finish(View view) {
        finish();
    }

    public void saveMaximoOpcoesParidade(View view) {
            dbService.updadeOpcoesParidade(valor + "");
            Toast.makeText(this, "O valor foi definido com sucesso", Toast.LENGTH_SHORT).show();
    }
}
package mz.unilurio.solidermed;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.slider.Slider;

import org.jetbrains.annotations.NotNull;

public class ActivityDefinirMaximoDeDilatacao extends AppCompatActivity {
    private Slider sliderMaximoDilatacao;
    private TextView textViewNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_definir_maximo_de_dilatacao);

        textViewNumber=findViewById(R.id.idViewMaximoDilatation);
        sliderMaximoDilatacao=findViewById(R.id.sliderMaximoDilatation);
        sliderMaximoDilatacao.addOnChangeListener(new Slider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull @NotNull Slider slider, float value, boolean fromUser) {
                textViewNumber.setText(value+"");
            }
        });
    }

    public void finish(View view) {
        finish();
    }

    public void saveMaximoDilatation(View view) {

    }
}
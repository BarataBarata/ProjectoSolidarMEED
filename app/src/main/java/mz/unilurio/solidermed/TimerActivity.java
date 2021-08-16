package mz.unilurio.solidermed;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.List;

import mz.unilurio.solidermed.model.DBManager;

public class TimerActivity extends AppCompatActivity {
    private Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);



        spinner = findViewById(R.id.spinner_dilatacaoInicial);
        List<Integer> list = DBManager.getInstance().getIntegerListInitDilatation();
        ArrayAdapter<Integer> adapterInitializeDilatation = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, list);
        adapterInitializeDilatation.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapterInitializeDilatation);

        spinner = findViewById(R.id.spinner_dilatacaoEnd);
        List<Integer> listLimit = DBManager.getInstance().getIntegerListLimitDilatation() ;
        ArrayAdapter<Integer> adapterInitializeDilatationlimit = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listLimit);
        adapterInitializeDilatationlimit.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapterInitializeDilatationlimit);

        spinner = findViewById(R.id.spinner_dilatacaoCervial);
        List<Integer> listLimitCervical = DBManager.getInstance().getIntegerListDilatationCervical() ;
        ArrayAdapter<Integer> adapterInitializeDilatationCervical = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listLimitCervical);
        adapterInitializeDilatationCervical.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapterInitializeDilatationCervical);

        spinner = findViewById(R.id.spinner_dilatacaoParidade);
        List<Integer> listLimitParidade = DBManager.getInstance().getIntegerListDilatationCervical() ;
        ArrayAdapter<Integer> adapterInitializeDilatationParidade = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listLimitParidade );
        adapterInitializeDilatationParidade .setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapterInitializeDilatationParidade );

        spinner = findViewById(R.id.spinner_Timer);
        List<Integer> listLimitTimer = DBManager.getInstance().getIntegerListTimer() ;
        ArrayAdapter<Integer> adapterInitializeTimer = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listLimitTimer);
        adapterInitializeTimer .setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapterInitializeTimer );

    }

    public void finish(View view) {
        finish();
    }
}
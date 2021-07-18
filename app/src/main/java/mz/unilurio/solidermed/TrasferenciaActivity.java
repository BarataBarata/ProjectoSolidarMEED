package mz.unilurio.solidermed;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

import mz.unilurio.solidermed.model.DBManager;
import mz.unilurio.solidermed.model.Parturient;

public class TrasferenciaActivity extends AppCompatActivity {

    private TextView textNome;
    private TextView textApelido;
    private Spinner spinnerDestino;
    private Spinner spinnerMotivo;
    private int idParturiente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trasferencia);

        initialize();

        if(getIntent().getStringExtra("idParturiente")!=null){
            idParturiente = Integer.parseInt(getIntent().getStringExtra("idParturiente"));

            for(Parturient parturient: DBManager.getInstance().getParturients()){
                if(parturient.getId()==idParturiente){
                    textNome.setText(parturient.getName());
                    textApelido.setText(parturient.getSurname());
                    break;
                }
            }
        }

        List<String> listSanitaria = DBManager.getInstance().getListOpcoesUnidadeSanitaria();
        ArrayAdapter<String> adapterSanitaria = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listSanitaria);
        adapterSanitaria.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerDestino.setAdapter( adapterSanitaria);

        List<String> listTrasferencia = DBManager.getInstance().getListMotivosTrasferencia();
        ArrayAdapter<String> adapterTrasferencia = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listTrasferencia);
        adapterTrasferencia.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerMotivo.setAdapter( adapterTrasferencia );


    }

    private void initialize() {
        textNome = findViewById(R.id.nomeParturienteTrasferenciaX);
        textApelido = findViewById(R.id.apelidoParturienteTrasferenciaX);
        spinnerDestino = findViewById(R.id.destinoTrasferenciaX);
        spinnerMotivo = findViewById(R.id.motivosTrasferenciaX);
    }

    public void finish(View view) {
        finish();
    }
}
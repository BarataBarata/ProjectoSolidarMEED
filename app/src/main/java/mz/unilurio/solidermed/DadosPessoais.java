package mz.unilurio.solidermed;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import mz.unilurio.solidermed.model.DBManager;
import mz.unilurio.solidermed.model.Parturient;

public class DadosPessoais extends AppCompatActivity {
    Toolbar toolbar;
    private int idParturiente;
    TextView nomeParturiente;
    TextView idadeParturiente;
    TextView dilatacaoParturiente;
    TextView idadeDeParidadeDaParturiente;
    TextView idadeGestacionalDaParturiente;
    TextView horaEntradaDaParturiente;
    TextView horaActualDaParturiente;
    TextView origemTrasferenciaParturiente;
    TextView motivosTrasferenciaParturiente;
    private TextView textApelido;
    private TextView textDilatacaoInicial;
    private Parturient parturient;
    private RelativeLayout relativeShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dados_pessoais);

        toolbar=findViewById(R.id.toolbarDadosPessoais);
        initialize();

        idParturiente =Integer.parseInt(getIntent().getStringExtra("id"));
        for(Parturient parturient:DBManager.getInstance().getParturients()){
            if(parturient.getId()==idParturiente){
                sendDade(parturient);
               break;
            }
        }
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fabEdit);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(DadosPessoais.this, AddParturientActivity.class);
                intent.putExtra("idParturiente",idParturiente+"");
                 startActivity(intent);
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });
    }

    private void sendDade(Parturient parturient) {
        nomeParturiente.setText(parturient.getName());
        idadeParturiente.setText(parturient.getAge()+"");
        dilatacaoParturiente.setText(parturient.getReason()+"");
        horaActualDaParturiente.setText(format(new Date()));
        idadeDeParidadeDaParturiente.setText(parturient.getPara()+"");
        idadeGestacionalDaParturiente.setText( parturient.getGestatinalRange()+"");

        if(parturient.getTime()!=null)
            horaEntradaDaParturiente.setText(format(parturient.getTime()));
        nomeParturiente.setText(parturient.getName());
        textApelido.setText(parturient.getSurname());
        textDilatacaoInicial.setText(parturient.getReason());

        if(parturient.isTransfered()){
            motivosTrasferenciaParturiente.setText(parturient.getMotivosDaTrasferencia()+"");
            origemTrasferenciaParturiente.setText(parturient.getOrigemTransferencia());
            relativeShow.setVisibility(View.VISIBLE);
        }else {
            relativeShow.setVisibility(View.INVISIBLE);
        }

    }

    private void initialize() {
        relativeShow = findViewById(R.id.isTransferidok);
        textDilatacaoInicial = findViewById(R.id.dilatacaoInicialParturientek);
        textApelido = findViewById(R.id.apelidoParturientek);
        nomeParturiente=(TextView)findViewById(R.id.nomeParturientek);
        idadeParturiente=(TextView)findViewById(R.id.idadeParturientek);
        dilatacaoParturiente=(TextView)findViewById(R.id.dilatacaoAtualk);
        idadeDeParidadeDaParturiente=(TextView)findViewById(R.id.opcoesDeParidadek);
        idadeGestacionalDaParturiente=(TextView)findViewById(R.id.idadeGestacionalk);
        horaActualDaParturiente=(TextView)findViewById(R.id.horaAtual);
        horaEntradaDaParturiente=(TextView)findViewById(R.id.horaEntrada);
        origemTrasferenciaParturiente=(TextView)findViewById(R.id.origemTrasferenciak);
        motivosTrasferenciaParturiente=(TextView)findViewById(R.id.motivosTrasferenciak);
    }

    private String format(Date date){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return dateFormat.format(date);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dados_pessoais, menu);

        return true;
    }

    public void finish(View view) {
        finish();
    }
}
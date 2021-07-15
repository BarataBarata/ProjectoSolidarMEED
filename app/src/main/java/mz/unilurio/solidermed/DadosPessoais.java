package mz.unilurio.solidermed;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import mz.unilurio.solidermed.model.DBManager;

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
    private RelativeLayout relativeShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dados_pessoais);
        toolbar=findViewById(R.id.toolbarDadosPessoais);
        setSupportActionBar(toolbar);
        initialize();
        sendDade();

    }

    private void sendDade() {

        idParturiente =Integer.parseInt(getIntent().getStringExtra("id"));
        System.out.println(idParturiente+"   hhhhhhhhhhhhf");
        nomeParturiente.setText(DBManager.getInstance().getParturients().get(idParturiente).getName());
        idadeParturiente.setText(DBManager.getInstance().getParturients().get(idParturiente).getAge()+"");
        dilatacaoParturiente.setText(DBManager.getInstance().getParturients().get(idParturiente).getReason()+"");
        horaActualDaParturiente.setText(format(new Date()));
        idadeDeParidadeDaParturiente.setText(DBManager.getInstance().getParturients().get(idParturiente).getPara()+"");
        idadeGestacionalDaParturiente.setText( DBManager.getInstance().getParturients().get(idParturiente).getGestatinalRange()+"");
        if(DBManager.getInstance().getParturients().get(idParturiente).getTime()!=null)
        horaEntradaDaParturiente.setText(format(DBManager.getInstance().getParturients().get(idParturiente).getTime()));
        nomeParturiente.setText(DBManager.getInstance().getParturients().get(idParturiente).getName());
        textApelido.setText(DBManager.getInstance().getParturients().get(idParturiente).getSurname());
        textDilatacaoInicial.setText(DBManager.getInstance().getParturients().get(idParturiente).getReason());

        if(DBManager.getInstance().getParturients().get(idParturiente).isTransfered()){
            motivosTrasferenciaParturiente.setText(DBManager.getInstance().getParturients().get(idParturiente).getMotivosDaTrasferencia()+"");
            origemTrasferenciaParturiente.setText(DBManager.getInstance().getParturients().get(idParturiente).getOrigemTransferencia());
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
package mz.unilurio.solidermed;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import mz.unilurio.solidermed.model.DBManager;

public class DadosPessoais extends AppCompatActivity {
    Toolbar toolbar;
    private int idNotification;
    TextView nomeParturiente;
    TextView idadeParturiente;
    TextView dilatacaoParturiente;
    TextView idadeDeParidadeDaParturiente;
    TextView idadeGestacionalDaParturiente;
    TextView horaEntradaDaParturiente;
    TextView horaActualDaParturiente;
    TextView origemTrasferenciaParturiente;
    TextView motivosTrasferenciaParturiente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dados_pessoais);
        toolbar=findViewById(R.id.toolbarDadosPessoais);
        setSupportActionBar(toolbar);

        nomeParturiente=(TextView)findViewById(R.id.idNomeParturiente);
        idadeParturiente=(TextView)findViewById(R.id.idIdadeParturiente);
        dilatacaoParturiente=(TextView)findViewById(R.id.idDilatacaoActual);
        //idadeDeParidadeDaParturiente=(TextView)findViewById(R.id.idOpcoesDeParidade);
        //idadeGestacionalDaParturiente=(TextView)findViewById(R.id.idIdadeGestacional);
        horaActualDaParturiente=(TextView)findViewById(R.id.idHoraActual);
        horaEntradaDaParturiente=(TextView)findViewById(R.id.idHoraEntrada);
        //origemTrasferenciaParturiente=(TextView)findViewById(R.id.idOrigemTrasferencia);
        //motivosTrasferenciaParturiente=(TextView)findViewById(R.id.IdMotivosTrasferencia);

        idNotification=Integer.parseInt(getIntent().getStringExtra("id"));
        System.out.println(idNotification);
        nomeParturiente.setText(DBManager.getInstance().getParturients().get(idNotification).getName()+" "+DBManager.getInstance().getParturients().get(idNotification).getSurname());
        idadeParturiente.setText(DBManager.getInstance().getParturients().get(idNotification).getAge()+"");
        dilatacaoParturiente.setText(DBManager.getInstance().getParturients().get(idNotification).getReason()+"");
        horaActualDaParturiente.setText(format(new Date()));
        //horaEntradaDaParturiente.setText(format(DBManager.getInstance().getParturients().get(idNotification).getTime()));
        //nomeParturiente.setText(new DBManager().getParturients().get(idNotification));
        //nomeParturiente.setText(new DBManager().getParturients().get(idNotification).getName());
        //nomeParturiente.setText(new DBManager().getParturients().get(idNotification).getName());
        //nomeParturiente.setText(new DBManager().getParturients().get(idNotification).getName());





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
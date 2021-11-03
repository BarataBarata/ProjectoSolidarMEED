package mz.unilurio.solidermed;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import mz.unilurio.solidermed.model.DBManager;
import mz.unilurio.solidermed.model.Parturient;

public class ViewDadosPessoaisActivity extends AppCompatActivity {
    private int idParturiente;
    TextView nomeParturiente;
    TextView idadeParturiente;
    TextView dilatacaoParturiente;
    TextView idadeDeParidadeDaParturiente;
    TextView idadeGestacionalDaParturiente;
    TextView horaEntradaDaParturiente;
    TextView tipoAtendimento;
    TextView origemTrasferenciaParturiente;
    TextView motivosTrasferenciaParturiente;
    private Parturient parturient;
    private CardView cardView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_dados_parturientes);
        initialize();
        //activity_view_dados_pessoais
        if(getIntent().getStringExtra("idParturiente")!=null) {
            idParturiente = Integer.parseInt(getIntent().getStringExtra("idParturiente"));
            for (Parturient parturient : DBManager.getInstance().getParturients()) {
                if (parturient.getId() == idParturiente) {
                    sendDade(parturient);
                    break;
                }
            }
        }

        if(getIntent().getStringExtra("idParturienteAtendidos")!=null) {
            idParturiente = Integer.parseInt(getIntent().getStringExtra("idParturienteAtendidos"));
            for (Parturient parturient : DBManager.getInstance().getListParturientesAtendidos()) {
                if (parturient.getId() == idParturiente) {
                    sendDade(parturient);
                    break;
                }
            }
        }



    }
    private void initialize() {
        cardView = findViewById(R.id.card3);
        nomeParturiente=(TextView)findViewById(R.id.idViewFullName);
        idadeParturiente=(TextView)findViewById(R.id.viewIdade);
        dilatacaoParturiente=(TextView)findViewById(R.id.viewDilatacaoInicial);
        idadeDeParidadeDaParturiente=(TextView)findViewById(R.id.viewOpcoesDeParidade);
        idadeGestacionalDaParturiente=(TextView)findViewById(R.id.viewIdadeGestacional);
        horaEntradaDaParturiente=(TextView)findViewById(R.id.viewHoraEntradaF);
        origemTrasferenciaParturiente=(TextView)findViewById(R.id.idOrigemTrasferencia);
        motivosTrasferenciaParturiente=(TextView)findViewById(R.id.idMotivosTrasferencia);
        tipoAtendimento =(TextView)findViewById(R.id.ViewTipoAtendimento);
    }





        private void sendDade(Parturient parturient) {

        if(!parturient.getTipoAtendimento().isEmpty()){
            tipoAtendimento.setText("Tipo de atentimento :"+parturient.getTipoAtendimento());
            tipoAtendimento.setVisibility(View.VISIBLE);
        }

        nomeParturiente.setText(" Nome da parturiente :"+ parturient.getFullName());
        idadeParturiente.setText(" Idade da parturiente :"+ parturient.getAge()+" anos de idade");
        dilatacaoParturiente.setText(" dilatação inicial da parturiente :"+parturient.getReason()+" cm");
        idadeDeParidadeDaParturiente.setText("opções de Paridade da parturiente é de : "+parturient.getPara()+"");
        idadeGestacionalDaParturiente.setText( "A sua idade gestacional é de "+ parturient.getGestatinalRange()+"");

        if(parturient.getHoraEntrada()!=null)
            horaEntradaDaParturiente.setText(" A hora de entrada da Parturiente : "+(parturient.getHoraEntrada()));

        if(parturient.isTransfered() || parturient.isTrasferidoParaForaDoHospital()){
            if(parturient.isTrasferidoParaForaDoHospital()){
                motivosTrasferenciaParturiente.setText(" Motivo da  transferência  da parturiente para fora da unidade Sanitaria :" + parturient.getMotivosDestinoDaTrasferencia() + " ");
                origemTrasferenciaParturiente.setText("Destino da  transferência :" + parturient.getDestinoTrasferencia());

            }else {
                motivosTrasferenciaParturiente.setText(" Motivo da  transferência  da parturiente :" + parturient.getMotivosDaTrasferencia() + " ");
                origemTrasferenciaParturiente.setText("Origem da  transferência :" + parturient.getOrigemTransferencia());
            }
            cardView.setVisibility(View.VISIBLE);
        }else {
            cardView.setVisibility(View.INVISIBLE);
        }

    }

    public void finish(View view) {
        finish();
    }
    private String format(Date date){
        DateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");
        return dateFormat.format(date);
    }
}
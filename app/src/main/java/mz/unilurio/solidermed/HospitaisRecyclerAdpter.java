package mz.unilurio.solidermed;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import mz.unilurio.solidermed.model.Hospitais;

public class HospitaisRecyclerAdpter extends RecyclerView.Adapter<HospitaisRecyclerAdpter.ViewHolder> {
    HospitaisActivity  context;
    private List<Hospitais> hospitais;
    private final LayoutInflater layoutInflater;


    public HospitaisRecyclerAdpter(HospitaisActivity context, List<Hospitais> hospitais) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        this.hospitais = hospitais;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_hospitais_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Hospitais hospitais1 =  hospitais.get(position);
        holder.currentPosition = position;
        holder.txtNameParturient.setText(hospitais1.getNomeHospital());
//
//        holder.buttonViewOption.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                //creating a popup menu
//                Context mCtx = null;
//                PopupMenu popup = new PopupMenu(context, holder.buttonViewOption);
//                //inflating menu from xml resource
//                popup.inflate(R.menu.options_menu);
//                //adding click listener
//                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//                    @Override
//                    public boolean onMenuItemClick(MenuItem item) {
//                        switch (item.getItemId()) {
//
//                            case R.id.atendimento:{
//                                Intent intent = new Intent(context, Atendimento.class);
//                                context.startActivity(intent);
//                            }
//                                return true;
//                            case R.id.item3:
//                                //handle menu3 click
//                                return true;
//                            default:
//                                return false;
//                        }
//                    }
//                });
//                //displaying the popup
//                popup.show();
//
//            }
//        });
    }
    @Override
    public int getItemCount() {
        return hospitais.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public int currentPosition;
        public final CardView cardView;
        public final TextView txtTime;
        public final TextView txtNameParturient;
        public final TextView txtDetails;
        public View buttonViewOption;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView = (CardView) itemView.findViewById(R.id.card_view);
            txtTime = (TextView) itemView.findViewById(R.id.txtTime);
            txtNameParturient = (TextView) itemView.findViewById(R.id.txtNamePartur);
            txtDetails = (TextView) itemView.findViewById(R.id.txtDetails);
            buttonViewOption=(TextView)itemView.findViewById(R.id.textViewOptions);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                     alerte(currentPosition);
                    }

            });
        }


    }

    private String format(Date date){
        DateFormat dateFormat = new SimpleDateFormat("hh:mm - dd, MMM");
        return dateFormat.format(date);
    }
    public  void alerte(int currentPosition){

        androidx.appcompat.app.AlertDialog.Builder dialog=new androidx.appcompat.app.AlertDialog.Builder(context);
        dialog.setTitle("Centro de Saude");
        dialog.setMessage(" Carregar dados do "+hospitais.get(currentPosition).getNomeHospital()+" ?");
        dialog.setCancelable(false);
        dialog.setIcon((R.drawable.hospital));

        dialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                ProgressDialog progressBar;
                progressBar=new ProgressDialog(context);
                progressBar.setTitle("Aguarde");
                progressBar.setMessage("processando...");
                progressBar.show();

                new Handler().postDelayed(new Thread() {
                    @Override
                    public void run() {
                        progressBar.dismiss();
                        Intent intent = new Intent(context, MainActivity.class);
                        intent.putExtra("nomeHospital",hospitais.get(currentPosition).getNomeHospital()+"");
                        context.startActivity(intent);
                    }
                },Long.parseLong("400"));
                    //Toast.makeText(getApplicationContext(), " Selecionado com sucesso", Toast.LENGTH_LONG).show();
                }

            });
        dialog.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(context.getApplicationContext()," Operacao Cancelada",Toast.LENGTH_LONG).show();

            }
        });

        dialog.create();
        dialog.show();
    }

}

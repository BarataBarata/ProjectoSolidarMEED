package mz.unilurio.solidermed;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import mz.unilurio.solidermed.model.Hospitais;

public class HospitaisRecyclerAdpter extends RecyclerView.Adapter<HospitaisRecyclerAdpter.ViewHolder> implements Filterable {
    HospitaisActivity  context;
    private List<Hospitais> auxListHospital;
    private List<Hospitais> originalListHospital;
    private final LayoutInflater layoutInflater;


    public HospitaisRecyclerAdpter(HospitaisActivity context, List<Hospitais> originalListHospital) {
        this.context = context;
        this.auxListHospital=new ArrayList<>(originalListHospital);
        layoutInflater = LayoutInflater.from(context);
        this.originalListHospital = originalListHospital;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_hospitais_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Hospitais hospitais1 =  originalListHospital.get(position);
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
        return originalListHospital.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter=new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            List<Hospitais>list =new ArrayList<>();

            if(constraint.toString().isEmpty()){
                list.addAll(auxListHospital);
            }else{
                for(Hospitais hospitais: auxListHospital){
                    if(hospitais.getNomeHospital().toLowerCase().contains(constraint.toString().toLowerCase())){
                        list.add(hospitais);
                    }
                }
            }

            FilterResults filterResults=new FilterResults();
            filterResults.values=list;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            originalListHospital.clear();
            originalListHospital.addAll((Collection<? extends Hospitais>) results.values);
            notifyDataSetChanged();
        }
    };


    public class ViewHolder extends RecyclerView.ViewHolder {
        public int currentPosition;
        public final TextView txtTime;
        public final TextView txtNameParturient;

        public View buttonViewOption;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtTime = (TextView) itemView.findViewById(R.id.txtTime);
            txtNameParturient = (TextView) itemView.findViewById(R.id.txt_idHospitais);

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
        dialog.setMessage(" Carregar dados do "+ originalListHospital.get(currentPosition).getNomeHospital()+" ?");
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
                        intent.putExtra("nomeHospital", originalListHospital.get(currentPosition).getNomeHospital()+"");
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

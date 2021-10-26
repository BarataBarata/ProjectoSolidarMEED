package mz.unilurio.solidermed;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import mz.unilurio.solidermed.model.Parturient;

public class AtendidosRecyclerAdpter extends RecyclerView.Adapter<AtendidosRecyclerAdpter.ViewHolder> implements Filterable {

    private final Context context;
    private List<Parturient> originalListParturientes;
    private List<Parturient> auxListParturientes;
    private final LayoutInflater layoutInflater;


    public AtendidosRecyclerAdpter(Context context, List<Parturient> parturients) {
        this.context = context;
        this.auxListParturientes =new ArrayList<>(parturients);
        layoutInflater = LayoutInflater.from(context);
        this.originalListParturientes = parturients;
    }

    public  String oUpperFirstCase(String string){
        String auxString=(string.charAt(0)+"").toUpperCase()+""+string.substring(1)+"";
        return  auxString;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_atendidos_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Parturient parturient =  originalListParturientes.get(position);
        holder.currentPosition = position;

        if(parturient.isTrasferidoParaForaDoHospital()){
            holder.tipoAtendimento.setText("Trasferida ( " + parturient.getMotivosDestinoDaTrasferencia() + " )");
        }else {
            if (parturient.isTrasferirParturiente()) {
                holder.tipoAtendimento.setText("Trasferida ( " + parturient.getMotivosDaTrasferencia() + " )");
            } else {
                holder.tipoAtendimento.setText("Tipo de atendimento ( " + parturient.getTipoAtendimento() + " )");
            }
        }
        System.out.println("  pppppppppppppp: === "+parturient.getFullName());
        holder.txtHoraAtendido.setText("Saida : "+oUpperFirstCase(parturient.getHoraAtendimento()));
        holder.textCircle.setText((parturient.getName().charAt(0)+""));
        holder.txtNameParturient.setText(oUpperFirstCase(parturient.getName()+ " "+ parturient.getSurname()));
//        holder.buttonViewOption.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                //creating a popup menu
//                Context mCtx = null;
//                PopupMenu popup = new PopupMenu(context, holder.buttonViewOption);
//                //inflating menu from xml resource
//                popup.inflate(R.menu.options_menu_parturiente_atendidos);
//                //adding click listener
//                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//                    @Override
//                    public boolean onMenuItemClick(MenuItem item) {
//                        switch (item.getItemId()) {
//                            case R.id.idEdit:{
//                                Intent intent = new Intent(context,AddParturientActivity.class);
//                                intent.putExtra("idParturienteAtendidos", originalListParturientes.get(position).getId()+"");
//                                context.startActivity(intent);
//                            }
//                            return true;
////                            case R.id.transferir:
////                                Intent intent = new Intent(context,TrasferenciaActivity.class);
////                                intent.putExtra("idParturienteAtendidos", originalListParturientes.get(position).getId()+"");
////                                context.startActivity(intent);
////                                //handle menu3 click
////                                return true;
//                            default:
//                                return false;
//                        }
//                    }
//                });
//                popup.show();
//            }
       /// });
    }

    @Override
    public int getItemCount() {
        return originalListParturientes.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter=new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            List<Parturient>list =new ArrayList<>();

            if(constraint.toString().isEmpty()){
                list.addAll(auxListParturientes);
            }else{
                for(Parturient parturient:auxListParturientes){
                    if(parturient.getFullName().toLowerCase().contains(constraint.toString().toLowerCase())){
                        list.add(parturient);
                        System.out.println(parturient.getName());
                    }
                }
            }
            System.out.println(list.toString());
            FilterResults filterResults=new FilterResults();
            filterResults.values=list;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            originalListParturientes.clear();
            originalListParturientes.addAll((Collection<? extends Parturient>) results.values);
            notifyDataSetChanged();
        }
    };

    public class ViewHolder extends RecyclerView.ViewHolder {
        public int currentPosition;
        public final CardView cardView;
        public final TextView txtHoraAtendido;
        public final TextView txtNameParturient;
        public final TextView textCircle;
        public View buttonViewOption;
        public final TextView tipoAtendimento;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tipoAtendimento=(TextView) itemView.findViewById(R.id.idTipoAtendimento);
            textCircle=(TextView)itemView.findViewById(R.id.id_ImagemSettings);
            cardView = (CardView) itemView.findViewById(R.id.card_view);
            txtHoraAtendido = (TextView) itemView.findViewById(R.id.idHoraSaida);
            txtNameParturient = (TextView) itemView.findViewById(R.id.idNomeMedico);
            buttonViewOption=(TextView) itemView.findViewById(R.id.textViewOptionsParturienteAtenditos);
            //txtDetails = (TextView) itemView.findViewById(R.id.txtDetails);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ViewDadosPessoaisActivity.class);
                    intent.putExtra("idParturienteAtendidos", +originalListParturientes.get(currentPosition).getId()+"");
                    context.startActivity(intent);
                }
            });
        }
    }
    private String format(Date date){
        DateFormat dateFormat = new SimpleDateFormat("hh:mm - dd, MMM");
        return dateFormat.format(date);
    }
}

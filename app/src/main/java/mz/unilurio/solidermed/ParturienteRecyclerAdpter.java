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

public class ParturienteRecyclerAdpter extends RecyclerView.Adapter<ParturienteRecyclerAdpter.ViewHolder> implements Filterable {

    private final Context context;
    private List<Parturient> originalListParturientes;
    private List<Parturient> auxListParturientes;
    private final LayoutInflater layoutInflater;


    public ParturienteRecyclerAdpter(Context context, List<Parturient> parturients) {
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

        View view = layoutInflater.inflate(R.layout.item_parturientes_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Parturient parturient =  originalListParturientes.get(position);
        holder.currentPosition = position;
        holder.txtTime.setText(format2(parturient.getTime()));
        holder.textCircle.setText((parturient.getName().charAt(0)+"").toUpperCase());
        holder.horaEntrada.setText("Entrada : "+format(parturient.getHoraEntrada())+" PM");
        holder.horaAlerta.setText("Tempo Alerta : "+format(parturient.getHoraAlerta())+" PM");
        holder.txtNameParturient.setText(oUpperFirstCase(parturient.getName())+ " "+oUpperFirstCase(parturient.getSurname()));
        holder.buttonViewOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //creating a popup menu
                Context mCtx = null;
                PopupMenu popup = new PopupMenu(context, holder.buttonViewOption);
                //inflating menu from xml resource
                popup.inflate(R.menu.options_menu_parturiente);
                //adding click listener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.idEditar:{
                                Intent intent = new Intent(context,AddParturientActivity.class);
                                intent.putExtra("idParturiente", originalListParturientes.get(position).getId()+"");
                               context.startActivity(intent);
                            }
                            return true;
                            case R.id.transferir:
                                Intent intent = new Intent(context,TrasferenciaActivity.class);
                                intent.putExtra("idParturiente", originalListParturientes.get(position).getId()+"");
                                context.startActivity(intent);
                                return true;
                            case R.id.item3:
                                //handle menu3 click
                                return true;
                            default:
                                return false;
                        }
                    }
                });
                //displaying the popup
                popup.show();

            }
        });




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
                    if(parturient.getName().toLowerCase().contains(constraint.toString().toLowerCase())){
                        list.add(parturient);
                        System.out.println(parturient.getName());
                    }
                }
            }

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
        public final TextView horaAlerta;
        public final TextView horaEntrada;
        public final TextView txtTime;
        public final TextView txtNameParturient;
        public final TextView textCircle;

        public View buttonViewOption;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            horaEntrada=(TextView)itemView.findViewById(R.id.idHoraEntrada);
            horaAlerta=(TextView) itemView.findViewById(R.id.idHoraAlerta);
            textCircle=(TextView)itemView.findViewById(R.id.textCircle);
            cardView = (CardView) itemView.findViewById(R.id.card_view);
            txtTime = (TextView) itemView.findViewById(R.id.txtTimeParturiente);
            txtNameParturient = (TextView) itemView.findViewById(R.id.idSetting);
            buttonViewOption=(TextView) itemView.findViewById(R.id.textViewOptionsParturiente);
            //txtDetails = (TextView) itemView.findViewById(R.id.txtDetails);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DadosPessoais.class);
                    intent.putExtra("id", originalListParturientes.get(currentPosition).getId()+"");
                    context.startActivity(intent);
                }
            });
        }


    }

    private String format(Date date){
        DateFormat dateFormat = new SimpleDateFormat("hh:mm");
        return dateFormat.format(date);
    }
    private String format2(Date date){
        DateFormat dateFormat = new SimpleDateFormat("hh:mm-yyyy-MM-dd ");
        return dateFormat.format(date);
    }

}

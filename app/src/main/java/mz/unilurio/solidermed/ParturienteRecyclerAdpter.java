package mz.unilurio.solidermed;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import mz.unilurio.solidermed.model.Parturient;

public class ParturienteRecyclerAdpter extends RecyclerView.Adapter<ParturienteRecyclerAdpter.ViewHolder> implements Filterable {
    private final Context context;
    private List<Parturient> originalListParturientes;
    private List<Parturient> auxListParturientes;
    private final LayoutInflater layoutInflater;
    private TimerTask taskMinutos;
    private Handler handlerMinutos;


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
        holder.textCircle.setText((parturient.getName().charAt(0)+""));
        holder.horaEntrada.setText("Entrada : "+parturient.getHoraEntrada()+"");
        holder.txtNameParturient.setText(parturient.getName()+" "+parturient.getSurname());
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
                            case R.id.idEdit:{
                                Intent intent = new Intent(context,AddParturientActivity.class);
                                intent.putExtra("idParturiente", originalListParturientes.get(position).getIdAuxParturiente()+"");
                                context.startActivity(intent);
                            }
                            return true;
                            case R.id.transferir:
                                Intent intent = new Intent(context,ViewAtendimentoActivity.class);
                                intent.putExtra("idParturiente", originalListParturientes.get(holder.currentPosition).getIdAuxParturiente()+"");
                                context.startActivity(intent);
                                return true;
                            case R.id.idCancel:
                               parturient.cancelCountDownTimer=true;
                              return true;
                            case R.id.idRetornar:
                                parturient.setStartCountDownTimer();
                                return true;
                            default:
                                return false;
                        }
                    }
                });
                popup.show();
            }
        });
        // atualiza os minutos
        handlerMinutos = new Handler();
        Timer timerMinutos = new Timer();


            taskMinutos = new TimerTask() {
                @Override
                public void run() {

                    handlerMinutos.post(new Runnable() {
                        @RequiresApi(api = Build.VERSION_CODES.O)
                        public void run() {

                            try {
                                    String tempoRestante = originalListParturientes.get(position).getTempoRest();
                                    if (tempoRestante.equals("Alerta Disparado")) {
                                        holder.horaAlerta.setText("Alerta Disparado");
                                        timerMinutos.cancel();
                                    } else {
                                        // System.out.println("========= : " +tempoRestante);
                                        holder.horaAlerta.setText(tempoRestante);
                                    }


                            } catch (Exception e) {
                                // error, do something
                            }
                        }
                    });
                }
            };

            timerMinutos.schedule(taskMinutos, 0, 1000);  // interval of one minute


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
                    if((parturient.getName()+" "+parturient.getSurname()).toLowerCase().contains(constraint.toString().toLowerCase())){
                        list.add(parturient);
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
        public final TextView txtNameParturient;
        public final TextView textCircle;


        public View buttonViewOption;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            horaEntrada=(TextView)itemView.findViewById(R.id.idHoraEntrada);
            horaAlerta=(TextView) itemView.findViewById(R.id.idTxtAlert);
            textCircle=(TextView)itemView.findViewById(R.id.id_ImagemSettings);
            cardView = (CardView) itemView.findViewById(R.id.card_view);
            txtNameParturient = (TextView) itemView.findViewById(R.id.idNomeMedico);
            buttonViewOption=(TextView) itemView.findViewById(R.id.textViewOptionsParturiente);
            //txtDetails = (TextView) itemView.findViewById(R.id.txtDetails);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ViewDadosPessoaisActivity.class);
                    intent.putExtra("idParturiente", originalListParturientes.get(currentPosition).getIdAuxParturiente()+"");
                    context.startActivity(intent);
                }
            });
        }

    }

    private String format(Date date){
        DateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");
        return dateFormat.format(date);
    }

}
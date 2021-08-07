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
    private Handler handler;
    private TimerTask task;
    public static int contTimer=60;
    private TimerTask taskMinutos;
    private Handler handlerMinutos;
    private int subSegundos;
    private int subMinutos;


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
        holder.textCircle.setText((parturient.getName().charAt(0)+"").toUpperCase());
        holder.horaEntrada.setText("Entrada : "+format(parturient.getHoraEntrada())+" pm");
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
                            case R.id.idDesativar:{
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
                popup.show();
            }
        });
        // atualiza os minutos
        handlerMinutos = new Handler();
        Timer timerMinutos = new Timer();

        if(!originalListParturientes.get(position).isDisparo()) {

            taskMinutos = new TimerTask() {
                @Override
                public void run() {
                    handlerMinutos.post(new Runnable() {
                        @RequiresApi(api = Build.VERSION_CODES.O)
                        public void run() {
                                 subSegundos=retornaSegundos(position);
                                 subMinutos=retornaMinutos(position);
                            System.out.println("segundo "+subSegundos);
                            try {
                                if(subMinutos>0) {
                                    System.out.println("minutos "+subMinutos);
                                    holder.horaAlerta.setText("Tempo Restante : " + subMinutos + " minuto");
                                } else {
                                    if (subSegundos<=0) {
                                        System.out.println("menor");
                                        holder.horaAlerta.setText("Alerta Disparado");
                                        originalListParturientes.get(position).setDisparo(true);
                                        timerMinutos.cancel();
                                        taskMinutos.cancel();
                                    }else {
                                        holder.horaAlerta.setText("Tempo Restante : " + subSegundos + " segundos");
                                    }

                                }

                            } catch (Exception e) {
                                // error, do something
                            }
                        }
                    });
                }
            };

            timerMinutos.schedule(taskMinutos, 0, 950);  // interval of one minute
        }
    }


    public int retornaMinutos(int position){ // retorna a diferenca entre o tempo de entrada e o tempo atual
          Date date=originalListParturientes.get(position).getHoraAlerta();
          int minutosAlerta = Integer.parseInt(formatMinuto(date));
          int minutosHoraAtual = Integer.parseInt(formatMinuto(new Date()));
          return minutosAlerta - minutosHoraAtual;
    }

    public int retornaSegundos(int position){ // retorna a diferenca entre o tempo de entrada e o tempo atual
        Date date=originalListParturientes.get(position).getHoraAlerta();
        int segundosAlerta = Integer.parseInt(formatSegundos(date));
        int segundosHora = Integer.parseInt(formatSegundos(new Date()));
        return segundosAlerta - segundosHora;
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
        public final TextView txtNameParturient;
        public final TextView textCircle;


        public View buttonViewOption;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            horaEntrada=(TextView)itemView.findViewById(R.id.idHoraEntrada);
            horaAlerta=(TextView) itemView.findViewById(R.id.idContactoMedico);
            textCircle=(TextView)itemView.findViewById(R.id.txt_iconName);
            cardView = (CardView) itemView.findViewById(R.id.card_view);
            txtNameParturient = (TextView) itemView.findViewById(R.id.idNomeParturienteAtendido);
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
    private String formatMinuto(Date date){
        DateFormat dateFormat = new SimpleDateFormat("mm");
        return dateFormat.format(date);
    }
    private String formatSegundos(Date date){
        DateFormat dateFormat = new SimpleDateFormat("ss");
        return dateFormat.format(date);
    }
    private String format(Date date){
        DateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");
        return dateFormat.format(date);
    }
    private String format2(Date date){
        DateFormat dateFormat = new SimpleDateFormat("hh:mm-yyyy-MM-dd ");
        return dateFormat.format(date);
    }

}

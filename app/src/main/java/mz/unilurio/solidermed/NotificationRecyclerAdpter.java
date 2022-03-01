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

import mz.unilurio.solidermed.model.DBManager;
import mz.unilurio.solidermed.model.Notificacao;
import mz.unilurio.solidermed.model.Parturient;

public class NotificationRecyclerAdpter extends RecyclerView.Adapter<NotificationRecyclerAdpter.ViewHolder> implements Filterable {

    private final Context context;
    private  List<Notificacao>auxListNotificacao;
    private List<Notificacao> notificacaos;
    private final LayoutInflater layoutInflater;
    private TimerTask taskMinutos;
    private Handler handlerMinutos;

        public NotificationRecyclerAdpter(Context context, List<Notificacao> notificacaos) {
        this.context = context;
        this.auxListNotificacao =new ArrayList<>(notificacaos);
        layoutInflater = LayoutInflater.from(context);
        this.notificacaos = notificacaos;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_notification_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Notificacao notificacao = notificacaos.get(position);
        holder.currentPosition = position;

        holder.cardView.setCardBackgroundColor(notificacao.getColour());
        holder.txtTime.setText(notificacao.getTime());
        holder.txtNameParturient.setText(notificacao.getMessage());

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
                            if (notificacao.isInProcess()) {
                                holder.txtDetails.setText("Em Processo de parto...");
                            } else {

                                String tempoRestante = notificacao.getViewTimerTwo();
                                holder.txtDetails.setText(" ( "+tempoRestante+" )");
                                if (notificacao.getAlertaDisparada()) {
                                    holder.txtDetails.setText("Alerta Disparado");
                                    timerMinutos.cancel();
                                    taskMinutos.cancel();


                                }
                            }

                        } catch (Exception e) {
                            // error, do something
                        }
                    }
                });
            }
        };

        timerMinutos.schedule(taskMinutos, 0, 1000);  // interval of one minute

//        if(isTrasferido(Integer.parseInt(notificacao.getId()))){
//            holder.idAlerteEmergence.setText("");
//        }else {
//            holder.idAlerteEmergence.setText("");
//        }



        holder.buttonViewOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                //creating a popup menu
                Context mCtx = null;
                PopupMenu popup = new PopupMenu(context, holder.buttonViewOption);
                //inflating menu from xml resource
                popup.inflate(R.menu.options_menu);
                //adding click listener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {

                            case R.id.atendimento:{
                                Intent intent = new Intent(context, ViewAtendimentoActivity.class);
                                intent.putExtra("idParturiente", notificacaos.get(position).getIdAuxParturiente());
                                context.startActivity(intent);
                            }
                                return true;
                            case R.id.edit:
                                Intent intent = new Intent(context, AddParturientActivity.class);
                                intent.putExtra("idParturiente", notificacaos.get(position).getIdAuxParturiente());
                                context.startActivity(intent);
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

    private boolean isTrasferido(int parseInt) {
        for(Parturient parturient: DBManager.getInstance().getParturients()){
            if(parturient.getId()==parseInt){
                return parturient.isTransfered();
            }
        }
        return false;
    }

    @Override
    public int getItemCount() {
        return notificacaos.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter=new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            List<Notificacao>list =new ArrayList<>();

            if(constraint.toString().isEmpty()){
                list.addAll(auxListNotificacao);
            }else{
                for(Notificacao notificacao :auxListNotificacao){
                    if(notificacao.getMessage().toLowerCase().contains(constraint.toString().toLowerCase())){
                        list.add(notificacao);
                    }
                }
            }

            FilterResults filterResults=new FilterResults();
            filterResults.values=list;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            notificacaos.clear();
            notificacaos.addAll((Collection<? extends Notificacao>) results.values);
            notifyDataSetChanged();
        }
    };


    public class ViewHolder extends RecyclerView.ViewHolder {
        public int currentPosition;
        public final CardView cardView;
        public final TextView txtTime;
        public final TextView txtNameParturient;
        public final TextView txtDetails;
        public final TextView idAlerteEmergence;
        public View buttonViewOption;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
          idAlerteEmergence=(TextView)itemView.findViewById(R.id.idAlertEmergence);
            txtDetails=(TextView)itemView.findViewById(R.id.idContactMedico);
            cardView = (CardView) itemView.findViewById(R.id.card_viewNotification);
            txtTime = (TextView) itemView.findViewById(R.id.idHoraAlertaNotification);
            txtNameParturient = (TextView) itemView.findViewById(R.id.txt_NomeParturienteNotification);
            buttonViewOption=(TextView)itemView.findViewById(R.id.textViewOptionsNotification);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ViewAtendimentoActivity.class);
                         intent.putExtra("idParturiente", notificacaos.get(currentPosition).getIdAuxParturiente()+"");
                              context.startActivity(intent);

//                        ProgressDialog progressBar;
//                        progressBar=new ProgressDialog(context);
//                        progressBar.setTitle("Aguarde");
//                        progressBar.setMessage("processando...");
//                        progressBar.show();
//
//                        new Handler().postDelayed(new Thread() {
//                            @Override
//                            public void run() {
//                                progressBar.dismiss();
//                                Intent intent = new Intent(context, DadosPessoais.class);
//                                intent.putExtra("idParturiente", notifications.get(currentPosition).getId()+"");
//                                context.startActivity(intent);
//                            }
//                        },Long.parseLong("400"));
                    }




            });
        }


    }

    private String format(Date date){
        DateFormat dateFormat = new SimpleDateFormat("hh:mm - dd, MMM");
        return dateFormat.format(date);
    }

    public  String oUpperFirstCase(String string){
        String auxString=(string.charAt(0)+"").toUpperCase()+""+string.substring(1)+"";
        return  auxString;
    }


    public String getIdade(int id){

        for(Parturient parturient: DBManager.getInstance().getParturients()){
            if(parturient.getId()==id){
                return parturient.getAge()+"";
            }
        }
        return "10";
    }
}

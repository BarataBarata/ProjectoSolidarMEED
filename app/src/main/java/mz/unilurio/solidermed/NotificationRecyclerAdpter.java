package mz.unilurio.solidermed;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import mz.unilurio.solidermed.model.Notification;
import mz.unilurio.solidermed.model.Parturient;
import mz.unilurio.solidermed.ui.fragments.NotificationFragment;

public class NotificationRecyclerAdpter extends RecyclerView.Adapter<NotificationRecyclerAdpter.ViewHolder> implements Filterable {

    private final Context context;
    private  List<Notification>auxListNotificacao;
    private List<Notification> notifications;
    private final LayoutInflater layoutInflater;

        public NotificationRecyclerAdpter(Context context, List<Notification> notifications) {
        this.context = context;
        this.auxListNotificacao =new ArrayList<>(notifications);
        layoutInflater = LayoutInflater.from(context);
        this.notifications = notifications;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_note_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Notification notification =  notifications.get(position);
        holder.currentPosition = position;

        holder.cardView.setCardBackgroundColor(notification.getColour());
        holder.txtTime.setText(format(notification.getTime()));
        holder.txtNameParturient.setText(notification.getDeliveryService().getParturient().getName()+ " "+notification.getDeliveryService().getParturient().getSurname());
        holder.txtDetails.setText("idade: "+notification.getDeliveryService().getParturient().getAge()+"   |  Dilatacao: "+ notification.getDeliveryService().getMeasure().peek().getInitialDilatation());


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
                                Intent intent = new Intent(context, Atendimento.class);
                                context.startActivity(intent);
                            }
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
        return notifications.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter=new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            List<Notification>list =new ArrayList<>();

            if(constraint.toString().isEmpty()){
                list.addAll(auxListNotificacao);
            }else{
                for(Notification notification:auxListNotificacao){
                    if(notification.getDeliveryService().getParturient().getName().toLowerCase().contains(constraint.toString().toLowerCase())){
                        list.add(notification);
                        System.out.println(notification.getDeliveryService().getParturient().getName());
                    }
                }
            }

            FilterResults filterResults=new FilterResults();
            filterResults.values=list;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            notifications.clear();
            notifications.addAll((Collection<? extends Notification>) results.values);
            notifyDataSetChanged();
        }
    };


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
                        ProgressDialog progressBar;
                        progressBar=new ProgressDialog(context);
                        progressBar.setTitle("Aguarde");
                        progressBar.setMessage("processando...");
                        progressBar.show();

                        new Handler().postDelayed(new Thread() {
                            @Override
                            public void run() {
                                progressBar.dismiss();
                                Intent intent = new Intent(context, DadosPessoais.class);
                                intent.putExtra("id", notifications.get(currentPosition).getId()+"");
                                context.startActivity(intent);
                            }
                        },Long.parseLong("400"));
                    }




            });
        }


    }

    private String format(Date date){
        DateFormat dateFormat = new SimpleDateFormat("hh:mm - dd, MMM");
        return dateFormat.format(date);
    }


}

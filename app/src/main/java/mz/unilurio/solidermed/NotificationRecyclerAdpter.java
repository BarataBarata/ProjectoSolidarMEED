package mz.unilurio.solidermed;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import mz.unilurio.solidermed.model.Notification;

public class NotificationRecyclerAdpter extends RecyclerView.Adapter<NotificationRecyclerAdpter.ViewHolder>{

    private final Context context;
    private List<Notification> notifications;
    private final LayoutInflater layoutInflater;


    public NotificationRecyclerAdpter(Context context, List<Notification> notifications) {
        this.context = context;
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
                            case R.id.item1:
                                //handle menu1 click
                                return true;
                            case R.id.item2:
                                //handle menu2 click
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
            txtNameParturient = (TextView) itemView.findViewById(R.id.txtNameParturient);
            txtDetails = (TextView) itemView.findViewById(R.id.txtDetails);
            buttonViewOption=(TextView)itemView.findViewById(R.id.textViewOptions);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DadosPessoais.class);
                    intent.putExtra("id", currentPosition+"");
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

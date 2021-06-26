package mz.unilurio.solidermed;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import mz.unilurio.solidermed.model.DBManager;
import mz.unilurio.solidermed.model.Notification;
import mz.unilurio.solidermed.model.Parturient;

public class ParturienteRecyclerAdpter extends RecyclerView.Adapter<ParturienteRecyclerAdpter.ViewHolder>{
    private static int countColor=0;
    private final Context context;
    private List<Parturient> parturientes;
    private final LayoutInflater layoutInflater;

    public ParturienteRecyclerAdpter(Context context, List<Parturient> parturients) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        this.parturientes = parturients;
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
        Parturient parturient =  parturientes.get(position);

        holder.currentPosition = position;
        //holder.cardView.setCardBackgroundColor(parturient.getColour());
        holder.textCircle.setText((DBManager.getInstance().getParturients().get(position).getName().charAt(0)+"").toUpperCase());
      //  holder.textCircle.setBackgroundTintList(DBManager.getInstance().getColors().get(countColor++));
        holder.txtTime.setText(format(DBManager.getInstance().getParturients().get(position).getTime()));
        holder.txtNameParturient.setText(oUpperFirstCase(DBManager.getInstance().getParturients().get(position).getName())+ " "+oUpperFirstCase(DBManager.getInstance().getParturients().get(position).getSurname()));
        holder.textNumeroCama.setText("| Nº cama: "+DBManager.getInstance().getParturients().get(position).getNumeroCama());
        holder.textDilatacao.setText("| dilatação: "+DBManager.getInstance().getParturients().get(position).getReason());
        holder.textIdade.setText("Idade: "+DBManager.getInstance().getParturients().get(position).getAge());

        //holder.txtDetails.setText("idade: "+parturient.getDeliveryService().getParturient().getAge()+"   |  Dilatacao: "+ parturient.getDeliveryService().getMeasure().peek().getInitialDilatation()+"  |  Nº de cama: 3");

    }

    @Override
    public int getItemCount() {
        return parturientes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public int currentPosition;
        public final CardView cardView;
        public final TextView txtTime;
        public final TextView txtNameParturient;
        public final TextView textNumeroCama;
        public final TextView textIdade;
        public final TextView textDilatacao;
        public final TextView textCircle;
//        public final TextView txtDetails;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textIdade=(TextView)itemView.findViewById(R.id.txtIdade);
            textDilatacao=(TextView)itemView.findViewById(R.id.txtDilatacao);
            textCircle=(TextView)itemView.findViewById(R.id.textCircle);
            textNumeroCama=(TextView)itemView.findViewById(R.id.id_numeroCamaParturiente);
            cardView = (CardView) itemView.findViewById(R.id.card_view);
            txtTime = (TextView) itemView.findViewById(R.id.txtTimeParturiente);
            txtNameParturient = (TextView) itemView.findViewById(R.id.txtNameParturientParturiente);
            //txtDetails = (TextView) itemView.findViewById(R.id.txtDetails);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Intent intent = new Intent(context, NoteActivity.class);
//                    intent.putExtra(NoteActivity.NOTE_POSITION, currentPosition);
//                    context.startActivity(intent);
                }
            });
        }


    }

    private String format(Date date){
        DateFormat dateFormat = new SimpleDateFormat("hh:mm - dd, MMM");
        return dateFormat.format(date);
    }
}

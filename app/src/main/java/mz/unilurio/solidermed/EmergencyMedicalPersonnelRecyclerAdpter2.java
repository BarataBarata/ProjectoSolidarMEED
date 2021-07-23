package mz.unilurio.solidermed;

import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import mz.unilurio.solidermed.model.EditContctClass;
import mz.unilurio.solidermed.model.EmergencyMedicalPersonnel;

public class EmergencyMedicalPersonnelRecyclerAdpter2 extends RecyclerView.Adapter<EmergencyMedicalPersonnelRecyclerAdpter2.ViewHolder> implements Filterable {
    EditContctClass   context;
    private List<EmergencyMedicalPersonnel> auxListContact;
    private List<EmergencyMedicalPersonnel> originalListContact;
    private final LayoutInflater layoutInflater;


    public EmergencyMedicalPersonnelRecyclerAdpter2(EditContctClass context, List<EmergencyMedicalPersonnel> originalListContact) {
        this.context = context;
        this.auxListContact =new ArrayList<>(originalListContact);
        layoutInflater = LayoutInflater.from(context.getContext());
        this.originalListContact = originalListContact;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_contactos_list, parent, false);
        return new ViewHolder(view);
    }


    public void delete(int position){

        AlertDialog.Builder dialog = new AlertDialog.Builder(context.getContext());
        dialog.setTitle(" Deletar ?");
        dialog.setCancelable(false);
        dialog.setIcon(R.drawable.delete);

        dialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
               // Toast.makeText(context.getApplicationContext(), " Contacto Eliminado", Toast.LENGTH_LONG).show();
               // DBManager.getInstance().getEmergencyMedicalPersonnels().clear();
                //startActivit();
            }


        });
        dialog.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(context.getContext().getApplicationContext(), " Operacao Cancelada", Toast.LENGTH_LONG).show();

            }
        });

        dialog.create();
        dialog.show();
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        EmergencyMedicalPersonnel contact =  originalListContact.get(position);
        holder.currentPosition = position;
        holder.textContact.setText(contact.getContact());
        holder.txtNameMedico.setText(contact.getName()+" "+contact.getSurname());
        holder.imageDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete(position);
            }
        });

        holder.imageEditarContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditContctClass editContctClass=new EditContctClass();
                editContctClass.getContact(position);
                editContctClass.show((FragmentManager)null,"Editar");
            }
        });
    }


    @Override
    public int getItemCount() {
        return originalListContact.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter=new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            List<EmergencyMedicalPersonnel>list =new ArrayList<>();

            if(constraint.toString().isEmpty()){
                list.addAll(auxListContact);
            }else{
                for(EmergencyMedicalPersonnel contact: auxListContact){
                    if(contact.getName().toLowerCase().contains(constraint.toString().toLowerCase())){
                        list.add(contact);
                    }
                }
            }

            FilterResults filterResults=new FilterResults();
            filterResults.values=list;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            originalListContact.clear();
            originalListContact.addAll((Collection<? extends EmergencyMedicalPersonnel>) results.values);
            notifyDataSetChanged();
        }
    };


    public class ViewHolder extends RecyclerView.ViewHolder {
        public int currentPosition;
        public final CardView cardView;
        public final TextView txtTime;
        public final TextView txtNameMedico;
        public final TextView textContact;
        public final TextView textApelido;
        public final TextView txtDetails;
        public View buttonViewOption;
        public  final ImageView imageEditarContact;
        public  final ImageView imageDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textApelido=(TextView) itemView.findViewById(R.id.idApelidoEditContact);
            imageDelete=(ImageView)itemView.findViewById(R.id.idDelete);
            imageEditarContact=(ImageView)itemView.findViewById(R.id.idEdit);
            textContact=(TextView)itemView.findViewById(R.id.idContact);
            cardView = (CardView) itemView.findViewById(R.id.card_view);
            txtTime = (TextView) itemView.findViewById(R.id.txtTime);
            txtNameMedico = (TextView) itemView.findViewById(R.id.idSetting);
            txtDetails = (TextView) itemView.findViewById(R.id.txtDetails);
            buttonViewOption=(TextView)itemView.findViewById(R.id.textViewOptions);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }

            });


        }


    }


    private String format(Date date){
        DateFormat dateFormat = new SimpleDateFormat("hh:mm - dd, MMM");
        return dateFormat.format(date);
    }
}

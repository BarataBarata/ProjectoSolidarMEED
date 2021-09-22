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
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import mz.unilurio.solidermed.model.AddDoctorClass;
import mz.unilurio.solidermed.model.DBManager;
import mz.unilurio.solidermed.model.DBService;
import mz.unilurio.solidermed.model.EditDoctorClass;
import mz.unilurio.solidermed.model.UserDoctor;

public class DoctorRecyclerAdpter extends RecyclerView.Adapter<DoctorRecyclerAdpter.ViewHolder> implements Filterable {
    ContactActivity  context;
    private List<UserDoctor> auxListContact;
    private List<UserDoctor> originalListContact;
    private final LayoutInflater layoutInflater;
    private DBService dbService;


    public DoctorRecyclerAdpter(ContactActivity context, List<UserDoctor> originalListContact) {
        this.context = context;
        this.auxListContact =new ArrayList<>(originalListContact);
        layoutInflater = LayoutInflater.from(context);
        this.originalListContact = originalListContact;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_docter_list, parent, false);
        return new ViewHolder(view);
    }


    public void delete(UserDoctor userDoctor){
        dbService=new DBService(context);
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setTitle(" Deletar ?");
        dialog.setCancelable(false);
        dialog.setIcon(R.drawable.delete);

        dialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
               // Toast.makeText(context.getApplicationContext(), " Contacto Eliminado", Toast.LENGTH_LONG).show();
               DBManager.getInstance().getEmergencyMedicalPersonnels().remove(userDoctor);
                dbService.deleteDoctor(userDoctor.getIdUser());
                AddDoctorClass e=new AddDoctorClass();
                e.isRemove=true;

            }


        });
        dialog.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(context.getApplicationContext(), " Operacao Cancelada", Toast.LENGTH_LONG).show();

            }
        });

        dialog.create();
        dialog.show();
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        UserDoctor userDoctor =  originalListContact.get(position);
        holder.currentPosition = position;
        holder.textContact.setText(userDoctor.getContacto());
        holder.txtNameMedico.setText(userDoctor.getFullName());
        holder.imageDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete(userDoctor);
            }
        });

        holder.imageEditarContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditDoctorClass editDoctorClass =new EditDoctorClass();
                editDoctorClass.setUserDoctor(userDoctor);
                editDoctorClass.show(context.getSupportFragmentManager(),"Editar");
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

            List<UserDoctor>list =new ArrayList<>();

            if(constraint.toString().isEmpty()){
                list.addAll(auxListContact);
            }else{
                for(UserDoctor contact: auxListContact){
                    if(contact.getFullName().toLowerCase().contains(constraint.toString().toLowerCase())){
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
            originalListContact.addAll((Collection<? extends UserDoctor>) results.values);
            notifyDataSetChanged();
        }
    };


    public class ViewHolder extends RecyclerView.ViewHolder {
        public int currentPosition;
        public final CardView cardView;
        public final TextView txtTime;
        public final TextView txtNameMedico;
        public final TextView textContact;
        public View buttonViewOption;
        public  final ImageView imageEditarContact;
        public  final ImageView imageDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageDelete=(ImageView)itemView.findViewById(R.id.idDelete);
            imageEditarContact=(ImageView)itemView.findViewById(R.id.idEditContacto);
            textContact=(TextView)itemView.findViewById(R.id.idContactMedico);
            cardView = (CardView) itemView.findViewById(R.id.card_view);
            txtTime = (TextView) itemView.findViewById(R.id.txtTime);
            txtNameMedico = (TextView) itemView.findViewById(R.id.idNomeMedico);
            buttonViewOption=(TextView)itemView.findViewById(R.id.textViewOptionsNotification);

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

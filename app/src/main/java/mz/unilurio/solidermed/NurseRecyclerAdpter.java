package mz.unilurio.solidermed;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Handler;
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
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import mz.unilurio.solidermed.model.AddNursesClass;
import mz.unilurio.solidermed.model.DBManager;
import mz.unilurio.solidermed.model.DBService;
import mz.unilurio.solidermed.model.EditNurseClass;
import mz.unilurio.solidermed.model.UserNurse;

public class NurseRecyclerAdpter extends RecyclerView.Adapter<NurseRecyclerAdpter.ViewHolder> implements Filterable {
    NurseActivity context;
    private List<UserNurse> auxListNurse;
    private List<UserNurse> originalListNurse;
    private final LayoutInflater layoutInflater;
    private DBService dbService;


    public NurseRecyclerAdpter(NurseActivity context, List<UserNurse> nurses) {
        this.context = context;
        this.auxListNurse=new ArrayList<>(nurses);
        layoutInflater = LayoutInflater.from(context);
        this.originalListNurse = nurses;
    }

    public void delete(UserNurse userNurse){
        dbService=new DBService(context);
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setTitle(" Deletar ?");
        dialog.setCancelable(false);
        dialog.setIcon(R.drawable.delete);

        dialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dbService.deleteNurse(userNurse.getIdNurse());
                AddNursesClass e=new AddNursesClass();
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


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_nurse_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        UserNurse userNurse =  originalListNurse.get(position);
        holder.currentPosition = position;
        holder.txtNameParturient.setText(userNurse.getFullName());
        holder.textVcontacto.setText(userNurse.getContacto());
        holder.imageEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditNurseClass editNurseClass=new EditNurseClass();
                editNurseClass.setIdNurse(userNurse);
                editNurseClass.show(context.getSupportFragmentManager(),"Editar");
            }
        });

        holder.imageDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete(userNurse);
            }
        });
    }
    @Override
    public int getItemCount() {
        return originalListNurse.size();
    }


    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter=new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<UserNurse>list =new ArrayList<>();

            if(constraint.toString().isEmpty()){
                list.addAll(auxListNurse);
            }else{
                for(UserNurse hospitais: auxListNurse){
                    if(hospitais.getFullName().toLowerCase().contains(constraint.toString().toLowerCase())){
                        list.add(hospitais);
                    }
                }
            }

            FilterResults filterResults=new FilterResults();
            filterResults.values=list;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            originalListNurse.clear();
            originalListNurse.addAll((Collection<? extends UserNurse>) results.values);
            notifyDataSetChanged();
        }
    };


    public class ViewHolder extends RecyclerView.ViewHolder {
        public int currentPosition;
        //public final CardView cardView;
        //public final TextView txtTime;
        public final TextView textVcontacto;
        public final TextView txtNameParturient;
        public final ImageView imageEditar;
        public final ImageView imageDelete;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageEditar=(ImageView)itemView.findViewById(R.id.idEditNurse);
            imageDelete=(ImageView)itemView.findViewById(R.id.idDeleteNurse);
            //buttonViewOption=(TextView)itemView.findViewById(R.id.textViewOptionsParturiente);
            textVcontacto=(TextView)itemView.findViewById(R.id.idContactMedico);
            //cardView = (CardView) itemView.findViewById(R.id.card_view);
            //txtTime = (TextView) itemView.findViewById(R.id.txtTime);
            txtNameParturient = (TextView) itemView.findViewById(R.id.idEnfermeiro);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                     alerte();
                    }
            });
        }


    }

    private String format(Date date){
        DateFormat dateFormat = new SimpleDateFormat("hh:mm - dd, MMM");
        return dateFormat.format(date);
    }
    public  void alerte(){

                ProgressDialog progressBar;
                progressBar=new ProgressDialog(context);
                progressBar.setTitle("Aguarde");
                progressBar.setMessage("processando...");
                progressBar.show();

                new Handler().postDelayed(new Thread() {
                    @Override
                    public void run() {
                        progressBar.dismiss();
//                        Intent intent = new Intent(context, MainActivity.class);
//                        context.startActivity(intent);
                    }
                },Long.parseLong("400"));
                    //Toast.makeText(getApplicationContext(), " Selecionado com sucesso", Toast.LENGTH_LONG).show();

    }

}

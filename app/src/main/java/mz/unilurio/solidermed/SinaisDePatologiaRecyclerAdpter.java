package mz.unilurio.solidermed;

import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
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

import mz.unilurio.solidermed.model.AddPatologiaClass;
import mz.unilurio.solidermed.model.DBService;
import mz.unilurio.solidermed.model.EditPatologiaClass;
import mz.unilurio.solidermed.model.Patologia;

public class SinaisDePatologiaRecyclerAdpter extends RecyclerView.Adapter<SinaisDePatologiaRecyclerAdpter.ViewHolder> implements Filterable {

    private final ActivityPatologia context;
    private List<Patologia> patologiaList;
    private List<Patologia> originalListPatologia;
    private final LayoutInflater layoutInflater;
    private DBService dbService;


    public SinaisDePatologiaRecyclerAdpter(ActivityPatologia context, List<Patologia> patologias) {
        this.context = context;
        this.patologiaList =new ArrayList<>(patologias);
        layoutInflater = LayoutInflater.from(context);
        this.originalListPatologia = patologias;
    }

    public void delete(Patologia patologia){
        dbService=new DBService(context);
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setTitle(" Deletar ?");
        dialog.setCancelable(false);
        dialog.setIcon(R.drawable.delete);

        dialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dbService.deletePatologia(patologia.getId());
                AddPatologiaClass e=new AddPatologiaClass();
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
        View view = layoutInflater.inflate(R.layout.item_patologias_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Patologia patologia =  originalListPatologia.get(position);
        holder.currentPosition = position;
        holder.textView.setText(patologia.getPatologia());
        holder.imageEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 //holder.checkBox.setChecked(true);
                EditPatologiaClass patologiaClass=new EditPatologiaClass();
                patologiaClass.setPatologia(originalListPatologia.get(position));
                patologiaClass.show(context.getSupportFragmentManager(),"Editar");
            }
        });
        holder.imageDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 delete(originalListPatologia.get(position));
            }
        });
    }
    @Override
    public int getItemCount() {
        return originalListPatologia.size();
    }


    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter=new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Patologia>list =new ArrayList<>();

            if(constraint.toString().isEmpty()){
                list.addAll(patologiaList);
            }else{
                for(Patologia patologia: patologiaList){
                    if(patologia.getPatologia().toLowerCase().contains(constraint.toString().toLowerCase())){
                        list.add(patologia);
                    }
                }
            }

            FilterResults filterResults=new FilterResults();
            filterResults.values=list;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            originalListPatologia.clear();
            originalListPatologia.addAll((Collection<? extends Patologia>) results.values);
            notifyDataSetChanged();
        }
    };


    public class ViewHolder extends RecyclerView.ViewHolder {
        public int currentPosition;
        //public final CardView cardView;
        //public final TextView txtTime;
        //public final TextView textVcontacto;
        public final TextView textView;
        public final ImageView imageEditar;
        public final ImageView imageDelete;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageEditar=(ImageView)itemView.findViewById(R.id.idEditNurse);
            imageDelete=(ImageView)itemView.findViewById(R.id.idDeleteNurse);
            textView = (TextView) itemView.findViewById(R.id.ch1);


            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    Toast.makeText(context, ""+ textView.getText().toString(), Toast.LENGTH_SHORT).show();
                }
            });

        }


    }

    private String format(Date date){
        DateFormat dateFormat = new SimpleDateFormat("hh:mm - dd, MMM");
        return dateFormat.format(date);
    }
    public  void setId( int id){

//                ProgressDialog progressBar;
//                progressBar=new ProgressDialog(context);
//                progressBar.setTitle("Aguarde");
//                progressBar.setMessage("processando...");
//                progressBar.show();
//
//                new Handler().postDelayed(new Thread() {
//                    @Override
//                    public void run() {
//                        progressBar.dismiss();
////                        Intent intent = new Intent(context, ActivitViewPasswordUser.class);
////                        intent.putExtra("userNurse",originalListNurse.get(id).getIdNurse()+"");
////                        context.startActivity(intent);
//                    }
//                },Long.parseLong("400"));
//                    //Toast.makeText(getApplicationContext(), " Selecionado com sucesso", Toast.LENGTH_LONG).show();
//
    }

}

package mz.unilurio.solidermed;

import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
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
import mz.unilurio.solidermed.model.DBManager;
import mz.unilurio.solidermed.model.DBService;
import mz.unilurio.solidermed.model.EditPatologiaClass;
import mz.unilurio.solidermed.model.Patologia;
import mz.unilurio.solidermed.model.SelectSinal;

public class SelectedPatologiaRecyclerAdpter extends RecyclerView.Adapter<SelectedPatologiaRecyclerAdpter.ViewHolder> implements Filterable {

    private final Activity_SelectPatologia context;
    private List<Patologia> patologiaList;
    private List<Patologia> originalListPatologia;
    private final LayoutInflater layoutInflater;
    private DBService dbService;


    public SelectedPatologiaRecyclerAdpter(Activity_SelectPatologia context, List<Patologia> patologias) {
        this.context = context;
        this.patologiaList =new ArrayList<>(patologias);
        layoutInflater = LayoutInflater.from(context);
        this.originalListPatologia = patologias;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_patologias_list_selected, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Patologia patologia =  originalListPatologia.get(position);
        holder.currentPosition = position;
        holder.checkBox.setText(patologia.getPatologia());

        for(Patologia patologia1: DBManager.getInstance().getSinaisPatologiaList()){
            if(patologia1.getId().equalsIgnoreCase(originalListPatologia.get(position).getId())){
                holder.checkBox.setChecked(patologia1.isSelected());
            }
        }

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
        public final CheckBox checkBox;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox = (CheckBox) itemView.findViewById(R.id.ch1);
            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Patologia patologia=originalListPatologia.get(currentPosition);
                    if(checkBox.isChecked()){
                        patologia.setSelected(true);
                        DBManager.getInstance().addPatologia(originalListPatologia.get(currentPosition));
                        Toast.makeText(context, ""+checkBox.getText().toString() +" Selecionado", Toast.LENGTH_SHORT).show();
                    }else {
                        System.out.println(" Antes de ser eliminado :"+DBManager.getInstance().getSinaisPatologiaList());
                        for(int i=0;i<DBManager.getInstance().getSinaisPatologiaList().size();i++){
                            if(DBManager.getInstance().getSinaisPatologiaList().get(i).getId().equalsIgnoreCase(originalListPatologia.get(currentPosition).getId())){
                                DBManager.getInstance().getSinaisPatologiaList().remove(DBManager.getInstance().getSinaisPatologiaList().get(i));
                            }
                        }
                        System.out.println(" Depois de ser eliminado :"+DBManager.getInstance().getSinaisPatologiaList());
                        Toast.makeText(context, ""+checkBox.getText().toString() +" Cancelado", Toast.LENGTH_SHORT).show();
                    }

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

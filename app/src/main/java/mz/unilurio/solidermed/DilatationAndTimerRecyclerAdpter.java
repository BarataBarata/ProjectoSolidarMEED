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
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import mz.unilurio.solidermed.model.AddDilatation;
import mz.unilurio.solidermed.model.DBManager;
import mz.unilurio.solidermed.model.DBService;
import mz.unilurio.solidermed.model.DilatationAndTimer;
import mz.unilurio.solidermed.model.EditClassDilatation;

public class DilatationAndTimerRecyclerAdpter extends RecyclerView.Adapter<DilatationAndTimerRecyclerAdpter.ViewHolder> implements Filterable {
    ActivityDilatetionAndHours context;
    private List<DilatationAndTimer> auxListDilatation;
    private List<DilatationAndTimer> originalListDilatation;
    private final LayoutInflater layoutInflater;
    private DBService dbService;


    public DilatationAndTimerRecyclerAdpter(ActivityDilatetionAndHours context, List<DilatationAndTimer> dilatationAndTimers) {
        this.context = context;
        this.auxListDilatation =new ArrayList<>(dilatationAndTimers);
        layoutInflater = LayoutInflater.from(context);
        this.originalListDilatation = dilatationAndTimers;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = layoutInflater.inflate(R.layout.item_dilatation_and_hours_list, parent, false);
        return new ViewHolder(view);
    }


    public void delete(int dilatation){

        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setTitle(" Deletar ?");
        dialog.setCancelable(false);
        dialog.setIcon(R.drawable.delete);

        dialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dbService=new DBService(context);
                dbService.deleteDilatation(dbService.getIdDilatation(dilatation+""));
                 AddDilatation e=new AddDilatation();
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
        DilatationAndTimer dilatationAndTimer =  originalListDilatation.get(position);
        holder.currentPosition = position;
        holder.textTimer.setText("Tempo definido :  "+dilatationAndTimer.getTimerDilatationHours()+ " : "+dilatationAndTimer.getTimerDilatationMinutes());
        holder.txtDilatation.setText("Para "+dilatationAndTimer.getNumberDilatation() +" de Dilatação ");
        holder.imageDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete(dilatationAndTimer.getNumberDilatation());
            }
        });

        holder.imageEditDilatation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditClassDilatation editContctClass=new EditClassDilatation();
                editContctClass.setDilatation(dilatationAndTimer);
                editContctClass.show(context.getSupportFragmentManager(),"Editar");
            }
        });
    }


    @Override
    public int getItemCount() {
        return originalListDilatation.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter=new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            List<DilatationAndTimer>list =new ArrayList<>();

            if(constraint.toString().isEmpty()){
                list.addAll(auxListDilatation);
            }else{
                for(DilatationAndTimer dilatation: auxListDilatation){
                    if((""+dilatation.getNumberDilatation()).toLowerCase().contains(constraint.toString().toLowerCase())){
                        list.add(dilatation);
                    }
                }
            }

            FilterResults filterResults=new FilterResults();
            filterResults.values=list;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            originalListDilatation.clear();
            originalListDilatation.addAll((Collection<? extends DilatationAndTimer>) results.values);
            notifyDataSetChanged();
        }
    };


    public class ViewHolder extends RecyclerView.ViewHolder {
        public int currentPosition;
        public final TextView txtDilatation;
        public final TextView textTimer;
        public  final ImageView imageEditDilatation;
        public  final ImageView imageDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageDelete=(ImageView)itemView.findViewById(R.id.idDeleteInDilatationInList);
            imageEditDilatation =(ImageView)itemView.findViewById(R.id.idEditDilatationInList);
            textTimer =(TextView)itemView.findViewById(R.id.idTimer);
            txtDilatation = (TextView) itemView.findViewById(R.id.idDilatacao);
        }


    }


    private String format(Date date){
        DateFormat dateFormat = new SimpleDateFormat("hh:mm - dd, MMM");
        return dateFormat.format(date);
    }
}

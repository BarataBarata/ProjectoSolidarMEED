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
import mz.unilurio.solidermed.model.DBService;
import mz.unilurio.solidermed.model.EditIadeGestacional;
import mz.unilurio.solidermed.model.IdadeGestacional;

public class IdadeGestacionalRecyclerAdpter extends RecyclerView.Adapter<IdadeGestacionalRecyclerAdpter.ViewHolder> implements Filterable {
    ActivityDefinitionIdadeGestacional context;
    private List<IdadeGestacional> auxListDilatation;
    private List<IdadeGestacional> idadeGestacionalList;
    private final LayoutInflater layoutInflater;
    private IdadeGestacional auxIdadeGestacional;
    private DBService dbService;


    public IdadeGestacionalRecyclerAdpter(ActivityDefinitionIdadeGestacional context, List<IdadeGestacional> idadeGestacionals) {
        this.context = context;
        this.auxListDilatation = new ArrayList<>(idadeGestacionals);
        layoutInflater = LayoutInflater.from(context);
        this.idadeGestacionalList = idadeGestacionals;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_idade_gestacional_list, parent, false);
        return new ViewHolder(view);
    }


    public void delete(int id, String idadegestacional) {

        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setTitle(" Deletar " + idadegestacional + " ?");
        dialog.setCancelable(false);
        dialog.setIcon(R.drawable.delete);

        dialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dbService = new DBService(context);
                dbService.deleteIdadeGestacional(id);
                EditIadeGestacional e = new EditIadeGestacional();
                e.isAdd = true;
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
        IdadeGestacional idadeGestacional = idadeGestacionalList.get(position);
        auxIdadeGestacional = idadeGestacional;
        holder.currentPosition = position;
        holder.txtDilatation.setText("" + idadeGestacional.getIdadeGestacional() + "");
        holder.imageDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete(Integer.parseInt(idadeGestacional.getId()), idadeGestacional.getIdadeGestacional());
            }
        });

        holder.imageEditIdadeGestacional.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditIadeGestacional editIadeGestacional = new EditIadeGestacional();
                editIadeGestacional.getIdadeGestacional(idadeGestacional);
                editIadeGestacional.show(context.getSupportFragmentManager(), "Editar");

//                EditClassDilatation editContctClass=new EditClassDilatation();
//                editContctClass.setDilatation(idadeGestacional);
//                editContctClass.show(context.getSupportFragmentManager(),"Editar");
            }
        });
    }


    @Override
    public int getItemCount() {
        return idadeGestacionalList.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            List<IdadeGestacional> list = new ArrayList<>();

            if (constraint.toString().isEmpty()) {
                list.addAll(auxListDilatation);
            } else {
                for (IdadeGestacional idadeGestacional : auxListDilatation) {
                    if (("" + idadeGestacional.getIdadeGestacional()).toLowerCase().contains(constraint.toString().toLowerCase())) {
                        list.add(idadeGestacional);
                    }
                }
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = list;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            idadeGestacionalList.clear();
            idadeGestacionalList.addAll((Collection<? extends IdadeGestacional>) results.values);
            notifyDataSetChanged();
        }
    };


    public class ViewHolder extends RecyclerView.ViewHolder {
        public int currentPosition;
        public final TextView txtDilatation;
        //public final TextView textTimer;
        public final ImageView imageEditIdadeGestacional;
        public final ImageView imageDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageDelete = (ImageView) itemView.findViewById(R.id.idDeleteInIdadeGestacionalnList);
            imageEditIdadeGestacional = (ImageView) itemView.findViewById(R.id.idEditIdadeGestacional);
            //textTimer =(TextView)itemView.findViewById(R.id.idTimer);
            txtDilatation = (TextView) itemView.findViewById(R.id.idIdadeGestacional);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EditIadeGestacional editIadeGestacional = new EditIadeGestacional();
                    editIadeGestacional.getIdadeGestacional(auxIdadeGestacional);
                    editIadeGestacional.show(context.getSupportFragmentManager(), "Editar");

                }
            });


        }


        private String format(Date date) {
            DateFormat dateFormat = new SimpleDateFormat("hh:mm - dd, MMM");
            return dateFormat.format(date);
        }
    }
}

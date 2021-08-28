package mz.unilurio.solidermed;

import android.app.ProgressDialog;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import mz.unilurio.solidermed.model.EditContctClassEnfermeira;
import mz.unilurio.solidermed.model.UserNurse;

public class NurseRecyclerAdpter extends RecyclerView.Adapter<NurseRecyclerAdpter.ViewHolder> implements Filterable {
    NurseActivity context;
    private List<UserNurse> auxListNurse;
    private List<UserNurse> originalListNurse;
    private final LayoutInflater layoutInflater;


    public NurseRecyclerAdpter(NurseActivity context, List<UserNurse> nurses) {
        this.context = context;
        this.auxListNurse=new ArrayList<>(nurses);
        layoutInflater = LayoutInflater.from(context);
        this.originalListNurse = nurses;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_infermeira_list, parent, false);
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
                EditContctClassEnfermeira editContctClass=new EditContctClassEnfermeira();
                editContctClass.getContact(Integer.parseInt(userNurse.getIdNurse()));
                editContctClass.show(context.getSupportFragmentManager(),"Editar");

            }
        });




        holder.buttonViewOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //creating a popup menu

                PopupMenu popup = new PopupMenu(context, holder.buttonViewOption);
                //inflating menu from xml resource
                popup.inflate(R.menu.options_menu_enfermeira);
                //adding click listener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {

                            case R.id.idDesativar:{
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
        public final CardView cardView;
        public final TextView txtTime;
        public final TextView textVcontacto;
        public final TextView txtNameParturient;
        public final TextView buttonViewOption;
        public final ImageView imageEditar;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageEditar=(ImageView)itemView.findViewById(R.id.idEditEnfermeira);
            buttonViewOption=(TextView)itemView.findViewById(R.id.textViewOptionsParturiente);
            textVcontacto=(TextView)itemView.findViewById(R.id.idContactMedico);
            cardView = (CardView) itemView.findViewById(R.id.card_view);
            txtTime = (TextView) itemView.findViewById(R.id.txtTime);
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

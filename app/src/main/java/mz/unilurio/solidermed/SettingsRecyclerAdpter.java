package mz.unilurio.solidermed;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import mz.unilurio.solidermed.model.Settings;

public class SettingsRecyclerAdpter extends RecyclerView.Adapter<SettingsRecyclerAdpter.ViewHolder> implements Filterable {
    SettingActivity  context;
    private List<Settings> auxListSetting;
    private List<Settings> originalListSetting;
    private final LayoutInflater layoutInflater;
    private Settings settings;


    public SettingsRecyclerAdpter(SettingActivity context, List<Settings> settings) {
        this.context = context;
        this.auxListSetting =new ArrayList<>(settings);
        layoutInflater = LayoutInflater.from(context);
        this.originalListSetting = settings;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_setting_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        settings = originalListSetting.get(position);
        holder.currentPosition = position;
        holder.txtNameSetting.setText(settings.getSetting());
        holder.imageSetting.setImageResource(settings.getImagem());
    }
    @Override
    public int getItemCount() {
        return originalListSetting.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter=new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            List<Settings>list =new ArrayList<>();

            if(constraint.toString().isEmpty()){
                list.addAll(auxListSetting);
            }else{
                for(Settings settings: auxListSetting){
                    if(settings.getSetting().toLowerCase().contains(constraint.toString().toLowerCase())){
                        list.add(settings);
                    }
                }
            }

            FilterResults filterResults=new FilterResults();
            filterResults.values=list;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            originalListSetting.clear();
            originalListSetting.addAll((Collection<? extends Settings>) results.values);
            notifyDataSetChanged();
        }
    };

    public class ViewHolder extends RecyclerView.ViewHolder {
        public int currentPosition;
        public final TextView txtNameSetting;
        public final ImageView imageSetting;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNameSetting = (TextView) itemView.findViewById(R.id.idSetting);
            imageSetting=(ImageView)itemView.findViewById(R.id.idImagenConfiguration);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                       process(originalListSetting.get(currentPosition).getIdSetting());
                    }

            });
        }
    }

    private String format(Date date){
        DateFormat dateFormat = new SimpleDateFormat("hh:mm - dd, MMM");
        return dateFormat.format(date);
    }
    public void process(int idSetting){

        ProgressDialog progressBar;
        progressBar=new ProgressDialog(context);
        progressBar.setTitle("Aguarde");
        progressBar.setMessage("processando...");
        progressBar.show();

        new Handler().postDelayed(new Thread() {
            @Override
            public void run() {
                progressBar.dismiss();
                System.out.println(idSetting);
                switch (idSetting){

                    case 1: {
                        context.startActivity(new Intent(context,ContactActivity.class)); break;}
                }



                //Intent intent = new Intent(context, MainActivity.class);
                //intent.putExtra("nomeHospital", originalListSetting.get(currentPosition).getNomeHospital()+"");
                //context.startActivity(intent);
            }
        },Long.parseLong("400"));

    }
}

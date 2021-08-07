package mz.unilurio.solidermed;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import mz.unilurio.solidermed.model.SeacherUser;

public class SeacherUserRecyclerAdpter extends RecyclerView.Adapter<SeacherUserRecyclerAdpter.ViewHolder> implements Filterable {
    ActivityViewUserSeacher context;
    private List<SeacherUser> seacherUsers;
    private List<SeacherUser> originalListSecherUser;
    private final LayoutInflater layoutInflater;


    public SeacherUserRecyclerAdpter(ActivityViewUserSeacher context, List<SeacherUser> seacherUsers) {
        this.context = context;
        this.seacherUsers = new ArrayList<>(seacherUsers);
        layoutInflater = LayoutInflater.from(context);
        this.originalListSecherUser = seacherUsers;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_seacher_user_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SeacherUser seacherUser = originalListSecherUser.get(position);
        holder.currentPosition = position;
        holder.txtNameParturient.setText(seacherUser.getNameUser());

//
//        holder.buttonViewOption.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                //creating a popup menu
//                Context mCtx = null;
//                PopupMenu popup = new PopupMenu(context, holder.buttonViewOption);
//                //inflating menu from xml resource
//                popup.inflate(R.menu.options_menu);
//                //adding click listener
//                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//                    @Override
//                    public boolean onMenuItemClick(MenuItem item) {
//                        switch (item.getItemId()) {
//
//                            case R.id.atendimento:{
//                                Intent intent = new Intent(context, Atendimento.class);
//                                context.startActivity(intent);
//                            }
//                                return true;
//                            case R.id.item3:
//                                //handle menu3 click
//                                return true;
//                            default:
//                                return false;
//                        }
//                    }
//                });
//                //displaying the popup
//                popup.show();
//
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return originalListSecherUser.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            List<SeacherUser> list = new ArrayList<>();

            if (constraint.toString().isEmpty()) {
                list.addAll(seacherUsers);
            } else {
                for (SeacherUser seacherUser : seacherUsers) {
                    if (seacherUser.getNameUser().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        list.add(seacherUser);
                    }
                }
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = list;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            originalListSecherUser.clear();
            originalListSecherUser.addAll((Collection<? extends SeacherUser>) results.values);
            notifyDataSetChanged();
        }
    };


    public class ViewHolder extends RecyclerView.ViewHolder {
        public int currentPosition;
        public final TextView txtTime;
        public final TextView txtNameParturient;

        public View buttonViewOption;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtTime = (TextView) itemView.findViewById(R.id.txtTime);
            txtNameParturient = (TextView) itemView.findViewById(R.id.txt_idHospitais);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    seacher(currentPosition);
                }

            });
        }


    }

    private String format(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("hh:mm - dd, MMM");
        return dateFormat.format(date);
    }

    public void seacher(int currentPosition) {

        ProgressDialog progressBar;
        progressBar=new ProgressDialog(context);
        progressBar.setTitle("Aguarde");
        progressBar.setMessage("processando...");
        progressBar.show();

        new Handler().postDelayed(new Thread() {
            @Override
            public void run() {
                progressBar.dismiss();
                Intent intent = new Intent(context, ActivitySendMenssage.class);
                intent.putExtra("nomeHospital", originalListSecherUser.get(currentPosition).getNameUser()+"");
                context.startActivity(intent);
            }
        },Long.parseLong("400"));

    }
}
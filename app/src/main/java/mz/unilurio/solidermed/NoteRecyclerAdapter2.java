package mz.unilurio.solidermed;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import mz.unilurio.solidermed.ui.home.HomeFragment;

public class NoteRecyclerAdapter2 extends RecyclerView.Adapter<NoteRecyclerAdapter2.ViewHolter>{
    private List<NoteInfo> mNote;
    private  HomeFragment context;
    public NoteRecyclerAdapter2(HomeFragment context, List<NoteInfo> note) {
        mNote=note;
        this.context=context;
    }


    @NonNull
    @NotNull
    @Override
    public ViewHolter onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
      // View itemView =LayoutInflater.from(parent.getContext())R.layout.item_note_list,parent,false);
        View itemView =LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note_list,parent,false);
        return new ViewHolter(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolter holder, int position) {
              NoteInfo note=mNote.get(position);
              holder.textCourses.setText(note.getCourse().getTitle().toString());
              holder.textTitle.setText(note.getTitle().toString());
              holder.noteCurrentePosition=position;
    }

    @Override
    public int getItemCount() {
        return mNote.size();
    }

    public class ViewHolter extends RecyclerView.ViewHolder{

        public final TextView textCourses;
        public int noteCurrentePosition;
        public final TextView textTitle;

        public ViewHolter(@NonNull @NotNull View itemView) {
            super(itemView);
            textCourses = (TextView)itemView.findViewById(R.id.txtTime);
            textTitle = (TextView)itemView.findViewById(R.id.txtNameParturient);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                      Intent intent=new Intent(context.getContext(), NoteActivity.class);
                      intent.putExtra(NoteActivity.NOTE_POSITION,noteCurrentePosition);
                      context.startActivity(intent);
                }
            });
        }
    }
}

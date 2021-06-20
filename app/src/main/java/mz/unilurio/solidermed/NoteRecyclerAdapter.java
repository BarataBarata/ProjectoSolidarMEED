package mz.unilurio.solidermed;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class NoteRecyclerAdapter extends RecyclerView.Adapter<NoteRecyclerAdapter.ViewHolter>{
    private NoteRecyclerAdapter noteRecyclerAdapter;

    private final Context context;
    private List<NoteInfo> mNote;
    private final LayoutInflater layoutInflater;
    private final Context context1;


    public NoteRecyclerAdapter(Context context, List<NoteInfo> note) {
        context1 = context;
        mNote=note;
        this.context = context1;
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolter onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
       View itemView =layoutInflater.inflate(R.layout.item_note_list,parent,false);
        return new ViewHolter(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolter holder, int position) {
              NoteInfo note=mNote.get(position);
              holder.textCourses.setText(note.getCourse().getTitle());
              holder.textTitle.setText(note.getTitle());
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
                    Intent intent=new Intent(context, NoteActivity.class);
                    intent.putExtra(NoteActivity.NOTE_POSITION,noteCurrentePosition);
                    context.startActivity(intent);
                }
            });
        }
    }
}

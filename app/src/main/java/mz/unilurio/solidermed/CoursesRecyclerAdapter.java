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

import mz.unilurio.solidermed.ui.gallery.GalleryFragment;

public class CoursesRecyclerAdapter extends RecyclerView.Adapter<CoursesRecyclerAdapter.ViewHolter>{
    private List<CourseInfo> courseInfos;
    private GalleryFragment  context;

    public CoursesRecyclerAdapter(GalleryFragment context, List<CourseInfo>courseInfo) {
        courseInfos =courseInfo;
        this.context=context;
    }

    @NonNull
    @NotNull
    @Override
    public CoursesRecyclerAdapter.ViewHolter onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View itemView =LayoutInflater.from(parent.getContext()).inflate(R.layout.item_course_list,parent,false);
        return new CoursesRecyclerAdapter.ViewHolter(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull CoursesRecyclerAdapter.ViewHolter holder, int position) {
        CourseInfo courseInfo= courseInfos.get(position);
        holder.textCourses.setText(courseInfo.getTitle().toString());
        holder.courseCurrentePosition =position;
    }

    @Override
    public int getItemCount() {
        return courseInfos.size();
    }

    public class ViewHolter extends RecyclerView.ViewHolder{

        public final TextView textCourses;
        public int courseCurrentePosition;

        public ViewHolter(@NonNull @NotNull View itemView) {
            super(itemView);
            textCourses = (TextView)itemView.findViewById(R.id.txtTime);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent=new Intent(context.getContext(), DadosPessoais.class);
                   // intent.putExtra(NoteActivity.NOTE_POSITION,noteCurrentePosition);
                    context.startActivity(intent);
                }
            });
        }
    }
}

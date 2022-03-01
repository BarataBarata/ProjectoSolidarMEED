package mz.unilurio.solidermed.ui.gallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import mz.unilurio.solidermed.R;

public class GalleryFragment extends Fragment {

    private RecyclerView recyclerView;

    public GalleryFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_gallery, container, false);
//        recyclerView = view.findViewById(R.id.recyclerVieParturiente);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        List<CourseInfo> courses = DataManager.getInstance().getCourses();
//        recyclerView.setAdapter(new CoursesRecyclerAdapter(this, courses));

        return view;
    }
}
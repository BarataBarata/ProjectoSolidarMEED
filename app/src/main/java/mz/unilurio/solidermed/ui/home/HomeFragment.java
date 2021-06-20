package mz.unilurio.solidermed.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import mz.unilurio.solidermed.DataManager;
import mz.unilurio.solidermed.NoteInfo;
import mz.unilurio.solidermed.NoteRecyclerAdapter;
import mz.unilurio.solidermed.NoteRecyclerAdapter2;
import mz.unilurio.solidermed.R;

public class HomeFragment extends Fragment {
    private RecyclerView noteRecyclerAdapter;
    public  HomeFragment(){
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_home, container, false);

        noteRecyclerAdapter=view.findViewById(R.id.list_notes);

        noteRecyclerAdapter.setLayoutManager(new LinearLayoutManager(getContext()));
        List<NoteInfo> notes= DataManager.getInstance().getNotes();
        noteRecyclerAdapter.setAdapter(new NoteRecyclerAdapter2(this,notes));

        return view;


    }
}
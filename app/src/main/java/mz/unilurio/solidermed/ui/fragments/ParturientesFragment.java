package mz.unilurio.solidermed.ui.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import mz.unilurio.solidermed.ParturienteRecyclerAdpter;
import mz.unilurio.solidermed.R;
import mz.unilurio.solidermed.model.DBManager;
import mz.unilurio.solidermed.model.Parturient;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ParturientesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ParturientesFragment extends Fragment {
   RecyclerView recyclerView;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ParturientesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ParturientesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ParturientesFragment newInstance(String param1, String param2) {
        ParturientesFragment fragment = new ParturientesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        List<Parturient> parturients= DBManager.getInstance().getParturients();
        recyclerView.setAdapter(new ParturienteRecyclerAdpter( this,parturients));
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    public void updade(){
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        List<Parturient> parturients= DBManager.getInstance().getParturients();
        recyclerView.setAdapter(new ParturienteRecyclerAdpter( this,parturients));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_parturientes, container, false);
        recyclerView = view.findViewById(R.id.recyclerVieParturiente);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        List<Parturient> parturients= DBManager.getInstance().getParturients();
        recyclerView.setAdapter(new ParturienteRecyclerAdpter( this,parturients));

        return view;
    }
}
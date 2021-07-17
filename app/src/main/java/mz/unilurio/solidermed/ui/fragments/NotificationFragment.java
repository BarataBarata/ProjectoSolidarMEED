package mz.unilurio.solidermed.ui.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import mz.unilurio.solidermed.NotificationRecyclerAdpter;
import mz.unilurio.solidermed.R;
import mz.unilurio.solidermed.model.DBManager;
import mz.unilurio.solidermed.model.Notification;

public class NotificationFragment extends Fragment {
   RecyclerView recyclerView;

    public NotificationFragment() {
        // Required empty public constructor
    }


    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        List<Notification> notifications= DBManager.getInstance().getNotifications();
        recyclerView.setAdapter(new NotificationRecyclerAdpter( getContext(), notifications));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notificacao, container, false);
        recyclerView = view.findViewById(R.id.list_notes);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        List<Notification> notifications= DBManager.getInstance().getNotifications();
        recyclerView.setAdapter(new NotificationRecyclerAdpter( getContext(), notifications));

        return view;
    }
}
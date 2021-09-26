package mz.unilurio.solidermed.model;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.google.android.material.slider.Slider;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import mz.unilurio.solidermed.R;
import mz.unilurio.solidermed.StartApp;

public class Classselecthospital extends AppCompatDialogFragment {

    private DBService dbService;
    public static boolean isOk=false;
    public static boolean isRemove=false;
    private Spinner spinner;

    @NonNull
    @NotNull
    @Override
    public Dialog onCreateDialog(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.select_hospital, null);
        builder.setView(view)
                .setTitle("")
                .setNegativeButton("", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                         isOk=true;
                    }
                });

        dbService=new DBService(this.getContext());
        spinner= view.findViewById(R.id.spinner_hospital);
        List<Hospitais> list = dbService.getListHospitais();
        ArrayAdapter<Hospitais> adapterhospital = new ArrayAdapter<>(this.getContext(), android.R.layout.simple_spinner_item, list);
        adapterhospital.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapterhospital);

        return builder.create();
 }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }


}

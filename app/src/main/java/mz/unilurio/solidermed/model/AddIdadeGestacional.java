package mz.unilurio.solidermed.model;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.google.android.material.slider.Slider;

import org.jetbrains.annotations.NotNull;

import mz.unilurio.solidermed.R;

public class AddIdadeGestacional extends AppCompatDialogFragment {

    TextView textViewDilatation;
    private DBService dbService;

    public static boolean isAdd=false;
    public static boolean isRemove=false;

    private static int numberHours;
    private static int numberMinutes;
    private static int numberDilatation;

    Slider sliderViewDlatation;
    Slider sliderMinutos;
    Slider sliderHora;

    TextView textIdadeGestacional;
    TextView textMinutos;


    @NonNull
    @NotNull
    @Override
    public Dialog onCreateDialog(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog_idade_gestacional, null);
        builder.setView(view)
                .setTitle("Adicionar ").setIcon(R.drawable.common_google_signin_btn_text_dark)
                .setNegativeButton("Nao", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                            dbService.addIdadeGestacional(textIdadeGestacional.getText().toString());
                            isAdd=true;
                            Toast.makeText(getContext(), "Erro ! o numero da dilatação existe", Toast.LENGTH_SHORT).show();

                    }
                });
        textIdadeGestacional = view.findViewById(R.id.idTxtIdadeGestacional);
        dbService=new DBService(this.getContext());
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

package mz.unilurio.solidermed.model;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.google.android.material.slider.Slider;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import mz.unilurio.solidermed.R;

public class EditClassDilatation extends AppCompatDialogFragment {

    private static int dilatation;
    private int hours;
    private  int minuts;
    private  int auxDilatation;

    private static int timerDilatation;
    public static boolean isEdit=false;
    private EditText editDilatation;
    private EditText editEditTimer;
    private  DilatationAndTimer dilatationAndTimer;
    Slider sliderMinutos;
    Slider sliderViewDlatation;
    Slider sliderHora;
    TextView textHoras;
    TextView textMinutos;
    TextView textViewDilatation;
    private  DBService dbService;


    @NonNull
    @NotNull
    @Override
    public Dialog onCreateDialog(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        LayoutInflater inflater=getActivity().getLayoutInflater();
        View view=inflater.inflate(R.layout.layout_dialog_edit_dilatation,null);
        builder.setView(view)
                .setTitle("Editar ").setIcon(R.drawable.mulhergravidabom2)
                .setNegativeButton("Nao", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dbService.updadeDilatation(dbService.getIdDilatation(auxDilatation+""),dilatation,hours,minuts);
                        auxDilatation=dilatation;
                        isEdit=true;
                    }
                });
                textHoras = view.findViewById(R.id.idViewHoras);
                textMinutos=view.findViewById(R.id.idViewMinutos);
                textViewDilatation=view.findViewById(R.id.txtTitleDilatationEdit);

                sliderViewDlatation=view.findViewById(R.id.idEditDilatationAdd);
                sliderMinutos=view.findViewById(R.id.sliderMinutos);
                sliderHora=view.findViewById(R.id.sliderHoras);

                sliderViewDlatation.addOnChangeListener(new Slider.OnChangeListener() {
                    @Override
                    public void onValueChange(@NonNull @NotNull Slider slider, float value, boolean fromUser) {
                        textViewDilatation.setText((int)sliderViewDlatation.getValue()+" : ");
                        dilatation=(int)sliderViewDlatation.getValue();
                    }
                });

                sliderHora.addOnChangeListener(new Slider.OnChangeListener() {
                    @Override
                    public void onValueChange(@NonNull @NotNull Slider slider, float value, boolean fromUser) {
                        textHoras.setText((int)sliderHora.getValue()+" : ");
                        hours=(int)sliderHora.getValue();
                    }
                });

                sliderMinutos.addOnChangeListener(new Slider.OnChangeListener() {
                    @Override
                    public void onValueChange(@NonNull @NotNull Slider slider, float value, boolean fromUser) {
                        textMinutos.setText((int)sliderMinutos.getValue()+" ");
                        minuts=(int)sliderMinutos.getValue();
                    }
                });

                //send
                textViewDilatation.setText(dilatation+"");
                sliderViewDlatation.setValue(dilatation);
                sliderMinutos.setValue(minuts);
                sliderHora.setValue(hours);

          dbService=new DBService(this.getContext());
        return builder.create();

    }

 public void setDilatation(DilatationAndTimer dilatationAndTimer){
     hours=dilatationAndTimer.getTimerDilatationHours();
     minuts=dilatationAndTimer.getTimerDilatationMinutes();
     dilatation = dilatationAndTimer.getNumberDilatation();
     auxDilatation=dilatationAndTimer.getNumberDilatation();
     this.dilatationAndTimer =dilatationAndTimer;
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

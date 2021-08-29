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

public class ViewEditClassNotification extends AppCompatDialogFragment {

    private static int dilatation;
    private static int timerDilatation;
    private EditText editDilatation;
    private EditText editEditTimer;
    private  DilatationAndTimer dilatationAndTimer;
    Slider sliderMinutos;
    Slider sliderViewDlatation;
    Slider sliderHora;
    TextView textHoras;
    TextView textMinutos;
    TextView textViewDilatation;


    @NonNull
    @NotNull
    @Override
    public Dialog onCreateDialog(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        LayoutInflater inflater=getActivity().getLayoutInflater();
        View view=inflater.inflate(R.layout.view_dados_parturientes,null);
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

                    }
                });

        return builder.create();

    }

 public void setContact(int id){
         List<DilatationAndTimer> list=DBManager.getInstance().getDilatationAndTimerList();
         for(DilatationAndTimer dilatationAndTimer:list) {
             if(dilatationAndTimer.getIdDilatation()==id) {
                 dilatation = dilatationAndTimer.getNumberDilatation();
                 timerDilatation = dilatationAndTimer.getTimerDilatationHours();
                 this.dilatationAndTimer =dilatationAndTimer;
                 break;
             }
         }
 }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }


//    Slider sliderMinutos;
//    Slider sliderHora;
//    TextView textHoras;
//    TextView textMinutos;

//
//        sliderMinutos.addOnChangeListener(new Slider.OnChangeListener() {
//        @Override
//        public void onValueChange(@NonNull @NotNull Slider slider, float value, boolean fromUser) {
//            textMinutos.setText((int)sliderMinutos.getValue()+" ");
//        }
//    });
//
//        sliderHora.addOnChangeListener(new Slider.OnChangeListener() {
//        @Override
//        public void onValueChange(@NonNull @NotNull Slider slider, float value, boolean fromUser) {
//            textHoras.setText((int)sliderHora.getValue()+" : ");
//        }
//    });

}

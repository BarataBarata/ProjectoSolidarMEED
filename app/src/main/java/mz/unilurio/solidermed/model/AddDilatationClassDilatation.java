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

public class AddDilatationClassDilatation extends AppCompatDialogFragment {

    private static String dilatation;
    private static String timerDilatation;
    private EditText editDilatation;
    private EditText editEditTimer;
    private DilatationAndTimer dilatationAndTimer;
    TextView textViewDilatation;

    private static int numberHours;
    private static int numberMinutes;
    private static int numberDilatation;

    Slider sliderViewDlatation;
    Slider sliderMinutos;
    Slider sliderHora;

    TextView textHoras;
    TextView textMinutos;


    @NonNull
    @NotNull
    @Override
    public Dialog onCreateDialog(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog_edit_dilatation, null);
        builder.setView(view)
                .setTitle("Adicionar ").setIcon(R.drawable.add)
                .setNegativeButton("Nao", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DBManager.getInstance().addDilatation(new DilatationAndTimer(numberDilatation,numberHours,numberMinutes));
                    }
                });
        textHoras = view.findViewById(R.id.idViewHoras);
        textMinutos = view.findViewById(R.id.idViewMinutos);

        sliderHora = view.findViewById(R.id.sliderHoras);
        sliderMinutos = view.findViewById(R.id.sliderMinutos);
        textViewDilatation = view.findViewById(R.id.txtTitleDilatationEdit);
        sliderViewDlatation = view.findViewById(R.id.idEditDilatationAdd);

        sliderViewDlatation.addOnChangeListener(new Slider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull @NotNull Slider slider, float value, boolean fromUser) {
                textViewDilatation.setText((int) sliderViewDlatation.getValue() + "");
                numberDilatation = (int) sliderViewDlatation.getValue();
            }
        });
        sliderHora.addOnChangeListener(new Slider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull @NotNull Slider slider, float value, boolean fromUser) {
                textHoras.setText((int) sliderHora.getValue() + " : ");
                numberHours = (int) sliderHora.getValue();
            }
        });

        sliderMinutos.addOnChangeListener(new Slider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull @NotNull Slider slider, float value, boolean fromUser) {
                textMinutos.setText((int) sliderMinutos.getValue() + " ");
                numberMinutes = (int) sliderMinutos.getValue();
            }
        });

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

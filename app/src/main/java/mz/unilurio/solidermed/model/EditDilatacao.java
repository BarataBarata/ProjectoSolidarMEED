package mz.unilurio.solidermed.model;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
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

import java.text.BreakIterator;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import mz.unilurio.solidermed.MainActivity;
import mz.unilurio.solidermed.R;

public class EditDilatacao extends AppCompatDialogFragment {

    TextView textViewDilatation;
    private DBService dbService;
    public static Parturient parturientR=new Parturient();

    public static boolean isAdd=false;
    public static boolean isRemove=false;

    private static int numberHours;
    private static int numberMinutes;
    private static int numberDilatation;

    Slider sliderViewDlatation;
    TextView textFase;
    TextView textEditDilatacao;
    TextView textMinutos;
    private int fase;


    @NonNull
    @NotNull
    @Override
    public Dialog onCreateDialog(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_edit_dilatacao, null);
        builder.setView(view)
                .setTitle("Editar ").setIcon(R.drawable.icondilatacao)
                .setNegativeButton("Nao", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                      MainActivity mainActivity=new MainActivity();

                        
                        if(mainActivity.isOutEdit){
                            mainActivity.isOutEdit=false;
                            if(!DBManager.getInstance().getNotifications().isEmpty()){
                                for(Notificacao notificacaor:DBManager.getInstance().getNotifications()){
                                    if(notificacaor.getIdAuxParturiente().equalsIgnoreCase(parturientR.getIdAuxParturiente())){
                                        dbService.deleteNotification(notificacaor);
                                        dbService.deleteParturienteInAuxList(parturientR.getIdAuxParturiente());
                                        dbService.removeInBD(parturientR);
                                        dbService.removParturiente(parturientR);
                                        DBManager.getInstance().getNotifications().remove(notificacaor);
                                    }
                                }
                                try {
                                    dbService.addParturiente(parturientR);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }
                        }else {


                            parturientR.setReason(fase);
                            parturientR.setEditDilatation(true);
                            try {
                                parturientR.setHoraExpulsoDoFeto(getTempoExpulso(dbService.getTimerDilatation(fase + "")));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            dbService.updadeAllDadeParturiente(parturientR);
                            dbService.initializeListParturientesAtendidos();
                            try {
                                dbService.initializeListParturiente();
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            try {
                                dbService.initializeListNotification();
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                });
        MainActivity mainActivity=new MainActivity();
        parturientR=mainActivity.publicParturiente;
        sliderViewDlatation=view.findViewById(R.id.dilatation2);
        TextView textNumber=view.findViewById(R.id.id00);
        TextView textFase=view.findViewById(R.id.txtFasez);
        sliderViewDlatation.setValue(parturientR.getReason());
        textNumber.setText(parturientR.getReason()+"");
        sliderViewDlatation.addOnChangeListener(new Slider.OnChangeListener(){
            @Override
            public void onValueChange(@NonNull @NotNull Slider slider, float value, boolean fromUser) {
                textNumber.setText((int) sliderViewDlatation.getValue()+"");
                fase = (int) sliderViewDlatation.getValue();
                if(fase >3){
                    textFase.setTextColor(Color.RED);
                    textFase.setText(" na fase activa");
                }else{
                    textFase.setTextColor(Color.BLACK);
                    textFase.setText(" na fase latente");
                }

            }
        });
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

    public  String getTempoExpulso(int seguntos) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy-HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.SECOND,seguntos);
        return formatd(calendar.getTime());
    }
    private String formatd(Date date){
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy-HH:mm:ss");

        return dateFormat.format(date);
    }
    public void setParturiente(Parturient parturient2) {
           // parturientR=parturient2;
    }
}

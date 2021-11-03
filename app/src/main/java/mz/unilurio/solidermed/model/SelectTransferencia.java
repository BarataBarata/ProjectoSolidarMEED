package mz.unilurio.solidermed.model;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import mz.unilurio.solidermed.R;

public class SelectTransferencia extends AppCompatDialogFragment {

    private DBService dbService;
    private Switch aSwitchTrans;
    private Spinner transferencia;
    private Spinner spinnerMotivos;
    public static boolean isAcceptTransference=false;
    public static boolean isRemove=false;
    public static String opcaoSanitaria;
    public static String motivoTransferencia;

    @NonNull
    @NotNull
    @Override
    public Dialog onCreateDialog(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.select_trasferencia, null);
        builder.setView(view)
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setPositiveButton("adicionar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        opcaoSanitaria=transferencia.getSelectedItem().toString();
                        motivoTransferencia= spinnerMotivos.getSelectedItem().toString();
                        isAcceptTransference=true;
                     }
                });



        transferencia = view.findViewById(R.id.spinnerOrigem);
        List<String> listSanitaria = DBManager.getInstance().getListOpcoesUnidadeSanitaria();
        ArrayAdapter<String> adapterSanitaria = new ArrayAdapter<>(this.getContext(), android.R.layout.simple_spinner_item, listSanitaria);
        adapterSanitaria.setDropDownViewResource(android.R.layout.simple_spinner_item);
        transferencia.setAdapter(adapterSanitaria);

        spinnerMotivos =view.findViewById(R.id.spinnerMotivos);
        List<String> listTrasferencia = DBManager.getInstance().getListMotivosTrasferencia();
        ArrayAdapter<String> adapterTrasferencia = new ArrayAdapter<>(this.getContext(), android.R.layout.simple_spinner_item, listTrasferencia);
        adapterTrasferencia.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerMotivos.setAdapter( adapterTrasferencia );

         aSwitchTrans=view.findViewById(R.id.switchTransferencia);
         transferencia=view.findViewById(R.id.spinnerOrigem);
         spinnerMotivos =view.findViewById(R.id.spinnerMotivos);
         aSwitchTrans.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
         @Override
         public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

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




}

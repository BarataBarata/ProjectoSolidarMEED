package mz.unilurio.solidermed.model;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import org.jetbrains.annotations.NotNull;

import mz.unilurio.solidermed.R;

public class AddDoctorClass extends AppCompatDialogFragment {

    private EditText editNome;
    private EditText editContact;
    public static boolean isAdd=false;
    public static boolean isRemove=false;
    DBService dbService;

    @NonNull
    @NotNull
    @Override
    public Dialog onCreateDialog(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        LayoutInflater inflater=getActivity().getLayoutInflater();
        View view=inflater.inflate(R.layout.layout_dialog_edit_contact,null);
        builder.setView(view)
                .setTitle("Adicionar Medico").setIcon(R.drawable.ic_baseline_person_add_alt_1_24)
                .setNegativeButton("Nao", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       if(validation(editContact)){
                           if(validation(editNome)){
                               if(!dbService.isTellDoctor(editContact.getText().toString())) {
                                   dbService.addDoctor("","",editContact.getText().toString(),editNome.getText().toString());
                                   isAdd = true;
                                   Toast.makeText( getContext(), " Usuario registado com sucesso", Toast.LENGTH_SHORT).show();

                               }else {
                                   Toast.makeText( getContext(), " Falha ao registar, o contacto existe", Toast.LENGTH_SHORT).show();
                               }
                           }
                       }

                    }
                });
              editContact=view.findViewById(R.id.idContactEditContact);
              editNome=view.findViewById(R.id.idNomeEditContact);
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

    public  boolean validation(EditText editText){
            if(editText.getText().toString().isEmpty()){
                Toast.makeText(getContext(), "Erro campo vazio", Toast.LENGTH_SHORT).show();
            return false;
            }
            return true;
    }
}

package mz.unilurio.solidermed.model;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import org.jetbrains.annotations.NotNull;

import java.util.Random;

import mz.unilurio.solidermed.R;

public class AddNursesClass extends AppCompatDialogFragment {
    private EditText editNome;
    private EditText editContact;
    public static boolean isAdd=false;
    public static boolean isRemove=false;
    private  DBService dbService;


    @NonNull
    @NotNull
    @Override
    public Dialog onCreateDialog(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        LayoutInflater inflater=getActivity().getLayoutInflater();
        View view=inflater.inflate(R.layout.layout_dialog_edit_nurse,null);
        builder.setView(view)
                .setTitle("Adicionar Enfermeira").setIcon(R.drawable.ic_baseline_person_add_alt_1_24)
                .setNegativeButton("Nao", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(validation(editNome)){
                            if(validation(editContact)){
                                dbService.addNurse(removeFiristSpece(editNome.getText().toString()),"","",editContact.getText().toString());
                                isAdd=true;
                            }
                        }
                    }
                });
        editContact=view.findViewById(R.id.idNurseEditContact);
        editNome=view.findViewById(R.id.idNomeEditNurse);
        dbService=new DBService(this.getContext());
        return builder.create();

    }


    public String removeFiristSpece(String nome){

        String user="";
        for(int i=0; i<nome.length();i++){
            if(nome.charAt(i)!=' '){
                user=nome.substring(i);
                return user;
            }
        }
        return user;
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

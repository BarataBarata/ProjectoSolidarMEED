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

import java.util.List;

import mz.unilurio.solidermed.R;

public class EditDoctorClass extends AppCompatDialogFragment {

    private String nome;
    private String contact;
    private  String apelido;
    public  static Boolean isOK=false;
    private EditText editNome;
    private EditText editContact;
    private  EditText editApelido;
    private UserDoctor userDoctor;
    private  DBService dbService;


    @NonNull
    @NotNull
    @Override
    public Dialog onCreateDialog(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        LayoutInflater inflater=getActivity().getLayoutInflater();
        View view=inflater.inflate(R.layout.layout_dialog_edit_contact,null);
        builder.setView(view)
                .setTitle("Editar Medico").setIcon(R.drawable.edit_timer_and_dilatation)
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
                                dbService.updadeDoctorNameAndTellphone(dbService.getIdDoctor(contact),editContact.getText().toString(),editNome.getText().toString());
                                contact=editContact.getText().toString();
                                isOK=true;
                            }
                        }

                    }
                });
              editContact=view.findViewById(R.id.idContactEditContact);
              editNome=view.findViewById(R.id.idNomeEditContact);

        editContact.setText(contact);
        editNome.setText(nome);
        dbService=new DBService(this.getContext());
        return builder.create();

    }

 public  void setUserDoctor(UserDoctor userDoctor){
     nome = userDoctor.getFullName();
     contact = userDoctor.getContacto();
     this.userDoctor =userDoctor;
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

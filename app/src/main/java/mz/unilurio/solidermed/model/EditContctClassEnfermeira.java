package mz.unilurio.solidermed.model;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import mz.unilurio.solidermed.R;

public class EditContctClassEnfermeira extends AppCompatDialogFragment {

    private String nome;
    private String contact;
    private String apelido;

    private EditText editNome;
    private EditText editContact;
    private EditText editApelido;
    private  UserNurse userNurse;

    @NonNull
    @NotNull
    @Override
    public Dialog onCreateDialog(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        LayoutInflater inflater=getActivity().getLayoutInflater();
        View view=inflater.inflate(R.layout.layout_dialog_edit_contact,null);
        builder.setView(view)
                .setTitle("Editar Contacto").setIcon(R.drawable.edit_contact)
                .setNegativeButton("Nao", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        userNurse.setContacto(editContact.getText().toString());
                        userNurse.setFullName(editNome.getText().toString());
                    }
                });
              editContact=view.findViewById(R.id.idContactEditContact);
              editNome=view.findViewById(R.id.idNomeEditContact);
              editApelido=view.findViewById(R.id.idApelidoEditContact);

        editContact.setText(contact);
        editNome.setText(nome);
        editApelido.setText(apelido);

        return builder.create();

    }

 public  void getContact(int id){
         List<UserNurse> list=DBManager.getInstance().getUserNurseList();
         for(UserNurse userNurse1:list) {
             if(Integer.parseInt(userNurse1.getIdNurse())==id) {
                 nome = userNurse1.getFullName();
                 contact = userNurse1.getContacto();
                 apelido=userNurse1.getUserNurse();
                 userNurse=userNurse1;
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
}

package mz.unilurio.solidermed.model;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import mz.unilurio.solidermed.ContactActivity;
import mz.unilurio.solidermed.EmergencyMedicalPersonnelRecyclerAdpter;
import mz.unilurio.solidermed.EmergencyMedicalPersonnelRecyclerAdpter2;
import mz.unilurio.solidermed.MainActivity;
import mz.unilurio.solidermed.R;
import mz.unilurio.solidermed.SettingActivity;

public class EditContctClass extends AppCompatDialogFragment {

    private String nome;
    private String contact;
    private  String apelido;

    private EditText editNome;
    private EditText editContact;
    private  EditText editApelido;
    private EmergencyMedicalPersonnel emergencyMedicalPersonnel;


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
                        emergencyMedicalPersonnel.setContact(editContact.getText().toString());
                        emergencyMedicalPersonnel.setName(editNome.getText().toString());
                        emergencyMedicalPersonnel.setSurname(editApelido.getText().toString());
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
         List<EmergencyMedicalPersonnel> list=DBManager.getInstance().getEmergencyMedicalPersonnels();
         for(EmergencyMedicalPersonnel emergencyMedical:list) {
             if(emergencyMedical.getId()==id) {
                 nome = emergencyMedical.getName();
                 contact = emergencyMedical.getContact();
                 apelido = emergencyMedical.getSurname();
                 emergencyMedicalPersonnel=emergencyMedical;
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

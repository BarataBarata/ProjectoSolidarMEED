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

import mz.unilurio.solidermed.ContactActivity;
import mz.unilurio.solidermed.R;

public class AddContctClass extends AppCompatDialogFragment {


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
                .setTitle("Adicionar Contacto").setIcon(R.drawable.edit_contact)
                .setNegativeButton("Nao", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       emergencyMedicalPersonnel=new EmergencyMedicalPersonnel();
                        emergencyMedicalPersonnel.setContact(editContact.getText().toString());
                        emergencyMedicalPersonnel.setName(editNome.getText().toString());
                        emergencyMedicalPersonnel.setSurname(editApelido.getText().toString());
                        DBManager.getInstance().addEmergencyMedicalPersonnels(emergencyMedicalPersonnel);


                    }
                });
              editContact=view.findViewById(R.id.idContactEditContact);
              editNome=view.findViewById(R.id.idNomeEditContact);
              editApelido=view.findViewById(R.id.idApelidoEditContact);

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

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

public class EditNurseClass  extends AppCompatDialogFragment {
    private EditText editNome;
    private EditText editContact;
    private EditText editUser;
    private EditText editPassword;
    public static boolean isEdit=false;
    private  EditText editApelido;
    private UserNurse userNurse;

    private String nome;
    private String contact;
    private DBService dbService;
    private  String apelido;


    @NonNull
    @NotNull
    @Override
    public Dialog onCreateDialog(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        LayoutInflater inflater=getActivity().getLayoutInflater();
        View view=inflater.inflate(R.layout.layout_dialog_edit_nurse,null);
        builder.setView(view)
                .setTitle("Editar").setIcon(R.drawable.edit_timer_and_dilatation)
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
                                 dbService.updadeNurse(dbService.getIdNurse(editContact.getText().toString()),upCaseName(editNome.getText().toString()),editUser.getText().toString(),editPassword.getText().toString(),editContact.getText().toString());
                                 contact=editContact.getText().toString();
                                 isEdit=true;
                             }
                         }

                    }
                });

        editContact=view.findViewById(R.id.idNurseEditContact);
        editNome=view.findViewById(R.id.idNomeEditNurse);
        editUser=view.findViewById(R.id.idNurseUser);
        editPassword=view.findViewById(R.id.idNursePassword);


        editContact.setText(userNurse.getContacto());
        editNome.setText(userNurse.getFullName());
        editPassword.setText(userNurse.getPassworNurse());
        editUser.setText(userNurse.getUserNurse());
        dbService=new DBService(this.getContext());

        return builder.create();

    }
    public  void setIdNurse(UserNurse userNurse){
        this.userNurse =userNurse;
    }
    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
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

    public  boolean validation(EditText editText){
        if(editText.getText().toString().isEmpty()){
            Toast.makeText(getContext(), "Erro campo vazio", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public String upCaseName(String name){

        name=oUpperFirstCase(name);
        String auxStr="";
        int i=0;

        for( i=0;i<name.length();i++){
            if(i<name.length()){
                if(!(name.charAt(i)==' ' && i+1==name.length()))
                    if(name.charAt(i)==' ' && name.charAt(i+1)!=' '){
                        auxStr=auxStr+(" ");
                        auxStr=auxStr+(name.charAt(i+1)+"").toUpperCase();
                        i++;
                    }else {
                        auxStr=auxStr+(name.charAt(i)+"");
                    }
            }
        }
        /// auxStr=auxStr+(name.charAt(i)+"");
        return auxStr;
    }

    public  String oUpperFirstCase(String string){
        String auxString=(string.charAt(0)+"").toUpperCase()+""+string.substring(1)+"";
        return  auxString;
    }
}

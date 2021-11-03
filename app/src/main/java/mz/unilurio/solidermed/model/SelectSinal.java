package mz.unilurio.solidermed.model;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.google.android.material.slider.Slider;

import org.jetbrains.annotations.NotNull;

import mz.unilurio.solidermed.R;

public class SelectSinal extends AppCompatDialogFragment {

    private DBService dbService;
    private Switch aSwitch;
    private CheckBox  checkBox1;
    private CheckBox  checkBox2;
    private boolean isSwich;
    public  String nameSelectetcheckBox1="";
    public  String nameSelectetcheckBox2="";
    public  static String allNameselect="Nenhum";
    public static boolean isSelectetOptionSanitaria=false;
    public static boolean isRemove=false;

    @NonNull
    @NotNull
    @Override
    public Dialog onCreateDialog(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.select_sinais, null);
        builder.setView(view)
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setPositiveButton("adicionar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                     isSelectetOptionSanitaria=true;
                     if(isSwich){
                         allNameselect="Nenhum";
                     }else{
                         if(!nameSelectetcheckBox2.isEmpty() || !nameSelectetcheckBox1.isEmpty()){
                             allNameselect=nameSelectetcheckBox1+", "+nameSelectetcheckBox2;
                         }else{
                             allNameselect="Nenhum";
                         }

                     }


                    }
                });

     aSwitch=view.findViewById(R.id.switchSinal);
     checkBox1=view.findViewById(R.id.c1);
     checkBox2=view.findViewById(R.id.c2);


     checkBox1.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
          if(checkBox1.isChecked()){
              aSwitch.setChecked(false);
              nameSelectetcheckBox1=checkBox1.getText().toString();
          }else {
              nameSelectetcheckBox1="";
          }
         }
     });

     checkBox2.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             if(checkBox2.isChecked()){
                 nameSelectetcheckBox2=checkBox2.getText().toString();
                 aSwitch.setChecked(false);
             }else {
                 nameSelectetcheckBox2="";
             }
         }
     });

     aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
         @Override
         public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    isSwich=true;
                    checkBox1.setChecked(false);
                    checkBox2.setChecked(false);
                }else {
                    isSwich=false;
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


}

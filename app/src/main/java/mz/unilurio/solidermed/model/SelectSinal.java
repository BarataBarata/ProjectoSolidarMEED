package mz.unilurio.solidermed.model;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import mz.unilurio.solidermed.R;

public class SelectSinal extends AppCompatDialogFragment {
    public static ArrayList<EscolhaSinaisPatologia> arrayList =new ArrayList<>();
    private DBService dbService;
    public Switch aSwitchNenhum;
    public CheckBox  checkBox1;
    public CheckBox  checkBox2;
    public CheckBox  checkBox3;
    public CheckBox  checkBox4;
    public CheckBox  checkBox5;
    public CheckBox  checkBox6;
    public CheckBox  checkBox7;
    public CheckBox  checkBox8;
    public CheckBox  checkBox9;
    public CheckBox  checkBox10;
    public static boolean  isBox1;
    public static boolean isBox2;
    public static boolean  isBox3;
    public static boolean  isBox4;
    public static boolean  isBox5;
    public static boolean  isBox6;
    public static boolean  isBox7;
    public static boolean  isBox8;
    public static boolean  isBox9;
    public static boolean isBox10;
    public  String nameSelectetcheckBox1="";
    public  String nameSelectetcheckBox2="";
    public  static String allNameselect="Nenhum";
    public static boolean isSelectetOptionSanitaria=false;
    public static boolean isRemove=false;
    private static boolean valuePara;
    private static boolean valuePara2;

    @NonNull
    @NotNull
    @Override
    public Dialog onCreateDialog(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.select_sinais, null);
        builder.setView(view)
                .setNegativeButton("", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                    }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        isSelectetOptionSanitaria=true;
//
//                     if(arrayList.isEmpty()){
//                         allNameselect="Nenhum";
//                     } else {
//                         allNameselect="";
//                         for (EscolhaSinaisPatologia escolhaSinaisPatologia: arrayList){
//                              allNameselect=allNameselect+escolhaSinaisPatologia.getNomeSinal()+", ";
//                         }
//                     }

                    }
                });


        aSwitchNenhum =view.findViewById(R.id.switchSinal);
        checkBox1=view.findViewById(R.id.c1);
        checkBox2=view.findViewById(R.id.c2);
        checkBox3=view.findViewById(R.id.c3);
        checkBox4=view.findViewById(R.id.c4);
        checkBox5=view.findViewById(R.id.c5);
        checkBox6=view.findViewById(R.id.c6);
        checkBox7=view.findViewById(R.id.c7);
        checkBox8=view.findViewById(R.id.c8);
        checkBox9=view.findViewById(R.id.c9);
        checkBox10=view.findViewById(R.id.c10);




        checkBox1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id=1;
                if(checkBox1.isChecked()){
                    isBox1=true;
                    aSwitchNenhum.setChecked(false);
                    addOptionSelect(id,checkBox1.getText().toString());

                }else {
                    isBox1=false;
                    removeListItem(id);
                }
            }
        });

        checkBox2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id=2;
                if(checkBox2.isChecked()){
                    isBox2=true;
                    aSwitchNenhum.setChecked(false);
                    aSwitchNenhum.setChecked(false);
                    addOptionSelect(id,checkBox2.getText().toString());

                }else {
                    isBox2= false;
                    removeListItem(id);

                }
            }
        });
        checkBox3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id=3;
                if(checkBox3.isChecked()){
                    isBox3=true;
                    aSwitchNenhum.setChecked(false);
                    addOptionSelect(id,checkBox3.getText().toString());

                }else {
                    isBox3=false;
                    removeListItem(id);
                }
            }
        });
        checkBox4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id=4;
                if(checkBox4.isChecked()){
                    isBox4=true;
                    aSwitchNenhum.setChecked(false);
                    addOptionSelect(id,checkBox4.getText().toString());
                }else {
                    isBox4=false;
                    removeListItem(id);
                }
            }
        });
        checkBox5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id=5;
                if(checkBox5.isChecked()){
                    isBox5=true;
                    aSwitchNenhum.setChecked(false);
                    addOptionSelect(id,checkBox5.getText().toString());

                }else {
                    if(!getIsSelected()) {
                        isBox5 = false;
                        removeListItem(id);
                    }
                }
            }
        });
        checkBox6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id=6;
                if(checkBox6.isChecked()){
                    isBox6=true;
                    aSwitchNenhum.setChecked(false);
                    addOptionSelect(id,checkBox6.getText().toString());

                }else {
                    if(!getIsSelected2()) {
                        isBox6 = false;
                        removeListItem(id);
                    }
                }
            }
        });
        checkBox7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id=7;
                if(checkBox7.isChecked()){
                    isBox7=true;
                    aSwitchNenhum.setChecked(false);
                    addOptionSelect(id,checkBox7.getText().toString());

                }else {
                    isBox7=false;
                    removeListItem(id);
                }
            }
        });

        checkBox8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int id=8;
                if(checkBox8.isChecked()){
                    isBox8=true;
                    aSwitchNenhum.setChecked(false);
                    addOptionSelect(id,checkBox8.getText().toString());

                }else {
                    isBox8=false;
                    removeListItem(id);
                }
            }
        });
        checkBox9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id=9;
                if(checkBox9.isChecked()){
                    isBox9=true;
                    aSwitchNenhum.setChecked(false);
                    addOptionSelect(id,checkBox9.getText().toString());

                }else {
                    isBox9=false;
                    removeListItem(id);
                }
            }
        });
        checkBox10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id=10;
                if(checkBox10.isChecked()){
                    isBox10=true;
                    aSwitchNenhum.setChecked(false);
                    addOptionSelect(id,checkBox10.getText().toString());

                }else {
                    isBox10=false;
                    removeListItem(id);
                }
            }
        });
        aSwitchNenhum.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    checkBox1.setChecked(false);
                    checkBox3.setChecked(false);
                    checkBox4.setChecked(false);
                    checkBox2.setChecked(false);
                    checkBox7.setChecked(false);
                    checkBox8.setChecked(false);
                    checkBox9.setChecked(false);
                    checkBox10.setChecked(false);
                    isBox1=false;
                    isBox3=false;
                    isBox4=false;
                    isBox2=false;
                    isBox7=false;
                    isBox8=false;
                    isBox9=false;
                    isBox10=false;




                    if(getIsSelected() && getIsSelected2()){
                        for( EscolhaSinaisPatologia e: arrayList){

                            if(e.getIdSelect()!=5 && e.getIdSelect()!=6){
                                arrayList.remove(e);
                            }
                        }
                    }else {
                        if(getIsSelected()){
                            for( EscolhaSinaisPatologia e: arrayList){

                                if(e.getIdSelect()!=5){
                                    arrayList.remove(e);
                                }
                            }
                        }else{
                            if(getIsSelected2()){
                                for( EscolhaSinaisPatologia e: arrayList){

                                    if(e.getIdSelect()!=5 && e.getIdSelect()!=6){
                                        arrayList.remove(e);
                                    }
                                }
                            }else{
                                isBox5=false;
                                isBox6=false;
                                checkBox5.setChecked(false);
                                checkBox6.setChecked(false);
                                arrayList.removeAll(arrayList);
                            }
                        }

                    }




                }

            }
        });

        dbService=new DBService(this.getContext());
        return builder.create();
    }

    public void removeListItem(int id) {
        for(EscolhaSinaisPatologia escolhaSinaisPatologia: arrayList){
            System.out.println(escolhaSinaisPatologia.getIdSelect()+"  ===  "+id);
            if(escolhaSinaisPatologia.getIdSelect()==id){
                arrayList.remove(escolhaSinaisPatologia);
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
        checkBox1.setChecked(isBox1);
        checkBox2.setChecked(isBox2);
        checkBox3.setChecked(isBox3);
        checkBox4.setChecked(isBox4);
        checkBox5.setChecked(isBox5);
        checkBox6.setChecked(isBox6);
        checkBox7.setChecked(isBox7);
        checkBox8.setChecked(isBox8);
        checkBox9.setChecked(isBox9);
        checkBox10.setChecked(isBox10);

        if(getIsSelected()){
            checkBox5.setChecked(true);
        }else {
            checkBox5.setChecked(false);
        }

        if(getIsSelected2()){
            checkBox6.setChecked(true);
        }else {
            checkBox6.setChecked(false);
        }

        if(arrayList.isEmpty()){
            aSwitchNenhum.setChecked(true);
        }else {
            aSwitchNenhum.setChecked(false);
        }
    }


    public void setIsSelected(boolean value){
        this.valuePara=value;
    }
    public  boolean getIsSelected(){
        return  valuePara;
    }

    public void setIsSelected2(boolean value){
        this.valuePara2=value;
    }
    public  boolean getIsSelected2(){
        return  valuePara2;
    }

    public void addOptionSelect(int id, String checkBox){
        int cont=0;
        for (EscolhaSinaisPatologia escolhaSinaisPatologia: arrayList){
            if(escolhaSinaisPatologia.getIdSelect()==id){
                cont++;
            }
        }
        if(cont==0){
            arrayList
                    .add(new EscolhaSinaisPatologia(checkBox,
                            true,id));
        }

    }

}

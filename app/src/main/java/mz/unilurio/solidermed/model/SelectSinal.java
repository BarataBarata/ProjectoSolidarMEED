package mz.unilurio.solidermed.model;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import mz.unilurio.solidermed.R;

public class SelectSinal extends AppCompatDialogFragment {
    private static ArrayList<EscolhaSinaisPatologia> arrayList =new ArrayList<>();
    private DBService dbService;
    private Switch aSwitchNenhum;
    private CheckBox  checkBox1;
    private CheckBox  checkBox2;
    private CheckBox  checkBox3;
    private CheckBox  checkBox4;
    private CheckBox  checkBox5;
    private CheckBox  checkBox6;
    private CheckBox  checkBox7;
    private CheckBox  checkBox8;
    private CheckBox  checkBox9;
    private CheckBox  checkBox10;
    private static boolean  isBox1;
    private static boolean isBox2;
    private static boolean  isBox3;
    private static boolean  isBox4;
    private static boolean  isBox5;
    private static boolean isBox6;
    private static boolean isBox7;
    private static boolean  isBox8;
    private static boolean  isBox9;
    private static boolean isBox10;
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


                     if(arrayList.isEmpty()){
                         allNameselect="Nenhum";
                     } else {
                         allNameselect="";
                         for (EscolhaSinaisPatologia escolhaSinaisPatologia: arrayList){
                              allNameselect=allNameselect+escolhaSinaisPatologia.getNomeSinal()+", ";
                         }
                     }

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
                 arrayList
                         .add(new EscolhaSinaisPatologia(checkBox1.getText().toString(),
                                 checkBox1.isChecked(),id));

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
                 arrayList
                         .add(new EscolhaSinaisPatologia(checkBox2.getText().toString(),
                                 checkBox2.isChecked(),id));

             }else {
                 isBox2=false;
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
                    arrayList
                            .add(new EscolhaSinaisPatologia(checkBox3.getText().toString(),
                                    checkBox3.isChecked(),id));

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
                    arrayList
                            .add(new EscolhaSinaisPatologia(checkBox4.getText().toString(),
                                    checkBox4.isChecked(),id));

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
                    arrayList
                            .add(new EscolhaSinaisPatologia(checkBox5.getText().toString(),
                                    checkBox5.isChecked(),id));

                }else {
                    isBox5=false;
                    removeListItem(id);
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
                    arrayList
                            .add(new EscolhaSinaisPatologia(checkBox6.getText().toString(),
                                    checkBox6.isChecked(),id));

                }else {
                    isBox6=false;
                    removeListItem(id);
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
                    arrayList
                            .add(new EscolhaSinaisPatologia(checkBox7.getText().toString(),
                                    checkBox7.isChecked(),id));

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
                    arrayList
                            .add(new EscolhaSinaisPatologia(checkBox8.getText().toString(),
                                    checkBox8.isChecked(),id));

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
                    arrayList
                            .add(new EscolhaSinaisPatologia(checkBox9.getText().toString(),
                                    checkBox9.isChecked(),id));

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
                    arrayList
                            .add(new EscolhaSinaisPatologia(checkBox10.getText().toString(),
                                    checkBox10.isChecked(),id));

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
                    checkBox2.setChecked(false);
                    checkBox3.setChecked(false);
                    checkBox4.setChecked(false);
                    checkBox5.setChecked(false);
                    checkBox6.setChecked(false);
                    checkBox7.setChecked(false);
                    checkBox8.setChecked(false);
                    checkBox9.setChecked(false);
                    checkBox10.setChecked(false);
                    isBox1=false;
                    isBox2=false;
                    isBox3=false;
                    isBox4=false;
                    isBox5=false;
                    isBox6=false;
                    isBox7=false;
                    isBox8=false;
                    isBox9=false;
                    isBox10=false;
                    arrayList.removeAll(arrayList);
                }

         }
     });

        dbService=new DBService(this.getContext());
        return builder.create();
 }

    private void removeListItem(int id) {
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

        if(arrayList.isEmpty()){
            aSwitchNenhum.setChecked(true);
        }else {
            aSwitchNenhum.setChecked(false);
        }
    }


}

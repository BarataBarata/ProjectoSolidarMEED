package mz.unilurio.solidermed;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import mz.unilurio.solidermed.model.DBService;
import mz.unilurio.solidermed.model.EditDoctorClass;
import mz.unilurio.solidermed.model.EditNurseClass;
import mz.unilurio.solidermed.model.UserDoctor;
import mz.unilurio.solidermed.model.UserNurse;

public class ActivitViewPasswordUser extends AppCompatActivity {

    ImageView imageUserEdit;
    ImageView imageUserPasswordEdit;
    TextView  textFullName;
    TextView  textUser;
    TextView  textPassword;
    TextView  textContaco;
    private String id;
    private DBService dbService;
    private boolean isUserNurse;
    private UserDoctor userDoctorAux;
    private UserNurse userNurseAux;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activit_view_password_user);

        dbService=new DBService(this);
        textPassword=findViewById(R.id.idTextPassword);
        textUser=findViewById(R.id.idUserPassword);
        textFullName=findViewById(R.id.txtNomeUsuario);
        textContaco=findViewById(R.id.idTextContacto);

        if(getIntent().getStringExtra("userDoctor")!=null){
            id=getIntent().getStringExtra("userDoctor");

            for(UserDoctor userDoctor : dbService.getListDoctor()){
                if(userDoctor.getIdUser()==Integer.parseInt(id)){
                    userDoctorAux=userDoctor;
                    textFullName.setText(userDoctor.getFullName());
                    textUser.setText("Usuario :"+userDoctor.getUserLogin());
                    textPassword.setText("Senha :"+userDoctor.getPasswordUser());
                    textContaco.setText(userDoctor.getContacto());
                }
            }
        }

        if(getIntent().getStringExtra("userNurse")!=null){
            id=getIntent().getStringExtra("userNurse");
            for(UserNurse userNurse : dbService.getListNurse()){
                if(userNurse.getIdNurse()==Integer.parseInt(id)){
                    userNurseAux=userNurse;
                    isUserNurse=true;
                    textFullName.setText(userNurse.getFullName());
                    textUser.setText("Usuario : "+userNurse.getUserNurse());
                    textPassword.setText("Senha : "+userNurse.getPassworNurse());
                    textContaco.setText(userNurse.getContacto());
                }
            }

        }

    }

    public void finish(View view) {
        finish();
    }

    public void editarPasswordUser(View view) {
        if(isUserNurse){
            isUserNurse=false;
            EditNurseClass editNurseClass=new EditNurseClass();
            editNurseClass.setIdNurse(userNurseAux);
            editNurseClass.show(getSupportFragmentManager(),"Editar");
        }else {
            EditDoctorClass editDoctorClass =new EditDoctorClass();
            editDoctorClass.setUserDoctor(userDoctorAux);
            editDoctorClass.show(getSupportFragmentManager(),"Editar");
        }


    }
}
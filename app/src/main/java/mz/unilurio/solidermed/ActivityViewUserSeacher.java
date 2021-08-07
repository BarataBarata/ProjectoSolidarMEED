package mz.unilurio.solidermed;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ActivityViewUserSeacher extends AppCompatActivity {
   private  String numberSeacher;
   private TextView textName;
   private String nomeCompleto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_user_seacher);

        textName=findViewById(R.id.idViewResult);
        if(getIntent().getStringExtra("numberSeacherUser")!=null){
            numberSeacher = getIntent().getStringExtra("numberSeacherUser");
            nomeCompleto=getIntent().getStringExtra("nomeCompleto");
            textName.setText(nomeCompleto);
        }
    }

    public void finish(View view) {
        finish();
    }

    public void voltar(View view) {
        finish();
    }

    public void continuar(View view) {
        Intent intent=new Intent(ActivityViewUserSeacher.this,ActivitySendMenssage.class);
        intent.putExtra("numberSeacherUser",numberSeacher);
        intent.putExtra("nomeCompleto", nomeCompleto);
        startActivity(intent);

    }
}
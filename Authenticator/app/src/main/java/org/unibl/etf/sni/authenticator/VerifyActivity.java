package org.unibl.etf.sni.authenticator;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import org.unibl.etf.sni.bean.SessionBean;
import java.util.Date;

public class VerifyActivity extends AppCompatActivity {
    private EditText verificationCode;
    private Button btnDate;
    private Button btnName;
    private SessionBean bean;
    private Button verify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);
        verificationCode = findViewById(R.id.verificationCode);
        btnDate=findViewById(R.id.btnPid);
        btnName=findViewById(R.id.btnName);
        verify=findViewById(R.id.btnVerify);
    }

    public void superOnBackPressed(){
        super.onBackPressed();
    }
    @Override
    public void onBackPressed() {
        final VerifyActivity activity=this;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.dialog_message)
                .setTitle(R.string.dialog_title);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                activity.superOnBackPressed();
            }
        });
        builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });
        AlertDialog dialog= builder.create();
        dialog.show();
    }


    public void verify(View view){
        Intent intent=getIntent();
        bean=(SessionBean)intent.getSerializableExtra("BEAN");
        String code=verificationCode.getText().toString();

        if(bean.getToken().equals(code) && bean.getValidUntil().after(new Date())){
            Toast.makeText(this,"Confirmed",Toast.LENGTH_LONG).show();
            if(bean.getAdmin()) {
                btnDate.setVisibility(View.VISIBLE);
            }
            btnName.setVisibility(View.VISIBLE);
            verificationCode.setVisibility(View.INVISIBLE);
            verify.setVisibility(View.INVISIBLE);
        }else{
            verificationCode.getText().clear();
            Toast.makeText(this,"Wrong code",Toast.LENGTH_LONG).show();
        }
        //new VerifyTask(verificationCode,this,token).execute();
    }

    public void getByDate(View view){
        Intent intent = new Intent(this, DateActivity.class);
        intent.putExtra("BEAN",bean);
        startActivity(intent);
    }

    public void getByName(View view){
        Intent intent = new Intent(this, PidActivity.class);
        intent.putExtra("BEAN",bean);
        startActivity(intent);
    }
}

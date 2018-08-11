package org.unibl.etf.sni.authenticator;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import java.util.Date;

public class VerifyActivity extends AppCompatActivity {
    private EditText verificationCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);
        verificationCode = findViewById(R.id.verificationCode);
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
        String response=intent.getStringExtra("TOKEN");
        String[] data=response.split("#");
        String token=data[0];
        Long time=Long.parseLong(data[1]);
        Date validUntil=new Date(time);
        String code=verificationCode.getText().toString();
        if(token.equals(code) && validUntil.before(new Date())){
            Toast.makeText(this,"Confirmed",Toast.LENGTH_LONG).show();
        }else{
            verificationCode.getText().clear();
            Toast.makeText(this,"Wrong code",Toast.LENGTH_LONG).show();
        }
        //new VerifyTask(verificationCode,this,token).execute();
    }
}

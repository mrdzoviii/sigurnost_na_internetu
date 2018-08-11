package org.unibl.etf.sni.authenticator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.unibl.etf.sni.task.DocumentsThread;
import org.unibl.etf.sni.task.LoginTask;


public class LoginActivity extends AppCompatActivity {

    private EditText mUsername;
    private EditText mPassword;
    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mButton = findViewById(R.id.btnLogin);
        mUsername=findViewById(R.id.username);
        mPassword=findViewById(R.id.password);

    }
    public void login(View view){
        new DocumentsThread(null,this).start();
        new LoginTask(mUsername,mPassword,this).execute();
    }

}

package org.unibl.etf.sni.authenticator;


        import android.content.Context;
        import android.content.SharedPreferences;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.TextView;

        import org.unibl.etf.sni.task.LoginTask;


public class LoginActivity extends AppCompatActivity {

    private EditText mUsername;
    private EditText mPassword;
    private EditText mKeyStorePass;
    private TextView error;
    private Button mButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mButton = findViewById(R.id.btnLogin);
        mUsername=findViewById(R.id.username);
        mPassword=findViewById(R.id.password);
        mKeyStorePass=findViewById(R.id.keyStorePass);
        error=findViewById(R.id.wrongCredentials);

        SharedPreferences preferences=getSharedPreferences("KeyStore",Context.MODE_PRIVATE);
        String password=preferences.getString("keyStorePass","");
        System.out.println("PASSWORD:"+password);
        if(password.isEmpty()){
            mKeyStorePass.setVisibility(View.VISIBLE);
        }



    }
    public void login(View view){
        // new DocumentsThread(null,this).start();
        new LoginTask(mUsername,mPassword,mKeyStorePass,error,this).execute();
    }


}

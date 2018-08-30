package org.unibl.etf.sni.authenticator;


        import android.content.Context;
        import android.content.SharedPreferences;
        import android.os.Environment;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.text.InputType;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.TextView;

        import com.obsez.android.lib.filechooser.ChooserDialog;

        import org.unibl.etf.sni.task.LoginTask;

        import java.io.File;


public class LoginActivity extends AppCompatActivity {

    private EditText mUsername;
    private EditText mPassword;
    private EditText mKeyStorePass;
    private EditText mCertPath;
    private TextView error;
    private Button mButton;


    private void updateLabel(String text){
        mCertPath.setText(text.toString());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mButton = findViewById(R.id.btnLogin);
        mUsername=findViewById(R.id.username);
        mPassword=findViewById(R.id.password);
        mKeyStorePass=findViewById(R.id.keyStorePass);
        mCertPath=findViewById(R.id.certPath);
        error=findViewById(R.id.wrongCredentials);
        mCertPath.setKeyListener(null);
        mCertPath.setInputType(InputType.TYPE_NULL);
        final SharedPreferences preferences=getSharedPreferences("KeyStore",Context.MODE_PRIVATE);
        mCertPath.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String path= Environment.getExternalStorageDirectory().getAbsolutePath();
                new ChooserDialog().with(LoginActivity.this)
                        .withStartFile(path)
                        .withChosenListener(new ChooserDialog.Result() {
                            @Override
                            public void onChoosePath(String path, File pathFile) {
                                updateLabel(path);
                            }
                        })
                        .build()
                        .show();
            }
        });

        String password=preferences.getString("keyStorePass","");
        String certPath=preferences.getString("certPath","");
        if(password.isEmpty()){
            mKeyStorePass.setVisibility(View.VISIBLE);
        }
        if(certPath.isEmpty()){
            mCertPath.setVisibility(View.VISIBLE);

        }



    }
    public void login(View view){

        if(mCertPath.getVisibility()==View.VISIBLE && mKeyStorePass.getVisibility()==View.VISIBLE){
            if(mCertPath.getText().toString().isEmpty() || mKeyStorePass.getText().toString().isEmpty()){
                error.setText(getString(R.string.wrong_certificate));
                error.setVisibility(View.VISIBLE);
                return;
            }
        }
        error.setVisibility(View.GONE);
        LoginTask task=new LoginTask(mUsername,mPassword,mKeyStorePass,mCertPath,error,this);
        task.execute();

    }


}

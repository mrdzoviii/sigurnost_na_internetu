package org.unibl.etf.sni.authenticator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.unibl.etf.sni.rest.Api;
import org.unibl.etf.sni.rest.RetrofitClientInstance;
import org.unibl.etf.sni.rest.beans.AndroidBean;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        mButton.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View view)
                    {
                        String username=mUsername.getText().toString();
                        String password=mPassword.getText().toString();
                        String token;
                        Api service = RetrofitClientInstance.getRetrofitInstance().create(Api.class);
                        Call<String> call = service.auth(new AndroidBean(username,password));
                        call.enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                                System.out.println(response.body());
                                String token=response.body();
                                if("NOT_SENT".equals(token)){
                                    System.out.println("NOT LOGGED");
                                }else{
                                    //logged
                                    mUsername.getText().clear();
                                }
                                mPassword.getText().clear();
                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {
                                t.getCause().printStackTrace();
                                Toast.makeText(LoginActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                });
    }

}

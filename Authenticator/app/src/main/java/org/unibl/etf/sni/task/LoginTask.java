package org.unibl.etf.sni.task;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import org.unibl.etf.sni.authenticator.VerifyActivity;
import org.unibl.etf.sni.bean.SessionBean;
import org.unibl.etf.sni.rest.Api;
import org.unibl.etf.sni.rest.RetrofitClientInstance;
import org.unibl.etf.sni.rest.beans.AndroidBean;
import org.unibl.etf.sni.util.Hash;
import java.io.IOException;
import java.util.Date;
import okhttp3.ResponseBody;
import retrofit2.Call;

public class LoginTask extends AsyncTask<Void,Void,Void> {
    private  EditText mUsername;
    private  EditText mPassword;
    private EditText mKeyStorePass;
    private  Activity activity;
    private  TextView wrong;
    private String token;

    public LoginTask(EditText mUsername, EditText mPassword,EditText mKeyStorePass,TextView wrong,Activity ctx) {
        this.mUsername = mUsername;
        this.mPassword = mPassword;
        this.mKeyStorePass=mKeyStorePass;
        this.wrong=wrong;
        this.activity = ctx;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        SharedPreferences preferences=activity.getSharedPreferences("KeyStore",Context.MODE_PRIVATE);
        String password=preferences.getString("keyStorePass","");
        if(password.isEmpty()){
            preferences.edit().putString("keyStorePass",mKeyStorePass.getText().toString()).apply();
            mKeyStorePass.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        wrong.setVisibility(View.INVISIBLE);
        if(token!=null) {
            if ("NOT_SENT".equals(token)) {
                wrong.setVisibility(View.VISIBLE);
                return;
            } else {
                SessionBean userBean=new SessionBean();
                userBean.setUsername(mUsername.getText().toString());
                userBean.setPassword(Hash.sha512Hash(mPassword.getText().toString()));
                userBean.setToken(token.split("#")[0]);
                userBean.setValidUntil(new Date(Long.valueOf(token.split("#")[1])));
                userBean.setAdmin(Boolean.valueOf(token.split("#")[2]));
                userBean.setPid(token.split("#")[3]);
                mUsername.getText().clear();
                wrong.setVisibility(View.INVISIBLE);
                Intent intent = new Intent(activity, VerifyActivity.class);
                intent.putExtra("BEAN",userBean);
                activity.startActivity(intent);
            }
            mPassword.getText().clear();
        }else{
            SharedPreferences preferences=activity.getSharedPreferences("KeyStore",Context.MODE_PRIVATE);
            String password=preferences.getString("keyStorePass","");
            if(!password.isEmpty()){
                preferences.edit().putString("keyStorePass","").apply();
                mKeyStorePass.setVisibility(View.VISIBLE);
            }
            Toast.makeText(activity,"Something went wrong",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected Void doInBackground(Void... voids) {
        Api service = RetrofitClientInstance.getApiService(activity.getBaseContext());
        final Call<ResponseBody> call = service.auth(new AndroidBean(mUsername.getText().toString(), mPassword.getText().toString()));
        try {
            token = call.execute().body().string();

        } catch (IOException e) {
            e.printStackTrace();

        }
        return null;
    }
}

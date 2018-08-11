package org.unibl.etf.sni.task;


import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.EditText;
import android.widget.Toast;

import org.unibl.etf.sni.authenticator.VerifyActivity;
import org.unibl.etf.sni.rest.Api;
import org.unibl.etf.sni.rest.RetrofitClientInstance;
import org.unibl.etf.sni.rest.beans.AndroidBean;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;

public class VerifyTask extends AsyncTask<Void,Void,Void> {
    private EditText mToken;
    private Activity activity;
    private String token;

    public VerifyTask(EditText mToken, Activity activity, String token) {
        this.mToken = mToken;
        this.activity = activity;
        this.token = token;
    }

    public EditText getmToken() {
        return mToken;
    }

    public void setmToken(EditText mToken) {
        this.mToken = mToken;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        String code=mToken.getText().toString();
        if(token.equals(code)){
            Toast.makeText(activity,"Confirmed",Toast.LENGTH_LONG).show();
        }else{
            mToken.getText().clear();
            Toast.makeText(activity,"Wrong code",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected Void doInBackground(Void... voids) {
        return null;
    }
}

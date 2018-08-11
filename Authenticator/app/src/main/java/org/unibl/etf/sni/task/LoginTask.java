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

public class LoginTask extends AsyncTask<Void,Void,Void> {
    private EditText mUsername;
    private EditText mPassword;
    private Activity activity;
    private String token;

    public LoginTask(EditText mUsername, EditText mPassword,Activity ctx) {
        this.mUsername = mUsername;
        this.mPassword = mPassword;

        this.activity = ctx;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }





    public EditText getmUsername() {
        return mUsername;
    }

    public void setmUsername(EditText mUsername) {
        this.mUsername = mUsername;
    }

    public EditText getmPassword() {
        return mPassword;
    }

    public void setmPassword(EditText mPassword) {
        this.mPassword = mPassword;
    }



    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        System.out.println("HELLO");
        if(token!=null) {
            if ("NOT_SENT".equals(token)) {
                System.out.println("NOT LOGGED");
                Toast.makeText(activity,"Wrong credentials",Toast.LENGTH_LONG);
                return;
            } else {
                //logged
                mUsername.getText().clear();
                System.out.println("TOKEN:" + token);
                Intent intent = new Intent(activity, VerifyActivity.class);
                intent.putExtra("TOKEN", token);
                activity.startActivity(intent);
            }
            mPassword.getText().clear();
        }else{
            System.out.println("TOKEN NOT NULL");
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

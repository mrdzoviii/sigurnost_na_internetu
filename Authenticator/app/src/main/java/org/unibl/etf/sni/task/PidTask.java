package org.unibl.etf.sni.task;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.unibl.etf.sni.adapter.DocumentAdapter;
import org.unibl.etf.sni.authenticator.LoginActivity;
import org.unibl.etf.sni.authenticator.VerifyActivity;
import org.unibl.etf.sni.bean.SessionBean;
import org.unibl.etf.sni.rest.Api;
import org.unibl.etf.sni.rest.RetrofitClientInstance;
import org.unibl.etf.sni.rest.beans.AndroidBean;
import org.unibl.etf.sni.rest.beans.DocumentsBean;
import org.unibl.etf.sni.rest.beans.RestBean;
import org.unibl.etf.sni.soap.model.WebServiceBean;
import org.unibl.etf.sni.util.Hash;

import java.io.IOException;
import java.util.Date;

import okhttp3.ResponseBody;
import retrofit2.Call;

public class PidTask extends AsyncTask<Void,Void,Void> {
    private Activity context;
    private String json;
    private SessionBean bean;
    private RecyclerView recyclerView;
    private DocumentsBean documents;
    private String pid;
    private boolean flag=false;


    public PidTask(SessionBean bean,String pid,RecyclerView view, Activity context){
        this.bean=bean;
        this.pid=pid;
        this.context = context;
        this.recyclerView=view;


    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        if(flag){
            if("INVALID_BEAN".equals(json)) {
                Toast.makeText(context,"Invalid bean",Toast.LENGTH_LONG).show();
                Intent intent=new Intent(context, LoginActivity.class);
                context.startActivity(intent);
                context.finish();
                return;
            } else if ("NOT_AUTHORIZED".equals(json)) {
                Toast.makeText(context, "Not authorized for this operation", Toast.LENGTH_LONG).show();
                return;
            }
        }
        if(documents!=null) {
                recyclerView.setHasFixedSize(true);
                LinearLayoutManager layoutManager = new LinearLayoutManager(context);
                recyclerView.setLayoutManager(layoutManager);
                DocumentAdapter mAdapter = new DocumentAdapter(documents,context);
                mAdapter.setHasStableIds(true);
                recyclerView.setAdapter(mAdapter);
        }else{
            Toast.makeText(context,"Something went wrong",Toast.LENGTH_LONG);
        }
    }

    @Override
    protected Void doInBackground(Void... voids) {
        Api service = RetrofitClientInstance.getApiService(context);
        WebServiceBean wb=new WebServiceBean();
        wb.setUsername(bean.getUsername());
        if(new Date().before(bean.getValidUntil())) {
            wb.setWsHash(Hash.sha512Hash(bean.getPassword() + bean.getToken()));
            RestBean restBean=new RestBean();
            restBean.setServiceBean(wb);
            restBean.setUid(pid);
            final Call<ResponseBody> call = service.getDocumentsByUid(restBean);
            try {
                json = call.execute().body().string();
                if("INVALID_BEAN".equals(json)) {
                    flag=true;
                    return null;

                } else if ("NOT_AUTHORIZED".equals(json)) {
                    flag=true;
                    return null;
                }
                else{
                    Gson gson=new Gson();
                    documents=gson.fromJson(json,DocumentsBean.class);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}

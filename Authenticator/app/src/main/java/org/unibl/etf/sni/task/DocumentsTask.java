package org.unibl.etf.sni.task;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;
import android.widget.Toast;


import com.google.gson.Gson;

import org.unibl.etf.sni.adapter.DocumentAdapter;
import org.unibl.etf.sni.authenticator.LoginActivity;
import org.unibl.etf.sni.bean.SessionBean;
import org.unibl.etf.sni.model.Document;
import org.unibl.etf.sni.rest.beans.DocumentsBean;
import org.unibl.etf.sni.soap.ApiService;
import org.unibl.etf.sni.soap.model.WebServiceBean;
import org.unibl.etf.sni.util.Hash;

import java.util.Date;

public class DocumentsTask extends AsyncTask<Void,Void,Void>{
    private String json;
    private String date;
    private SessionBean bean;
    private RecyclerView recyclerView;
    private DocumentsBean documents;
    private Boolean flag=false;

    private Activity context;

    public DocumentsTask(SessionBean bean,String date,RecyclerView view, Activity context){
        this.bean=bean;
        this.date=date;
        this.context = context;
        this.recyclerView=view;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        if(flag){
            if("INVALID_BEAN".equals(json)) {
                Intent intent=new Intent(context, LoginActivity.class);
                context.startActivity(intent);
                context.finish();
                return;
            }else if("NOT_AUTHORIZED".equals(json)) {
                Toast.makeText(context,"Not authorized",Toast.LENGTH_LONG).show();
                return;
            }
        }
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        DocumentAdapter mAdapter = new DocumentAdapter(documents,context);
        mAdapter.setHasStableIds(true);
        recyclerView.setAdapter(mAdapter);
    }

    public String getJson() {
        return json;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        ApiService apiService = new ApiService(context);
        WebServiceBean wb=new WebServiceBean();
        wb.setUsername(bean.getUsername());
        if(new Date().before(bean.getValidUntil())) {
            wb.setWsHash(Hash.sha512Hash(bean.getPassword() + bean.getToken()));
            System.out.println(wb.getWsHash());
            json = apiService.getDocumentsByDate(wb.getUsername(), wb.getWsHash(),date);
            System.out.println("ANSWER:" + json);
            if("INVALID_BEAN".equals(json)) {
              flag=true;
              return null;
            }else if("NOT_AUTHORIZED".equals(json)) {
                flag=true;
                return null;
            }
            else{
                Gson gson=new Gson();
                documents=gson.fromJson(json,DocumentsBean.class);
            }
        }
        else{
            Intent intent=new Intent(context, LoginActivity.class);
            context.startActivity(intent);
            context.finish();
        }
        return null;
    }
}

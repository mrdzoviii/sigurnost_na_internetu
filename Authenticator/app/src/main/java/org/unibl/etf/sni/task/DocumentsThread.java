package org.unibl.etf.sni.task;

import android.content.Context;


import org.unibl.etf.sni.soap.ApiService;
import org.unibl.etf.sni.soap.model.WebServiceBean;

import java.util.Date;

public class DocumentsThread extends Thread {
    private String json;
    private String wsHash;

    private Context context;

    public DocumentsThread(String wsHash, Context context){
        this.wsHash = wsHash;
        this.context = context;
    }

    @Override
    public void run() {
        ApiService apiService = new ApiService(context);
        WebServiceBean wb=new WebServiceBean();
        wb.setUsername("jovan");
        wb.setWsHash("e0c21ee2cf5b82a67a59385fad7a53be0d3c11625d9f8b75ea6229165c78fcf1c7c02f4031a89bdedc9603f113fc3906abbbb8affbd9cb56abd5291a9e772749\n");
        json = apiService.getDocumentsByDate(wb.getUsername(),wb.getWsHash(), new Date().getTime());
        System.out.println("ANSWER:"+json);
    }

    public String getJson() {
        return json;
    }
}

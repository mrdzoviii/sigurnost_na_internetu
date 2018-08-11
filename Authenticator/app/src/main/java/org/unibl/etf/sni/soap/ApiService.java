package org.unibl.etf.sni.soap;

//------------------------------------------------------------------------------
// <wsdl2code-generated>
//    This code was generated by http://www.wsdl2code.com version  2.6
//
// Date Of Creation: 5/17/2018 2:48:17 PM
//    Please dont change this code, regeneration will override your changes
//</wsdl2code-generated>
//
//------------------------------------------------------------------------------
//
//This source code was auto-generated by Wsdl2Code  Version
//

import android.content.Context;
import android.os.AsyncTask;

import org.ksoap2.HeaderProperty;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpsTransportSE;
import org.unibl.etf.sni.authenticator.R;
import org.unibl.etf.sni.soap.model.WebServiceBean;
import org.unibl.etf.sni.util.MyCAVerification;


import java.util.Date;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

public class ApiService {

    private String NAMESPACE ="http://soap.sni.etf.unibl.org";
    private String host;
    private int port;
    private String file;
    private int timeOut;
    private IWsdl2CodeEvents eventHandler;
    private Context context;
    
    public ApiService(Context context){
        this.context = context;
        this.host = context.getResources().getString(R.string.host);
        this.port = Integer.parseInt(context.getResources().getString(R.string.port));
        this.file = context.getResources().getString(R.string.soapServicePath);
    }
    
    public ApiService(IWsdl2CodeEvents eventHandler)
    {
        this.eventHandler = eventHandler;
    }

    public void setTimeOut(int seconds){
        this.timeOut = seconds * 1000;
    }
    public void getDocumentsByDateAsync(String username,String wsHash,long time) throws Exception {
        if (this.eventHandler == null)
            throw new Exception("Async Methods Requires IWsdl2CodeEvents");
        getDocumentsByDateAsync(username,wsHash,time, null);
    }
    
    public void getDocumentsByDateAsync(final String username,final String wsHash,final long time, final List<HeaderProperty> headers) throws Exception {
        
        new AsyncTask<Void, Void, String>(){
            @Override
            protected void onPreExecute() {
                eventHandler.Wsdl2CodeStartedRequest();
            };
            @Override
            protected String doInBackground(Void... params) {
                return getDocumentsByDate(username,wsHash,time, headers);
            }
            @Override
            protected void onPostExecute(String result)
            {
                eventHandler.Wsdl2CodeEndedRequest();
                if (result != null){
                    eventHandler.Wsdl2CodeFinished("getDocumentsByDate", result);
                }
            }
        }.execute();
    }
    
    public String getDocumentsByDate(String username,String wsHash, long time){
        return getDocumentsByDate(username, wsHash,time, null);
    }
    
    public String getDocumentsByDate(String username,String wsHash,long time, List<HeaderProperty> headers){
        SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        soapEnvelope.implicitTypes = true;
        soapEnvelope.dotNet = true;
        SoapObject soapReq = new SoapObject(NAMESPACE,"getDocumentsByDate");
        soapReq.addProperty("username",username);
        soapReq.addProperty("wsHash",wsHash);
        soapReq.addProperty("time",time);
        soapEnvelope.setOutputSoapObject(soapReq);
        HttpsTransportSE httpsTransportSE = new HttpsTransportSE(host,port,file,timeOut);
        HttpsURLConnection.setDefaultSSLSocketFactory(MyCAVerification.sslSocketFactory);
        HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String s, SSLSession sslSession) {
               return MyCAVerification.verifyCert(context,sslSession);
            }
        });
        try{
            if (headers!=null){
                httpsTransportSE.call(NAMESPACE+"/getDocumentsByName", soapEnvelope,headers);
            }else{
                httpsTransportSE.call(NAMESPACE+"/getDocumentsByName", soapEnvelope);
            }
            Object retObj = soapEnvelope.bodyIn;
            if (retObj instanceof SoapFault){
                SoapFault fault = (SoapFault)retObj;
                Exception ex = new Exception(fault.faultstring);
                if (eventHandler != null)
                    eventHandler.Wsdl2CodeFinishedWithException(ex);
            }else{
                SoapObject result=(SoapObject)retObj;
                if (result.getPropertyCount() > 0){
                    Object obj = result.getProperty(0);
                    if (obj != null && obj.getClass().equals(SoapPrimitive.class)){
                        SoapPrimitive j =(SoapPrimitive) obj;
                        String resultVariable = j.toString();
                        return resultVariable;
                    }else if (obj!= null && obj instanceof String){
                        String resultVariable = (String) obj;
                        return resultVariable;
                    }
                }
            }
        }catch (Exception e) {
            if (eventHandler != null)
                eventHandler.Wsdl2CodeFinishedWithException(e);
            e.printStackTrace();
        }
        return "Certificate not verified!";
    }
    
}

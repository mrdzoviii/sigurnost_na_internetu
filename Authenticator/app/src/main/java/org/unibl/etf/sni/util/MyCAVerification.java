package org.unibl.etf.sni.util;

import android.content.Context;
import android.content.res.AssetManager;

import org.spongycastle.asn1.sec.ECPrivateKeyStructure;
import org.unibl.etf.sni.authenticator.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;


public class MyCAVerification {
    private static SSLSocketFactory sslSocketFactory;
    private static TrustManager[] trustManagers;
    public static SSLSocketFactory getSslSocketFactory(final Context context){
        if(sslSocketFactory==null){
        trustManagers = new TrustManager[] {
                new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                    }

                    @Override
                    public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                    }

                    @Override
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return new java.security.cert.X509Certificate[]{};
                    }
                }
        };
        KeyManager[] keyManagers=null;
        try {
            KeyStore keyStore=KeyStore.getInstance("PKCS12");
            AssetManager assetManager=context.getAssets();
            InputStream is=assetManager.open("client.p12");
            char[] clientCertPassword=context.getString(R.string.client_cert_pass).toCharArray();
            keyStore.load(is,clientCertPassword);
            KeyManagerFactory kmf = KeyManagerFactory.getInstance("X509");
            kmf.init(keyStore, clientCertPassword);
            keyManagers = kmf.getKeyManagers();
        } catch (KeyStoreException | IOException | CertificateException | UnrecoverableKeyException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        SSLContext sslContext = null;
        try {
            sslContext = SSLContext.getInstance("SSL");
            sslContext.init(keyManagers, trustManagers, new java.security.SecureRandom());
        } catch (Exception e) {
            e.printStackTrace();
        }
        sslSocketFactory = sslContext.getSocketFactory();
        }
        return sslSocketFactory;
    }
    /*
    static{
        trustManagers = new TrustManager[] {
                new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                    }

                    @Override
                    public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                    }

                    @Override
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return new java.security.cert.X509Certificate[]{};
                    }
                }
        };
        SSLContext sslContext = null;
        try {
            sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustManagers, new java.security.SecureRandom());
        } catch (Exception e) {
            e.printStackTrace();
        }
        sslSocketFactory = sslContext.getSocketFactory();
    }
*/
    public static OkHttpClient getOkHttpClient(final Context context) {
        try {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.sslSocketFactory(getSslSocketFactory(context), (X509TrustManager)trustManagers[0]);
            builder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return verifyCert(context,session);
                }
            });

            OkHttpClient okHttpClient = builder.build();
            return okHttpClient;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean verifyCert(Context context, SSLSession sslSession){
        try {
            AssetManager assetManager = context.getAssets();
            InputStream in = assetManager.open("ca_cert.pem");

            CertificateFactory factory = CertificateFactory.getInstance("X.509");
            X509Certificate caCert = (X509Certificate) factory.generateCertificate(in);

            javax.security.cert.X509Certificate serverCert = sslSession.getPeerCertificateChain()[0];

            serverCert.verify(caCert.getPublicKey());
            serverCert.checkValidity();

            return true;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
}

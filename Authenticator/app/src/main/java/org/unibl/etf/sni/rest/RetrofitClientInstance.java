package org.unibl.etf.sni.rest;
import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.unibl.etf.sni.authenticator.LoginActivity;
import org.unibl.etf.sni.authenticator.R;
import org.unibl.etf.sni.util.MyCAVerification;

import java.io.IOException;

import okhttp3.CertificatePinner;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClientInstance {
    private static Retrofit retrofit;




    private static Retrofit getRetrofitInstance(Context context) {
        if (retrofit==null) {
            Interceptor interceptor = new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request newRequest = chain.request().newBuilder()
                            //This is where you would add headers if need be.
//                            .addHeader("Content-Type", "application/json")
                            .build();
                    return chain.proceed(newRequest);
                }
            };
            String BASE_URL=context.getString(R.string.serviceProtocol)+context.getString(R.string.host)+":"+context.getString(R.string.port)+
                    context.getString(R.string.restServicePath);
            System.out.println(BASE_URL);
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient.Builder httpClient = MyCAVerification.getOkHttpClient(context).newBuilder();
            httpClient.addInterceptor(interceptor);
            httpClient.addInterceptor(logging);
            retrofit= new Retrofit.Builder()
                    .baseUrl(BASE_URL)

                    .addConverterFactory(buildGsonConverterFactory())
                    .client(httpClient.build())
                    .build();
        }
        return retrofit;
    }
    public static Api getApiService(Context context) {
        return getRetrofitInstance(context).create(Api.class);
    }

    private static GsonConverterFactory buildGsonConverterFactory () {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.excludeFieldsWithoutExposeAnnotation();
        Gson myGson = gsonBuilder.create();
        return GsonConverterFactory.create(myGson);
    }
}

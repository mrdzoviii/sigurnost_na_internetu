package org.unibl.etf.sni.rest;

import org.unibl.etf.sni.rest.beans.AndroidBean;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface Api {
    @POST("android/auth")
    Call<ResponseBody> auth(@Body AndroidBean bean);
}

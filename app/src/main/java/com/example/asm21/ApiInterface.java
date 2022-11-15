package com.example.asm21;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIInterface {
    @Headers({
            "Connection: close"
    })
    @FormUrlEncoded
    @POST("sendPayLoad")
    Call<TripCloud> getTripInformation(@Field("jsonpayload") String value);
}

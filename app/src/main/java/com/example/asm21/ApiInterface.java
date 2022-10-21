package com.example.asm21;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {
    @GET("https://192.168.1.9:5001/weatherforecast/")
    Call<Post> getAllData();

    void getData();
}

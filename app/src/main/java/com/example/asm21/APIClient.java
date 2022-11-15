package com.example.asm21;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {
    public static Retrofit retrofit;
    public static final String BASE_URL ="https://cwservice1786.herokuapp.com/";
    public static Retrofit getRetrofitInstance(){
        if(retrofit== null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
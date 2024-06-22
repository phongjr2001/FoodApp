package com.example.noworderfoodapp.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class ApiClient {
    private static Retrofit retrofit;
    public static String BASE_URL = "http://192.168.205.1:8080";
    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
             retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL) // Base URL của API
                    .addConverterFactory(JacksonConverterFactory.create())// Converter để chuyển đổi JSON thành đối tượng Java
                    .build();
        }
        return retrofit;
    }
}
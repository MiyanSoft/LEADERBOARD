package com.miyansoft.leaderboard;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static final String BASE_URL = "https://docs.google.com/forms/d/e/";
    //retrofit singleton instance
    private static RetrofitClient retrofitInstance;
    private Retrofit retrofit;

    private RetrofitClient()    {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

    }
    public static synchronized RetrofitClient getInstance() {
        if(retrofitInstance == null) {
            retrofitInstance = new RetrofitClient();
        }
        return retrofitInstance;
    }

    public GadsApi getGadsApi(){
        return retrofit.create(GadsApi.class);
    }
}

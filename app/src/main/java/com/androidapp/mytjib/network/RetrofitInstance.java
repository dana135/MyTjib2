package com.androidapp.mytjib.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.moshi.Moshi;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {
    private static Retrofit retrofit;
    //private static final String BASE_URL = "https://api.themoviedb.org/3/movie/";
    private static final String BASE_URL = "http://192.168.44.1:8080/";
    //private static final String BASE_URL = "http://tjib-gateway.azuremicroservices.io/";


    public static Retrofit getRetrofitInstance() {

        if (retrofit == null) {

           // Moshi moshi = new Moshi.Builder().build();

            Gson gson = new GsonBuilder().serializeNulls().create();

            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(loggingInterceptor)
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(okHttpClient)
                    .build();
        }
        return retrofit;
    }
}

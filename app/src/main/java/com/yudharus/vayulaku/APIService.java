package com.yudharus.vayulaku;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
public class APIService {

    private static String BASE_URL = "https://vayulaku.yudharus.repl.co/api/";
    private static Retrofit retrofit;
    public static AllProductEndPoint allProduct(){
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(AllProductEndPoint.class);
    }
    public static ProductEndPointById productById(){
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(ProductEndPointById.class);
    }
}

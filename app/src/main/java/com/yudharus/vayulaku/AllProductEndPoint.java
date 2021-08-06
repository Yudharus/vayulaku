package com.yudharus.vayulaku;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface AllProductEndPoint {
    @GET("products")
    Call<List<Product>> getData();

}

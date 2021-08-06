package com.yudharus.vayulaku;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface ProductEndPointById {
    @GET
    Call<Product> getData(@Url String url);

}

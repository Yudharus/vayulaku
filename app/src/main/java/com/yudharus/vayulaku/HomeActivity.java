package com.yudharus.vayulaku;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {
    List<Product> products;
    List<Product> limitedProducts;
    List<Product> biasaProducts;

    RecyclerView limitedView, biasaView;
    ProductAdapter limitedAdapter, biasaAdapter;
    RecyclerView.LayoutManager layoutManagerBiasa, layoutManagerLimited;

    SearchView findProductView;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        biasaView = findViewById(R.id.new_stock_list);
        limitedView = findViewById(R.id.limited_stock_list);
        layoutManagerLimited = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        layoutManagerBiasa = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);


        GetData();
        findProductView = findViewById(R.id.find_product);
        findProductView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                newText = newText.toLowerCase();
                List<Product> filteredProducts = new ArrayList<>();
                for(Product product : biasaProducts){
                    if(product.getNama().contains(newText)) filteredProducts.add(product);
                }
                biasaAdapter.SetFilter(filteredProducts);
                return true;
            }
        });



    }

    private void GetData(){
        ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(HomeActivity.this);
        progressDoalog.setMax(100);
        progressDoalog.setMessage("Loading Products");
        progressDoalog.setTitle("Loading");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDoalog.show();


        APIService.allProduct().getData().enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                products = response.body();
                limitedProducts = new ArrayList<>();
                biasaProducts = new ArrayList<>();
                for(Product product : products){
                    if(product.getType().equalsIgnoreCase("biasa")) biasaProducts.add(product);
                    else limitedProducts.add(product);
                }
                limitedAdapter = new ProductAdapter(getApplicationContext(), limitedProducts);
                biasaAdapter = new ProductAdapter(getApplicationContext(), biasaProducts);

                biasaView.setLayoutManager(layoutManagerBiasa);
                limitedView.setLayoutManager(layoutManagerLimited);
                biasaView.setAdapter(biasaAdapter);
                limitedView.setAdapter(limitedAdapter);
                progressDoalog.dismiss();



            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Log.d("HOME", t.toString());
            }
        });
    }
}
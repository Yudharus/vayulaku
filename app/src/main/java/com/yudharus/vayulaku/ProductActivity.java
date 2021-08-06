package com.yudharus.vayulaku;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductActivity extends AppCompatActivity {
    TextView namaProduk, hargaProduk, sizeProduk, deskripsiProduk;
    Button button;
    Product produk;
    String produkId;
    String[] gambarUrls;

    ImageSlider slider;
    List<SlideModel> images;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        Intent intent = getIntent();
        produkId = intent.getStringExtra("productId");

        slider = findViewById(R.id.image_slider);
        images = new ArrayList<>();


        namaProduk = findViewById(R.id.nama_produk);
        hargaProduk = findViewById(R.id.harga_produk);
        sizeProduk = findViewById(R.id.size_produk);
        deskripsiProduk = findViewById(R.id.deskripsi_produk);
        button = findViewById(R.id.btn_beli);


        GetData("product/"+produkId);


    }


    void GetData(String url){
        APIService.productById().getData(url).enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                produk = response.body();
                Log.d("PRODUK", "" + produk.toString());
                String unformattedUrls = produk.getUrl();
                String[] formattedUrls = unformattedUrls.split(",");
                String baseUrl = "https://vayulaku.yudharus.repl.co/uploads/";
                List<String> urls = new ArrayList<>();
                for(String url : formattedUrls){
                    urls.add(baseUrl + url);
                }
                gambarUrls = new String[urls.size()];
                urls.toArray(gambarUrls);

                for(String url : gambarUrls){
                    images.add(new SlideModel(url, null));
                }

                namaProduk.setText(produk.getNama());
                hargaProduk.setText(produk.getHarga());
                sizeProduk.setText(produk.getUkuran());
                deskripsiProduk.setText(produk.getDeskripsi());
                button.setOnClickListener(v -> {
                    Uri uri = Uri.parse("https://wa.me/6281214720940?text=Halo Saya Mau Beli Barang Ini,Apakah Masih Ada? " + produk.getNama());
                    startActivity(new Intent(Intent.ACTION_VIEW, uri));
                });

                slider.setImageList(images);
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {

            }
        });
    }
}
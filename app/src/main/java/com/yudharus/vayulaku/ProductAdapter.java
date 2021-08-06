package com.yudharus.vayulaku;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.FriendView>{


    List<Product> products = new ArrayList<>();
    Context context;

    public ProductAdapter(Context activity, List<Product> products) {
        this.context = activity;
        this.products = products;
    }

    @NonNull
    @Override
    public FriendView onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_product,viewGroup,false);

        return new FriendView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FriendView holder, int position) {
        String unformattedUrls = products.get(position).getUrl();
        String[] formattedUrls = unformattedUrls.split(",");
        String baseUrl = "https://coba.wildanfikri.repl.co/uploads/";
        String url = baseUrl + formattedUrls[0];

        Picasso.get().load(url).into(holder.image);
        holder.nama.setText(products.get(position).getNama());
        holder.harga.setText(products.get(position).getHarga());

        holder.image.setOnClickListener(v -> {
            Intent intent = new Intent(context, ProductActivity.class);
            intent.putExtra("productId", products.get(position).getId());
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return products.size();
    }



    public class FriendView extends RecyclerView.ViewHolder{


        public ImageView image;
        public TextView nama, harga;
        public FriendView(@NonNull View itemView) {
            super(itemView);
            image = (ImageView)itemView.findViewById(R.id.product_image);
            nama = (TextView)itemView.findViewById(R.id.product_name);
            harga = (TextView)itemView.findViewById(R.id.product_cost);
        }


    }

    public void SetFilter(List<Product> filterlist){
        products = new ArrayList<>();
        products.addAll(filterlist);
        notifyDataSetChanged();
    }

}

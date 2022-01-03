package com.example.book_app_store.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.book_app_store.Modals.Model_Home;
import com.example.book_app_store.Activity.Product_Details_Activity;
import com.example.book_app_store.R;


public class Home_Adapter1_books extends RecyclerView.Adapter<Home_Adapter1_books.holder> {


    Model_Home[] data;
    Context context;

    public Home_Adapter1_books(Model_Home[] data, Context context) {
        this.data = data;
        this.context = context;
    }
//
//    public Home_Adapter1_books(Object data) {
//    }

    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.single_book_item1, parent, false);
        return new holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull holder holder, @SuppressLint("RecyclerView") int position) {

        holder.product_name.setText(data[holder.getAdapterPosition()].getProduct_Title());
        holder.product_price.setText(data[holder.getAdapterPosition()].getProduct_Price());
        holder.product_desc.setText(data[holder.getAdapterPosition()].getProduct_Desc());
        holder.product_location.setText(data[holder.getAdapterPosition()].getProduct_Location());

        String product_image = "http://192.168.43.70/learnphp/Book_App_Store/product_images/prodpuct_book_images/"
                + data[holder.getAdapterPosition()].getProduct_Image();

        Glide.with(context).load("http://192.168.43.70/learnphp/Book_App_Store/product_images/prodpuct_book_images/"
                + data[holder.getAdapterPosition()].getProduct_Image())
                .into(holder.product_iamge);
        //below is used for click event in recyclerview in home fragment
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Product_Details_Activity.class);
                intent.putExtra("product_name", data[holder.getAdapterPosition()].getProduct_Title());
                intent.putExtra("product_price", data[holder.getAdapterPosition()].getProduct_Price());
                intent.putExtra("product_desc", data[holder.getAdapterPosition()].getProduct_Desc());
                intent.putExtra("product_location", data[holder.getAdapterPosition()].getProduct_Location());
                intent.putExtra("product_image", product_image);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.length;
    }

    public static class holder extends RecyclerView.ViewHolder {

        ImageView product_iamge;
        TextView product_name, product_price, product_desc, product_location;

        public holder(@NonNull View itemView) {
            super(itemView);

            product_iamge = itemView.findViewById(R.id.product_image);
            product_name = itemView.findViewById(R.id.product_name);
            product_price = itemView.findViewById(R.id.product_price);
            product_desc = itemView.findViewById(R.id.product_desc);
            product_location = itemView.findViewById(R.id.product_location);
        }
    }


}

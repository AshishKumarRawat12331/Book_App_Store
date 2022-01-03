package com.example.book_app_store.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.example.book_app_store.R;
import com.example.book_app_store.databinding.ActivityProductDetailsBinding;

public class Product_Details_Activity extends AppCompatActivity {

    ActivityProductDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        Intent i = getIntent();
        if (i.getExtras() != null) {

            binding.productName.setText(i.getStringExtra("product_name"));
            binding.productPrice.setText(i.getStringExtra("product_price"));
            binding.productDesc.setText(i.getStringExtra("product_desc"));
            binding.detailsDescription.setText(i.getStringExtra("product_desc"));
            binding.productLocation.setText(i.getStringExtra("product_location"));

            String image = i.getStringExtra("product_image");

            Glide.with(getApplicationContext())
                    .load(image)
                    .fitCenter()
                    .into(binding.productImage);
        }

    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


}
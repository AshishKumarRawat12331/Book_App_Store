package com.example.book_app_store.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.book_app_store.R;
import com.example.book_app_store.databinding.ActivitySelectProductPriceBinding;

public class Select_Product_Price extends AppCompatActivity {

    ActivitySelectProductPriceBinding binding;

    String title="", desc="", product_image="", price="", location="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySelectProductPriceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Upload some details");

        /// get intent of SELECT IMAGE ACTIVITY
        Intent i = getIntent();
        if (i.getExtras()!=null){
            title = i.getStringExtra("product_title");
            desc = i.getStringExtra("product_desc");
            product_image = i.getStringExtra("prodctImageInString");
        }else {
            Toast.makeText(Select_Product_Price.this, "No Data.", Toast.LENGTH_SHORT).show();
        }


        binding.nextBtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                send_to_post_your_product_Activity();
            }
        });

    }

    private void send_to_post_your_product_Activity() {
        if (!validate_Price_edit_text() | !validate_Location_edit_text()) {
            return;
        }
        price = binding.priceEdittext.getEditText().getText().toString();
        location = binding.locationEdittext.getEditText().getText().toString();

        Intent intent = new Intent(Select_Product_Price.this, Post_Your_Product.class);
        intent.putExtra("product_title", title);
        intent.putExtra("product_desc", desc);
        intent.putExtra("product_image", product_image);
        intent.putExtra("product_price", price);
        intent.putExtra("user_location", location);
        startActivity(intent);
        Toast.makeText(Select_Product_Price.this, title+desc, Toast.LENGTH_SHORT).show();
        Animatoo.animateSlideLeft(Select_Product_Price.this);

    }

    ////////////  VALIDATE TITLE EDIT TEXT INPUT ////////

    private boolean validate_Price_edit_text() {
        String product_price = binding.priceEdittext.getEditText().getText().toString().trim();
        if (product_price.isEmpty()) {
            binding.priceEdittext.setError("field can't not be empty");
            return false;
        } else {
            binding.priceEdittext.setError(null);
            return true;
        }
    }

    ////////////  VALIDATE TITLE EDIT TEXT INPUT ////////

    private boolean validate_Location_edit_text() {
        String location = binding.locationEdittext.getEditText().getText().toString().trim();
        if (location.isEmpty()) {
            binding.locationEdittext.setError("field can't not be empty");
            return false;
        } else if (location.length() < 10) {
            binding.locationEdittext.setError("description is too short");
            return false;
        } else {
            binding.locationEdittext.setError(null);
            return true;
        }
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


}
package com.example.book_app_store.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.book_app_store.R;
import com.example.book_app_store.databinding.ActivityPostYourProductBinding;

import java.util.HashMap;
import java.util.Map;

public class Post_Your_Product extends AppCompatActivity {

    ActivityPostYourProductBinding binding;
    String title, desc, product_image, product_price, user_location;
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    boolean loggedIn;
    String userid;

    private static final String url =
            "http://192.168.43.70/learnphp/Book_App_Store/insert_product_details.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPostYourProductBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Check Your Details");

        sp = getSharedPreferences("Login", Context.MODE_PRIVATE);
        editor = sp.edit();
        loggedIn = sp.getBoolean("isloggedIn", false);
        if (loggedIn) {
            userid = sp.getString("user_id", "");
        }
        /// get intent of SELECT PRODUCT PRICE ACTIVITY
        Intent i = getIntent();
        if (i.getExtras() != null) {
            title = i.getStringExtra("product_title");
            desc = i.getStringExtra("product_desc");

            product_image = i.getStringExtra("product_image");
            product_price = i.getStringExtra("product_price");
            user_location = i.getStringExtra("user_location");
        } else {
            Toast.makeText(Post_Your_Product.this, "NO Data", Toast.LENGTH_SHORT).show();
        }

        binding.nextBtn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insert_Record();
            }
        });

    }

    /////////  Insert Record  //////////////////////////
    private void insert_Record() {

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(Post_Your_Product.this, response, Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Post_Your_Product.this, MainActivity.class));
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Post_Your_Product.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                params.put("userid", userid);
                params.put("product_title", title);
                params.put("product_desc", desc);
                params.put("product_image", product_image);
                params.put("product_price", product_price);
                params.put("user_location", user_location);
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(request);

    }
}
package com.example.book_app_store.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.book_app_store.R;
import com.example.book_app_store.databinding.ActivitySellProductBinding;

public class Sell_Product_Activity extends AppCompatActivity {

    ActivitySellProductBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySellProductBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Enter some details");

        binding.nextBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                send_to_second_Activity();
            }
        });
    }

    private void send_to_second_Activity() {
        if (!validate_Title_edit_text() | !validate_description_edit_text()) {
            return;
        }
        String title="", description="";
        title = binding.titleEdittext.getEditText().getText().toString().trim();
        description = binding.descriptionEdittext.getEditText().getText().toString().trim();

        Intent intent = new Intent(Sell_Product_Activity.this, Select_Product_Images.class);
        intent.putExtra("product_title", title);
        intent.putExtra("product_desc", description);
        startActivity(intent);
        Animatoo.animateSlideLeft(Sell_Product_Activity.this);

        Toast.makeText(Sell_Product_Activity.this, title + description, Toast.LENGTH_SHORT).show();
    }

    ////////////  VALIDATE TITLE EDIT TEXT INPUT ////////

    private boolean validate_Title_edit_text() {
        String title;
        title = binding.titleEdittext.getEditText().getText().toString().trim();
        if (title.isEmpty()) {
            binding.titleEdittext.setError("field can't not be empty");
            return false;
        } else if (title.length() > 50) {
            binding.titleEdittext.setError("title is too long");
            return false;
        } else if (title.length() < 10) {
            binding.titleEdittext.setError("title is too short");
            return false;
        } else {
            binding.titleEdittext.setError(null);
            return true;
        }
    }

    ////////////  VALIDATE TITLE EDIT TEXT INPUT ////////

    private boolean validate_description_edit_text() {
        String description;
        description = binding.descriptionEdittext.getEditText().getText().toString().trim();
        if (description.isEmpty()) {
            binding.descriptionEdittext.setError("field can't not be empty");
            return false;
        } else if (description.length() < 10) {
            binding.descriptionEdittext.setError("description is too short");
            return false;
        } else {
            binding.descriptionEdittext.setError(null);
            return true;
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
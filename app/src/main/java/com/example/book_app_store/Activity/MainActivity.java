package com.example.book_app_store.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.book_app_store.Fragments.Ads_Fragment;
import com.example.book_app_store.Fragments.Chat_Fragment;
import com.example.book_app_store.Fragments.Home_Fragment;
import com.example.book_app_store.Fragments.Login_OR_Register_Fragment;
import com.example.book_app_store.Fragments.Profile_Fragment;
import com.example.book_app_store.R;
import com.example.book_app_store.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    Toolbar toolbar;
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    boolean loggedIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.bottomNav.setBackground(null);
        binding.bottomNav.getMenu().getItem(2).setEnabled(false);

        binding.fabbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loggedIn == false) {
                    Toast.makeText(MainActivity.this, "Please login to upload ads", Toast.LENGTH_SHORT).show();
                } else {
                    startActivity(new Intent(MainActivity.this, Sell_Product_Activity.class));
                }
            }
        });
        ////// load all fragments when app is open
        bottomNavigation();
    }

    private void bottomNavigation() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragcontainer, new Home_Fragment());
        transaction.commit();
        sp = getSharedPreferences("Login", MODE_PRIVATE);
        editor = sp.edit();
        loggedIn = sp.getBoolean("isloggedIn", false);

        binding.bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

                switch (item.getItemId()) {
                    case R.id.home:
                        transaction.replace(R.id.fragcontainer, new Home_Fragment());
                        break;
                    case R.id.chat:
                        transaction.replace(R.id.fragcontainer, new Chat_Fragment());
                        break;
                    case R.id.ads:
                        transaction.replace(R.id.fragcontainer, new Ads_Fragment());
                        break;
                    case R.id.profile:
                        sp = getSharedPreferences("Login", MODE_PRIVATE);
                        editor = sp.edit();
                        if (loggedIn) {
                            transaction.replace(R.id.fragcontainer, new Profile_Fragment());
                            break;
                        } else {
                            transaction.replace(R.id.fragcontainer, new Login_OR_Register_Fragment());
                            break;
                        }

                }
                transaction.commit();
                return true;
            }
        });

    }

    /////// this function is used for when you press back then home fragment is load
    @Override
    public void onBackPressed() {
        if (binding.bottomNav.getSelectedItemId() == R.id.home) {
            super.onBackPressed();
            finish();
        } else {
            binding.bottomNav.setSelectedItemId(R.id.home);
        }

    }
}
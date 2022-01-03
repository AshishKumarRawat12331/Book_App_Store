package com.example.book_app_store.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.book_app_store.Activity.MainActivity;
import com.example.book_app_store.R;
import com.example.book_app_store.databinding.FragmentProfileBinding;

public class Profile_Fragment extends Fragment {


    public Profile_Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    FragmentProfileBinding binding;
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    boolean loggedIn;
    String email, password, username, profileimage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater, container, false);

        sp = getActivity().getSharedPreferences("Login", Context.MODE_PRIVATE);
        editor = sp.edit();
        loggedIn = sp.getBoolean("isloggedIn", false);

        if (loggedIn) {
            email = sp.getString("email", "");
            password = sp.getString("password", "");

            username = sp.getString("username", "");
            profileimage = sp.getString("profileimage", "");

            binding.loginTextView.setText(username);
            Glide.with(getActivity()).
                    load("http://192.168.43.70/learnphp/Book_App_Store/profile_pictures/" + profileimage)
                    .placeholder(R.drawable.user_image)
                    .into(binding.profileImage);

        }
        binding.logoutTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logout();
            }
        });
        return binding.getRoot();
    }

    private void Logout() {
        editor.clear();
        editor.apply();
        startActivity(new Intent(getActivity(), MainActivity.class));
        getActivity().finish();

    }

}
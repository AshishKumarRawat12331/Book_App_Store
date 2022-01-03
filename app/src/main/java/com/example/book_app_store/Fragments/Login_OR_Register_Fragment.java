package com.example.book_app_store.Fragments;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.book_app_store.Activity.Login_Activity;
import com.example.book_app_store.Activity.Signup_Activity;
import com.example.book_app_store.databinding.FragmentLoginORRegisterBinding;

public class Login_OR_Register_Fragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    FragmentLoginORRegisterBinding binding;
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    boolean loggedIn;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentLoginORRegisterBinding.inflate(inflater, container, false);

        sp = getActivity().getSharedPreferences("Login", MODE_PRIVATE);
        editor = sp.edit();
        loggedIn = sp.getBoolean("isloggedIn", false);
        if (loggedIn) {
         getActivity().finish();
        }

        binding.loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Login_Activity.class));
            }
        });
        binding.signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Signup_Activity.class));
            }
        });


        return binding.getRoot();
    }
}
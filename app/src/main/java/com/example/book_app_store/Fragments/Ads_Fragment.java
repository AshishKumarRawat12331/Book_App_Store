package com.example.book_app_store.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.book_app_store.Adapters.Fragment_Loads_Adapter;
import com.example.book_app_store.R;
import com.example.book_app_store.databinding.FragmentAdsBinding;

public class Ads_Fragment extends Fragment {


    public Ads_Fragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    FragmentAdsBinding binding;
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    boolean loggedIn;
    String userid;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAdsBinding.inflate(inflater, container, false);

        sp = getActivity().getSharedPreferences("Login", Context.MODE_PRIVATE);
        editor = sp.edit();
        loggedIn = sp.getBoolean("isloggedIn", false);
        if (loggedIn) {
            userid = sp.getString("user_id", "");
            binding.textView.setText(userid);
        }

        binding.viewpager1.setAdapter(new Fragment_Loads_Adapter(getFragmentManager()));
        binding.tablayoutAdsFragment.setupWithViewPager(binding.viewpager1);


        return binding.getRoot();
    }
}
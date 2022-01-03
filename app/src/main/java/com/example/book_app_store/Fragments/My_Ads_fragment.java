package com.example.book_app_store.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.book_app_store.Adapters.Home_Adapter1_books;
import com.example.book_app_store.Adapters.MyAds_Adapter1;
import com.example.book_app_store.Modals.Model_Home;
import com.example.book_app_store.databinding.FragmentMyAdsFragmentBinding;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.Map;

public class My_Ads_fragment extends Fragment {

    public My_Ads_fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    FragmentMyAdsFragmentBinding binding;
    String userid;
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    boolean loggedIn;

    private static final String url = "http://192.168.43.70/learnphp/Book_App_Store/fetch_products_of_login_user.php";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentMyAdsFragmentBinding.inflate(inflater, container, false);
        /// call this functionto fetch all products from table

        sp = getActivity().getSharedPreferences("Login", Context.MODE_PRIVATE);
        editor = sp.edit();
        loggedIn = sp.getBoolean("isloggedIn", false);
        if (loggedIn) {
            userid = sp.getString("user_id", "");
        }

        fetch_all_products();
        return binding.getRoot();
    }

    private void fetch_all_products() {

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    ////// convert json into array of objects using GSON library
                    GsonBuilder gsonBuilder = new GsonBuilder();
                    Gson gson = gsonBuilder.create();

                    Model_Home[] data = gson.fromJson(response, Model_Home[].class);
                    if (data.length!=0){
                        binding.myAdsRec1.setVisibility(View.VISIBLE);
                        binding.textView.setVisibility(View.INVISIBLE);
                        MyAds_Adapter1 ads_adapter1 = new MyAds_Adapter1(data, getActivity());
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                        binding.myAdsRec1.setLayoutManager(linearLayoutManager);
                        binding.myAdsRec1.setAdapter(ads_adapter1);
                    }else {
                        binding.textView.setVisibility(View.VISIBLE);
                        binding.myAdsRec1.setVisibility(View.GONE);
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                params.put("userid", userid);
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.add(request);

    }

}
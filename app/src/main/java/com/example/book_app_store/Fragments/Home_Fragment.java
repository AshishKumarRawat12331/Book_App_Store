package com.example.book_app_store.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.book_app_store.Adapters.Home_Adapter1_books;
import com.example.book_app_store.Modals.Model_Home;
import com.example.book_app_store.databinding.FragmentHomeBinding;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public class Home_Fragment extends Fragment {


    public Home_Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    FragmentHomeBinding binding;
    private static final String url = "http://192.168.43.70/learnphp/Book_App_Store/fetch_all_products.php";


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false);

        binding.shimmer.startShimmer();
        /// call this functionto fetch all products from table
        fetch_all_products();
        return binding.getRoot();
    }

    private void fetch_all_products() {

        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    binding.shimmer.stopShimmer();
                    binding.shimmer.setVisibility(View.GONE);
                    binding.homeRecyclerview.setVisibility(View.VISIBLE);
                    ////// convert json into array of objects using GSON library
                    GsonBuilder gsonBuilder = new GsonBuilder();
                    Gson gson = gsonBuilder.create();

                    Model_Home[] data = gson.fromJson(response, Model_Home[].class);
                    if (data.length!=0){
                        binding.textView.setVisibility(View.INVISIBLE);
                        Home_Adapter1_books home_adapter1 = new Home_Adapter1_books(data, getContext());
                        GridLayoutManager manager = new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false);
                        binding.homeRecyclerview.setLayoutManager(manager);
                        binding.homeRecyclerview.setAdapter(home_adapter1);


                    }else {
                        binding.textView.setVisibility(View.VISIBLE);
                        binding.shimmer.stopShimmer();
                        binding.shimmer.setVisibility(View.GONE);
//                        binding.homeRecyclerview.setVisibility(View.VISIBLE);
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
        });

        RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.add(request);

    }


}
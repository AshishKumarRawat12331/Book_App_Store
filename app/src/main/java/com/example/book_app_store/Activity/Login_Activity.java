package com.example.book_app_store.Activity;

import androidx.appcompat.app.AppCompatActivity;

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
import com.example.book_app_store.databinding.ActivityLoginBinding;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login_Activity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private static final String url = "http://192.168.43.70/learnphp/Book_App_Store/login.php";
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    boolean loggedIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sp = getSharedPreferences("Login", MODE_PRIVATE);
        editor = sp.edit();

        loggedIn = sp.getBoolean("isloggedIn", false);
        if (loggedIn) {
            startActivity(new Intent(Login_Activity.this, MainActivity.class));
            finish();
        }

        binding.loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login();
            }
        });

    }

    private void Login() {

        String email, password;
        email = binding.editEmail.getText().toString().trim();
        password = binding.editPass.getText().toString().trim();

        if (email.equals("") || password.equals("")) {
            Toast.makeText(Login_Activity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
        } else {
            StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String result = jsonObject.getString("msg");
                        if (result.equals("Login Successfully")) {
                            editor.putString("email", email);
                            editor.putString("password", password);
                            editor.putBoolean("isloggedIn", true);
//                            editor.apply();

                            ///////// fetch json data of login user from API
                            JSONArray array = jsonObject.getJSONArray("record");
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject data = array.getJSONObject(i);
                                String user_id = data.getString("id");
                                String username = data.getString("Username");
                                String image = data.getString("Profile_Image");
                                editor.putString("user_id", user_id);
                                editor.putString("username", username);
                                editor.putString("profileimage", image);
                                editor.apply();
                            }
                            //////////////////////////////////
                            Toast.makeText(Login_Activity.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Login_Activity.this, MainActivity.class));
                            finish();

                        } else {
                            Toast.makeText(Login_Activity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(Login_Activity.this, "Error: " + e.toString(), Toast.LENGTH_SHORT).show();

                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(Login_Activity.this, "Error: " + error.toString(), Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    Map<String, String> params = new HashMap<String, String>();
                    params.put("email", email);
                    params.put("password", password);

                    return params;
                }
            };

            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
            queue.add(request);
        }

    }
}
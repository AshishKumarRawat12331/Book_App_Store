package com.example.book_app_store.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.book_app_store.databinding.ActivitySignupBinding;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class Signup_Activity extends AppCompatActivity {

    private ActivitySignupBinding binding;
    private int GALLERY_CODE = 1;
    private Bitmap bitmap;
    private boolean cameraBtnisClicked = false;
    String encodedImageString = "" , selPic="", username, email, password, phone;
    Map<String, String> params = new HashMap<String, String>();

    private static final String url = "http://192.168.43.70/learnphp/Book_App_Store/signup.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                insert_Record();
                username = binding.editUsername.getText().toString().trim();
                email = binding.editEmail.getText().toString().trim();
                password = binding.editPass.getText().toString().trim();
                phone = binding.editPhone.getText().toString().trim();
                if (selPic.equals("")){
                    params.put("profile_image", "user_image.png");
                    insert_Record();
                }else {
                    encodeImageToString();
                }


            }
        });
        binding.cameraIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cameraBtnisClicked = true;
                runTimePermissions();
            }
        });
    }

    /////// Permissions with Dextre library
    private void runTimePermissions() {
        Dexter.withContext(getApplicationContext())
                .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        Intent galleryIntent = new Intent();
                        galleryIntent.setType("image/*");
                        galleryIntent.setAction(Intent.ACTION_PICK);
                        startActivityForResult(Intent.createChooser(galleryIntent, "Choose Profile Picture"), GALLERY_CODE);
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();

    }

    /////// onActivityResult for gallery intent//////
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == GALLERY_CODE) {
                onSelectFromGallery(data, requestCode, resultCode);
            }
        }
    }

    ////////// onSelectFromGallery function /////
    private void onSelectFromGallery(Intent data, int requestCode, int resultCode) {
        Bitmap bm = null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
                bitmap = bm;
                selPic="selected";
                binding.profileImage.setImageBitmap(bm);
            } catch (Exception e) {
            }
        }
    }

    //////// Encode image to string and then send to the server///////
    public void encodeImageToString() {

        class c1 extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected String doInBackground(Void... voids) {
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                //before upload image to server you have to compress the image size
                bitmap.compress(Bitmap.CompressFormat.JPEG, 80, stream);
                byte[] byte_arr = stream.toByteArray();
                //Encode image to string
                encodedImageString = Base64.encodeToString(byte_arr, 0);
                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                params.put("profile_image", encodedImageString);
                insert_Record();
            }
        }
        c1 ob = new c1();
        ob.execute();

    }

    /////////  Insert Record  //////////////////////////
    private void insert_Record() {

        if (username.isEmpty() || email.isEmpty() || password.isEmpty() || phone.isEmpty()) {
            Toast.makeText(Signup_Activity.this, "Fields can not be blank", Toast.LENGTH_SHORT).show();
        } else {
            StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String result = jsonObject.getString("msg");
                        if (result.equals("Account created")) {
                            Toast.makeText(Signup_Activity.this, "Successfully Registered", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Signup_Activity.this, Login_Activity.class));
                        }
                        Toast.makeText(Signup_Activity.this, result, Toast.LENGTH_SHORT).show();

                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(Signup_Activity.this, "Failed to Register" + e.toString(), Toast.LENGTH_SHORT).show();

                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(Signup_Activity.this, "Failed to Register" + error.toString(), Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    params.put("username", username);
                    params.put("email", email);
                    params.put("password", password);
                    params.put("phone", phone);
                    return params;
                }
            };

            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
            queue.add(request);

        }
    }
}
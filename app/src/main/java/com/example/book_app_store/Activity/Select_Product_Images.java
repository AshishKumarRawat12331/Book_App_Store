package com.example.book_app_store.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.bumptech.glide.Glide;
import com.example.book_app_store.R;
import com.example.book_app_store.databinding.ActivitySelectProductImagesBinding;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.ByteArrayOutputStream;

public class Select_Product_Images extends AppCompatActivity {

    ActivitySelectProductImagesBinding binding;
    Uri imageUri;
    private int GALLERY_CODE = 12;
    private Bitmap bitmap;
    String encodedImageString = "", selPic = "";
    String title = "", desc = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySelectProductImagesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Upload Product image");

        /// get intent of SELL PRODUCT ACTIVITY
        Intent i = getIntent();
        if (i.getExtras() != null) {

            title = i.getStringExtra("product_title");
            desc = i.getStringExtra("product_desc");

        } else {
            Toast.makeText(Select_Product_Images.this, "No Data.", Toast.LENGTH_SHORT).show();
        }

        binding.selectImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runTimePermissions();
            }
        });

        binding.nextBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selPic.equals("")) {
                    Toast.makeText(Select_Product_Images.this, "Please select product image", Toast.LENGTH_SHORT).show();
                } else {
                    encodeImageToString();
                }
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

                        ImagePicker.with(Select_Product_Images.this)
//                        .crop()                    //Crop image(Optional), Check Customization for more option
                        .compress(1024)			//Final image size will be less than 1 MB(Optional)
//                        .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                                .start(GALLERY_CODE);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null) {
            if (resultCode == Activity.RESULT_OK) {
                if (requestCode == GALLERY_CODE) {
                    onSelectFromGallery(data, requestCode, resultCode);
                }
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
                selPic = "selected";
//                binding.profileImage.setImageBitmap(bm);
                Glide.with(this).load(bm)
                        .placeholder(R.drawable.svg_img_1)
                        .centerCrop()
                        .placeholder(R.drawable.ic_launcher_background).into(binding.productImageview);
            } catch (Exception e) {
                e.printStackTrace();
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
                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream);
                byte[] byte_arr = stream.toByteArray();
                //Encode image to string
                encodedImageString = Base64.encodeToString(byte_arr, 0);
                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Intent intent = new Intent(Select_Product_Images.this, Select_Product_Price.class);
                intent.putExtra("prodctImageInString", encodedImageString);
                intent.putExtra("product_title", title);
                intent.putExtra("product_desc", desc);
                startActivity(intent);
                Toast.makeText(Select_Product_Images.this, title + desc, Toast.LENGTH_SHORT).show();
                Animatoo.animateSlideLeft(Select_Product_Images.this);
            }
        }
        c1 ob = new c1();
        ob.execute();

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


}
package com.example.SelfieApp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.widget.ImageView;

public class ImageDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_detail);



        Intent intent = getIntent();
        String bitmapString = (String) intent.getExtras().getString("BitmapImage");
        Bitmap bitmap = getBitmapFromEncodedString(bitmapString);
        ImageView imageView = findViewById(R.id.imageView);
        imageView.setImageBitmap(bitmap);
    }

    private Bitmap getBitmapFromEncodedString(String encodedString){

        byte[] arr = Base64.decode(encodedString, Base64.URL_SAFE);

        Bitmap img = BitmapFactory.decodeByteArray(arr, 0, arr.length);

        return img;

    }


}

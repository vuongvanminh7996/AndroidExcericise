package com.example.nationinfo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.bumptech.glide.request.RequestOptions;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class DisplayMessageActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);

        Log.d("Error","http://www.geonames.org/flags/x/"+MainActivity.countryCode_message+".gif");

//
//        new DownloadImageTask((ImageView) findViewById(R.id.countryImage))
//                        .execute("https://media.giphy.com/media/SKGo6OYe24EBG/giphy.gif");

//                .load("http://www.geonames.org/flags/x/"+MainActivity.countryCode_message+".gif")
        String string = "http://www.geonames.org/flags/x/"+MainActivity.countryCode_message+".gif";

        ImageView imageView = findViewById(R.id.countryImage);
//
//        Glide.with(this)
//                .load(string)
//                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
//                .into(imageView);

        GlideUrl glideUrl = new GlideUrl("http://www.geonames.org/flags/x/"+MainActivity.countryCode_message+".gif", new LazyHeaders.Builder()
                .addHeader("User-Agent", "Mozilla/5.0")
                .build());

        Glide.with(this)
                .load(glideUrl)
                .into(imageView);



        TextView name = findViewById(R.id.countryName);
        name.setText("Name: " + MainActivity.countryName_message);

        TextView population = findViewById(R.id.countryPopulation);
        population.setText("Population: " + MainActivity.countryPopulation_message);

        TextView area = findViewById(R.id.countryArea);
        area.setText("Area: " + MainActivity.countryArea_message);


    }
    public Bitmap getbmpfromURL(String surl){
        try {
            URL url = new URL(surl);
            HttpURLConnection urlcon = (HttpURLConnection) url.openConnection();
            urlcon.setDoInput(true);
            urlcon.connect();
            InputStream in = urlcon.getInputStream();
            Bitmap mIcon = BitmapFactory.decodeStream(in);
            return  mIcon;
        } catch (Exception e) {
            Log.e("Error", ""+e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}

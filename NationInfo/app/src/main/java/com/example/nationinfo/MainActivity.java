package com.example.nationinfo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView textResult;
    LinearLayout verticalLayout;


    public static String countryCode_message = "";
    public static ImageView countryImage_message;
    public static String countryName_message = "";
    public static String countryPopulation_message = "";
    public static String countryArea_message = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (isNetwork(getApplicationContext())){

            Toast.makeText(getApplicationContext(), "Internet Connected", Toast.LENGTH_SHORT).show();

        } else {

            Toast.makeText(getApplicationContext(), "Internet Is Not Connected", Toast.LENGTH_SHORT).show();
        }
//

        new GeoNamesTask().execute();
    }

    public boolean isNetwork(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }

    private class GeoNamesTask extends AsyncTask<String, Void, String> {
//        TextView tResult;


        JSONArray jsonFile;

        public GeoNamesTask(){
//            tResult = vResult;
//            tResult.setText("");
        }

        @Override
        protected String doInBackground(String... params) {

            /*
            Do not use the 'demo' account for your app or your tests.
            It is only meant for the sample links on the documentation pages.
            Create your own account instead.
             */


            String queryString =
                    "http://api.geonames.org/countryInfoJSON?formatted=true&username=minhgnob&country";
//                    "http://api.geonames.org/countryCode?lat=" + params[0]
//                            + "&lng=" + params[1] + "&username=minhgnob";

            String s = "";
            String result = "";
            List<String> items = new ArrayList<>();

            try {
                s = sendQuery(queryString);
                JSONObject jsonObj = new JSONObject(s);
                JSONArray geonames = jsonObj.getJSONArray("geonames");
                jsonFile = geonames;

                // looping through All Contacts
                for (int i = 0; i < geonames.length(); i++) {
                    JSONObject c = geonames.getJSONObject(i);
                    items.add(c.getString("countryName"));
//                    result += c.get("countryName") + " || ";
                }

                s = result;
                final List<String> completedItem = items;
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        ListView listView = new ListView(getBaseContext());
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(getBaseContext(),
                                android.R.layout.simple_list_item_1, completedItem);
                        listView.setAdapter(adapter);
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                final String text = (String) ((TextView)view).getText();
                                for(int i =0;i<jsonFile.length();i++)
                                {
                                    JSONObject c = null;
                                    try {
                                        c = jsonFile.getJSONObject(i);
                                        if(c.getString("countryName") == text)
                                        {
                                            Intent intent = new Intent(getBaseContext(), DisplayMessageActivity.class);
                                            countryCode_message = c.getString("countryCode").toLowerCase();
//                                             new DownloadImageTask(countryImage_message )
//                                                                    .execute("http://www.geonames.org/flags/x/"+MainActivity.countryCode_message+".gif");
                                                    countryName_message = text;
                                            countryPopulation_message = "" + c.getInt("population");
                                            countryArea_message = "" + c.getDouble("areaInSqKm");
                                            startActivity(intent);
                                            break;
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        });
                        setContentView(listView);
                    }
                });


            } catch (IOException | JSONException e) {
                e.printStackTrace();
                s = e.getMessage();

            }
            return s;
        }

        @Override
        protected void onPostExecute(String s) {
        }


        private String sendQuery(String query) throws IOException {
            String result = "";

            URL searchURL = new URL(query);

            HttpURLConnection httpURLConnection = (HttpURLConnection)searchURL.openConnection();
            if(httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK){
                InputStreamReader inputStreamReader = new InputStreamReader(httpURLConnection.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(
                        inputStreamReader,
                        8192);

                String line = null;
                while((line = bufferedReader.readLine()) != null){
                    result += line;
                }

                bufferedReader.close();
            }

            return result;
        }
    }
}

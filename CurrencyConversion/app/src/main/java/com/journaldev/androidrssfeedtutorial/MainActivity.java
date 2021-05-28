package com.journaldev.androidrssfeedtutorial;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


//    ArrayList<String> rssLinks = new ArrayList<>();
    public static HashMap<String,String> map = new HashMap<>();
    public static Map<String,String> ReverseMap= new HashMap<>();
    public static EditText mainEditText;
    public static TextView subEditText;
    public static Spinner mainSpinner;
    public static Spinner subSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnRediff = findViewById(R.id.btnRediff);
//        Button btnCinemaBlend = findViewById(R.id.btnCinemaBlend);
        btnRediff.setOnClickListener(this);
//        btnCinemaBlend.setOnClickListener(this);


        map.put("USD","United States Dollar");
        map.put("EUR","Euro");
        map.put("GBP","British Pound");
        map.put("JPY","Japanese Yen");
        map.put("CHF","Swiss Franc");
        map.put("CAD","Canadian Dollar");
        map.put("AUD","Australian Dollar");
        map.put("CNY","Chinese Yuan");
        map.put("HKD","Hong Kong Dollar");
        map.put("RUB","Russian Rouble");
        map.put("MXN","Mexican Peso");
        map.put("ZAR","South African Rand");
        map.put("ALL","Albanian Lek");
        map.put("DZD","Algerian Dinar");
        map.put("ARS","Argentine Peso");
        map.put("AWG","Aruba Florin");
        map.put("BSD","Bahamian Dollar");
        map.put("BHD","Bahraini Dinar");
        map.put("BDT","Bangladesh Taka");
        map.put("BBD","Barbados Dollar");
        map.put("BYR","Belarus Ruble");
        map.put("BZD","Belize Dollar");
        map.put("BMD","Bermuda Dollar");
        map.put("BTN","Bhutan Ngultrum");
        map.put("BOB","Bolivian Boliviano");
        map.put("BWP","Botswana Pula");
        map.put("BRL","Brazilian Real");
        map.put("BND","Brunei Dollar");
        map.put("BGN","Bulgarian Lev");
        map.put("BIF","Burundi Franc");
        map.put("KHR","Cambodia Riel");
        map.put("CVE","Cape Verde Escudo");
        map.put("KYD","Cayman Islands Dollar");
        map.put("XOF","CFA Franc (BCEAO)");
        map.put("XAF","CFA Franc (BEAC)");
        map.put("CLP","Chilean Peso");
        map.put("COP","Colombian Peso");
        map.put("KMF","Comoros Franc");
        map.put("CRC","Costa Rica Colon");
        map.put("HRK","Croatian Kuna");
        map.put("CUP","Cuban Peso");
        map.put("CZK","Czech Koruna");
        map.put("DKK","Danish Krone");
        map.put("DJF","Djibouti Franc");
        map.put("DOP","Dominican Peso");
        map.put("XCD","East Caribbean Dollar");
        map.put("EGP","Egyptian Pound");
        map.put("SVC","El Salvador Colon");
        map.put("EEK","Estonian Kroon");
        map.put("ETB","Ethiopian Birr");
        map.put("FKP","Falkland Islands Pound");
        map.put("FJD","Fiji Dollar");
        map.put("IDR","Indonesian Rupiah");
        map.put("INR","Indian Rupee");
        map.put("GMD","Gambian Dalasi");
        map.put("GTQ","Guatemala Quetzal");
        map.put("GNF","Guinea Franc");
        map.put("GYD","Guyana Dollar");
        map.put("HTG","Haiti Gourde");
        map.put("HNL","Honduras Lempira");
        map.put("HUF","Hungarian Forint");
        map.put("ISK","Iceland Krona");
        map.put("IRR","Iran Rial");
        map.put("IQD","Iraqi Dinar");
        map.put("ILS","Israeli Shekel");
        map.put("JMD","Jamaican Dollar");
        map.put("JOD","Jordanian Dinar");
        map.put("KZT","Kazakhstan Tenge");
        map.put("KES","Kenyan Shilling");
        map.put("KRW","Korean Won");
        map.put("KWD","Kuwaiti Dinar");
        map.put("LAK","Lao Kip");
        map.put("LVL","Latvian Lat");
        map.put("LBP","Lebanese Pound");
        map.put("LSL","Lesotho Loti");
        map.put("LRD","Liberian Dollar");
        map.put("LYD","Libyan Dinar");
        map.put("LTL","Lithuanian Lita");
        map.put("MOP","Macau Pataca");
        map.put("MKD","Macedonian Denar");
        map.put("MWK","Malawi Kwacha");
        map.put("MYR","Malaysian Ringgit");
        map.put("MVR","Maldives Rufiyaa");
        map.put("MRO","Mauritania Ougulya");
        map.put("MUR","Mauritius Rupee");
        map.put("MDL","Moldovan Leu");
        map.put("MNT","Mongolian Tugrik");
        map.put("MAD","Moroccan Dirham");
        map.put("MMK","Myanmar Kyat");
        map.put("NAD","Namibian Dollar");
        map.put("NPR","Nepalese Rupee");
        map.put("ANG","Neth Antilles Guilder");
        map.put("NZD","New Zealand Dollar");
        map.put("NIO","Nicaragua Cordoba");
        map.put("NGN","Nigerian Naira");
        map.put("KPW","North Korean Won");
        map.put("NOK","Norwegian Krone");
        map.put("OMR","Omani Rial");
        map.put("XPF","Pacific Franc");
        map.put("PKR","Pakistani Rupee");
        map.put("PAB","Panama Balboa");
        map.put("PGK","Papua New Guinea Kina");
        map.put("PYG","Paraguayan Guarani");
        map.put("PEN","Peruvian Nuevo Sol");
        map.put("PHP","Philippine Peso");
        map.put("PLN","Polish Zloty");
        map.put("QAR","Qatar Rial");
        map.put("RON","Romanian New Leu");
        map.put("RWF","Rwanda Franc");
        map.put("WST","Samoa Tala");
        map.put("STD","Sao Tome Dobra");
        map.put("SAR","Saudi Arabian Riyal");
        map.put("SCR","Seychelles Rupee");
        map.put("SLL","Sierra Leone Leone");
        map.put("SGD","Singapore Dollar");
        map.put("SKK","Slovak Koruna");
        map.put("SBD","Solomon Islands Dollar");
        map.put("SOS","Somali Shilling");
        map.put("LKR","Sri Lanka Rupee");
        map.put("SHP","St Helena Pound");
        map.put("SDG","Sudanese Pound");
        map.put("SZL","Swaziland Lilageni");
        map.put("SEK","Swedish Krona");
        map.put("SYP","Syrian Pound");
        map.put("THB","Thai Baht");
        map.put("TRY","Turkish Lira");
        map.put("TWD","Taiwan Dollar");
        map.put("TZS","Tanzanian Shilling");
        map.put("TOP","Tongan paʻanga");
        map.put("TTD","Trinidad Tobago Dollar");
        map.put("TND","Tunisian Dinar");
        map.put("AED","UAE Dirham");
        map.put("UGX","Ugandan Shilling");
        map.put("UAH","Ukraine Hryvnia");
        map.put("UYU","Uruguayan New Peso");
        map.put("VUV","Vanuatu Vatu");
        map.put("VND","Vietnam Dong");
        map.put("YER","Yemen Riyal");
        map.put("ZMK","Zambian Kwacha");
        map.put("ZWD","Zimbabwe dollar");
        map.put("VEF","Venezuelan Bolivar");
        map.put("UZS","Uzbekistan Sum");
        map.put("KGS","Kyrgyzstan Som");
        map.put("GHS","Ghanaian Cedi");
        map.put("BYN","Belarusian ruble");
        map.put("AFN","Afghan afghani");
        map.put("AOA","Angolan kwanza");
        map.put("AMD","Armenian dram");
        map.put("AZN","Azerbaijani manat");
        map.put("BAM","Convertible mark");
        map.put("CDF","Congolese franc");
        map.put("ERN","Eritrean nakfa");
        map.put("GEL","Georgian lari");
        map.put("MGA","Malagasy ariary");
        map.put("MZN","Mozambican metical");
        map.put("RSD","Serbian dinar");
        map.put("SRD","Surinamese dollar");
        map.put("TJS","Tajikistani somoni");
        map.put("TMT","Turkmenistan manat");
        map.put("ZMW","Zambian kwacha");

        mainEditText = findViewById(R.id.editTextMain);
        subEditText = findViewById(R.id.resultTextView);
        mainSpinner = findViewById(R.id.MainSpinner);
        subSpinner = findViewById(R.id.SubSpinner);

//        TreeMap<String, String> sorted = new TreeMap<>(map);
//        sorted.putAll(map);
        map =sortByValues(map);

        List<String> arraySpinner = new ArrayList<>();
        List<String> arraySpinner1 = new ArrayList<>();


//        rssLinks.add("https://aud.fxexchangerate.com/rss.xml");
        for(Map.Entry<String,String> mapEach : map.entrySet()){
            arraySpinner.add(mapEach.getValue());
            arraySpinner1.add(mapEach.getValue());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, arraySpinner1);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mainSpinner.setAdapter(adapter);
        subSpinner.setAdapter(adapter1);
    }

    private static HashMap sortByValues(HashMap map) {
        List list = new LinkedList(map.entrySet());
        // Defined Custom Comparator here
        Collections.sort(list, new Comparator() {
            public int compare(Object o1, Object o2) {
                return ((Comparable) ((Map.Entry) (o1)).getValue())
                        .compareTo(((Map.Entry) (o2)).getValue());
            }
        });

        // Here I am copying the sorted list in HashMap
        // using LinkedHashMap to preserve the insertion order
        HashMap sortedHashMap = new LinkedHashMap();
        for (Iterator it = list.iterator(); it.hasNext();) {
            Map.Entry entry = (Map.Entry) it.next();
            sortedHashMap.put(entry.getKey(), entry.getValue());
        }
        return sortedHashMap;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnRediff:
                if(TextUtils.isEmpty(mainEditText.getText()))
                {break;}
                ReverseMap = invertMap(map);
                String link = "https://" + ReverseMap.get(mainSpinner.getSelectedItem().toString()) + ".fxexchangerate.com/rss.xml";
//                Intent intent =new Intent(MainActivity.this, RSSFeedActivity.class);
//                intent.putExtra("rssLink", link);
//                startActivity(intent);
                new LoadRSSFeedItems().execute(link);

                break;

//            case R.id.btnCinemaBlend:
//                startActivity(new Intent(MainActivity.this, RSSFeedActivity.class).putExtra("rssLink", rssLinks.get(1)));
//                break;
        }
    }

    public static String formatValue(double value) {
        int power;
        String suffix = " kmbt";
        String formattedNumber = "";

        NumberFormat formatter = new DecimalFormat("#,###.#");
        power = (int)StrictMath.log10(value);
        value = value/(Math.pow(10,(power/3)*3));
        formattedNumber=formatter.format(value);
        formattedNumber = formattedNumber + suffix.charAt(power/3);
        return formattedNumber.length()>4 ?  formattedNumber.replaceAll("\\.[0-9]+", "") : formattedNumber;
    }

    public static <K,V> Map<V, K> invertMap(Map<K, V> toInvert) {
        Map<V, K> result = new HashMap<V, K>();
        for(K k: toInvert.keySet()){
            result.put(toInvert.get(k), k);
        }
        return result;
    }

    public class LoadRSSFeedItems extends AsyncTask<String, String, String> {
//        ArrayList<HashMap<String, String>> rssItemList = new ArrayList<>();

        RSSParser rssParser = new RSSParser();

        List<RSSItem> rssItems = new ArrayList<>();
        private  String TAG_TITLE = "title";
        private  String TAG_LINK = "link";


        @Override
        protected String doInBackground(String... args) {
            // rss link url
            String rss_url = args[0];

            // list of rss items
            rssItems = rssParser.getRSSFeedItems(rss_url);

            // looping through each item
                // creating new HashMap
//                if (item.link.toString().equals(""))
//                    break;
//                HashMap<String, String> map = new HashMap<String, String>();

                // adding each child node to HashMap key => value

//                if(item.title.contains("Algerian Dinar"))
//                {
//                    String givenDateString = item.pubdate.trim();
//                    SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z");
//                    try {
//                        Date mDate = sdf.parse(givenDateString);
//                        SimpleDateFormat sdf2 = new SimpleDateFormat("EEEE, dd MMMM yyyy - hh:mm a", Locale.US);
//                        item.pubdate = sdf2.format(mDate);
//
//                    } catch (ParseException e) {
//                        e.printStackTrace();
//
//                    }
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        for (RSSItem item : rssItems) {

                        if(item.description.contains(mainSpinner.getSelectedItem().toString())
                                && item.description.contains(subSpinner.getSelectedItem().toString()))
                        {
                            Log.e("SubSpinner",subSpinner.getSelectedItem().toString());
                            Log.e("BeforeTrim",item.description);
                            String text = item.description;
                            text=text.replace(subSpinner.getSelectedItem().toString(),"");
                            text= text.substring(text.lastIndexOf("="));
                            text=text.replace(" ","");
                            text=text.replace("=","");

                            Log.e("AfterTrim",text +"\n");
                            Double value = Double.parseDouble(mainEditText.getText().toString()) * Double.parseDouble(text);
                            Log.e("Value",formatValue((value)));
                            subEditText.setText(formatValue((value)));
                            break;
                        }
                        }
                    }
                });




//                map.put(TAG_TITLE, item.title);
//                map.put(TAG_LINK, item.link);
////                    map.put(TAG_PUB_DATE, item.pubdate); // If you want parse the date
//
//                // adding HashList to ArrayList
//                rssItemList.add(map);
////                }



            return null;
        }
    }
}

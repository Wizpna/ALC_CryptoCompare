package com.example.promise.alc_cryptocompare;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.promise.alc_cryptocompare.R;
import com.example.promise.alc_cryptocompare.BtUser_Convert;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Promise on 11/2/2017.
 */

public class Bitcoin_Card extends AppCompatActivity {
    //Symbols & Price Url  Declarations
    static final String DATA = "AUD,USD,EUR,NGN,CNY,CAD,GBP,CHF,SEK,AZN,JPY,HKD,CRC,TRY,GGP,MXN,KRW,ZAR,BRL,PHP";
    static final String API_URL = "https://min-api.cryptocompare.com/data/price?fsym=BTC&tsyms=" + DATA + "&10%20seconds";
    //Declaring an Spinner
    private Spinner spinner;
    //Declaring an textView and ImageView as public for insertion later in the program
    public static TextView text_price;
    public static ImageView imageView;
    public static ImageView btc_logo;
    public static Button btc_convert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bitcoin_card);
        spinner = (Spinner) findViewById(R.id.spinner);

        //Initializing TextViews= Current Price  and Coin Logo
        text_price = (TextView) findViewById(R.id.text_price);
        imageView = (ImageView) findViewById(R.id.currency_symb);
        btc_logo = (ImageView) findViewById(R.id.btc_logo);
        btc_convert = (Button) findViewById(R.id.btc_convert);

        //This Method Below works on the Spinner Adapter Setup
        setupSpinner();
        //Calling the Class extending AsynTask for Connection,Truncating, and setting up
        new RetrieveFeedTask().execute();

        //Calling the Conversion Activity by On Card Click
        Convert_Btc();
    }

    private void setupSpinner() {
        // Create adapter for spinner. The list options are from the String array it will use
        // the spinner will use the default layout
        ArrayAdapter selectedSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_currency, android.R.layout.simple_spinner_item);

        // Specifying dropdown layout style - simple list view with 1 item per line
        selectedSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        // Applying  the adapter to the spinner
        spinner.setAdapter(selectedSpinnerAdapter);


    }

    class RetrieveFeedTask extends AsyncTask<Void, Void, String> {

        //Calling the Method Doing Background to work on Connection and some other Logics
        protected String doInBackground(Void... urls) {

            try {

                URL url = new URL(API_URL);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                try {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line).append("\n");
                    }
                    bufferedReader.close();
                    return stringBuilder.toString();
                } finally {
                    urlConnection.disconnect();
                }
            } catch (Exception e) {
                Log.e("ERROR", e.getMessage(), e);
                return null;
            }
        }

        protected void onPostExecute(String response) {
            if (response == null) {
                response = "Problem Occured ,Check your Internet Connetion";
            }
            //LogCat to Display the Current State of Connection
            Log.i("INFO", response);

            //Truncation and Trimming of the Json Object & Setting up the Spinner onItemSelected
            try {
                final JSONObject object = (JSONObject) new JSONTokener(response).nextValue();

                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String selection = (String) parent.getItemAtPosition(position);
                        if (!TextUtils.isEmpty(selection)) {
                            if (selection.equals(getString(R.string.aud))) {
                                String aud = null;
                                //Wrapped up each String of Symbols Call, in Try and Catch for cases of exception
                                try {
                                    aud = object.getString("AUD");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                //Calling the Prices and setting the Symbols or sign
                                text_price.setText(aud);
                                imageView.setImageResource(R.drawable.usd);
                                new RetrieveFeedTask().execute();


                            } else if (selection.equals(getString(R.string.usd))) {

                                String usd = null;
                                try {
                                    usd = object.getString("USD");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                text_price.setText(usd);
                                imageView.setImageResource(R.drawable.usd);
                                new RetrieveFeedTask().execute();

                            } else if (selection.equals(getString(R.string.cny))) {

                                String cny = null;
                                try {
                                    cny = object.getString("CNY");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                text_price.setText(cny);
                                imageView.setImageResource(R.drawable.cny);
                                new RetrieveFeedTask().execute();

                            } else if (selection.equals(getString(R.string.cad))) {

                                String cad = null;
                                try {
                                    cad = object.getString("CAD");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                text_price.setText(cad);
                                imageView.setImageResource(R.drawable.cad);
                                new RetrieveFeedTask().execute();

                            } else if (selection.equals(getString(R.string.ngn))) {
                                String ngn = null;
                                try {
                                    ngn = object.getString("NGN");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                text_price.setText(ngn);
                                imageView.setImageResource(R.drawable.ngn);
                                new RetrieveFeedTask().execute();
                            } else if (selection.equals(getString(R.string.gbp))) {
                                String gbp = null;
                                try {
                                    gbp = object.getString("GBP");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                text_price.setText(gbp);
                                imageView.setImageResource(R.drawable.gbp);
                                new RetrieveFeedTask().execute();
                            } else if (selection.equals(getString(R.string.chf))) {
                                String chf = null;
                                try {
                                    chf = object.getString("CHF");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                text_price.setText(chf);
                                imageView.setImageResource(R.drawable.chf);
                                new RetrieveFeedTask().execute();
                            } else if (selection.equals(getString(R.string.sek))) {
                                String sek = null;
                                try {
                                    sek = object.getString("SEK");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                text_price.setText(sek);
                                imageView.setImageResource(R.drawable.sek);
                                new RetrieveFeedTask().execute();
                            } else if (selection.equals(getString(R.string.azn))) {
                                String azn = null;
                                try {
                                    azn = object.getString("AZN");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                text_price.setText(azn);
                                imageView.setImageResource(R.drawable.azn);
                                new RetrieveFeedTask().execute();
                            } else if (selection.equals(getString(R.string.jpy))) {
                                String jpy = null;
                                try {
                                    jpy = object.getString("JPY");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                text_price.setText(jpy);
                                imageView.setImageResource(R.drawable.jpy);
                                new RetrieveFeedTask().execute();
                            } else if (selection.equals(getString(R.string.hkd))) {
                                String hkd = null;
                                try {
                                    hkd = object.getString("HKD");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                text_price.setText(hkd);
                                imageView.setImageResource(R.drawable.hkd);
                                new RetrieveFeedTask().execute();
                            } else if (selection.equals(getString(R.string.crc))) {
                                String crc = null;
                                try {
                                    crc = object.getString("CRC");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                text_price.setText(crc);
                                imageView.setImageResource(R.drawable.crc);
                                new RetrieveFeedTask().execute();
                            } else if (selection.equals(getString(R.string.trysym))) {
                                String trysym = null;
                                try {
                                    trysym = object.getString("TRY");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                text_price.setText(trysym);
                                imageView.setImageResource(R.drawable.trysym);
                                new RetrieveFeedTask().execute();
                            } else if (selection.equals(getString(R.string.ggp))) {
                                String ggp = null;
                                try {
                                    ggp = object.getString("GGP");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                text_price.setText(ggp);
                                imageView.setImageResource(R.drawable.gbp);
                                new RetrieveFeedTask().execute();
                            } else if (selection.equals(getString(R.string.mxn))) {
                                String mxn = null;
                                try {
                                    mxn = object.getString("MXN");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                text_price.setText(mxn);
                                imageView.setImageResource(R.drawable.mxn);
                                new RetrieveFeedTask().execute();
                            } else if (selection.equals(getString(R.string.krw))) {
                                String krw = null;
                                try {
                                    krw = object.getString("KRW");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                text_price.setText(krw);
                                imageView.setImageResource(R.drawable.krw);
                                new RetrieveFeedTask().execute();
                            } else if (selection.equals(getString(R.string.zar))) {
                                String zar = null;
                                try {
                                    zar = object.getString("ZAR");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                text_price.setText(zar);
                                imageView.setImageResource(R.drawable.zar);
                                new RetrieveFeedTask().execute();
                            } else if (selection.equals(getString(R.string.brl))) {
                                String brl = null;
                                try {
                                    brl = object.getString("BRL");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                text_price.setText(brl);
                                imageView.setImageResource(R.drawable.brl);
                                new RetrieveFeedTask().execute();
                            } else if (selection.equals(getString(R.string.php))) {
                                String php = null;
                                try {
                                    php = object.getString("PHP");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                text_price.setText(php);
                                imageView.setImageResource(R.drawable.php);
                                new RetrieveFeedTask().execute();
                            } else if (selection.equals(getString(R.string.eur))) {
                                String eur = null;
                                try {
                                    eur = object.getString("EUR");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                text_price.setText(eur);
                                imageView.setImageResource(R.drawable.eur);
                                new RetrieveFeedTask().execute();
                            }
                        }
                    }

                    // Because AdapterView is an abstract class, onNothingSelected must be defined
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        parent.getItemAtPosition(0);
                    }
                });

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void Convert_Btc() {
        btc_convert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Bitcoin_Card.this, BtUser_Convert.class);
                startActivity(intent);
            }
        });

    }
}


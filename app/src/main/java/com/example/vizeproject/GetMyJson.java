package com.example.vizeproject;

import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class GetMyJson extends AsyncTask<String,Void,String> {

    @Override
    protected String doInBackground(String... strings) {
        StringBuilder sb= new StringBuilder("");
        try {
            URL url = new URL("https://raw.githubusercontent.com/robconery/json-sales-data/master/data/customers.json");
            HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
            con.connect();

            InputStream in = con.getInputStream();

            InputStreamReader reader= new InputStreamReader(in);

            int data= reader.read();
            while (data!=-1){

               sb.append((char)data) ;
               data=reader.read();
                }


        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        return sb.toString();
    }
}

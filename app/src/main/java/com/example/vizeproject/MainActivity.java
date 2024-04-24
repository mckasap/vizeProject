package com.example.vizeproject;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        lv= (ListView)findViewById(R.id.listView);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
              Customer c = (Customer)  adapterView.getItemAtPosition(i);
                Toast.makeText(MainActivity.this, c.getEmail() + " "+c.getId(),Toast.LENGTH_LONG).show();
            }
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        GetMyJson get= new GetMyJson();

        try {
            String str= get.execute().get();
            Log.d("JSON",str);
            doldur(str);

        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
    ArrayList<Customer> tumMusteriler= new ArrayList<>();

    public void doldur(String str) {
        tumMusteriler.clear();
        try {
            JSONArray array = new JSONArray(str);

            for(int i=0;i<array.length();i++){
                Customer c= new Customer();
                JSONObject jo= array.getJSONObject(i);
                c.setId(jo.getInt("id"));
                c.setFirst(jo.getString("first"));
                c.setLast(jo.getString("last"));
                c.setCompany(jo.getString("company"));
                c.setCreated_at(jo.getString("created_at"));
                c.setEmail(jo.getString("email"));
                c.setCountry(jo.getString("country"));

               tumMusteriler.add(c);

            }


            ArrayAdapter<Customer> adap = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1,tumMusteriler);
            lv.setAdapter(adap);
        } catch (JSONException e) {

            throw new RuntimeException(e);

        }


    }


}
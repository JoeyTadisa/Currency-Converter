package com.example.currencyconverter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.widget.Toolbar;

public class CurrencyListActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.currency_list_view);

        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        final ExchangeRateDatabase rateDatabase = new ExchangeRateDatabase();

        //ArrayAdapter<String> currencyListAdapter = new ArrayAdapter<>(this,
        //       R.layout.activity_currency_list, rateDatabase.getCurrencies());

        final CurrencyListAdapter currencyListAdapter1 = new CurrencyListAdapter(rateDatabase);

        ListView listView = findViewById(R.id.list_view);
        listView.setAdapter(currencyListAdapter1);
        currencyListAdapter1.notifyDataSetChanged();


        /*Extend CurrencyListActivity with a listener to select an entry.
        There's a fitting Intent that is created that starts the Activity for the map view.
         */
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String capitalCitySearchName = rateDatabase.getCapital(currencyListAdapter1.getItem(position).toString());

                Intent mapIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0'?q=" + capitalCitySearchName));
                startActivity(mapIntent);
            }
        });
    }
}
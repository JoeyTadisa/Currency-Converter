package com.example.currencyconverter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toolbar;

public class CurrencyListActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency_list);

        /*Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/

        ExchangeRateDatabase exchangeRateDatabase = new ExchangeRateDatabase();

        ArrayAdapter<String> currencyListAdapter = new ArrayAdapter<>(this,
                R.layout.activity_currency_list, exchangeRateDatabase.getCurrencies());

        ListView listView = findViewById(R.id.currency_list);
        listView.setAdapter(currencyListAdapter);
    }
}
package com.example.currencyconverter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class CurrencyListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency_list);

        ExchangeRateDatabase exchangeRateDatabase = new ExchangeRateDatabase();

        ArrayAdapter<String> currencyListAdapter = new ArrayAdapter<>(this,
                R.layout.activity_currency_list, exchangeRateDatabase.getCurrencies());

        ListView listView = (ListView) findViewById(R.id.currency_list);
        listView.setAdapter(currencyListAdapter);
    }
}
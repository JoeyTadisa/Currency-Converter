package com.example.currencyconverter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.widget.Toolbar;

public class CurrencyListActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency_list);

        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        ExchangeRateDatabase rateDatabase = new ExchangeRateDatabase();

        //ArrayAdapter<String> currencyListAdapter = new ArrayAdapter<>(this,
        //       R.layout.activity_currency_list, rateDatabase.getCurrencies());

        CurrencyListAdapter currencyListAdapter1 = new CurrencyListAdapter(rateDatabase);

        ListView listView = findViewById(R.id.list_view);
        listView.setAdapter(currencyListAdapter1);
    }
}
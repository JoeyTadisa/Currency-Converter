package com.example.currencyconverter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.widget.ShareActionProvider;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;

public class MainActivity extends AppCompatActivity {

    String response; //store the conversion result for sharing
    ExchangeRateUpdateRunnable eruRunnable;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent = new Intent(MainActivity.this, CurrencyListActivity.class);
        startActivity(intent);

        switch (item.getItemId()) {
            case R.id.my_menu:
                Log.i("Toolbar menu", "Menu was clicked");
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        final Spinner dropdown1, dropdown2;
        final EditText textInput;
        Button b1;
        final TextView currencyResult = findViewById(R.id.textViewResult);

        textInput = findViewById(R.id.textInputEditText);
        dropdown1 = findViewById(R.id.spinner3);

        dropdown2 = findViewById(R.id.spinner2);
        b1 = findViewById(R.id.button);

        final ExchangeRateDatabase currencyDropList = new ExchangeRateDatabase();
        final ExchangeRate exchangeRate = new ExchangeRate();

        Context context = null;
        context.getApplicationContext();
        eruRunnable = new ExchangeRateUpdateRunnable(context);
        Thread t = new Thread(eruRunnable);
        t.start();

        /*ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                R.layout.support_simple_spinner_dropdown_item, currencyDropList.getCurrencies()
        );*/
        CurrencyListAdapter cla = new CurrencyListAdapter(currencyDropList);
        /*dropdown1.setAdapter(adapter);
        dropdown2.setAdapter(adapter);*/
        dropdown1.setAdapter(cla);
        dropdown2.setAdapter(cla);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double result;
                double amount = Double.parseDouble(textInput.getText().toString());

                String d1 = dropdown1.getSelectedItem().toString();
                String d2 = dropdown2.getSelectedItem().toString();
                result = currencyDropList.convert(amount, d1, d2);

                currencyResult.setText(("Result: " + exchangeRate.roundValue(amount) + " " +
                        d1 + " is " + exchangeRate.roundValue(result) + " " + d2));

                response = "Result: " + exchangeRate.roundValue(amount) + " " +
                        d1 + " is " + exchangeRate.roundValue(result) + " " + d2;
            }
        });
    }
    //TODO Exercise 4.3: Include Share-Button
    //In this exercise you will extend the currency converter with a „share“-button.
    // On selection the current result of the currency conversion can be shared with other apps.
    ShareActionProvider shareAction;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu, menu);
        MenuItem shareItem = menu.findItem(R.id.share_button);
        MenuItem refreshCurrencies = menu.findItem(R.id.refresh_currencies_button);
        refreshCurrencies.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                    ExchangeRateUpdateRunnable.updateCurrency();
                return true;
            }
        });
        shareAction = (ShareActionProvider) MenuItemCompat.getActionProvider(shareItem);
        setShareText(response);
        return true;
    }


    private void setShareText(String text) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        if (text != null) {
            shareIntent.putExtra(Intent.EXTRA_TEXT, text);
        }
        shareAction.setShareIntent(shareIntent);
    }
}
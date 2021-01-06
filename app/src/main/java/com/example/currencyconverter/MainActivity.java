package com.example.currencyconverter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.ShareActionProvider;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;

public class MainActivity extends AppCompatActivity {

    //Declarations
    String response; //store the conversion result for sharing
    ExchangeRateUpdateRunnable eruRunnable;
    public Spinner dropdown1 = null;
    public Spinner dropdown2 = null;
    public EditText textInput = null;
    Button b1;
    final ExchangeRateDatabase currencyDropList = new ExchangeRateDatabase();
    final ExchangeRate exchangeRate = new ExchangeRate();
    CurrencyListAdapter cla = new CurrencyListAdapter(currencyDropList);
    ToastHandler th = new ToastHandler(MainActivity.this);

    /**
     *
     * @param item
     * @return
     */
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

    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);


        final TextView currencyResult = findViewById(R.id.textViewResult);

        textInput = findViewById(R.id.textInputEditText);
        dropdown1 = findViewById(R.id.spinner3);

        dropdown2 = findViewById(R.id.spinner2);
        b1 = findViewById(R.id.button);

       /* Context context = null;
        context.getApplicationContext();*/
        eruRunnable = new ExchangeRateUpdateRunnable();
        Thread t = new Thread(eruRunnable);
        t.start();

        /*ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                R.layout.support_simple_spinner_dropdown_item, currencyDropList.getCurrencies()
        );*/

        cla.notifyDataSetInvalidated();
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
                if(amount >= 0) {
                    result = currencyDropList.convert(amount, d1, d2);
                }else {
                    result = 0;
                    Toast invalidNumberToast = Toast.makeText(MainActivity.this,"Invalid Input!", Toast.LENGTH_LONG);
                    invalidNumberToast.show();
                }

                currencyResult.setText(("Result: " + exchangeRate.roundValue(amount) + " " +
                        d1 + " is " + exchangeRate.roundValue(result) + " " + d2));

                response = "Result: " + exchangeRate.roundValue(amount) + " " +
                        d1 + " is " + exchangeRate.roundValue(result) + " " + d2;
            }
        });
    }

    /**
     * Dispatch onPause() to fragments.
     */
    @Override
    protected void onPause() {
        super.onPause();

        SharedPreferences preferences = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        String amountToConvert = textInput.getText().toString();
        editor.putString("amountToConvert", amountToConvert);
        editor.apply();

        //keep the new currency values
        String [] storedCurrencies = currencyDropList.getCurrencies();
        for(String values: storedCurrencies){
            int i = 1;
            editor.putString("value" + i,values);
            i++;
        }
        /*
        String spinnerOneState = dropdown1.getSelectedItem().toString();
        String spinnerTwoState = dropdown2.getSelectedItem().toString();

        editor.putString("spinner 1", spinnerOneState);
        editor.putString("spinner 2", spinnerTwoState);*/
    }

    /**
     * Dispatch onResume() to fragments.  Note that for better inter-operation
     * with older versions of the platform, at the point of this call the
     * fragments attached to the activity are <em>not</em> resumed.
     */
    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences preferences = getPreferences(Context.MODE_PRIVATE);

        String storedAmountToConvert = preferences.getString("amountToConvert", "0");
        textInput.setText(storedAmountToConvert);

        for(String restoredCurrencies: currencyDropList.getCurrencies()){
            String currencyPrefs = preferences.getString(restoredCurrencies,"0.00");
            double rate = Double.parseDouble(currencyPrefs);
            ExchangeRateDatabase.setExchangeRate(currencyDropList.getCurrencyName(), rate);
        }
        /*Toast currencyToastUpdate = Toast.makeText(this, "Currencies Updated",
                Toast.LENGTH_LONG);
        currencyToastUpdate.show();*/
       /* String restoredSpinnerOne = preferences.getString("spinner 1", "EUR");
        String restoredSpinnerTwo = preferences.getString("spinner 2", "EUR");

        dropdown1.setSelection();*/
    }

    //TODO Exercise 4.3: Include Share-Button
    //In this exercise you will extend the currency converter with a „share“-button.
    // On selection the current result of the currency conversion can be shared with other apps.
    ShareActionProvider shareAction;

    /**
     *
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu, menu);
        MenuItem shareItem = menu.findItem(R.id.share_button);
        MenuItem refreshCurrencies = menu.findItem(R.id.refresh_currencies_button);
        refreshCurrencies.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                ExchangeRateUpdateRunnable.updateCurrency();
                /*ExchangeRateUpdateRunnable runnable = new ExchangeRateUpdateRunnable();
                runnable.run();*/
                th.showToast("Currency list was successfully updated!",Toast.LENGTH_LONG);
                return true;
            }
        });
        shareAction = (ShareActionProvider) MenuItemCompat.getActionProvider(shareItem);
        setShareText(response);
        return true;
    }

    /**
     *
     * @param text
     */
    private void setShareText(String text) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        if (text != null) {
            shareIntent.putExtra(Intent.EXTRA_TEXT, text);
        }
        shareAction.setShareIntent(shareIntent);
    }
}
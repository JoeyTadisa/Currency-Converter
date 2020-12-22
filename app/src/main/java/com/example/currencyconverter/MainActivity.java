package com.example.currencyconverter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

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

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                R.layout.support_simple_spinner_dropdown_item, currencyDropList.getCurrencies()
        );
        dropdown1.setAdapter(adapter);
        dropdown2.setAdapter(adapter);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double result;
                double amount = Double.parseDouble(textInput.getText().toString());

                String d1 = dropdown1.getSelectedItem().toString();
                String d2 = dropdown2.getSelectedItem().toString();
                result = currencyDropList.convert(amount, d1, d2);

                currencyResult.setText(("Result: " + exchangeRate.roundValue(amount) + " " +
                        d1 + " is  " + exchangeRate.roundValue(result) + " " + d2 ));
            }
        });

        //TODO Exercise 2.5 Currency List (Custom Adapter)
        //Extend your list and the Spinners such that you directly show the exchange rate in the list.
        //For this you need to write your own adapter class as shown in the lecture.
    }
}
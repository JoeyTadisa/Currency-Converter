package com.example.currencyconverter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String [] currencies = {"EUR", "USD"};

        final Spinner dropdown1, dropdown2;
        final EditText textInput;
        Button b1;
        final TextView currencyResult = (TextView)findViewById(R.id.textViewResult);

        textInput = findViewById(R.id.textInputEditText);
        dropdown1 = findViewById(R.id.spinner3);

        dropdown2 = findViewById(R.id.spinner2);
        b1 = findViewById(R.id.button);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                R.layout.support_simple_spinner_dropdown_item,
                currencies
        );
        dropdown1.setAdapter(adapter);
        dropdown2.setAdapter(adapter);

        final Double toEuro = 0.85;
        final Double toDollar = 1.17;

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double result;
                double amount = Double.parseDouble(textInput.getText().toString());

                if(dropdown1.getSelectedItem().toString().equals("USD") && dropdown2.getSelectedItem().toString().equals("EUR")) {
                    result = amount * toEuro;
                    currencyResult.setText("$" + textInput.getText().toString() + "  = €" + result + "\n The rate today is $1 = €" + toEuro );
                }else if(dropdown1.getSelectedItem().toString().equals("EUR") && dropdown2.getSelectedItem().toString().equals("USD")){
                    result = amount * toDollar;
                    currencyResult.setText("€" +textInput.getText().toString() + " = $" + result + "\n The rate today is €1 = $" + toDollar);
                }else currencyResult.setText(R.string.encourageAlternative); //Prompts the user to pick a currency pairs that are not the same
            }
        });
    }
}
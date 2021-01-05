package com.example.currencyconverter;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import java.util.List;

public class ExchangeRateUpdateRunnable implements Runnable {

    private final Context context;
    private boolean isRunning = false;

    //default constructor
    public ExchangeRateUpdateRunnable(Context context){
        this.context = context;
    }

    public ExchangeRateUpdateRunnable(){
        context = null;
    }


    /**
     *
     */
    static void updateCurrency() {
        /*StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);*/

        ECBXmlParser xmlParser = new ECBXmlParser();
        List<ECBXmlParser.NewExchangeRate> newCurrencyList = xmlParser.parse();
        for (ECBXmlParser.NewExchangeRate newExchangeRate : newCurrencyList
        ) {
            ExchangeRateDatabase rateDatabase = new ExchangeRateDatabase();
            double newRate = Double.parseDouble(newExchangeRate.rate);
            rateDatabase.setExchangeRate(newExchangeRate.currencyName, newRate);
        }
    }

    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        while(true){
            synchronized (ExchangeRateUpdateRunnable.this) {
                if (isRunning) {
                    updateCurrency();
                    runToastOnUIThread();
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     *
     */
   synchronized private void runToastOnUIThread(){
        Runnable runnable = new Runnable() {

            private Context MainActivity;

            @Override
            public void run() {
                Toast toast = Toast.makeText(context,"Update Successful", Toast.LENGTH_LONG);
                toast.show();
            }

        };
    }
}

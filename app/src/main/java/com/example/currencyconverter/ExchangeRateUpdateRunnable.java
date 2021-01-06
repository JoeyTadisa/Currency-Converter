package com.example.currencyconverter;

import android.content.Context;
import android.os.Build;
import android.os.StrictMode;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import java.util.List;

/**
 *  Run currency updates in the background using <code>Runnable</code>
 */
public class ExchangeRateUpdateRunnable implements Runnable {

    //private final Context context;
    private boolean isRunning = false;

    public ExchangeRateUpdateRunnable() {
    }

    /**
     *  Makes an instance of <code>ECBXmlParser</code> and begins extracting the relevant information.
     */
    static void updateCurrency() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        ECBXmlParser xmlParser = new ECBXmlParser();
        xmlParser.parse();
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
                    //runToastOnUIThread();
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

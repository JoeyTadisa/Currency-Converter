package com.example.currencyconverter;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;

/**
 * JobService to be scheduled by the JobScheduler.
 * start another service
 */
public class CurrencyJobService extends JobService{

    //private static final String TAG = "SyncService";
    ExchangeRateUpdateRunnable exchangeRateUpdateRunnable;

    /**
     *  Trigger the <code>JobService</code>
     * @param params Identifier to start a particular <code>JobService</code>
     * @return boolean true
     */
    @Override
    public boolean onStartJob(JobParameters params) {
        Intent service = new Intent(getApplicationContext(), MainActivity.class);
        getApplicationContext().startService(service);
        Util.scheduleJob(getApplicationContext()); // reschedule the job

        exchangeRateUpdateRunnable = new ExchangeRateUpdateRunnable();
        Thread updateThreadInBackground = new Thread(exchangeRateUpdateRunnable);
        updateThreadInBackground.start();
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return true;
    }
}

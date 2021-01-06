package com.example.currencyconverter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class StartCurrencyServiceReceiver extends BroadcastReceiver {

    /**
     * The registered receiver uses the job scheduler to trigger the JobService on a regular basis.
     * This receiver registers for the android.intent.action.BOOT_COMPLETED broadcast which is sent
     * after the Android system is rebooted.
     *
     * @param context Activity context
     * @param intent JobService intent
     */
        @Override
        public void onReceive(Context context, Intent intent) {
            Util.scheduleJob(context);
        }
}
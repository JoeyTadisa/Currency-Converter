package com.example.currencyconverter;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;

public class Util {
    private static final long ONE_DAY_INTERVAL = 24 * 60 * 60 * 1000L; // 1 Day
        // schedule the start of the service every 10 - 30 seconds

    /**
     *  Schedule a <code>JobService</code>
     * @param context Activity context
     */
        public static void scheduleJob(Context context) {
            ComponentName serviceComponent = new ComponentName(context, CurrencyJobService.class);
            JobInfo.Builder builder = new JobInfo.Builder(0, serviceComponent);
            builder.setMinimumLatency(ONE_DAY_INTERVAL); // wait at least
            //builder.setOverrideDeadline(3 * 1000); // maximum delay
            builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY);
            builder.setPersisted(true);
            //builder.setRequiresDeviceIdle(true); // device should be idle
            //builder.setRequiresCharging(false); // we don't care if the device is charging or not
            JobScheduler jobScheduler = context.getSystemService(JobScheduler.class);
            jobScheduler.schedule(builder.build());
        }
    }
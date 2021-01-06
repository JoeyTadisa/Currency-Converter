package com.example.currencyconverter;
import android.content.Context;
import android.os.Handler;
import android.widget.Toast;
/*
  Taken from an answer in the following thread
  http://stackoverflow.com/questions/7378936/how-to-show-toast-message-from-background-thread
  From user kaolick
* /
/**
 *
 * A class for showing a <code>Toast</code> from background thread using a
 * <code>Handler</code>. To get around the issue of activity contexts.
 *
 *
 */
public class ToastHandler
{
    //Member Variables
    private Context mContext;
    private Handler mHandler;

    /**
     * Class constructor to initialise <code>Context</code> and a <code>Handler</code>.
     *
     * @param context
     *            The activity<code>Context</code> for showing the <code>Toast</code>
     */
    public ToastHandler(Context context)
    {
        this.mContext = context;
        this.mHandler = new Handler();
    }
    /**
     * Runs the <code>Runnable</code> in a separate <code>Thread</code>.
     *
     * @param runnable
     *            The <code>Runnable</code> containing the <code>Toast</code>
     */
    private void runRunnable(final Runnable runnable)
    {
        Thread thread = new Thread()
        {
            public void run()
            {
                mHandler.post(runnable);
            }
        };
        thread.start();
        thread.interrupt();
        thread = null;
    }
    /**
     * Shows a <code>Toast</code> using a <code>Handler</code>.
     *
     * @param resID
     *            The resource id of the string resource to use. Can be
     *            formatted text.
     * @param toastDuration
     *            How long to display the message. Only use LENGTH_LONG or
     *            LENGTH_SHORT from <code>Toast</code>.
     */
    public void showToast(final int resID, final int toastDuration)
    {
        final Runnable runnable = new Runnable()
        {
            @Override
            public void run()
            {
// Get the text for the given resource ID
                String text = mContext.getResources().getString(resID);
                Toast.makeText(mContext, text, toastDuration).show();
            }
        };
        runRunnable(runnable);
    }
    /**
     * Shows a <code>Toast</code> using a <code>Handler</code>.
     *
     * @param toastCharSequence
     *            The text to show. Can be formatted text.
     * @param toastDuration
     *            How long to display the message. Only use LENGTH_LONG or
     *            LENGTH_SHORT from <code>Toast</code>.
     */
    public void showToast(final CharSequence toastCharSequence, final int toastDuration)
    {
        final Runnable runnable = new Runnable()
        {
            @Override
            public void run()
            {
                Toast.makeText(mContext, toastCharSequence, toastDuration).show();
            }
        };
        runRunnable(runnable);
    }
}
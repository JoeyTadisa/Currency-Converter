package com.example.currencyconverter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CurrencyListAdapter extends BaseAdapter {
    ExchangeRateDatabase entry;

    //public CurrencyListAdapter(String[] currencyList){
        //this.currencyList = currencyList;
    //}
    public CurrencyListAdapter(ExchangeRateDatabase db) { entry = db;
    }

    @Override
    public int getCount() {
        return entry.getCurrencies().length;
    }

    /**
     * Get the data item associated with the specified position in the data set.
     *
     * @param position Position of the item whose data we want within the adapter's
     *                 data set.
     * @return The data at the specified position.
     */
    @Override
    public Object getItem(int position) {
        return entry.getCurrencies()[position];
    }

    /**
     * Get the row id associated with the specified position in the list.
     *
     * @param position The position of the item within the adapter's data set whose row id we want.
     * @return The id of the item at the specified position.
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * Get a View that displays the data at the specified position in the data set. You can either
     * create a View manually or inflate it from an XML layout file. When the View is inflated, the
     * parent View (GridView, ListView...) will apply default layout parameters unless you use
     * {@link LayoutInflater#inflate(int, ViewGroup, boolean)}
     * to specify a root view and to prevent attachment to the root.
     *
     * @param position    The position of the item within the adapter's data set of the item whose view
     *                    we want.
     * @param convertView The old view to reuse, if possible. Note: You should check that this view
     *                    is non-null and of an appropriate type before using. If it is not possible to convert
     *                    this view to display the correct data, this method can create a new view.
     *                    Heterogeneous lists can specify their number of view types, so that this View is
     *                    always of the right type (see {@link #getViewTypeCount()} and
     *                    {@link #getItemViewType(int)}).
     * @param parent      The parent that this view will eventually be attached to
     * @return A View corresponding to the data at the specified position.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Context context = parent.getContext();

        String currencyName = entry.getCurrencies()[position];
        String imageResource = "flag_" + currencyName.toLowerCase();
        int imageId = context.getResources().getIdentifier(imageResource, "drawable", context.getPackageName());
        Double forex = entry.getExchangeRate(currencyName);

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.activity_currency_list, null, false);
        }

        ImageView flagBox = convertView.findViewById(R.id.flag_icon);
        flagBox.setImageResource(imageId);

        //currencyList = entry.getCurrencies();

        TextView currencyNameView = convertView.findViewById(R.id.list_text_view);
        currencyNameView.setText(currencyName);

        TextView foreignExchangeRate = convertView.findViewById(R.id.forex);
        foreignExchangeRate.setText(forex.toString());

        return convertView;
    }
}

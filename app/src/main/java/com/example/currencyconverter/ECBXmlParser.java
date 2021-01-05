package com.example.currencyconverter;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class ECBXmlParser {
    private String content;

    public static class NewExchangeRate {
        public final String currencyName;
        public final String rate;

        /**
         * @param currencyName
         * @param rate
         */
        private NewExchangeRate(String currencyName, String rate){
            this.currencyName = currencyName;
            this.rate = rate;
        }
    }

    /**
     * @return
     */
    public List<NewExchangeRate> parse() {
        List<NewExchangeRate> currencyEntries = new ArrayList<>();
        String currenciesReferenceSite = "https://www.ecb.europa.eu/stats/eurofxref/eurofxref-daily.xml";

        try {
            URLConnection connection;
            URL url;
            url = new URL(currenciesReferenceSite);
            connection = url.openConnection();
            InputStream inputStream = connection.getInputStream(); //TODO The program cuts out here and says that the variable connection is unknown, so it doesn't actually retrieve any values.
            //XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser parser = factory.newPullParser();
            //parser.setInput(new StringReader(content.replace("&","&amp;")));
            parser.setInput(inputStream , connection.getContentEncoding());

            int eventType = parser.getEventType();
            //String tag =;
            Log.i("Retrieve the event type",Integer.toString(eventType));

            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG) {
                    while ("cube".equalsIgnoreCase(parser.getName()) && ((parser.getAttributeValue(null, "currency")!=null) || (parser.getAttributeValue(null,"rate") != null))) {
                        NewExchangeRate newExchangeRate = new NewExchangeRate(parser.getAttributeValue(null, "currency"),
                                             parser.getAttributeValue(null, "rate"));
                        currencyEntries.add(newExchangeRate);
                    }
                    eventType = parser.nextTag();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return currencyEntries;
    }
}
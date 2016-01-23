package com.example.xfang.sunshine;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.xfang.sunshine.data.WeatherContract.WeatherEntry;

public class ForecastAdapter extends CursorAdapter{

    private static final int VIEW_TYPE_TODAY = 0;
    private static final int VIEW_TYPE_NOT_TODAY = 1;

    public ForecastAdapter(Context context, Cursor c, int flags){
        super(context, c, flags);
    }

    private static String convertCursorRowToUXFormat(Cursor c){
        double max = c.getDouble(ForecastFragment.COL_WEATHER_MAX_TEMP);
        double min = c.getDouble(ForecastFragment.COL_WEATHER_MIN_TEMP);
        long dateInMilliseconds = c.getLong(ForecastFragment.COL_WEATHER_DATE);
        String description = c.getString(ForecastFragment.COL_WEATHER_DESC);

        String day = Utilities.formatMillisecondsToReadableDate(dateInMilliseconds);

        return day + "   " + min + " / " + max + ", " + description;

    }

    @Override
    public int getViewTypeCount(){
        return 2;
    }

    @Override
    public int getItemViewType(int position){
        if (position == 0){
            return VIEW_TYPE_TODAY;
        }
        else{
            return VIEW_TYPE_NOT_TODAY;
        }
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent){
        int layoutId;
        int viewType = getItemViewType(cursor.getPosition());
        if (viewType == VIEW_TYPE_TODAY){
            layoutId = R.layout.list_item_forecast_today;
        }
        else{
            layoutId = R.layout.list_item_forecast;
        }

        return LayoutInflater.from(context).inflate(layoutId, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor){
        TextView dateView = (TextView) view.findViewById(R.id.list_item_date_textview);
        TextView descView = (TextView) view.findViewById(R.id.list_item_forecast_textview);
        TextView highView = (TextView) view.findViewById(R.id.list_item_high_textview);
        TextView lowView = (TextView) view.findViewById(R.id.list_item_low_textview);

        long dateInMilliseconds = cursor.getLong(ForecastFragment.COL_WEATHER_DATE);
        String readableDate = Utilities.formatMillisecondsToReadableDate(dateInMilliseconds);
        dateView.setText(readableDate);
        descView.setText(cursor.getString(ForecastFragment.COL_WEATHER_DESC));
        highView.setText(cursor.getString(ForecastFragment.COL_WEATHER_MAX_TEMP));
        lowView.setText(cursor.getString(ForecastFragment.COL_WEATHER_MIN_TEMP));
    }

}
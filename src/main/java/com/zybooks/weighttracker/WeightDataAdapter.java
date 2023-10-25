package com.zybooks.weighttracker;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cursoradapter.widget.CursorAdapter;

public class WeightDataAdapter extends CursorAdapter {
    public WeightDataAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }

    // The newView method is used to inflate a new view and return it,
    // you don't bind any data to the view at this point.
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.weight_data_layout, parent, false);
    }

    // The bindView method is used to bind all data to a given view
    // such as setting the text on a TextView.
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Find fields to populate in inflated template
        TextView date = (TextView) view.findViewById(R.id.wDate);
        TextView weight = (TextView) view.findViewById(R.id.wWeight);
        TextView action = (TextView) view.findViewById(R.id.wAction);
        // Extract properties from cursor
        String wDate = cursor.getString(cursor.getColumnIndexOrThrow("date"));
        int weightVal = cursor.getInt(cursor.getColumnIndexOrThrow("starting_weight"));

        // Populate fields with extracted properties
        date.setText("Date: " + wDate);
        weight.setText("Weight: " + String.valueOf(weightVal));
        action.setText("Edit");
    }

    public void deleteEntry(long id){
        Log.d("suck my items", String.valueOf(id));
    }
}

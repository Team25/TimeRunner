package com.t25.hbv601g.timerunner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.t25.hbv601g.timerunner.entities.Employee;
import com.t25.hbv601g.timerunner.entities.Entry;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by dingo on 4.4.2017.
 */

public class EntryAdapter extends ArrayAdapter<Entry> {

    public EntryAdapter(Context context, ArrayList<Entry> entryList) {
        super(context, 0, entryList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Entry entry = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_entry, parent, false);
        }
        // Lookup view for data population
        TextView inTime = (TextView) convertView.findViewById(R.id.entry_in_time);
        TextView outTime = (TextView) convertView.findViewById(R.id.entry_out_time);
        // Populate the data into the template view using the data object
        inTime.setText(getDate(Long.parseLong(entry.getInTime()), "dd/MM/yyyy kk:mm:ss"));
        if(entry.getOutTime()!=null) {
            outTime.setText(getDate(Long.parseLong(entry.getOutTime()), "dd/MM/yyyy kk:mm:ss"));
        } else
            outTime.setText("Open entry");
        // Return the completed view to render on screen
        return convertView;
    }

    private static String getDate(long milliSeconds, String dateFormat)
    {
        // Create a DateFormatter object for displaying date in specified format.
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }

}

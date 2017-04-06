package com.t25.hbv601g.timerunner;

import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.t25.hbv601g.timerunner.entities.Entry;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class EntryActivity extends AppCompatActivity {

    private static final String EXTRA_ENTRY_ID = "com.t25.hbv601g.timerunner.entryintent.entry_id";
    private TextView mInTimeTextView;
    private TextView mOutTimeTextView;
    private TextView mTotalTimeTextView;

    public static Intent newIntent(Context context, Entry entry){
        Intent intent = new Intent(context, EntryActivity.class);
        intent.putExtra(EXTRA_ENTRY_ID, entry);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);

        // kannski þarf ég að nota UUID hérna?
        Entry entry = (Entry) getIntent().getSerializableExtra(EXTRA_ENTRY_ID);

        mInTimeTextView = (TextView) findViewById(R.id.entry_in_time);
        mOutTimeTextView = (TextView) findViewById(R.id.entry_out_time);
        mTotalTimeTextView = (TextView) findViewById(R.id.entry_total_time);
        long inTime = Long.parseLong(entry.getInTime());
        mInTimeTextView.setText("In Time: " + getDate(inTime, "dd/MM/yyyy kk:mm:ss"));
        if(entry.getOutTime()!=null) {
            long outTime = Long.parseLong(entry.getOutTime());
            mOutTimeTextView.setText("Out Time: " + getDate(outTime, "dd/MM/yyyy kk:mm:ss"));

            long totalTime = outTime - inTime;
            // Can only display total time if its under 24 hours :(... good enough for presentation.
            mTotalTimeTextView.setText("Total hours: " + getDate(totalTime, "KK:mm:ss"));

        } else {
            mOutTimeTextView.setText("Out Time: This entry is still open");
            mTotalTimeTextView.setText("Total clockInTime: " + getDate((System.currentTimeMillis()-inTime), "KK:mm:ss"));
        }



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

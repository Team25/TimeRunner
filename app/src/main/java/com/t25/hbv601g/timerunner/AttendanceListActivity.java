package com.t25.hbv601g.timerunner;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;

import com.t25.hbv601g.timerunner.entities.Entry;

public class AttendanceListActivity extends AppCompatActivity
        implements  AttendanceListFragment.Callbacks{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupActionBar();
        setContentView(R.layout.activity_attendance_list);

        AttendanceListFragment attendanceListFragment = new AttendanceListFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.entry_list, attendanceListFragment).commit();
    }

    @Override
    public void onEntrySelected(Entry entry) {
        Intent intent = EntryActivity.newIntent(this, entry);
        startActivity(intent);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            return returnToClockPage();
        }
        return super.onKeyDown(keyCode, event);
    }

    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Show the Up button in the action bar.
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("My Entries");
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            return returnToClockPage();
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean returnToClockPage() {
        Intent intent = new Intent(AttendanceListActivity.this, ClockActivity.class);
        AttendanceListActivity.this.startActivity(intent);
        this.finish();
        return true;
    }
}

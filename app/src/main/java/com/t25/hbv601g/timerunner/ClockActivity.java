package com.t25.hbv601g.timerunner;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.NotificationCompat;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.t25.hbv601g.timerunner.entities.Employee;
import com.t25.hbv601g.timerunner.repositories.UserLocalStorage;
import com.t25.hbv601g.timerunner.services.ClockService;


public class ClockActivity extends AppCompatActivity {

    private TextView mCurrentEmployeeDisplay;
    private ImageButton mBtnPopupMenu;
    private ImageButton mBtnClock;
    private NotificationCompat.Builder mClockNotification;
    private static final int mUniqueNotificationId = 13371337; // We don't mind overwriting older notifications
    private ClockService mClockService;
    private Employee mEmployee;
    private TextView mClockDurationLabel;
    private TextView mTimerDisplay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_clock);

        final UserLocalStorage localStorage = UserLocalStorage.getInstance(this);

        mCurrentEmployeeDisplay = (TextView) findViewById(R.id.employee_name);
        mClockDurationLabel = (TextView) findViewById(R.id.clock_duration_lab);
        mTimerDisplay = (TextView) findViewById(R.id.timer_display);

        mTimerDisplay.setVisibility(View.INVISIBLE);
        mClockDurationLabel.setVisibility(View.INVISIBLE);

        mClockService = new ClockService(this, mTimerDisplay, new Handler());

        Bundle employeeBundle = getIntent().getExtras();
        if (employeeBundle != null)
            mEmployee = (Employee) employeeBundle.getSerializable("currentEmployee");

        if (mEmployee != null) mCurrentEmployeeDisplay.setText(mEmployee.getFullName());

        mBtnPopupMenu = (ImageButton) findViewById(R.id.btn_popup_menu);

        mBtnPopupMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(ClockActivity.this, v);

                MenuInflater inflater = popupMenu.getMenuInflater();
                inflater.inflate(R.menu.menu, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.settings_menu:
                                Intent settingsIntent = new Intent(ClockActivity.this, SettingsActivity.class);
                                ClockActivity.this.startActivity(settingsIntent);
                                return true;
                            case R.id.logout_menu:
                                localStorage.removeToken();
                                Intent logoutIntent = new Intent(ClockActivity.this, LoginActivity.class);
                                ClockActivity.this.startActivity(logoutIntent);
                                ClockActivity.this.finish();
                                return true;
                            case R.id.attendance_menu:
                                Intent entryIntent = new Intent(ClockActivity.this, AttendanceListActivity.class);
                                ClockActivity.this.startActivity(entryIntent);
                                return true;
                            default:
                                return false;
                        }
                    }
                });

                popupMenu.show();
            }
        });

        mBtnClock = (ImageButton) findViewById(R.id.btn_clock);
        //TODO check if user is clocked in or out and put right text on button
        mClockService.setClockedButtonText(mBtnClock, mClockDurationLabel);

        mBtnClock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO clock in/out and have progress circle while we wait for confirmation from server
                mClockService.clock(mBtnClock, mClockDurationLabel);
            }

        });

        mClockNotification = new NotificationCompat.Builder(this);
        mClockNotification.setAutoCancel(true);

        registerReceiver(broadcastReceiver, new IntentFilter("com.t25.hbv601g.timerunner.NOTIFY_CLOCK_IN"));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Add check for which notification we are experiencing, clock-in or clock-out.
            mClockService.notifyIfClockedOut(mClockNotification, mUniqueNotificationId, mEmployee);
        }
    };

}

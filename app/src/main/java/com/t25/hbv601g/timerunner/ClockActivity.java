package com.t25.hbv601g.timerunner;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.NotificationCompat;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import com.t25.hbv601g.timerunner.repositories.UserLocalStorage;
import com.t25.hbv601g.timerunner.services.ClockService;


public class ClockActivity extends AppCompatActivity {

    private TextView mCurrentEmployeeDisplay;
    private Button mBtnDeleteToken;
    private Button mBtnClock;
    private ImageButton mBtnSettings;
    private NotificationCompat.Builder mClockNotification;
    private static final int mUniqueNotificationId = 13371337; // We don't mind overwriting older notifications
    private ClockService mClockService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mClockService = new ClockService(this);
        setContentView(R.layout.activity_clock);

        final UserLocalStorage localStorage = UserLocalStorage.getInstance(this);

        String token = localStorage.getToken();
        mCurrentEmployeeDisplay = (TextView) findViewById(R.id.employee_name);

        mCurrentEmployeeDisplay.setText(token);

        mBtnSettings = (ImageButton) findViewById(R.id.btn_settings);

        mBtnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent settingsIntent = new Intent(ClockActivity.this, SettingsActivity.class);
                ClockActivity.this.startActivity(settingsIntent);
            }
        });

        mBtnDeleteToken = (Button) findViewById(R.id.btn_delete_token);

        mBtnDeleteToken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (localStorage.removeToken()) {
                    mCurrentEmployeeDisplay.setText("Dude, where's your token?"); //where is your token dude
                    Toast.makeText(ClockActivity.this, "token removed", Toast.LENGTH_LONG).show();
                }
            }
        });

        mBtnClock = (Button) findViewById(R.id.btn_clock);
        //TODO check if user is clocked in or out and put right text on button
        mClockService.setClockedButtonText(mBtnClock);

        mBtnClock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO clock in/out and have brogress circle while we wait for confirmation from server
                mClockService.clock(mBtnClock);
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
            mClockService.notifyIfClockedOut(mClockNotification, mUniqueNotificationId);
        }
    };

}

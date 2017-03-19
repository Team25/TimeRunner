package com.t25.hbv601g.timerunner;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.graphics.Color;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.NotificationCompat;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.t25.hbv601g.timerunner.communications.NetworkManager;
import com.t25.hbv601g.timerunner.entities.Employee;
import com.t25.hbv601g.timerunner.repositories.UserLocalStorage;
import com.t25.hbv601g.timerunner.services.ClockService;

import org.w3c.dom.Text;

public class ClockActivity extends AppCompatActivity {

    private TextView mCurrentEmployeeDisplay;
    private Button mBtnDeleteToken;
    private Button mBtnClock;
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
        mClockService.isClockedIn(mBtnClock);

        mBtnClock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO clock in/out and have brogress circle while we wait for confirmation from server
                mClockService.clock();
                if(mClockService.getCurrentEntry()==null || mClockService.getCurrentEntry().getOutTime()==null)
                    mBtnClock.setText(getString(R.string.clock_in_btn_text));
                else
                    mBtnClock.setText(getString(R.string.clock_out_btn_text));
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
            clockInNotification();
        }
    };

    public void clockInNotification() {
        // Build the notification
        mClockNotification.setSmallIcon(R.drawable.running_man);
        mClockNotification.setTicker("Want to clock in?");
        mClockNotification.setWhen(System.currentTimeMillis());
        mClockNotification.setContentTitle("Clock in to TimerRunner?");
        mClockNotification.setContentText("Tap to clock-in.");
        mClockNotification.setVibrate(new long[] {2000, 500});
        mClockNotification.setLights(Color.RED, 500, 500);

        Intent clockInIntent = new Intent(this, ClockActivity.class);
        // Give Android OS access to our app's newly created intent.
        PendingIntent pendingClockIntent = PendingIntent.getActivity(this, 0, clockInIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        mClockNotification.setContentIntent(pendingClockIntent);

        // Issue the notification
        NotificationManager nm =  (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        nm.notify(mUniqueNotificationId, mClockNotification.build());
    }

}

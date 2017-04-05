package com.t25.hbv601g.timerunner.services;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Ringtone;
import android.graphics.Color;
import android.net.Uri;
import android.os.Handler;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.t25.hbv601g.timerunner.ClockActivity;
import com.t25.hbv601g.timerunner.R;
import com.t25.hbv601g.timerunner.communications.ClockCallback;
import com.t25.hbv601g.timerunner.communications.NetworkManager;
import com.t25.hbv601g.timerunner.entities.Entry;
import com.t25.hbv601g.timerunner.repositories.UserLocalStorage;

import org.w3c.dom.Text;

import java.util.Timer;
import java.util.TimerTask;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by dingo on 17.3.2017.
 */

public class ClockService {

    private NetworkManager mNetworkManager;
    private final Context mContext;
    private UserLocalStorage mLocalStorage;
    private Entry mCurrentEntry; //ekki í uml
    private Timer mTimer;
    long totalSeconds; // used with timer
    public TextView mTimerDisplay;
    public Handler mTimerHandler;

    public ClockService(Context context, TextView timerDisplay, Handler timerHandler) {
        mNetworkManager = NetworkManager.getInstance(context);
        mLocalStorage = UserLocalStorage.getInstance(context);
        mContext = context;
        mTimerDisplay = timerDisplay;
        mTimerHandler = timerHandler;
    }

    public void notifyIfClockedOut(final NotificationCompat.Builder clockNotification, final int uniqueNotificationId){
        String token = mLocalStorage.getToken();
        mNetworkManager.getOpenClockEntry(token, new ClockCallback() {
            @Override
            public void onSuccess(Entry entry) {
                mCurrentEntry = entry;
                if(entry==null){
                    // Get user preferences
                    SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(mContext.getApplicationContext());
                    boolean vibrateEnabled = sharedPref.getBoolean("notifications_clock_vibrates", false);
                    String strRingtonePreference = sharedPref.getString("notifications_clock_alert_sound", "DEFAULT_SOUND");
                    Uri soundNotification = Uri.parse(strRingtonePreference);

                    // Build the notification
                    clockNotification.setSmallIcon(R.drawable.running_man);
                    clockNotification.setTicker(mContext.getString(R.string.clock_in_reminder_ticker));
                    clockNotification.setWhen(System.currentTimeMillis());
                    clockNotification.setContentTitle(mContext.getString(R.string.clock_in_reminder_title));
                    clockNotification.setContentText(mContext.getString(R.string.clock_in_reminder_text));
                    if (vibrateEnabled) {
                        clockNotification.setVibrate(new long[]{500, 500});
                    }
                    clockNotification.setSound(soundNotification);
                    clockNotification.setLights(Color.RED, 500, 500);

                    Intent clockInIntent = new Intent(mContext, ClockActivity.class);
                    clockInIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    // Give Android OS access to our app's newly created intent.
                    PendingIntent pendingClockIntent = PendingIntent.getActivity(mContext, 0, clockInIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                    clockNotification.setContentIntent(pendingClockIntent);

                    // Issue the notification
                    NotificationManager nm =  (NotificationManager) mContext.getSystemService(NOTIFICATION_SERVICE);
                    nm.notify(uniqueNotificationId, clockNotification.build());
                } else {
                    // Todo Nothing if he is not clocked in
                }
            }

            @Override
            public void onFailure(String error) {
                // TODO perhaps nothing?
                //Toast.makeText(mContext,
                //        mContext.getString(R.string.server_error), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void setClockedButtonText(final ImageButton button,
                                     final TextView timerLab){
        String token = mLocalStorage.getToken();
        mNetworkManager.getOpenClockEntry(token, new ClockCallback() {
            @Override
            public void onSuccess(Entry entry) {
                mCurrentEntry = entry;
                if(entry==null){
                    button.setImageResource(R.drawable.clock_in);
                    timerLab.setVisibility(View.INVISIBLE);
                    mTimerDisplay.setText("00:00:00");
                    mTimerDisplay.setVisibility(View.INVISIBLE);
                } else {
                    button.setImageResource(R.drawable.clock_out);
                    timerLab.setText(R.string.clock_duration_lab);
                    mTimerDisplay.setVisibility(View.VISIBLE);
                    timerLab.setVisibility(View.VISIBLE);
                    initClockTimer();
                }
            }

            @Override
            public void onFailure(String error) {
                Toast.makeText(mContext,
                        mContext.getString(R.string.server_error), Toast.LENGTH_LONG).show();
            }
        });
    }

    final Runnable updateUiTimer = new Runnable() {
        @Override
        public void run() {
            mTimerDisplay.setText(String.format("%02d:%02d:%02d",
                    totalSeconds / 3600,
                    (totalSeconds % 3600) / 60,
                    (totalSeconds % 60)));
            totalSeconds++;
        }
    };

    private void initClockTimer() {
        String clockInTimeStr = mLocalStorage.getClockInTime();
        if (clockInTimeStr == null) return;
        long clockInTime = Long.parseLong(clockInTimeStr);
        long currentTime = System.currentTimeMillis();

        long elapsedClockTime = currentTime - clockInTime;
        totalSeconds = elapsedClockTime / 1000;

        mTimerDisplay.setVisibility(View.VISIBLE);
        mTimer = new Timer();
        mTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                mTimerHandler.post(updateUiTimer);
            }
        }, 0, 1000);
    }

    public void clock(final ImageButton button,
                      final TextView timerLab){
        String token = mLocalStorage.getToken();
        mNetworkManager.clockInOut(token, mCurrentEntry, new ClockCallback(){

            @SuppressLint("StringFormatInvalid")
            @Override
            public void onSuccess(Entry entry) {
                if(entry.getOutTime() != null){
                    Toast.makeText(mContext,
                            mContext.getString(R.string.clock_out_toast), Toast.LENGTH_LONG).show();
                    button.setImageResource(R.drawable.clock_in);
                    timerLab.setVisibility(View.INVISIBLE);
                    mTimerDisplay.setText("00:00:00");
                    mTimer.cancel();
                    mTimerDisplay.setVisibility(View.INVISIBLE);
                } else {
                    mLocalStorage.saveClockInTime(String.valueOf(System.currentTimeMillis()));

                    button.setImageResource(R.drawable.clock_out);
                    timerLab.setVisibility(View.VISIBLE);
                    mTimerDisplay.setVisibility(View.VISIBLE);
                    initClockTimer();
                }
                mCurrentEntry = entry;

            }

            @Override
            public void onFailure(String error) {
                Toast.makeText(mContext,
                        mContext.getString(R.string.server_error), Toast.LENGTH_LONG).show();
            }
        });
    }

    public Entry getCurrentEntry(){
        return mCurrentEntry;
    }
}
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
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.widget.Button;
import android.widget.Toast;

import com.t25.hbv601g.timerunner.ClockActivity;
import com.t25.hbv601g.timerunner.R;
import com.t25.hbv601g.timerunner.communications.ClockCallback;
import com.t25.hbv601g.timerunner.communications.NetworkManager;
import com.t25.hbv601g.timerunner.entities.Entry;
import com.t25.hbv601g.timerunner.repositories.UserLocalStorage;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by dingo on 17.3.2017.
 */

public class ClockService {

    private NetworkManager mNetworkManager;
    private final Context mContext;
    private UserLocalStorage mLocalStorage;
    private Entry mCurrentEntry; //ekki í uml

    public ClockService(Context context) {
        mNetworkManager = NetworkManager.getInstance(context);
        mLocalStorage = UserLocalStorage.getInstance(context);
        mContext = context;
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

    public void setClockedButtonText(final Button button){
        String token = mLocalStorage.getToken();
        mNetworkManager.getOpenClockEntry(token, new ClockCallback() {
            @Override
            public void onSuccess(Entry entry) {
                mCurrentEntry = entry;
                if(entry==null){
                    button.setText(mContext.getString(R.string.clock_in_btn_text));
                } else {
                    button.setText(mContext.getString(R.string.clock_out_btn_text));
                }
            }

            @Override
            public void onFailure(String error) {
                Toast.makeText(mContext,
                        mContext.getString(R.string.server_error), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void clock(final Button button){
        String token = mLocalStorage.getToken();
        mNetworkManager.clockInOut(token, mCurrentEntry, new ClockCallback(){

            @SuppressLint("StringFormatInvalid")
            @Override
            public void onSuccess(Entry entry) {
                if(entry.getOutTime() != null){
                    Toast.makeText(mContext,
                            mContext.getString(R.string.clock_out_toast), Toast.LENGTH_LONG).show();
                    button.setText(mContext.getString(R.string.clock_in_btn_text));
                } else {
                    Toast.makeText(mContext,
                            mContext.getString(R.string.clock_in_toast), Toast.LENGTH_LONG).show();
                    button.setText(mContext.getString(R.string.clock_out_btn_text));
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
package com.t25.hbv601g.timerunner;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

public class WifiChangeReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO event when disconnecting from work network
        NetworkInfo info = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
        if (info != null && info.isConnected()) {
            WifiManager wifiManager = (WifiManager)context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            String joined_network = wifiInfo.getSSID();

            // TODO add time dimension and last_wifi_network so that we don't bombard user with notifications if the wifi is unreliable
            SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
            boolean wifiNotificationEnabled = sharedPref.getBoolean("notifications_clock_reminder", false);
            String wifiName = sharedPref.getString("notifications_wifi_name", "");
            if (wifiNotificationEnabled && joined_network.equals("\"" + wifiName + "\"")) {
                context.sendBroadcast(new Intent("com.t25.hbv601g.timerunner.NOTIFY_CLOCK_IN"));
            }
        }

    }
}

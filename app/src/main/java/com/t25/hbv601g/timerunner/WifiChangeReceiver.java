package com.t25.hbv601g.timerunner;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
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
            // TODO get desired SSID from settings or server
            // TODO add time dimension and last_wifi_network so that we don't bombard user with notifications if the wifi is unreliable
            if (joined_network.equals("\"eduroam\"")) {
                context.sendBroadcast(new Intent("com.t25.hbv601g.timerunner.NOTIFY_CLOCK_IN"));
            }
        }

    }
}

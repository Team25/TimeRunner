package com.t25.hbv601g.timerunner;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.widget.Toast;

public class WifiChangeReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO event when disconnecting from work network
        NetworkInfo info = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
        if (info != null && info.isConnected()) {
            WifiManager wifiManager = (WifiManager)context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            // TODO get desired SSID from settings or server
            if (wifiInfo.getSSID().equals("\"simafelagid\"")) {
                // check for token
                // if token, check whether clocked in
                // if not clocked in, notify and take to clockactivity
                context.sendBroadcast(new Intent("com.t25.hbv601g.timerunner.NOTIFY_CLOCK_IN"));
            }
            else {
                Toast.makeText(context, "Tengdur " + wifiInfo.getSSID() + " - Geri ekkert", Toast.LENGTH_LONG).show();
            }
        }

    }
}

package com.t25.hbv601g.timerunner;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by orn on 14/03/2017.
 */

public class ReminderReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context ctx, Intent i) {
        Toast.makeText(ctx, "Test Receiver says hello", Toast.LENGTH_LONG).show();
    }
}

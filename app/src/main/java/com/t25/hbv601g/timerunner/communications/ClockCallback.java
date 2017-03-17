package com.t25.hbv601g.timerunner.communications;

import com.t25.hbv601g.timerunner.entities.Entry;

/**
 * Created by dingo on 17.3.2017.
 */

public interface ClockCallback {

    void onSuccess(Entry entry);
    void onFailure(String error);
}

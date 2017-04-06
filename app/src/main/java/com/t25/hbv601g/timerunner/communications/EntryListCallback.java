package com.t25.hbv601g.timerunner.communications;

import com.t25.hbv601g.timerunner.entities.Entry;

import java.util.List;

/**
 * Created by dingo on 6.4.2017.
 */

public interface EntryListCallback {

    void onSuccess(List<Entry> entries);

    void onFailure(String error);
}

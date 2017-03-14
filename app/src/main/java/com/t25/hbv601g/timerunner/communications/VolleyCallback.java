package com.t25.hbv601g.timerunner.communications;

import com.t25.hbv601g.timerunner.entities.Employee;

import org.json.JSONObject;

/**
 * Created by Óli Legend on 13.3.2017.
 */

public interface VolleyCallback {
    void onSuccess();
    void onFailure(String error);
}

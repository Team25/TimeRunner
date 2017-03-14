package com.t25.hbv601g.timerunner.communications;

/**
 * Created by Óli Legend on 14.3.2017.
 */

public interface TokenValidityCallback {
    void onSuccess(boolean valid);
    void onFailure(String error);
}

package com.t25.hbv601g.timerunner.services;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.t25.hbv601g.timerunner.ClockActivity;
import com.t25.hbv601g.timerunner.communications.NetworkManager;
import com.t25.hbv601g.timerunner.communications.VolleyCallback;

/**
 * Created by Óli Legend on 13.3.2017.
 */

public class LoginService {

    private NetworkManager mNetworkManager;
    private final Context mContext;

    public LoginService(Context context) {
        mNetworkManager = NetworkManager.getInstance(context);
        mContext = context;
    }

    public void login(String username, String password) {
        mNetworkManager.login(username, password, new VolleyCallback() {
            @Override
            public void onSuccess() {
                Intent intent = new Intent(mContext, ClockActivity.class);
                mContext.startActivity(intent);
            }

            @Override
            public void onFailure(String error) {
                // breyta í að sækja úr string skrá
                Toast.makeText(mContext, "A server error has occurred", Toast.LENGTH_LONG).show();
            }
        });
    }
}

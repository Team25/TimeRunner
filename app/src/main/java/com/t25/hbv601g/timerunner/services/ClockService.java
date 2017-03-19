package com.t25.hbv601g.timerunner.services;

import android.content.Context;

import com.t25.hbv601g.timerunner.communications.ClockCallback;
import com.t25.hbv601g.timerunner.communications.NetworkManager;
import com.t25.hbv601g.timerunner.entities.Entry;
import com.t25.hbv601g.timerunner.repositories.UserLocalStorage;

/**
 * Created by dingo on 17.3.2017.
 */

public class ClockService {

    private NetworkManager mNetworkManager;
    private final Context mContext;
    private UserLocalStorage mLocalStorage;
    private Entry currentEntry; //ekki í uml

    public ClockService(Context context) {
        mNetworkManager = NetworkManager.getInstance(context);
        mLocalStorage = UserLocalStorage.getInstance(context);
        mContext = context;
    }

    public boolean isClockedIn(){
        return false;
    }

    public boolean clock(){

        return false;
    }

    //ég er ekki að digga callbacks XD :'(

    public void getCurrentEntry(){
        String token = mLocalStorage.getToken();
        mNetworkManager.getOpenClockEntry(token, new ClockCallback(){

            @Override
            public void onSuccess(Entry entry) {
                //TODO implement this
                currentEntry = entry;

            }

            @Override
            public void onFailure(String error) {
                //TODO Implement this

            }
        });
    }

}

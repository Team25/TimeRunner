package com.t25.hbv601g.timerunner.services;

import android.content.Context;
import android.widget.Button;
import android.widget.Toast;

import com.t25.hbv601g.timerunner.R;
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

    public void isClockedIn(final Button button){
        String token = mLocalStorage.getToken();
        mNetworkManager.getOpenClockEntry(token, new ClockCallback() {
            @Override
            public void onSuccess(Entry entry) {
                if(entry==null){
                    button.setText("Clock in");
                } else {
                    button.setText("Clock out");
                }
            }

            @Override
            public void onFailure(String error) {
                Toast.makeText(mContext,
                        mContext.getString(R.string.server_error), Toast.LENGTH_LONG).show();
            }
        });
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
package com.t25.hbv601g.timerunner.services;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
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
    private Entry mCurrentEntry; //ekki í uml

    public ClockService(Context context) {
        mNetworkManager = NetworkManager.getInstance(context);
        mLocalStorage = UserLocalStorage.getInstance(context);
        mContext = context;
    }

    //TODO change name to more appropriet name.
    public void setClockedButtonText(final Button button){
        String token = mLocalStorage.getToken();
        mNetworkManager.getOpenClockEntry(token, new ClockCallback() {
            @Override
            public void onSuccess(Entry entry) {
                mCurrentEntry = entry;
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

    public void clock(){
        String token = mLocalStorage.getToken();
        mNetworkManager.clockInOut(token, mCurrentEntry, new ClockCallback(){

            @SuppressLint("StringFormatInvalid")
            @Override
            public void onSuccess(Entry entry) {
                Log.e("place1","HI THERE");
                if(entry.getOutTime() == null){
                    Log.e("place2","HI THERE");
                    Toast.makeText(mContext,
                            mContext.getString(R.string.clock_out_toast), Toast.LENGTH_LONG).show();
                } else {
                    Log.e("place3","HI THERE");
                    Toast.makeText(mContext,
                            mContext.getString(R.string.clock_in_toast), Toast.LENGTH_LONG).show();
                }
                Log.e("place4","HI THERE");
                mCurrentEntry = entry;

            }

            @Override
            public void onFailure(String error) {
                Toast.makeText(mContext,
                        mContext.getString(R.string.server_error), Toast.LENGTH_LONG).show();
            }
        });
    }

    public Entry getCurrentEntry(){
        return mCurrentEntry;
    }

}

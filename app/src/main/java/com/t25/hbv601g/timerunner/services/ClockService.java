package com.t25.hbv601g.timerunner.services;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
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

    public void clock(final Button button){
        String token = mLocalStorage.getToken();
        mNetworkManager.clockInOut(token, mCurrentEntry, new ClockCallback(){

            @SuppressLint("StringFormatInvalid")
            @Override
            public void onSuccess(Entry entry) {
                Gson zz = new Gson();
                Log.e("FLE", zz.toJson(entry));
                if(entry.getOutTime() != null){
                    Toast.makeText(mContext,
                            mContext.getString(R.string.clock_out_toast), Toast.LENGTH_LONG).show();
                    button.setText(mContext.getString(R.string.clock_in_btn_text));
                } else {
                    Toast.makeText(mContext,
                            mContext.getString(R.string.clock_in_toast), Toast.LENGTH_LONG).show();
                    button.setText(mContext.getString(R.string.clock_out_btn_text));
                }
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

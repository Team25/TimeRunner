package com.t25.hbv601g.timerunner.services;

import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;

import com.t25.hbv601g.timerunner.ClockActivity;
import com.t25.hbv601g.timerunner.LoginActivity;
import com.t25.hbv601g.timerunner.communications.NetworkManager;
import com.t25.hbv601g.timerunner.communications.LoginCallback;
import com.t25.hbv601g.timerunner.communications.TokenValidityCallback;
import com.t25.hbv601g.timerunner.repositories.UserLocalStorage;

/**
 * Created by Óli Legend on 13.3.2017.
 */

public class LoginService {

    private NetworkManager mNetworkManager;
    private final Context mContext;
    private UserLocalStorage mLocalStorage;

    public LoginService(Context context) {
        mNetworkManager = NetworkManager.getInstance(context);
        mLocalStorage = UserLocalStorage.getInstance(context);
        mContext = context;
    }

    public void isLoggedIn() {
        final long beginTime = SystemClock.elapsedRealtime();
        final String token = mLocalStorage.getToken();

        // TODO taka þetta comment út
        // Mér finnst réttara að láta bara services klasana sjá um að sækja/setja Tokens locally
        // Minnkar networkManager smá og hann einbeitir sér bara að þessum leiðinda async
        // requests i raun.
        if (token == null) {
            long elapsedTime = SystemClock.elapsedRealtime() - beginTime;
            intentHandler(false, elapsedTime);
        } else {
            mNetworkManager.isValidToken(token, new TokenValidityCallback() {
                @Override
                public void onSuccess(final boolean valid) {
                    final long elapsedTime = SystemClock.elapsedRealtime() - beginTime;
                    intentHandler(valid, elapsedTime);
                }

                @Override
                public void onFailure(String error) {
                    // TODO user-friendly error message þegar þetta er alveg klárt.
                    Toast.makeText(mContext, error + ": App restart is required.", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    private void intentHandler(final boolean valid, final long elapsedTime) {
        /*
            Gerum nýjan thread svo hægt sé að bíða með intent án þess að frysta UI.
            Leyfum fólki að dást að logo-inu í a.m.k. 5sek.
         */
        Thread thread = new Thread() {
            public void run() {

                if (elapsedTime < 5000) {
                    try {
                        Thread.sleep(5000 - elapsedTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                if (valid) {
                    Intent intent = new Intent(mContext, ClockActivity.class);
                    mContext.startActivity(intent);
                } else {
                    Intent intent = new Intent(mContext, LoginActivity.class);
                    mContext.startActivity(intent);
                }

            }
        };
        thread.start();
    }

    public void login(String username, String password) {
        mNetworkManager.login(username, password, new LoginCallback() {
            @Override
            public void onSuccess() {
                Intent intent = new Intent(mContext, ClockActivity.class);
                mContext.startActivity(intent);
            }

            @Override
            public void onFailure(String error) {
                // TODO breyta í að sækja úr string skrá
                Toast.makeText(mContext, "A server error has occurred", Toast.LENGTH_LONG).show();
            }
        });
    }
}

package com.t25.hbv601g.timerunner.services;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.t25.hbv601g.timerunner.ClockActivity;
import com.t25.hbv601g.timerunner.LoginActivity;
import com.t25.hbv601g.timerunner.R;
import com.t25.hbv601g.timerunner.communications.NetworkManager;
import com.t25.hbv601g.timerunner.communications.LoginCallback;
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

    public void isLoggedIn(final ProgressBar progressWheel) {
        final long beginTime = SystemClock.elapsedRealtime();
        final String token = mLocalStorage.getToken();

        // TODO Ræða um það hver á að sjá um Tokens
        if (token == null) {
            long elapsedTime = SystemClock.elapsedRealtime() - beginTime;
            intentDelayHandler(false, elapsedTime);
        } else {
            mNetworkManager.isValidToken(token, new LoginCallback() {
                @Override
                public void onSuccess(final boolean isValid) {
                    final long elapsedTime = SystemClock.elapsedRealtime() - beginTime;
                    intentDelayHandler(isValid, elapsedTime);
                }

                @Override
                public void onFailure(String error) {
                    Toast.makeText(mContext, mContext.getString(R.string.server_down),
                            Toast.LENGTH_LONG).show();

                    progressWheel.setVisibility(ProgressBar.INVISIBLE);
                }
            });
        }
    }

    private void intentDelayHandler(final boolean isValid, final long elapsedTime) {
        // Minimum delay tími verður í raun fremri talan.
        final long delay = 5000 - elapsedTime;

        // Nýr þráður svo hægt sé að sleepa án þess að frysta UI áður en það
        // fær séns á að rendera.
        Thread thread = new Thread() {
            public void run() {
                // Bíðum bara ef networking tók minni tíma en 5 sek, svo logo fái alltaf
                // minnst 5sek skjátíma.
                if (delay  > 0) {
                    try {
                        Thread.sleep(delay);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                if (isValid) {
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
            public void onSuccess(boolean isValid) {
                if (isValid) {
                    Intent intent = new Intent(mContext, ClockActivity.class);
                    mContext.startActivity(intent);
                } else {
                    Toast.makeText(mContext,
                            mContext.getString(R.string.incorrect_username_password),
                            Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(String error) {
                Toast.makeText(mContext,
                        mContext.getString(R.string.server_error), Toast.LENGTH_LONG).show();
            }
        });
    }

}

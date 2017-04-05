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
import com.t25.hbv601g.timerunner.entities.Employee;
import com.t25.hbv601g.timerunner.repositories.UserLocalStorage;

import org.json.JSONException;
import org.json.JSONObject;

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

    public void tokenLogin(final ProgressBar progressWheel) {
        final long beginTime = SystemClock.elapsedRealtime();
        final String token = mLocalStorage.getToken();


        if (token == null) {
            long elapsedTime = SystemClock.elapsedRealtime() - beginTime;
            intentDelayHandler(null, elapsedTime);
        } else {
            mNetworkManager.tokenLogin(token, new LoginCallback() {
                @Override
                public void onSuccess(final JSONObject employee) {
                    Employee employeeMobile = null;

                    if (employee.length() != 0) {
                        try {
                            employeeMobile = new Employee(employee.getInt("mId"),
                                    employee.getString("mFullName"),
                                    employee.getString("mPhoneNumber"),
                                    employee.getString("mToken"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    final long elapsedTime = SystemClock.elapsedRealtime() - beginTime;

                    intentDelayHandler(employeeMobile, elapsedTime);
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

    private void intentDelayHandler(final Employee employee, final long elapsedTime) {
        final long delay = 1000 - elapsedTime;

        // Nýr þráður svo hægt sé að sleepa án þess að frysta UI áður en það
        // fær séns á að rendera.
        Thread thread = new Thread() {
            public void run() {
                // Bíðum bara ef networking tók minni tíma en 2 sek, svo logo fái alltaf
                // minnst 2sek skjátíma.
                if (delay  > 0) {
                    try {
                        Thread.sleep(delay);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                if (employee != null) {
                    Intent intent = new Intent(mContext, ClockActivity.class);
                    intent.putExtra("currentEmployee", employee);
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
            public void onSuccess(JSONObject employee) {
                if (employee.length() != 0) {
                    Employee employeeMobile = null;
                    try {
                        employeeMobile = new Employee(employee.getInt("mId"),
                                employee.getString("mFullName"),
                                employee.getString("mPhoneNumber"),
                                employee.getString("mToken"));

                        mLocalStorage.saveToken(employeeMobile.getToken());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    Intent intent = new Intent(mContext, ClockActivity.class);
                    intent.putExtra("currentEmployee", employeeMobile);
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


    public void resetPassword(String username) {
        // TODO Ignore this for now. Implemented later with mail system.
        mNetworkManager.resetPassword(username, new LoginCallback() {
            @Override
            public void onSuccess(JSONObject obj) {
                if (obj.length() != 0) {
                    Toast.makeText(mContext,
                            "Instructions sent by email.",
                            Toast.LENGTH_LONG).show();

                    Intent intent = new Intent (mContext, LoginActivity.class);
                    mContext.startActivity(intent);
                } else {
                    Toast.makeText(mContext,
                            "Username not on file.",
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

package com.t25.hbv601g.timerunner;

import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.t25.hbv601g.timerunner.services.LoginService;

public class LogoActivity extends AppCompatActivity {

    private long beginTime;
    private LoginService mLoginService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);

        mLoginService = new LoginService(this);

        mLoginService.isLoggedIn();

    }
}

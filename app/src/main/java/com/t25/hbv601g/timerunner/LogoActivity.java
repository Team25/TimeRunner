package com.t25.hbv601g.timerunner;

import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.t25.hbv601g.timerunner.services.LoginService;

public class LogoActivity extends AppCompatActivity {

    private LoginService mLoginService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_logo);

        mLoginService = new LoginService(this);

        mLoginService.isLoggedIn();

    }
}

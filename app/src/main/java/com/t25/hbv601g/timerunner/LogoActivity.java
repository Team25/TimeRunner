package com.t25.hbv601g.timerunner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;

import com.t25.hbv601g.timerunner.services.LoginService;

public class LogoActivity extends AppCompatActivity {

    private ProgressBar mProgressWheel;
    private LoginService mLoginService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide(); // Losna við title bar.
        setContentView(R.layout.activity_logo);

        mLoginService = new LoginService(this);

        mProgressWheel = (ProgressBar) findViewById(R.id.progress_wheel);

        mLoginService.tokenLogin(mProgressWheel);
    }
}

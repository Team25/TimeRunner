package com.t25.hbv601g.timerunner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.t25.hbv601g.timerunner.services.LoginService;


public class LoginActivity extends AppCompatActivity {

    private Button mLoginButton;
    private EditText mUsername;
    private EditText mPassword;
    private TextView mForgotPassword;

    private LoginService mLoginService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide(); // Losna við title bar.
        setContentView(R.layout.activity_login);

        mLoginButton = (Button) findViewById(R.id.login_button);
        mUsername = (EditText) findViewById(R.id.input_login_username);
        mPassword = (EditText) findViewById(R.id.input_login_password);
        mForgotPassword = (TextView) findViewById(R.id.text_login_reclaimPassword);

        mLoginService = new LoginService(this);

        mForgotPassword.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(LoginActivity.this, ResetPasswordActivity.class);
                LoginActivity.this.startActivity(intent);
            }
        });

        mLoginButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String username = mUsername.getText().toString().trim();
                String password = mPassword.getText().toString().trim();

                mLoginService.login(username, password);
            }
        });
    }
}

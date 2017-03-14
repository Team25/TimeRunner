package com.t25.hbv601g.timerunner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.t25.hbv601g.timerunner.services.LoginService;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private Button mLoginButton;
    private EditText mUsername;
    private EditText mPassword;
    private TextView mForgotPassword;

    private LoginService mLoginService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mLoginButton = (Button) findViewById(R.id.login_button);
        mUsername = (EditText) findViewById(R.id.input_login_username);
        mPassword = (EditText) findViewById(R.id.input_login_password);
        mForgotPassword = (TextView) findViewById(R.id.text_login_reclaimPassword);

        mLoginService = new LoginService(this);

        mForgotPassword.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                // new activity for this?
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

package com.t25.hbv601g.timerunner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.t25.hbv601g.timerunner.services.LoginService;

public class ResetPasswordActivity extends AppCompatActivity {

    Button mResetButton;
    EditText mUserName;

    private LoginService mLoginService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide(); // Losna við title bar.
        setContentView(R.layout.activity_reset_password);

        mLoginService = new LoginService(this);

        mUserName = (EditText) findViewById(R.id.input_reset_username);
        mResetButton = (Button) findViewById(R.id.reset_button);
        mResetButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                String username = mUserName.getText().toString().trim();

                mLoginService.resetPassword(username);
            }
        });

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {

            Intent intent = new Intent(ResetPasswordActivity.this, LoginActivity.class);
            ResetPasswordActivity.this.startActivity(intent);
            this.finish();

            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}

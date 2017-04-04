package com.t25.hbv601g.timerunner;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
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
        setupActionBar();
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
            return returnToLogin();
        }
        return super.onKeyDown(keyCode, event);
    }

    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Show the Up button in the action bar.
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("Reset password");
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            return returnToLogin();
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean returnToLogin() {
        Intent intent = new Intent(ResetPasswordActivity.this, LoginActivity.class);
        ResetPasswordActivity.this.startActivity(intent);
        this.finish();
        return true;
    }
}

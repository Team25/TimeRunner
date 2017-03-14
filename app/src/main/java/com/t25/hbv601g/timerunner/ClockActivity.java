package com.t25.hbv601g.timerunner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.t25.hbv601g.timerunner.communications.NetworkManager;
import com.t25.hbv601g.timerunner.entities.Employee;
import com.t25.hbv601g.timerunner.repositories.UserLocalStorage;

import org.w3c.dom.Text;

public class ClockActivity extends AppCompatActivity {

    private TextView mCurrentEmployeeDisplay;
    private Button mBtnDeleteToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clock);

        final UserLocalStorage localStorage = UserLocalStorage.getInstance(this);

        String token = localStorage.getToken();
        mCurrentEmployeeDisplay = (TextView) findViewById(R.id.employee_name);

        mCurrentEmployeeDisplay.setText(token);

        mBtnDeleteToken = (Button) findViewById(R.id.btn_delete_token);

        mBtnDeleteToken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (localStorage.removeToken()) {
                    mCurrentEmployeeDisplay.setText("Dude, where's your token?");
                    Toast.makeText(ClockActivity.this, "token removed", Toast.LENGTH_LONG).show();
                }
            }
        });


    }
}

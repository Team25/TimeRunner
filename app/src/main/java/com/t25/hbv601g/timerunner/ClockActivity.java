package com.t25.hbv601g.timerunner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.t25.hbv601g.timerunner.communications.NetworkManager;
import com.t25.hbv601g.timerunner.entities.Employee;
import com.t25.hbv601g.timerunner.repositories.UserLocalStorage;

import org.w3c.dom.Text;

public class ClockActivity extends AppCompatActivity {

    private TextView mCurrentEmployeeDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clock);

        UserLocalStorage localStorage = UserLocalStorage.getInstance(this);

        String token = localStorage.getToken();
        mCurrentEmployeeDisplay = (TextView) findViewById(R.id.employee_name);

         mCurrentEmployeeDisplay.setText(token);
    }
}

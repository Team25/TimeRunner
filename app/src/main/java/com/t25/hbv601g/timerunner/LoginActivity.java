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

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    Button mLoginButton;
    EditText mUsername;
    EditText mPassword;
    TextView mForgotPassword;

    public static final String LOGIN_URL = "http://10.0.2.2:8080/applogin";

    public static final String KEY_USERNAME="username";
    public static final String KEY_PASSWORD="password";


    private String username;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mLoginButton = (Button) findViewById(R.id.login_button);
        mUsername = (EditText) findViewById(R.id.input_login_username);
        mPassword = (EditText) findViewById(R.id.input_login_password);
        mForgotPassword = (TextView) findViewById(R.id.text_login_reclaimPassword);

        mForgotPassword.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                // new activity for this?
            }
        });


        mLoginButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                userLogin();
                /*JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,LOGIN_URL,null,
                        new Response.Listener<JSONObject>(){
                            @Override
                            public void onResponse(JSONObject response){


                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error){

                    }
                })*/

            }
        });

    }

    private void userLogin() {
        username = mUsername.getText().toString().trim();
        password = mPassword.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, LOGIN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.trim().equals("success")){
                            Toast.makeText(LoginActivity.this,response,Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(LoginActivity.this,response,Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(LoginActivity.this,error.toString(),Toast.LENGTH_LONG ).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String,String>();
                map.put(KEY_USERNAME,username);
                map.put(KEY_PASSWORD,password);
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}

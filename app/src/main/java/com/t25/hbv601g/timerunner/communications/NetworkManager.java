package com.t25.hbv601g.timerunner.communications;


import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.t25.hbv601g.timerunner.entities.Employee;
import com.t25.hbv601g.timerunner.repositories.UserLocalStorage;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Óli Legend on 13.3.2017.
 */

// Singleton klasi sem lifir á meðan appið lifir, þ.e
// þó svo activity deyi, t.d. við device rotation.
public class NetworkManager {

    private final String mServerUrl= "http://10.0.2.2:8080/";
    private String mToken;
    private UserLocalStorage mLocalStorage;
    private static RequestQueue mQueue;
    private static NetworkManager mInstance;
    private Context mContext;


    private NetworkManager(Context context) {
        // tekur inn activity context til þess að hægt sé að instantiate-a
        // RequestQueue með application context, sem heldur queue-inu á lífi
        // á meðan appið lifir.
        mContext = context; // getapplicationcontext ?
        mQueue = getRequestQueue();
        mLocalStorage = UserLocalStorage.getInstance(context);
    }

    public static synchronized NetworkManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new NetworkManager(context);
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mQueue == null) {
            mQueue = Volley.newRequestQueue(mContext.getApplicationContext());
        }
        return mQueue;
    }

    public void login(String username, String password, final VolleyCallback callback) {
        String loginPath = String.format("applogin?userName=%s&password=%s", username, password);

        StringRequest stringRequest = new StringRequest
                (Request.Method.GET, mServerUrl + loginPath, new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response){
                        mLocalStorage.saveToken(response);

                        callback.onSuccess();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error){
                        callback.onFailure(error.toString());
                    }
                }
                );
        mQueue.add(stringRequest);

    }

}

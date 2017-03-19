package com.t25.hbv601g.timerunner.communications;


import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.t25.hbv601g.timerunner.R;
import com.t25.hbv601g.timerunner.entities.Entry;
import com.t25.hbv601g.timerunner.repositories.UserLocalStorage;

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

    public void isValidToken(String token, final LoginCallback callback) {
        String tokenPath = String.format("apptoken?token=%s", token);

        JsonObjectRequest jsonRequest = new JsonObjectRequest
                (Request.Method.GET, mServerUrl + tokenPath, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response){
                        try {
                            callback.onSuccess(response.getBoolean("valid"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                            callback.onFailure(mContext.getString(R.string.json_error));
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error){
                        callback.onFailure(error.toString());
                    }
                }
                );
        mQueue.add(jsonRequest);
    }

    public void login(String username, String password, final LoginCallback callback) {
        String loginPath = String.format("applogin?userName=%s&password=%s", username, password);

        StringRequest stringRequest = new StringRequest
                (Request.Method.GET, mServerUrl + loginPath, new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response){
                        if (!response.equals("")) {
                            mLocalStorage.saveToken(response);
                            callback.onSuccess(true);
                        } else callback.onSuccess(false);
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

    public void resetPassword(String username, final LoginCallback callback) {
        //TODO
        callback.onFailure("error");
    }

    public void getOpenClockEntry(String token, final ClockCallback callback) {
        String clockPath = Uri.parse(mServerUrl)
                .buildUpon()
                .appendPath("appclockstatus") //careful that controller listens to this address
                .appendQueryParameter("token", token)
                .build().toString();

        StringRequest stringRequest = new StringRequest
                (Request.Method.GET, clockPath, new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response){
                        if (!response.equals("")) {
                            Gson gson = new Gson();
                            Entry entry = gson.fromJson(response, Entry.class);
                            callback.onSuccess(entry);
                        } else callback.onSuccess(null);
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

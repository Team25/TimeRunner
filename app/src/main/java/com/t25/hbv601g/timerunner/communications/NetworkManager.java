package com.t25.hbv601g.timerunner.communications;


import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.t25.hbv601g.timerunner.entities.Entry;
import com.t25.hbv601g.timerunner.repositories.UserLocalStorage;

import org.json.JSONObject;

/**
 * Created by Óli Legend on 13.3.2017.
 */

// Singleton klasi sem lifir á meðan appið lifir, þ.e
// þó svo activity deyi, t.d. við device rotation.

// Flest föll hér sjá bara um networking og eru bara passthrough fyrir gögn.
// Gögn koma sem parametrar frá services og gögnum frá server er skilað til
// services Með callbacks.
public class NetworkManager {

    private final String mServerUrl= "http://timethief.biz:8080/";
    private String mToken;
    private UserLocalStorage mLocalStorage;
    private static RequestQueue mQueue;
    private static NetworkManager mInstance;
    private Context mContext;


    private NetworkManager(Context context) {
        // tekur inn activity context til þess að hægt sé að instantiate-a
        // RequestQueue með application context.
        mContext = context;
        mQueue = getRequestQueue();
        mLocalStorage = UserLocalStorage.getInstance(context);
    }

    public static synchronized NetworkManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new NetworkManager(context);
        }
        return mInstance;
    }

    // RequestQueue er biðröð sem öll http request eru sett í. RequestQueue-ið sér
    // um að þau séu send út í réttri röð. Kemur úr Volley pakkanum.
    public RequestQueue getRequestQueue() {
        if (mQueue == null) {
            mQueue = Volley.newRequestQueue(mContext.getApplicationContext());
        }
        return mQueue;
    }

    // Single sign on. Notum token til að logga inn ef tokenið er til/gilt.
    public void tokenLogin(String token, final LoginCallback callback) {
        String tokenPath = String.format("apptoken?token=%s", token);

        JsonObjectRequest jsonRequest = new JsonObjectRequest
                (Request.Method.GET, mServerUrl + tokenPath, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response){
                        callback.onSuccess(response);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error){
                        error.printStackTrace();
                        callback.onFailure(error.toString());
                    }
                }
                );
        mQueue.add(jsonRequest);
    }


    public void login(String username, String password, final LoginCallback callback) {
        String loginPath = String.format("applogin?userName=%s&password=%s", username, password);

        JsonObjectRequest jsonRequest = new JsonObjectRequest
                (Request.Method.GET, mServerUrl + loginPath, null,new Response.Listener<JSONObject>(){
                    @Override
                    public void onResponse(JSONObject response){
                        callback.onSuccess(response);
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

    public void resetPassword(String username, final LoginCallback callback) {
        //TODO Unimplemented. Ignore for now.
        // callback.onFailure("error");
    }

    // Athugum hvort að user sé clocked in.
    public void getOpenClockEntry(String token, final ClockCallback callback) {
        String clockPath = Uri.parse(mServerUrl)
                .buildUpon()
                .appendPath("appentry") //careful that controller listens to this address
                .appendQueryParameter("token", token)
                .build().toString();

        StringRequest stringRequest = new StringRequest
                (Request.Method.GET, clockPath, new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response){
                        Gson gson = new Gson();
                        Log.e("zzz", response);
                        Entry entry = gson.fromJson(response, Entry.class);
                        Log.e("zzz", response);
                        if (entry != null)
                            callback.onSuccess(entry);
                        else
                            callback.onSuccess(null);
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

    // CHANGE TO POST INSTEAD OF GET
    public void clockInOut(String token, Entry entry, final ClockCallback callback) {
        String clockPath;
        // TODO make this less redundant.
        if(entry == null) {
            clockPath = Uri.parse(mServerUrl)
                    .buildUpon()
                    .appendPath("appclock") //careful that controller listens to this address
                    .appendQueryParameter("token", token)
                    .appendQueryParameter("department", "Overlord")
                    .build().toString();
        } else {
            clockPath = Uri.parse(mServerUrl)
                    .buildUpon()
                    .appendPath("appclock") //careful that controller listens to this address
                    .appendQueryParameter("token", token)
                    .appendQueryParameter("department", entry.getDepartment())
                    .build().toString();
        }

        StringRequest stringRequest = new StringRequest
                (Request.Method.GET, clockPath, new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response){
                        Gson gson = new Gson();
                        Entry entry = gson.fromJson(response, Entry.class);
                        if (entry != null)
                            callback.onSuccess(entry);
                        else
                            callback.onSuccess(null);
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

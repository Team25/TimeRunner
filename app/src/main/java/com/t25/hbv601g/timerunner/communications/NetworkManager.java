package com.t25.hbv601g.timerunner.communications;


import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.t25.hbv601g.timerunner.AttendanceListFragment;
import com.t25.hbv601g.timerunner.entities.Conversation;
import com.t25.hbv601g.timerunner.entities.Employee;
import com.t25.hbv601g.timerunner.entities.Entry;
import com.t25.hbv601g.timerunner.repositories.UserLocalStorage;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Óli Legend on 13.3.2017.
 */

// Singleton klasi sem lifir á meðan appið lifir, þ.e
// þó svo activity deyi, t.d. við device rotation.

// Flest föll hér sjá bara um networking og eru bara passthrough fyrir gögn.
// Gögn koma sem parametrar frá services og gögnum frá server er skilað til
// services Með callbacks.
public class NetworkManager {

    //private final String mServerUrl= "http://timethief.biz:8080/";
    private final String mServerUrl = "http://10.0.2.2:8080/";
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
        String tokenPath = String.format("/apptoken?token=%s", token);

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
        String loginPath = String.format("/applogin?userName=%s&password=%s", username, password);

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
        String resetPath = String.format("/appreset?userName=%s", username);

        JsonObjectRequest jsonRequest = new JsonObjectRequest
                (Request.Method.GET, mServerUrl + resetPath, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        callback.onSuccess(response);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callback.onFailure(error.toString());
                    }
                });


        mQueue.add(jsonRequest);

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

    public void getUserList(String token, final EmployeeListCallback callback) {

        String employeeListPath;
        employeeListPath = Uri.parse(mServerUrl)
                .buildUpon()
                .appendPath("appemployeelist") //careful that controller listens to this address
                .appendQueryParameter("token", token)
                .build().toString();

        StringRequest stringRequest = new StringRequest
                (Request.Method.GET, employeeListPath, new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response){
                        Gson gson = new Gson();
                        Type listType = new TypeToken<List<Employee>>(){}.getType();
                        List<Employee> employeeList = gson.fromJson(response, listType);
                        callback.onSuccess(employeeList);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error){
                        callback.onFailure(error.toString());
                    }
                }
                );
        mQueue.add(stringRequest);
        //TODO: finish getting employee list from network (make sure it works on server side also)
    }


    // coupled with ConversationFragment
    /*
     * conversationistId is the id of the employee that the user wants to message
     *
     */
    public void getConversation(String token, long conversationistId, final ConversationCallback callback) {
        String employeeListPath;
        employeeListPath = Uri.parse(mServerUrl)
                .buildUpon()
                .appendPath("appgetconversation") //careful that controller listens to this address
                .appendQueryParameter("token", token)
                .appendQueryParameter("conversationist", conversationistId+"")
                .build().toString();
        Log.e("stuff", "getUserList: Do we get here?");
        StringRequest stringRequest = new StringRequest
                (Request.Method.GET, employeeListPath, new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response){
                        Gson gson = new Gson();
                        Log.e("jsonString","json: " + response);
                        Conversation conversation = gson.fromJson(response, Conversation.class);
                        Log.e("tag", "onResponse: " + (conversation.getId()));

                        callback.onSuccess(conversation);
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

    public void getEntryList(String token, final EntryListCallback callback) {
        String entryListPath;
        entryListPath = Uri.parse(mServerUrl)
                .buildUpon()
                .appendPath("appgetentries") //careful that controller listens to this address
                .appendQueryParameter("token", token)
                .build().toString();
        Log.e("stuff", "getUserList: Do we get here?");
        StringRequest stringRequest = new StringRequest
                (Request.Method.GET, entryListPath, new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response){
                        Log.e("networkManager", response);
                        Gson gson = new Gson();
                        Type listType = new TypeToken<List<Entry>>(){}.getType();
                        List<Entry> entryList = gson.fromJson(response, listType);
                        callback.onSuccess(entryList);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error){
                        callback.onFailure(error.toString());
                    }
                }
                );
        Log.e("Networkmanager","Do we finish the getEntryList call?");
        mQueue.add(stringRequest);
    }
}

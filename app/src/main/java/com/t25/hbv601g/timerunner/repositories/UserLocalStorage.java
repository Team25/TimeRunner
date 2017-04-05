package com.t25.hbv601g.timerunner.repositories;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.t25.hbv601g.timerunner.R;
import com.t25.hbv601g.timerunner.entities.Settings;

import org.json.JSONObject;

/**
 * Created by dingo on 13.3.2017.
 * This UserLocalStorage is a singleton
 */

public class UserLocalStorage {

    private static UserLocalStorage instance = null;
    private SharedPreferences mUserLocalDatabase;
    private final String TOKEN_KEY = "token_key";
    private final String SETTINGS_KEY = "settings_key";
    private final String CLOCK_IN_TIMER_KEY = "timer_key";

    private UserLocalStorage(Context context) {
        //TODO make local storage work from a nonactivity class. consider sql if it turns out to be difficult
        mUserLocalDatabase = context.getSharedPreferences("com.t25.hbv601g.timeRunner.UserPreferences",context.MODE_PRIVATE);
    }

    public static UserLocalStorage getInstance(Context context) {
        if(instance == null) {
            instance = new UserLocalStorage(context);
        }
        return instance;
    }

    public void saveClockInTime(String clockInTime) {
        mUserLocalDatabase.edit()
                .putString(CLOCK_IN_TIMER_KEY, clockInTime)
                .apply();
    }

    public String getClockInTime() {
        return mUserLocalDatabase.getString(CLOCK_IN_TIMER_KEY, null);
    }

    /**
     * Saves the verification token that shall be sent with every http request to server
     * @param token
     */
    public void saveToken(String token){
        mUserLocalDatabase.edit()
                .putString(TOKEN_KEY, token)
                .apply();
    }

    /**
     *
     * @return Token if it is found, else return null
     */
    public String getToken(){
        return mUserLocalDatabase.getString(TOKEN_KEY,null);
    }

    public boolean removeToken() {
        mUserLocalDatabase.edit()
                .remove(TOKEN_KEY)
                .apply();

        // TODO taka þetta burt og breyta return type í void, bara fyrir test
        if (getToken() == null) return true;
        else return false;
    }

    public void saveSettings(Settings settings){
        mUserLocalDatabase.edit()
                .putString(SETTINGS_KEY, new Gson().toJson(settings))
                .apply();
    }

    public Settings getSettings(){
        String s = mUserLocalDatabase.getString(SETTINGS_KEY, null);
        if(s.equals(null))
            return new Settings();
        else
            return new Gson().fromJson(s, Settings.class);
    }

}

package com.t25.hbv601g.timerunner.entities;

import java.io.Serializable;

/**
 * Created by dingo on 13.3.2017.
 */

public class Employee {

    private long mId;
    private String mFullName;
    private String mPhoneNumber;
    private boolean mClockedIn;
    private String mToken;

    public Employee(long id, String fullName, String phoneNumber, boolean clockedIn, String token) {
        mId = id;
        mFullName = fullName;
        mPhoneNumber = phoneNumber;
        mClockedIn = clockedIn;
        mToken = token;

    }

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
    }

    public String getFullName() {
        return mFullName;
    }

    public void setFullName(String fullName) {
        mFullName = fullName;
    }

    public String getPhoneNumber() {
        return mPhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        mPhoneNumber = phoneNumber;
    }

    public boolean isClockedIn() {
        return mClockedIn;
    }

    public void setClockedIn(boolean clockedIn) {
        mClockedIn = clockedIn;
    }

    public String getToken() { return mToken; }

    public void setToken(String token) { mToken = token; }
}

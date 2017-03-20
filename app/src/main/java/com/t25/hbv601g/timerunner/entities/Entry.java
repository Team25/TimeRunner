package com.t25.hbv601g.timerunner.entities;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by dingo on 13.3.2017.
 */

public class Entry {

    private long mId;
    private List<Comment> mComments;
    private String mDepartment;
    private Timestamp mInTime;
    private Timestamp mOutTime;
    private boolean mVerified;

    public Entry(long id, List<Comment> comments, String department, Timestamp inTime, Timestamp outTime, boolean isVerified) {
        mId = id;
        mComments = comments;
        mDepartment = department;
        mInTime = inTime;
        mOutTime = outTime;
        mVerified = isVerified;
    }

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
    }

    public List<Comment> getComments() {
        return mComments;
    }

    public void setComments(List<Comment> comments) {
        mComments = comments;
    }

    public String getDepartment() {
        return mDepartment;
    }

    public void setDepartment(String department) {
        mDepartment = department;
    }

    public Timestamp getInTime() {
        return mInTime;
    }

    public void setInTime(Timestamp inTime) {
        mInTime = inTime;
    }

    public Timestamp getOutTime() {
        return mOutTime;
    }

    public void setOutTime(Timestamp outTime) {
        mOutTime = outTime;
    }

    public boolean isVerified() {
        return mVerified;
    }

    public void setVerified(boolean verified) {
        mVerified = verified;
    }
}

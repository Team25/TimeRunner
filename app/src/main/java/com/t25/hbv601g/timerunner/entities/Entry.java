package com.t25.hbv601g.timerunner.entities;

import java.util.List;

/**
 * Created by dingo on 13.3.2017.
 */

public class Entry {

    private long mId;
    private List<Comment> mComments;
    private String mDepartment;
    private String mInTime;
    private String mOutTime;
    private boolean mVerified;

    public Entry(long id, List<Comment> comments, String department, String inTime, String outTime, boolean verified) {
        this.mId = id;
        this.mComments = comments;
        this.mDepartment = department;
        this.mInTime = inTime;
        this.mOutTime = outTime;
        this.mVerified = verified;
    }

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        this.mId = id;
    }

    public List<Comment> getComments() {
        return mComments;
    }

    public void setComments(List<Comment> comments) {
        this.mComments = comments;
    }

    public String getDepartment() {
        return mDepartment;
    }

    public void setDepartment(String department) {
        this.mDepartment = department;
    }

    public String getInTime() {
        return mInTime;
    }

    public void setInTime(String inTime) {
        this.mInTime = inTime;
    }

    public String getOutTime() {
        return mOutTime;
    }

    public void setOutTime(String outTime) {
        this.mOutTime = outTime;
    }

    public boolean isVerified() {
        return mVerified;
    }

    public void setVerified(boolean verified) {
        this.mVerified = verified;
    }
}

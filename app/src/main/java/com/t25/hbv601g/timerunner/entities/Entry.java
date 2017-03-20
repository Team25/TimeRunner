package com.t25.hbv601g.timerunner.entities;

import java.util.List;

/**
 * Created by dingo on 13.3.2017.
 */

public class Entry {

    private long id;
    private List<Comment> comments;
    private String department;
    private String inTime;
    private String outTime;
    private boolean verified;

    public Entry(long id, List<Comment> comments, String department, String inTime, String outTime, boolean verified) {
        this.id = id;
        this.comments = comments;
        this.department = department;
        this.inTime = inTime;
        this.outTime = outTime;
        this.verified = verified;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getInTime() {
        return inTime;
    }

    public void setInTime(String inTime) {
        this.inTime = inTime;
    }

    public String getOutTime() {
        return outTime;
    }

    public void setOutTime(String outTime) {
        this.outTime = outTime;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }
}

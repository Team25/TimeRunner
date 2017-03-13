package com.t25.hbv601g.timerunner.entities;

/**
 * Created by dingo on 13.3.2017.
 */

public class Comment {

    private long mId;
    private Entry mEntry;
    private Employee mEmployee;
    private String mText;

    public Comment(long id, Entry entry, Employee employee, String text) {
        mId = id;
        mEntry = entry;
        mEmployee = employee;
        mText = text;
    }

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
    }

    public Entry getEntry() {
        return mEntry;
    }

    public void setEntry(Entry entry) {
        mEntry = entry;
    }

    public Employee getEmployee() {
        return mEmployee;
    }

    public void setEmployee(Employee employee) {
        mEmployee = employee;
    }

    public String getText() {
        return mText;
    }

    public void setText(String text) {
        mText = text;
    }
}

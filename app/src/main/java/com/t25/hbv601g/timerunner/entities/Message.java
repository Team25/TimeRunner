package com.t25.hbv601g.timerunner.entities;

import java.sql.Timestamp;

/**
 * Created by dingo on 13.3.2017.
 */

public class Message {

    private long mId;
    private Conversation mConversation;
    private Employee mSender;
    private Timestamp mSentTime;
    private String mText;

    public Message(long id, Conversation conversation, Employee sender, Timestamp sentTime, String text){
        this.mId = id;
        this.mConversation = conversation;
        this.mSender = sender;
        this.mSentTime = sentTime;
        this.mText = text;
    }

    public long getId() {
        return mId;
    }

    public void setId(long mId) {
        this.mId = mId;
    }

    public Conversation getConversation() {
        return mConversation;
    }

    public void setConversation(Conversation mConversation) {
        this.mConversation = mConversation;
    }

    public Employee getSender() {
        return mSender;
    }

    public void setSender(Employee mSender) {
        this.mSender = mSender;
    }

    public Timestamp getSentTime() {
        return mSentTime;
    }

    public void setSentTime(Timestamp mSentTime) {
        this.mSentTime = mSentTime;
    }

    public String getText() {
        return mText;
    }

    public void setText(String mText) {
        this.mText = mText;
    }


}

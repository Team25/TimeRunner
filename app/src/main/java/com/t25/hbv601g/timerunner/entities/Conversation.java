package com.t25.hbv601g.timerunner.entities;

import java.util.List;

/**
 * Created by dingo on 13.3.2017.
 */

public class Conversation {
    /**
     * Id of the conversation, matches servers conversation Id
     */
    private long mId;
    /**
     * list of employees that are participating in the conversation
     */
    private List<Employee> mMembers;

    private List<Message> mMessages;
    /**
     * number of messages that have fetched from server
     */
    private int mMessageCount;

    /**
     * Constructor for conversation
     * @param id is the id of the conversation
     * @param members are the participating members of the conversation
     * @param messageCount is the number of messages that we currently hold
     */
    public Conversation(long id, List<Employee> members, int messageCount){
        this.mId = id;
        this.mMembers = members;
        this.mMessageCount = messageCount;
    }

    public long getId() {
        return mId;
    }

    public void setId(long mId) {
        this.mId = mId;
    }

    public List<Employee> getMembers() {
        return mMembers;
    }

    public void setMembers(List<Employee> mMembers) {
        this.mMembers = mMembers;
    }

    public int getMessageCount() {
        return mMessageCount;
    }

    public void setMessageCount(int mMessageCount) {
        this.mMessageCount = mMessageCount;
    }


}

package com.t25.hbv601g.timerunner.communications;

import com.t25.hbv601g.timerunner.entities.Conversation;


/**
 * Created by dingo on 5.4.2017.
 */

public interface ConversationCallback {

    void onSuccess(Conversation conversation);

    void onFailure(String error);
}

package com.t25.hbv601g.timerunner;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.t25.hbv601g.timerunner.entities.Conversation;

public class ConversationListActivity extends AppCompatActivity
        implements ConversationListFragment.Callbacks {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);

        ConversationListFragment conversationList = new ConversationListFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.conversation, conversationList).commit();
    }

    @Override
    public void onConversationSelected(long conversationistId) {
        // ignoring two pane interfaces for now
        Intent intent = ConversationActivity.newIntent(this, conversationistId);
        startActivity(intent);

    }
}

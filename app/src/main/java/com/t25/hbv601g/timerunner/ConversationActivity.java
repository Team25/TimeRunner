package com.t25.hbv601g.timerunner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ConversationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);

        ConversationListFragment conversationList = new ConversationListFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.conversation, conversationList).commit();
    }
}

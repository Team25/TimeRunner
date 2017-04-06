package com.t25.hbv601g.timerunner;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.UUID;

public class ConversationActivity extends AppCompatActivity {

    private static final String EXTRA_CONVERSATION_ID = "com.t25.hbv601g.timerunner.conversationintent.conversation_id";

    public static Intent newIntent(Context context, long conversationId){
        Intent intent = new Intent(context, ConversationActivity.class);
        intent.putExtra(EXTRA_CONVERSATION_ID, conversationId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);

        // kannski þarf ég að nota UUID hérna?
        long conversationId = (long) getIntent().getSerializableExtra(EXTRA_CONVERSATION_ID);

        ConversationFragment conversationFragment = ConversationFragment.newInstance(conversationId);
        getSupportFragmentManager().beginTransaction().add(R.id.conversation, conversationFragment).commit();



    }
}

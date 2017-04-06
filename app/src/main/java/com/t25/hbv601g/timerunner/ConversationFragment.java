package com.t25.hbv601g.timerunner;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.t25.hbv601g.timerunner.entities.Conversation;
import com.t25.hbv601g.timerunner.services.InteractionService;


public class ConversationFragment extends Fragment {

    private static final String ARG_EMPLOYEE_ID = "employee_id";
    private Conversation mConversation;
    private long temporaryVariableForTesting;
    private InteractionService mInteractionService;

    public static ConversationFragment newInstance(long employeeId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_EMPLOYEE_ID, employeeId);
        ConversationFragment fragment = new ConversationFragment();
        fragment.setArguments(args);
        return fragment;
    }


    public ConversationFragment() {

    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);

    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        mInteractionService = new InteractionService(this.getContext());
        View view = inflater.inflate(R.layout.fragment_conversation, container, false);
        TextView text = (TextView) view.findViewById(R.id.conversation_id);
        mInteractionService.openConversation(text, (long) getArguments().getSerializable(ARG_EMPLOYEE_ID));
        return view;
    }
}

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

import com.t25.hbv601g.timerunner.services.InteractionService;


public class ConversationFragment extends Fragment {

    InteractionService mInteractionService;

    public ConversationFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.fragment_conversation, container, false);

        return view;
    }
}

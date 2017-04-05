package com.t25.hbv601g.timerunner;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.t25.hbv601g.timerunner.services.InteractionService;


/**
 * A simple {@link Fragment} subclass.
 */
public class ConversationListFragment extends Fragment {

    InteractionService mInteractionService;


    public ConversationListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        mInteractionService = new InteractionService(getActivity());

        View view = inflater.inflate(R.layout.fragment_conversation_list, container, false);

        //String[] menuItems = {"lala","lulu", "lale"};

        ListView listView = (ListView) view.findViewById(R.id.conversation_list_view);

        mInteractionService.getEmployeeList(listView);

        /*
        ArrayAdapter<String> listViewAdapter = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                menuItems
        );

        listView.setAdapter(listViewAdapter);
        */

        return view;
    }

}

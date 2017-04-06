package com.t25.hbv601g.timerunner;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.telecom.Call;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.t25.hbv601g.timerunner.entities.Conversation;
import com.t25.hbv601g.timerunner.services.InteractionService;


/**
 * A simple {@link Fragment} subclass.
 */
public class ConversationListFragment extends Fragment {

    private InteractionService mInteractionService;
    private Callbacks mCallbacks;


    public ConversationListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        mCallbacks = (Callbacks) context;
    }

    @Override
    public void onDetach(){
        super.onDetach();
        mCallbacks = null;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        mInteractionService = new InteractionService(getActivity());

        View view = inflater.inflate(R.layout.fragment_conversation_list, container, false);


        ListView listView = (ListView) view.findViewById(R.id.conversation_list_view);

        mInteractionService.getEmployeeList(listView, mCallbacks);

        return view;
    }

    public interface Callbacks {
        void onConversationSelected(long conversationistId);
    }

}

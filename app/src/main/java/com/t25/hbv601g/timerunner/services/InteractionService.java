package com.t25.hbv601g.timerunner.services;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.t25.hbv601g.timerunner.ConversationListFragment;
import com.t25.hbv601g.timerunner.EmployeeAdapter;
import com.t25.hbv601g.timerunner.R;
import com.t25.hbv601g.timerunner.communications.ConversationCallback;
import com.t25.hbv601g.timerunner.communications.EmployeeListCallback;
import com.t25.hbv601g.timerunner.communications.NetworkManager;
import com.t25.hbv601g.timerunner.entities.Conversation;
import com.t25.hbv601g.timerunner.entities.Employee;
import com.t25.hbv601g.timerunner.repositories.UserLocalStorage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by dingo on 4.4.2017.
 */

public class InteractionService {

    ArrayList<Employee> mEmployeeList;
    Conversation mCurrentConversation;
    NetworkManager mNetworkManager;
    UserLocalStorage mLocalStorage;
    Context mContext;

    public InteractionService(Context context){
        this.mEmployeeList = null;
        this.mCurrentConversation = null;
        mNetworkManager = NetworkManager.getInstance(context);
        mLocalStorage = UserLocalStorage.getInstance(context);
        mContext = context;

    }

    // coupled with ConversationListFragment
    public void getEmployeeList(final ListView listView, final ConversationListFragment.Callbacks conversationCallback){
        String token = mLocalStorage.getToken();

        mNetworkManager.getUserList(token, new EmployeeListCallback(){

            @Override
            public void onSuccess(List<Employee> employeeList) {

                mEmployeeList = (ArrayList<Employee>)employeeList;
                //TODO fill listView with employees.

                final EmployeeAdapter adapter = new EmployeeAdapter(mContext, mEmployeeList);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                        /*Toast.makeText(mContext.getApplicationContext(),
                                "Click ListItem Number " + position +" : "+id + " ::: " + adapter.getItem(position).getId(), Toast.LENGTH_LONG)
                                .show();
                                */
                        conversationCallback.onConversationSelected(adapter.getItem(position).getId());

                    }
                });

            }

            @Override
            public void onFailure(String error) {
                Toast.makeText(mContext,
                        mContext.getString(R.string.server_error), Toast.LENGTH_LONG).show();
            }
        });


    }

    /*
     *
     * employeeId is the id of the other participant in the conversation (not current user)
     */
    public void openConversation(final TextView textView, long employeeId){
        String token = mLocalStorage.getToken();

        mNetworkManager.getConversation(token, employeeId, new ConversationCallback(){

            @Override
            public void onSuccess(Conversation conversation) {
                textView.setText(conversation.getId()+"");

                //TODO make callback get messages to display them.
            }

            @Override
            public void onFailure(String error) {
                Toast.makeText(mContext,
                        mContext.getString(R.string.server_error), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void getMessages(){

    }

    public void sendMessage(){

    }
}

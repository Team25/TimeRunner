package com.t25.hbv601g.timerunner.services;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ListView;
import android.widget.Toast;

import com.t25.hbv601g.timerunner.EmployeeAdapter;
import com.t25.hbv601g.timerunner.R;
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
    public void getEmployeeList(final ListView listView){
        String token = mLocalStorage.getToken();

        /*
        Employee employee1 = new Employee(1,"Andri Valur","661-5216","token");
        Employee employee2 = new Employee(1,"Örn","661-5216","token");
        Employee employee3 = new Employee(1,"Óli","661-5216","token");
        Employee employee4 = new Employee(1,"Pétur","661-5216","token");
        ArrayList<Employee> list = new ArrayList<Employee>();

        list.add(employee1);
        list.add(employee2);
        list.add(employee3);
        list.add(employee4);

        EmployeeAdapter adapter = new EmployeeAdapter(mContext, list);
        listView.setAdapter(adapter);
        */

        mNetworkManager.getUserList(token, new EmployeeListCallback(){

            @Override
            public void onSuccess(List<Employee> employeeList) {

                mEmployeeList = (ArrayList<Employee>)employeeList;
                //TODO fill listView with employees.

                EmployeeAdapter adapter = new EmployeeAdapter(mContext, mEmployeeList);
                listView.setAdapter(adapter);

            }

            @Override
            public void onFailure(String error) {
                Toast.makeText(mContext,
                        mContext.getString(R.string.server_error), Toast.LENGTH_LONG).show();
            }
        });


    }

    public void openConversation(){

    }

    public void getMessages(){

    }

    public void sendMessage(){

    }
}

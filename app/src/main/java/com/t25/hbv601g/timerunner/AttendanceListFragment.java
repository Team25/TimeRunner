package com.t25.hbv601g.timerunner;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.t25.hbv601g.timerunner.communications.EntryListCallback;
import com.t25.hbv601g.timerunner.communications.NetworkManager;
import com.t25.hbv601g.timerunner.entities.Entry;
import com.t25.hbv601g.timerunner.repositories.UserLocalStorage;
import com.t25.hbv601g.timerunner.services.InteractionService;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class AttendanceListFragment extends Fragment {


    private NetworkManager mNetworkManager;
    private UserLocalStorage mLocalStorage;
    private ArrayList<Entry> mEntryList;
    private AttendanceListFragment.Callbacks mCallbacks;


    public AttendanceListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        mCallbacks = (AttendanceListFragment.Callbacks) context;
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

        mNetworkManager = NetworkManager.getInstance(getContext());
        mLocalStorage = UserLocalStorage.getInstance(getContext());

        View view = inflater.inflate(R.layout.fragment_attendance_list, container, false);


        final ListView listView = (ListView) view.findViewById(R.id.entry_list_view);

        String token = mLocalStorage.getToken();
        mNetworkManager.getEntryList(token, new EntryListCallback() {
            @Override
            public void onSuccess(List<Entry> entries) {
                Log.e("AttendanceListFragment", (entries==null)+"");
                mEntryList = (ArrayList<Entry>)entries;
                final EntryAdapter adapter = new EntryAdapter(getContext(), mEntryList);
                Log.e("AttendanceListFragment", "onSuccess: number 2");
                listView.setAdapter(adapter);
                Log.e("AttendanceListFragment", "onSuccess: number 3");
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                        mCallbacks.onEntrySelected(adapter.getItem(position));

                    }
                });
            }

            @Override
            public void onFailure(String error) {
                Toast.makeText(getContext(),
                        getContext().getString(R.string.server_error), Toast.LENGTH_LONG).show();
            }
        });



        return view;
    }



    public interface Callbacks {
        void onEntrySelected(Entry entry);
    }



}

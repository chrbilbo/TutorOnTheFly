package com.tonikamitv.loginregister;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by tylweiss on 11/22/2015.
 */
public class TabFragment1 extends Fragment implements View.OnClickListener{
    User user;
    EditText messageText;
    Button button, syncButton;
    View container;
    ArrayList<String> userMessages = new ArrayList<String>();

    ListView theListView;
    final ListAdapter theAdapter = null; // new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, userMessages);
    //UserLocalStore userLocalStore;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);

        View rootView = inflater.inflate(R.layout.tab_fragment_1 ,null);
        userMessages.add("Message1");
        userMessages.add("Message2");
        userMessages.add("Message10000");

        ListAdapter theAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, userMessages);
        theListView = (ListView) rootView.findViewById(R.id.listView);

        theListView.setAdapter(theAdapter);

        theListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Toast.makeText(getActivity(), "Messsssge Clicked!",
                        Toast.LENGTH_LONG).show();

            }
        });


        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        button = (Button) view.findViewById(R.id.btnPostMessage);
        syncButton = (Button)view.findViewById(R.id.btnSyncMessage);
        messageText = (EditText) view.findViewById(R.id.tutorMessageInput);
        container = view.findViewById(R.id.fragmentContainer);
        button.setOnClickListener(this);
        syncButton.setOnClickListener(this);
        user = ((MainActivity)getActivity()).getCurrentUser();

        //closeKeyboard(getActivity(), messageText.getWindowToken());
        //view.findViewById(R.id.btnSyncMessage).requestFocus();

    }

    public void postMessageClicked(){

        ServerRequests serverRequest = new ServerRequests(getActivity());

        String message = messageText.getText().toString();

        serverRequest.storeUserMessageInBackground(user, message, new GetUserCallback() {
            @Override
            public void done(User returnedUser) {
                if (returnedUser == null) {
                    // showErrorMessage();
                } else {
                    //logUserIn(returnedUser);
                }

            }
        });
        messageText.getText().clear();

        Toast.makeText(getActivity(), "Posting The Message!",
                Toast.LENGTH_LONG).show();

    }

    public void syncMessageClicked(){

        ServerRequests serverRequest = new ServerRequests(getActivity());
       // Toast.makeText(getActivity(), "Seeeeinking the POSTSSS!!!!! =)",
         //       Toast.LENGTH_LONG).show();


        String message = messageText.getText().toString();

        serverRequest.fetchMessagesAsyncTask(new GetMessageCallback() {
            @Override
            public void done(Message returnedMessages) {
                if (returnedMessages == null) {
                    // showErrorMessage();
                } else {

                    userMessages.add(returnedMessages.message);
                    ListAdapter theAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, userMessages);

                    theListView.setAdapter(theAdapter);

                    theListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                            Toast.makeText(getActivity(), "Messsssge Clicked!",
                                    Toast.LENGTH_LONG).show();

                        }
                    });

                    Toast.makeText(getActivity(), returnedMessages.message,
                            Toast.LENGTH_LONG).show();
                }

            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.btnPostMessage:
                postMessageClicked();
                break;
            case R.id.btnSyncMessage:
                syncMessageClicked();
                break;

        }
    }

    public static void closeKeyboard(Context c, IBinder windowToken) {
        InputMethodManager mgr = (InputMethodManager) c.getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(windowToken, 0);

    }



}
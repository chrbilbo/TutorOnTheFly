package com.tonikamitv.loginregister;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.maps.MapFragment;

//import android.support.v7.app.ActionBarActivity;


public class MainActivity extends Activity {

    //implements View.OnClickListener{

    UserLocalStore userLocalStore;
    User user;
    EditText etName, etStudentID, etUsername;
    Button bLogout;

    ActionBar.Tab tab1, tab2, tab3;

    Fragment fragment1 = new TabFragment1();
    Fragment fragment2 = new TabFragment2();
    Fragment fragment3 = new TabFragment3();


    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        getLayoutInflater().setFactory(new LayoutInflater.Factory() {

            @Override
            public View onCreateView(String name, Context context, AttributeSet attrs) {
                View v = tryInflate(name, context, attrs);
                if (v instanceof TextView) {
                    setTypeFace((TextView) v);
                }
                return v;
            }
        });



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ///Stuff for action bar tabs navigation
        ActionBar actionBar = getActionBar();

        actionBar.setNavigationMode(actionBar.NAVIGATION_MODE_TABS);

        tab1 = actionBar.newTab().setText("Find Help");
        tab2 = actionBar.newTab().setText("Anonymous");
        tab3 = actionBar.newTab().setText("User Location");

        tab1.setTabListener(new TabListener(fragment1));
        tab2.setTabListener(new TabListener(fragment2));
        tab3.setTabListener(new TabListener(fragment3));

        actionBar.addTab(tab1);
        actionBar.addTab(tab2);
        actionBar.addTab(tab3);




        /*
        etUsername = (EditText) findViewById(R.id.etUsername);
        etName = (EditText) findViewById(R.id.etName);
        etStudentID = (EditText) findViewById(R.id.etStudentID);
        bLogout = (Button) findViewById(R.id.bLogout);

        bLogout.setOnClickListener(this);
*/
        Bundle bundle = this.getIntent().getExtras();
        user = (User)bundle.getSerializable("user");


        //userLocalStore = new UserLocalStore(this);

    }

    private void addMapFragment() {
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        MapFragment fragment = new MapFragment();
        transaction.add(R.id.mapView, fragment);
        transaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public User getCurrentUser(){
        return user;
    }

/*
    @Override
    public void onClick(View v) {


        switch(v.getId()){
            case R.id.bLogout:
                userLocalStore.clearUserData();
                userLocalStore.setUserLoggedIn(false);
                Intent loginIntent = new Intent(this, Login.class);
                startActivity(loginIntent);
                break;
        }
    }
    @Override
    protected void onStart() {

        super.onStart();
        if (authenticate() == true) {
            //displayUserDetails();
        }else{
            startActivity(new Intent(MainActivity.this, Login.class) );
        }
    }

    private boolean authenticate() {
        if (userLocalStore.getLoggedInUser() == null) {
            Intent intent = new Intent(this, Login.class);
            startActivity(intent);
            return false;
        }
        return true;
    }

    private void displayUserDetails() {
        User user = userLocalStore.getLoggedInUser();
        etUsername.setText(user.username);
        etName.setText(user.name);
        etStudentID.setText(user.age + "");
    }
*/


    private View tryInflate(String name, Context context, AttributeSet attrs) {
        LayoutInflater li = LayoutInflater.from(context);
        View v = null;
        try {
            v = li.createView(name, null, attrs);
        } catch (Exception e) {
            try {
                v = li.createView("android.widget." + name, null, attrs);
            } catch (Exception e1) {
            }
        }
        return v;
    }



    private void setTypeFace(TextView tv) {
        tv.setTypeface(Typeface.createFromAsset(getAssets(), "AManoRegulold.ttf"));
    }

    public void postMessageClicked(View view){

        //String message = .toString();

        Log.w("Message: ", "I'm a banana!!!");
    }



}

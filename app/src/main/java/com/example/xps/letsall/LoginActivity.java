package com.example.xps.letsall;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xps.letsall.library.DatabaseHandler;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.login.widget.ProfilePictureView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

public class LoginActivity extends AppCompatActivity {
    public String globalFBID = "";
    public boolean storedOnceFlag = false;
    Intent CLA;
    private interface Service {

        @FormUrlEncoded
        @POST("/letsall/API/")
        public void addUser(@Field("tag") String tag, @Field("fb_id") String fb_id, Callback<addUserResponse> callback);
    }

    /*public class addUserCall{
        private String tag;
        private String fb_id;
    }*/

    public class addUserResponse{
        private String tag;
        private String success;
    }


    private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FacebookSdk.sdkInitialize(getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        callbackManager = CallbackManager.Factory.create();


        AccessTokenTracker accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken accessToken, AccessToken accessToken2) {
                //Log.d(TAG, "onCurrentAccessTokenChanged()");
                if (accessToken == null) {
                    // Log in Logic
                    Toast.makeText(getApplicationContext(),"LOGIN",Toast.LENGTH_SHORT).show();
                } else if (accessToken2 == null) {
                    // Log out logic
                    Toast.makeText(getApplicationContext(),"LOGOUT",Toast.LENGTH_SHORT).show();
                    DatabaseHandler dbHandler = new DatabaseHandler(getApplicationContext());
                    dbHandler.removeUser();
                }
            }
        };



        DatabaseHandler dbHandler = new DatabaseHandler(getApplicationContext());

//        Toast.makeText(getApplicationContext(), String.valueOf(dbHandler.getUserFBID()), Toast.LENGTH_SHORT).show();

//dbHandler.addUser("asfasdasd");

        //Toast.makeText(getApplicationContext(), String.valueOf(dbHandler.getSessionRowCount()), Toast.LENGTH_SHORT).show();

        CLA = new Intent(LoginActivity.this, CausesListActivity.class);

        LoginButton loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions("public_profile", "email", "user_friends");


        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

            private ProfileTracker mProfileTracker;

            @Override
            public void onSuccess(LoginResult loginResult) {

                if (Profile.getCurrentProfile() == null) {
                    mProfileTracker = new ProfileTracker() {
                        @Override
                        protected void onCurrentProfileChanged(Profile profile, Profile profile2) {
                            if(profile2!=null){
                            Log.v("facebook IF - profile2", profile2.getFirstName());
                                globalFBID = profile2.getId();
                                addProfileID(profile2.getCurrentProfile());
                                /*DatabaseHandler dbHandler = new DatabaseHandler(getApplicationContext());
                                dbHandler.addUser(profile2.getId());*/
                            }
                            mProfileTracker.stopTracking();
                        }
                    };
                    mProfileTracker.startTracking();
                } else {
                    Profile profile = Profile.getCurrentProfile();
                    Log.v("facebook ELSE - profile", profile.getFirstName());
                    globalFBID = profile.getId();
                    addProfileID(profile.getCurrentProfile());
                    /*DatabaseHandler dbHandler = new DatabaseHandler(getApplicationContext());
                    dbHandler.addUser(profile.getId());*/

                }

                //Toast.makeText(getApplicationContext(),globalFBID,Toast.LENGTH_SHORT).show();
/*userId*//* "100001143923761" */

                //GridView gv = (GridView) findViewById(R.id.gv);
                //gv.setAdapter(new ImageAdapter(getApplicationContext(), new String[]{"asd", "ewr", "ewr", "ewr", "ewr", "ewr", "ewr", "ewr", "ewr", "ewr", "ewr", "ewr", "ewr", "ewr", "ewr", "ewr", "ewr", "ewr", "ewr", "ewr", "ewr", "ewr", "ewr", "ewr", "ewr", "ewr", "ewr", "ewr", "ewr", "ewr", "ewr", "ewr", "ewr", "ewr", "ewr", "ewr", "ewr", "ewr", "ewr", "ewr", "ewr", "ewr", "ewr", "ewr", "ewr", "ewr"}));


/*
                Profile profile = Profile.getCurrentProfile();
                Toast.makeText(getApplicationContext(), profile.getFirstName(), Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(),profile.getId(),Toast.LENGTH_SHORT).show();
*/
                /*Profile profile = Profile.getCurrentProfile();
                Toast.makeText(getApplicationContext(),profile.getId(),Toast.LENGTH_SHORT).show();

                RestAdapter adapter = new RestAdapter.Builder().setEndpoint("http://gogetout.net").setLogLevel(RestAdapter.LogLevel.FULL).build();
                Service service = adapter.create(Service.class);

                service.addUser("addUser", profile.getId(), new Callback<addUserResponse>() {
                    @Override
                    public void success(addUserResponse addUserRes, Response response) {
                        //Toast.makeText(MainActivity.this, addUser.success, Toast.LENGTH_LONG).show();
                        //TextView tv = (TextView) findViewById(R.id.tv);
                        //tv.setText(addUser.success);

                        Toast.makeText(LoginActivity.this, addUserRes.success, Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        //Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                        //TextView tv = (TextView) findViewById(R.id.tv);
                        //tv.setText(error.toString());
                    }
                });*/

                //insert FB id into DB
                //Intent CLA = new Intent(LoginActivity.this, CausesListActivity.class);
                //CLA.putExtra("fb_id",globalFBID);
                startActivity(CLA);
            }

            @Override
            public void onCancel() {
                // App code

            }

            @Override
            public void onError(FacebookException exception) {
                // App code
                Log.i("Error", "Error");
            }


        });
    }




    public void addProfileID(Profile profile){

        if (storedOnceFlag == true) return;
        storedOnceFlag = true;
        //Profile profile = Profile.getCurrentProfile();
        //Toast.makeText(getApplicationContext(),profile.getId(),Toast.LENGTH_SHORT).show();
        //Toast.makeText(getApplicationContext(),profile.getId(),Toast.LENGTH_SHORT).show();
        //CLA.putExtra("fb_id",profile.getId());

        DatabaseHandler dbHandler = new DatabaseHandler(getApplicationContext());
        dbHandler.addUser(profile.getId());

        RestAdapter adapter = new RestAdapter.Builder().setEndpoint("http://gogetout.net").setLogLevel(RestAdapter.LogLevel.FULL).build();
        Service service = adapter.create(Service.class);

        service.addUser("addUser", profile.getId(), new Callback<addUserResponse>() {
            @Override
            public void success(addUserResponse addUserRes, Response response) {
                //Toast.makeText(MainActivity.this, addUser.success, Toast.LENGTH_LONG).show();
                //TextView tv = (TextView) findViewById(R.id.tv);
                //tv.setText(addUser.success);

                //Toast.makeText(LoginActivity.this, addUserRes.success, Toast.LENGTH_LONG).show();
            }

            @Override
            public void failure(RetrofitError error) {
                //Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                //TextView tv = (TextView) findViewById(R.id.tv);
                //tv.setText(error.toString());
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);

        if (callbackManager.onActivityResult(requestCode, resultCode, data)) {
            return;
        }

    }


}
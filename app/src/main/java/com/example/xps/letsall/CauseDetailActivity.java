package com.example.xps.letsall;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xps.letsall.library.DatabaseHandler;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

public class CauseDetailActivity extends AppCompatActivity {

    private interface Service {

        @FormUrlEncoded
        @POST("/letsall/API/")
        public void joinUser(@Field("tag") String tag, @Field("fb_id") String fb_id, @Field("cause_id") String cause_id, Callback<userResponse> callback);

        @FormUrlEncoded
        @POST("/letsall/API/")
        public void departUser(@Field("tag") String tag, @Field("fb_id") String fb_id, @Field("cause_id") String cause_id, Callback<userResponse> callback);
    }

    public class userResponse {
        private String tag;
        private String success;
        private String error;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cause_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView tvText = (TextView) findViewById(R.id.text);
        TextView tvFBID = (TextView) findViewById(R.id.fb_id);
        TextView tvID = (TextView) findViewById(R.id.id);
        TextView tvTitle = (TextView) findViewById(R.id.title);

        String text = getIntent().getStringExtra("text");
        String fb_id = getIntent().getStringExtra("fb_id");
        final String id = getIntent().getStringExtra("id");
        String title = getIntent().getStringExtra("title");
        String ends = getIntent().getStringExtra("ends");

        tvText.setText(text);
        tvFBID.setText(fb_id);
        tvID.setText(id);
        tvTitle.setText(title);
        //Toast.makeText(getApplicationContext(), ends, Toast.LENGTH_LONG).show();

        final DatabaseHandler dbHandler = new DatabaseHandler(getApplicationContext());
        RestAdapter adapter = new RestAdapter.Builder().setEndpoint("http://gogetout.net").setLogLevel(RestAdapter.LogLevel.FULL).build();
        final Service service = adapter.create(Service.class);

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        final FloatingActionButton fab2 = (FloatingActionButton) findViewById(R.id.fab2);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/


                service.joinUser("joinUser", dbHandler.getUserFBID(), id, new Callback<userResponse>() {
                    @Override
                    public void success(userResponse userResponse, Response response) {
                        if (userResponse != null) {
                            Toast.makeText(CauseDetailActivity.this, userResponse.tag + userResponse.success, Toast.LENGTH_LONG).show();


                        } else
                            Toast.makeText(CauseDetailActivity.this, "User already joined.", Toast.LENGTH_LONG).show();
                        fab.setVisibility(View.INVISIBLE);
                        fab2.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Toast.makeText(CauseDetailActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                });

            }
        });


        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(CauseDetailActivity.this, "Fab2 Clicked!", Toast.LENGTH_LONG).show();
                service.departUser("departUser", dbHandler.getUserFBID(), id, new Callback<userResponse>() {
                    @Override
                    public void success(userResponse userResponse, Response response) {
                        if (userResponse != null) {
                            Toast.makeText(CauseDetailActivity.this, userResponse.tag + userResponse.success, Toast.LENGTH_LONG).show();

                        } else
                            Toast.makeText(CauseDetailActivity.this, "User already left.", Toast.LENGTH_LONG).show();
                        fab2.setVisibility(View.INVISIBLE);
                        fab.setVisibility(View.VISIBLE);

                    }

                    @Override
                    public void failure(RetrofitError error) {

                    }
                });
                /*fab2.setVisibility(View.INVISIBLE);
                fab.setVisibility(View.VISIBLE);*/
            }
        });
    }

}

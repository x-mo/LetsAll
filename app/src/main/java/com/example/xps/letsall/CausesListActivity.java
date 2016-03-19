package com.example.xps.letsall;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.xps.letsall.library.DatabaseHandler;
import com.facebook.login.LoginManager;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

public class CausesListActivity extends AppCompatActivity {

    List<Cause> globalCauses;

    private interface Service {

        @FormUrlEncoded
        @POST("/letsall/API/")
        void getCauses(@Field("tag") String tag, Callback<List<Cause>> callback);

    }

    public class Cause {
        public String id;
        public String fb_id;
        public String title;
        public String text;
        public String list;
        public String ends;
    }

    private class CauseEnvelope {

        /*public String success;
        public String error;
        public String tag;*/

        //public List<Cause> causes;
        public HashMap<String, Cause> causes;

        @Override
        public String toString() {
            String output = ": ";

            for (Map.Entry<String, Cause> cause : causes.entrySet()) {
                output += cause.getKey() + "=" + cause.getValue().title + ",3aaaaaaaaaaaaaaaah";
            }
            return output;
        }
        //public String date_time;
    }

    private class userID {
        public String ID;
    }


    ListView LV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_causes_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Toast.makeText(getApplicationContext(),getIntent().getStringExtra("fb_id"),Toast.LENGTH_LONG).show();

        DatabaseHandler dbHandler = new DatabaseHandler(getApplicationContext());


//        Toast.makeText(getApplicationContext(),dbHandler.getUserFBID(),Toast.LENGTH_LONG).show();


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent CCA = new Intent(CausesListActivity.this, CreateCauseActivity.class);
                //CCA.putExtra("fb_id", getIntent().getStringExtra("fb_id"));

                /*DatabaseHandler dbHandler = new DatabaseHandler(getApplicationContext());
                Toast.makeText(getApplicationContext(),dbHandler.getUserFBID(),Toast.LENGTH_LONG).show();*/

                startActivity(CCA);

            }
        });

        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint("http://gogetout.net").setLogLevel(RestAdapter.LogLevel.FULL).build();
        Service service = restAdapter.create(Service.class);
        final ArrayAdapter<Object> listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        ListView listView = (ListView) findViewById(R.id.lv);
        listView.setAdapter(listAdapter);

        service.getCauses("fetchCauses", new Callback<List<Cause>>() {
            @Override
            public void success(List<Cause> causes, Response response) {

                globalCauses = causes;
                String output = ": ";
                ArrayList al = new ArrayList();
                for (int i = 0; i < causes.size(); i++) {
                    al.add(causes.get(i).title);
                }

                listAdapter.addAll(al);
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(getApplicationContext(), error.toString() + "  Nope.", Toast.LENGTH_SHORT).show();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent CDA = new Intent(CausesListActivity.this, CauseDetailActivity.class);
                CDA.putExtra("text", globalCauses.get(position).text);
                CDA.putExtra("fb_id", globalCauses.get(position).fb_id);
                CDA.putExtra("id", globalCauses.get(position).id);
                CDA.putExtra("title", globalCauses.get(position).title);
                CDA.putExtra("ends", globalCauses.get(position).ends);

                startActivity(CDA);

            }
        });


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
        if (id == R.id.action_logout) {

            DatabaseHandler dbHandler = new DatabaseHandler(getApplicationContext());
            dbHandler.removeUser();

            Intent LA = new Intent(CausesListActivity.this, LoginActivity.class);
            LA.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

            LoginManager.getInstance().logOut();

            startActivity(LA);

            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

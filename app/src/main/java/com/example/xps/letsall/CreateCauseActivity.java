package com.example.xps.letsall;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.xps.letsall.library.DatabaseHandler;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

public class CreateCauseActivity extends AppCompatActivity {

    private interface Service {

        @FormUrlEncoded
        @POST("/letsall/API/")
        public void addCause(@Field("tag") String tag, @Field("fb_id") String fb_id, @Field("title") String title, @Field("text") String text, Callback<addCauseResponse> callback);
    }

    public class addCauseResponse {
        private String tag;
        private String success;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_cause);

        final EditText titleEdit = (EditText) findViewById(R.id.title);
        final EditText textEdit = (EditText) findViewById(R.id.text);

        Button butt = (Button) findViewById(R.id.butt);

        RestAdapter adapter = new RestAdapter.Builder().setEndpoint("http://gogetout.net").setLogLevel(RestAdapter.LogLevel.FULL).build();
        final Service service = adapter.create(Service.class);


        butt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = titleEdit.getText().toString();
                String text = textEdit.getText().toString();
                DatabaseHandler dbHandler = new DatabaseHandler(getApplicationContext());

                Toast.makeText(getApplicationContext(), title + " : " + text + " : " + String.valueOf(dbHandler.getUserFBID()), Toast.LENGTH_LONG).show();

                service.addCause("addCause", dbHandler.getUserFBID(), title, text, new Callback<addCauseResponse>() {
                    @Override
                    public void success(addCauseResponse addCauseResponse, Response response) {


                        Toast.makeText(CreateCauseActivity.this, addCauseResponse.tag + addCauseResponse.success, Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Toast.makeText(CreateCauseActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                });


            }
        });

    }
}

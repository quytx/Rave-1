package com.rave.rave;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import Adapters.CreateEventAdapter;
import savage.UrlJsonAsyncTask;


public class CreateEventActivity extends ActionBarActivity {

    private Toolbar toolbar;
    private Bitmap imageResource;
    private CreateEventAdapter createEventAdapter;
    private final static String CREATE_EVENT_API_ENDPOINT_URL = "http://madrave.herokuapp.com/api/v1/events";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_layout);

        setResult(RESULT_OK);


        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setTitle("New Event");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.RecyclerView);
        createEventAdapter = new CreateEventAdapter();
        recyclerView.setAdapter(createEventAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        final GestureDetector mGestureDetector = new GestureDetector(getBaseContext(),
                new GestureDetector.SimpleOnGestureListener() {
                    @Override public boolean onSingleTapUp(MotionEvent e) {
                        return true;
                    }


                });
//
        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
                View child = recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());
                if (child != null && mGestureDetector.onTouchEvent(motionEvent)) {
                    int position = recyclerView.getChildAdapterPosition(child);
                    if(position == 0){
                        dispatchTakePictureIntent();
                    }
                    return true;
                }
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {
            }

        });
//
//        Button aButton = (Button) this.findViewById(R.id.submitButton);
//        aButton.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                createEvent();
//            }
//        });
    }

    static final int REQUEST_IMAGE_CAPTURE = 1;

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            imageResource = (Bitmap) extras.get("data");
            createEventAdapter.setImageResource(imageResource);
        }
    }


    private String eventTitle;
    private String eventLocation;
    private String eventDescription;
    private String eventTime;
    private String eventDate;
    public void createEvent(View submitButton) { //
        EditText eventField = (EditText) findViewById(R.id.userEmail);
        eventTitle = eventField.getText().toString();
        EditText locationFeild = (EditText) findViewById(R.id.userPassword);
        eventLocation = locationFeild.getText().toString();
        EditText descriptionField = (EditText) findViewById(R.id.userPassword);
        eventDescription = descriptionField.getText().toString();
        EditText timeField = (EditText) findViewById(R.id.userPassword);
        eventTime = timeField.getText().toString();
        EditText dateField = (EditText) findViewById(R.id.userPassword);
        eventDate = dateField.getText().toString();

        if (eventTitle.length() == 0 || eventLocation.length() == 0 || eventDescription.length() == 0
                 || eventTime.length() == 0 || eventDate.length() == 0) {
            // input fields are empty
            Toast.makeText(this, "Please complete all the fields",
                    Toast.LENGTH_LONG).show();
            return;
        } else {
            CreateEventTask createTask = new CreateEventTask(CreateEventActivity.this);
            createTask.setMessageLoading("Publishing Party Parameters...");
            createTask.execute(CREATE_EVENT_API_ENDPOINT_URL);
        }
    }

    private class CreateEventTask extends UrlJsonAsyncTask {
        public CreateEventTask(Context context) {
            super(context);
        }

        @Override
        protected JSONObject doInBackground(String... urls) {
            DefaultHttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost(urls[0]);   //urls[0] api/v1/sessions
            JSONObject holder = new JSONObject();
            JSONObject userObj = new JSONObject();
            String response = null;
            JSONObject json = new JSONObject();

            try {
                try {
                    // setup the returned values in case
                    // something goes wrong
//                    json.put("success", false);
//                    json.put("info", "Something went wrong. Retry!");
                    // add the user email and password to
                    // the params
                    userObj.put("title", eventTitle);
                    userObj.put("location", eventLocation);
                    userObj.put("start_time", eventTime);
                    userObj.put("end_time", "");

                    holder.put("event", userObj);
                    StringEntity se = new StringEntity(holder.toString());
                    post.setEntity(se);

                    // setup the request headers
                    post.setHeader("Accept", "application/json");
                    post.setHeader("Content-Type", "application/json");

                    ResponseHandler<String> responseHandler = new BasicResponseHandler();
                    response = client.execute(post, responseHandler);
                    json = new JSONObject(response);

                } catch (HttpResponseException e) {
                    e.printStackTrace();
                    Log.e("ClientProtocol", "" + e);
                    json.put("info", "Email and/or password are invalid. Retry!");
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("IO", "" + e);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Log.e("JSON", "" + e);
            }

            return json;
        }

        @Override
        protected void onPostExecute(JSONObject json) {
            try {
                if (json.getBoolean("success")) {
//                    // everything is ok
//                    SharedPreferences.Editor editor = mPreferences.edit();
//                    // save the returned auth_token into
//                    // the SharedPreferences
//                    editor.putString("AuthToken", json.getJSONObject("data").getString("auth_token"));
//                    editor.commit();
//
//
//                    String token = mPreferences.getString("AuthToken", "missing");
//                    Log.d("bimbam", token);
                    // launch the HomeActivity and close this one
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                Toast.makeText(context, json.getString("info"), Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                // something went wrong: show a Toast
                // with the exception message
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
            } finally {
                super.onPostExecute(json);
            }
        }
    }
}

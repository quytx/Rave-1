package com.rave.rave;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.ImageView;
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
import java.util.ArrayList;
import java.util.List;

import Data.EventData;
import savage.UrlJsonAsyncTask;

public class CheckInTask extends UrlJsonAsyncTask {
    SharedPreferences mPreferences;
    private List<EventData> mDataSet = new ArrayList<>();
    int currEvent;
    String url = "";
    private boolean checkedin;
    ImageView attendingButton;


    public CheckInTask(Context context, SharedPreferences mPreferences, List<EventData> mDataSet, int currEvent, String url, ImageView attendingButton) {
        super(context);
        this.mPreferences = mPreferences;
        this.mDataSet = mDataSet;
        this.currEvent = currEvent;
        this.url = url;
        this.attendingButton = attendingButton;
        checkedin = false;
    }

    @Override
    protected JSONObject doInBackground(String... urls) {
        DefaultHttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(urls[0]);   //urls[0] api/v1/sessions
        JSONObject holder = new JSONObject();
        String response = null;
        JSONObject json = new JSONObject();

        try {
            try {
                // setup the returned values in case
                holder.put("user_id", mPreferences.getString("UserID","none"));
                holder.put("event_id", mDataSet.get(currEvent).eventID);

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
                    // everything is ok
                checkedin = json.getBoolean("checked_in");
                if(checkedin){
                    attendingButton.setImageResource(R.drawable.check_mark_green);
                }
                else{
                    attendingButton.setImageResource(R.drawable.check_mark_gray);
                }
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

    public void checkIn(){
        this.execute(url);
    }

    public boolean getCheckedIn(){
        return checkedin;
    }
}
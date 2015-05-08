package com.rave.rave;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import Adapters.EventDetailAdapter;
import Data.EventData;


public class EventActivity extends ActionBarActivity {

    private Toolbar toolbar;

    //Create Adapters for Card View
    RecyclerView recyclerView;
    RecyclerView.Adapter mRecyclerAdapter;
    RecyclerView.LayoutManager mLayoutManager;

    private String mEventTitle;
    private TextView textView;
    private String events;
    private JSONArray array;
    private JSONObject rec;
    private String name;
    private JSONObject ourEvent;

    private String[] detailTitles = {"Date", "Time", "Location", "Type"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_layout);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.RecyclerView);
        EventDetailAdapter eventDetailAdapter = new EventDetailAdapter();
        recyclerView.setAdapter(eventDetailAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mEventTitle = getIntent().getStringExtra(MainActivity.EVENT_NAME);

        events = getIntent().getStringExtra(MainActivity.eventActivity);

        try {
            ourEvent = new JSONObject(events);
        } catch (JSONException e) {
            Log.d("bam", "error with event array");
        }



        eventDetailAdapter.setEventDataSet(getEventData());


        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle(mEventTitle);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    private List<EventData> getEventData() {
        List<EventData> dataSet = new ArrayList<>();

        EventData data = new EventData();

        //      int detailListSize = data.detailTitles.length;

        try {
            data.eventTitle = ourEvent.getString("name");
            data.eventImage = ourEvent.getString("cover_photo");
            if (data.eventImage.equals("null")) {
                data.eventImage = "http://s30.postimg.org/43c1roizl/no_image_large.jpg";
            }
            data.profilePic = R.drawable.profile_pic_example;
            data.description = ourEvent.getString("description");
            data.startTime = ourEvent.getString("start_time");
            data.endTime = ourEvent.getString("end_time");
            data.location = ourEvent.getString("location");
            data.latLng = getLocationFromAddress(data.location);
            data.eventID = ourEvent.getString("id");
 
        } catch (Exception e){
            Log.d("bam", "error with adding data");
        }

        dataSet.add(data);

        return dataSet;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_event, menu);
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

    public LatLng getLocationFromAddress(String strAddress) {

        Geocoder coder = new Geocoder(this);
        List<Address> address;
        LatLng p1 = null;

        try {
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }
            Address location = address.get(0);
            location.getLatitude();
            location.getLongitude();

            p1 = new LatLng(location.getLatitude(), location.getLongitude() );

        } catch (Exception ex) {

            ex.printStackTrace();
        }

        return p1;
    }
}
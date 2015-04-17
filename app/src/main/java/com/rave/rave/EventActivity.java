package com.rave.rave;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import Adapters.ListAdapter;
import Data.EventData;


public class EventActivity extends ActionBarActivity {

    private Toolbar toolbar;

    //Create Adapters for Card View
    RecyclerView recyclerView;
    RecyclerView.Adapter mRecyclerAdapter;
    RecyclerView.LayoutManager mLayoutManager;


    private String mEventTitle;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_layout);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.RecyclerView);
        ListAdapter listAdapter = new ListAdapter();
        recyclerView.setAdapter(listAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mEventTitle = getIntent().getStringExtra(MainActivity.EVENT_NAME);

        listAdapter.setEventDataSet(getEventData());


        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle(mEventTitle);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    private List<EventData> getEventData() {
        List<EventData> dataSet = new ArrayList<>();

        EventData data = new EventData();
        data.eventTitle = mEventTitle;
        data.eventImage = R.drawable.madison_header_background;
        data.profilePic = R.drawable.profile_pic_example;
        data.description = "THIS IS A NEW EVENT DESCRIPTION......." +
                "..................................................." +
                "...................................................";
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
}

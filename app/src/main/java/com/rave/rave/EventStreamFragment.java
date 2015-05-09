package com.rave.rave;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.melnykov.fab.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import Helpers.EventDataParser;

public class EventStreamFragment extends Fragment implements AdapterView.OnItemClickListener{

    //Create Adapters for Card View
    RecyclerView mCardView;
    RecyclerView.Adapter mCardAdapter;
    RecyclerView.LayoutManager mLayoutManager;

    String[] EVENT_NAMES;
    String[] EVENT_LOCATIONS;
    String[] EVENT_PHOTOS;
    String[] EVENT_IDS;
    String[] EVENT_PARTICIPANTS_LIST;
    String[] EVENT_PARTICPANT_COUNT;
    //Wrk

    String EVENTS = "";
    JSONArray array;
    JSONObject recs;
    JSONObject toEventActivity;

    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        Bundle bundle = this.getArguments();
        if(bundle != null){

            EVENTS = bundle.getString(MainActivity.EVENTS);
            try {
                array = new JSONArray(EVENTS);
                EventDataParser eventDataParser = new EventDataParser(array);
                eventDataParser.parseEvents();
                EVENT_NAMES = eventDataParser.getEventNames();
                EVENT_LOCATIONS = eventDataParser.getEventLocations();
                EVENT_PHOTOS = eventDataParser.getEventPhotos();
                EVENT_IDS = eventDataParser.getEventIds();
                EVENT_PARTICPANT_COUNT = eventDataParser.getEventParticipantCount();
            } catch (JSONException e) {
                Log.d("bam", "error with event array");
            }
        }


        final View parent = inflater.inflate(R.layout.fragment_event_stream, container, false);
        mCardView = (RecyclerView) parent.findViewById(R.id.eventCardRecycler);

        mLayoutManager = new LinearLayoutManager(parent.getContext());
        mCardView.setLayoutManager(mLayoutManager);



        mCardAdapter = new CardAdapter(EVENT_NAMES, EVENT_LOCATIONS,EVENT_IDS,EVENT_PHOTOS);
        mCardView.setAdapter(mCardAdapter);

        FloatingActionButton fab = (FloatingActionButton) parent.findViewById(R.id.fab);
        fab.attachToRecyclerView(mCardView);
        fab.show();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getBaseContext(), CreateEventActivity.class);
                startActivity(intent);
            }
        });




        final GestureDetector mGestureDetector = new GestureDetector(getActivity().getBaseContext(),
                new GestureDetector.SimpleOnGestureListener() {
                    @Override public boolean onSingleTapUp(MotionEvent e) {
                        return true;
                    }


                });

        mCardView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
                View child = recyclerView.findChildViewUnder(motionEvent.getX(),motionEvent.getY());
                if(child!=null && mGestureDetector.onTouchEvent(motionEvent)){
                    int position = recyclerView.getChildAdapterPosition(child);
                    //TODO: when pressing back, alpha stays as 0.5
                    // child.setAlpha((float)0.5);



                    Intent intent = new Intent(getActivity().getBaseContext(), EventActivity.class);

                    try {
                        toEventActivity = array.getJSONObject(position);
                    } catch (Exception e){
                        Log.d("bam", "error with event array");
                    }

                    intent.putExtra(MainActivity.EVENT_NAME, EVENT_NAMES[position]);
                    intent.putExtra(MainActivity.eventActivity, toEventActivity.toString());

                    startActivity(intent);
                    return true;
                }
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {

            }
        });


        return parent;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(getActivity().getBaseContext(), "The Item Clicked is: " +
                position, Toast.LENGTH_SHORT).show();
    }


}
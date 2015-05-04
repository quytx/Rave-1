package com.rave.rave;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import Adapters.CreateEventAdapter;


public class CreateEventActivity extends ActionBarActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_layout);

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setTitle("New Event");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.RecyclerView);
        CreateEventAdapter createEventAdapter = new CreateEventAdapter();
        recyclerView.setAdapter(createEventAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

//        final GestureDetector mGestureDetector = new GestureDetector(getBaseContext(),
//                new GestureDetector.SimpleOnGestureListener() {
//                    @Override public boolean onSingleTapUp(MotionEvent e) {
//                        return true;
//                    }
//
//
//                });
//
//        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
//            @Override
//            public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
//                View child = recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());
//                if (child != null && mGestureDetector.onTouchEvent(motionEvent)) {
//                    int position = recyclerView.getChildAdapterPosition(child);
//                    int position1 = recyclerView.findViewHolderForAdapterPosition(position)
//                    Toast.makeText(getApplicationContext(), "Item clicked is " + position1, Toast.LENGTH_SHORT).show();
//                    return true;
//                }
//                return false;
//            }
//
//            @Override
//            public void onTouchEvent(RecyclerView rv, MotionEvent e) {
//            }
//
//        });
    }
}

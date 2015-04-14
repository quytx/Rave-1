package com.rave.rave;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;


public class EventStreamFragment extends Fragment implements AdapterView.OnItemClickListener{

    //Create Adapters for Card View
    RecyclerView mCardView;
    RecyclerView.Adapter mCardAdapter;
    RecyclerView.LayoutManager mLayoutManager;

    String[] EVENT_NAMES = {""};
    String[] EVENT_LOCATIONS = {""};




    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        Bundle bundle = this.getArguments();
        if(bundle != null){
            EVENT_NAMES = bundle.getStringArray(MainActivity.EVENT_NAME);
            EVENT_LOCATIONS = bundle.getStringArray(MainActivity.LOCATIONS);
        }


        final View parent = inflater.inflate(R.layout.fragment_event_stream, container, false);
        mCardView = (RecyclerView) parent.findViewById(R.id.eventCardRecycler);

        mLayoutManager = new LinearLayoutManager(parent.getContext());
        mCardView.setLayoutManager(mLayoutManager);

        mCardAdapter = new CardAdapter(EVENT_NAMES, EVENT_LOCATIONS);
        mCardView.setAdapter(mCardAdapter);



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
                    child.setAlpha((float)0.5);
                    Toast.makeText(getActivity().getBaseContext(), "The Item Clicked is: " +
                            position, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getActivity().getBaseContext(), EventActivity.class);
                    intent.putExtra(MainActivity.EVENT_NAME, EVENT_NAMES[position]);
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

    }
}

package com.rave.rave;

import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity implements AdapterView.OnItemClickListener{

    private Toolbar toolbar;

    //Declare Titles and Icons for Nav Drawer
    String DRAWER_ITEMS[];

    //Declare Titles for Cards
    String EVENT_TITLES[] = {"Event 1", "Event 2", "Event 3", "Event 4"};

    //Create string res for name and email in header view
    String NAME = "Jacob Pandl";
    String EMAIL = "jpandl19@gmail.com";
    int PROFILE_PIC = R.drawable.profile_pic_example;


    //Variables for EventStreamFragment
    final String[] EVENT_NAMES = {"Party Name 1", "Party Name 2", "Party Name 3",
            "Party 4 Name", "Party Name 5"};
    final static String EVENT_NAME = "Event Names";

    final static String LOCATIONS = "Event Locations";
    final String[] EVENT_LOCATIONS = {"123 Main St", "5026 N Woodburn", "56 N Park", "27 N Brooks",
            "West Palm Beach"};

    //Create Adapters for drawer
    RecyclerView mRecyclerView;
    NavAdapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    DrawerLayout drawerLayout;

    private int currItemSelected = 1;

    ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DRAWER_ITEMS = getResources().getStringArray(R.array.drawer_items);

        int ICONS[] = {R.drawable.event_stream_icon,R.drawable.itinerary_icon,
                R.drawable.friends_icon,
                R.drawable.favorites_icon,
                R.drawable.history_icon,
                R.drawable.my_event_icon};

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);



        //Declare RecycleView and set Adapter for drawer
        mRecyclerView = (RecyclerView) findViewById(R.id.RecyclerView);
        mRecyclerView.setHasFixedSize(true);    //List objects have fixed size
        mAdapter = new NavAdapter(DRAWER_ITEMS, ICONS, NAME, EMAIL, PROFILE_PIC);

        mRecyclerView.setAdapter(mAdapter);

        mLayoutManager = new LinearLayoutManager(this);

        mRecyclerView.setLayoutManager(mLayoutManager);

        drawerLayout = (DrawerLayout) findViewById(R.id.DrawerLayout);

        //Create drawer toggle object
        mDrawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                R.string.drawer_open,
                R.string.drawer_closed
        ){
            @Override
            public void onDrawerOpened(View drawerView){
                super.onDrawerOpened(drawerView);
                supportInvalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView){
                super.onDrawerClosed(drawerView);
                supportInvalidateOptionsMenu();
            }
        };


        final GestureDetector mGestureDetector = new GestureDetector(MainActivity.this, new GestureDetector.SimpleOnGestureListener() {

            @Override public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }

        });
        //**********BUG: NO CLICK EFFECT FOR ITEM 1 and 2************
        mRecyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
                View child = recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());


                if (child != null && mGestureDetector.onTouchEvent(motionEvent)) {
                    int position = recyclerView.getChildAdapterPosition(child);

                    if(currItemSelected != position && position != 0) {
                        recyclerView.getChildAt(currItemSelected).setBackgroundColor(Color.WHITE);
                        currItemSelected = position;
                        child.setBackgroundColor(Color.LTGRAY);
                    }

                    Toast.makeText(MainActivity.this, "The Item Clicked is: " +
                            position, Toast.LENGTH_SHORT).show();

                    drawerLayout.closeDrawers();
                    return true;
                }
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {

            }
        });



        drawerLayout.setDrawerListener(mDrawerToggle);


        mDrawerToggle.syncState();
        
        //Create bundle for EventStreamFragment
        Bundle eventBundle = new Bundle();
        eventBundle.putStringArray(EVENT_NAME, EVENT_TITLES);
        eventBundle.putStringArray(LOCATIONS, EVENT_LOCATIONS);
        
        //Set args and create fragment
        if(savedInstanceState==null) {
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            EventStreamFragment eventStreamFragment = new EventStreamFragment();
            eventStreamFragment.setArguments(eventBundle);
            fragmentTransaction.add(R.id.fragmentContainer, eventStreamFragment).commit();
        }
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

        if(mDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

}

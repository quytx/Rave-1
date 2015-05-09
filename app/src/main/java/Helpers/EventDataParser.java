package Helpers;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Jacob on 5/8/2015.
 */
public class EventDataParser {
    private JSONArray array;
    private JSONObject recs;

    public String[] getEventLocations() {
        return eventLocations;
    }

    public String[] getEventPhotos() {
        return eventPhotos;
    }

    public String[] getEventIds() {
        return eventIds;
    }

    public String[] getEventNames() {
        return eventNames;
    }

    public String[] getEventParticipantCount(){return eventParticipantCount;}

    private String[] eventLocations;
    private String[] eventPhotos;
    private String[] eventIds;
    private String[] eventNames;
    private ArrayList<String> eventParticipantLists;
    private ArrayList<String> eventParticipants;
    private String[] eventParticipantCount;
    private JSONArray eventParticipantListArray;



    public EventDataParser(JSONArray array){
        this.array = array;
    }


    public void parseEvents() {
        try {
            Log.d("bam", array.toString());
            Log.d("bam"," " + array.length());
            eventNames = new String[array.length()];
            eventLocations = new String[array.length()];
            eventPhotos = new String[array.length()];
            eventIds = new String[array.length()];
            eventParticipantCount = new String[array.length()];

            for (int i = 0; i < array.length(); i++) {
                recs = array.getJSONObject(i);
                eventNames[i] = recs.getString("name");
                eventLocations[i] = recs.getString("location");
                eventPhotos[i] = recs.getString("cover_photo");
                eventIds[i] = recs.getString("id");
//                eventParticipantListArray = recs.getJSONArray("participant_list");
//                if(!eventParticipantListArray.equals("null")){
//                    for(int x = 0; x < eventParticipantListArray.length(); x++){
//                        eventParticipantListArray.get
//                    }
//                }
                eventParticipantCount[i] = recs.getString("participant_count");
            }
        } catch (JSONException e) {
            Log.d("bam", "error with event array");
        }
    }
}

package Helpers;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

    private String[] eventLocations;
    private String[] eventPhotos;
    private String[] eventIds;
    private String[] eventNames;


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

            for (int i = 0; i < array.length(); i++) {
                recs = array.getJSONObject(i);
                eventNames[i] = recs.getString("name");
                eventLocations[i] = recs.getString("location");
                eventPhotos[i] = recs.getString("cover_photo");
                if (eventPhotos[i].equals("null")) {
                    eventPhotos[i] = "http://s30.postimg.org/43c1roizl/no_image_large.jpg";
                }
                eventIds[i] = recs.getString("id");
            }
        } catch (JSONException e) {
            Log.d("bam", "error with event array");
        }
    }
}

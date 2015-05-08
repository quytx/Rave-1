package Data;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by Jacob on 4/17/2015.
 */
public class EventData {
    public String eventTitle;
    public String description;
    public int profilePic;
    public String eventImage;
    public String eventID;
    public ArrayList<String> attendingListNames;
    public ArrayList<Integer> attendingListpics;
  //  public String[] details;
    public String date;
    public String startTime;
    public String endTime;
    public String location;
    public LatLng latLng;

    //public ImageView eventImage;
}

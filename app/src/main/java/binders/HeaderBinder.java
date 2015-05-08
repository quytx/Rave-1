package binders;

/**
 * Created by Jacob on 4/17/2015.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.rave.rave.CreateEventActivity;
import com.rave.rave.R;
import com.yqritc.recyclerviewmultipleviewtypesadapter.DataBindAdapter;
import com.yqritc.recyclerviewmultipleviewtypesadapter.DataBinder;

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
import de.hdodenhof.circleimageview.CircleImageView;
import savage.UrlJsonAsyncTask;

/**
 * Created by yqritc on 2015/03/20.
 */
public class HeaderBinder extends DataBinder<HeaderBinder.ViewHolder> {

    private SharedPreferences mPreferences;
    public int currEvent;
    Context mContext;

    //URL for connecting to server
    private final static String CHECKIN_URL = "http://madrave.herokuapp.com/api/v1/checkin";


    private List<EventData> mDataSet = new ArrayList<>();
    private boolean checked = false;

    public HeaderBinder(DataBindAdapter dataBindAdapter) {
        super(dataBindAdapter);
    }

    @Override
    public ViewHolder newViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.event_detail_header, parent, false);
        mContext = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void bindViewHolder(final ViewHolder holder, final int position) {
        EventData data = mDataSet.get(position);
        holder.mTitleText.setText(data.eventTitle);
        holder.profilePic.setImageResource(data.profilePic);
        holder.eventImageView.setImageResource(data.eventImage);

        holder.attendingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitNewEvent(CHECKIN_URL, position);
                if (!checked) {
                    holder.attendingButton.setImageResource(R.drawable.check_mark_green);
                    checked = true;
                } else {
                    holder.attendingButton.setImageResource(R.drawable.check_mark_gray);
                    checked = false;
                }
            }
        });
    }

    private void submitNewEvent(String url, int position) {
        currEvent = position;
        CheckInTask checkInTask = new CheckInTask(mContext);
        CheckInTask.setMessageLoading("Checking In");
        CheckInTask.execute(url);

    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public void addAll(List<EventData> dataSet){
        mDataSet.addAll(dataSet);
        notifyBinderDataSetChanged();
    }

    public void clear(){
        mDataSet.clear();
        notifyBinderDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView mTitleText;
        ImageView eventImageView;
        ImageView attendingButton;
        CircleImageView profilePic;
        TextView mContent;

        public ViewHolder(View view) {
            super(view);
            mTitleText = (TextView) view.findViewById(R.id.event_title_text);
            eventImageView = (ImageView) view.findViewById(R.id.event_detail_main_image);
            attendingButton = (ImageView) view.findViewById(R.id.check_mark);
            profilePic = (CircleImageView) view.findViewById(R.id.profile_pic);


        }
    }

    private class CheckInTask extends UrlJsonAsyncTask {

        public CheckInTask(Context context) {
            super(context);
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
//                    // everything is ok
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
    }
}
package binders;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.rave.rave.R;
import com.yqritc.recyclerviewmultipleviewtypesadapter.DataBindAdapter;
import com.yqritc.recyclerviewmultipleviewtypesadapter.DataBinder;

import java.util.ArrayList;
import java.util.List;

import Data.EventData;

/**
 * Created by Jacob on 4/17/2015.
 */
public class EventDescriptionBinder extends DataBinder<EventDescriptionBinder.ViewHolder> implements OnMapReadyCallback {
    private List<EventData> mDataSet = new ArrayList<>();
    Context mContext;
    Activity callingActivity;
    FragmentManager fragmentManager;

    public EventDescriptionBinder(DataBindAdapter dataBindAdapter) {
        super(dataBindAdapter);
    }

    @Override
    public ViewHolder newViewHolder(ViewGroup parent) {
        mContext = parent.getContext();
        callingActivity = (Activity) mContext;
        fragmentManager = callingActivity.getFragmentManager();
        View view = LayoutInflater.from(mContext).inflate(
                R.layout.event_detail_description, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void bindViewHolder(ViewHolder holder, int position) {
        EventData data = mDataSet.get(position);
        holder.descriptionView.setText(data.description);
        holder.mapFragment = (MapFragment) fragmentManager.findFragmentById(R.id.map);
        holder.mapFragment.getMapAsync(this);
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

    @Override
    public void onMapReady(GoogleMap map) {
        CameraUpdate center=
                CameraUpdateFactory.newLatLng(new LatLng(43.0691100,
                        -89.4010630));
        CameraUpdate zoom=CameraUpdateFactory.zoomTo(15);
        map.addMarker(new MarkerOptions().position(new LatLng(43.0691100, -89.4010630))
                .title("Party ova here"));
                map.moveCamera(center);
        map.animateCamera(zoom);
    }


    static class ViewHolder extends RecyclerView.ViewHolder{

        TextView descriptionView;
        MapFragment mapFragment;
        GoogleMap map;


        public ViewHolder(View view) {
            super(view);
            descriptionView = (TextView) view.findViewById(R.id.event_description);
;
        }
    }
}

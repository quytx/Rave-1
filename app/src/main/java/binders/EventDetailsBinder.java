package binders;

import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rave.rave.R;
import com.yqritc.recyclerviewmultipleviewtypesadapter.DataBindAdapter;
import com.yqritc.recyclerviewmultipleviewtypesadapter.DataBinder;

import java.util.ArrayList;
import java.util.List;

import Data.EventData;

/**
 * Created by Jacob on 4/17/2015.
 */
public class EventDetailsBinder extends DataBinder<EventDetailsBinder.ViewHolder> {
    private List<EventData> mDataSet = new ArrayList<>();

    public EventDetailsBinder(DataBindAdapter dataBindAdapter) {
        super(dataBindAdapter);
    }

    @Override
    public ViewHolder newViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.event_detail_info, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void bindViewHolder(ViewHolder holder, int position) {
        EventData data = mDataSet.get(position);



        holder.startTime.setText(data.startTime);
        holder.endTime.setText(data.endTime);
        holder.location.setText(data.location);

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


        TextView startTime;
        TextView endTime;
        TextView location;


        public ViewHolder(View view) {
            super(view);

            startTime = (TextView) view.findViewById(R.id.start_time_field);
            endTime = (TextView) view.findViewById(R.id.end_time_field);
            location = (TextView) view.findViewById(R.id.location_field);

            location.setMovementMethod(new ScrollingMovementMethod());



        }
    }
}

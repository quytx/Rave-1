package binders;

import android.support.v7.widget.RecyclerView;
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
public class EventDescriptionBinder extends DataBinder<EventDescriptionBinder.ViewHolder> {
    private List<EventData> mDataSet = new ArrayList<>();

    public EventDescriptionBinder(DataBindAdapter dataBindAdapter) {
        super(dataBindAdapter);
    }

    @Override
    public ViewHolder newViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.event_detail_description, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void bindViewHolder(ViewHolder holder, int position) {
        EventData data = mDataSet.get(position);
        holder.descriptionView.setText(data.description);
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

        TextView descriptionView;


        public ViewHolder(View view) {
            super(view);
            descriptionView = (TextView) view.findViewById(R.id.event_description);

        }
    }
}

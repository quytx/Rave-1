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
public class EventDetailsBinder extends DataBinder<EventDetailsBinder.ViewHolder> {
    private List<EventData> mDataSet = new ArrayList<>();

    public EventDetailsBinder(DataBindAdapter dataBindAdapter) {
        super(dataBindAdapter);
    }

    @Override
    public ViewHolder newViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.detail_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void bindViewHolder(ViewHolder holder, int position) {
        EventData data = mDataSet.get(position);
        holder.detailTitleText.setText(data.detailTitles);
        holder.detailValueText.setText(data.details);
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

        TextView detailTitleText;
        TextView detailValueText;


        public ViewHolder(View view) {
            super(view);
            detailTitleText = (TextView) view.findViewById(R.id.detail_title_text);
            detailValueText = (TextView) view.findViewById(R.id.detail_field_text);

        }
    }
}

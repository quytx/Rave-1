package binders;

/**
 * Created by Jacob on 4/17/2015.
 */

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rave.rave.R;
import com.yqritc.recyclerviewmultipleviewtypesadapter.DataBindAdapter;
import com.yqritc.recyclerviewmultipleviewtypesadapter.DataBinder;

import java.util.ArrayList;
import java.util.List;

import Data.EventData;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by yqritc on 2015/03/20.
 */
public class HeaderBinder extends DataBinder<HeaderBinder.ViewHolder> {

    private List<EventData> mDataSet = new ArrayList<>();
    private boolean checked = false;

    public HeaderBinder(DataBindAdapter dataBindAdapter) {
        super(dataBindAdapter);
    }

    @Override
    public ViewHolder newViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.event_detail_header, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void bindViewHolder(final ViewHolder holder, int position) {
        EventData data = mDataSet.get(position);
        holder.mTitleText.setText(data.eventTitle);
        holder.profilePic.setImageResource(data.profilePic);
        holder.eventImageView.setImageResource(data.eventImage);

        holder.attendingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!checked) {
                    holder.attendingButton.setImageResource(R.drawable.check_mark_green);
                    checked = true;
                }
                else{
                    holder.attendingButton.setImageResource(R.drawable.check_mark_gray);
                    checked = false;
                }
            }
        });
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
}
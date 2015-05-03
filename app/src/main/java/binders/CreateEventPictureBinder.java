package binders;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.rave.rave.R;
import com.yqritc.recyclerviewmultipleviewtypesadapter.DataBindAdapter;
import com.yqritc.recyclerviewmultipleviewtypesadapter.DataBinder;

/**
 * Created by Jacob on 4/30/2015.
 */
public class CreateEventPictureBinder extends DataBinder<CreateEventPictureBinder.ViewHolder> {

    public CreateEventPictureBinder(DataBindAdapter dataBindAdapter) {
        super(dataBindAdapter);
    }

    @Override
    public ViewHolder newViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.create_event_pic_field, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void bindViewHolder(ViewHolder holder, int position){
    }

    @Override
    public int getItemCount() {
        return 1;
    }



    static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView uploadImageView;
        Button button;


        public ViewHolder(View view) {
            super(view);
            uploadImageView = (ImageView) view.findViewById(R.id.uploadImage);
            button = (Button) view.findViewById(R.id.selectImageButton);
        }
    }
}

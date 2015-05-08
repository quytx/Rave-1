package binders;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
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
    private Bitmap uploadImage = null;

    Context context;
//    final int REQUEST_IMAGE_CAPTURE = 1;

    public CreateEventPictureBinder(DataBindAdapter dataBindAdapter) {
        super(dataBindAdapter);
    }

    @Override
    public ViewHolder newViewHolder(ViewGroup parent) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.create_event_pic_field, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void bindViewHolder(final ViewHolder holder, int position){

        if(uploadImage != null){
            holder.uploadImageView.setImageBitmap(uploadImage);
        }

    }

    public void addImageResource(Bitmap newImage){
        uploadImage = newImage;
        notifyBinderDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return 1;
    }



    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView uploadImageView;
        Button selectImageButton;


        public ViewHolder(View view) {
            super(view);
            uploadImageView = (ImageView) view.findViewById(R.id.uploadImage);
            selectImageButton = (Button) view.findViewById(R.id.selectImageButton);
        }

        public ImageView getUploadImageView(){
            return uploadImageView;
        }

        public void setUploadImageView(Drawable d){
            uploadImageView.setImageDrawable(d);
            notify();
        }

    }

}

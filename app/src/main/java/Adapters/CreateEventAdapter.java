package Adapters;

import android.graphics.Bitmap;

import com.yqritc.recyclerviewmultipleviewtypesadapter.ListBindAdapter;

import binders.CreateEventPictureBinder;
import binders.CreateEventTextFieldBinder;

/**
 * Created by Jacob on 4/30/2015.
 */
public class CreateEventAdapter extends ListBindAdapter{

    public CreateEventAdapter() {
        addAllBinder(new CreateEventPictureBinder(this),
                new CreateEventTextFieldBinder(this));
    }

    public void setImageResource(Bitmap d){
        ((CreateEventPictureBinder) getDataBinder(0)).addImageResource(d);
    }


}

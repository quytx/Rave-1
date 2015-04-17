package Adapters;

import com.yqritc.recyclerviewmultipleviewtypesadapter.ListBindAdapter;

import java.util.List;

import Data.EventData;
import binders.EventDescriptionBinder;
import binders.HeaderBinder;

/**
 * Created by Jacob on 4/17/2015.
 */
public class ListAdapter extends ListBindAdapter {

    public ListAdapter() {
        addAllBinder(new HeaderBinder(this),
                new EventDescriptionBinder(this));
    }

    public void setEventDataSet(List<EventData> dataSet) {
        ((HeaderBinder) getDataBinder(0)).addAll(dataSet);
        ((EventDescriptionBinder) getDataBinder(1)).addAll(dataSet);
    }

}
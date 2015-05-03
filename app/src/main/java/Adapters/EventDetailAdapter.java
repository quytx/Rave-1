package Adapters;

import com.yqritc.recyclerviewmultipleviewtypesadapter.ListBindAdapter;

import java.util.List;

import Data.EventData;
import binders.EventDescriptionBinder;
import binders.EventDetailsBinder;
import binders.HeaderBinder;

/**
 * Created by Jacob on 4/17/2015.
 */
public class EventDetailAdapter extends ListBindAdapter {

    public EventDetailAdapter() {
        addAllBinder(new HeaderBinder(this),
                new EventDescriptionBinder(this),
                        new EventDetailsBinder(this));
    }

    //TODO: Clean up hardcoding of sublists... is there a more effecient way?
    public void setEventDataSet(List<EventData> dataSet) {
//        List<EventData> headerData = dataSet.subList(0,1);
//        List<EventData> descriptionData = dataSet.subList(0, 1);
//        List<EventData> detailsData = dataSet.subList(1,dataSet.size());

        ((HeaderBinder) getDataBinder(0)).addAll(dataSet);
        ((EventDescriptionBinder) getDataBinder(1)).addAll(dataSet);
        ((EventDetailsBinder) getDataBinder(2)).addAll(dataSet);

    }

}
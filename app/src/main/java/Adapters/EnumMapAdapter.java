package Adapters;

import com.yqritc.recyclerviewmultipleviewtypesadapter.EnumMapBindAdapter;

import binders.EventDescriptionBinder;
import binders.EventDetailsBinder;
import binders.HeaderBinder;

/**
 * Created by Jacob on 4/17/2015.
 */
public class EnumMapAdapter extends EnumMapBindAdapter<EnumMapAdapter.EventViewType> {

    enum EventViewType {
        HEADER, EVENTDESCRIPTION, EVENTDETAILS
    }

    public EnumMapAdapter() {
        putBinder(EventViewType.HEADER, new HeaderBinder(this));
        putBinder(EventViewType.EVENTDESCRIPTION, new EventDescriptionBinder(this));
        putBinder(EventViewType.EVENTDETAILS, new EventDetailsBinder(this));
    }

//    public void setSample2Data(List<EventData> dataSet) {
//        ((EventData) getDataBinder(EventData.class.)).addAll(dataSet);
//    }

    @Override
    public EventViewType getEnumFromPosition(int position) {
        if (position == 1) {
            return EventViewType.HEADER;
        } else if (position == 3) {
            return EventViewType.EVENTDETAILS;
        } else {
            return EventViewType.EVENTDESCRIPTION;
        }
    }

    @Override
    public EventViewType getEnumFromOrdinal(int ordinal) {
        return EventViewType.values()[ordinal];
    }
}

package binders;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import com.rave.rave.R;
import com.yqritc.recyclerviewmultipleviewtypesadapter.DataBindAdapter;
import com.yqritc.recyclerviewmultipleviewtypesadapter.DataBinder;

/**
 * Created by Jacob on 4/30/2015.
 */
public class CreateEventTextFieldBinder extends DataBinder<CreateEventTextFieldBinder.ViewHolder> {

    public CreateEventTextFieldBinder(DataBindAdapter dataBindAdapter) {
        super(dataBindAdapter);
    }

    @Override
    public ViewHolder newViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.create_event_text_fields, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void bindViewHolder(ViewHolder holder, int position) {
    }

    @Override
    public int getItemCount() {
        return 6;
    }


    static class ViewHolder extends RecyclerView.ViewHolder {

        EditText eventTitle;
        EditText eventDescription;
        EditText eventTheme;
        AutoCompleteTextView eventLocation;
//        DatePicker eventDate;
//        TimePicker eventTime;

        EditText eventDate;
        EditText eventTime;



        public ViewHolder(View view) {
            super(view);
            eventTitle = (EditText) view.findViewById(R.id.eventTitleField);
            eventDescription = (EditText) view.findViewById(R.id.eventDescriptionField);
            eventTheme = (EditText) view.findViewById(R.id.eventThemeField);
            eventLocation = (AutoCompleteTextView) view.findViewById(R.id.autoCompleteLocation);
//            eventDate = (DatePicker) view.findViewById(R.id.datePicker);
//            eventTime = (TimePicker) view.findViewById(R.id.timePicker);
            eventDate = (EditText) view.findViewById(R.id.eventDateField);
            eventTime = (EditText) view.findViewById(R.id.eventTimeField);

        }
    }
}

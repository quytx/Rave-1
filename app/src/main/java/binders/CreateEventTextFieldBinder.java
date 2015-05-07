package binders;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.rave.rave.R;
import com.yqritc.recyclerviewmultipleviewtypesadapter.DataBindAdapter;
import com.yqritc.recyclerviewmultipleviewtypesadapter.DataBinder;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Jacob on 4/30/2015.
 */
public class CreateEventTextFieldBinder extends DataBinder<CreateEventTextFieldBinder.ViewHolder> {

    public Context context;
    Calendar calendar = Calendar.getInstance();

    String eventTitleText = "";
    String eventDescriptionText = "";
    String eventThemeText = "";
    String eventLocationText ="";

    String eventDateText = "";
    String eventTimeText = "";


    public CreateEventTextFieldBinder(DataBindAdapter dataBindAdapter) {
        super(dataBindAdapter);
    }

    @Override
    public ViewHolder newViewHolder(ViewGroup parent) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.create_event_text_fields, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void bindViewHolder(final ViewHolder holder, int position) {
        eventTitleText = holder.eventTitle.getText().toString();
        eventDescriptionText = holder.eventDescription.getText().toString();
        eventThemeText = holder.eventTheme.getText().toString();
        eventLocationText = holder.eventLocation.getText().toString();

        eventDateText = holder.eventDate.getText().toString();
        eventTimeText = holder.eventTime.getText().toString();


        //Create pop-up dialog for date selector
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "MM/dd/yy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                holder.eventDate.setText(sdf.format(calendar.getTime()));
            }

        };

        holder.eventDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean gainFocus) {
                if (gainFocus) {
                    new DatePickerDialog(context, date, calendar
                            .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                            calendar.get(Calendar.DAY_OF_MONTH)).show();
                }
            }
        });

        //Create pop-up time selector dialog
        holder.eventTime.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean gainFocus) {
                if (gainFocus) {
                    showTimePicker();
                }
            }

            private void showTimePicker() {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        if(selectedMinute < 10){
                            holder.eventTime.setText(selectedHour + ":0" + selectedMinute );
                        }
                        else {
                            holder.eventTime.setText(selectedHour + ":" + selectedMinute);
                        }
                    }
                }, hour, minute, false);//12 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

        //Handle "done" button clicks
        holder.doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("CreateEventTextField", "Submit button clicked");
            }
        });
    }


    @Override
    public int getItemCount() {
        return 1;
    }

    public String getEventTitleText(){return eventTitleText;}

    public String getEventDescriptionText() {return eventDescriptionText;}

    public String getEventThemeText(){return eventThemeText;}

    public String getEventTimeText(){return eventTimeText;}

    public String getEventDateText() { return eventDateText;}


    public String getEventLocationText() {return eventLocationText;}




    static class ViewHolder extends RecyclerView.ViewHolder {

        EditText eventTitle;
        EditText eventDescription;
        EditText eventTheme;
        AutoCompleteTextView eventLocation;

        EditText eventDate;
        EditText eventTime;

        Button doneButton;



        public ViewHolder(View view) {
            super(view);

            eventTitle = (EditText) view.findViewById(R.id.eventTitleField);
            eventDescription = (EditText) view.findViewById(R.id.eventDescriptionField);
            eventTheme = (EditText) view.findViewById(R.id.eventThemeField);
            eventLocation = (AutoCompleteTextView) view.findViewById(R.id.autoCompleteLocation);
            eventDate = (EditText) view.findViewById(R.id.eventDateField);
            eventTime = (EditText) view.findViewById(R.id.eventStartTimeField);
            doneButton = (Button) view.findViewById(R.id.submitButton);

        }

    }


}
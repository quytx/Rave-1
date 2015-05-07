package com.rave.rave;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import savage.UrlJsonAsyncTask;

/**
 * Created by Jacob on 5/6/2015.
 */
public class CreateEventActivity extends ActionBarActivity {

    private SharedPreferences mPreferences;


    private Toolbar toolbar;
    private Uri picUri;
    private Bitmap imageResource;
    private String mCurrentPhotoPath;

    //URL for connecting to server
    private final static String CREATE_EVENT_API_ENDPOINT_URL = "http://madrave.herokuapp.com/api/v1/events";

    //EditText Details
    protected String eventTitleText;
    protected String eventLocationText;
    protected String eventDescriptionText;
    protected String eventStartTimeText;
    protected String eventEndTimeText;
    protected String eventDateText;

    ImageView uploadImageView;
    Button selectImageButton;

    EditText eventTitle;
    EditText eventDescription;
    AutoCompleteTextView eventLocation;

    EditText eventDate;
    EditText eventStartTime;
    EditText eventEndTime;

    Button submitButton;

    Calendar calendar = Calendar.getInstance();

    final int REQUEST_IMAGE_CAPTURE = 1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        mPreferences = getSharedPreferences("CurrentUser", MODE_PRIVATE);


        uploadImageView = (ImageView) findViewById(R.id.uploadImage);
        selectImageButton = (Button) findViewById(R.id.selectImageButton);

        eventTitle = (EditText) findViewById(R.id.eventTitleField);
        eventDescription = (EditText) findViewById(R.id.eventDescriptionField);
        eventLocation = (AutoCompleteTextView) findViewById(R.id.autoCompleteLocation);
        eventDate = (EditText) findViewById(R.id.eventDateField);
        eventStartTime = (EditText) findViewById(R.id.eventStartTimeField);
        eventEndTime = (EditText) findViewById(R.id.eventEndTimeField);
        submitButton = (Button) findViewById(R.id.submitButton);

        picUri = null;
        setResult(RESULT_OK);


        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setTitle("New Event");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        selectImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });



        eventDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean gainFocus) {
                if (gainFocus) {
                    selectDate();
                }
            }
        });


        eventStartTime.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean gainFocus) {
                if (gainFocus) {
                    showTimePickerForStart();
                }
            }
        });

        eventEndTime.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean gainFocus) {
                if (gainFocus) {
                    showTimePickerForEnd();
                }
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eventTitleText = eventTitle.getText().toString();
                eventLocationText = eventLocation.getText().toString();
                eventDescriptionText = eventDescription.getText().toString();
                eventStartTimeText = eventStartTime.getText().toString();
                eventDateText = eventDate.getText().toString();
                eventEndTimeText = eventEndTime.getText().toString();
                submitNewEvent(CREATE_EVENT_API_ENDPOINT_URL);
            }
        });
    }



    private void dispatchTakePictureIntent() {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        takePictureIntent.putExtra("crop", "true");
//        takePictureIntent.putExtra("outputX", 150);
//        takePictureIntent.putExtra("outputY", 150);
//        takePictureIntent.putExtra("aspectX", 1);
//        takePictureIntent.putExtra("aspectY", 1);
//        takePictureIntent.putExtra("scale", true);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                ex.printStackTrace();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(photoFile));
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);

            }
        }

        takePictureIntent.putExtra("outputFormat",
                Bitmap.CompressFormat.JPEG.toString());


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            File currPhotoFile = new File(mCurrentPhotoPath);
            picUri = Uri.fromFile(currPhotoFile);
//            Bundle extras = data.getExtras();
            try {
                imageResource = MediaStore.Images.Media.getBitmap(this.getContentResolver(), picUri);
                uploadImageView.setImageBitmap(imageResource);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void selectDate() {
        //Create pop-up dialog for date selector
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "MM-dd-yyyy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                eventDate.setText(sdf.format(calendar.getTime()));
            }

        };

        new DatePickerDialog(this, date, calendar
                .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)).show();
    }


    //                  showTimePicker();


    private void showTimePickerForStart() {
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(CreateEventActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                if(selectedMinute < 10){
                    eventStartTime.setText(selectedHour + ":0" + selectedMinute);
                }
                else {
                    eventStartTime.setText(selectedHour + ":" + selectedMinute);
                }
            }
        }, hour, minute, false);//12 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
    }

    private void showTimePickerForEnd() {
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(CreateEventActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                if(selectedMinute < 10){
                    eventEndTime.setText(selectedHour + ":0" + selectedMinute);
                }
                else {
                    eventEndTime.setText(selectedHour + ":" + selectedMinute);
                }
            }
        }, hour, minute, false);//12 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
    }

    private void submitNewEvent(String url) {
        CreateEventTask createEventTask = new CreateEventTask(CreateEventActivity.this);
        createEventTask.setMessageLoading("Submitting new event...");
        createEventTask.execute(url);

    }



    private class CreateEventTask extends UrlJsonAsyncTask {

        public CreateEventTask(Context context) {
            super(context);
        }

        @Override
        protected JSONObject doInBackground(String... urls) {
            DefaultHttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost(urls[0]);   //urls[0] api/v1/sessions
            JSONObject holder = new JSONObject();
            JSONObject userObj = new JSONObject();
            String response = null;
            JSONObject json = new JSONObject();

            try {
                try {
                    // setup the returned values in case
                    // something goes wrong
//                    json.put("success", false);
//                    json.put("info", "Something went wrong. Retry!");
                    // add the user email and password to
                    // the params
                    userObj.put("user_id", mPreferences.getString("UserID","none"));
                    userObj.put("title", eventTitleText);
                    userObj.put("description", eventDescriptionText);
                    userObj.put("location", eventLocationText);
                    userObj.put("start_time", eventDateText + " " + eventStartTimeText + ":00");
                    userObj.put("end_time", eventDateText + " " + eventEndTimeText + ":00");

                    holder.put("event", userObj);
                    StringEntity se = new StringEntity(holder.toString());
                    post.setEntity(se);

                    // setup the request headers
                    post.setHeader("Accept", "application/json");
                    post.setHeader("Content-Type", "application/json");

                    ResponseHandler<String> responseHandler = new BasicResponseHandler();
                    response = client.execute(post, responseHandler);
                    json = new JSONObject(response);

                } catch (HttpResponseException e) {
                    e.printStackTrace();
                    Log.e("ClientProtocol", "" + e);
                    json.put("info", "Email and/or password are invalid. Retry!");
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("IO", "" + e);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Log.e("JSON", "" + e);
            }

            return json;
        }

        @Override
        protected void onPostExecute(JSONObject json) {
            try {
                if (json.getBoolean("success")) {
//                    // everything is ok
//                    SharedPreferences.Editor editor = mPreferences.edit();
//                    // save the returned auth_token into
//                    // the SharedPreferences
//                    editor.putString("AuthToken", json.getJSONObject("data").getString("auth_token"));
//                    editor.commit();
//
//
//                    String token = mPreferences.getString("AuthToken", "missing");
//                    Log.d("bimbam", token);
                    // launch the HomeActivity and close this one
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                Toast.makeText(context, json.getString("info"), Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                // something went wrong: show a Toast
                // with the exception message
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
            } finally {
                super.onPostExecute(json);
            }
        }
    }
}

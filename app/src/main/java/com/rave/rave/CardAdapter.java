package com.rave.rave;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;

/**
 * Created by Jacob on 3/23/2015.
 */
public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {

    private String[] event_titles;
    private String[] event_addresses;
    private String[] event_photos;
    private String[] event_ids;

    public CardAdapter(String[] event_titles, String[] event_addresses, String[] event_ids, String[] event_photos) {
        this.event_titles = event_titles;
        this.event_addresses = event_addresses;
        this.event_photos = event_photos;
        this.event_ids = event_ids;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        TextView addressTextView;
        //ImageView imageView;
        DownloadImageTask task;
        String event_id;


        public ViewHolder(View itemView) {                 // Creating ViewHolder Constructor with View and viewType As a parameter
            super(itemView);

            titleTextView = (TextView) itemView.findViewById(R.id.event_title_text);
            addressTextView = (TextView) itemView.findViewById(R.id.event_address_text);
            //imageView = (ImageView) itemView.findViewById(R.id.event_image);
            task = new DownloadImageTask((ImageView) itemView.findViewById(R.id.event_image));

        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_card,parent,false); //Inflating the layout
        ViewHolder vhItem = new ViewHolder(itemView);

        return vhItem; // Returning the created object
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.titleTextView.setText(event_titles[position]);
        holder.addressTextView.setText(event_addresses[position]);
        holder.event_id = event_ids[position];
        holder.task.execute(event_photos[position]);

    }

    @Override
    public int getItemCount() {
        return event_titles.length;
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}

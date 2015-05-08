package com.rave.rave;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Jacob on 3/23/2015.
 */
public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {

    private String[] event_titles;
    private String[] event_addresses;
    private String[] event_ids;
    private String[] event_picture_url;

    public CardAdapter(String[] event_titles, String[] event_addresses, String[] event_ids, String[] event_picture_url) {
        this.event_titles = event_titles;
        this.event_addresses = event_addresses;
        this.event_ids = event_ids;
        this.event_picture_url = event_picture_url;

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        TextView addressTextView;
        ImageView imageView;
        String event_id;


        public ViewHolder(View itemView) {                 // Creating ViewHolder Constructor with View and viewType As a parameter
            super(itemView);

            titleTextView = (TextView) itemView.findViewById(R.id.event_title_text);
            addressTextView = (TextView) itemView.findViewById(R.id.event_address_text);
            imageView = (ImageView) itemView.findViewById(R.id.event_image);
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
        holder.imageView.setImageResource(R.drawable.madison_header_background);
        holder.event_id = event_ids[position];
    }

    @Override
    public int getItemCount() {
        return event_titles.length;
    }
}

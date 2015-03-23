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

    private String event_titles[];

    public CardAdapter(String[] event_titles) {
        this.event_titles = event_titles;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView imageView;


        public ViewHolder(View itemView) {                 // Creating ViewHolder Constructor with View and viewType As a parameter
            super(itemView);

            textView = (TextView) itemView.findViewById(R.id.event_title_text);
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
        holder.textView.setText(event_titles[position-1]);
        holder.imageView.setImageResource(R.drawable.madison_header_background);
    }

    @Override
    public int getItemCount() {
        return event_titles.length;
    }
}

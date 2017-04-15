package com.firdavsiimajidzoda.myvideplayer.Controllers;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.firdavsiimajidzoda.myvideplayer.Models.FeedItem;
import com.firdavsiimajidzoda.myvideplayer.R;
import com.firdavsiimajidzoda.myvideplayer.UI.VideoDetail;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by firdavsiimajidzoda on 4/12/17.
 */

/**
 *  Adapter for recycler view
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewholder> {
    // Fields
    ArrayList<FeedItem> feedItems;
    Context context;
    public  MyAdapter(Context context, ArrayList<FeedItem> feedItems){
        // Passing Contex feed items
        this.context = context;
        this.feedItems = feedItems;
    }

    @Override
    public MyViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Setting view holder


        View view  = LayoutInflater.from(context).inflate(R.layout.custum_row_video_item, parent, false);
        MyViewholder holder = new MyViewholder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewholder holder, int position) {
        // Binding each item

        //Animate item

        YoYo.with(Techniques.BounceIn).playOn(holder.cardView);
        // Get current item
        final FeedItem current = feedItems.get(position);

        // Set the items field
        if (current.getTitle() == null || current.getTitle().equals("")){
            holder.title.setText("Title: Unknown");
        } else {
            holder.title.setText(current.getTitle());
        }

        if (current.getDuration() == null || current.getDuration().equals("")){
            holder.duration.setText("Duration: unknown");
        } else {
            holder.duration.setText("Duration: "+calculateSeconds(current.getDuration()));
        }

        if (current.getPubDate() == null || current.getPubDate().equals("")){
            holder.pubDate.setText("Year: Unknown");
        } else {
            holder.pubDate.setText("Year: "+current.getPubDate());
        }

        if (current.getDescription() == null || current.getDescription().equals("")){
            holder.description.setText("Description: Unknown");
        } else {
            holder.description.setText(current.getDescription());
        }

        // Download the thumbnail picture and set it to items pictuer

        if (current.getThumbnail() == null || current.getThumbnail().equals("")){
            holder.image.setBackgroundResource(R.drawable.default_video);
        } else {
            Picasso.with(context).load(current.getThumbnail()).into(holder.image);
        }



        // Open video detail on click
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, VideoDetail.class);
                intent.putExtra("link", current.getLink());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return feedItems.size();
    }

    public class MyViewholder extends RecyclerView.ViewHolder {
        // Item fields
        TextView title;
        ImageView image;
        TextView duration;
        TextView pubDate;
        TextView description;
        CardView cardView;
        public MyViewholder(View itemView) {
            super(itemView);

            // Initializing item fields
            title = (TextView)itemView.findViewById(R.id.title_text_view);
            image = (ImageView) itemView.findViewById(R.id.imageView);
            duration = (TextView)itemView.findViewById(R.id.duration_text_view);
            pubDate = (TextView)itemView.findViewById(R.id.pub_date_text_view);
            description = (TextView)itemView.findViewById(R.id.description_text_view);
            cardView = (CardView)itemView.findViewById(R.id.cardView);

        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    /**
     * Calculates secodnds into minute:seconds format
     * @param seconds
     * @return minuete:second format
     */
    private String calculateSeconds(String seconds){
        System.out.print(seconds);
        int sec = Integer.parseInt(seconds);
        int minute = sec/60;
        int second = sec - (minute*60);

        return minute+":"+second;
    }
}



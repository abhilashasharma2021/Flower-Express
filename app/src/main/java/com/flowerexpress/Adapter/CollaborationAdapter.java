package com.flowerexpress.Adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.flowerexpress.Models.CollaborationData;
import com.flowerexpress.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CollaborationAdapter extends RecyclerView.Adapter<CollaborationAdapter.ViewHolder> {


    List<CollaborationData> collaborationDataList;
    Context context;

    public CollaborationAdapter(List<CollaborationData> collaborationDataList, Context context) {
        this.collaborationDataList = collaborationDataList;
        this.context = context;
    }

    @NonNull
    @Override
    public CollaborationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.collaborations, parent, false);
        return new CollaborationAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CollaborationAdapter.ViewHolder holder, int position) {
        CollaborationData data = collaborationDataList.get(position);

        holder.txt_name.setText(data.getName());

        Log.e("scjkxjvkj", data.getPath() + data.getImage() );

        try {
           // Picasso.get().load(data.getPath()+data.getImage()).error(R.drawable.lo).into(holder.image);
            Glide.with(context)
                    .load(data.getPath() + data.getImage())
                    .centerInside().diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(new CustomTarget<Drawable>(512, 512) {
                        @Override
                        public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                            holder.image.setImageDrawable(resource);
                        }

                        @Override
                        public void onLoadCleared(@Nullable Drawable placeholder) {

                        }
                    });

        } catch (Exception e) {


        }

    }

    @Override
    public int getItemCount() {
        return collaborationDataList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView txt_name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.image);
            txt_name = itemView.findViewById(R.id.txt_name);
        }
    }
}

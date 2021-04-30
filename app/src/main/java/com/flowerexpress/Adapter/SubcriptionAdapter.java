package com.flowerexpress.Adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
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
import com.flowerexpress.Models.SubscriptionData;
import com.flowerexpress.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SubcriptionAdapter extends RecyclerView.Adapter<SubcriptionAdapter.ViewHolder> {


    List<SubscriptionData> subscriptionDataList;
    Context context;

    public SubcriptionAdapter(List<SubscriptionData> subscriptionDataList, Context context) {
        this.subscriptionDataList = subscriptionDataList;
        this.context = context;
    }

    @NonNull
    @Override
    public SubcriptionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.subcription_layout, parent, false);
        return new SubcriptionAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubcriptionAdapter.ViewHolder holder, int position) {
        SubscriptionData data=subscriptionDataList.get(position);
        holder.txt_name.setText(data.getName());
        holder.txt_price.setText(data.getCode());
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

        }catch (Exception e){


        }

    }

    @Override
    public int getItemCount() {
        return subscriptionDataList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
      TextView txt_price,txt_name;
      ImageView image;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_price=itemView.findViewById(R.id.txt_price);
            txt_name=itemView.findViewById(R.id.txt_name);
            image=itemView.findViewById(R.id.image);
        }
    }
}

package com.flowerexpress.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.flowerexpress.Activities.ProductDetailActivity;
import com.flowerexpress.Models.WhatsNewData;
import com.flowerexpress.Others.AppConstant;
import com.flowerexpress.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class WhatsNewAdapter extends RecyclerView.Adapter<WhatsNewAdapter.ViewHolder> {


    List<WhatsNewData> whatsNewDataList;
    Context context;

    public WhatsNewAdapter(List<WhatsNewData> whatsNewDataList, Context context) {
        this.whatsNewDataList = whatsNewDataList;
        this.context = context;
    }

    @NonNull
    @Override
    public WhatsNewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.whatsnew_list, parent, false);
        return new WhatsNewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WhatsNewAdapter.ViewHolder holder, int position) {

        WhatsNewData data = whatsNewDataList.get(position);
        holder.txt_variationMax.setText(data.getVariationMax());
        holder.txt_variMin.setText(data.getVariationMin());
        holder.title.setText(data.getName());

        Log.e("fgjkhfgjkf",data.getPath()+"");
        Log.e("fgjkhfgjkf",data.getImage()+"");

        try {
           // Glide.with(context).load(data.getPath()+data.getImage()).into(holder.image);
          //  Picasso.get().load(data.getPath()+data.getImage()).error(R.drawable.lo).into(holder.image);
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


        }
        catch (Exception e){


        }

        holder.rl_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppConstant.sharedpreferences =context.getSharedPreferences(AppConstant.MYPREFERENCE, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = AppConstant.sharedpreferences.edit();
                editor.putString(AppConstant.ProductId,data.getId());
                editor.commit();

                context.startActivity(new Intent(context, ProductDetailActivity.class));
            }
        });


    }

    @Override
    public int getItemCount() {
        return whatsNewDataList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView image;
        public TextView title,txt_variMin,txt_variationMax;
        public   RelativeLayout rl_container;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.image);
            rl_container = itemView.findViewById(R.id.rl_container);
            title = itemView.findViewById(R.id.title);
            txt_variationMax = itemView.findViewById(R.id.txt_variationMax);
            txt_variMin = itemView.findViewById(R.id.txt_variMin);
        }
    }
}

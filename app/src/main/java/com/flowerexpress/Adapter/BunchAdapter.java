package com.flowerexpress.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.Uri;
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
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.flowerexpress.Activities.ProductDetailActivity;
import com.flowerexpress.Models.BunchData;
import com.flowerexpress.Others.AppConstant;
import com.flowerexpress.R;
import com.google.android.material.card.MaterialCardView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.List;

import static android.app.Activity.RESULT_OK;

public class BunchAdapter extends RecyclerView.Adapter<BunchAdapter.ViewHolder> {


    List<BunchData> bunchDataList;
    Context context;

    public BunchAdapter(List<BunchData> bunchDataList, Context context) {
        this.bunchDataList = bunchDataList;
        this.context = context;
    }

    @NonNull
    @Override
    public BunchAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.subcategory_layout, parent, false);
        return new BunchAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BunchAdapter.ViewHolder holder, int position) {
        BunchData data = bunchDataList.get(position);

        holder.txt_name.setText(data.getName());
        holder.txt_price.setText(data.getPrice());

        Log.e("fdjkfj",data.getPath() + data.getImage());

        try {
            //Picasso.get().load(data.getPath() + data.getImage()).error(R.drawable.lo).into( holder.image);
            /*Compress image */
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

            Log.e("dffdgb",e.getMessage());
        }





        holder.imageDesigner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
        return bunchDataList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView txt_name,txt_price;
        MaterialCardView imageDesigner;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.image);
            txt_name = itemView.findViewById(R.id.txt_name);
            txt_price = itemView.findViewById(R.id.txt_price);
            imageDesigner = itemView.findViewById(R.id.imageDesigner);
        }
    }



}

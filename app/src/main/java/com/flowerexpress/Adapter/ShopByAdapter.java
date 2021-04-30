package com.flowerexpress.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.flowerexpress.Activities.CategoryActivity;
import com.flowerexpress.Models.ShopByData;
import com.flowerexpress.Models.SliderModel;
import com.flowerexpress.Others.AppConstant;
import com.flowerexpress.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ShopByAdapter extends RecyclerView.Adapter<ShopByAdapter.ViewHolder> {


    List<ShopByData> shopByDataList;
    Context context;

    public ShopByAdapter(List<ShopByData> shopByDataList, Context context) {
        this.shopByDataList = shopByDataList;
        this.context = context;
    }

    @NonNull
    @Override
    public ShopByAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.shop_by_layout, parent, false);
        return new ShopByAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShopByAdapter.ViewHolder holder, int position) {
      final   ShopByData data = shopByDataList.get(position);

      holder.txt_name.setText(data.getName());

        try {
           // Picasso.get().load(data.getPath() + data.getImage()).error(R.drawable.lo).into(holder.image);
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

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppConstant.sharedpreferences =context.getSharedPreferences(AppConstant.MYPREFERENCE, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = AppConstant.sharedpreferences.edit();
                editor.putString(AppConstant.CategoryId,data.getId());
                editor.putString(AppConstant.CategoryName,data.getName());
                editor.putString(AppConstant.ShopStatus,"0");/*shop by type*/
                editor.commit();

                context.startActivity(new Intent(context, CategoryActivity.class));
            }
        });


    }

    @Override
    public int getItemCount() {
        return shopByDataList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        CardView card;
        TextView txt_name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            card = itemView.findViewById(R.id.card);
            image = itemView.findViewById(R.id.image);
            txt_name = itemView.findViewById(R.id.txt_name);
        }
    }
}


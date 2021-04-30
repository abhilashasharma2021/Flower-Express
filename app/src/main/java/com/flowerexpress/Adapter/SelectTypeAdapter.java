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
import com.flowerexpress.Activities.CategoryActivity;
import com.flowerexpress.Activities.ProductDetailActivity;
import com.flowerexpress.Models.BunchData;
import com.flowerexpress.Models.SelectTypeData;
import com.flowerexpress.Others.AppConstant;
import com.flowerexpress.R;
import com.google.android.material.card.MaterialCardView;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SelectTypeAdapter extends RecyclerView.Adapter<SelectTypeAdapter.Viewholder> {

    List<SelectTypeData> typeDataList;
    Context context;
    String strStatusHome="";

    public SelectTypeAdapter(List<SelectTypeData> typeDataList, Context context) {
        this.typeDataList = typeDataList;
        this.context = context;
    }


    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.subcategory_layout,parent,false);


        return new SelectTypeAdapter.Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        AppConstant.sharedpreferences=context.getSharedPreferences(AppConstant.MYPREFERENCE, Context.MODE_PRIVATE);
        strStatusHome=AppConstant.sharedpreferences.getString(AppConstant.StatusHome,"");
        SelectTypeData data = typeDataList.get(position);

        holder.txt_name.setText(data.getName());

        holder.imageDesigner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, ProductDetailActivity.class));
            }
        });


        Log.e("dslkjfdjv",data.getPath() + data.getImage());
        try {
         //   Picasso.get().load(data.getPath() + data.getImage()).error(R.drawable.lo).into( holder.image);
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



        if (strStatusHome.equals("0")){

               holder.image.setOnClickListener(new View.OnClickListener() {
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
        else
        {

            holder.rl_container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AppConstant.sharedpreferences =context.getSharedPreferences(AppConstant.MYPREFERENCE, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = AppConstant.sharedpreferences.edit();
                    editor.putString(AppConstant.CategoryId,data.getId());
                    editor.putString(AppConstant.CategoryName,data.getName());
                    editor.commit();

                    context.startActivity(new Intent(context, CategoryActivity.class));
                }
            });
        }





    }

    @Override
    public int getItemCount() {
        return typeDataList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView txt_name;
        RelativeLayout rl_container;
        MaterialCardView imageDesigner;
        public Viewholder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.image);
            rl_container = itemView.findViewById(R.id.rl_container);
            txt_name = itemView.findViewById(R.id.txt_name);

            imageDesigner = itemView.findViewById(R.id.imageDesigner);
        }
    }
}

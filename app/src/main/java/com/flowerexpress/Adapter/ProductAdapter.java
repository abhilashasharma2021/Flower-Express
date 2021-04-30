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
import com.flowerexpress.Models.ProductData;
import com.flowerexpress.Others.AppConstant;
import com.flowerexpress.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {


    List<ProductData> productDataList;
    Context context;

    public ProductAdapter(List<ProductData> productDataList, Context context) {
        this.productDataList = productDataList;
        this.context = context;
    }

    @NonNull
    @Override
    public ProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.product_list, parent, false);
        return new ProductAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ViewHolder holder, int position) {

        ProductData data = productDataList.get(position);
        holder.txt_name.setText(data.getName());



        Log.e("dxcvkldv",data.getPath()+data.getImage());

        try {
       //  Picasso.get().load(data.getPath()+data.getImage()).error(R.drawable.lo).into(holder.dImage);
          //Glide.with(context).load(data.getPath()+data.getImage()).into(holder.dImage);
            Glide.with(context)
                    .load(data.getPath() + data.getImage())
                    .centerInside().diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(new CustomTarget<Drawable>(512, 512) {
                        @Override
                        public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                            holder.dImage.setImageDrawable(resource);
                        }

                        @Override
                        public void onLoadCleared(@Nullable Drawable placeholder) {

                        }
                    });

        }catch (Exception e){
            Log.e("kvjnkcjv", e.getMessage() );
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
        return productDataList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView dImage;
        TextView txt_name;
        RelativeLayout rl_container;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            dImage = itemView.findViewById(R.id.dImage);
            txt_name = itemView.findViewById(R.id.txt_name);
            rl_container = itemView.findViewById(R.id.rl_container);
        }
    }
}

package com.flowerexpress.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.androidnetworking.common.Priority;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.flowerexpress.Models.SliderModel;
import com.flowerexpress.R;
import com.smarteist.autoimageslider.SliderViewAdapter;
import com.squareup.picasso.Picasso;

import java.util.List;


public class SliderAdapter extends SliderViewAdapter<SliderAdapter.SliderAdapterViewHolder> {

    private Context context;
    List<SliderModel> dataAdapters;

    public SliderAdapter(List<SliderModel> getDataAdapter, Context context) {
        this.context = context;
        this.dataAdapters = getDataAdapter;
    }
    @Override
    public SliderAdapterViewHolder onCreateViewHolder(ViewGroup parent) {

        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.slider_layout, null);
        return new SliderAdapterViewHolder(inflate);
    }


    @Override
    public void onBindViewHolder(SliderAdapterViewHolder viewHolder, int position) {

        final SliderModel dataAdapterOBJ = dataAdapters.get(position);


        Log.e("dsxjdklsjcl",dataAdapterOBJ.getPath() + dataAdapterOBJ.getImage());
        try {
            Glide.with(context)
                    .load(dataAdapterOBJ.getPath() + dataAdapterOBJ.getImage())
                    .centerInside().diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(new CustomTarget<Drawable>(512, 512) {
                        @Override
                        public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                            viewHolder.img_Background.setImageDrawable(resource);
                        }

                        @Override
                        public void onLoadCleared(@Nullable Drawable placeholder) {

                        }
                    });
        } catch (Exception e) {


        }

     //   Glide.with(context).load( dataAdapterOBJ.getPath() + dataAdapterOBJ.getImage() ).into(viewHolder.img_Background );

      /*  Glide.with(context).load( dataAdapterOBJ.getPath() + dataAdapterOBJ.getImage() )
                .error( R.drawable.lo )
                .override(320,180)
                .centerCrop()
                .into(viewHolder.img_Background );*/


    }

    @Override
    public int getCount() {
        return dataAdapters.size();
    }

    public static class SliderAdapterViewHolder extends SliderViewAdapter.ViewHolder {

        View itemView;

        ImageView img_Background;


        public SliderAdapterViewHolder(View itemView) {
            super(itemView);

            img_Background = itemView.findViewById(R.id.img_Background);
            this.itemView = itemView;

        }
    }

  /*  private void showImage(final String URL) {
        new Thread(new Runnable() {

            @Override
            public void run() {
                URL url = new URL(URL);
                Bitmap bm = BitmapFactory.decodeStream(url.openConnection()
                        .getInputStream());

                int width = bm.getWidth();
                int height = bm.getHeight();
                float scaleWidth = ((float) 100) / width;
                float scaleHeight = ((float) 100) / height;
                // CREATE A MATRIX FOR THE MANIPULATION
                Matrix matrix = new Matrix();
                // RESIZE THE BIT MAP
                matrix.postScale(scaleWidth, scaleHeight);

                // "RECREATE" THE NEW BITMAP
                final   Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width,
                        height, matrix, false);

                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                         .setImageBitmap(resizedBitmap);
                    }
                });
            }
        }).start();
    }*/
}

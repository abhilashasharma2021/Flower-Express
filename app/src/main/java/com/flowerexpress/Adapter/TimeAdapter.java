package com.flowerexpress.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.flowerexpress.Models.TimeData;
import com.flowerexpress.R;

import java.util.List;

public class TimeAdapter extends RecyclerView.Adapter<TimeAdapter.ViewHolder> {

    List<TimeData> timeDataList;
    Context context;
    int index = -1;

    public TimeAdapter(List<TimeData> timeDataList, Context context) {
        this.timeDataList = timeDataList;
        this.context = context;
    }

    @NonNull
    @Override
    public TimeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.time_layout, parent, false);
        return new TimeAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TimeAdapter.ViewHolder holder, int position) {
        TimeData data = timeDataList.get(position);
        holder.relative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                index = position;
                notifyDataSetChanged();
            }
        });


        if (index==position){
            holder.relative.setBackground(context.getResources().getDrawable(R.drawable.select_border));
            holder.time.setTextColor(context.getResources().getColor(R.color.white));
        }else {
            holder.relative.setBackground(context.getResources().getDrawable(R.drawable.selected_border));
            holder.time.setTextColor(context.getResources().getColor(R.color.black));
        }

    }

    @Override
    public int getItemCount() {
        return timeDataList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {


        RelativeLayout relative;
        TextView time;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            relative = itemView.findViewById(R.id.relative);
            time = itemView.findViewById(R.id.time);

        }
    }
}


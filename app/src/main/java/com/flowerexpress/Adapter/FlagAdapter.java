package com.flowerexpress.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.flowerexpress.Models.FlagsData;
import com.flowerexpress.R;

import java.util.List;

public class FlagAdapter extends RecyclerView.Adapter<FlagAdapter.ViewHolder> {


    List<FlagsData> flagsDataList;
    Context context;

    public FlagAdapter(List<FlagsData> flagsDataList, Context context) {
        this.flagsDataList = flagsDataList;
        this.context = context;
    }

    @NonNull
    @Override
    public FlagAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.flag_list, parent, false);
        return new FlagAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FlagAdapter.ViewHolder holder, int position) {
        FlagsData data = flagsDataList.get(position);

        if (position % 2 == 0) {
            holder.flagImg.setImageResource(R.drawable.kuwaitflag);
            holder.flagName.setText("Kuwait");
        } else if (position % 3 == 1) {
            holder.flagImg.setImageResource(R.drawable.bahrainflag);
            holder.flagName.setText("Bahrain");
        } else {
            holder.flagImg.setImageResource(R.drawable.saudiflag);
            holder.flagName.setText("Saudi arabia");
        }


        holder.flagName.setText(data.getCountryName());

    }

    @Override
    public int getItemCount() {
        return flagsDataList.size();
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

        ImageView flagImg;
        TextView flagName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            flagImg = itemView.findViewById(R.id.flagImg);
            flagName = itemView.findViewById(R.id.flagName);
        }
    }
}

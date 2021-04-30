package com.flowerexpress.Others;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.deishelon.roundedbottomsheet.RoundedBottomSheetDialogFragment;
import com.flowerexpress.Adapter.FlagAdapter;
import com.flowerexpress.Models.FlagsData;
import com.flowerexpress.R;

import java.util.ArrayList;
import java.util.List;


public class BottomSheet extends RoundedBottomSheetDialogFragment {
    List<FlagsData> flagsDataList = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.bottomsheet_flags, container, false);

        RecyclerView flagsRecycler = view.findViewById(R.id.flagsRecycler);
        flagsRecycler.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        flagsRecycler.setItemViewCacheSize(20);
        flagsRecycler.setHasFixedSize(true);
        flagsRecycler.setAdapter(new FlagAdapter(flagsDataList, getActivity()));

        showFlags();

        return view;
    }

    public void showFlags() {

        FlagsData data = new FlagsData("Kuwait");

        for (int i = 0; i <5 ; i++) {
            flagsDataList.add(data);
        }



    }


}

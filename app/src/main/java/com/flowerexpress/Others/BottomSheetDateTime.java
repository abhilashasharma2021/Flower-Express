package com.flowerexpress.Others;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.deishelon.roundedbottomsheet.RoundedBottomSheetDialogFragment;
import com.flowerexpress.Activities.CartActivity;
import com.flowerexpress.Activities.ProductDetailActivity;
import com.flowerexpress.Adapter.TimeAdapter;
import com.flowerexpress.Models.TimeData;
import com.flowerexpress.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class BottomSheetDateTime extends RoundedBottomSheetDialogFragment {

    DatePickerDialog datePickerDialog;
    TimeAdapter adapter;
    List<TimeData> timeDataList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.bottomsheet_datetime, container, false);

        TextView selectedDate = view.findViewById(R.id.selectedDate);
        TextView todayDate = view.findViewById(R.id.todayDate);
        TextView nextDate = view.findViewById(R.id.nextDate);
        LinearLayout linearDate = view.findViewById(R.id.linearDate);
        RecyclerView timeRecycler = view.findViewById(R.id.timeRecycler);
        Button btnContinue = view.findViewById(R.id.btnContinue);

        timeRecycler.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        timeRecycler.setHasFixedSize(true);
        timeRecycler.setItemViewCacheSize(20);
        adapter = new TimeAdapter(timeDataList, getActivity());
        timeRecycler.setAdapter(adapter);


        try {
            Date dateC = new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy");
            String cDate = simpleDateFormat.format(dateC);
            String[] splitDate1 = cDate.split("-");
            String lDate1 = splitDate1[0];
            String lMonth1 = splitDate1[1];
            todayDate.setText(" " + lDate1 + "\n" + lMonth1);

            Calendar c = Calendar.getInstance();

            try {
                c.setTime(simpleDateFormat.parse(cDate));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            c.add(Calendar.DATE, 1);
            SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MMM-yyyy");
            String output = sdf1.format(c.getTime());

            Log.e("kmsdlskd", output+"");

            String[] splitDate = output.split("-");
            String lDate = splitDate[0];
            String lMonth = splitDate[1];

            nextDate.setText(" " + lDate + "\n" + lMonth);


        } catch (Exception e) {
            Log.e("jkdefre", e.getMessage());
        }


        linearDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        String date = dayOfMonth + "-" + (month + 1) + "-" + year;
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
                        SimpleDateFormat output = new SimpleDateFormat("dd-MMM-yyyy");

                        try {
                                Date sDate = simpleDateFormat.parse(date);
                            String fDate = output.format(sDate);
                            String[] splitDate1 = fDate.split("-");
                            String lDate1 = splitDate1[0];
                            String lMonth1 = splitDate1[1];
                            selectedDate.setText(" " + lDate1 + "\n" + lMonth1);
                        } catch (Exception e) {
                            Log.e("deuue", e.getMessage());
                        }


                    }
                }, year, month, day);
                datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
                datePickerDialog.show();
            }
        });


        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), CartActivity.class));
                ProductDetailActivity.bottomSheet.dismiss();
            }
        });

        showTiming();
        return view;
    }


    public void showTiming() {
        TimeData timeData = new TimeData();
        for (int i = 0; i < 3; i++) {
            timeDataList.add(timeData);
        }
    }
}

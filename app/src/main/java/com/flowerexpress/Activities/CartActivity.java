package com.flowerexpress.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.flowerexpress.Adapter.CartAdapter;
import com.flowerexpress.Models.CartData;
import com.flowerexpress.R;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {

    RecyclerView flowerListRecycler;
    List<CartData> cartDataList = new ArrayList<>();
    CartAdapter adapter;
    ImageView back;
    Button btnPlaceOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        flowerListRecycler = findViewById(R.id.flowerListRecycler);
        back = findViewById(R.id.back);
        btnPlaceOrder = findViewById(R.id.btnPlaceOrder);


        flowerListRecycler.setHasFixedSize(true);
        flowerListRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        adapter = new CartAdapter(cartDataList, this);
        flowerListRecycler.setAdapter(adapter);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        showCart();
    }


    public void showCart() {
        CartData cartData = new CartData();
        for (int i = 0; i < 3; i++) {
            cartDataList.add(cartData);
        }
    }
}
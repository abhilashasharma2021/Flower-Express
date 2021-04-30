package com.flowerexpress.Activities;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.flowerexpress.Adapter.BunchAdapter;
import com.flowerexpress.Models.BunchData;
import com.flowerexpress.Models.ShopByData;
import com.flowerexpress.Models.SliderModel;
import com.flowerexpress.Others.API;
import com.flowerexpress.Others.AppConstant;
import com.flowerexpress.ProgressBar.CustomDialog;
import com.flowerexpress.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CategoryActivity extends AppCompatActivity {

    RecyclerView subCategoryRecycler;
    List<BunchData> bunchDataList = new ArrayList<>();
    ProgressBar progressBar;
    TextView txt_catName, txt_noProduct;
    String strCategoryId = "", strCategoryName = "", strShopStatus = "";
    String st_page="";
    RelativeLayout rl_previous,rl_next;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        AppConstant.sharedpreferences = getSharedPreferences(AppConstant.MYPREFERENCE, Context.MODE_PRIVATE);
        strCategoryId = AppConstant.sharedpreferences.getString(AppConstant.CategoryId, "");
        strCategoryName = AppConstant.sharedpreferences.getString(AppConstant.CategoryName, "");
        strCategoryName = AppConstant.sharedpreferences.getString(AppConstant.CategoryName, "");
        strShopStatus = AppConstant.sharedpreferences.getString(AppConstant.ShopStatus, "");

        Log.e("sfncnd", strCategoryId);
        Log.e("sfncnd", strShopStatus);

        subCategoryRecycler = findViewById(R.id.subCategoryRecycler);
        progressBar = findViewById(R.id.progressBar);
        txt_noProduct = findViewById(R.id.txt_noProduct);
        rl_next = findViewById(R.id.rl_next);
        rl_previous = findViewById(R.id.rl_previous);
        ImageView back = findViewById(R.id.back);
        txt_catName = findViewById(R.id.txt_catName);
        txt_catName.setText(strCategoryName);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });



        if (strShopStatus.equals("0")) {

            showFlowerBunchType(st_page );
        } else {

            showFlowerBunchOccasion();
        }


    }


    public void showFlowerBunchType(String st_page) {

        CustomDialog dialog=new CustomDialog();
        dialog.showDialog(R.layout.progress_layout,this);
        AndroidNetworking.post(API.BaseUrl)
                .addBodyParameter("control", "shop_by_type_product")
                .addBodyParameter("category_id", strCategoryId)
                .addBodyParameter("page",st_page)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("cfmkxzjckl", response.toString());
                        dialog.hideDialog();
                        bunchDataList = new ArrayList<>();
                        try {
                            if (response.has("result")) {
                                if (response.getString("result").equals("true")) {

                                    String previous_page = response.getString("previous_page");
                                    String current_page = response.getString("current_page");
                                    String next_page = response.getString("next_page");
                                    String total_page = response.getString("total_page");
                                    String data = response.getString("data");

                                    JSONArray jsonArray = new JSONArray(data);
                                    for (int i = 0; i < jsonArray.length(); i++) {

                                        JSONObject object = jsonArray.getJSONObject(i);
                                        String id = object.getString("id");
                                        String name_en = object.getString("name_en");
                                        String name_ar = object.getString("name_ar");
                                        String price = object.getString("price");
                                        String code = object.getString("code");
                                        String image = object.getString("image");
                                        String path = object.getString("path");

                                        Log.e("eferg", image);
                                        Log.e("eferg", id);
                                        Log.e("eferg", path);


                                        BunchData bunchData = new BunchData();
                                        bunchData.setId(object.getString("id"));
                                        bunchData.setName(object.getString("name_en"));
                                        bunchData.setPath(object.getString("path"));
                                        bunchData.setImage(object.getString("image"));
                                        bunchData.setPrice(object.getString("price"));
                                        bunchDataList.add(bunchData);

                                        rl_previous.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                showFlowerBunchType(previous_page);
                                            }
                                        });

                                        rl_next.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                showFlowerBunchType(next_page);
                                            }
                                        });
                                    }

                                }

                                if (response.getString("result").equals("false")) {
                                    txt_noProduct.setVisibility(View.VISIBLE);
                                    // Toast.makeText(CategoryActivity.this, "Here is no product available !!!!", Toast.LENGTH_SHORT).show();
                                    dialog.hideDialog();
                                } else {
                                    subCategoryRecycler.setHasFixedSize(true);
                                    subCategoryRecycler.setItemViewCacheSize(20);
                                    subCategoryRecycler.setLayoutManager(new GridLayoutManager(CategoryActivity.this, 2));
                                    subCategoryRecycler.setAdapter(new BunchAdapter(bunchDataList, CategoryActivity.this));


                                    LayoutAnimationController animationController = AnimationUtils.loadLayoutAnimation(getApplicationContext(), R.anim.layout_animation_slide_bottom);
                                    subCategoryRecycler.setLayoutAnimation(animationController);
                                    subCategoryRecycler.scheduleLayoutAnimation();
                                }

                            }
                        } catch (JSONException e) {
                            Log.e("regrtg", e.getMessage());
                            dialog.hideDialog();
                        }


                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("ergrth", anError.getMessage());
                        dialog.hideDialog();
                    }
                });

    }


    public void showFlowerBunchOccasion() {

        CustomDialog dialog=new CustomDialog();
        dialog.showDialog(R.layout.progress_layout,this);
        AndroidNetworking.post(API.BaseUrl)
                .addBodyParameter("control","shop_by_type_product")
                .addBodyParameter("occasion_id",strCategoryId)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("cfmkxzjckl",response.toString());
                        dialog.hideDialog();

                        bunchDataList=new ArrayList<>();
                        try {
                            if (response.has("result")) {
                                if (response.getString("result").equals("true")) {

                                    String data = response.getString("data");

                                    JSONArray jsonArray = new JSONArray(data);
                                    for (int i = 0; i < jsonArray.length(); i++) {

                                        JSONObject object = jsonArray.getJSONObject(i);
                                        String id = object.getString("id");
                                        String name_en = object.getString("name_en");
                                        String name_ar = object.getString("name_ar");
                                        String price = object.getString("price");
                                        String code = object.getString("code");
                                        String image = object.getString("image");
                                        String path = object.getString("path");

                                        Log.e("eferg", image);
                                        Log.e("eferg", id);
                                        Log.e("eferg", path);


                                        BunchData bunchData=new BunchData();
                                        bunchData.setId(object.getString("id"));
                                        bunchData.setName(object.getString("name_en"));
                                        bunchData.setPath(object.getString("path"));
                                        bunchData.setImage(object.getString("image"));
                                        bunchData.setPrice(object.getString("price"));
                                        bunchDataList.add(bunchData);

                                    }

                                }

                                if (response.getString("result").equals("false")) {
                                    txt_noProduct.setVisibility(View.VISIBLE);
                                    // Toast.makeText(CategoryActivity.this, "Here is no product available !!!!", Toast.LENGTH_SHORT).show();
                                    dialog.hideDialog();
                                }
                                else {
                                    subCategoryRecycler.setHasFixedSize(true);
                                    subCategoryRecycler.setItemViewCacheSize(20);
                                    subCategoryRecycler.setLayoutManager(new GridLayoutManager(CategoryActivity.this, 2));
                                    subCategoryRecycler.setAdapter(new BunchAdapter(bunchDataList, CategoryActivity.this));


                                    LayoutAnimationController animationController = AnimationUtils.loadLayoutAnimation(getApplicationContext(), R.anim.layout_animation_slide_bottom);
                                    subCategoryRecycler.setLayoutAnimation(animationController);
                                    subCategoryRecycler.scheduleLayoutAnimation();
                                }

                            }
                        }catch (JSONException e) {
                            Log.e("regrtg",e.getMessage());
                            dialog.hideDialog();
                        }



                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("ergrth",anError.getMessage());
                        dialog.hideDialog();
                    }
                });

    }
}
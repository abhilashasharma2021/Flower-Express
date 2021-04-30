package com.flowerexpress.Activities;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;
import android.widget.ProgressBar;
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
import com.flowerexpress.Adapter.SelectTypeAdapter;
import com.flowerexpress.Models.BunchData;
import com.flowerexpress.Models.SelectTypeData;
import com.flowerexpress.Others.API;
import com.flowerexpress.Others.AppConstant;
import com.flowerexpress.ProgressBar.CustomDialog;
import com.flowerexpress.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SelectByTypeActivity extends AppCompatActivity {

    RecyclerView selectTypeRecycler;
    String strSelectType="",strSelectTypeName="";
    ProgressBar progressBar;
    TextView txt_CatName;
    List<SelectTypeData> selectTypeDatalist = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_by_type);

        AppConstant.sharedpreferences=getSharedPreferences(AppConstant.MYPREFERENCE, Context.MODE_PRIVATE);
        strSelectType=AppConstant.sharedpreferences.getString(AppConstant.SelectType,"");
        strSelectTypeName=AppConstant.sharedpreferences.getString(AppConstant.SelectTypeName,"");

        selectTypeRecycler = findViewById(R.id.selectTypeRecycler);
        progressBar = findViewById(R.id.progressBar);
        txt_CatName = findViewById(R.id.txt_CatName);
        ImageView back = findViewById(R.id.back);
         txt_CatName.setText(strSelectTypeName);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        showbyType();
    }

    public void showbyType()
    {


        CustomDialog dialog=new CustomDialog();
        dialog.showDialog(R.layout.progress_layout,this);
        AndroidNetworking.post(API.BaseUrl)
                .addBodyParameter("control","all_cat")
                .addBodyParameter("type",strSelectType)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("seggggdxfhg",response.toString());
                        dialog.hideDialog();

                        selectTypeDatalist=new ArrayList<>();
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
                                        String image = object.getString("image");
                                        String path = object.getString("path");

                                        Log.e("eferg", image);
                                        Log.e("eferg", id);
                                        Log.e("eferg", path);


                                        SelectTypeData selectTypeData=new SelectTypeData();
                                        selectTypeData.setId(object.getString("id"));
                                        selectTypeData.setName(object.getString("name_en"));
                                        selectTypeData.setImage(object.getString("image"));
                                        selectTypeData.setPath(object.getString("path"));
                                        selectTypeDatalist.add(selectTypeData);

                                    }


                                        selectTypeRecycler.setHasFixedSize(true);
                                        selectTypeRecycler.setItemViewCacheSize(20);
                                        selectTypeRecycler.setLayoutManager(new GridLayoutManager(SelectByTypeActivity.this, 2));
                                        selectTypeRecycler.setAdapter(new SelectTypeAdapter(selectTypeDatalist, SelectByTypeActivity.this));

                                        LayoutAnimationController animationController = AnimationUtils.loadLayoutAnimation(getApplicationContext(), R.anim.layout_animation_fall_down);
                                        selectTypeRecycler.setLayoutAnimation(animationController);
                                        selectTypeRecycler.scheduleLayoutAnimation();

                                }
                            }
                        }catch (JSONException e) {
                            Log.e("tgrfjhntf",e.getMessage());
                            dialog.hideDialog();
                        }



                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("trjfr",anError.getMessage());
                        dialog.hideDialog();
                    }
                });

    }

    }

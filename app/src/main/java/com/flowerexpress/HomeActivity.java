package com.flowerexpress;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.flowerexpress.Activities.SelectByTypeActivity;
import com.flowerexpress.Adapter.CollaborationAdapter;
import com.flowerexpress.Adapter.OccasionAdapter;
import com.flowerexpress.Adapter.SubcriptionAdapter;
import com.flowerexpress.Adapter.ProductAdapter;
import com.flowerexpress.Adapter.ShopByAdapter;
import com.flowerexpress.Adapter.SliderAdapter;
import com.flowerexpress.Adapter.WhatsNewAdapter;
import com.flowerexpress.Models.CollaborationData;
import com.flowerexpress.Models.OccasionByData;
import com.flowerexpress.Models.SubscriptionData;
import com.flowerexpress.Models.ProductData;
import com.flowerexpress.Models.ShopByData;
import com.flowerexpress.Models.SliderModel;
import com.flowerexpress.Models.WhatsNewData;
import com.flowerexpress.Others.API;
import com.flowerexpress.Others.AppConstant;
import com.flowerexpress.Others.BottomSheet;
import com.flowerexpress.ProgressBar.CustomDialog;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    RecyclerView collaborationrecycler, productRecycler, whatsNewRecycler, shopByrecycler, subcriptionRecycler,occationrecycler;
    List<SubscriptionData> subscriptionDataList = new ArrayList<>();
    List<CollaborationData> collaborationDataList = new ArrayList<>();
    List<ProductData> productDataList = new ArrayList<>();
    List<WhatsNewData> whatsNewDataList = new ArrayList<>();
    List<ShopByData> shopByDataList = new ArrayList<>();
    List<OccasionByData> occasionByDataList = new ArrayList<>();
    ProgressBar progressBar;
    OccasionAdapter occasionAdapter;
    RelativeLayout relFlag,rl_shop,rl_collb,rl_designer,rl_bestSeller,rl_occation;
    TextView txt_shop;

    SliderAdapter sliderAdapter;
    List<SliderModel> listOfSlider = new ArrayList<>();
    SliderView sliderView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        collaborationrecycler = findViewById(R.id.collaborationrecycler);
        progressBar = findViewById(R.id.progressBar);
        rl_collb = findViewById(R.id.rl_collb);
        occationrecycler = findViewById(R.id.occationrecycler);
        rl_bestSeller = findViewById(R.id.rl_bestSeller);
        txt_shop = findViewById(R.id.txt_shop);
        rl_shop = findViewById(R.id.rl_shop);
        rl_occation = findViewById(R.id.rl_occation);
        productRecycler = findViewById(R.id.productRecycler);
        whatsNewRecycler = findViewById(R.id.whatsNewRecycler);
        sliderView = findViewById(R.id.sliderView);
        shopByrecycler = findViewById(R.id.shopByrecycler);
        relFlag = findViewById(R.id.relFlag);
        subcriptionRecycler = findViewById(R.id.subcriptionRecycler);
        rl_designer = findViewById(R.id.rl_designer);

        rl_shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppConstant.sharedpreferences =getSharedPreferences(AppConstant.MYPREFERENCE, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = AppConstant.sharedpreferences.edit();
                editor.putString(AppConstant.SelectType,"1");
                editor.putString(AppConstant.SelectTypeName,"Shop By Type");
                editor.putString(AppConstant.StatusHome,"1");
                editor.commit();
                startActivity(new Intent(HomeActivity.this, SelectByTypeActivity.class));
            }
        });
        rl_collb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppConstant.sharedpreferences =getSharedPreferences(AppConstant.MYPREFERENCE, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = AppConstant.sharedpreferences.edit();
                editor.putString(AppConstant.SelectType,"2");
                editor.putString(AppConstant.SelectTypeName,"Whole Sale ");
                editor.commit();
                startActivity(new Intent(HomeActivity.this, SelectByTypeActivity.class));
            }
        });

        rl_occation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppConstant.sharedpreferences =getSharedPreferences(AppConstant.MYPREFERENCE, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = AppConstant.sharedpreferences.edit();
                editor.putString(AppConstant.SelectType,"5");
                editor.putString(AppConstant.StatusHome,"1");
                editor.putString(AppConstant.SelectTypeName,"Shop by Occasion");
                editor.commit();
                startActivity(new Intent(HomeActivity.this, SelectByTypeActivity.class));
            }
        });
        rl_designer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppConstant.sharedpreferences =getSharedPreferences(AppConstant.MYPREFERENCE, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = AppConstant.sharedpreferences.edit();
                editor.putString(AppConstant.SelectType,"3");
                editor.putString(AppConstant.SelectTypeName,"Designers");
                editor.commit();
                startActivity(new Intent(HomeActivity.this, SelectByTypeActivity.class));
            }
        });

        rl_bestSeller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppConstant.sharedpreferences =getSharedPreferences(AppConstant.MYPREFERENCE, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = AppConstant.sharedpreferences.edit();
                editor.putString(AppConstant.SelectType,"4");
                editor.putString(AppConstant.SelectTypeName,"Best Sellers");
                editor.putString(AppConstant.StatusHome,"0");
                editor.commit();
                startActivity(new Intent(HomeActivity.this, SelectByTypeActivity.class));
            }
        });


        sliderView.setIndicatorAnimation(IndicatorAnimations.WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.CUBEOUTROTATIONTRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderView.setIndicatorSelectedColor(Color.DKGRAY);
        sliderView.setIndicatorUnselectedColor(Color.LTGRAY);
        sliderView.setScrollTimeInSec(4); //set scroll delay in seconds :
        sliderView.startAutoCycle();




        relFlag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheet bottomSheet = new BottomSheet();
                bottomSheet.show(((FragmentActivity) view.getContext()).getSupportFragmentManager(), "flag");
            }
        });

       /* subcriptionRecycler.setLayoutManager(new LinearLayoutManager(HomeActivity.this, LinearLayoutManager.HORIZONTAL, false));
        subcriptionRecycler.setItemViewCacheSize(20);
        subcriptionRecycler.setHasFixedSize(true);
        subcriptionRecycler.setAdapter(new SubcriptionAdapter(subscriptionDataList, HomeActivity.this));*/
        showCollaboration();
        showNewSeller();
        showShopBy();
        showProduct();
        showOccasionBy();
        showSubscription();
        show_banner();
    }


    public void showCollaboration() {

      AndroidNetworking.post(API.BaseUrl)
              .addBodyParameter("control","component")
              .setPriority(Priority.HIGH)
              .build()
              .getAsJSONObject(new JSONObjectRequestListener() {
                  @Override
                  public void onResponse(JSONObject response) {
                      Log.e("djsfkjk",response.toString());
                      collaborationDataList=new ArrayList<>();
                      try {
                          if (response.getString("result").equals("true"));
                          String data=response.getString("data");
                          JSONArray jsonArray=new JSONArray(data);
                          for (int i=0;i<3;i++){

                              JSONObject jsonObject=jsonArray.getJSONObject(i);

                              String id=jsonObject.getString("id");
                              String name_en=jsonObject.getString("name_en");
                              String name_ar=jsonObject.getString("name_ar");
                              String price=jsonObject.getString("price");
                              String image=jsonObject.getString("image");
                              String path=jsonObject.getString("path");



                                  CollaborationData collaborationData=new CollaborationData();

                                  collaborationData.setId(jsonObject.getString("id"));
                                  collaborationData.setName(jsonObject.getString("name_en"));
                                  collaborationData.setImage(jsonObject.getString("image"));
                                  collaborationData.setPath(jsonObject.getString("path"));

                              Log.e("xzvckxjzk", path+image);

                                  collaborationDataList.add(collaborationData);


                          }

                          collaborationrecycler.setLayoutManager(new LinearLayoutManager(HomeActivity.this, LinearLayoutManager.HORIZONTAL, false));
                          collaborationrecycler.setItemViewCacheSize(20);
                          collaborationrecycler.setHasFixedSize(true);
                          collaborationrecycler.setAdapter(new CollaborationAdapter(collaborationDataList, HomeActivity.this));



                      } catch (JSONException e) {
                         Log.e("sdjfcdk",e.getMessage());

                      }
                  }

                  @Override
                  public void onError(ANError anError) {
                      Log.e("regr",anError.getMessage());

                  }
              });

    }


    public void showProduct() {

        AndroidNetworking.post(API.BaseUrl)
                .addBodyParameter("control","home_products")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("ryhftgjh",response.toString());
                        productDataList =new ArrayList<>();

                        try {
                            if (response.getString("result").equals("true"));
                            String data=response.getString("data");
                            JSONArray jsonArray=new JSONArray(data);
                            for (int i=0;i<3;i++){

                                JSONObject jsonObject=jsonArray.getJSONObject(i);

                                String id=jsonObject.getString("id");
                                String name_en=jsonObject.getString("name_en");
                                String name_ar=jsonObject.getString("name_ar");
                                String image=jsonObject.getString("image");
                                String path=jsonObject.getString("path");
                                String status=jsonObject.getString("status");

                              Log.e("sfkdlkvkv",path+image);

                                ProductData productData =new ProductData();

                                productData.setId(jsonObject.getString("id"));
                                productData.setName(jsonObject.getString("name_en"));
                                productData.setImage(jsonObject.getString("image"));
                                productData.setPath(jsonObject.getString("path"));

                                productDataList.add(productData);


                            }


                            productRecycler.setLayoutManager(new LinearLayoutManager(HomeActivity.this, LinearLayoutManager.HORIZONTAL, false));
                            productRecycler.setItemViewCacheSize(20);
                            productRecycler.setHasFixedSize(true);
                            productRecycler.setAdapter(new ProductAdapter(productDataList, HomeActivity.this));


                        } catch (JSONException e) {
                            Log.e("ryhtdh",e.getMessage());

                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("truyt",anError.getMessage());

                    }
                });


    }

    public void showNewSeller() {

        AndroidNetworking.post(API.BaseUrl)
                .addBodyParameter("control","bestseller")
                .setTag("showing bestseller Product")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("reyhrthy",response.toString());
                        whatsNewDataList=new ArrayList<>();
                        try {
                            if (response.getString("result").equals("true"));

                            String data = response.getString("data");
                            JSONArray jsonArray = new JSONArray(data);

                            for (int i = 0; i < 3; i++) {

                                JSONObject object = jsonArray.getJSONObject(i);
                                String id = object.getString("id");
                                String name_en = object.getString("name_en");
                                String name_ar = object.getString("name_ar");
                                String image = object.getString("image");
                                String path = object.getString("path");
                                String variation = object.getString("variation");

                                JSONArray jsonArray1=new JSONArray(variation);
                                for (int j=0;j<1;j++){

                                    JSONObject object1=jsonArray1.getJSONObject(j);
                                    String mass=object1.getString("mass");
                                    String price=object1.getString("price");

                                    WhatsNewData newseller=new WhatsNewData();
                                    newseller.setId(object.getString("id"));
                                    newseller.setName(object.getString("name_en"));
                                    newseller.setImage(object.getString("image"));
                                    newseller.setPath(object.getString("path"));
                                    newseller.setVariationMin(object1.getString("price"));
                                    newseller.setVariationMax(object1.getString("price"));
                                    whatsNewDataList.add(newseller);
                                }


                            }
                            whatsNewRecycler.setLayoutManager(new LinearLayoutManager(HomeActivity.this, LinearLayoutManager.HORIZONTAL, false));
                            whatsNewRecycler.setItemViewCacheSize(20);
                            whatsNewRecycler.setHasFixedSize(true);
                            whatsNewRecycler.setAdapter(new WhatsNewAdapter(whatsNewDataList, HomeActivity.this));



                        } catch (JSONException e) {
                            Log.e("rtfghtrf",e.getMessage());

                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("y6jytj",anError.getMessage());

                    }
                });

    }


    public void showShopBy() {


       CustomDialog dialog=new CustomDialog();
       dialog.showDialog(R.layout.progress_layout,this);

        AndroidNetworking.post(API.BaseUrl)
                .addBodyParameter("control","shopbytype")
                 .setTag("showing category")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("sgdfg",response.toString());
                        shopByDataList=new ArrayList<>();
                        dialog.hideDialog();
                        try {
                            if (response.getString("result").equals("true")) {

                                String data = response.getString("data");
                                JSONArray jsonArray = new JSONArray(data);
                                for (int i = 0; i < 3; i++) {

                                    JSONObject object = jsonArray.getJSONObject(i);
                                    String id = object.getString("id");
                                    String name_en = object.getString("name_en");
                                    String name_ar = object.getString("name_ar");
                                    String image = object.getString("image");
                                    String path = object.getString("path");

                                    Log.e("eryg",image);
                                    Log.e("eryg",path);

                                    ShopByData shopByData=new ShopByData();
                                    shopByData.setId(object.getString("id"));
                                    shopByData.setName(object.getString("name_en"));
                                    shopByData.setImage(object.getString("image"));
                                    shopByData.setPath(object.getString("path"));
                                    shopByDataList.add(shopByData);
                                }

                                shopByrecycler.setLayoutManager(new LinearLayoutManager(HomeActivity.this, LinearLayoutManager.HORIZONTAL, false));
                                shopByrecycler.setItemViewCacheSize(20);
                                shopByrecycler.setHasFixedSize(true);
                                shopByrecycler.setAdapter(new ShopByAdapter(shopByDataList, HomeActivity.this));
                            }
                        } catch (JSONException e) {
                            Log.e("reyhtr",e.getMessage());
                            dialog.hideDialog();

                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("sgfdgf",anError.getMessage());
                        dialog.hideDialog();

                    }
                });

    }



    public void showOccasionBy() {

        AndroidNetworking.post(API.BaseUrl)
                .addBodyParameter("control", "shop_ocasion")
                .setTag("showing Occasion")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("rehrtrhjnb", response.toString());
                        occasionByDataList = new ArrayList<>();

                        try {
                            if (response.getString("result").equals("true")) {

                                String data = response.getString("data");
                                JSONArray jsonArray = new JSONArray(data);
                                for (int i = 0; i < 3; i++) {

                                    JSONObject object = jsonArray.getJSONObject(i);
                                    String id = object.getString("id");
                                    String name_en = object.getString("name_en");
                                    String name_ar = object.getString("name_ar");
                                    String image = object.getString("image");
                                    String path = object.getString("path");

                                    Log.e("eryg", image);
                                    Log.e("eryg", path);

                                    OccasionByData occasionByData = new OccasionByData();
                                    occasionByData.setId(object.getString("id"));
                                    occasionByData.setName(object.getString("name_en"));
                                    occasionByData.setImage(object.getString("image"));
                                    occasionByData.setPath(object.getString("path"));
                                    occasionByDataList.add(occasionByData);
                                }

                                occationrecycler.setLayoutManager(new LinearLayoutManager(HomeActivity.this, LinearLayoutManager.HORIZONTAL, false));
                                occationrecycler.setItemViewCacheSize(20);
                                occationrecycler.setHasFixedSize(true);
                                occationrecycler.setAdapter(new OccasionAdapter(occasionByDataList, HomeActivity.this));
                            }
                        } catch (JSONException e) {
                            Log.e("sdgdfg", e.getMessage());


                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("dfhbfb", anError.getMessage());


                    }
                });
    }



        public void showSubscription() {

        AndroidNetworking.post(API.BaseUrl)
                .addBodyParameter("control","subscription_product")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("fregth",response.toString());
                        subscriptionDataList=new ArrayList<>();

                        try {
                            if (response.getString("result").equals("true")){

                                String data = response.getString("data");
                                JSONArray jsonArray = new JSONArray(data);
                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject object = jsonArray.getJSONObject(i);
                                    String id = object.getString("id");
                                    String name_en = object.getString("name_en");
                                    String name_ar = object.getString("name_ar");
                                    String image = object.getString("image");
                                    String price = object.getString("price");
                                    String code = object.getString("code");
                                    String path = object.getString("path");

                                    Log.e("eryg",image);
                                    Log.e("eryg",path);

                                    SubscriptionData subscriptionData=new SubscriptionData();
                                    subscriptionData.setId(object.getString("id"));
                                    subscriptionData.setName(object.getString("name_en"));
                                    subscriptionData.setImage(object.getString("image"));
                                    subscriptionData.setPath(object.getString("path"));
                                    subscriptionData.setCode(object.getString("code"));
                                    subscriptionDataList.add(subscriptionData);

                                }

                                subcriptionRecycler.setLayoutManager(new LinearLayoutManager(HomeActivity.this, LinearLayoutManager.HORIZONTAL, false));
                                subcriptionRecycler.setItemViewCacheSize(20);
                                subcriptionRecycler.setHasFixedSize(true);
                                subcriptionRecycler.setAdapter(new SubcriptionAdapter(subscriptionDataList, HomeActivity.this));

                            }
                        } catch (JSONException e) {
                            Log.e("eyt",e.getMessage());


                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("regregrg",anError.getMessage());

                    }
                });
    }


    public void show_banner() {


        AndroidNetworking.post(API.BaseUrl)
                .addBodyParameter("control", "show_banners")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("reyhtrfh",response.toString());
                        listOfSlider=new ArrayList<>();
                        try {
                            if (response.getString("result").equals("true")) {
                                String data = response.getString("data");
                                JSONArray jsonArray = new JSONArray(data);
                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject object = jsonArray.getJSONObject(i);
                                    String id = object.getString("id");
                                    String image = object.getString("image");
                                    String path = object.getString("path");

                                    Log.e("sdlfjdk",image);
                                    Log.e("sdlfjdk",path);

                                    SliderModel sliderModel = new SliderModel();

                                    sliderModel.setImage(object.getString("image"));
                                    sliderModel.setPath(object.getString("path"));

                                    listOfSlider.add(sliderModel);

                                }

                                 sliderAdapter = new SliderAdapter(listOfSlider,HomeActivity.this);
                                sliderView.setSliderAdapter(sliderAdapter);
                            }
                            else {
                                Toast.makeText(HomeActivity.this, response.getString("result"), Toast.LENGTH_SHORT).show();

                            }
                        } catch (JSONException e) {
                            Log.e("dsjkfdj",e.getMessage());

                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("tryhtuy",anError.getMessage());


                    }
                });

    }
}
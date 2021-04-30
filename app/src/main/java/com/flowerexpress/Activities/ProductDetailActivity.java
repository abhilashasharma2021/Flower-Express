package com.flowerexpress.Activities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.flowerexpress.Adapter.SliderAdapter;
import com.flowerexpress.Models.SliderModel;
import com.flowerexpress.Others.AppConstant;
import com.flowerexpress.Others.BottomSheetDateTime;
import com.flowerexpress.ProgressBar.CustomDialog;
import com.flowerexpress.R;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ProductDetailActivity extends AppCompatActivity {
    SliderView sliderView;
    Button btnAddtoCart;
    ImageView back;
    TextView txt_price,txt_name,txt_description;
    public static BottomSheetDateTime bottomSheet;
    String st_productId="";
    ArrayList<SliderModel>sliderArray=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        AppConstant.sharedpreferences = getSharedPreferences(AppConstant.MYPREFERENCE, MODE_PRIVATE);
        st_productId = AppConstant.sharedpreferences.getString(AppConstant.ProductId, "");
         Log.e("dcxlkkcl",st_productId);


        sliderView = findViewById(R.id.sliderView);
        btnAddtoCart = findViewById(R.id.btnAddtoCart);
        back = findViewById(R.id.back);
        txt_price = findViewById(R.id.txt_price);
        txt_name = findViewById(R.id.txt_name);
        txt_description = findViewById(R.id.txt_description);

        sliderView.setIndicatorAnimation(IndicatorAnimations.WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderView.setIndicatorSelectedColor(Color.DKGRAY);
        sliderView.setIndicatorUnselectedColor(Color.LTGRAY);
        sliderView.setScrollTimeInSec(4); //set scroll delay in seconds :
        sliderView.startAutoCycle();




        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        btnAddtoCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  startActivity(new Intent(getApplicationContext(),CartActivity.class));
                bottomSheet = new BottomSheetDateTime();
                bottomSheet.show(((FragmentActivity) view.getContext()).getSupportFragmentManager(), "dateTime");
            }
        });

        show_product_banner();
        show_detail();
    }



    public void show_detail(){
        CustomDialog dialog=new CustomDialog();
        dialog.showDialog(R.layout.progress_layout,this);


        AndroidNetworking.post("https://flowersoutletkw.com/appservice/process.php")
                .addBodyParameter("control","product_details")
                .addBodyParameter("product_id",st_productId)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("xlkclxk",response.toString());
                        dialog.hideDialog();

                        try {
                              if (response.has("result")) {
                                  if (response.getString("result").equals("true")) {

                                      String data=response.getString("data");

                                      JSONArray jsonArray=new JSONArray(data);

                                      for (int i = 0; i <jsonArray.length() ; i++) {
                                       JSONObject object=jsonArray.getJSONObject(i);

                                       String id=object.getString("id");
                                       String name_en=object.getString("name_en");
                                       String name_ar=object.getString("name_ar");
                                       String price=object.getString("price");
                                       String description_en=object.getString("description_en");
                                       String description_ar=object.getString("description_ar");
                                       String path=object.getString("path");

                                          txt_description.setText(description_en);
                                          txt_name.setText(name_en);
                                          txt_price.setText("BHD"+price);
                                      }


                                  }
                              }
                          }catch (JSONException e) {
                            Log.e("sjdsckl",e.getMessage());
                            dialog.hideDialog();
                          }
                      }



                    @Override
                    public void onError(ANError anError) {
                        Log.e("rehgfdg",anError.getMessage());
                        dialog.hideDialog();
                    }
                });




    }


    public void show_product_banner(){

        AndroidNetworking.post("https://flowersoutletkw.com/appservice/process.php")
                .addBodyParameter("control","product_details")
                .addBodyParameter("product_id",st_productId)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                      Log.e("xzkjck",response.toString());
                        sliderArray=new ArrayList<>();
                        try {
                            if (response.has("result")) {
                                if (response.getString("result").equals("true")) {

                                    String data=response.getString("data");

                                    JSONArray jsonArray=new JSONArray(data);

                                    for (int i = 0; i <jsonArray.length() ; i++) {
                                        JSONObject object=jsonArray.getJSONObject(i);

                                            String path=object.getString("path");
                                            String images=object.getString("images");

                                            JSONArray array=new JSONArray(images);
                                        for (int j = 0; j <array.length() ; j++) {

                                            JSONObject obj=array.getJSONObject(j);
                                            String  product_id=obj.getString("product_id");
                                            String  image=obj.getString("image");
                                            String  path_new=obj.getString("path");


                                            Log.e("slkjfckj",path+image);

                                            SliderModel sliderModel=new SliderModel();
                                            sliderModel.setImage(obj.getString("image"));
                                            sliderModel.setPath(obj.getString("path"));

                                           sliderArray.add(sliderModel);


                                        }


                                    }

                              SliderAdapter sliderAdapter = new SliderAdapter(sliderArray,ProductDetailActivity.this);
                                 sliderView.setSliderAdapter(sliderAdapter);
                                }
                            }
                        }catch (JSONException e) {
                            Log.e("sjdsckl",e.getMessage());

                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("ewgdds",anError.getMessage());
                    }
                });
    }



}
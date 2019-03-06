
package com.example.smatech.ay5edma;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.example.smatech.ay5edma.Dialoge.ContactOptionDialoge;
import com.example.smatech.ay5edma.Models.Modelss.OffersModel;
import com.example.smatech.ay5edma.Models.Modelss.StatusModel;
import com.example.smatech.ay5edma.Utils.Connectors;
import com.example.smatech.ay5edma.Utils.Constants;
import com.example.smatech.ay5edma.Utils.CustomSliderView;
import com.google.gson.Gson;
import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceProviderDescription extends AppCompatActivity {

    //Details of service provider

    Button Accept;
    LinearLayout Reveiws;
    OffersModel offersModel;
    TextView name, gender, Occupaion, Birthday, Location, About;
    me.zhanghai.android.materialratingbar.MaterialRatingBar Stars;
    View parentLayout;
    ProgressDialog progressDialog;
    String T;
    PagerIndicator pagerIndicator;
    HashMap<String, String> url_maps;
    private com.daimajia.slider.library.SliderLayout mDemoSlider;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_provider_description);
        parentLayout = findViewById(android.R.id.content);
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.Loading));
        mDemoSlider = (SliderLayout) findViewById(R.id.slider);
        pagerIndicator =  findViewById(R.id.custom_indicator);
        url_maps = new HashMap<String, String>();
        get_home_sliders();
        ImageView back;
        TextView toolbar_title;
        toolbar_title = findViewById(R.id.toolbar_title);
        toolbar_title.setText(this.getString(R.string.Service_Provider_Details) + "");
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        offersModel = Hawk.get(Constants.mOfferModel);
        ///
        // bottom nav
        LinearLayout homeLayout,requestLayout,notificationLayout,settingLayout ;
        homeLayout = findViewById(R.id.homeLayout);
        homeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        requestLayout = findViewById(R.id.requestLayout);
        requestLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(ServiceProviderDescription.this,RequestsActivity.class));
            }
        });
        notificationLayout = findViewById(R.id.notificationLayout);
        notificationLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(ServiceProviderDescription.this,NotificationsActivity.class));
            }
        });
        settingLayout = findViewById(R.id.settingLayout);
        settingLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(ServiceProviderDescription.this,SettingActiviy.class));

            }
        });
        //
        name = findViewById(R.id.name);
        gender = findViewById(R.id.gender);
        Occupaion = findViewById(R.id.Occupaion);
        Birthday = findViewById(R.id.Birthday);
        Location = findViewById(R.id.Location);
        About = findViewById(R.id.About);
        //
        name.setText(offersModel.getFrom().getName());
        if (offersModel.getFrom().getGender().equals("1")) {
            gender.setText(getString(R.string.male));
        } else {
            gender.setText(getString(R.string.female));

        }
        if(Hawk.get(Constants.Language).equals("ar")){
            Occupaion.setText(offersModel.getFrom().getSubcategory().getNameAr() );

        }else {
            Occupaion.setText(offersModel.getFrom().getSubcategory().getName() );

        }
        Birthday.setText(offersModel.getFrom().getBirthday() + "");
        Location.setText(offersModel.getFrom().getAddress() + " Location ");
        About.setText(offersModel.getFrom().getAbout());
        ///
        Accept = findViewById(R.id.Accept);
        Accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                acceptOffer(offersModel.getUserId(),offersModel.getShopId(),offersModel.getRequestId(),"1");
                progressDialog.show();
            }
        });

        //
        Reveiws = findViewById(R.id.Reveiws);
        Reveiws.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ServiceProviderDescription.this, ClientReviewsActivity.class);
                startActivity(intent);
            }
        });

    }

    private void acceptOffer(String user_id, String shop_id, String request_id, String status) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Connectors.connectionServices.BaseURL)
                .addConverterFactory(GsonConverterFactory
                        .create(new Gson())).build();
        Connectors.connectionServices connectionService =
                retrofit.create(Connectors.connectionServices.class);

        connectionService.update_request_status(user_id+"", ""+shop_id, ""+request_id, ""+status)
                .enqueue(new Callback<StatusModel>() {
            @Override
            public void onResponse(Call<StatusModel> call, Response<StatusModel> response) {
                progressDialog.dismiss();
                StatusModel statusModel = response.body();
                if (statusModel.getStatus()) {
                    Log.d("TTTT", "onResponse:true ");

                    if (Hawk.get(Constants.Language).equals("ar")) {
                        Snackbar.make(parentLayout, "" + statusModel.getMessage_ar(), Snackbar.LENGTH_LONG)
                                .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                                .show();
                    } else {
                        Snackbar.make(parentLayout, "" + statusModel.getMessage(), Snackbar.LENGTH_LONG)
                                .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                                .show();
                    }
                    ContactOptionDialoge cdd = new ContactOptionDialoge(ServiceProviderDescription.this,offersModel.getFrom().getMobile());
                    cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    cdd.show();

                }else {
                    Log.d("TTTT", "onResponse:false ");
                }
            }

            @Override
            public void onFailure(Call<StatusModel> call, Throwable t) {
                progressDialog.dismiss();
                Log.d("TTTT", "onFailure:  vvv "+t.getMessage());

            }
        });
    }

    private void get_home_sliders() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Connectors.connectionServices.BaseURL)
                .addConverterFactory(GsonConverterFactory
                        .create(new Gson())).build();
        Connectors.connectionServices getRegistrationsConnectionServices =
                retrofit.create(Connectors.connectionServices.class);
        getRegistrationsConnectionServices.get_Sliders().enqueue(new Callback<StatusModel>() {
            @Override
            public void onResponse(Call<StatusModel> call, Response<StatusModel> response) {

                StatusModel statusModel = response.body();
                if (statusModel.getStatus()) {

                    ArrayList<String> Title = new ArrayList<>();
                    for (int i = 0; i < statusModel.getSliders().size(); i++) {
                        T = statusModel.getSliders().get(i).getId();
                       /* if (Title.contains(T)) {
                            Title.add(T+i);
                            T+=i;
                            }else{
                            Title.add(T);

                        }*/

                        url_maps.put("http://www.anyservice-ksa.com/prod_img/" + statusModel.getSliders().get(i).getName(), T);

                    }
                    for (String name : url_maps.keySet()) {
                        CustomSliderView textSliderView = new CustomSliderView(ServiceProviderDescription.this);
                        // initialize a SliderLayout
                        textSliderView
                                .description(url_maps.get(name))
                                .image(name)
                                .setScaleType(BaseSliderView.ScaleType.Fit);
                        //add your extra information
                        textSliderView.bundle(new Bundle());
                        textSliderView.getBundle()
                                .putString("extra", name);

                        mDemoSlider.addSlider(textSliderView);
                    }
                    mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Default);
                    mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
                    mDemoSlider.setIndicatorVisibility(PagerIndicator.IndicatorVisibility.Visible);
                    mDemoSlider.setCustomAnimation(new DescriptionAnimation());
                    mDemoSlider.setDuration(3000);
                    mDemoSlider.setCustomIndicator(pagerIndicator);




                    //setSliderViews(IMGs, Descs);

                }


            }

            @Override
            public void onFailure(Call<StatusModel> call, Throwable t) {

            }
        });

    }

}

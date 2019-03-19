package com.example.smatech.ay5edma;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.example.smatech.ay5edma.Models.offerModel.example.Offer;
import com.example.smatech.ay5edma.Utils.Constants;
import com.example.smatech.ay5edma.Utils.CustomSliderView;
import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;
import java.util.HashMap;

public class SliderAcivity extends AppCompatActivity {
    HashMap<String, String> url_maps;
    PagerIndicator pagerIndicator;
    Offer offersModel;

    private com.daimajia.slider.library.SliderLayout mDemoSlider;
    String T;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slider_acivity);

        offersModel = Hawk.get(Constants.mOfferModel);

        mDemoSlider = (SliderLayout) findViewById(R.id.slider);
        mDemoSlider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        pagerIndicator = findViewById(R.id.custom_indicator);
        url_maps = new HashMap<String, String>();
        get_home_sliders();
    }
    private void get_home_sliders() {
        Log.d("TTT", "get_home_sliders: " + offersModel.getFrom().getName());
        if (offersModel.getFrom().getImages() != null && offersModel.getFrom().getImages().size() > 0) {
            ArrayList<String> Title = new ArrayList<>();
            for (int i = 0; i < offersModel.getFrom().getImages().size(); i++) {
                T = i + "";
                url_maps.put("http://www.anyservice-ksa.com/prod_img/" + offersModel.getFrom().getImages().get(i), T);

            }
            Log.d("TTT", "get_home_sliders: " + offersModel.getFrom().getImages().size());
            for (String name : url_maps.keySet()) {
                CustomSliderView textSliderView = new CustomSliderView(SliderAcivity.this);
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

        } else {

        }
    }

}

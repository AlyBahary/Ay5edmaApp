package com.example.smatech.ay5edma;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.smatech.ay5edma.Adapters.CatgryAdapter;
import com.example.smatech.ay5edma.Adapters.OffersAdapter;
import com.example.smatech.ay5edma.Models.DummyModel;
import com.example.smatech.ay5edma.Models.Modelss.OffersModel;
import com.example.smatech.ay5edma.Models.Modelss.StatusModel;
import com.example.smatech.ay5edma.Models.OffersDummyModel;
import com.example.smatech.ay5edma.Utils.Connectors;
import com.example.smatech.ay5edma.Utils.Constants;
import com.google.gson.Gson;
import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OffersAcivity extends AppCompatActivity {
    RecyclerView RV;
    ArrayList<OffersModel> DM;
    OffersAdapter offersAdapter;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offers_acivity);
        ImageView back;
        TextView toolbar_title;
        toolbar_title=findViewById(R.id.toolbar_title);
        toolbar_title.setText(getString(R.string.Offers));
        back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.Loading));

        // bottom nav
        LinearLayout homeLayout, requestLayout, notificationLayout, settingLayout;
        homeLayout = findViewById(R.id.homeLayout);
        homeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //finish();
            }
        });
        requestLayout = findViewById(R.id.requestLayout);
        requestLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        notificationLayout = findViewById(R.id.notificationLayout);
        notificationLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(OffersAcivity.this, NotificationsActivity.class));
            }
        });
        settingLayout = findViewById(R.id.settingLayout);
        settingLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(OffersAcivity.this, SettingActiviy.class));

            }
        });
        //

        DM=new ArrayList<>();

        RV=findViewById(R.id.RV);



  offersAdapter = new OffersAdapter(DM, this, new OffersAdapter.OnItemClick() {
      @Override
      public void setOnItemClick(int position) {
          if(Hawk.get(Constants.UserType).equals("0")) {
              Intent intent = new Intent(OffersAcivity.this, ServiceProviderDescription.class);
              Hawk.put(Constants.mOfferModel,DM.get(position));
              startActivity(intent);
          }else{
              Intent intent = new Intent(OffersAcivity.this, ClientRequestDetailsActivity.class);
              startActivity(intent);
          }
      }
  });
        RV.setAdapter(offersAdapter);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        RV.setLayoutManager(mLayoutManager);
        offersAdapter.notifyDataSetChanged();


        getOffers(Hawk.get(Constants.mRequestID)+"","");

    }
    private void getOffers(String requestId,String id){
        progressDialog.show();
        Log.d("TTT", "getOffers: Called"+requestId);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Connectors.connectionServices.BaseURL)
                .addConverterFactory(GsonConverterFactory
                        .create(new Gson())).build();
        Connectors.connectionServices connectionService =
                retrofit.create(Connectors.connectionServices.class);

        connectionService.get_offers(requestId).enqueue(new Callback<StatusModel>() {
            @Override
            public void onResponse(Call<StatusModel> call, Response<StatusModel> response) {
                progressDialog.dismiss();
                StatusModel statusModel=response.body();
                Log.d("TTTT", "id : "+requestId+statusModel.getStatus());

                if (statusModel.getStatus()){
                    Log.d("TTTT", "id : "+requestId+"--"+"onResponse: "+statusModel.getOffers().size());
                    DM.clear();
                    DM.addAll(statusModel.getOffers());
                    offersAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onFailure(Call<StatusModel> call, Throwable t) {
                progressDialog.dismiss();
                Log.d("TTT", "onFailure: "+t.getMessage());

            }
        });
    }
}

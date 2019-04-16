package com.example.smatech.ay5edma;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smatech.ay5edma.Adapters.CatgryAdapter;
import com.example.smatech.ay5edma.Adapters.OffersAdapter;
import com.example.smatech.ay5edma.Models.DummyModel;
import com.example.smatech.ay5edma.Models.Modelss.OffersModel;
import com.example.smatech.ay5edma.Models.Modelss.StatusModel;
import com.example.smatech.ay5edma.Models.Modelss.UserModel;
import com.example.smatech.ay5edma.Models.OffersDummyModel;
import com.example.smatech.ay5edma.Models.offerModel.example.Example;
import com.example.smatech.ay5edma.Models.offerModel.example.Offer;
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
    ArrayList<Offer> DM;
    OffersAdapter offersAdapter;
    ProgressDialog progressDialog;
    private View parentLayout;
    Button Delete;
    String userID;
    UserModel userModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offers_acivity);
        parentLayout = findViewById(android.R.id.content);

        ImageView back;
        TextView toolbar_title;
        toolbar_title = findViewById(R.id.toolbar_title);
        toolbar_title.setText(getString(R.string.Offers));
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        progressDialog = new ProgressDialog(this);
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

        DM = new ArrayList<>();

        Delete = findViewById(R.id.Delete);
        Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteRequset(Hawk.get(Constants.mRequestID) + "", Hawk.get(Constants.mRequestUserID) + "");
            }
        });
        if (Hawk.contains(Constants.mRequestStatus)) {
            if (!Hawk.get(Constants.mRequestStatus).equals("0")) {
                Delete.setVisibility(View.GONE);
            }
        } else {
            Delete.setVisibility(View.GONE);
        }
        //userModel=Hawk.get(Constants.userData);
        //if(userModel.getId().equals())


        RV = findViewById(R.id.RV);


        offersAdapter = new OffersAdapter(DM, this, new OffersAdapter.OnItemClick() {
            @Override
            public void setOnItemClick(int position) {
                if (Hawk.get(Constants.UserType).equals("0")) {
                    Intent intent = new Intent(OffersAcivity.this, ServiceProviderDescription.class);
                    Hawk.put(Constants.mOfferModel, DM.get(position));
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(OffersAcivity.this, ClientRequestDetailsActivity.class);
                    startActivity(intent);
                }
            }
        });
        RV.setAdapter(offersAdapter);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        RV.setLayoutManager(mLayoutManager);
        offersAdapter.notifyDataSetChanged();


        getOffers(Hawk.get(Constants.mRequestID) + "", "");

    }

    private void getOffers(String requestId, String id) {
        progressDialog.show();
        Log.d("TTT", "getOffers: Called" + requestId);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Connectors.connectionServices.BaseURL)
                .addConverterFactory(GsonConverterFactory
                        .create(new Gson())).build();
        Connectors.connectionServices connectionService =
                retrofit.create(Connectors.connectionServices.class);

        connectionService.get_offers(requestId).enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {
                progressDialog.dismiss();
                Example statusModel = response.body();
                Log.d("TTTT", "id : " + requestId + statusModel.getStatus());

                if (statusModel.getStatus()) {
                    Log.d("TTTT", "id : " + requestId + "--" + "onResponse: " + statusModel.getOffers().size());
                    DM.clear();
                    DM.addAll(statusModel.getOffers());
                    offersAdapter.notifyDataSetChanged();
                    if(statusModel.getCount()==0){
                        progressDialog.setMessage(getString(R.string.waiting_for_service_provider));
                        progressDialog.show();
                    }
                }

            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {
                progressDialog.dismiss();
                Snackbar.make(parentLayout, "" + getString(R.string.noInternetConnecion), Snackbar.LENGTH_LONG)
                        .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                        .show();
                Log.d("TTT", "onFailure: " + t.getMessage());

            }
        });
    }

    private void deleteRequset(String id, String userID) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Connectors.connectionServices.BaseURL)
                .addConverterFactory(GsonConverterFactory
                        .create(new Gson())).build();
        Connectors.connectionServices connectionService =
                retrofit.create(Connectors.connectionServices.class);

        connectionService.delete_request(userID, id).enqueue(new Callback<StatusModel>() {
            @Override
            public void onResponse(Call<StatusModel> call, Response<StatusModel> response) {
                StatusModel statusModel = response.body();
                if (statusModel.getStatus()) {
                    Toast.makeText(OffersAcivity.this, ""+getString(R.string.Request_Deleted), Toast.LENGTH_SHORT).show();
                    finishAffinity();
                    startActivity(new Intent(OffersAcivity.this,ClientHomeActivity.class));
                } else {

                }
            }

            @Override
            public void onFailure(Call<StatusModel> call, Throwable t) {
                Log.d("TTT", "onFailure:1 " + t.getMessage());
                Snackbar.make(parentLayout, "" + getString(R.string.noInternetConnecion) + "000", Snackbar.LENGTH_LONG)
                        .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                        .show();
            }
        });

    }
}

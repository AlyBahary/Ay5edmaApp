package com.example.smatech.ay5edma;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.smatech.ay5edma.Adapters.OffersAdapter;
import com.example.smatech.ay5edma.Adapters.RequestAdapter;
import com.example.smatech.ay5edma.Models.Modelss.StatusModel;
import com.example.smatech.ay5edma.Models.Modelss.UserModel;
import com.example.smatech.ay5edma.Models.OffersDummyModel;
import com.example.smatech.ay5edma.Models.RequestModel;
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

public class RequestsActivity extends AppCompatActivity {

    RecyclerView RV;
    ArrayList<com.example.smatech.ay5edma.Models.Modelss.RequestModel> DM;
    RequestAdapter requestAdapter;
    TextView Previous, Recent;
    UserModel userModel;
    String userType;
    ProgressDialog progressDialog;
    private View parentLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requests);
        parentLayout = findViewById(android.R.id.content);
        Previous = findViewById(R.id.Previous);
        Recent = findViewById(R.id.Recent);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.Loading));

        //
        ImageView back;
        TextView toolbar_title;
        toolbar_title = findViewById(R.id.toolbar_title);
        toolbar_title.setText(this.getString(R.string.Requestes) + "");
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //
        // bottom nav
        LinearLayout homeLayout, requestLayout, notificationLayout, settingLayout;
        homeLayout = findViewById(R.id.homeLayout);
        homeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        requestLayout = findViewById(R.id.requestLayout);
        requestLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        notificationLayout = findViewById(R.id.notificationLayout);
        notificationLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(RequestsActivity.this, NotificationsActivity.class));
            }
        });
        settingLayout = findViewById(R.id.settingLayout);
        settingLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(RequestsActivity.this, SettingActiviy.class));

            }
        });
        //
        userModel = Hawk.get(Constants.userData);
        //

        userType = Hawk.get(Constants.UserType);
        Log.d("TTTT", "onCreate:UserType " + userType);

        DM = new ArrayList<>();
        RV = findViewById(R.id.RV);
        if (Hawk.get(Constants.UserType).equals("0")) {
            requestAdapter = new RequestAdapter(DM, "0", this, RequestsActivity.this, new RequestAdapter.OnItemClick() {
                @Override
                public void setOnItemClick(int position) {
                    if (Hawk.get(Constants.UserType).equals("0") && DM.get(position).getStatus().equals("0")) {
                        Intent intent = new Intent(RequestsActivity.this, OffersAcivity.class);
                        Hawk.put(Constants.mRequestID, DM.get(position).getId());
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(RequestsActivity.this, OffersAcivity.class);
                        Hawk.put(Constants.mRequestID, DM.get(position).getId());
                        startActivity(intent);

                    }

                }
            });
        } else {
            requestAdapter = new RequestAdapter(DM, "1", this, RequestsActivity.this, new RequestAdapter.OnItemClick() {
                @Override
                public void setOnItemClick(int position) {
                    if (DM.get(position).getStatus().equals("2")) {
                        Intent intent = new Intent(RequestsActivity.this, ClientRequestDetailsActivity.class).putExtra("flag", "1");
                        Hawk.put(Constants.mRequestID, DM.get(position).getId());
                        Hawk.put(Constants.mRequestFrom, DM.get(position).getUser());
                        Hawk.put(Constants.mRequestDesc, DM.get(position).getBody());
                        startActivity(intent);

                    }


                }
            });
        }


        if (getIntent().getStringExtra("stat") != null) {
            if (getIntent().getStringExtra("stat").equals("0")) {
                //getPrev
                getPreviouse();
                Recent.setBackgroundColor(Color.TRANSPARENT);
                Previous.setBackground(ContextCompat.getDrawable(RequestsActivity.this, R.drawable.toggle1));

            } else {
                //getRecent
                getRecent();
                Previous.setBackgroundColor(Color.TRANSPARENT);
                Recent.setBackground(ContextCompat.getDrawable(RequestsActivity.this, R.drawable.toggle2));
            }
        }else{
            getRecent();
            Previous.setBackgroundColor(Color.TRANSPARENT);
            Recent.setBackground(ContextCompat.getDrawable(RequestsActivity.this, R.drawable.toggle2));

        }

        Previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPreviouse();
                Recent.setBackgroundColor(Color.TRANSPARENT);
                Previous.setBackground(ContextCompat.getDrawable(RequestsActivity.this, R.drawable.toggle1));

            }
        });
        Recent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getRecent();
                Previous.setBackgroundColor(Color.TRANSPARENT);
                Recent.setBackground(ContextCompat.getDrawable(RequestsActivity.this, R.drawable.toggle2));

            }
        });
    }

    //
    public void getPreviouse() {
        Hawk.put(Constants.Time, "0");
        DM.clear();
        if (userType.equals("0")) {
            getRequestes(userModel.getId() + "", "1", "", "", "", "");
            progressDialog.show();
        } else {
            getRequestes("", "1", "" + userModel.getId(), "" + userModel.getSubcategoryId()
                    , "" + userModel.getCategoryId(), "");
            progressDialog.show();
        }

        RV.setAdapter(requestAdapter);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        RV.setLayoutManager(mLayoutManager);
        requestAdapter.notifyDataSetChanged();

    }

    public void getRecent() {
        Hawk.put(Constants.Time, "1");

        DM.clear();
        if (userType.equals("0")) {
            getRequestes(userModel.getId() + "", "0", "", "", "", "");
            progressDialog.show();
        } else {
            getRequestes("", "0", "" + userModel.getId()
                    , "" + userModel.getSubcategoryId(), "" + userModel.getCategoryId(), "");
            progressDialog.show();

            Log.d("TTT", "getRecent:CatgryID " + userModel.getCategoryId());
            Log.d("TTT", "getRecent:SubCatgry " + userModel.getSubcategoryId());
        }


        RV.setAdapter(requestAdapter);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        RV.setLayoutManager(mLayoutManager);
        requestAdapter.notifyDataSetChanged();

    }

    private void getRequestes(String user_id, final String status, String shop_id, String subcatgry, String Catgry, String filter_id) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Connectors.connectionServices.BaseURL)
                .addConverterFactory(GsonConverterFactory
                        .create(new Gson())).build();
        Connectors.connectionServices connectionService =
                retrofit.create(Connectors.connectionServices.class);

        connectionService.get_Requests(user_id, status, shop_id, "" + subcatgry, "" + Catgry, filter_id)
                .enqueue(new Callback<StatusModel>() {
                    @Override
                    public void onResponse(Call<StatusModel> call, Response<StatusModel> response) {
                        progressDialog.dismiss();
                        StatusModel statusModel = response.body();
                        if (statusModel.getStatus()) {
                            DM.clear();
                            DM.addAll(statusModel.getRequests());
                            requestAdapter.notifyDataSetChanged();
                        } else {

                        }

                    }

                    @Override
                    public void onFailure(Call<StatusModel> call, Throwable t) {
                        progressDialog.dismiss();
                        Snackbar.make(parentLayout, "" + getString(R.string.noInternetConnecion), Snackbar.LENGTH_LONG)
                                .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                                .show();

                    }
                });

    }
}

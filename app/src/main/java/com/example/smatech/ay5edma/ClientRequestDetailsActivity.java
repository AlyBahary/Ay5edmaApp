package com.example.smatech.ay5edma;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.smatech.ay5edma.Models.Modelss.StatusModel;
import com.example.smatech.ay5edma.Models.Modelss.UserModel;
import com.example.smatech.ay5edma.Utils.Connectors;
import com.example.smatech.ay5edma.Utils.Constants;
import com.google.gson.Gson;
import com.orhanobut.hawk.Hawk;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ClientRequestDetailsActivity extends AppCompatActivity {

    String requestID,requestBody,flag;
    UserModel fromModel,userModel;
    TextView name,type,occupation,b_d,address,about;
    LinearLayout Reveiws;
    ImageView Accept,Reject;
    me.zhanghai.android.materialratingbar.MaterialRatingBar stars;
    ProgressDialog progressDialog;
    View parentLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_request_details);
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.Loading));

        ImageView back;
        TextView toolbar_title;
        toolbar_title = findViewById(R.id.toolbar_title);
        toolbar_title.setText(getString(R.string.Client_request_details));
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

         flag=getIntent().getStringExtra("flag");
        // bottom nav
        LinearLayout homeLayout,requestLayout,notificationLayout,settingLayout ;
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
                finish();
                //startActivity(new Intent(ClientRequestDetailsActivity.this,RequestsActivity.class));
            }
        });
        notificationLayout = findViewById(R.id.notificationLayout);
        notificationLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
                startActivity(new Intent(ClientRequestDetailsActivity.this,NotificationsActivity.class));

            }
        });
        settingLayout = findViewById(R.id.settingLayout);
        settingLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(ClientRequestDetailsActivity.this,SettingActiviy.class));

            }
        });
        //



        fromModel=Hawk.get(Constants.mRequestFrom);
        userModel=Hawk.get(Constants.userData);
        requestID=Hawk.get(Constants.mRequestID);
        requestBody=Hawk.get(Constants.mRequestDesc);
        Accept=findViewById(R.id.Accept);
        Accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sendOffer(fromModel.getId(),userModel.getId(),requestID);
            }
        });
        Reject=findViewById(R.id.Reject);
        Reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        name=findViewById(R.id.name);
        name.setText(fromModel.getName());
        stars=findViewById(R.id.Stars);
        stars.setRating(Float.parseFloat(fromModel.getRate()));
        type=findViewById(R.id.type);
        if(fromModel.getType().equals("0")){
            type.setText(R.string.male);
        }else {
            type.setText(R.string.female);

        }
        if(flag==null){

        }else{
            Reject.setVisibility(View.GONE);
            Accept.setVisibility(View.GONE);
        }
        occupation=findViewById(R.id.occupation);
        occupation.setText("");
        b_d=findViewById(R.id.b_d);
        b_d.setText(fromModel.getBirthday());
        address=findViewById(R.id.address);
        address.setText(fromModel.getAddress());
        about=findViewById(R.id.about);
        about.setText(requestBody);
        Reveiws=findViewById(R.id.Reveiws);
        Reveiws.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        parentLayout = findViewById(android.R.id.content);

    }
    private void sendOffer(String userId,String shopId,String requestId){
        progressDialog.show();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Connectors.connectionServices.BaseURL)
                .addConverterFactory(GsonConverterFactory
                        .create(new Gson())).build();
        Connectors.connectionServices connectionService =
                retrofit.create(Connectors.connectionServices.class);

        connectionService.sendOffer(userId,shopId,requestId).enqueue(new Callback<StatusModel>() {
            @Override
            public void onResponse(Call<StatusModel> call, Response<StatusModel> response) {
                progressDialog.dismiss();
                StatusModel statusModel=response.body();
                if(statusModel.getStatus()){
                    Snackbar.make(parentLayout, "" + getString(R.string.offerhadbeensend), Snackbar.LENGTH_LONG).show();


                }else{

                }

            }

            @Override
            public void onFailure(Call<StatusModel> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
}
}

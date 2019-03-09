package com.example.smatech.ay5edma;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.smatech.ay5edma.Adapters.OffersAdapter;
import com.example.smatech.ay5edma.Adapters.ReveiwsAdapter;
import com.example.smatech.ay5edma.Models.Modelss.StatusModel;
import com.example.smatech.ay5edma.Models.OffersDummyModel;
import com.example.smatech.ay5edma.Models.ReviewModel;
import com.example.smatech.ay5edma.Utils.Connectors;
import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ClientReviewsActivity extends AppCompatActivity {

    RecyclerView RV;
    ArrayList<com.example.smatech.ay5edma.Models.Modelss.ReviewModel> DM;
    ReveiwsAdapter reveiwsAdapter;
    ProgressDialog progressDialog;
    private View parentLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_reviews);
        progressDialog=new ProgressDialog(this);
        parentLayout = findViewById(android.R.id.content);
        ImageView back;
        TextView toolbar_title;
        toolbar_title=findViewById(R.id.toolbar_title);
        toolbar_title.setText(getString(R.string.Client_Reviews));
        back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        DM=new ArrayList<>();
    
        RV=findViewById(R.id.RV);



       reveiwsAdapter=new ReveiwsAdapter(DM, ClientReviewsActivity.this, new ReveiwsAdapter.OnItemClick() {
           @Override
           public void setOnItemClick(int position) {

           }
       });
        RV.setAdapter(reveiwsAdapter);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        RV.setLayoutManager(mLayoutManager);
        reveiwsAdapter.notifyDataSetChanged();
        getReviews(getIntent().getStringExtra("userID"));

    }
    private void getReviews(String userID){
        progressDialog.show();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Connectors.connectionServices.BaseURL)
                .addConverterFactory(GsonConverterFactory
                        .create(new Gson())).build();
        Connectors.connectionServices connectionService =
                retrofit.create(Connectors.connectionServices.class);
        connectionService.get_reviews(userID).enqueue(new Callback<StatusModel>() {
            @Override
            public void onResponse(Call<StatusModel> call, Response<StatusModel> response) {
                progressDialog.dismiss();
                StatusModel statusModel=response.body();
                if(statusModel.getStatus()){
                    DM.clear();
                    DM.addAll(statusModel.getReview());
                    reveiwsAdapter.notifyDataSetChanged();
                }else{
                    Snackbar.make(parentLayout, "" + getString(R.string.thereisnoReviews), Snackbar.LENGTH_LONG)
                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                            .show();
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

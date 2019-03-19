package com.example.smatech.ay5edma;

import android.app.ProgressDialog;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.smatech.ay5edma.Adapters.FavAdapter;
import com.example.smatech.ay5edma.Models.Modelss.StatusModel;
import com.example.smatech.ay5edma.Models.Modelss.UserModel;
import com.example.smatech.ay5edma.Models.RequestModel;
import com.example.smatech.ay5edma.Models.favModel.Example;
import com.example.smatech.ay5edma.Models.favModel.Request;
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

public class FavouritsActivity extends AppCompatActivity {
    RecyclerView RV;
    ArrayList<Request> DM;
    FavAdapter favAdapter;
    ProgressDialog progressDialog;
    UserModel userModel;
    private View parentLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_re_order);
        parentLayout = findViewById(android.R.id.content);

        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.Loading));
        ImageView back;
        TextView toolbar_title;
        toolbar_title=findViewById(R.id.toolbar_title);
        toolbar_title.setText(this.getString(R.string.Favourits)+"");
        back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        ////
        userModel= Hawk.get(Constants.userData);
        getAllfav(userModel.getId());

        DM=new ArrayList<>();
        RV=findViewById(R.id.RV);


        favAdapter= new FavAdapter(DM, this,FavouritsActivity.this, new FavAdapter.OnItemClick() {
            @Override
            public void setOnItemClick(int position) {

            }
        });
        RV.setAdapter(favAdapter);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        RV.setLayoutManager(mLayoutManager);
        favAdapter.notifyDataSetChanged();


        //

    }
    private void getAllfav(String userID){
        progressDialog.show();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Connectors.connectionServices.BaseURL)
                .addConverterFactory(GsonConverterFactory
                        .create(new Gson())).build();
        Connectors.connectionServices connectionService =
                retrofit.create(Connectors.connectionServices.class);

        connectionService.get_Favourite(userID,true).enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {
                progressDialog.dismiss();
                Example statusModel=response.body();
                Log.d("TTT-->Fav", "onResponse: "+response.raw());
                if(statusModel.getStatus()){
                    DM.addAll(statusModel.getRequests());
                    favAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {
                progressDialog.dismiss();
                Log.d("TTT", "onFailure: "+t.getMessage());
                Snackbar.make(parentLayout, "" + getString(R.string.noInternetConnecion), Snackbar.LENGTH_LONG)
                        .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                        .show();
            }
        });
    }
}

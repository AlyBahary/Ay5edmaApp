package com.example.smatech.ay5edma;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.smatech.ay5edma.Adapters.PackageAdapter;
import com.example.smatech.ay5edma.Adapters.RequestAdapter;
import com.example.smatech.ay5edma.Models.Modelss.PointsModel;
import com.example.smatech.ay5edma.Models.Modelss.StatusModel;
import com.example.smatech.ay5edma.Models.Modelss.UserModel;
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

public class PackageActivity extends AppCompatActivity {
    RecyclerView RV;
    ArrayList<PointsModel> DM;
    PackageAdapter packageAdapter;
    ProgressDialog progressDialog;
    private View parentLayout;
    TextView points;
    UserModel userModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package);
        progressDialog = new ProgressDialog(this);
        parentLayout = findViewById(android.R.id.content);
        progressDialog.setMessage(getString(R.string.Loading));
        getPoints();
        points=findViewById(R.id.points);
        userModel= Hawk.get(Constants.userData);
        //set here num of points
        points.setText(""+userModel.getPoints());
        ImageView back;
        TextView toolbar_title;
        toolbar_title = findViewById(R.id.toolbar_title);
        toolbar_title.setText(getString(R.string.Buy_Points));
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        /////////////

        DM = new ArrayList<>();
        RV = findViewById(R.id.RV);


        packageAdapter = new PackageAdapter(DM, this, PackageActivity.this, new PackageAdapter.OnItemClick() {
            @Override
            public void setOnItemClick(int position) {
                Intent i = new Intent(PackageActivity.this, BankAccountsRVAcivity.class);
                startActivity(i);
            }
        });
        RV.setAdapter(packageAdapter);
        RV.setLayoutManager(new GridLayoutManager(this, 2));
        packageAdapter.notifyDataSetChanged();
        /////////////

    }
    private void getPoints() {
        progressDialog.show();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Connectors.connectionServices.BaseURL)
                .addConverterFactory(GsonConverterFactory
                        .create(new Gson())).build();
        Connectors.connectionServices connectionService =
                retrofit.create(Connectors.connectionServices.class);

        connectionService.get_points().enqueue(new Callback<StatusModel>() {
            @Override
            public void onResponse(Call<StatusModel> call, Response<StatusModel> response) {
                progressDialog.dismiss();
                StatusModel statusModel=response.body();
                if(statusModel.getStatus()){
                    DM.addAll(statusModel.getPoints());
                    packageAdapter.notifyDataSetChanged();
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

package com.example.smatech.ay5edma;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.smatech.ay5edma.Adapters.CatgryAdapter;
import com.example.smatech.ay5edma.Models.DummyModel;
import com.example.smatech.ay5edma.Models.Modelss.CategoryModel;
import com.example.smatech.ay5edma.Models.Modelss.StatusModel;
import com.example.smatech.ay5edma.Models.Modelss.SubCategoryModel;
import com.example.smatech.ay5edma.Utils.Connectors;
import com.example.smatech.ay5edma.Utils.Constants;
import com.google.gson.Gson;
import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SpecificCatgryActivity extends AppCompatActivity {


    RecyclerView RV;
    ArrayList<CategoryModel> DM;
    CatgryAdapter catgryAdapter;
    View parentLayout;
    ProgressDialog progressDialog;
    LinearLayout Go;
    EditText Search;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific_catgry);
        ImageView back;
        TextView toolbar_title;
        toolbar_title = findViewById(R.id.toolbar_title);
        toolbar_title.setText(Hawk.get(Constants.SubCat1) + "");
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        parentLayout = findViewById(android.R.id.content);
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.Loading));

        Search=findViewById(R.id.Search);
        Go=findViewById(R.id.Go);

        //initialization
        Hawk.put((Constants.Addlocationdlong), "");
        Hawk.put((Constants.Addlocationdlat), "");

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
                if (Hawk.get(Constants.skipe).equals(true)) {
                    Snackbar.make(parentLayout, "" + getString(R.string.you_have_to_login_to_use_all_app), Snackbar.LENGTH_LONG)
                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                            .show();
                } else {
                    startActivity(new Intent(SpecificCatgryActivity.this,RequestsActivity.class));

                }
            }
        });
        notificationLayout = findViewById(R.id.notificationLayout);
        notificationLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Hawk.get(Constants.skipe).equals(true)) {
                    Snackbar.make(parentLayout, "" + getString(R.string.you_have_to_login_to_use_all_app), Snackbar.LENGTH_LONG)
                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                            .show();
                } else {
                    startActivity(new Intent(SpecificCatgryActivity.this,NotificationsActivity.class));

                }

            }
        });
        settingLayout = findViewById(R.id.settingLayout);
        settingLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Hawk.get(Constants.skipe).equals(true)) {
                    Snackbar.make(parentLayout, "" + getString(R.string.you_have_to_login_to_use_all_app), Snackbar.LENGTH_LONG)
                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                            .show();
                } else {
                    startActivity(new Intent(SpecificCatgryActivity.this,SettingActiviy.class));

                }

            }
        });
        //

        DM = new ArrayList<>();

        RV = findViewById(R.id.RV);
        catgryAdapter = new CatgryAdapter(DM, this, new CatgryAdapter.OnItemClick() {
            @Override
            public void setOnItemClick(int position) {
                Hawk.put(Constants.SubCat2, DM.get(position).getName());
                Hawk.put(Constants.mSubCatgrID, DM.get(position).getId());
                // Log.d("TTT", "setOnItemClick: "+DM.get(position).getDesc()+"----"+Hawk.get(Constants.SubCat2)+"");
                Intent intent = new Intent(SpecificCatgryActivity.this, RequestDescriptionActivity.class);
                startActivity(intent);
            }
        });
        RV.setAdapter(catgryAdapter);
        RV.setLayoutManager(new GridLayoutManager(this, 3));
        catgryAdapter.notifyDataSetChanged();
        getSubCatg(Hawk.get(Constants.mCatgrID) + "","");
        progressDialog.show();
        Go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!Search.getText().toString().equals("")) {
                    DM.clear();
                    catgryAdapter.notifyDataSetChanged();
                    getSubCatg(Hawk.get(Constants.mCatgrID)+"",Search.getText().toString());
                }
            }
        });
    }

    private void getSubCatg(String category_id,String search) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Connectors.connectionServices.BaseURL)
                .addConverterFactory(GsonConverterFactory
                        .create(new Gson())).build();
        Connectors.connectionServices connectionService =
                retrofit.create(Connectors.connectionServices.class);

        connectionService.get_SubCategory(category_id, ""+search).enqueue(new Callback<StatusModel>() {
            @Override
            public void onResponse(Call<StatusModel> call, Response<StatusModel> response) {
                progressDialog.dismiss();
                StatusModel statusModel = response.body();

                if (statusModel.getStatus() == true) {
                    ArrayList<SubCategoryModel> subCategoryModels = statusModel.getSubCategoryModels();

                    for (int i = 0; i < subCategoryModels.size(); i++) {
                        CategoryModel categoryModel = new CategoryModel();
                        categoryModel.setName(subCategoryModels.get(i).getName() + "");
                        categoryModel.setNameAr(subCategoryModels.get(i).getNameAr() + "");
                        categoryModel.setId(subCategoryModels.get(i).getId());
                        categoryModel.setImage(subCategoryModels.get(i).getImage());
                        DM.add(categoryModel);
                    }
                    catgryAdapter.notifyDataSetChanged();
                } else {

                }

            }

            @Override
            public void onFailure(Call<StatusModel> call, Throwable t) {
                progressDialog.dismiss();


            }
        });

    }

}

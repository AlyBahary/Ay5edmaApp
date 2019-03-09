package com.example.smatech.ay5edma;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smatech.ay5edma.Models.Modelss.CategoryModel;
import com.example.smatech.ay5edma.Models.Modelss.StatusModel;
import com.example.smatech.ay5edma.Models.Modelss.SubCategoryModel;
import com.example.smatech.ay5edma.Models.Modelss.UserModel;
import com.example.smatech.ay5edma.Models.Modelss.UserModelSatus;
import com.example.smatech.ay5edma.Utils.Connectors;
import com.example.smatech.ay5edma.Utils.Constants;
import com.google.gson.Gson;
import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BeServiceProvier extends AppCompatActivity {
    Spinner Spinner1, Spinner2;
    Button Agree;
    UserModel userModel;
    View parentLayout;
    List<String> list2, list;
    List<String> ids2, ids;
    String category_id = "", subcategory_id = "", subcategory2_id = "";

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_be_service_provier);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.Loading));
        ImageView back;

        parentLayout = findViewById(android.R.id.content);
        TextView toolbar_title;
        toolbar_title = findViewById(R.id.toolbar_title);
        toolbar_title.setText(getString(R.string.convert_to_service_provider));
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getCategries();
        ////
        userModel = Hawk.get(Constants.userData);

        Agree = findViewById(R.id.Agree);
        Agree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (category_id != "" && subcategory_id != "") {
                    edit("" + userModel.getMobile()
                            , "" + userModel.getImage()
                            , "" + userModel.getId()
                            , "" + userModel.getName()
                            , "" + userModel.getGender()
                            , "" + category_id
                            , "" + subcategory_id);

                }
            }
        });

        Spinner1 = findViewById(R.id.Spinner1);
        ids2 = new ArrayList<>();
        ids2.add("0");
        ids = new ArrayList<>();
        ids.add("0");
        list = new ArrayList<String>();
        list.add(getString(R.string.Main_Service));
        list2 = new ArrayList<String>();
        list2.add(getString(R.string.Secondary_service));
        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner1.setAdapter(dataAdapter1);
        Spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position == 0) {
                } else {
                    category_id = ids.get(position);
                    getSubCatg(category_id);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ///Spinner2
        Spinner2 = findViewById(R.id.Spinner2);
        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list2);
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner2.setAdapter(dataAdapter2);
        Spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position == 0) {
                } else {
                    subcategory_id = ids2.get(position);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if (userModel.getCategoryId() != null) {
            if (userModel.getCategoryId().equals("") || userModel.getCategoryId().equals("0")) {

            } else {
                edit("" + userModel.getMobile()
                        , "" + userModel.getImage()
                        , "" + userModel.getId()
                        , "" + userModel.getName()
                        , "" + userModel.getGender()
                        , "" + userModel.getCategoryId()
                        , "" + userModel.getSubcategoryId());
            }
        }


    }

    private void getCategries() {
        progressDialog.show();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Connectors.connectionServices.BaseURL)
                .addConverterFactory(GsonConverterFactory
                        .create(new Gson())).build();
        Connectors.connectionServices connectionService =
                retrofit.create(Connectors.connectionServices.class);

        connectionService.get_Category().enqueue(new Callback<StatusModel>() {
            @Override
            public void onResponse(Call<StatusModel> call, Response<StatusModel> response) {
                progressDialog.dismiss();
                StatusModel statusModel = response.body();
                if (statusModel.getStatus() == true) {
                    ArrayList<CategoryModel> categoryModels = statusModel.getCategories();
                    if (categoryModels.size() > 0) {

                        for (int i = 0; categoryModels.size() > i; i++) {
                            ids.add(categoryModels.get(i).getId());

                            if (Locale.getDefault().getDisplayLanguage().equals("العربية")) {
                                list.add(categoryModels.get(i).getNameAr());
                            } else {
                                list.add(categoryModels.get(i).getName());
                            }
                        }

                    }

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

    private void getSubCatg(String category_id) {
        progressDialog.show();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Connectors.connectionServices.BaseURL)
                .addConverterFactory(GsonConverterFactory
                        .create(new Gson())).build();
        Connectors.connectionServices connectionService =
                retrofit.create(Connectors.connectionServices.class);

        connectionService.get_SubCategory(category_id, "").enqueue(new Callback<StatusModel>() {
            @Override
            public void onResponse(Call<StatusModel> call, Response<StatusModel> response) {
                progressDialog.dismiss();
                StatusModel statusModel = response.body();
                list2.clear();
                list2.add(getString(R.string.Secondary_service));
                if (statusModel.getStatus() == true) {
                    ArrayList<SubCategoryModel> subCategoryModels = statusModel.getSubCategoryModels();
                    if (subCategoryModels.size() > 0) {

                        for (int i = 0; subCategoryModels.size() > i; i++) {
                            ids2.add(subCategoryModels.get(i).getId());

                            if (Locale.getDefault().getDisplayLanguage().equals("العربية")) {
                                list2.add(subCategoryModels.get(i).getNameAr() + "");
                            } else {
                                list2.add(subCategoryModels.get(i).getName());
                            }
                        }

                    }

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

    private void edit(String mobile, String img, String id, String name, String gender, String catgryid, String subcategory_id) {
        progressDialog.show();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Connectors.connectionServices.BaseURL)
                .addConverterFactory(GsonConverterFactory
                        .create(new Gson())).build();
        Connectors.connectionServices connectionService =
                retrofit.create(Connectors.connectionServices.class);

        connectionService.edit(mobile, img, id, name, gender, catgryid, subcategory_id,"","2").enqueue(new Callback<StatusModel>() {
            @Override
            public void onResponse(Call<StatusModel> call, Response<StatusModel> response) {

                progressDialog.dismiss();
                StatusModel statusModel = response.body();
                if (statusModel.getStatus()) {
                    login(mobile, Hawk.get(Constants.password), Hawk.get(Constants.TOKEN));

                } else {
                    Snackbar.make(parentLayout, "" + getString(R.string.somethingwentwrong), Snackbar.LENGTH_LONG)
                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                            .show();
                }
            }

            @Override
            public void onFailure(Call<StatusModel> call, Throwable t) {
                Snackbar.make(parentLayout, "" + getString(R.string.noInternetConnecion), Snackbar.LENGTH_LONG)
                        .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                        .show();
                progressDialog.dismiss();

            }
        });
    }

    private void login(String mobile, String password, String token) {
        progressDialog.show();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Connectors.connectionServices.BaseURL)
                .addConverterFactory(GsonConverterFactory
                        .create(new Gson())).build();
        Connectors.connectionServices connectionService =
                retrofit.create(Connectors.connectionServices.class);

        connectionService.login(password, mobile, token).enqueue(new Callback<UserModelSatus>() {
            @Override
            public void onResponse(Call<UserModelSatus> call, Response<UserModelSatus> response) {
                Hawk.put(Constants.password, password);
                Hawk.put(Constants.username, mobile);
                Log.d("TTTT", "onResponse: Response00");
                progressDialog.dismiss();
                UserModelSatus statusModel = response.body();
                if (statusModel.getStatus() == true) {
                    Log.d("TTTT", "onResponse: Response11");
                    UserModel userModel = statusModel.getUser();
                    userModel.setAccepted(statusModel.getAccepted() + "");
                    userModel.setPoints(statusModel.getPoints() + "");
                    userModel.setPeople(statusModel.getPeople() + "");
                    userModel.setRejected(statusModel.getRejected() + "");
                    userModel.setRole("2");
                    Hawk.put(Constants.UserType, "1");
                    Hawk.put(Constants.userData, userModel);
                    // Log.d("TTTTTT", "onResponse: "+userModel.getAccepted()+userModel.getPoints()+userModel.getPeople()+userModel.getRejected());
                    Hawk.put(Constants.userData, userModel);
                    Hawk.put(Constants.extraauserData1, statusModel.getCategory());
                    Hawk.put(Constants.extraauserData2, statusModel.getSubcategory());
                    finish();
                    Intent intent = new Intent(BeServiceProvier.this, ClientHomeActivity.class);
                    startActivity(intent);
                } else {
                    Log.d("TTTT", "onResponse: Response22");

                    if (Locale.getDefault().getDisplayLanguage().equals("English")) {
                        Snackbar.make(parentLayout, "" + statusModel.getMessage(), Snackbar.LENGTH_LONG)
                                .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                                .show();
                    } else {
                        Snackbar.make(parentLayout, "" + statusModel.getMessage_ar(), Snackbar.LENGTH_LONG)
                                .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                                .show();
                    }
                }

            }

            @Override
            public void onFailure(Call<UserModelSatus> call, Throwable t) {
                progressDialog.dismiss();
                Snackbar.make(parentLayout, "" + getString(R.string.noInternetConnecion), Snackbar.LENGTH_LONG)
                        .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                        .show();
                Log.d("TTTT", "onResponse: fail" + t.getMessage());


            }
        });
    }

}

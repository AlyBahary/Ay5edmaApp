
package com.example.smatech.ay5edma;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.features.ReturnMode;
import com.esafirm.imagepicker.model.Image;
import com.example.smatech.ay5edma.Models.Modelss.CategoryModel;
import com.example.smatech.ay5edma.Models.Modelss.ImageUrlModel;
import com.example.smatech.ay5edma.Models.Modelss.StatusModel;
import com.example.smatech.ay5edma.Models.Modelss.SubCategoryModel;
import com.example.smatech.ay5edma.Models.Modelss.UserModel;
import com.example.smatech.ay5edma.Models.Modelss.UserModelSatus;
import com.example.smatech.ay5edma.Utils.Connectors;
import com.example.smatech.ay5edma.Utils.Constants;
import com.google.gson.Gson;
import com.orhanobut.hawk.Hawk;
import com.thefinestartist.finestwebview.FinestWebView;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceProviderSingupActivity extends AppCompatActivity {
    Spinner signup_Primary_Service, signup_Secondary_Service, signup_Secondary1_Service;
    View parentLayout;
    List<String> list2, list;
    List<String> ids2, ids;
    String category_id, subcategory_id, subcategory2_id = "";
    CheckBox Agrement_CheckBox;
    Button Signup;
    EditText Signup_About;
    ProgressDialog progressDialog;
    Button uploadImgs;
    ArrayList<String> urlStrings;
    LinearLayout terms_conditions;
    Map imagesMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_provider_singup);
        imagesMap=new HashMap();
        urlStrings = new ArrayList<>();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.Loading));
        terms_conditions = findViewById(R.id.terms_conditions);
        Signup_About = findViewById(R.id.Signup_About);
        parentLayout = findViewById(android.R.id.content);
        Agrement_CheckBox = findViewById(R.id.Agrement_CheckBox);
        getCategries();

        terms_conditions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Hawk.contains(Constants.Language)) {
                    if (Hawk.get(Constants.Language).equals("ar")) {
                        new FinestWebView.Builder(ServiceProviderSingupActivity.this).updateTitleFromHtml(false)
                                .titleDefault(getString(R.string.Aboutus)).show("http://anyservice-ksa.com/api/webview?type=terms_ar");

                    } else {
                        new FinestWebView.Builder(ServiceProviderSingupActivity.this).updateTitleFromHtml(false)
                                .titleDefault(getString(R.string.Aboutus)).show("http://anyservice-ksa.com/api/webview?type=terms");
                    }
                } else {
                    new FinestWebView.Builder(ServiceProviderSingupActivity.this).updateTitleFromHtml(false)
                            .titleDefault(getString(R.string.Aboutus)).show("http://anyservice-ksa.com/api/webview?type=terms");

                }
            }
        });
        signup_Primary_Service = findViewById(R.id.signup_Primary_Service);
        ids2 = new ArrayList<>();
        ids2.add("0");
        ids = new ArrayList<>();
        ids.add("0");
        list = new ArrayList<String>();
        list.add(getString(R.string.Main_Service));
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        signup_Primary_Service.setAdapter(dataAdapter);
        signup_Primary_Service.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                } else {
                    category_id = ids.get(position);
                    Toast.makeText(ServiceProviderSingupActivity.this, "" + category_id, Toast.LENGTH_SHORT).show();
                    getSubCatg(category_id);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        signup_Secondary_Service =

                findViewById(R.id.signup_Secondary_Service);


        list2 = new ArrayList<String>();
        list2.add(getString(R.string.Secondary_service));
        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list2);
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        signup_Secondary_Service.setAdapter(dataAdapter2);
        signup_Secondary_Service.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

        ///
        signup_Secondary1_Service =

                findViewById(R.id.signup_Secondary1_Service);
        signup_Secondary1_Service.setAdapter(dataAdapter2);
        signup_Secondary1_Service.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position == 0) {
                } else {
                    subcategory2_id = ids2.get(position);

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        uploadImgs = findViewById(R.id.uploadImgs);
        uploadImgs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.create(ServiceProviderSingupActivity.this)
                        .folderMode(true) // folder mode (false by default)
                        .toolbarFolderTitle("Folder") // folder selection title
                        .toolbarImageTitle("Tap to select") // image selection title
                        .toolbarArrowColor(Color.BLACK) // Toolbar 'up' arrow color
                        .includeVideo(false) // Show video on image picker
                        .multi() // multi mode (default mode)
                        .limit(5) // max images can be selected (99 by default)
                        .showCamera(true) // show camera or not (true by default)
                        .imageDirectory("Camera") // directory name for captured image  ("Camera" folder by default)
                        .enableLog(false) // disabling log
                        .start(); // start image picker activity with request code

            }
        });
        //
        Signup = findViewById(R.id.Signup);
        Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (category_id.equals("0") || subcategory_id.equals("0")) {
                    Snackbar.make(parentLayout, "" + getString(R.string.Please_fill_empty_fields), Snackbar.LENGTH_LONG)
                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                            .show();
                } else if (!Agrement_CheckBox.isChecked()) {
                    Snackbar.make(parentLayout, "" + getString(R.string.Please_Agree_terms_Conditions), Snackbar.LENGTH_LONG)
                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                            .show();

                } else {

                    signup(Hawk.get(Constants.pass) + ""
                            , "2"
                            , Hawk.get(Constants.mobile) + ""
                            , Hawk.get(Constants.BirthDate)
                            , Hawk.get(Constants.sex) + ""
                            , Hawk.get(Constants.name), "" + "", subcategory_id, category_id, subcategory2_id + "", "" + Hawk.get(Constants.userLong)
                            , "" + Hawk.get(Constants.userLat), Hawk.get(Constants.userAddress) + ""
                            ,""+Signup_About.getText().toString(), urlStrings);
                    progressDialog.show();
                }
            }
        });
        //
    }


    private void signup(String password
            , String role
            , String mobile
            , String birthdate
            , String gender
            , String name
            , String image
            , String subcategory_id
            , String category_id
            , String subcategory2_id
            , String longitude
            , String latitude
            , String address
            , String About
            , ArrayList<String> images
    ) {
        Log.d("TTTT", "esssssst: tsssssssst");

        if(images.size()>0){
            for(int ii=0;ii<images.size();ii++){
                imagesMap.put("images[" + ii + "]",images.get(ii));
            }
        }
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Connectors.connectionServices.BaseURL)
                .addConverterFactory(GsonConverterFactory
                        .create(new Gson())).build();
        Connectors.connectionServices connectionService =
                retrofit.create(Connectors.connectionServices.class);

        connectionService.signup(password, role, mobile, birthdate, gender, name, image
                , subcategory_id, subcategory2_id, category_id, longitude, latitude, address
                ,About+"",imagesMap)
                .enqueue(new Callback<UserModelSatus>() {
            @Override
            public void onResponse(Call<UserModelSatus> call, Response<UserModelSatus> response) {
                Log.d("TTTT", "onResponse: Response00");

                Hawk.put(Constants.password, password);
                Hawk.put(Constants.username, mobile);
                UserModelSatus statusModel = response.body();
                if (statusModel.getStatus() == true) {
                    Log.d("TTTT", "onResponse: signup Sucess");

                    login(mobile, password, ""+Hawk.get(Constants.TOKEN));
                    UserModel userModel = statusModel.getUser();
                    Hawk.put(Constants.userData, userModel);
                    //  Hawk.put(Constants.extraauserData1,statusModel.getCategory());
                    // Hawk.put(Constants.extraauserData2,statusModel.getSubcategory());

                    Toast.makeText(ServiceProviderSingupActivity.this, "" + userModel.getName(), Toast.LENGTH_SHORT).show();
                } else {
                    if (Hawk.get(Constants.Language).equals("en")) {
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
                Snackbar.make(parentLayout, "" + getString(R.string.noInternetConnecion), Snackbar.LENGTH_LONG)
                        .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                        .show();
                progressDialog.dismiss();

            }
        });

    }


    private void getCategries() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Connectors.connectionServices.BaseURL)
                .addConverterFactory(GsonConverterFactory
                        .create(new Gson())).build();
        Connectors.connectionServices connectionService =
                retrofit.create(Connectors.connectionServices.class);

        connectionService.get_Category().enqueue(new Callback<StatusModel>() {
            @Override
            public void onResponse(Call<StatusModel> call, Response<StatusModel> response) {
                StatusModel statusModel = response.body();
                if (statusModel.getStatus() == true) {
                    ArrayList<CategoryModel> categoryModels = statusModel.getCategories();
                    if (categoryModels.size() > 0) {

                        for (int i = 0; categoryModels.size() > i; i++) {
                            ids.add(categoryModels.get(i).getId());

                            if (Hawk.get(Constants.Language).equals("العربية")) {
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
                Snackbar.make(parentLayout, "" + getString(R.string.noInternetConnecion), Snackbar.LENGTH_LONG)
                        .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                        .show();
            }
        });
    }

    private void getSubCatg(String category_id) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Connectors.connectionServices.BaseURL)
                .addConverterFactory(GsonConverterFactory
                        .create(new Gson())).build();
        Connectors.connectionServices connectionService =
                retrofit.create(Connectors.connectionServices.class);

        connectionService.get_SubCategory(category_id, "").enqueue(new Callback<StatusModel>() {
            @Override
            public void onResponse(Call<StatusModel> call, Response<StatusModel> response) {
                StatusModel statusModel = response.body();
                list2.clear();
                list2.add(getString(R.string.Secondary_service));
                if (statusModel.getStatus() == true) {
                    ArrayList<SubCategoryModel> subCategoryModels = statusModel.getSubCategoryModels();
                    if (subCategoryModels.size() > 0) {

                        for (int i = 0; subCategoryModels.size() > i; i++) {
                            ids2.add(subCategoryModels.get(i).getId());

                            if (Hawk.get(Constants.Language).equals("ar")) {
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
                Snackbar.make(parentLayout, "" + getString(R.string.noInternetConnecion), Snackbar.LENGTH_LONG)
                        .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                        .show();

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

        connectionService.login(password, mobile,Hawk.get(Constants.TOKEN), Hawk.get(Constants.loginLat),Hawk.get(Constants.loginLong)).enqueue(new Callback<UserModelSatus>() {
            @Override
            public void onResponse(Call<UserModelSatus> call, Response<UserModelSatus> response) {
                Hawk.put(Constants.password, password);
                Hawk.put(Constants.username, mobile);
                progressDialog.dismiss();
                Log.d("TTTT", "onResponse: Response33");
                progressDialog.dismiss();
                UserModelSatus statusModel = response.body();
                if (statusModel.getStatus() == true) {
                    Log.d("TTTT", "onResponse: Response22");
                    UserModel userModel = statusModel.getUser();
                    userModel.setAccepted(statusModel.getAccepted() + "");
                    userModel.setPoints(statusModel.getPoints() + "");
                    userModel.setPeople(statusModel.getPeople() + "");
                    userModel.setRejected(statusModel.getRejected() + "");
                    Hawk.put(Constants.userData, userModel);
                    Hawk.put(Constants.extraauserData1, statusModel.getCategory());
                    Hawk.put(Constants.extraauserData2, statusModel.getSubcategory());

                    /*Intent i = new Intent(ServiceProviderSingupActivity.this, NumberConfirmationActivity.class);
                    startActivity(i);*/
                } else {
                    Intent i = new Intent(ServiceProviderSingupActivity.this, NumberConfirmationActivity.class);
                    startActivity(i);
                    Log.d("TTTT", "onResponse: Response22");

                    if (Hawk.get(Constants.Language).equals("en")) {
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
                Log.d("TTTT", "onResponse: fail" + t.getMessage());
                Snackbar.make(parentLayout, "" + getString(R.string.noInternetConnecion), Snackbar.LENGTH_LONG)
                        .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                        .show();
                progressDialog.dismiss();

            }
        });
    }

    public void uploadPhotoService(MultipartBody.Part body) {
        progressDialog.show();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Connectors.connectionServices.BaseURL)
                .addConverterFactory(GsonConverterFactory
                        .create(new Gson())).build();

        Connectors.connectionServices getRegistrationsConnectionServices =
                retrofit.create(Connectors.connectionServices.class);
        getRegistrationsConnectionServices
                .Upload_Img(body).enqueue(new Callback<ImageUrlModel>() {
            @Override
            public void onResponse(Call<ImageUrlModel> call, Response<ImageUrlModel> response) {
                progressDialog.dismiss();
                ImageUrlModel imageUrlModel = response.body();
                if (imageUrlModel.getImages().size() > 0)
                    urlStrings.add(imageUrlModel.getImages().get(0));
                Log.d("TTTT", "onResponse: Size of array of images " + urlStrings.size());
                for (int j = 0; urlStrings.size() > j; j++) {
                    Log.d("TTT", "onResponse: img url : " + urlStrings.get(j));
                }
                Toast.makeText(ServiceProviderSingupActivity.this, "" + getString(R.string.ImageUploaded), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ImageUrlModel> call, Throwable t) {
                progressDialog.dismiss();
                Snackbar.make(parentLayout, "" + getString(R.string.noInternetConnecion), Snackbar.LENGTH_LONG)
                        .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                        .show();

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, final int resultCode, Intent data) {
        if (ImagePicker.shouldHandle(requestCode, resultCode, data)) {
            // Get a list of picked images
            urlStrings = new ArrayList<>();
            List<Image> images = ImagePicker.getImages(data);
            for (int i = 0; i < images.size(); i++) {
                File file = new File(images.get(i).getPath());
                RequestBody reqFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                MultipartBody.Part body = MultipartBody.Part.createFormData("parameters[0]", file.getName(), reqFile);
                uploadPhotoService(body);

            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}

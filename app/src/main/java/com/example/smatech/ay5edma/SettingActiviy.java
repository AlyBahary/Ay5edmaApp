package com.example.smatech.ay5edma;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.esafirm.imagepicker.features.ImagePicker;
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
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SettingActiviy extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    // this activiy for editting profile

    LinearLayout Secondaryservicelayout, primaryservicelayout, datelayout;
    Button Edit;
    String Sex = "";
    ImageView eye;
    EditText Name, mobile, pass, birthdate;
    Spinner primaryservice, Secondaryservice;
    UserModel userModel;
    String category_id="", subcategory_id="";
    Spinner Signup_Gender;
    List<String> list2, list;
    List<String> ids2, ids;
    UserModelSatus statusModel;
    View parentLayout;
    LinearLayout changeImage;
    ProgressDialog progressDialog;
    String urlString = "";
    CircleImageView profile_image;
    private DatePickerDialog datePickerDialog;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_activiy);
        parentLayout = findViewById(android.R.id.content);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.Loading));
        //
        ImageView back;
        TextView toolbar_title;
        toolbar_title = findViewById(R.id.toolbar_title);
        toolbar_title.setText(this.getString(R.string.Setting) + "");
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //

        userModel = Hawk.get(Constants.userData);
        Secondaryservicelayout = findViewById(R.id.Secondaryservicelayout);
        primaryservicelayout = findViewById(R.id.primaryservicelayout);
        datelayout = findViewById(R.id.datelayout);
        //textViews  Declaration
        Name = findViewById(R.id.Name);
        mobile = findViewById(R.id.mobile);
        pass = findViewById(R.id.pass);
        datePickerDialog = new DatePickerDialog(
                this, SettingActiviy.this, 1990, 1, 1);

        birthdate = findViewById(R.id.birthdate);
        birthdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });
        eye = findViewById(R.id.eye);
        profile_image = findViewById(R.id.profile_image);

        changeImage = findViewById(R.id.changeImage);
        changeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ImagePicker.create(SettingActiviy.this)
                        .folderMode(true) // folder mode (false by default)
                        .toolbarFolderTitle("Folder") // folder selection title
                        .toolbarImageTitle("Tap to select") // image selection title
                        .toolbarArrowColor(Color.BLACK) // Toolbar 'up' arrow color
                        .includeVideo(false) // Show video on image picker
                        .multi() // multi mode (default mode)
                        .limit(1) // max images can be selected (99 by default)
                        .showCamera(true) // show camera or not (true by default)
                        .imageDirectory("Camera") // directory name for captured image  ("Camera" folder by default)
                        .enableLog(false) // disabling log
                        .start(); // start image picker activity with request code

            }
        });

        primaryservice = findViewById(R.id.primaryservice);

        ids2 = new ArrayList<>();
        ids2.add("0");
        ids = new ArrayList<>();
        ids.add("0");
        list = new ArrayList<String>();
        list.add(getString(R.string.Main__Service));
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        primaryservice.setAdapter(dataAdapter);
        primaryservice.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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


        //Seccondry Catgry Spinner


        Secondaryservice = findViewById(R.id.Secondaryservice);

        list2 = new ArrayList<String>();
        list2.add(getString(R.string.Secondary__service));
        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list2);
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Secondaryservice.setAdapter(dataAdapter2);
        Secondaryservice.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()

        {
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


////////

        Signup_Gender = findViewById(R.id.Signup_Gender);
        List<String> list3 = new ArrayList<String>();
        list3.add(getString(R.string.type));
        list3.add(getString(R.string.male));
        list3.add(getString(R.string.female));
        ArrayAdapter<String> dataAdapter3 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list3);
        dataAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Signup_Gender.setAdapter(dataAdapter3);
        Signup_Gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    Sex = "";
                } else if (position == 1) {
                    Sex = "1";
                } else {
                    Sex = "2";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

       /* eye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // pass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                pass.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                pass.setSelected(true);

            }
        });*/
        eye.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    int action = event.getActionMasked();


                    switch (action) {

                        case MotionEvent.ACTION_DOWN:
                            // TODO show password
                            pass.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                            pass.setSelected(true);

                            break;

                        case MotionEvent.ACTION_UP:
                        case MotionEvent.ACTION_OUTSIDE:
                            // TODO mask password
                            pass.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                            pass.setSelected(true);
  /*                          pass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                            pass.setSelected(true);
*/
                            break;
                    }

                    return v.onTouchEvent(event);
                }
                return true;
            }
        });

        if (Hawk.get(Constants.UserType).equals("0")) {
            primaryservicelayout.setVisibility(View.GONE);
            Secondaryservicelayout.setVisibility(View.GONE);
//            datelayout.setVisibility(View.GONE);
        } else {
            primaryservicelayout.setVisibility(View.VISIBLE);
            Secondaryservicelayout.setVisibility(View.VISIBLE);
            datelayout.setVisibility(View.VISIBLE);
        }


        //
        Edit = findViewById(R.id.Edit);
        Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               /* Intent intent = new Intent(SettingActiviy.this, EditProfileActivity.class);
                startActivity(intent);*/
                if (mobile.getText().toString().equals("") || Name.getText().toString().equals("")) {
                    Snackbar.make(parentLayout, "" + getString(R.string.Please_fill_empty_fields), Snackbar.LENGTH_LONG)
                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                            .show();
                } else {
                    edit(mobile.getText().toString(), urlString + ""
                            , userModel.getId(), Name.getText().toString()
                            ,Sex,category_id,subcategory_id,birthdate.getText().toString()+"");
                }//Primary Catgry Spinner

            }
        });
        getCategries();

    }

    private void getUserData(String id) {
        progressDialog.show();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Connectors.connectionServices.BaseURL)
                .addConverterFactory(GsonConverterFactory
                        .create(new Gson())).build();
        Connectors.connectionServices connectionService =
                retrofit.create(Connectors.connectionServices.class);

        connectionService.login(Hawk.get(Constants.password)+"",Hawk.get(Constants.username)+"",Hawk.get(Constants.TOKEN)+"").enqueue(new Callback<UserModelSatus>() {
            @Override
            public void onResponse(Call<UserModelSatus> call, Response<UserModelSatus> response) {
                progressDialog.dismiss();
                statusModel = response.body();
                if (statusModel.getStatus()) {
                    userModel.setAccepted(statusModel.getAccepted()+"");
                    userModel.setPoints(statusModel.getPoints()+"");
                    userModel.setPeople(statusModel.getPeople()+"");
                    userModel.setRejected(statusModel.getRejected()+"");
                    Hawk.put(Constants.userData, statusModel.getUser());
                    if (statusModel.getUser().getImage().equals("")){
                    }else {
                        Picasso.with(SettingActiviy.this).load("http://www.anyservice-ksa.com/prod_img/" + statusModel.getUser().getImage()).into(profile_image);
                    }
                    birthdate.setText(""+statusModel.getUser().getBirthday());

                    if (statusModel.getUser().getRole().equals("2")) {
                        Name.setText(statusModel.getUser().getName());

                        Toast.makeText(SettingActiviy.this, "" + statusModel.getUser().getGender(), Toast.LENGTH_SHORT).show();
                        mobile.setText(statusModel.getUser().getMobile());
                        if (statusModel.getUser().getGender().equals("1")) {
                            Signup_Gender.setSelection(1);
                        } else {
                            Signup_Gender.setSelection(2);

                        }
                        pass.setText(statusModel.getUser().getPassword());

                        primaryservice.setSelection(ids.indexOf(statusModel.getUser().getCategoryId()));
                        getSubCatg(statusModel.getUser().getCategoryId());

                  /*      primaryservice.setText(statusModel.getUser().getCategoryId());
                        Secondaryservice.setText(statusModel.getUser().getSubcategoryId());
                */
                    } else {

                        Name.setText(statusModel.getUser().getName());
                        mobile.setText(statusModel.getUser().getMobile());
                        if (statusModel.getUser().getGender().equals("1"))
                            Signup_Gender.setSelection(1);
                        else
                            Signup_Gender.setSelection(2);
                        pass.setText(statusModel.getUser().getPassword());
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
                getUserData(userModel.getId());
                StatusModel statusModel2 = response.body();
                if (statusModel2.getStatus() == true) {
                    ArrayList<CategoryModel> categoryModels = statusModel2.getCategories();
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
                StatusModel statusModel1 = response.body();
                list2.clear();
                list2.add(getString(R.string.Secondary_service));
                if (statusModel1.getStatus() == true) {
                    ArrayList<SubCategoryModel> subCategoryModels = statusModel1.getSubCategoryModels();
                    if (subCategoryModels.size() > 0) {

                        for (int i = 0; subCategoryModels.size() > i; i++) {
                            ids2.add(subCategoryModels.get(i).getId());

                            if (Locale.getDefault().getDisplayLanguage().equals("ar")) {
                                list2.add(subCategoryModels.get(i).getNameAr() + "");
                            } else {
                                list2.add(subCategoryModels.get(i).getName());
                            }
                        }

                        Log.d("TTT", "onResponse: " + statusModel.getUser().getSubcategoryId());
                        Secondaryservice.setSelection(ids2.indexOf(statusModel.getUser().getSubcategoryId()));

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

    private void edit(String mobile, String img, String id, String name,String gender,String catgryid,String subcategory_id,String bd) {
        progressDialog.show();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Connectors.connectionServices.BaseURL)
                .addConverterFactory(GsonConverterFactory
                        .create(new Gson())).build();
        Connectors.connectionServices connectionService =
                retrofit.create(Connectors.connectionServices.class);

        connectionService.edit(mobile, img, id, name,gender,catgryid,subcategory_id,bd,"").enqueue(new Callback<StatusModel>() {
            @Override
            public void onResponse(Call<StatusModel> call, Response<StatusModel> response) {

                progressDialog.dismiss();
                StatusModel statusModel = response.body();
                if (statusModel.getStatus()) {
                    userModel = Hawk.get(Constants.userData);
                    userModel.setMobile(mobile);
                    userModel.setImage(img);
                    userModel.setName(name);
                    userModel.setGender(gender);
                    Hawk.put(Constants.userData,userModel);
                    Snackbar.make(parentLayout, "" + getString(R.string.Informaion_Updated), Snackbar.LENGTH_LONG)
                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                            .show();
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
                    urlString = (imageUrlModel.getImages().get(0));

            }

            @Override
            public void onFailure(Call<ImageUrlModel> call, Throwable t) {
                progressDialog.dismiss();

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, final int resultCode, Intent data) {
        if (ImagePicker.shouldHandle(requestCode, resultCode, data)) {
            // Get a list of picked images
            // or get a single image only
            Image image = ImagePicker.getFirstImageOrNull(data);
            Bitmap myBitmap = BitmapFactory.decodeFile(image.getPath());
            profile_image.setImageBitmap(myBitmap);
            File file = new File(image.getPath());
            RequestBody reqFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            MultipartBody.Part body = MultipartBody.Part.createFormData("parameters[0]", file.getName(), reqFile);
            uploadPhotoService(body);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        birthdate.setText(dayOfMonth + "/" + month + "/" + year);

    }
}


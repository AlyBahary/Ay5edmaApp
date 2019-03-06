package com.example.smatech.ay5edma;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import com.example.smatech.ay5edma.Models.Modelss.StatusModel;
import com.example.smatech.ay5edma.Models.Modelss.UserModel;
import com.example.smatech.ay5edma.Models.Modelss.UserModelSatus;
import com.example.smatech.ay5edma.Utils.Connectors;
import com.example.smatech.ay5edma.Utils.Constants;
import com.google.gson.Gson;
import com.orhanobut.hawk.Hawk;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EditProfileActivity extends AppCompatActivity {
    String Sex = "";
    LinearLayout birthdateLayout, spinner1Layout, spinner2Layout;
    DatePickerDialog datePickerDialog;
    // Spinner Spinner1,Spinner2;
    TextView name, Login_Mobile, Signup_Gender, Birthdate, mainCatgry, subCatgry;
    UserModel userModel;
    Button EditInfo;
    ProgressDialog progressDialog;
    CircleImageView profile_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        userModel = Hawk.get(Constants.userData);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.Loading));

        ImageView back;
        TextView toolbar_title;
        toolbar_title = findViewById(R.id.toolbar_title);
        toolbar_title.setText(this.getString(R.string.EditProfile) + "");
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
////
        EditInfo = findViewById(R.id.EditInfo);
        name = findViewById(R.id.name);
        Login_Mobile = findViewById(R.id.Login_Mobile);
        Signup_Gender = findViewById(R.id.Signup_Gender);
        Birthdate = findViewById(R.id.Birthdate);
        mainCatgry = findViewById(R.id.mainCatgry);
        subCatgry = findViewById(R.id.subCatgry);
        profile_image = findViewById(R.id.profile_image);
        getUserData(userModel.getId());

/*
        Spinner Signup_Gender;
        Signup_Gender = findViewById(R.id.Signup_Gender);
        List<String> list = new ArrayList<String>();
        list.add(getString(R.string.type));
        list.add(getString(R.string.male));
        list.add(getString(R.string.female));
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Signup_Gender.setAdapter(dataAdapter);
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
*/

        //secion Delete if profiile client
        if (Hawk.get(Constants.UserType).equals("0")) {
            birthdateLayout = findViewById(R.id.birthdateLayout);
            spinner1Layout = findViewById(R.id.spinner1Layout);
            spinner2Layout = findViewById(R.id.spinner2Layout);
            birthdateLayout.setVisibility(View.GONE);
            spinner1Layout.setVisibility(View.GONE);
            spinner2Layout.setVisibility(View.GONE);
        }
        EditInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent i = new Intent(EditProfileActivity.this, SettingActiviy.class);
                startActivity(i);
            }
        });


    }

    private void getUserData(String id) {
        progressDialog.show();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Connectors.connectionServices.BaseURL)
                .addConverterFactory(GsonConverterFactory
                        .create(new Gson())).build();
        Connectors.connectionServices connectionService =
                retrofit.create(Connectors.connectionServices.class);

        connectionService.login(Hawk.get(Constants.password)+"",Hawk.get(Constants.username),Hawk.get(Constants.TOKEN))
                .enqueue(new Callback<UserModelSatus>() {
            @Override
            public void onResponse(Call<UserModelSatus> call, Response<UserModelSatus> response) {
                progressDialog.dismiss();
                UserModelSatus statusModel = response.body();
                if (statusModel.getStatus()) {
                    userModel.setAccepted(statusModel.getAccepted()+"");
                    userModel.setPoints(statusModel.getPoints()+"");
                    userModel.setPeople(statusModel.getPeople()+"");
                    userModel.setRejected(statusModel.getRejected()+"");
                    Hawk.put(Constants.userData, statusModel.getUser());
                    name.setText(statusModel.getUser().getName());
                    Login_Mobile.setText(statusModel.getUser().getMobile());
                    Birthdate.setText(statusModel.getUser().getBirthday());
                    if (statusModel.getUser().getImage().equals("")){

                    }else {
                        Picasso.with(EditProfileActivity.this).load("http://www.anyservice-ksa.com/prod_img/" + statusModel.getUser().getImage()).into(profile_image);
                    }if (statusModel.getUser().getGender().equals("1")) {
                        Signup_Gender.setText(getString(R.string.male));
                    } else {
                        Signup_Gender.setText(getString(R.string.female));

                    }

                    if (statusModel.getUser().getRole().equals("2")) {
                        if(Hawk.contains(Constants.Language)){
                            if(Hawk.get(Constants.Language).equals("ar")){
                                mainCatgry.setText(statusModel.getCategory().getNameAr());
                                subCatgry.setText(statusModel.getSubcategory().getNameAr());
                            }else{
                                mainCatgry.setText(statusModel.getCategory().getName());
                                subCatgry.setText(statusModel.getSubcategory().getName());
                            }

                        }else{
                            mainCatgry.setText(statusModel.getCategory().getName());
                            subCatgry.setText(statusModel.getSubcategory().getName());
                        }

                    } else {

                    }
                }
            }

            @Override
            public void onFailure(Call<UserModelSatus> call, Throwable t) {
                progressDialog.dismiss();

            }
        });
    }
}

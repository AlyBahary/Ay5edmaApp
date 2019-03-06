package com.example.smatech.ay5edma;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smatech.ay5edma.Models.Modelss.StatusModel;
import com.example.smatech.ay5edma.Models.Modelss.UserModel;
import com.example.smatech.ay5edma.Models.Modelss.UserModelSatus;
import com.example.smatech.ay5edma.Utils.Connectors;
import com.example.smatech.ay5edma.Utils.Constants;
import com.google.gson.Gson;
import com.nabinbhandari.android.permissions.PermissionHandler;
import com.nabinbhandari.android.permissions.Permissions;
import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActiivty extends AppCompatActivity {

    EditText Login_Mobile, Login_Password;
    TextView forgetPass, CreateNewAccount, Skip;
    Button Login;
    View parentLayout;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_actiivty);
        Hawk.init(this).build();
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.CALL_PHONE};
        String rationale = "Please provide Some permission so that will help you to get best service";
        Permissions.Options options = new Permissions.Options()
                .setRationaleDialogTitle("Info")
                .setSettingsDialogTitle("Warning");

        Permissions.check(this/*context*/, permissions, rationale, options, new PermissionHandler() {
            @Override
            public void onGranted() {
                if(Hawk.contains(Constants.userData)){
                    finish();
                    startActivity(new Intent(LoginActiivty.this,ClientHomeActivity.class));
                }
                // do your task.
                //Toast.makeText(RegistrtionTypeActivity.this, "Thank you :)", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDenied(Context context, ArrayList<String> deniedPermissions) {
                // permission denied, block the feature.
                finish();
            }
        });

        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.Loading));

        Login_Mobile = findViewById(R.id.Login_Mobile);
        Login_Password = findViewById(R.id.Login_Password);
        forgetPass = findViewById(R.id.forgetPass);
        forgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActiivty.this, ForgetPasswordAcivity.class);
                startActivity(intent);
            }
        });
        CreateNewAccount = findViewById(R.id.CreateNewAccount);
        CreateNewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Hawk.put(Constants.skipe,false);
                Intent i = new Intent(LoginActiivty.this, RegistrtionTypeActivity.class);
                startActivity(i);
            }
        });
        Skip = findViewById(R.id.Skip);
        Skip.setPaintFlags(Skip.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        Skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Hawk.put(Constants.skipe,true);
                Intent i = new Intent(LoginActiivty.this, ClientHomeActivity.class);
                Hawk.put(Constants.UserType,Constants.Client);
                startActivity(i);
            }
        });

         parentLayout = findViewById(android.R.id.content);

        Login = findViewById(R.id.Login);
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Hawk.put(Constants.skipe,false);
                if (Login_Mobile.getText().toString().equals("") || Login_Mobile.getText().toString() == null) {
                    Snackbar.make(parentLayout, "" + getString(R.string.Please_fill_empty_fields), Snackbar.LENGTH_LONG)
                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                            .show();

                } else if (Login_Password.getText().toString().equals("") || Login_Mobile.getText().toString() == null) {
                    Snackbar.make(parentLayout, "" + getString(R.string.Please_fill_empty_fields), Snackbar.LENGTH_LONG)
                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                            .show();
                }else{
                    login(Login_Mobile.getText().toString(),Login_Password.getText().toString(),""+Hawk.get(Constants.TOKEN));
                }

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
                Hawk.put(Constants.password,password);
                Hawk.put(Constants.username,mobile);
                Log.d("TTTT", "onResponse: Response00");
                progressDialog.dismiss();
                UserModelSatus statusModel = response.body();
                if (statusModel.getStatus() == true) {
                    Log.d("TTTT", "onResponse: Response11");
                    UserModel userModel = statusModel.getUser();
                    userModel.setAccepted(statusModel.getAccepted()+"");
                    userModel.setPoints(statusModel.getPoints()+"");
                    userModel.setPeople(statusModel.getPeople()+"");
                    userModel.setRejected(statusModel.getRejected()+"");
                   // Log.d("TTTTTT", "onResponse: "+userModel.getAccepted()+userModel.getPoints()+userModel.getPeople()+userModel.getRejected());
                    Hawk.put(Constants.userData,userModel);
                    Hawk.put(Constants.extraauserData1,statusModel.getCategory());
                    Hawk.put(Constants.extraauserData2,statusModel.getSubcategory());
                    Intent i = new Intent(LoginActiivty.this, ClientHomeActivity.class);
                    finishAffinity();
                    startActivity(i);
                    Toast.makeText(LoginActiivty.this, "" + userModel.getName(), Toast.LENGTH_SHORT).show();
                }else{
                    Log.d("TTTT", "onResponse: Response22");

                    Snackbar.make(parentLayout, "" + statusModel.getMessage(), Snackbar.LENGTH_LONG)
                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                            .show();
                }

            }

            @Override
            public void onFailure(Call<UserModelSatus> call, Throwable t) {
                progressDialog.dismiss();
                Log.d("TTTT", "onResponse: fail"+t.getMessage());


            }
        });
    }
}
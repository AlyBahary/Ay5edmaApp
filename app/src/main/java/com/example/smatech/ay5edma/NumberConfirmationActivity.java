package com.example.smatech.ay5edma;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.chaos.view.PinView;
import com.example.smatech.ay5edma.Models.Modelss.StatusModel;
import com.example.smatech.ay5edma.Models.Modelss.UserModel;
import com.example.smatech.ay5edma.Models.Modelss.UserModelSatus;
import com.example.smatech.ay5edma.Utils.Connectors;
import com.example.smatech.ay5edma.Utils.Constants;
import com.google.gson.Gson;
import com.orhanobut.hawk.Hawk;

import java.util.Locale;

import cn.iwgang.countdownview.CountdownView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NumberConfirmationActivity extends AppCompatActivity {

    PinView firstPinView;
    Button Done;
    View parentLayout;
    ProgressDialog  progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number_confirmation);
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.Loading));
        Hawk.put(Constants.STUCK, "1");
        parentLayout = findViewById(android.R.id.content);
        CountdownView mCvCountdownView = (CountdownView) findViewById(R.id.cv_countdownViewTest1);
        mCvCountdownView.start(59000); // Millisecond


        firstPinView = findViewById(R.id.firstPinView);
        Done = findViewById(R.id.Done);
        Done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (firstPinView.getText().toString().equals("")) {
                } else {
                    verifyAccount(firstPinView.getText().toString() + "");
                }
            }
        });
    }

    private void verifyAccount(String code) {
        progressDialog.show();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Connectors.connectionServices.BaseURL)
                .addConverterFactory(GsonConverterFactory
                        .create(new Gson())).build();
        Connectors.connectionServices connectionService =
                retrofit.create(Connectors.connectionServices.class);

        connectionService.validateAccount(code).enqueue(new Callback<StatusModel>() {
            @Override
            public void onResponse(Call<StatusModel> call, Response<StatusModel> response) {
                Log.d("TTTT", "onResponse: "+response.raw());
                Log.d("TTTT", "onResponse: "+response.message());
                progressDialog.dismiss();
                StatusModel statusModel = response.body();
                if (statusModel.getStatus()) {
                    login(Hawk.get(Constants.username),Hawk.get(Constants.password),Hawk.get(Constants.TOKEN));
                    Hawk.put(Constants.STUCK, "0");

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

        connectionService.login(password, mobile,Hawk.get(Constants.TOKEN), Hawk.get(Constants.loginLat),Hawk.get(Constants.loginLong)).enqueue(new Callback<UserModelSatus>() {
            @Override
            public void onResponse(Call<UserModelSatus> call, Response<UserModelSatus> response) {
                Hawk.put(Constants.password, password);
                Hawk.put(Constants.username, mobile);
                Log.d("TTTT", "onResponse: Response00");
                Hawk.put(Constants.STUCK, "0");
                progressDialog.dismiss();
                UserModelSatus statusModel = response.body();
                UserModel userModel = statusModel.getUser();

                if (statusModel.getStatus() == true ) {
                    Log.d("TTTT", "onResponse: Response11");
                    userModel.setAccepted(statusModel.getAccepted() + "");
                    userModel.setPoints(statusModel.getPoints() + "");
                    userModel.setPeople(statusModel.getPeople() + "");
                    userModel.setRejected(statusModel.getRejected() + "");
                    // Log.d("TTTTTT", "onResponse: "+userModel.getAccepted()+userModel.getPoints()+userModel.getPeople()+userModel.getRejected());
                    Hawk.put(Constants.userData, userModel);
                    Hawk.put(Constants.extraauserData1, statusModel.getCategory());
                    Hawk.put(Constants.extraauserData2, statusModel.getSubcategory());
                    Intent i = new Intent(NumberConfirmationActivity.this, ClientHomeActivity.class);
                    finishAffinity();
                    startActivity(i);


                } else {
                    Log.d("TTTT", "onResponse: Response22");
                    Hawk.put(Constants.STUCK, "0");
                    if (Hawk.get(Constants.Language).equals("English")) {
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

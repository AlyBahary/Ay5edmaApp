package com.example.smatech.ay5edma;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.chaos.view.PinView;
import com.example.smatech.ay5edma.Models.Modelss.StatusModel;
import com.example.smatech.ay5edma.Utils.Connectors;
import com.example.smatech.ay5edma.Utils.Constants;
import com.google.gson.Gson;
import com.orhanobut.hawk.Hawk;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ForgetPasswordAcivity extends AppCompatActivity {

    EditText Forge_Pass_Mobile;
    Button Send;
    private View parentLayout;
    EditText pass;
    PinView firstPinView;
    LinearLayout pass_layout;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password_acivity);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.Loading));
        parentLayout = findViewById(android.R.id.content);
        pass_layout = findViewById(R.id.pass_layout);
        Forge_Pass_Mobile = findViewById(R.id.Forge_Pass_Mobile);
        firstPinView = findViewById(R.id.firstPinView);
        pass = findViewById(R.id.pass);
        Send = findViewById(R.id.Send);
        Send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Send.getText().equals(getString(R.string.changePass))) {
                    if (pass.getText().toString().equals("") && firstPinView.getText().toString().equals("")) {
                        Snackbar.make(parentLayout, "" + getString(R.string.Please_fill_empty_fields), Snackbar.LENGTH_LONG)
                                .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                                .show();
                    } else {
                       // changePass(pass.getText().toString() + "", firstPinView.getText().toString() + "");
                    }
                } else {
                    if (Forge_Pass_Mobile.getText().toString().equals("")) {
                        Snackbar.make(parentLayout, "" + getString(R.string.Please_fill_empty_fields), Snackbar.LENGTH_LONG)
                                .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                                .show();
                    } else {
                        sendMSG(Forge_Pass_Mobile.getText().toString());
                    }
                }

            }
        });
    }

/*
    private void changePass(String num, String pin) {
        progressDialog.show();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Connectors.connectionServices.BaseURL)
                .addConverterFactory(GsonConverterFactory
                        .create(new Gson())).build();
        Connectors.connectionServices connectionService =
                retrofit.create(Connectors.connectionServices.class);

        connectionService.change_password(num, pin).enqueue(new Callback<StatusModel>() {
            @Override
            public void onResponse(Call<StatusModel> call, Response<StatusModel> response) {
                progressDialog.dismiss();
                Log.d("TTT", "onResponse: "+response.toString());
                StatusModel statusModel = response.body();
                if (statusModel.getStatus()) {
                    Hawk.put(Constants.password, num);
                    Toast.makeText(ForgetPasswordAcivity.this, "" + getString(R.string.password_changed)
                            , Toast.LENGTH_SHORT).show();
                    finishAffinity();
                    startActivity(new Intent(ForgetPasswordAcivity.this, LoginActiivty.class));
                } else {

                    if (Hawk.get(Constants.Language).equals("en")) {

                        Snackbar.make(parentLayout, "" + statusModel.getMessage(), Snackbar.LENGTH_LONG)
                                .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                                .show();
                    } else {
                        if (statusModel.getMessage_ar() != null) {
                            Snackbar.make(parentLayout, "" + statusModel.getMessage_ar(), Snackbar.LENGTH_LONG)
                                    .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                                    .show();
                        }else{
                            Snackbar.make(parentLayout, "" + statusModel.getMessage(), Snackbar.LENGTH_LONG)
                                    .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                                    .show();
                        }
                    }

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
*/


    private void sendMSG(String num) {
        progressDialog.show();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Connectors.connectionServices.BaseURL)
                .addConverterFactory(GsonConverterFactory
                        .create(new Gson())).build();
        Connectors.connectionServices connectionService =
                retrofit.create(Connectors.connectionServices.class);

        connectionService.forgetPass(num).enqueue(new Callback<StatusModel>() {
            @Override
            public void onResponse(Call<StatusModel> call, Response<StatusModel> response) {
                progressDialog.dismiss();
                StatusModel statusModel = response.body();
                if (statusModel.getStatus()) {
                    //pass_layout.setVisibility(View.VISIBLE);
                    //firstPinView.setVisibility(View.VISIBLE);
                    //Send.setText(getString(R.string.changePass));
                    startActivity(new Intent(ForgetPasswordAcivity.this, NumberConfirmationForPasswordActivity.class)
                            .putExtra("mob", "" + num));
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
                progressDialog.dismiss();
                Snackbar.make(parentLayout, "" + getString(R.string.noInternetConnecion), Snackbar.LENGTH_LONG)
                        .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                        .show();
            }
        });

    }
}

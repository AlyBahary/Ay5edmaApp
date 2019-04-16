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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smatech.ay5edma.Models.Modelss.StatusModel;
import com.example.smatech.ay5edma.Models.Modelss.UserModel;
import com.example.smatech.ay5edma.Utils.Connectors;
import com.example.smatech.ay5edma.Utils.Constants;
import com.google.gson.Gson;
import com.orhanobut.hawk.Hawk;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChangePasswordActivity extends AppCompatActivity {

    Button Send;
    EditText new_pass,old_pass;
    UserModel userModel;
    private View parentLayout;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        parentLayout = findViewById(android.R.id.content);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        ImageView back;
        TextView toolbar_title;
        toolbar_title=findViewById(R.id.toolbar_title);
        toolbar_title.setText(this.getString(R.string.changePassword)+"");
        back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.Loading));
        userModel = Hawk.get(Constants.userData);
        Send = findViewById(R.id.Send);
        old_pass = findViewById(R.id.old_pass);
        new_pass = findViewById(R.id.new_pass);
        Send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (old_pass.getText().toString() == null && old_pass.getText().toString().equals("")) {
                    Snackbar.make(parentLayout, "" + getString(R.string.Please_fill_empty_fields), Snackbar.LENGTH_LONG)
                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                            .show();
                }else if(new_pass.getText().toString().length() <4){
                    Snackbar.make(parentLayout, "" + getString(R.string.password_must_be_more_than_4), Snackbar.LENGTH_LONG)
                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                            .show();
                }
                else if (new_pass.getText().toString() != null && !new_pass.getText().toString().equals("")) {
                    changePass(userModel.getId(), new_pass.getText().toString(),old_pass.getText().toString());
                }else {
                    Snackbar.make(parentLayout, "" + getString(R.string.Please_fill_empty_fields), Snackbar.LENGTH_LONG)
                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                            .show();

                }
            }
        });

    }

    private void changePass(String id, String pass,String old) {
        progressDialog.show();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Connectors.connectionServices.BaseURL)
                .addConverterFactory(GsonConverterFactory
                        .create(new Gson())).build();
        Connectors.connectionServices connectionService =
                retrofit.create(Connectors.connectionServices.class);

        connectionService.change_password1(id, pass,old).enqueue(new Callback<StatusModel>() {
            @Override
            public void onResponse(Call<StatusModel> call, Response<StatusModel> response) {
                Log.d("TTTT", "onResponse: "+response.toString());
                progressDialog.dismiss();
                StatusModel statusModel = response.body();
                if (statusModel.getStatus()) {
                    Hawk.put(Constants.password, pass);
                    Snackbar.make(parentLayout, "" + getString(R.string.password_changed), Snackbar.LENGTH_LONG)
                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                            .show();
                } else {

                    Snackbar.make(parentLayout, "" + statusModel.getMessage(), Snackbar.LENGTH_LONG)
                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                            .show();


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

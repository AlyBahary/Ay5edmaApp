package com.example.smatech.ay5edma;

import android.app.ProgressDialog;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.smatech.ay5edma.Models.Modelss.StatusModel;
import com.example.smatech.ay5edma.Models.Modelss.UserModel;
import com.example.smatech.ay5edma.Utils.Connectors;
import com.example.smatech.ay5edma.Utils.Constants;
import com.google.gson.Gson;
import com.orhanobut.hawk.Hawk;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ContactUsActivity extends AppCompatActivity {

    UserModel userModel;
    TextView name;
    EditText MSG;
    Button send;
    View parentLayout;
    CircleImageView profile_image;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        profile_image=findViewById(R.id.profile_image);
        parentLayout = findViewById(android.R.id.content);
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.Loading));
        ImageView back;
        TextView toolbar_title;
        toolbar_title=findViewById(R.id.toolbar_title);
        toolbar_title.setText(getString(R.string.Contact_us));
        back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        userModel= Hawk.get(Constants.userData);
        name=findViewById(R.id.name);
        name.setText(userModel.getName());
        if (!userModel.getImage().equals("")) {
            Picasso.with(this).load("http://www.anyservice-ksa.com/prod_img/" + userModel.getImage()).into(profile_image);
        }
        MSG=findViewById(R.id.MSG);
        send=findViewById(R.id.send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!MSG.getText().equals("")){
                    sendMsg(userModel.getId(),MSG.getText()+"");
                }else {

                }
            }
        });
    }
    private void sendMsg(String user_id,String comment){
        progressDialog.show();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Connectors.connectionServices.BaseURL)
                .addConverterFactory(GsonConverterFactory
                        .create(new Gson())).build();
        Connectors.connectionServices connectionService =
                retrofit.create(Connectors.connectionServices.class);
        connectionService.send_feedback(user_id,comment).enqueue(new Callback<StatusModel>() {
            @Override
            public void onResponse(Call<StatusModel> call, Response<StatusModel> response) {
                progressDialog.dismiss();
                StatusModel statusMode=response.body();
                if(statusMode.getStatus()){
                    Snackbar.make(parentLayout, "" + getString(R.string.message_had_been_send), Snackbar.LENGTH_LONG)
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

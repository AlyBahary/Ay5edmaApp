package com.example.smatech.ay5edma;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smatech.ay5edma.Models.Modelss.RequestModel;
import com.example.smatech.ay5edma.Models.Modelss.StatusModel;
import com.example.smatech.ay5edma.Models.Modelss.UserModel;
import com.example.smatech.ay5edma.Utils.Connectors;
import com.example.smatech.ay5edma.Utils.Constants;
import com.google.gson.Gson;
import com.orhanobut.hawk.Hawk;
import com.squareup.picasso.Picasso;

import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ClientRequestDetailsActivity extends AppCompatActivity {

    String requestID,requestBody,flag;
    UserModel fromModel,userModel;
    TextView name,type,occupation,b_d,address,about;
    LinearLayout Reveiws,Location_layout,occupation_section;
    ImageView Accept,Reject;
    me.zhanghai.android.materialratingbar.MaterialRatingBar stars;
    ProgressDialog progressDialog;
    View parentLayout;
    RequestModel mRequest;
    CircleImageView profile_image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_request_details);
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.Loading));

        ImageView back;
        TextView toolbar_title;
        toolbar_title = findViewById(R.id.toolbar_title);
        toolbar_title.setText(getString(R.string.Client_request_details));
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

         flag=getIntent().getStringExtra("flag");
        // bottom nav
        LinearLayout homeLayout,requestLayout,notificationLayout,settingLayout ;
        homeLayout = findViewById(R.id.homeLayout);
        homeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();


            }
        });
        requestLayout = findViewById(R.id.requestLayout);
        requestLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                //startActivity(new Intent(ClientRequestDetailsActivity.this,RequestsActivity.class));
            }
        });
        notificationLayout = findViewById(R.id.notificationLayout);
        notificationLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
                startActivity(new Intent(ClientRequestDetailsActivity.this,NotificationsActivity.class));

            }
        });
        settingLayout = findViewById(R.id.settingLayout);
        settingLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(ClientRequestDetailsActivity.this,SettingActiviy.class));

            }
        });
        //



        fromModel=Hawk.get(Constants.mRequestFrom);

        userModel=Hawk.get(Constants.userData);
        requestID=Hawk.get(Constants.mRequestID);
        requestBody=Hawk.get(Constants.mRequestDesc);
        mRequest=Hawk.get(Constants.mRequest);
        Accept=findViewById(R.id.Accept);
        Accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TTTTTTTT", "onClick: "+userModel.getLongitude());
                sendOffer(fromModel.getId(),userModel.getId(),requestID);
            }
        });
        Reject=findViewById(R.id.Reject);
        Reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        profile_image=findViewById(R.id.profile_image);
        if(fromModel.getImage().equals("")){

        }else{
            Picasso.with(this).load("http://www.anyservice-ksa.com/prod_img/"+fromModel.getImage()).fit().into(profile_image);
        }
        name=findViewById(R.id.name);
        name.setText(" "+fromModel.getName());
        stars=findViewById(R.id.Stars);
        stars.setRating(Float.parseFloat(fromModel.getRate()));
        type=findViewById(R.id.type);
        Log.d("TTT", "onCreate: "+fromModel.getType());
        if(fromModel.getType().equals("0")){
            type.setText(" "+getString(R.string.male));
        }else {
            type.setText(" "+getString(R.string.female));

        }
        if(flag==null){

        }else{
            Reject.setVisibility(View.GONE);
            Accept.setVisibility(View.GONE);
        }
        occupation_section=findViewById(R.id.occupation_section);
        occupation=findViewById(R.id.occupation);
        if(fromModel.getSubcategory()!=null&&fromModel.getSubcategory().getName()!=null ){
            if(Hawk.get(Constants.Language).equals("ar")
                    &&(!fromModel.getSubcategory().getNameAr().equals("")||fromModel.getSubcategory().getNameAr()!=null)){
                occupation.setText(" "+fromModel.getSubcategory().getNameAr());

            }else {
                occupation.setText(" "+fromModel.getSubcategory().getName());

            }

        }else {
            occupation_section.setVisibility(View.GONE);
        }
       // occupation.setText("");
        b_d=findViewById(R.id.b_d);
        b_d.setText(" "+fromModel.getBirthday());
        address=findViewById(R.id.address);
       // address.setText(" "+mRequest.getAddress());
        address.setText(" "+getString(R.string.Show_Location_on_Map));
        address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
           /*     String uri = String.format(Locale.ENGLISH, "geo:%f,%f", Double.parseDouble(mRequest.getLatitude())
                        ,Double.parseDouble(mRequest.getLongitude()));
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                startActivity(intent);
*/
                Uri gmmIntentUri = Uri.parse("google.navigation:q=" + Double.parseDouble(mRequest.getLatitude()) +
                        "," + Double.parseDouble(mRequest.getLongitude()));
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);

             /*   startActivity(new Intent(ClientRequestDetailsActivity.this,MapAcvity.class)
                        .putExtra("address",mRequest.getAddress())
                        .putExtra("lat",mRequest.getLatitude())
                        .putExtra("long",mRequest.getLongitude()));
                Log.d("TTTT", "onMapReady:lat--> "+fromModel.getLatitude());
                Log.d("TTTT", "onMapReady:long--> "+fromModel.getLongitude());
*/
            }
        });
        about=findViewById(R.id.about);
        about.setText(requestBody);
        Location_layout=findViewById(R.id.Location_layout);
        Location_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uri = String.format(Locale.ENGLISH, "geo:%f,%f", Double.parseDouble(mRequest.getLatitude())
                        ,Double.parseDouble(mRequest.getLongitude()));
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                startActivity(intent);
              /*  startActivity(new Intent(ClientRequestDetailsActivity.this,MapAcvity.class)
                        .putExtra("address",fromModel.getAddress())
                        .putExtra("lat",fromModel.getLatitude())
                        .putExtra("long",fromModel.getLongitude()));
                Log.d("TTTT", "onMapReady:lat--> "+fromModel.getLatitude());
                Log.d("TTTT", "onMapReady:long--> "+fromModel.getLongitude());*/
            }
        });
        Reveiws=findViewById(R.id.Reveiws);
        Reveiws.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ClientRequestDetailsActivity.this,ClientReviewsActivity.class)
                        .putExtra("userID",fromModel.getId()));

            }
        });
        parentLayout = findViewById(android.R.id.content);

    }
    private void sendOffer(String userId,String shopId,String requestId){
        progressDialog.show();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Connectors.connectionServices.BaseURL)
                .addConverterFactory(GsonConverterFactory
                        .create(new Gson())).build();
        Connectors.connectionServices connectionService =
                retrofit.create(Connectors.connectionServices.class);

        connectionService.sendOffer(userId,shopId,requestId).enqueue(new Callback<StatusModel>() {
            @Override
            public void onResponse(Call<StatusModel> call, Response<StatusModel> response) {
                Log.d("TTT", "Call: "+response.toString());
                progressDialog.dismiss();
                StatusModel statusModel=response.body();
                if(statusModel.getStatus()){
                    Snackbar.make(parentLayout, "" + getString(R.string.offerhadbeensend), Snackbar.LENGTH_LONG).show();
                    Toast.makeText(ClientRequestDetailsActivity.this
                            , ""+getString(R.string.offerhadbeensend), Toast.LENGTH_SHORT).show();
                    finish();
                    Hawk.put(Constants.reload, "1");
                    startActivity(new Intent(ClientRequestDetailsActivity.this,RequestsActivity.class)
                            .putExtra("stat","1"));

                }else{
                    {
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

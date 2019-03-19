package com.example.smatech.ay5edma;

import android.app.ProgressDialog;
import android.content.Intent;
import android.location.Geocoder;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
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

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import se.arbitur.geocoding.Constants.AddressTypes;
import se.arbitur.geocoding.Constants.LocationTypes;
import se.arbitur.geocoding.Result;
import se.arbitur.geocoding.ReverseGeocoding;

public class RequestDescriptionActivity extends AppCompatActivity {

    TextView catg1, catg2,locatioText;
    CardView Location;
    Button Send;
    EditText Descrption;
    View parentLayout;
    String Address="";
    ProgressDialog progressDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_description);
        ImageView back;
        TextView toolbar_title;
        toolbar_title = findViewById(R.id.toolbar_title);
        toolbar_title.setText(this.getString(R.string.RequesteDescription) + "");
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        parentLayout = findViewById(android.R.id.content);

        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.Loading));


        catg1 = findViewById(R.id.catg1);
        catg2 = findViewById(R.id.catg2);

        catg1.setText(Hawk.get(Constants.SubCat1) + "");
        catg2.setText(Hawk.get(Constants.SubCat2) + "");

        Descrption = findViewById(R.id.Descrption);
        //
        locatioText = findViewById(R.id.locatioText);
        Location = findViewById(R.id.Location);
        Location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RequestDescriptionActivity.this, MapAcvity.class);
                startActivity(i);
            }
        });

        Send = findViewById(R.id.Send);
        Send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Hawk.get(Constants.Addlocationdlat) == null || Hawk.get(Constants.Addlocationdlat).equals("")) {
                    Snackbar.make(parentLayout, "" + getString(R.string.Please_Add_marker_on_the_map), Snackbar.LENGTH_LONG)
                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                            .show();

                } else if (Descrption.getText().toString().equals("")) {
                    Snackbar.make(parentLayout, "" + getString(R.string.Please_Add_Description), Snackbar.LENGTH_LONG)
                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                            .show();

                } else {
                    if (Hawk.contains(Constants.userData)){
                        sendRequest(Descrption.getText().toString());

                        progressDialog.show();
                    }
                    else {
                        Snackbar.make(parentLayout, "" + getString(R.string.You_Have_to_signup_to_make_request), Snackbar.LENGTH_LONG)
                                .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                                .show();

                    }
                }/*
                Intent i=new Intent(RequestDescriptionActivity.this,OffersAcivity.class);
                startActivity(i);
*/
            }
        });
    }

    private void sendRequest(final String Description) {
        new ReverseGeocoding(Double.parseDouble(Hawk.get(Constants.Addlocationdlat) + ""), Double.parseDouble(Hawk.get(Constants.Addlocationdlong) + ""), Constants.API_KEY)
                .setLanguage("sv")
                .setResultTypes()
                .setLocationTypes(LocationTypes.ROOFTOP)
                .isSensor(true)
                .fetch(new se.arbitur.geocoding.Callback() {
                    @Override
                    public void onResponse(se.arbitur.geocoding.Response response) {
                        for (Result result : response.getResults()) {
                            Log.d("TTTTTTT","Complete"+ result.getFormattedAddress());
                            Result.AddressComponent[] component=result.getAddressComponents();

                            Log.d("TTTTTTTTTT", "tesst" + component.length);
                            Log.d("TTTTTTTT", "not Complete" + result.getAddressComponents());
                            Address=component[0].getShortName()
                                    +component[1].getShortName()
                                    +component[2].getShortName()
                                    +component[3].getShortName()+"";
                            break;
                        }
                        UserModel userModel = Hawk.get(Constants.userData);
                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl(Connectors.connectionServices.BaseURL)
                                .addConverterFactory(GsonConverterFactory
                                        .create(new Gson())).build();
                        Connectors.connectionServices connectionService =
                                retrofit.create(Connectors.connectionServices.class);
                        Log.d("TTTTTT", "sendRequest: " + userModel.getId() + "--subID:" +
                                Hawk.get(Constants.mSubCatgrID) + "" +
                                Description + "" + "--MainID:" +
                                Hawk.get(Constants.mCatgrID) + "" +
                                "" +
                                Hawk.get(Constants.Addlocationdlong) + "" +
                                Hawk.get(Constants.Addlocationdlat) + "");
                        connectionService.add_Request(userModel.getId()
                                , Hawk.get(Constants.mSubCatgrID) + ""
                                , Description + "", userModel.getId()
                                , Hawk.get(Constants.mCatgrID) + ""
                                , Address
                                , Hawk.get(Constants.Addlocationdlong) + ""
                                , Hawk.get(Constants.Addlocationdlat) + ""
                        ).enqueue(new Callback<StatusModel>() {
                            @Override
                            public void onResponse(Call<StatusModel> call, Response<StatusModel> response) {progressDialog.dismiss();
                                StatusModel statusModel = response.body();
                                Log.d("TTTTT", "onResponse: "+response.message());
                                if (statusModel.getStatus()) {
                                    Log.d("TTT", "onResponse: true ");
                                    Hawk.put((Constants.Addlocationdlong), "");
                                    Hawk.put((Constants.Addlocationdlat), "");
                                    Snackbar.make(parentLayout, "" + getString(R.string.Your_Request_Had_been_sent), Snackbar.LENGTH_LONG)
                                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                                            .show();
                                    Toast.makeText(RequestDescriptionActivity.this, ""+getString(R.string.Your_Request_Had_been_sent), Toast.LENGTH_SHORT).show();
                                    finish();
                                    startActivity(new Intent(RequestDescriptionActivity.this,RequestsActivity.class).putExtra("stat","1"));
                                } else {

                                    Snackbar.make(parentLayout, "" + getString(R.string.Some_thing_went_wrong_Please_try_again), Snackbar.LENGTH_LONG)
                                            .setAction(getString(R.string.Retry) + "", new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    sendRequest(Description);
                                                    progressDialog.show();
                                                }
                                            })
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
                                Log.d("TTT", "fails:  "+t.getMessage());

                            }
                        });



                    }

                    @Override
                    public void onFailed(se.arbitur.geocoding.Response response, IOException e) {
                        progressDialog.dismiss();

                        Log.d("TTTTTTT", "onFailed: NO Response" + response.getErrorMessage() + " - " + response.getResults().length);
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(Hawk.contains(Constants.Addlocationdlat)&&!Hawk.get(Constants.Addlocationdlat).equals("")){
            locatioText.setText(getString(R.string.locationPicked));
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Hawk.put(Constants.Addlocationdlat,"");
    }
}

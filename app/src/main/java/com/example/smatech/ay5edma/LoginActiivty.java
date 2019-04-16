package com.example.smatech.ay5edma;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Paint;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
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
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;
import com.nabinbhandari.android.permissions.PermissionHandler;
import com.nabinbhandari.android.permissions.Permissions;
import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;
import java.util.Locale;

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
    private FusedLocationProviderClient mFusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_actiivty);
        Hawk.init(this).build();
        //prepare to get location
        mFusedLocationProviderClient = LocationServices
                .getFusedLocationProviderClient(LoginActiivty.this);
        Log.d("TTT", "onCreate: login" + "----" + Locale.getDefault().getLanguage());

        if (!Hawk.contains(Constants.Set) && !Hawk.contains("ft")) {
            if (Locale.getDefault().getLanguage().equals("en")) {
                Hawk.put(Constants.Language, "en");
                languageChange("en", LoginActiivty.this, "", Locale.getDefault().getLanguage());
            } else {
                Hawk.put(Constants.Language, "ar");
                languageChange("ar", LoginActiivty.this, "", Locale.getDefault().getLanguage());

            }
        }
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.CALL_PHONE};
        String rationale = "Please provide Some permission so that will help you to get best service";
        Permissions.Options options = new Permissions.Options()
                .setRationaleDialogTitle("Info")
                .setSettingsDialogTitle("Warning");

        Permissions.check(this, permissions, rationale, options, new PermissionHandler() {
            @Override
            public void onGranted() {
                if (Hawk.contains(Constants.userData) && Hawk.contains(Constants.STUCK)) {
                    if (Hawk.get(Constants.STUCK).equals("0") || true) {
                        finish();
                        startActivity(new Intent(LoginActiivty.this, ClientHomeActivity.class));
                    }
                } else if (Hawk.contains(Constants.userData)) {
                    finish();
                    startActivity(new Intent(LoginActiivty.this, ClientHomeActivity.class));
                }
                getCurrentLocation();
                // do your task.
                //Toast.makeText(RegistrtionTypeActivity.this, "Thank you :)", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDenied(Context context, ArrayList<String> deniedPermissions) {
                // permission denied, block the feature.
                finish();
            }
        });

        progressDialog = new ProgressDialog(this);
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
                Hawk.put(Constants.skipe, false);
                Intent i = new Intent(LoginActiivty.this, RegistrtionTypeActivity.class);
                startActivity(i);
            }
        });
        Skip = findViewById(R.id.Skip);
        Skip.setPaintFlags(Skip.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        Skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Hawk.put(Constants.skipe, true);
                Intent i = new Intent(LoginActiivty.this, ClientHomeActivity.class);
                Hawk.put(Constants.UserType, Constants.Client);
                startActivity(i);
            }
        });

        parentLayout = findViewById(android.R.id.content);

        Login = findViewById(R.id.Login);
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCurrentLocation();
                Hawk.put(Constants.skipe, false);
                if (Login_Mobile.getText().toString().equals("") || Login_Mobile.getText().toString() == null) {
                    Snackbar.make(parentLayout, "" + getString(R.string.Please_fill_empty_fields), Snackbar.LENGTH_LONG)
                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                            .show();

                }/*else if(Login_Mobile.getText().toString().trim().length()!=10){
                    Snackbar.make(parentLayout, "" + getString(R.string.Please_enter_valid_number), Snackbar.LENGTH_LONG)
                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                            .show();
                }*/ else if (Login_Password.getText().toString().equals("") || Login_Mobile.getText().toString() == null) {
                    Snackbar.make(parentLayout, "" + getString(R.string.Please_fill_empty_fields), Snackbar.LENGTH_LONG)
                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                            .show();
                } else if (!Hawk.contains(Constants.loginLat)) {
                    Snackbar.make(parentLayout, "" + getString(R.string.Please_open_GPS), Snackbar.LENGTH_LONG)
                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                            .show();
                } else {
                    login(Login_Mobile.getText().toString(), Login_Password.getText().toString(), "" + Hawk.get(Constants.TOKEN));
                }

            }
        });
        //getCurrentLocation();

    }

    private void login(String mobile, String password, String token) {
        progressDialog.show();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Connectors.connectionServices.BaseURL)
                .addConverterFactory(GsonConverterFactory
                        .create(new Gson())).build();
        Connectors.connectionServices connectionService =
                retrofit.create(Connectors.connectionServices.class);

        connectionService.login(password, mobile, Hawk.get(Constants.TOKEN), Hawk.get(Constants.loginLat), Hawk.get(Constants.loginLong)).enqueue(new Callback<UserModelSatus>() {
            @Override
            public void onResponse(Call<UserModelSatus> call, Response<UserModelSatus> response) {
                Hawk.put(Constants.password, password);
                Hawk.put(Constants.username, mobile);
                Log.d("TTTT", Hawk.get(Constants.TOKEN));
                Log.d("TTTT", "onResponse: Response00"+response.toString());
                progressDialog.dismiss();
                UserModelSatus statusModel = response.body();
                UserModel userModel = statusModel.getUser();

                if (statusModel.getStatus() == true && userModel.getActivate().equals("1")) {
                    Log.d("TTTT", "onResponse: Response11" + userModel.getId());
                    userModel.setAccepted(statusModel.getAccepted() + "");
                    userModel.setPoints(statusModel.getPoints() + "");
                    userModel.setPeople(statusModel.getPeople() + "");
                    userModel.setRejected(statusModel.getRejected() + "");
                    // Log.d("TTTTTT", "onResponse: "+userModel.getAccepted()+userModel.getPoints()+userModel.getPeople()+userModel.getRejected());
                    Hawk.put(Constants.userData, userModel);
                    Hawk.put(Constants.extraauserData1, statusModel.getCategory());
                    Hawk.put(Constants.extraauserData2, statusModel.getCategory());
                    Intent i = new Intent(LoginActiivty.this, ClientHomeActivity.class);
                    finishAffinity();
                    startActivity(i);
                    Toast.makeText(LoginActiivty.this, "" + userModel.getName(), Toast.LENGTH_SHORT).show();
                } else if (statusModel.getActivated() != null && statusModel.getActivated().equals(false)) {
                    startActivity(new Intent(LoginActiivty.this, NumberConfirmationActivity.class));
                } else {
                    {
                        Log.d("TTTT", "onResponse: Response22" + Hawk.get(Constants.Language));
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
            public void onFailure(Call<UserModelSatus> call, Throwable t) {
                progressDialog.dismiss();
                Snackbar.make(parentLayout, "" + getString(R.string.noInternetConnecion), Snackbar.LENGTH_LONG)
                        .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                        .show();
                Log.d("TTTT", "onResponse: fail" + t.getMessage());


            }
        });
    }

    public static final void languageChange(String L, Activity context, String flag, String Language) {
        String languageToLoad = L; // your language
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        context.getBaseContext().getResources().updateConfiguration(config,
                context.getBaseContext().getResources().getDisplayMetrics());
        Intent intent = new Intent(context, LoginActiivty.class);
        //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.finishAffinity();
        context.startActivity(intent);
        Hawk.put("ft", "" + Language);


    }

    private void getCurrentLocation() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this
                , Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Task<Location> locationResult = mFusedLocationProviderClient.getLastLocation();
        locationResult.addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                if (task.isSuccessful()) {
                    if (task.getResult() == null) {
                        //  Toast.makeText(LoginActiivty.this, "Please turn on device location ", Toast.LENGTH_SHORT).show();
                    } else {
                        // Set the map's camera position to the current location of the device.
                        Location location = task.getResult();
                        Log.d("NN", "onComplete: task : " + task.getResult());


                        LatLng currentLatLng = new LatLng(location.getLatitude(),
                                location.getLongitude());

                        Hawk.put(Constants.loginLat, location.getLatitude() + "");
                        Hawk.put(Constants.loginLong, location.getLongitude() + "");


                    }

                }

            }
        });

    }

}

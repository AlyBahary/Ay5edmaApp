package com.example.smatech.ay5edma;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smatech.ay5edma.Models.Modelss.StatusModel;
import com.example.smatech.ay5edma.Models.Modelss.UserModel;
import com.example.smatech.ay5edma.Models.Modelss.UserModelSatus;
import com.example.smatech.ay5edma.Utils.Connectors;
import com.example.smatech.ay5edma.Utils.Constants;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;
import com.orhanobut.hawk.Hawk;
import com.thefinestartist.finestwebview.FinestWebView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import se.arbitur.geocoding.Constants.AddressTypes;
import se.arbitur.geocoding.Constants.LocationTypes;
import se.arbitur.geocoding.Result;
import se.arbitur.geocoding.ReverseGeocoding;

public class SignupAcivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    Spinner Signup_Gender;
    EditText Signup_name, Signup_Mobile, Signup_Password;
    String name, mobile, password, Sex = "0";
    EditText Signup_BD;
    CheckBox Agrement_CheckBox;
    TextView Agreement_text, signupText;
    Button Signup;
    LinearLayout terms_conditions;
    View parentLayout;
    LocationManager locationManager;
    String Address = "";
    Location location1;
    DatePickerDialog datePickerDialog;
    ProgressDialog progressDialog;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    ArrayList<String> strings;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        strings = new ArrayList<>();
        mFusedLocationProviderClient = LocationServices
                .getFusedLocationProviderClient(SignupAcivity.this);
        locationManager = (LocationManager) getSystemService(this.LOCATION_SERVICE);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.Loading));

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                50000,
                50000,
                new MyLocationListener()
        );
        datePickerDialog = new DatePickerDialog(
                this, SignupAcivity.this, 1990, 1, 1);

        Signup_BD = findViewById(R.id.Signup_BD);
        Signup_BD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();

            }
        });

        parentLayout = findViewById(android.R.id.content);
        terms_conditions = findViewById(R.id.terms_conditions);
        terms_conditions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Hawk.contains(Constants.Language)) {
                    if (Hawk.get(Constants.Language).equals("ar")) {
                        new FinestWebView.Builder(SignupAcivity.this).updateTitleFromHtml(false)
                                .titleDefault(getString(R.string.Aboutus)).show("http://anyservice-ksa.com/api/webview?type=terms_ar");

                    } else {
                        new FinestWebView.Builder(SignupAcivity.this).updateTitleFromHtml(false)
                                .titleDefault(getString(R.string.Aboutus)).show("http://anyservice-ksa.com/api/webview?type=terms");
                    }
                } else {
                    new FinestWebView.Builder(SignupAcivity.this).updateTitleFromHtml(false)
                            .titleDefault(getString(R.string.Aboutus)).show("http://anyservice-ksa.com/api/webview?type=terms");

                }
            }
        });
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
                    Sex = "0";
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
        signupText = findViewById(R.id.signupText);

        Signup_name = findViewById(R.id.Signup_name);
        Signup_Mobile = findViewById(R.id.Signup_Mobile);
        Signup_Password = findViewById(R.id.Signup_Password);
        Agrement_CheckBox = findViewById(R.id.Agrement_CheckBox);
        Agreement_text = findViewById(R.id.Agreement_text);
        Agreement_text.setPaintFlags(Agreement_text.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);


        Signup = findViewById(R.id.Signup);
        Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Signup_name.getText().toString().equals("") || Signup_name.getText().toString() == null) {
                    Snackbar.make(parentLayout, "" + getString(R.string.Please_fill_empty_fields), Snackbar.LENGTH_LONG)
                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                            .show();
                } else if (Signup_Password.getText().toString().equals("") || Signup_Password.getText().toString() == null) {
                    Snackbar.make(parentLayout, "" + getString(R.string.Please_fill_empty_fields), Snackbar.LENGTH_LONG)
                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                            .show();
                } else if (Signup_BD.getText().toString().equals("") || Signup_BD.getText().toString() == null) {
                    Snackbar.make(parentLayout, "" + getString(R.string.Please_fill_empty_fields), Snackbar.LENGTH_LONG)
                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                            .show();
                }
                if (Signup_name.getText().toString().equals("") || Signup_name.getText().toString() == null) {
                    Snackbar.make(parentLayout, "" + getString(R.string.Please_fill_empty_fields), Snackbar.LENGTH_LONG)
                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                            .show();
                } else if (Signup_Password.getText().toString().equals("") || Signup_Password.getText().toString() == null) {
                    Snackbar.make(parentLayout, "" + getString(R.string.Please_fill_empty_fields), Snackbar.LENGTH_LONG)
                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                            .show();
                } else if (Signup_Mobile.getText().toString().equals("") || Signup_Mobile.getText().toString() == null) {
                    Snackbar.make(parentLayout, "" + getString(R.string.Please_fill_empty_fields), Snackbar.LENGTH_LONG)
                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                            .show();
                } else if (Sex.equals("0")) {
                    Snackbar.make(parentLayout, "" + getString(R.string.Please_fill_empty_fields), Snackbar.LENGTH_LONG)
                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                            .show();
                } else if (!Agrement_CheckBox.isChecked() && Hawk.get(Constants.UserType).equals("0")) {
                    Snackbar.make(parentLayout, "" + getString(R.string.Please_Agree_terms_Conditions), Snackbar.LENGTH_LONG)
                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                            .show();
                } else {
                    name = Signup_name.getText().toString();
                    password = Signup_Password.getText().toString();
                    mobile = Signup_Mobile.getText().toString();
                    showCurrentLocation();


                }
               /* if (Hawk.get(Constants.UserType).equals("1")) {
                    Intent i = new Intent(SignupAcivity.this, ServiceProviderSingupActivity.class);
                    startActivity(i);

                } else {
                    Intent i = new Intent(SignupAcivity.this, NumberConfirmationActivity.class);
                    startActivity(i);
                }*/
            }
        });

        if (Hawk.get(Constants.UserType).equals("1")) {
            signupText.setText("" + getString(R.string.Servive_provier_Registration));
            terms_conditions.setVisibility(View.GONE);
            Signup.setText(getString(R.string.Next));
        }
    }


    protected void showCurrentLocation() {
        progressDialog.show();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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

                    // Set the map's camera position to the current location of the device.
                    location1 = task.getResult();

                }
            }
        });
        if (location1 != null) {
            Hawk.put(Constants.userLat, location1.getLatitude());
            Hawk.put(Constants.userLong, location1.getLongitude());

            new ReverseGeocoding(Double.parseDouble(Hawk.get(Constants.userLat) + "")
                    , Double.parseDouble(Hawk.get(Constants.userLong) + ""), Constants.API_KEY)
                    .setLanguage("sv")
                    .setResultTypes()
                    .setLocationTypes(LocationTypes.ROOFTOP)
                    .isSensor(true)
                    .fetch(new se.arbitur.geocoding.Callback() {
                        @Override
                        public void onResponse(se.arbitur.geocoding.Response response) {
                            for (Result result : response.getResults()) {
                                Result.AddressComponent[] component = result.getAddressComponents();


                                Log.d("TTTTTTT", "not Complete" + response.getResults(AddressTypes.STREET_ADDRESS));
                                Address = component[0].getShortName()
                                        + component[1].getShortName()
                                        + component[2].getShortName()
                                        + component[3].getShortName() + "";
                                Hawk.put(Constants.userAddress, Address);
                                break;
                            }
                            if (Hawk.get(Constants.UserType).equals("0")) {
                                signup(password, "1", mobile, Signup_BD.getText().toString()
                                        , Sex, name, ""
                                        , "", ""
                                        , Hawk.get(Constants.userLong) + ""
                                        , Hawk.get(Constants.userLat) + "", Address);


                                //  signup(password, "1", mobile, "", Sex, name, "", "");
                            } else {
                                progressDialog.dismiss();
                                Hawk.put(Constants.name, name);
                                Hawk.put(Constants.pass, password);
                                Hawk.put(Constants.mobile, mobile);
                                Hawk.put(Constants.sex, Sex);
                                Hawk.put(Constants.BirthDate, Signup_BD.getText().toString());

                                Intent i = new Intent(SignupAcivity.this, ServiceProviderSingupActivity.class);
                                startActivity(i);

                            }

                        }

                        @Override
                        public void onFailed(se.arbitur.geocoding.Response response, IOException e) {
                            progressDialog.dismiss();
                            Snackbar.make(parentLayout, "" + getString(R.string.noInternetConnecion), Snackbar.LENGTH_LONG)
                                    .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                                    .show();
                        }
                    });

        } else {
            progressDialog.dismiss();
            Snackbar.make(parentLayout, "" + getString(R.string.Please_open_GPS), Snackbar.LENGTH_LONG)
                    .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                    .setAction(getString(R.string.open), new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent I = new Intent(
                                    android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivity(I);
                        }
                    })
                    .show();

        }
    }

    private void signup(String password
            , String role
            , String mobile
            , String birthdate
            , String gender
            , String name
            , String image
            , String subcategory_id
            , String category_id
            , String longitude
            , String latitude
            , String address
    ) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Connectors.connectionServices.BaseURL)
                .addConverterFactory(GsonConverterFactory
                        .create(new Gson())).build();
        Connectors.connectionServices connectionService =
                retrofit.create(Connectors.connectionServices.class);

        connectionService.signup(password, role, mobile, birthdate, gender, name, image
                , subcategory_id, "", category_id, longitude, latitude, address, "").enqueue(new Callback<UserModelSatus>() {
            @Override
            public void onResponse(Call<UserModelSatus> call, Response<UserModelSatus> response) {
                Hawk.put(Constants.password, password);
                Hawk.put(Constants.username, mobile);
                progressDialog.dismiss();
                UserModelSatus statusModel = response.body();
                if (statusModel.getStatus() == true) {
                    Log.d("TTTTT", "onResponse: locaion"+statusModel.getUser().getLatitude());
                    Log.d("TTTTT", "onResponse: locaion"+statusModel.getUser().getLongitude());
                    UserModel userModel = statusModel.getUser();
                    userModel.setAccepted(statusModel.getAccepted() + "");
                    userModel.setPoints(statusModel.getPoints() + "");
                    userModel.setPeople(statusModel.getPeople() + "");
                    userModel.setRejected(statusModel.getRejected() + "");
                    Hawk.put(Constants.userData, userModel);
                    Hawk.put(Constants.extraauserData1, statusModel.getCategory());
                    Hawk.put(Constants.extraauserData2, statusModel.getSubcategory());

                    Intent i = new Intent(SignupAcivity.this, NumberConfirmationActivity.class);
                    startActivity(i);
                    Toast.makeText(SignupAcivity.this, "" + userModel.getName(), Toast.LENGTH_SHORT).show();
                } else {
                    Log.d("TTT", "onResponse: " + Locale.getDefault().getDisplayLanguage());
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
            public void onFailure(Call<UserModelSatus> call, Throwable t) {
                Snackbar.make(parentLayout, "" + getString(R.string.noInternetConnecion), Snackbar.LENGTH_LONG)
                        .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                        .show();
                progressDialog.dismiss();


            }
        });

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Signup_BD.setText(dayOfMonth + "/" + month + "/" + year);

    }


    private class MyLocationListener implements LocationListener {

        public void onLocationChanged(Location location) {
        }

        public void onStatusChanged(String s, int i, Bundle b) {
        }

        public void onProviderDisabled(String s) {
        }

        public void onProviderEnabled(String s) {

        }

    }


}

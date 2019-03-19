package com.example.smatech.ay5edma;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smatech.ay5edma.Utils.Constants;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.orhanobut.hawk.Hawk;

public class MapAcvity extends FragmentActivity implements OnMapReadyCallback , GoogleApiClient.OnConnectionFailedListener  {

    private GoogleMap mMap;
    Marker marker;
    BitmapDescriptor icon;
    ImageView CurrentLocation,ZoomOut,ZoomIn;
    Button Save;
    View parentLayout;

    private PlaceAutocompleteFragment placeAutocompleteFragment;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private static final LatLngBounds LAT_LNG_BOUNDS = new LatLngBounds(
            new LatLng(-40, -168), new LatLng(71, 136));


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_acvity);
        ImageView back;
        icon = BitmapDescriptorFactory.fromResource(R.drawable.map_yellow);
        TextView toolbar_title;
        toolbar_title=findViewById(R.id.toolbar_title);
        toolbar_title.setText(this.getString(R.string.Select_Location)+"");
        back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        parentLayout = findViewById(android.R.id.content);
        Save=findViewById(R.id.Save);
        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Hawk.get(Constants.Addlocationdlat)!=null&&!Hawk.get(Constants.Addlocationdlat).equals("")) {
                    Log.d("TTTTT", "onClick: "+Hawk.get(Constants.Addlocationdlat));
                    finish();
                }else{
                    Snackbar.make(parentLayout, "" + getString(R.string.Please_Add_marker_on_the_map), Snackbar.LENGTH_LONG)
                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                            .show();
                }
            }
        });
        CurrentLocation=findViewById(R.id.CurrentLocation);
        ZoomOut=findViewById(R.id.ZoomOut);
        ZoomIn=findViewById(R.id.ZoomIn);
        //AutoComplete Places Api
        placeAutocompleteFragment = (PlaceAutocompleteFragment) this
                .getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
        placeAutocompleteFragment.getView().setBackgroundResource(R.drawable.searchbar_bg);


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        if(getIntent().getStringExtra("lat")!=null){

            CameraPosition cameraPosition1 = new CameraPosition.Builder()
                    .target(new LatLng(Double.parseDouble(getIntent().getStringExtra("lat"))
                            ,Double.parseDouble(getIntent().getStringExtra("long")))).zoom(12).build();
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition1));
            marker = mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(Double.parseDouble(getIntent().getStringExtra("lat"))
                    ,Double.parseDouble(getIntent().getStringExtra("long"))))
                    .title(getIntent().getStringExtra("address")).icon(icon));
            Log.d("TTTT", "onMapReady:lat--> "+getIntent().getStringExtra("lat"));
            Log.d("TTTT", "onMapReady:long--> "+getIntent().getStringExtra("long"));

            marker.showInfoWindow();
           /* ZoomIn.setVisibility(View.GONE);
            ZoomOut.setVisibility(View.GONE);
          */
           Save.setVisibility(View.GONE);

        }
        //prepare to get location
        mFusedLocationProviderClient = LocationServices
                .getFusedLocationProviderClient(MapAcvity.this);
        //
        CurrentLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCurrentLocation();
            }
        });
        ZoomIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.animateCamera(CameraUpdateFactory.zoomTo(mMap.getCameraPosition().zoom + 0.5f));

            }
        });
        ZoomOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.animateCamera(CameraUpdateFactory.zoomTo(mMap.getCameraPosition().zoom - 0.5f));

            }
        });
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                if (marker != null) {
                    marker.remove();
                }
                marker = mMap.addMarker(new MarkerOptions().position(latLng).title(getString(R.string.CurrentLocatio)).icon(icon));
                Hawk.put(Constants.Addlocationdlat, marker.getPosition().latitude + "");
                Hawk.put(Constants.Addlocationdlong, marker.getPosition().longitude + "");
                CameraPosition cameraPosition1 = new CameraPosition.Builder().target(latLng).zoom(12).build();
                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition1));
                marker.showInfoWindow();

            }
        });
        //Handle google map places auto Complete
        placeAutocompleteFragment.setFilter(new AutocompleteFilter.Builder().setCountry("EG").build());
        placeAutocompleteFragment.setBoundsBias(LAT_LNG_BOUNDS);
        placeAutocompleteFragment.setHint(getString(R.string.Search_for_Place));

        placeAutocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                final LatLng latLngLoc = place.getLatLng();

                if (marker != null) {
                    marker.remove();
                }
                marker = mMap.addMarker(new MarkerOptions().position(latLngLoc).title(place.getName().toString()));
                CameraPosition cameraPosition1 = new CameraPosition.Builder().target(latLngLoc).zoom(12).build();
                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition1));
                marker.showInfoWindow();

            }

            @Override
            public void onError(Status status) {
                Toast.makeText(MapAcvity.this, "" + status.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        LatLng Start_map = new LatLng(33, 33);
        //mMap.addMarker(new MarkerOptions().position(sydney).title(""));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(Start_map));
        getCurrentLocation();
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
                        Toast.makeText(MapAcvity.this, "Please turn on device location ", Toast.LENGTH_SHORT).show();
                    } else {
                        // Set the map's camera position to the current location of the device.
                        Location location = task.getResult();
                        Log.d("NN", "onComplete: task : " + task.getResult());

                        if (marker != null) {
                            marker.remove();
                        }
                        mMap.clear();
                        LatLng currentLatLng = new LatLng(location.getLatitude(),
                                location.getLongitude());

                        marker = mMap.addMarker(new MarkerOptions().position(currentLatLng).title(getString(R.string.CurrentLocatio)).icon(icon));
                        Hawk.put(Constants.Addlocationdlat, marker.getPosition().latitude + "");
                        Hawk.put(Constants.Addlocationdlong, marker.getPosition().longitude + "");

                        //marker.showInfoWindow();
                        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(currentLatLng,
                                15f);
                        Log.d("NN", "onComplete: camera upadte" + update.toString() + location.getLatitude() + " -- " + location.getLongitude());
                        mMap.moveCamera(update);
                    }

                }
                mMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
                    @Override
                    public boolean onMyLocationButtonClick() {
                        getCurrentLocation();
                        return true;
                    }
                });
            }
        });

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}

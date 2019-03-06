package com.example.smatech.ay5edma;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.example.smatech.ay5edma.Adapters.CatgryAdapter;
import com.example.smatech.ay5edma.Adapters.RequestAdapter;
import com.example.smatech.ay5edma.Dialoge.LanguageDialoge;
import com.example.smatech.ay5edma.Models.DummyModel;
import com.example.smatech.ay5edma.Models.Modelss.CategoryModel;
import com.example.smatech.ay5edma.Models.Modelss.StatusModel;
import com.example.smatech.ay5edma.Models.Modelss.SubCategoryModel;
import com.example.smatech.ay5edma.Models.Modelss.UserModel;
import com.example.smatech.ay5edma.Utils.Connectors;
import com.example.smatech.ay5edma.Utils.Constants;
import com.example.smatech.ay5edma.Utils.CustomSliderView;
import com.google.gson.Gson;
import com.orhanobut.hawk.Hawk;
import com.squareup.picasso.Picasso;
import com.thefinestartist.finestwebview.FinestWebView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ClientHomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    //Navigation Views' Views
    LinearLayout ProfileLayout, RequestsLayout, Go,nav_home, nav_Notifications, nav_ContactUs, nav_Language,
            nav_Fav, nav_BE_Serv_provider, nav_Setting, aboutUs, nav_BE_Serv_Client, nav_Buy_Poins, logOut;
    //
    String T;
    PagerIndicator pagerIndicator, pagerIndicator1;
    HashMap<String, String> url_maps;
    private com.daimajia.slider.library.SliderLayout mDemoSlider, mDemoSlider1;
    DrawerLayout drawer;
    ImageView menu;
    ScrollView client_view, Server_view;
    RecyclerView RV, RV2;
    ArrayList<CategoryModel> DM;
    CatgryAdapter catgryAdapter, catgryAdapterSrearch;
    View parentLayout;
    EditText Search;
    RequestAdapter requestAdapter;
    ArrayList<com.example.smatech.ay5edma.Models.Modelss.RequestModel> DM1;
    UserModel userModel;
    ProgressDialog progressDialog;
    TextView name, rate, points, requestesApproved, requestRej, favTo, occupation;
    me.zhanghai.android.materialratingbar.MaterialRatingBar Stars;
    CategoryModel categoryModel;
    CircleImageView profile_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_home);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.Loading));

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        parentLayout = findViewById(android.R.id.content);
        if (Hawk.contains(Constants.userData)) {
            userModel = Hawk.get(Constants.userData);
            if (userModel.getRole().equals("1")) {
                Hawk.put(Constants.UserType, "0");
            } else {
                Hawk.put(Constants.UserType, "1");
            }
        }
        categoryModel = Hawk.get(Constants.extraauserData2);
        // bottom nav
        LinearLayout homeLayout, requestLayout, notificationLayout, settingLayout;
        homeLayout = findViewById(R.id.homeLayout);
        homeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
        requestLayout = findViewById(R.id.requestLayout);
        requestLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Hawk.get(Constants.skipe).equals(true)) {
                    Snackbar.make(parentLayout, "" + getString(R.string.you_have_to_login_to_use_all_app), Snackbar.LENGTH_LONG)
                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                            .show();
                } else {
                    startActivity(new Intent(ClientHomeActivity.this, RequestsActivity.class));

                }
            }
        });
        notificationLayout = findViewById(R.id.notificationLayout);
        notificationLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Hawk.get(Constants.skipe).equals(true)) {
                    Snackbar.make(parentLayout, "" + getString(R.string.you_have_to_login_to_use_all_app), Snackbar.LENGTH_LONG)
                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                            .show();
                } else {
                    startActivity(new Intent(ClientHomeActivity.this, NotificationsActivity.class));

                }

            }
        });
        settingLayout = findViewById(R.id.settingLayout);
        settingLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Hawk.get(Constants.skipe).equals(true)) {
                    Snackbar.make(parentLayout, "" + getString(R.string.you_have_to_login_to_use_all_app), Snackbar.LENGTH_LONG)
                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                            .show();
                } else {
                    startActivity(new Intent(ClientHomeActivity.this, SettingActiviy.class));

                }

            }
        });
        //
        menu = findViewById(R.id.menu);
        client_view = findViewById(R.id.client_view);
        Server_view = findViewById(R.id.Server_view);
        Search = findViewById(R.id.Search);
        Go = findViewById(R.id.Go);
        if (!Hawk.contains(Constants.Language)) {
            Hawk.put(Constants.Language, "en");
        }
        if (Hawk.get(Constants.UserType).equals("0")) {
            client_view.setVisibility(View.VISIBLE);
            Server_view.setVisibility(View.GONE);
            DM = new ArrayList<>();
            RV = findViewById(R.id.Rv);
            catgryAdapter = new CatgryAdapter(DM, this, new CatgryAdapter.OnItemClick() {
                @Override
                public void setOnItemClick(int position) {
                    Intent intent = new Intent(ClientHomeActivity.this, SpecificCatgryActivity.class);
                    startActivity(intent);
                    Hawk.put(Constants.SubCat1, DM.get(position).getName());
                    Hawk.put(Constants.mCatgrID, DM.get(position).getId());

                }
            });
            RV.setAdapter(catgryAdapter);
            RV.setLayoutManager(new GridLayoutManager(this, 3));
            catgryAdapter.notifyDataSetChanged();
            getCategries("");
            Go.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DM.clear();
                    catgryAdapter.notifyDataSetChanged();
                  /*  catgryAdapterSrearch = new CatgryAdapter(DM, ClientHomeActivity.this, new CatgryAdapter.OnItemClick() {
                        @Override
                        public void setOnItemClick(int position) {
                            Hawk.put(Constants.SubCat1, DM.get(position).getName());
                            Hawk.put(Constants.mCatgrID, DM.get(position).getId());
                            Hawk.put(Constants.SubCat2, DM.get(position).getName());
                            Hawk.put(Constants.mSubCatgrID, DM.get(position).getId());
                            // Log.d("TTT", "setOnItemClick: "+DM.get(position).getDesc()+"----"+Hawk.get(Constants.SubCat2)+"");
                            Intent intent = new Intent(ClientHomeActivity.this, RequestDescriptionActivity.class);
                            startActivity(intent);
                            RV.setAdapter(catgryAdapterSrearch);

                        }
                    });*/
                    if (!Search.getText().toString().equals("")) {
                        getCategries(Search.getText().toString());
                    }
                }
            });
        } else {
            /* Drawable drawable = ratingBar.getProgressDrawable();
    drawable.setColorFilter(Color.parseColor("#FFFDEC00"), PorterDuff.Mode.SRC_ATOP);*/
            profile_image = findViewById(R.id.profile_image);
            if (!userModel.getImage().equals("")) {
                Log.d("yyyy", "onCreate: " + userModel.getImage());
                Picasso.with(this).load("http://www.anyservice-ksa.com/prod_img/" + userModel.getImage()).into(profile_image);
            }
            client_view.setVisibility(View.GONE);
            Server_view.setVisibility(View.VISIBLE);
            RV2 = findViewById(R.id.RV2);
            DM1 = new ArrayList<>();
            requestAdapter = new RequestAdapter(DM1, "3", this, ClientHomeActivity.this, new RequestAdapter.OnItemClick() {
                @Override
                public void setOnItemClick(int position) {
                    Intent intent = new Intent(ClientHomeActivity.this, ClientRequestDetailsActivity.class);
                    Hawk.put(Constants.mRequestID, DM1.get(position).getId());
                    Hawk.put(Constants.mRequestFrom, DM1.get(position).getUser());
                    Hawk.put(Constants.mRequestDesc, DM1.get(position).getBody());
                    startActivity(intent);

                }
            });
            RV2.setAdapter(requestAdapter);
            LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            RV2.setLayoutManager(mLayoutManager);
            requestAdapter.notifyDataSetChanged();
            getRequestes("", "1", "", "" + userModel.getSubcategoryId()
                    , "" + userModel.getCategoryId(), "" + userModel.getId());
            name = findViewById(R.id.name);
            name.setText(userModel.getName());
            Stars = findViewById(R.id.Stars);
            Stars.setRating(Float.parseFloat(userModel.getRate()));
            rate = findViewById(R.id.rate);
            rate.setText(userModel.getRate());
            points = findViewById(R.id.points);
            points.setText(userModel.getPoints());
            requestesApproved = findViewById(R.id.requestesApproved);
            requestesApproved.setText(userModel.getAccepted());
            requestRej = findViewById(R.id.requestRej);
            requestRej.setText(userModel.getRejected());
            favTo = findViewById(R.id.favTo);
            favTo.setText(userModel.getPeople());
            occupation = findViewById(R.id.occupation);
            if (Hawk.get(Constants.Language).equals("ar")) {
                occupation.setText(categoryModel.getNameAr());
            } else {
                occupation.setText(categoryModel.getName());

            }


        }


        mDemoSlider1 = (SliderLayout) findViewById(R.id.slider1);
        mDemoSlider = (SliderLayout) findViewById(R.id.slider);
        pagerIndicator = findViewById(R.id.custom_indicator);
        pagerIndicator1 = findViewById(R.id.custom_indicator1);
        url_maps = new HashMap<String, String>();
        get_home_sliders();

        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        toggle.setDrawerIndicatorEnabled(false);
        toolbar.setNavigationIcon(null);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                } else {
                    drawer.openDrawer(GravityCompat.START);
                }


            }
        });

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        ProfileLayout = navigationView.findViewById(R.id.nav_Profile);
        ProfileLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Hawk.get(Constants.skipe).equals(true)) {
                    Snackbar.make(parentLayout, "" + getString(R.string.you_have_to_login_to_use_all_app), Snackbar.LENGTH_LONG)
                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                            .show();
                } else {
                    Intent intent = new Intent(ClientHomeActivity.this, EditProfileActivity.class);
                    startActivity(intent);
                }
            }
        });
        RequestsLayout = navigationView.findViewById(R.id.nav_Requests);
        RequestsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Hawk.get(Constants.skipe).equals(true)) {
                    Snackbar.make(parentLayout, "" + getString(R.string.you_have_to_login_to_use_all_app), Snackbar.LENGTH_LONG)
                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                            .show();
                } else {
                    Intent intent = new Intent(ClientHomeActivity.this, RequestsActivity.class);
                    startActivity(intent);

                }
            }
        });
        nav_Notifications = navigationView.findViewById(R.id.nav_Notifications);
        nav_Notifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Hawk.get(Constants.skipe).equals(true)) {
                    Snackbar.make(parentLayout, "" + getString(R.string.you_have_to_login_to_use_all_app), Snackbar.LENGTH_LONG)
                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                            .show();
                } else {
                    Intent intent = new Intent(ClientHomeActivity.this, NotificationsActivity.class);
                    startActivity(intent);
                }
            }
        });
        nav_home = navigationView.findViewById(R.id.nav_home);
        nav_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);

            }
        });
        nav_ContactUs = navigationView.findViewById(R.id.nav_ContactUs);
        nav_ContactUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Hawk.get(Constants.skipe).equals(true)) {
                    Snackbar.make(parentLayout, "" + getString(R.string.you_have_to_login_to_use_all_app), Snackbar.LENGTH_LONG)
                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                            .show();
                } else {
                    Intent intent = new Intent(ClientHomeActivity.this, ContactUsActivity.class);
                    startActivity(intent);
                }
            }
        });
        nav_Language = navigationView.findViewById(R.id.nav_Language);
        nav_Language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.closeDrawer(GravityCompat.START);
                LanguageDialoge cdd = new LanguageDialoge(ClientHomeActivity.this);
                cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                cdd.show();
            }
        });
        nav_Fav = navigationView.findViewById(R.id.nav_Fav);
        nav_Fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Hawk.get(Constants.skipe).equals(true)) {
                    Snackbar.make(parentLayout, "" + getString(R.string.you_have_to_login_to_use_all_app), Snackbar.LENGTH_LONG)
                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                            .show();
                } else {
                    Intent intent = new Intent(ClientHomeActivity.this, FavouritsActivity.class);
                    startActivity(intent);
                }
            }
        });
        nav_BE_Serv_provider = navigationView.findViewById(R.id.nav_BE_Serv_provider);
        nav_BE_Serv_provider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Hawk.get(Constants.skipe).equals(true)) {
                    Snackbar.make(parentLayout, "" + getString(R.string.you_have_to_login_to_use_all_app), Snackbar.LENGTH_LONG)
                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                            .show();
                } else {
                    finish();
                    ClientHomeActivity.this.finishActivity(1);
                    Intent intent = new Intent(ClientHomeActivity.this, BeServiceProvier.class);
                    startActivity(intent);
                }
            }
        });
        nav_Setting = navigationView.findViewById(R.id.nav_Setting);
        nav_Setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Hawk.get(Constants.skipe).equals(true)) {
                    Snackbar.make(parentLayout, "" + getString(R.string.you_have_to_login_to_use_all_app), Snackbar.LENGTH_LONG)
                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                            .show();
                } else {
                    Intent intent = new Intent(ClientHomeActivity.this, EditProfileActivity.class);
                    startActivity(intent);
                }
            }
        });
        aboutUs = navigationView.findViewById(R.id.aboutUs);
        aboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Hawk.get(Constants.skipe).equals(true)) {
                    Snackbar.make(parentLayout, "" + getString(R.string.you_have_to_login_to_use_all_app), Snackbar.LENGTH_LONG)
                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                            .show();
                } else {

                    if (Hawk.contains(Constants.Language)) {
                        if (Hawk.get(Constants.Language).equals("ar")) {
                            new FinestWebView.Builder(ClientHomeActivity.this).updateTitleFromHtml(false)
                                    .titleDefault(getString(R.string.Aboutus)).show("http://anyservice-ksa.com/api/webview?type=about_ar");

                        } else {
                            new FinestWebView.Builder(ClientHomeActivity.this).updateTitleFromHtml(false)
                                    .titleDefault(getString(R.string.Aboutus)).show("http://anyservice-ksa.com/api/webview?type=about");
                        }
                    } else {
                        new FinestWebView.Builder(ClientHomeActivity.this).updateTitleFromHtml(false)
                                .titleDefault(getString(R.string.Aboutus)).show("http://anyservice-ksa.com/api/webview?type=about");

                    }
                }
            }
        });
        nav_Buy_Poins = navigationView.findViewById(R.id.nav_Buy_Poins);
        nav_Buy_Poins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Hawk.get(Constants.skipe).equals(true)) {
                    Snackbar.make(parentLayout, "" + getString(R.string.you_have_to_login_to_use_all_app), Snackbar.LENGTH_LONG)
                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                            .show();
                } else {
                    Intent intent = new Intent(ClientHomeActivity.this, PackageActivity.class);
                    startActivity(intent);
                }
            }
        });
        logOut = navigationView.findViewById(R.id.logOut);
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Hawk.deleteAll();
                Hawk.destroy();
                startActivity(new Intent(ClientHomeActivity.this, RegistrtionTypeActivity.class));
            }
        });
        nav_BE_Serv_Client = navigationView.findViewById(R.id.nav_BE_Serv_Client);
        nav_BE_Serv_Client.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Hawk.get(Constants.skipe).equals(true)) {
                    Snackbar.make(parentLayout, "" + getString(R.string.you_have_to_login_to_use_all_app), Snackbar.LENGTH_LONG)
                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                            .show();
                } else {
                    userModel.setRole("1");
                    Hawk.put(Constants.userData, userModel);
                    Hawk.put(Constants.UserType, "0");
                    Intent intent = new Intent(ClientHomeActivity.this, ClientHomeActivity.class);
                    finish();
                    startActivity(intent);
                }
            }
        });
        if (Hawk.get(Constants.UserType).equals("0")) {
            nav_BE_Serv_Client.setVisibility(View.GONE);
            nav_Buy_Poins.setVisibility(View.GONE);
            nav_BE_Serv_provider.setVisibility(View.VISIBLE);
        } else {
            nav_BE_Serv_Client.setVisibility(View.VISIBLE);
            nav_BE_Serv_provider.setVisibility(View.GONE);
            nav_Buy_Poins.setVisibility(View.VISIBLE);
        }
        /* */
        ////////
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
     /*   // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.client_home, menu);*/
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void getCategries(String search) {
        progressDialog.show();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Connectors.connectionServices.BaseURL)
                .addConverterFactory(GsonConverterFactory
                        .create(new Gson())).build();
        Connectors.connectionServices connectionService =
                retrofit.create(Connectors.connectionServices.class);

        connectionService.get_Category(search).enqueue(new Callback<StatusModel>() {
            @Override
            public void onResponse(Call<StatusModel> call, Response<StatusModel> response) {
                progressDialog.dismiss();
                StatusModel statusModel = response.body();
                if (statusModel.getStatus() == true) {
                    ArrayList<CategoryModel> categoryModels = statusModel.getCategories();
                    DM.addAll(categoryModels);
                    catgryAdapter.notifyDataSetChanged();
                } else {
                    Snackbar.make(parentLayout, "" + getString(R.string.Couldnt_find) + " " + search + " " + getString(R.string.try_another_word), Snackbar.LENGTH_LONG)
                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                            .show();
                    getCategries("");
                }
            }

            @Override
            public void onFailure(Call<StatusModel> call, Throwable t) {
                progressDialog.dismiss();

            }
        });
    }

    {


    }

    private void getSubCatg(final String search) {
        progressDialog.show();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Connectors.connectionServices.BaseURL)
                .addConverterFactory(GsonConverterFactory
                        .create(new Gson())).build();
        Connectors.connectionServices connectionService =
                retrofit.create(Connectors.connectionServices.class);

        connectionService.get_SubCategory("", search).enqueue(new Callback<StatusModel>() {
            @Override
            public void onResponse(Call<StatusModel> call, Response<StatusModel> response) {
                progressDialog.dismiss();
                StatusModel statusModel = response.body();
                DM.clear();
                if (statusModel.getStatus() == true) {
                    ArrayList<SubCategoryModel> subCategoryModels = statusModel.getSubCategoryModels();

                    for (int i = 0; i < subCategoryModels.size(); i++) {
                        CategoryModel categoryModel = new CategoryModel();
                        categoryModel.setName(subCategoryModels.get(i).getName() + "");
                        categoryModel.setNameAr(subCategoryModels.get(i).getNameAr() + "");
                        categoryModel.setId(subCategoryModels.get(i).getId());
                        categoryModel.setImage(subCategoryModels.get(i).getImage());
                        DM.add(categoryModel);
                    }
                    catgryAdapterSrearch.notifyDataSetChanged();
                } else {
                    Snackbar.make(parentLayout, "" + getString(R.string.Couldnt_find) + " " + search + " " + getString(R.string.try_another_word), Snackbar.LENGTH_LONG)
                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                            .show();
                    getCategries("");

                }

            }

            @Override
            public void onFailure(Call<StatusModel> call, Throwable t) {
                progressDialog.dismiss();

            }
        });

    }

    private void getRequestes(String user_id, final String status, String shop_id, String subcatgry, String Catgry, String filter_id) {
        progressDialog.show();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Connectors.connectionServices.BaseURL)
                .addConverterFactory(GsonConverterFactory
                        .create(new Gson())).build();
        Connectors.connectionServices connectionService =
                retrofit.create(Connectors.connectionServices.class);

        connectionService.get_Requests(user_id, status, shop_id, "" + subcatgry, "" + Catgry, filter_id)
                .enqueue(new Callback<StatusModel>() {
                    @Override
                    public void onResponse(Call<StatusModel> call, Response<StatusModel> response) {
                        progressDialog.dismiss();
                        StatusModel statusModel = response.body();
                        if (statusModel.getStatus()) {
                            DM1.clear();
                            DM1.addAll(statusModel.getRequests());
                            requestAdapter.notifyDataSetChanged();
                        } else {

                        }

                    }

                    @Override
                    public void onFailure(Call<StatusModel> call, Throwable t) {
                        progressDialog.dismiss();

                    }
                });

    }

    //Slider

    private void get_home_sliders() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Connectors.connectionServices.BaseURL)
                .addConverterFactory(GsonConverterFactory
                        .create(new Gson())).build();
        Connectors.connectionServices getRegistrationsConnectionServices =
                retrofit.create(Connectors.connectionServices.class);
        getRegistrationsConnectionServices.get_Sliders().enqueue(new Callback<StatusModel>() {
            @Override
            public void onResponse(Call<StatusModel> call, Response<StatusModel> response) {

                StatusModel statusModel = response.body();
                if (statusModel.getStatus()) {

                    ArrayList<String> Title = new ArrayList<>();
                    for (int i = 0; i < statusModel.getSliders().size(); i++) {
                        T = statusModel.getSliders().get(i).getId();
                       /* if (Title.contains(T)) {
                            Title.add(T+i);
                            T+=i;
                            }else{
                            Title.add(T);

                        }*/

                        url_maps.put("http://www.anyservice-ksa.com/prod_img/" + statusModel.getSliders().get(i).getName(), T);

                    }
                    for (String name : url_maps.keySet()) {
                        CustomSliderView textSliderView = new CustomSliderView(ClientHomeActivity.this);
                        // initialize a SliderLayout
                        textSliderView
                                .description(url_maps.get(name))
                                .image(name)
                                .setScaleType(BaseSliderView.ScaleType.Fit);
                        //add your extra information
                        textSliderView.bundle(new Bundle());
                        textSliderView.getBundle()
                                .putString("extra", name);

                        mDemoSlider.addSlider(textSliderView);
                        mDemoSlider1.addSlider(textSliderView);
                    }
                    mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Default);
                    mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
                    mDemoSlider.setIndicatorVisibility(PagerIndicator.IndicatorVisibility.Visible);
                    mDemoSlider.setCustomAnimation(new DescriptionAnimation());
                    mDemoSlider.setDuration(3000);
                    mDemoSlider.setCustomIndicator(pagerIndicator);
                    mDemoSlider1.setPresetTransformer(SliderLayout.Transformer.Default);
                    mDemoSlider1.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
                    mDemoSlider1.setIndicatorVisibility(PagerIndicator.IndicatorVisibility.Visible);
                    mDemoSlider1.setCustomAnimation(new DescriptionAnimation());
                    mDemoSlider1.setDuration(3000);
                    mDemoSlider1.setCustomIndicator(pagerIndicator1);


                    //setSliderViews(IMGs, Descs);

                }


            }

            @Override
            public void onFailure(Call<StatusModel> call, Throwable t) {

            }
        });

    }

}
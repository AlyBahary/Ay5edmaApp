package com.example.smatech.ay5edma;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.smatech.ay5edma.Adapters.OffersAdapter;
import com.example.smatech.ay5edma.Adapters.SpecialAapterwihSwipe.NotificationsAdapter;
import com.example.smatech.ay5edma.Adapters.SpecialAapterwihSwipe.RecyclerItemTouchListner;
import com.example.smatech.ay5edma.Adapters.SpecialAapterwihSwipe.RecyclerViewSwipeItem;
import com.example.smatech.ay5edma.Models.DummyModel;
import com.example.smatech.ay5edma.Models.Modelss.NotificationModel;
import com.example.smatech.ay5edma.Models.Modelss.StatusModel;
import com.example.smatech.ay5edma.Models.Modelss.UserModel;
import com.example.smatech.ay5edma.Models.Modelss.notficationsModel.Example;
import com.example.smatech.ay5edma.Models.Modelss.notficationsModel.Notification;
import com.example.smatech.ay5edma.Models.OffersDummyModel;
import com.example.smatech.ay5edma.Utils.Connectors;
import com.example.smatech.ay5edma.Utils.Constants;
import com.google.gson.Gson;
import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NotificationsActivity extends AppCompatActivity implements RecyclerItemTouchListner {

    RecyclerView RV;
    ArrayList<Notification> DM;
    NotificationsAdapter notificationsAdapter;
    UserModel userModel;
    ProgressDialog progressDialog;
    private View parentLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        parentLayout = findViewById(android.R.id.content);

        ImageView back;
        TextView toolbar_title;
        toolbar_title = findViewById(R.id.toolbar_title);
        toolbar_title.setText(getString(R.string.Notifications));
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        userModel = Hawk.get(Constants.userData);
        DM = new ArrayList<>();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.Loading));

// bottom nav
        LinearLayout homeLayout, requestLayout, notificationLayout, settingLayout;
        homeLayout = findViewById(R.id.homeLayout);
        homeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(NotificationsActivity.this, ClientHomeActivity.class));


            }
        });
        requestLayout = findViewById(R.id.requestLayout);
        requestLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(NotificationsActivity.this, RequestsActivity.class));
            }
        });
        notificationLayout = findViewById(R.id.notificationLayout);
        notificationLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        settingLayout = findViewById(R.id.settingLayout);
        settingLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(NotificationsActivity.this, SettingActiviy.class));

            }
        });
        //

        RV = findViewById(R.id.RV);


        notificationsAdapter = new NotificationsAdapter(DM, this, new NotificationsAdapter.OnItemClick() {
            @Override
            public void setOnItemClick(int position) {
                /*Intent intent=new Intent(NotificationsActivity.this,ServiceProviderDescription.class);
                startActivity(intent);*/

            }
        });
        RV.setAdapter(notificationsAdapter);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        RV.setLayoutManager(mLayoutManager);
        ItemTouchHelper.SimpleCallback itemTouchHelperL = new RecyclerViewSwipeItem(0, ItemTouchHelper.LEFT, this);
        ItemTouchHelper.SimpleCallback itemTouchHelperR = new RecyclerViewSwipeItem(0, ItemTouchHelper.RIGHT, this);
        new ItemTouchHelper(itemTouchHelperR).attachToRecyclerView(RV);
        new ItemTouchHelper(itemTouchHelperL).attachToRecyclerView(RV);
        notificationsAdapter.notifyDataSetChanged();
        getNotificaions(userModel.getId(),userModel.getRole());


    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int Position) {
        if (viewHolder instanceof NotificationsAdapter.ViewHolder) {
            final Notification Item = DM.get(viewHolder.getAdapterPosition());
            final int deleteIndex = viewHolder.getAdapterPosition();
            notificationsAdapter.removeItem(deleteIndex);
            View parentLayout = findViewById(android.R.id.content);
            DeleteNotficaion("" + Item.getUserId(), "" + Item.getId());
           /* Snackbar.make(parentLayout, "" + getString(R.string.Item_Deleted), Snackbar.LENGTH_LONG)
                    *//*  .setAction(""+getString(R.string.Undo), new View.OnClickListener() {
                          @Override
                          public void onClick(View view) {
                              notificationsAdapter.restoreItem(Item,deleteIndex);
                          }
                      })*//*
                    .setActionTextColor(getResources().getColor(android.R.color.holo_orange_dark))
                    .show();
*/
        }

    }

    private void getNotificaions(String userID,String role) {
        progressDialog.show();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Connectors.connectionServices.BaseURL)
                .addConverterFactory(GsonConverterFactory
                        .create(new Gson())).build();
        Connectors.connectionServices connectionService =
                retrofit.create(Connectors.connectionServices.class);

        connectionService.get_notificaions(userID,role).enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {
                Log.d("TTTTT", "onResponse: "+response.raw());
                progressDialog.dismiss();
                Example statusModel = response.body();
                if (statusModel.getStatus()) {
                    DM.addAll(statusModel.getNotifications());
                    notificationsAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {
                progressDialog.dismiss();
                Log.d("TTTT", "onFailure: "+t.getMessage());

                Snackbar.make(parentLayout, "" + getString(R.string.noInternetConnecion)+"12", Snackbar.LENGTH_LONG)
                        .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                        .show();
            }
        });
    }

    private void DeleteNotficaion(String userID, String ID) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Connectors.connectionServices.BaseURL)
                .addConverterFactory(GsonConverterFactory
                        .create(new Gson())).build();
        Connectors.connectionServices connectionService =
                retrofit.create(Connectors.connectionServices.class);

        connectionService.delete_request(userID, ID).enqueue(new Callback<StatusModel>() {
            @Override
            public void onResponse(Call<StatusModel> call, Response<StatusModel> response) {
                StatusModel statusModel = response.body();
                if (statusModel.getStatus()) {
                    Snackbar.make(parentLayout, "" + getString(R.string.Item_Deleted), Snackbar.LENGTH_LONG)
                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                            .show();

                } else {

                }
            }

            @Override
            public void onFailure(Call<StatusModel> call, Throwable t) {
                Log.d("TTT", "onFailure:1 "+t.getMessage());
                Snackbar.make(parentLayout, "" + getString(R.string.noInternetConnecion)+"000", Snackbar.LENGTH_LONG)
                        .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                        .show();
            }
        });

    }

}

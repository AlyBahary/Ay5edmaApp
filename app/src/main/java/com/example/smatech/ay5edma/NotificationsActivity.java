package com.example.smatech.ay5edma;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
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
                if(DM.get(position).getType().equals("send_offer")){
                    startActivity(new Intent(NotificationsActivity.this,OffersAcivity.class));
                    Hawk.put(Constants.mRequestID,DM.get(position).getRequestId());
                }else if(DM.get(position).getType().equals("update_offer")){
                    startActivity(new Intent(NotificationsActivity.this,RequestsActivity.class));
                }else {
                    startActivity(new Intent(NotificationsActivity.this,ChatActivity.class)
                            .putExtra("to_id",DM.get(position).getFromId()));
                }
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
        getNotificaions(userModel.getId(), userModel.getRole());


    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int Position) {
        if (viewHolder instanceof NotificationsAdapter.ViewHolder) {
            final Notification Item = DM.get(viewHolder.getAdapterPosition());
            final int deleteIndex = viewHolder.getAdapterPosition();

            new AlertDialog.Builder(NotificationsActivity.this)
                    .setTitle("")
                    .setMessage(""+getString(R.string.are_you_sure_you_want_to_delete_this_item))

                    // Specifying a listener allows you to take an action before dismissing the dialog.
                    // The dialog is automatically dismissed when a dialog button is clicked.
                    .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Continue with delete operation
                            Log.d("TTTT", "onClick: "+DM.size());
                            notificationsAdapter.removeItem(deleteIndex);
                            DeleteNotficaion("" + Item.getUserId(), "" + Item.getId());
                            //DM.remove(deleteIndex);
                            Log.d("TTTT", "onClick: "+DM.size());
                        }
                    })

                    // A null listener allows the button to dismiss the dialog and take no further action.
                    .setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            notificationsAdapter.notifyDataSetChanged();
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
            /*

            notificationsAdapter.restoreItem(Item,deleteIndex);
            View parentLayout = findViewById(android.R.id.content);
            Snackbar.make(parentLayout, "" + getString(R.string.Are_you_sure_you_want_to_delete), Snackbar.LENGTH_LONG)
                    .setAction("" + getString(R.string.yes), new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //notificationsAdapter.restoreItem(Item,deleteIndex);
                            notificationsAdapter.removeItem(deleteIndex);
                            DeleteNotficaion("" + Item.getUserId(), "" + Item.getId());

                        }
                    })
                    .setActionTextColor(getResources().getColor(android.R.color.holo_orange_dark))
                    .show();*/

        }

    }

    private void getNotificaions(String userID, String role) {
        progressDialog.show();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Connectors.connectionServices.BaseURL)
                .addConverterFactory(GsonConverterFactory
                        .create(new Gson())).build();
        Connectors.connectionServices connectionService =
                retrofit.create(Connectors.connectionServices.class);

        connectionService.get_notificaions(userID, role).enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {
                Log.d("TTTTT", "onResponse: " + response.raw());
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
                Log.d("TTTT", "onFailure: " + t.getMessage());

                Snackbar.make(parentLayout, "" + getString(R.string.noInternetConnecion) + "12", Snackbar.LENGTH_LONG)
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

        connectionService.delete_notification(userID, ID).enqueue(new Callback<StatusModel>() {
            @Override
            public void onResponse(Call<StatusModel> call, Response<StatusModel> response) {
                Log.d("TTT", "onResponse: "+response.toString());
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
                Log.d("TTT", "onFailure:1 " + t.getMessage());
                Snackbar.make(parentLayout, "" + getString(R.string.noInternetConnecion) + "000", Snackbar.LENGTH_LONG)
                        .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                        .show();
            }
        });

    }

}

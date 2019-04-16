package com.example.smatech.ay5edma;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.smatech.ay5edma.Adapters.ChatAdapter;
import com.example.smatech.ay5edma.Models.ChatModel;
import com.example.smatech.ay5edma.Models.Modelss.StatusModel;
import com.example.smatech.ay5edma.Models.Modelss.UserModel;
import com.example.smatech.ay5edma.Utils.Connectors;
import com.example.smatech.ay5edma.Utils.Constants;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChatActivity extends AppCompatActivity {
    RecyclerView RV;
    ArrayList<com.example.smatech.ay5edma.Models.Modelss.ChatModel> DM;
    ChatAdapter chatAdapter;
    LinearLayout send;
    UserModel userModel;
    EditText message;
    String to_id;
    ProgressDialog progressDialog;

    //--------------//
    FirebaseDatabase database;
    DatabaseReference myRef;
    private View parentLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        parentLayout = findViewById(android.R.id.content);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.Loading));

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Chats");
        ImageView back;
        TextView toolbar_title;
        toolbar_title = findViewById(R.id.toolbar_title);
        toolbar_title.setText(getString(R.string.Chatting));
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        userModel = Hawk.get(Constants.userData);
        DM = new ArrayList<>();
        /*DM.add(new ChatModel("helllllllllllllllllllllllllllllllllllllll" +
                "lllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllll" +
                "lllllllllllllllllllllllllllllllllllllllllllllo","","1","4"));
        DM.add(new ChatModel("","hiii","1","4"));
        DM.add(new ChatModel("hello","","1","4"));
        DM.add(new ChatModel("","hiii","1","4"));
*/
        message = findViewById(R.id.message);
        send = findViewById(R.id.send);
        RV = findViewById(R.id.RV);

        to_id = getIntent().getStringExtra("to_id");


        chatAdapter = new ChatAdapter(DM, this, new ChatAdapter.OnItemClick() {
            @Override
            public void setOnItemClick(int position) {


            }
        });
        RV.setAdapter(chatAdapter);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        RV.setLayoutManager(mLayoutManager);
        chatAdapter.notifyDataSetChanged();
        //to edit later
        getMessages1("0", "" + userModel.getId(), to_id);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (message.getText().toString().trim().equals("")) {

                } else {
                    //to edit
                    SendMSG(message.getText().toString(), userModel.getId(), "" + to_id, "0");

                }

            }
        });
        myRef.child(to_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                getMessages("0", "" + userModel.getId(), to_id);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        myRef.child(userModel.getId()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                getMessages("0", "" + userModel.getId(), to_id);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void getMessages(String chatID, String userID, String to_id) {

        if (isFinishing()) {

        } else {
           // progressDialog.show();
        }
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Connectors.connectionServices.BaseURL)
                .addConverterFactory(GsonConverterFactory
                        .create(new Gson())).build();
        Connectors.connectionServices connectionService =
                retrofit.create(Connectors.connectionServices.class);

        connectionService.get_chat_messages(chatID, userID, to_id).enqueue(new Callback<StatusModel>() {
            @Override
            public void onResponse(Call<StatusModel> call, Response<StatusModel> response) {
                //progressDialog.dismiss();
                StatusModel statusModel = response.body();
                if (statusModel.getStatus()) {
                    DM.clear();
                    DM.addAll(statusModel.getChats());
                    chatAdapter.notifyDataSetChanged();
                    if (DM.size() > 0) {
                        RV.scrollToPosition(DM.size() - 1);
                    }
                } else {

                }
            }

            @Override
            public void onFailure(Call<StatusModel> call, Throwable t) {
                //progressDialog.dismiss();
                Snackbar.make(parentLayout, "" + getString(R.string.noInternetConnecion), Snackbar.LENGTH_LONG)
                        .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                        .show();

            }
        });
    }

    private void SendMSG(String Message, String userID, String to_id, String Chatid) {
        Date currentTime = Calendar.getInstance().getTime();

        DM.add(new com.example.smatech.ay5edma.Models.Modelss.ChatModel(Chatid+""
                ,""+Message
                ,""+to_id
                ,""+userID
                ,""+currentTime
                ,"0"));
        Log.d("TTT", "SendMSG: "+Message);
        chatAdapter.notifyDataSetChanged();


        //send_message
        if (isFinishing()) {

        } else {
            //progressDialog.show();
        }
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Connectors.connectionServices.BaseURL)
                .addConverterFactory(GsonConverterFactory
                        .create(new Gson())).build();
        Connectors.connectionServices connectionService =
                retrofit.create(Connectors.connectionServices.class);

        connectionService.send_message(Message, userID, to_id, Chatid).enqueue(new Callback<StatusModel>() {
            @Override
            public void onResponse(Call<StatusModel> call, Response<StatusModel> response) {
                //progressDialog.dismiss();
                Log.d("TTTT", "onResponse: "+response.raw());
                Log.d("TTTT", "onResponse: "+response.toString());
                StatusModel statusModel = response.body();
                if (statusModel.getStatus()) {
                    /*Intent intent = getIntent();
                    finish();
                    startActivity(intent);*/
                    final int min = 2000;
                    final int max = 8000;
                    final int random = new Random().nextInt((max - min) + 1) + min;
                    myRef.child(userID).setValue(random);
                    myRef.child(to_id).setValue(random);
                    message.setText("");

                }
            }

            @Override
            public void onFailure(Call<StatusModel> call, Throwable t) {
                //progressDialog.dismiss();
                Snackbar.make(parentLayout, "" + getString(R.string.noInternetConnecion), Snackbar.LENGTH_LONG)
                        .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                        .show();
            }
        });
    }
    //get Messages with progress dialoge
    private void getMessages1(String chatID, String userID, String to_id) {

             progressDialog.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Connectors.connectionServices.BaseURL)
                .addConverterFactory(GsonConverterFactory
                        .create(new Gson())).build();
        Connectors.connectionServices connectionService =
                retrofit.create(Connectors.connectionServices.class);

        connectionService.get_chat_messages(chatID, userID, to_id).enqueue(new Callback<StatusModel>() {
            @Override
            public void onResponse(Call<StatusModel> call, Response<StatusModel> response) {
                progressDialog.dismiss();
                StatusModel statusModel = response.body();
                if (statusModel.getStatus()) {
                    DM.clear();
                    DM.addAll(statusModel.getChats());
                    chatAdapter.notifyDataSetChanged();
                    if (DM.size() > 0) {
                        RV.scrollToPosition(DM.size() - 1);
                    }
                } else {

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

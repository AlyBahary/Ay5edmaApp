package com.example.smatech.ay5edma;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.google.gson.Gson;
import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
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
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.Loading));

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
        getMessages("0", ""+userModel.getId(),to_id);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (message.getText().toString().trim().equals("")) {

                } else {
                    //to edit
                    SendMSG(message.getText().toString(), userModel.getId(), ""+to_id, "0");

                }

            }
        });

    }

    private void getMessages(String chatID, String userID,String to_id) {
        progressDialog.show();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Connectors.connectionServices.BaseURL)
                .addConverterFactory(GsonConverterFactory
                        .create(new Gson())).build();
        Connectors.connectionServices connectionService =
                retrofit.create(Connectors.connectionServices.class);

        connectionService.get_chat_messages(chatID, userID,to_id).enqueue(new Callback<StatusModel>() {
            @Override
            public void onResponse(Call<StatusModel> call, Response<StatusModel> response) {
                progressDialog.dismiss();
                StatusModel statusModel = response.body();
                if (statusModel.getStatus()) {
                    DM.addAll(statusModel.getChats());
                    chatAdapter.notifyDataSetChanged();
                } else {

                }
            }

            @Override
            public void onFailure(Call<StatusModel> call, Throwable t) {
                progressDialog.dismiss();

            }
        });
    }

    private void SendMSG(String Message, String userID, String to_id, String Chatid) {
        //send_message
        progressDialog.show();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Connectors.connectionServices.BaseURL)
                .addConverterFactory(GsonConverterFactory
                        .create(new Gson())).build();
        Connectors.connectionServices connectionService =
                retrofit.create(Connectors.connectionServices.class);

        connectionService.send_message(Message, userID, to_id, Chatid).enqueue(new Callback<StatusModel>() {
            @Override
            public void onResponse(Call<StatusModel> call, Response<StatusModel> response) {
                progressDialog.dismiss();
                StatusModel statusModel = response.body();
                if (statusModel.getStatus()) {
                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<StatusModel> call, Throwable t) {
                progressDialog.dismiss();

            }
        });
    }
}

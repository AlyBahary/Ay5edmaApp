package com.example.smatech.ay5edma.Dialoge;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.smatech.ay5edma.ChatActivity;
import com.example.smatech.ay5edma.Models.Modelss.StatusModel;
import com.example.smatech.ay5edma.R;
import com.example.smatech.ay5edma.Utils.Connectors;
import com.google.gson.Gson;

import me.zhanghai.android.materialratingbar.MaterialRatingBar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EvaluationDaialoge extends Dialog {


    public Activity c;
    public Dialog d;
    MaterialRatingBar Stars;
    EditText comment;
    Button Signup;
    String from,user,requestID;
    ProgressDialog progressDialog;
    public EvaluationDaialoge(Activity a,String from,String user,String requestID) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
        this.from= from;
        this.user= user;
        this.requestID= requestID;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.evaluationdialoge);
        progressDialog=new ProgressDialog(c);
        progressDialog.setMessage(c.getString(R.string.Loading));

        Stars=findViewById(R.id.Stars);
        comment=findViewById(R.id.comment);
        Signup=findViewById(R.id.Signup);
        Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Stars.getRating()==0){
                    Toast.makeText(c, ""+c.getString(R.string.pleaseaddRate), Toast.LENGTH_SHORT).show();
                }else {
                    addReview(Stars.getRating()+"",comment.getText().toString(),user,from,requestID);
                }
            }
        });

    }
    private void addReview(String rate,String Comment,String userID,String fromID,String requestID){
        progressDialog.show();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Connectors.connectionServices.BaseURL)
                .addConverterFactory(GsonConverterFactory
                        .create(new Gson())).build();
        Connectors.connectionServices connectionService =
                retrofit.create(Connectors.connectionServices.class);

        connectionService.add_review(""+rate,""+Comment,""+userID,""+fromID,requestID).enqueue(new Callback<StatusModel>() {
            @Override
            public void onResponse(Call<StatusModel> call, Response<StatusModel> response) {
                progressDialog.dismiss();
                Log.d("TTTT", "onResponse: "+rate+"--"+Comment+"--"+userID+""+fromID);
                Log.d("TTTT", "onResponse: "+response.toString());
                Log.d("TTTT", "onResponse: "+call.toString());
                Log.d("TTTT", "onResponse: "+response.message());
                StatusModel statusModel=response.body();
                if(statusModel.getStatus()){
                    d.dismiss();
                    Toast.makeText(c, ""+c.getString(R.string.reviewAddedSuccessfuly), Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(c, ""+statusModel.getMessage(), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<StatusModel> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(c, ""+c.getString(R.string.noInternetConnecion), Toast.LENGTH_SHORT).show();

            }
        });
    }
}

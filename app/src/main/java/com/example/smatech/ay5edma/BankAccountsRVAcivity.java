package com.example.smatech.ay5edma;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.smatech.ay5edma.Adapters.BankAdapter;
import com.example.smatech.ay5edma.Models.Modelss.BankModel;
import com.example.smatech.ay5edma.Models.Modelss.StatusModel;
import com.example.smatech.ay5edma.Models.RequestModel;
import com.example.smatech.ay5edma.Utils.Connectors;
import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BankAccountsRVAcivity extends AppCompatActivity {
    RecyclerView RV;
    ArrayList<BankModel> DM;
    BankAdapter bankAdapter;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_accounts_rvacivity);
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.Loading));

        ImageView back;
        TextView toolbar_title;
        toolbar_title = findViewById(R.id.toolbar_title);
        toolbar_title.setText(getString(R.string.Bank_Accounts));
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        DM = new ArrayList<>();
        RV = findViewById(R.id.RV);


        bankAdapter = new BankAdapter(DM, this, BankAccountsRVAcivity.this, new BankAdapter.OnItemClick() {
            @Override
            public void setOnItemClick(int position) {

                Intent i = new Intent(BankAccountsRVAcivity.this, BankAccountsActivity.class);
                i.putExtra("Bank_name",DM.get(position).getBankName());
                i.putExtra("Bank_ID",DM.get(position).getId());
                startActivity(i);
            }
        });
        RV.setAdapter(bankAdapter);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        RV.setLayoutManager(mLayoutManager);
        bankAdapter.notifyDataSetChanged();
        getBanks();
    }

    private void getBanks(){
        progressDialog.show();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Connectors.connectionServices.BaseURL)
                .addConverterFactory(GsonConverterFactory
                        .create(new Gson())).build();
        Connectors.connectionServices connectionService =
                retrofit.create(Connectors.connectionServices.class);

        connectionService.get_bank_list().enqueue(new Callback<StatusModel>() {
            @Override
            public void onResponse(Call<StatusModel> call, Response<StatusModel> response) {
                progressDialog.dismiss();
                StatusModel statusModel=response.body();
                if(statusModel.getStatus()){
                    DM.addAll(statusModel.getBanks());
                    bankAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<StatusModel> call, Throwable t) {
                progressDialog.dismiss();

            }
        });
    }
}

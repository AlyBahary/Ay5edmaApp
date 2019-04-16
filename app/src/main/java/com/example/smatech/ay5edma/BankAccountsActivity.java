package com.example.smatech.ay5edma;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.smatech.ay5edma.Models.Modelss.StatusModel;
import com.example.smatech.ay5edma.Models.Modelss.UserModel;
import com.example.smatech.ay5edma.Utils.Connectors;
import com.example.smatech.ay5edma.Utils.Constants;
import com.google.gson.Gson;
import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BankAccountsActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    TextView Bank_name;
    String BanlName, id;
    EditText cash, tran_num, Date, Sender;
    Button Signup;
    View parentLayout;
    UserModel userModel;
    ProgressDialog progressDialog;
    DatePickerDialog datePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_accounts);
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.Loading));


        userModel = Hawk.get(Constants.userData);
        ImageView back;
        TextView toolbar_title;
        toolbar_title = findViewById(R.id.toolbar_title);
        toolbar_title.setText(this.getString(R.string.Bank_Accounts) + "");
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        parentLayout = findViewById(android.R.id.content);
        datePickerDialog = new DatePickerDialog(
                this, BankAccountsActivity.this, 2019, 1, 1);
        BanlName = getIntent().getStringExtra("Bank_name");
        id = getIntent().getStringExtra("Bank_ID");
        //Bank-name
        Bank_name = findViewById(R.id.Bank_name);
        Bank_name.setText(BanlName);
        cash = findViewById(R.id.cash);
        tran_num = findViewById(R.id.tran_num);
        Date = findViewById(R.id.Date);
        Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });
        Sender = findViewById(R.id.Sender);
        Signup = findViewById(R.id.Signup);
        Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cash.getText().toString().trim().equals("") || cash.getText().toString() == null) {
                    Snackbar.make(parentLayout, "" + getString(R.string.Please_fill_empty_fields), Snackbar.LENGTH_LONG)
                            .show();
                } else if (tran_num.getText().toString().trim().equals("") || tran_num.getText().toString() == null) {
                    Snackbar.make(parentLayout, "" + getString(R.string.Please_fill_empty_fields), Snackbar.LENGTH_LONG)
                            .show();
                } else if (Date.getText().toString().trim().equals("") || Date.getText().toString() == null) {
                    Snackbar.make(parentLayout, "" + getString(R.string.Please_fill_empty_fields), Snackbar.LENGTH_LONG)
                            .show();
                } else if (Sender.getText().toString().trim().equals("") || Sender.getText().toString() == null) {
                    Snackbar.make(parentLayout, "" + getString(R.string.Please_fill_empty_fields), Snackbar.LENGTH_LONG)
                            .show();
                } else {
                    add_Deposit_Details(userModel.getId(), id, cash.getText().toString()
                            , Date.getText().toString(), Sender.getText().toString(), tran_num.getText().toString());

                }

            }
        });


    }

    private void add_Deposit_Details(String userID
            , String BankID
            , String amount
            , String sendDate
            , String name
            , String number) {
        progressDialog.show();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Connectors.connectionServices.BaseURL)
                .addConverterFactory(GsonConverterFactory
                        .create(new Gson())).build();
        Connectors.connectionServices connectionService =
                retrofit.create(Connectors.connectionServices.class);

        connectionService.add_deposite(userID, BankID, amount, sendDate, name, number).enqueue(new Callback<StatusModel>() {
            @Override
            public void onResponse(Call<StatusModel> call, Response<StatusModel> response) {
                StatusModel statusModel = response.body();
                if (statusModel.getStatus()) {
                    progressDialog.dismiss();
                    if (Hawk.get(Constants.Language).equals("ar")) {
                        Snackbar.make(parentLayout, "" + statusModel.getMessage_ar(), Snackbar.LENGTH_LONG).show();
                    } else {
                        Snackbar.make(parentLayout, "" + statusModel.getMessage(), Snackbar.LENGTH_LONG).show();
                    }
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

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Date.setText(dayOfMonth+"/"+(month+1)+"/"+year);
    }
}

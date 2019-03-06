package com.example.smatech.ay5edma;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.smatech.ay5edma.Utils.Constants;
import com.nabinbhandari.android.permissions.PermissionHandler;
import com.nabinbhandari.android.permissions.Permissions;
import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;
import java.util.Locale;

public class RegistrtionTypeActivity extends AppCompatActivity {

    Button client,Provider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

/*

        typeface = Typeface.createFromAsset(am,
                String.format(Locale.US, "fonts/%s", "be_black.ttf"));
        setTypeface(typeface);
*/

        setContentView(R.layout.activity_registrtion_type);

        client=findViewById(R.id.client);
        client.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(RegistrtionTypeActivity.this,SignupAcivity.class);
                startActivity(intent);
                Hawk.put(Constants.UserType,Constants.Client);

            }
        });
        Provider=findViewById(R.id.Provider);
        Provider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(RegistrtionTypeActivity.this,SignupAcivity.class);
                startActivity(intent);
                Hawk.put(Constants.UserType,Constants.Provider);


            }
        });

    }
}

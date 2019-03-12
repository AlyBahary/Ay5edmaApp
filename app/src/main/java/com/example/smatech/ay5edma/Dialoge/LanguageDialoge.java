package com.example.smatech.ay5edma.Dialoge;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.smatech.ay5edma.ChatActivity;
import com.example.smatech.ay5edma.ClientHomeActivity;
import com.example.smatech.ay5edma.R;
import com.example.smatech.ay5edma.Utils.Constants;
import com.orhanobut.hawk.Hawk;

import java.util.Locale;

public class LanguageDialoge extends Dialog {


    public Activity c;
    public Dialog d;

    LinearLayout English,Arabic;
    public LanguageDialoge(Activity a) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.language_diaoge);
        English=findViewById(R.id.English);
        English.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Hawk.put(Constants.Language,"en");
                Hawk.put(Constants.Set,"1");
                languageChange("en",c,"");
            }
        });
        Arabic=findViewById(R.id.Arabic);
        Arabic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Hawk.put(Constants.Language,"ar");
                Hawk.put(Constants.Set,"1");
                languageChange("ar",c,"");

            }
        });


    }

    public static final void languageChange(String L, Activity context, String flag) {
        String languageToLoad = L; // your language
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        context.getBaseContext().getResources().updateConfiguration(config,
                context.getBaseContext().getResources().getDisplayMetrics());
        Intent intent = new Intent(context, ClientHomeActivity.class);
        //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.finishAffinity();
        context.startActivity(intent);


    }
}

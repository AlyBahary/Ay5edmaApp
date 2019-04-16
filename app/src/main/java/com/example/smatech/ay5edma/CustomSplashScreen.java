package com.example.smatech.ay5edma;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class CustomSplashScreen extends AppCompatActivity {
    private final int SPLASH_DISPLAY_LENGTH = 1000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_splash_screen);
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                Intent mainIntent = new Intent(CustomSplashScreen.this,LoginActiivty.class);
                CustomSplashScreen.this.startActivity(mainIntent);
                CustomSplashScreen.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}

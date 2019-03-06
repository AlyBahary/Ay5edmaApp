package com.example.smatech.ay5edma.Dialoge;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.example.smatech.ay5edma.ChatActivity;
import com.example.smatech.ay5edma.R;

public class EvaluationDaialoge extends Dialog {


    public Activity c;
    public Dialog d;
    public EvaluationDaialoge(Activity a) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.evaluationdialoge);

    }
}

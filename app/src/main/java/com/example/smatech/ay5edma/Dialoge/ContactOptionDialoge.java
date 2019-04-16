package com.example.smatech.ay5edma.Dialoge;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;

import com.example.smatech.ay5edma.ChatActivity;
import com.example.smatech.ay5edma.ClientHomeActivity;
import com.example.smatech.ay5edma.R;
import com.example.smatech.ay5edma.RequestsActivity;

public class ContactOptionDialoge extends Dialog {


    public Activity c;
    public Dialog d;
    String num,to_id;
    CardView Call, Chat;

    public ContactOptionDialoge(Activity a, String num,String to_id) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
        this.num = num;
        this.to_id = to_id;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.contact_option_dialog);
        Call = findViewById(R.id.Call);
        Call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL);
                c.finishAffinity();
                c.startActivity(new Intent(c, ClientHomeActivity.class));
                c.startActivity(new Intent(c, RequestsActivity.class));
                intent.setData(Uri.parse("tel:" + num));
                if (ActivityCompat.checkSelfPermission(c, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                c.startActivity(intent);
            }
        });
        Chat=findViewById(R.id.Chat);
        Chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c.finishAffinity();
                c.startActivity(new Intent(c, ClientHomeActivity.class));
                c.startActivity(new Intent(c, RequestsActivity.class));
                Intent intent=new Intent(c, ChatActivity.class).putExtra("to_id",to_id);
                c.startActivity(intent);
            }
        });
    }
}

package com.example.smatech.ay5edma;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.smatech.ay5edma.Adapters.OffersAdapter;
import com.example.smatech.ay5edma.Adapters.ReveiwsAdapter;
import com.example.smatech.ay5edma.Models.OffersDummyModel;
import com.example.smatech.ay5edma.Models.ReviewModel;

import java.util.ArrayList;

public class ClientReviewsActivity extends AppCompatActivity {

    RecyclerView RV;
    ArrayList<ReviewModel> DM;
    ReveiwsAdapter reveiwsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_reviews);
        ImageView back;
        TextView toolbar_title;
        toolbar_title=findViewById(R.id.toolbar_title);
        toolbar_title.setText(getString(R.string.Client_Reviews));
        back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        DM=new ArrayList<>();
        DM.add(new ReviewModel("Catgr1","Catgry2","12/1/2010","Aly","He is good"));
        DM.add(new ReviewModel("Catgr1","Catgry2","12/1/2010","Aly","He is good"));
        DM.add(new ReviewModel("Catgr1","Catgry2","12/1/2010","Aly","He is good"));
        DM.add(new ReviewModel("Catgr1","Catgry2","12/1/2010","Aly","He is good"));
        DM.add(new ReviewModel("Catgr1","Catgry2","12/1/2010","Aly","He is good"));
        DM.add(new ReviewModel("Catgr1","Catgry2","12/1/2010","Aly","He is good"));
        DM.add(new ReviewModel("Catgr1","Catgry2","12/1/2010","Aly","He is good"));
        DM.add(new ReviewModel("Catgr1","Catgry2","12/1/2010","Aly","He is good"));
        DM.add(new ReviewModel("Catgr1","Catgry2","12/1/2010","Aly","He is good"));
        RV=findViewById(R.id.RV);



       reveiwsAdapter=new ReveiwsAdapter(DM, ClientReviewsActivity.this, new ReveiwsAdapter.OnItemClick() {
           @Override
           public void setOnItemClick(int position) {

           }
       });
        RV.setAdapter(reveiwsAdapter);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        RV.setLayoutManager(mLayoutManager);
        reveiwsAdapter.notifyDataSetChanged();

    }
}

package com.example.smatech.ay5edma.Adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.smatech.ay5edma.Dialoge.EvaluationDaialoge;
import com.example.smatech.ay5edma.Models.RequestModel;
import com.example.smatech.ay5edma.R;
import com.example.smatech.ay5edma.Utils.Constants;
import com.orhanobut.hawk.Hawk;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FavAdapter extends RecyclerView.Adapter<FavAdapter.ViewHolder> {
    private ArrayList<com.example.smatech.ay5edma.Models.Modelss.RequestModel> requestModels;
    private FavAdapter.OnItemClick mOnItemClick;
    private Context context;
    private Activity a;

    public FavAdapter(ArrayList<com.example.smatech.ay5edma.Models.Modelss.RequestModel> requestModels, Context context, Activity a, FavAdapter.OnItemClick mOnItemClick) {
        this.requestModels = requestModels;
        this.mOnItemClick = mOnItemClick;
        this.context = context;
        this.a = a;
    }


    @NonNull
    @Override

    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.fav_item, viewGroup, false);
        return new FavAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        com.example.smatech.ay5edma.Models.Modelss.RequestModel itemMode = requestModels.get(i);
        if(Hawk.get(Constants.Language).equals("ar")){
            viewHolder.Catgry1.setText("" + itemMode.getCategoryNameAr());
            viewHolder.Catgry2.setText("" + itemMode.getSubNameAr());
        }else {
            viewHolder.Catgry1.setText("" + itemMode.getCategoryName());
            viewHolder.Catgry2.setText("" + itemMode.getSubName());
        }

        viewHolder.Date.setText("" + itemMode.getUpdated());
        viewHolder.name.setText("" + itemMode.getShop().getName());
        viewHolder.ReOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

    }

    @Override
    public int getItemCount() {
        return requestModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        LinearLayout ReOrder;
        TextView Catgry1, Catgry2, Date, name;

        ImageView fav;

        public ViewHolder(View itemView) {
            super(itemView);
            Catgry1 = itemView.findViewById(R.id.catgr1);
            Catgry2 = itemView.findViewById(R.id.catgr2);
            Date = itemView.findViewById(R.id.Date);
            name = itemView.findViewById(R.id.Name);
            ReOrder = itemView.findViewById(R.id.ReOrder);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mOnItemClick.setOnItemClick(getAdapterPosition());
        }
    }

    public interface OnItemClick {
        void setOnItemClick(int position);
    }
}

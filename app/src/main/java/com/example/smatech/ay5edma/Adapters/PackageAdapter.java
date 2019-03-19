package com.example.smatech.ay5edma.Adapters;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.smatech.ay5edma.Models.Modelss.PointsModel;
import com.example.smatech.ay5edma.Models.RequestModel;
import com.example.smatech.ay5edma.R;
import com.example.smatech.ay5edma.Utils.Constants;
import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;

public class PackageAdapter extends RecyclerView.Adapter<PackageAdapter.ViewHolder> {
    private ArrayList<PointsModel> requestModels;
    private PackageAdapter.OnItemClick mOnItemClick;
    private Context context;
    private Activity a;

    public PackageAdapter(ArrayList<PointsModel> requestModels, Context context, Activity a, PackageAdapter.OnItemClick mOnItemClick) {
        this.requestModels = requestModels;
        this.mOnItemClick = mOnItemClick;
        this.context = context;
        this.a = a;
    }


    @NonNull
    @Override

    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.packages_item, viewGroup, false);
        return new PackageAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        PointsModel itemMode = requestModels.get(i);
        if(Hawk.get(Constants.Language).equals("ar")){
            viewHolder.packageName.setText(itemMode.getNameAr());
        }else {
            viewHolder.packageName.setText(itemMode.getName());

        }
        viewHolder.price.setText(itemMode.getPrice());
        viewHolder.points.setText(itemMode.getPoints());

    }

    @Override
    public int getItemCount() {
        return requestModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView points, price,packageName;
        Button buy;


        public ViewHolder(View itemView) {
            super(itemView);
            price = itemView.findViewById(R.id.price);
            points = itemView.findViewById(R.id.points);
            packageName = itemView.findViewById(R.id.packageName);
            buy = itemView.findViewById(R.id.buy);
            buy.setOnClickListener(this);
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

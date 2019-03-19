package com.example.smatech.ay5edma.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.smatech.ay5edma.Models.DummyModel;
import com.example.smatech.ay5edma.Models.Modelss.CategoryModel;
import com.example.smatech.ay5edma.R;
import com.example.smatech.ay5edma.Utils.Constants;
import com.orhanobut.hawk.Hawk;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Locale;

public class CatgryAdapter extends RecyclerView.Adapter<CatgryAdapter.ViewHolder> {
    private ArrayList<CategoryModel> categoryModels;
    private CatgryAdapter.OnItemClick mOnItemClick;
    private Context context;

    public CatgryAdapter(ArrayList<CategoryModel> categoryModels, Context context, CatgryAdapter.OnItemClick mOnItemClick) {
        this.categoryModels = categoryModels;
        this.mOnItemClick = mOnItemClick;
        this.context = context;
    }


    @NonNull
    @Override

    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.catgry_item, viewGroup, false);
        return new CatgryAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        CategoryModel itemMode = categoryModels.get(i);
        if (Hawk.get(Constants.Language).equals("ar")) {
            viewHolder.TextTitle.setText(""+itemMode.getNameAr());
        } else {
            viewHolder.TextTitle.setText(""+itemMode.getName());
        }
        Picasso.with(context).load(itemMode.getImage()).into(viewHolder.ImageType);
        }

    @Override
    public int getItemCount() {
        return categoryModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView ImageType;
        TextView TextTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            ImageType = itemView.findViewById(R.id.image);
            TextTitle = itemView.findViewById(R.id.Desc);
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

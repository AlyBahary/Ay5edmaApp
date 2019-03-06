package com.example.smatech.ay5edma.Adapters;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.smatech.ay5edma.Models.Modelss.BankModel;
import com.example.smatech.ay5edma.Models.RequestModel;
import com.example.smatech.ay5edma.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class BankAdapter extends RecyclerView.Adapter<BankAdapter.ViewHolder> {
    private ArrayList<BankModel> bankModels;
    private BankAdapter.OnItemClick mOnItemClick;
    private Context context;
    private Activity a;

    public BankAdapter(ArrayList<BankModel> bankModels, Context context, Activity a, BankAdapter.OnItemClick mOnItemClick) {
        this.bankModels = bankModels;
        this.mOnItemClick = mOnItemClick;
        this.context = context;
        this.a = a;
    }


    @NonNull
    @Override

    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.bank_item, viewGroup, false);
        return new BankAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        BankModel itemMode = bankModels.get(i);
        viewHolder.Bank_name.setText(itemMode.getBankName());
        viewHolder.accNum.setText(itemMode.getBankAccount());
        viewHolder.ibanNum.setText(itemMode.getBankIpan());
        if(viewHolder.Bank_img.equals("")){
            Picasso.with(context).load("hfbvdbshj").into(viewHolder.Bank_img);

        }else{
            Picasso.with(context).load("http://www.anyservice-ksa.com/prod_img/"+itemMode.getImage()).into(viewHolder.Bank_img);

        }

    }

    @Override
    public int getItemCount() {
        return bankModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView name, accNum, ibanNum,Bank_name;
        com.github.siyamed.shapeimageview.RoundedImageView Bank_img;
        ImageView fav;

        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            accNum = itemView.findViewById(R.id.accNum);
            ibanNum = itemView.findViewById(R.id.ibanNum);
            Bank_img = itemView.findViewById(R.id.Bank_img);
            Bank_name = itemView.findViewById(R.id.Bank_name);

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

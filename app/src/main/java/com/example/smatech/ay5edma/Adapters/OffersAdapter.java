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
import com.example.smatech.ay5edma.Models.Modelss.OffersModel;
import com.example.smatech.ay5edma.Models.OffersDummyModel;
import com.example.smatech.ay5edma.Models.offerModel.example.Offer;
import com.example.smatech.ay5edma.R;
import com.github.siyamed.shapeimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class OffersAdapter extends RecyclerView.Adapter<OffersAdapter.ViewHolder> {
    private ArrayList<Offer> offersModels;
    private OffersAdapter.OnItemClick mOnItemClick;
    private Context context;

    public OffersAdapter(ArrayList<Offer> offersModels, Context context, OffersAdapter.OnItemClick mOnItemClick) {
        this.offersModels = offersModels;
        this.mOnItemClick = mOnItemClick;
        this.context = context;
    }


    @NonNull
    @Override

    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.offer_item, viewGroup, false);
        return new OffersAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Offer itemMode = offersModels.get(i);
        viewHolder.name.setText("" + itemMode.getFrom().getName());
        viewHolder.distance.setText("20 km");
        viewHolder.distance.setVisibility(View.GONE);
        viewHolder.Stars.setNumStars(5);
        viewHolder.Stars.setRating(Float.parseFloat(itemMode.getFrom().getRate()));
        if (itemMode.getFrom().getImage().equals("") || itemMode.getFrom().getImage() == null) {
            Picasso.with(context).load(R.drawable.man_b).fit().placeholder(R.drawable.man_b).into(viewHolder.image);
        } else {
            Picasso.with(context).load("http://www.anyservice-ksa.com/prod_img/"+itemMode.getFrom().getImage()).fit().placeholder(R.drawable.man_b).into(viewHolder.image);
        }
    }

    @Override
    public int getItemCount() {
        return offersModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        RoundedImageView image;
        TextView name, distance;
        me.zhanghai.android.materialratingbar.MaterialRatingBar Stars;

        public ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            name = itemView.findViewById(R.id.name);
            distance = itemView.findViewById(R.id.distance);
            Stars = itemView.findViewById(R.id.Stars);
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

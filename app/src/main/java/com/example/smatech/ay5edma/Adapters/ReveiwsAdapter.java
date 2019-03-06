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
import com.example.smatech.ay5edma.Models.ReviewModel;
import com.example.smatech.ay5edma.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ReveiwsAdapter extends RecyclerView.Adapter<ReveiwsAdapter.ViewHolder> {
    private ArrayList<ReviewModel> reviewModels;
    private ReveiwsAdapter.OnItemClick mOnItemClick;
    private Context context;

    public ReveiwsAdapter(ArrayList<ReviewModel> reviewModels, Context context, ReveiwsAdapter.OnItemClick mOnItemClick) {
        this.reviewModels = reviewModels;
        this.mOnItemClick = mOnItemClick;
        this.context = context;
    }


    @NonNull
    @Override

    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.review_item, viewGroup, false);
        return new ReveiwsAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        ReviewModel itemMode = reviewModels.get(i);
        viewHolder.Ctgry1.setText(""+itemMode.getCtgry1()+"-"+itemMode.getCtgry2());
        viewHolder.time.setText(""+itemMode.getTime());
        viewHolder.Name.setText(""+itemMode.getName());
        viewHolder.Comment.setText(""+itemMode.getComment());
        viewHolder.Stars.setNumStars(5);
        viewHolder.Stars.setRating(4);
        }

    @Override
    public int getItemCount() {
        return reviewModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {



        TextView Ctgry1;
        TextView time;
        TextView Name;
        TextView Comment;
        me.zhanghai.android.materialratingbar.MaterialRatingBar Stars;

        public ViewHolder(View itemView) {
            super(itemView);
            Ctgry1 = itemView.findViewById(R.id.Service_Catgry);
            Comment = itemView.findViewById(R.id.Comment);
            Name = itemView.findViewById(R.id.name);
            time = itemView.findViewById(R.id.Time);
            Stars=itemView.findViewById(R.id.Stars);
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

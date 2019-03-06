package com.example.smatech.ay5edma.Adapters.SpecialAapterwihSwipe;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.smatech.ay5edma.Models.DummyModel;
import com.example.smatech.ay5edma.Models.Modelss.NotificationModel;
import com.example.smatech.ay5edma.R;
import com.example.smatech.ay5edma.Utils.Constants;
import com.orhanobut.hawk.Hawk;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.ViewHolder> {
    private ArrayList<NotificationModel> dummyModels;
    private NotificationsAdapter.OnItemClick mOnItemClick;
    private Context context;

    public NotificationsAdapter(ArrayList<NotificationModel> dummyModels, Context context, NotificationsAdapter.OnItemClick mOnItemClick) {
        this.dummyModels = dummyModels;
        this.mOnItemClick = mOnItemClick;
        this.context = context;
    }


    @NonNull
    @Override

    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.notification_item, viewGroup, false);
        return new NotificationsAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        NotificationModel itemMode = dummyModels.get(i);
        if (Hawk.get(Constants.Language).equals("ar")) {
            viewHolder.Title.setText(itemMode.getTitleAr());
            viewHolder.ctgry1.setText(""+itemMode.getCategory().getNameAr());
            viewHolder.ctgry2.setText(""+itemMode.getSubcategory().getNameAr());
        } else {
            viewHolder.Title.setText(itemMode.getTitle());
            viewHolder.ctgry1.setText(""+itemMode.getCategory().getName());
            viewHolder.ctgry2.setText(""+itemMode.getSubcategory().getName());
        }

        viewHolder.name.setText(itemMode.getFrom().getName());
        viewHolder.Date.setText(itemMode.getCreated());
    }

    @Override
    public int getItemCount() {
        return dummyModels.size();
    }

    public void removeItem(int Position) {
        dummyModels.remove(Position);
        notifyItemRemoved(Position);
    }

    public void restoreItem(NotificationModel DM, int Position) {
        dummyModels.add(Position, DM);
        notifyItemRemoved(Position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView ctgry1, ctgry2, name, Date, Title;
        LinearLayout forground_view;

        public ViewHolder(View itemView) {
            super(itemView);
            ctgry1 = itemView.findViewById(R.id.ctgry1);
            ctgry2 = itemView.findViewById(R.id.ctgry2);
            Title = itemView.findViewById(R.id.Title);
            name = itemView.findViewById(R.id.name);
            Date = itemView.findViewById(R.id.Date);
            forground_view = itemView.findViewById(R.id.forground_view);
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

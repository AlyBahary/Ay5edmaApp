package com.example.smatech.ay5edma.Adapters;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.smatech.ay5edma.Models.ChatModel;
import com.example.smatech.ay5edma.Models.DummyModel;
import com.example.smatech.ay5edma.Models.Modelss.UserModel;
import com.example.smatech.ay5edma.R;
import com.example.smatech.ay5edma.Utils.Constants;
import com.orhanobut.hawk.Hawk;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {
    private ArrayList<com.example.smatech.ay5edma.Models.Modelss.ChatModel> chatModels;
    private ChatAdapter.OnItemClick mOnItemClick;
    private Context context;
    String x = "";
    UserModel userModel= Hawk.get(Constants.userData);

    public ChatAdapter(ArrayList<com.example.smatech.ay5edma.Models.Modelss.ChatModel> chatModels, Context context, ChatAdapter.OnItemClick mOnItemClick) {
        this.chatModels = chatModels;
        this.mOnItemClick = mOnItemClick;
        this.context = context;
    }


    @NonNull
    @Override

    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.chat_item, viewGroup, false);
        return new ChatAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        com.example.smatech.ay5edma.Models.Modelss.ChatModel itemMode = chatModels.get(i);
       // DateFormat dateFormat = new SimpleDateFormat("hh:mm a");

        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
        String currentDateandTime = sdf.format(new Date());

        if (x == ""||x.equals("")) {
            x = currentDateandTime;
            viewHolder.Date.setText(itemMode.getDate());
        }else {
            viewHolder.Date.setVisibility(View.GONE);
        }

        //  Picasso.with(context).load("1").into(viewHolder.ImageType);
        if (itemMode.getToId().equals(userModel.getId())) {
            viewHolder.MsG.setText(itemMode.getMessage() + "");
            viewHolder.MsG.setGravity(Gravity.END);

        } else {
            viewHolder.MsG.setText(itemMode.getMessage() + "");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                viewHolder.relaiveLayout.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

            }

        }
    }

    @Override
    public int getItemCount() {
        return chatModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        RelativeLayout relaiveLayout;
        TextView Date, MsG;
        ImageView deliverd;
        CircleImageView Avatar;

        public ViewHolder(View itemView) {
            super(itemView);
            relaiveLayout = itemView.findViewById(R.id.RelaiveLayout);
            Date = itemView.findViewById(R.id.Date);
            MsG = itemView.findViewById(R.id.MsG);
            Avatar = itemView.findViewById(R.id.Avatar);
            deliverd = itemView.findViewById(R.id.deliverd);
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

package com.example.smatech.ay5edma.Adapters;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smatech.ay5edma.ChatActivity;
import com.example.smatech.ay5edma.Dialoge.ContactOptionDialoge;
import com.example.smatech.ay5edma.Dialoge.EvaluationDaialoge;
import com.example.smatech.ay5edma.Models.DummyModel;
import com.example.smatech.ay5edma.Models.Modelss.StatusModel;
import com.example.smatech.ay5edma.Models.Modelss.UserModel;
import com.example.smatech.ay5edma.Models.RequestModel;
import com.example.smatech.ay5edma.R;
import com.example.smatech.ay5edma.ServiceProviderDescription;
import com.example.smatech.ay5edma.Utils.Connectors;
import com.example.smatech.ay5edma.Utils.Constants;
import com.google.gson.Gson;
import com.orhanobut.hawk.Hawk;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.ViewHolder> {
    private ArrayList<com.example.smatech.ay5edma.Models.Modelss.RequestModel> requestModels;
    private RequestAdapter.OnItemClick mOnItemClick;
    private Context context;
    private String userType;
    private Activity a;
    UserModel userModel = Hawk.get(Constants.userData);

    View parentLayout;

    public RequestAdapter(ArrayList<com.example.smatech.ay5edma.Models.Modelss.RequestModel> requestModels, String userType, Context context, Activity a, RequestAdapter.OnItemClick mOnItemClick) {
        this.requestModels = requestModels;
        this.mOnItemClick = mOnItemClick;
        this.context = context;
        this.a = a;
        this.userType = userType;
    }


    @NonNull
    @Override

    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;

        if (userType.equals("0")) {
            view = LayoutInflater.from(context).inflate(R.layout.request_item, viewGroup, false);

        } else if (userType.equals("1")) {
            view = LayoutInflater.from(context).inflate(R.layout.request_item_provider, viewGroup, false);

        } else {
            view = LayoutInflater.from(context).inflate(R.layout.request_item_ser_prov, viewGroup, false);

        }
        return new RequestAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        com.example.smatech.ay5edma.Models.Modelss.RequestModel itemMode = requestModels.get(i);

        String l = Locale.getDefault().getLanguage();
        Log.d("TTT", "onBindViewHolder: " + l);

        if (Hawk.contains(Constants.Time)) {

            if (Hawk.get(Constants.Time).equals("0") && itemMode.getFinished() != null && itemMode.getFinished()) {
                //viewHolder.viewHoolder.setVisibility(View.GONE);
                viewHolder.viewHoolder.setClickable(false);
            }
        }
        if (Hawk.contains(Constants.Set)) {
            Log.d("TTTT", "onBindViewHolder:Language Sets-->" + Hawk.get(Constants.Language));
            if (Hawk.get(Constants.Language).equals("ar")) {
                if (itemMode.getCategoryNameAr() != null) {
                    viewHolder.Catgry1.setText("" + itemMode.getCategoryNameAr());
                } else {
                    viewHolder.Catgry1.setText("");

                }
                if (itemMode.getSubNameAr() != null)
                    viewHolder.Catgry2.setText("" + itemMode.getSubNameAr());
                else
                    viewHolder.Catgry2.setText("");

            } else {
                if (itemMode.getCategoryName() != null) {
                    viewHolder.Catgry1.setText("" + itemMode.getCategoryName());
                } else {
                    viewHolder.Catgry1.setText("");

                }
                if (itemMode.getSubName() != null)
                    viewHolder.Catgry2.setText("" + itemMode.getSubName());
                else
                    viewHolder.Catgry2.setText("");


            }
        } else {
            Log.d("TTTT", "onBindViewHolder:Language def-->" + l);

            if (l.equals("ar")) {
                if (itemMode.getCategoryNameAr() != null) {
                    viewHolder.Catgry1.setText("" + itemMode.getCategoryNameAr());
                } else {
                    viewHolder.Catgry1.setText("");

                }
                if (itemMode.getSubNameAr() != null)
                    viewHolder.Catgry2.setText("" + itemMode.getSubNameAr());
                else
                    viewHolder.Catgry2.setText("");
            } else {
                if (itemMode.getCategoryName() != null) {
                    viewHolder.Catgry1.setText("" + itemMode.getCategoryName());
                } else {
                    viewHolder.Catgry1.setText("");

                }
                if (itemMode.getSubName() != null)
                    viewHolder.Catgry2.setText("" + itemMode.getSubName());
                else
                    viewHolder.Catgry2.setText("");


            }
        }

        viewHolder.Date.setText("" + itemMode.getUpdated());

        if ((itemMode.getStatus().equals("0") || itemMode.getStatus().equals("0") || itemMode.getStatus().equals("1")) && (!itemMode.getShopId().equals("0"))) {
            viewHolder.Call.setBackground(ContextCompat.getDrawable(context, R.drawable.orang_button));
            viewHolder.MSG.setBackground(ContextCompat.getDrawable(context, R.drawable.orang_button));
            viewHolder.evaluate.setBackground(ContextCompat.getDrawable(context, R.drawable.orang_button));

            viewHolder.evaluate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EvaluationDaialoge cdd;
                    if (userModel.getId().equals(itemMode.getUserId())) {
                        cdd = new EvaluationDaialoge(a, "" + userModel.getId()
                                , "" + itemMode.getShop().getId(), itemMode.getId());

                    } else {
                        cdd = new EvaluationDaialoge(a, "" + userModel.getId()
                                , "" + itemMode.getUserId(), itemMode.getId() + "");

                    }
                    cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    cdd.show();
                }
            });
            viewHolder.MSG.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //to edit later
                    if (itemMode.getShop().getId() != null) {
                        Intent i = new Intent(context, ChatActivity.class);
                        if (userModel.getId().equals(itemMode.getUserId())) {
                            i.putExtra("to_id", itemMode.getShop().getId() + "");

                        } else {
                            i.putExtra("to_id", itemMode.getUserId() + "");

                        }
                        context.startActivity(i);
                    }
                }
            });
            viewHolder.Call.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_CALL);

                    if (userModel.getMobile().equals(itemMode.getShop().getMobile())) {
                        intent.setData(Uri.parse("tel:" + itemMode.getUser().getMobile()));

                    } else {
                        intent.setData(Uri.parse("tel:" + itemMode.getShop().getMobile()));

                    }
                    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    context.startActivity(intent);
                }
            });
            // if (itemMode.getFavourite().equals("0")) {
            if (!itemMode.getFavourite()) {
                viewHolder.fav.setVisibility(View.VISIBLE);
                Picasso.with(context).load(R.drawable.hart_strok_yellow).into(viewHolder.fav);
                viewHolder.fav.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Picasso.with(context).load(R.drawable.hart_yellow).into(viewHolder.fav);
                        addFav(itemMode.getUserId(), itemMode.getId());
                        itemMode.setFavourite(true);
                        notifyItemChanged(i);

                    }
                });

            } else {
                viewHolder.fav.setVisibility(View.VISIBLE);
                Picasso.with(context).load(R.drawable.hart_yellow).into(viewHolder.fav);
                viewHolder.fav.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Picasso.with(context).load(R.drawable.hart_strok_yellow).into(viewHolder.fav);
                        addFav(itemMode.getUserId(), itemMode.getId());
                        itemMode.setFavourite(false);
                        notifyItemChanged(i);

                    }
                });


            }

        }
        if (userType.equals("1")) {
            viewHolder.name.setText("" + itemMode.getUser().getName());
            viewHolder.fav.setVisibility(View.GONE);
            if (itemMode.getStatus().equals("1")) {
                viewHolder.Status.setText("" + context.getString(R.string.Requests_Accepted));

            } else if (itemMode.getStatus().equals("3")) {
                viewHolder.Status.setText("" + context.getString(R.string.Requests_Rejected));
                viewHolder.Removable_Section.setVisibility(View.GONE);
            } else if (itemMode.getStatus().equals("2")) {
                viewHolder.Status.setText("" + context.getString(R.string.Requests_finished));
            } else if (itemMode.getStatus().equals("0")) {
                viewHolder.Status.setText("" + context.getString(R.string.Requests_progress));
            }


        } else {
            if (itemMode.getShop().getName() != null)
                viewHolder.name.setText("" + itemMode.getShop().getName());
            else {
                viewHolder.name.setText("" + itemMode.getOffers_count());
                viewHolder.offers_text.setText(context.getString(R.string.offers_count));
            }
        }
        if (userType.equals("3")) {
            viewHolder.fav.setVisibility(View.GONE);
            viewHolder.offers_text.setText(context.getString(R.string.Client_name));

            viewHolder.name.setText(itemMode.getUser().getName());


        }


    }


    @Override
    public int getItemCount() {
        return requestModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        LinearLayout evaluate, MSG, Call, Removable_Section, viewHoolder;
        TextView Catgry1, Catgry2, Date, name, Status, offers_text;

        ImageView fav;

        public ViewHolder(View itemView) {
            super(itemView);
            Catgry1 = itemView.findViewById(R.id.catgr1);
            Catgry2 = itemView.findViewById(R.id.catgr2);
            Date = itemView.findViewById(R.id.Date);
            name = itemView.findViewById(R.id.Name);
            offers_text = itemView.findViewById(R.id.offers_text);
            //
            viewHoolder = itemView.findViewById(R.id.viewHoolder);
            evaluate = itemView.findViewById(R.id.evaluate);
            MSG = itemView.findViewById(R.id.MSG);
            Call = itemView.findViewById(R.id.Call);
            fav = itemView.findViewById(R.id.fav);
            //
            Status = itemView.findViewById(R.id.Status);
            Removable_Section = itemView.findViewById(R.id.Removable_Section);
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

    private void addFav(String userID, String ReqID) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Connectors.connectionServices.BaseURL)
                .addConverterFactory(GsonConverterFactory
                        .create(new Gson())).build();
        Connectors.connectionServices connectionService =
                retrofit.create(Connectors.connectionServices.class);

        connectionService.add_favourite(userID, ReqID).enqueue(new Callback<StatusModel>() {
            @Override
            public void onResponse(Call<StatusModel> call, Response<StatusModel> response) {
                StatusModel statusModel = response.body();
                if (statusModel.getStatus()) {
                    if (Hawk.get(Constants.Language).equals("ar")) {
                        Toast.makeText(context, "" + statusModel.getMessage_ar(), Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(context, "" + statusModel.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<StatusModel> call, Throwable t) {

            }
        });

    }
}

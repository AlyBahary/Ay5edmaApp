package com.example.smatech.ay5edma.Adapters;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smatech.ay5edma.Dialoge.EvaluationDaialoge;
import com.example.smatech.ay5edma.Models.Modelss.StatusModel;
import com.example.smatech.ay5edma.Models.Modelss.UserModel;
import com.example.smatech.ay5edma.Models.RequestModel;
import com.example.smatech.ay5edma.Models.favModel.Example;
import com.example.smatech.ay5edma.Models.favModel.Request;
import com.example.smatech.ay5edma.R;
import com.example.smatech.ay5edma.RequestDescriptionActivity;
import com.example.smatech.ay5edma.RequestsActivity;
import com.example.smatech.ay5edma.Utils.Connectors;
import com.example.smatech.ay5edma.Utils.Constants;
import com.google.gson.Gson;
import com.orhanobut.hawk.Hawk;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import se.arbitur.geocoding.Constants.LocationTypes;
import se.arbitur.geocoding.Result;
import se.arbitur.geocoding.ReverseGeocoding;

public class FavAdapter extends RecyclerView.Adapter<FavAdapter.ViewHolder> {
    private ArrayList<Request> requestModels;
    private FavAdapter.OnItemClick mOnItemClick;
    private Context context;
    private Activity a;
    ProgressDialog progressDialog;

    public FavAdapter(ArrayList<Request> requestModels, Context context, Activity a, FavAdapter.OnItemClick mOnItemClick) {
        this.requestModels = requestModels;
        this.mOnItemClick = mOnItemClick;
        this.context = context;
        this.a = a;
        progressDialog = new ProgressDialog(context);
    }


    @NonNull
    @Override

    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.fav_item, viewGroup, false);
        return new FavAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        Request itemMode = requestModels.get(i);
        // Log.d("TTT", "onBindViewHolder: item:  "i);
        if (Hawk.get(Constants.Language).equals("ar")) {
            viewHolder.Catgry1.setText("" + itemMode.getCategory().getNameAr());
            viewHolder.Catgry2.setText("" + itemMode.getSubcategory().getNameAr());
        } else {
            viewHolder.Catgry1.setText("" + itemMode.getCategory().getName());
            viewHolder.Catgry2.setText("" + itemMode.getCategory().getName());
        }

        viewHolder.Date.setText("" + itemMode.getUpdated());
        viewHolder.name.setText("" + itemMode.getShop().getName());
        viewHolder.fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFav("" + itemMode.getUserId(), "" + itemMode.getId());
                requestModels.remove(i);
                notifyItemRemoved(i);
            }
        });
        viewHolder.ReOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* sendRequest("" + itemMode.getBody(), "" + itemMode.getAddress(), "" + itemMode.getLatitude()
                        , "" + itemMode.getLongitude(), "" + itemMode.getCategoryId(), itemMode.getSubcategoryId());
*/
                re_request(itemMode.getId());
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
            fav = itemView.findViewById(R.id.fav);
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

    private void sendRequest(final String Description, String Address, String lat, String longt, String catgry, String subctgry) {

        progressDialog.show();
        UserModel userModel = Hawk.get(Constants.userData);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Connectors.connectionServices.BaseURL)
                .addConverterFactory(GsonConverterFactory
                        .create(new Gson())).build();
        Connectors.connectionServices connectionService =
                retrofit.create(Connectors.connectionServices.class);

        connectionService.add_Request(userModel.getId()
                , subctgry + ""
                , Description + "", userModel.getId()
                , catgry + ""
                , Address
                , longt + ""
                , lat + ""
        ).enqueue(new Callback<StatusModel>() {
            @Override
            public void onResponse(Call<StatusModel> call, Response<StatusModel> response) {
                progressDialog.dismiss();
                StatusModel statusModel = response.body();
                Log.d("TTTTT", "onResponse: " + response.message());
                if (statusModel.getStatus()) {
                    Log.d("TTT", "onResponse: true ");
                    Hawk.put((Constants.Addlocationdlong), "");
                    Hawk.put((Constants.Addlocationdlat), "");
                    a.finish();
                    Toast.makeText(a, "" + context.getString(R.string.Your_Request_Had_been_sent), Toast.LENGTH_SHORT).show();
                    a.startActivity(new Intent(a, RequestsActivity.class).putExtra("stat", "1"));
                } else {


                }
            }

            @Override
            public void onFailure(Call<StatusModel> call, Throwable t) {
                progressDialog.dismiss();
                View parentLayout = a.findViewById(android.R.id.content);

                Snackbar.make(parentLayout, "" + context.getString(R.string.noInternetConnecion), Snackbar.LENGTH_LONG)
                        .setActionTextColor(context.getResources().getColor(android.R.color.holo_red_light))
                        .show();
                Log.d("TTT", "fails:  " + t.getMessage());

            }
        });


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

    private void re_request(String id) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Connectors.connectionServices.BaseURL)
                .addConverterFactory(GsonConverterFactory
                        .create(new Gson())).build();
        Connectors.connectionServices connectionService =
                retrofit.create(Connectors.connectionServices.class);

        connectionService.re_request(id).enqueue(new Callback<StatusModel>() {
            @Override
            public void onResponse(Call<StatusModel> call, Response<StatusModel> response) {
                StatusModel statusModel = response.body();
                if (statusModel.getStatus()) {
                    a.finish();
                    Toast.makeText(a, "" + context.getString(R.string.Your_Request_Had_been_sent), Toast.LENGTH_SHORT).show();
                    a.startActivity(new Intent(a, RequestsActivity.class).putExtra("stat", "1"));
                }

            }

            @Override
            public void onFailure(Call<StatusModel> call, Throwable t) {
                View parentLayout = a.findViewById(android.R.id.content);

                Snackbar.make(parentLayout, "" + context.getString(R.string.noInternetConnecion), Snackbar.LENGTH_LONG)
                        .setActionTextColor(context.getResources().getColor(android.R.color.holo_red_light))
                        .show();
            }
        });

    }


}

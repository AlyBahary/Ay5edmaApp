package com.example.smatech.ay5edma.Utils;

import com.example.smatech.ay5edma.Models.Modelss.ImageUrlModel;
import com.example.smatech.ay5edma.Models.Modelss.StatusModel;
import com.example.smatech.ay5edma.Models.Modelss.UserModel;
import com.example.smatech.ay5edma.Models.Modelss.UserModelSatus;
import com.example.smatech.ay5edma.Models.Modelss.notficationsModel.Example;

import java.util.ArrayList;
import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public class Connectors {
    public interface connectionServices {
        public String BaseURL = Constants.mBase_Url;
/*
Hawk.get(Constants.loginLat);
Hawk.get(Constants.TOKEN);
Hawk.get(Constants.loginLong);
*/
        //login
        @GET(Constants.mLogin)
        Call<UserModelSatus> login(
                @Query("password") String password
                , @Query("mobile") String mobile
                , @Query("token") String token
                , @Query("latitude") String latitude
                , @Query("longitude") String longitude
        );

        //Signnup
        @GET(Constants.mSignup)
        Call<UserModelSatus> signup(
                @Query("password") String password
                , @Query("role") String role
                , @Query("mobile") String mobile
                , @Query("birthday") String birthday
                , @Query("gender") String gender
                , @Query("name") String name
                , @Query("image") String image
                , @Query("subcategory_id") String subcategory_id
                , @Query("subcategory_id2") String subcategory2_id
                , @Query("category_id") String category_id
                , @Query("longitude") String longitude
                , @Query("latitude") String latitude
                , @Query("address") String address
                , @Query("about") String about

        );
        //Signnup
        @GET(Constants.mSignup)
        Call<UserModelSatus> signup(
                @Query("password") String password
                , @Query("role") String role
                , @Query("mobile") String mobile
                , @Query("birthday") String birthday
                , @Query("gender") String gender
                , @Query("name") String name
                , @Query("image") String image
                , @Query("subcategory_id") String subcategory_id
                , @Query("subcategory_id2") String subcategory2_id
                , @Query("category_id") String category_id
                , @Query("longitude") String longitude
                , @Query("latitude") String latitude
                , @Query("address") String address
                , @Query("about") String about
                , @QueryMap Map<String,String> images

        );
        @GET(Constants.mSignup)
        Call<UserModelSatus> signup(
                @Query("password") String password
                , @Query("role") String role
                , @Query("mobile") String mobile
                , @Query("birthday") String birthday
                , @Query("gender") String gender
                , @Query("name") String name
                , @Query("image") String image
                , @Query("subcategory_id") String subcategory_id
                , @Query("subcategory_id2") String subcategory2_id
                , @Query("category_id") String category_id
                , @Query("longitude") String longitude
                , @Query("latitude") String latitude
                , @Query("address") String address
                , @Query("about") String about
                , @Query("images") ArrayList<String> images

        );
 @GET(Constants.mSignup)
        Call<UserModelSatus> signup(
                @Query("password") String password
                , @Query("role") String role
                , @Query("mobile") String mobile
                , @Query("birthday") String birthday
                , @Query("gender") String gender
                , @Query("name") String name
                , @Query("image") String image
                , @Query("subcategory_id") String subcategory_id
                , @Query("subcategory_id2") String subcategory2_id
                , @Query("category_id") String category_id
                , @Query("longitude") String longitude
                , @Query("latitude") String latitude
                , @Query("address") String address
                , @Query("about") String about
                , @Query("images[0]") String images0
                , @Query("images[1]") String images1
                , @Query("images[2]") String images2
                , @Query("images[3]") String images3
                , @Query("images[4]") String images4

        );

        //get All primary Catgry
        @GET(Constants.mGet_Category)
        Call<StatusModel> get_Category(
                @Query("search") String search

        );

        //get All primary Catgry
        @GET(Constants.mGet_Category)
        Call<StatusModel> get_Category(

        );

        //get Secondry Catgry with search filter
        @GET(Constants.mGet_SubCategory)
        Call<StatusModel> get_SubCategory(
                @Query("category_id") String category_id
                , @Query("search") String search
        );

        //Send Request
        @GET(Constants.mAdd_Request)
        Call<StatusModel> add_Request(
                @Query("user_id") String user_id
                , @Query("subcategory_id") String subcategory_id
                , @Query("body") String body
                , @Query("user_id") String user_id2
                , @Query("category_id") String category_id
                , @Query("address") String address
                , @Query("longitude") String longtide
                , @Query("latitude") String latitude
        );

        //Get All Requests with filters
        @GET(Constants.mGet_requests)
        Call<StatusModel> get_Requests(
                @Query("user_id") String user_id
                , @Query("status") String status
                , @Query("shop_id") String shop_id
                , @Query("subcategory_id") String subcategory_id
                , @Query("category_id") String category_id
                , @Query("filter_id") String filter_id
        );

        //Get All Requests with filters favourit
        @GET(Constants.mGet_requests)
        Call<com.example.smatech.ay5edma.Models.favModel.Example> get_Favourite(
                @Query("user_id") String user_id
                , @Query("favourite") Boolean status

        );

        //Get All Requests with filters favourit
        @GET(Constants.add_favourite)
        Call<StatusModel> add_favourite(
                @Query("user_id") String user_id
                , @Query("request_id") String request_id

        );

        @GET(Constants.send_feedback)
        Call<StatusModel> send_feedback(
                @Query("user_id") String user_id
                , @Query("comment") String comment

        );

        //sendOffer
        @GET(Constants.send_offer)
        Call<StatusModel> sendOffer(
                @Query("user_id") String user_id
                , @Query("shop_id") String shop_id
                , @Query("request_id") String request_id
        );

        //Get UserData
        @GET(Constants.mGet_user)
        Call<UserModelSatus> get_user(
                @Query("id") String id

        );

        //Edit UserData
        @GET(Constants.mUser_edit)
        Call<StatusModel> edit(
                @Query("mobile") String mobile
                , @Query("image") String image
                , @Query("id") String id
                , @Query("name") String name
                , @Query("gender") String gender
                , @Query("category_id") String category_id
                , @Query("subcategory_id") String subcategory_id
                , @Query("subcategory_id2") String subcategory_id2
                , @Query("birthday") String birthday
                , @Query("role") String role
                , @Query("about") String about

        );
//Edit UserData
        @GET(Constants.mUser_edit)
        Call<StatusModel> edit(
                @Query("mobile") String mobile
                , @Query("image") String image
                , @Query("id") String id
                , @Query("name") String name
                , @Query("gender") String gender
                , @Query("category_id") String category_id
                , @Query("subcategory_id") String subcategory_id
                , @Query("subcategory_id2") String subcategory_id2
                , @Query("birthday") String birthday
                , @Query("role") String role
                , @Query("about") String about
                , @QueryMap Map<String,String> images
        );

        //get Offers
        @GET(Constants.mGet_offers)
        Call<com.example.smatech.ay5edma.Models.offerModel.example.Example> get_offers(
                @Query("request_id") String request_id

        );

        @GET(Constants.mUpdate_request_status)
        Call<StatusModel> update_request_status(
                @Query("user_id") String user_id
                , @Query("shop_id") String shop_id
                , @Query("request_id") String request_id
                , @Query("status") String status

        );

        @GET(Constants.mGet_bank_list)
        Call<StatusModel> get_bank_list(
        );

        @GET(Constants.mAdd_deposite)
        Call<StatusModel> add_deposite(
                @Query("user_id") String user_id
                , @Query("bank_id") String bank_id
                , @Query("amount") String amount
                , @Query("send_date") String send_date
                , @Query("name") String name
                , @Query("number") String number

        );

        @GET(Constants.mGet_notifications)
        Call<Example> get_notificaions(
                @Query("user_id") String user_id
                ,@Query("role") String role
        );

        @GET(Constants.validateAccount)
        Call<StatusModel> validateAccount(
                @Query("verification") String verification
        );

        @GET(Constants.change_password)
        Call<StatusModel> change_password(
                @Query("new_password") String new_password
                , @Query("vertificaion") String verification
        );

        @GET(Constants.change_password)
        Call<StatusModel> change_password1(
                @Query("id") String id
                , @Query("new_password") String new_password
        );

        @GET(Constants.forget_password)
        Call<StatusModel> forgetPass(
                @Query("mobile") String mobile
        );

        @GET(Constants.add_review)
        Call<StatusModel> add_review(
                @Query("rate") String rate
                ,@Query("comment") String comment
                ,@Query("user_id") String user_id
                ,@Query("from_id") String from_id
                ,@Query("request_id") String request_id
        );

        @GET(Constants.mDelete_request)
        Call<StatusModel> delete_request(
                @Query("user_id") String user_id
                , @Query("id") String id
        );
        @GET(Constants.get_reviews)
        Call<StatusModel> get_reviews(
                @Query("user_id") String user_id
        );

        @GET(Constants.mGet_chat_messages)
        Call<StatusModel> get_chat_messages(
                @Query("chat_id") String chat_id
                , @Query("user_id") String user_id
                , @Query("to_id") String to_id
        );

        @GET(Constants.send_message)
        Call<StatusModel> send_message(
                @Query("message") String message
                , @Query("user_id") String user_id
                , @Query("to_id") String to_id
                , @Query("chat_id") String chat_id
        );

        @GET(Constants.get_home_sliders)
        Call<StatusModel> get_Sliders(
        );

        @GET(Constants.get_points)
        Call<StatusModel> get_points(
        );


        /// call of upload image

        @Multipart
        @POST(Constants.upload_image)
        Call<ImageUrlModel> Upload_Img(
                @Part MultipartBody.Part file
        );

    }

}

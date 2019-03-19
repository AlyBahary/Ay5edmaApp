package com.example.smatech.ay5edma.Utils;

import android.app.ProgressDialog;
import android.content.Context;

import com.example.smatech.ay5edma.R;

public class Constants {
    public final static String API_KEY="AIzaSyAcazeBKVO9e7HvHB9ssU1jc9NhTj_AFsQ";
    public final static String UserType="UserType";
    public final static String Client="0";
    public final static String username="username";
    public final static String password="password";
    public final static String Provider="1";
    public final static String skipe="5";
    public final static String SubCat1="SubCat1";
    public final static String SubCat2="SubCat2";
    public final static String TOKEN="TOKEN";
    public final static String Time="1";
    public final static String Language="Language";
    public final static String Set="Set";
    public final static String Arabic="ar";
    public final static String English="en";
    public final static String reload="reload";
    public final static String Addlocationdlat="Addlocationdlat";
    public final static String Addlocationdlong="Addlocationdlong";

    ///

    public final static String Data="Data";
    public final static String userData="userData";
    public final static String extraauserData1="extraauserData1";
    public final static String extraauserData2="extraauserData2";
    public final static String userLat="userLat";
    public final static String userLong="userLong";
    public final static String userAddress="userAddress";

    public static String loginLat="loginLat";
    public static String loginLong="loginLong";

    ///Constants Use o pass Date
    public final static String name="userDataname";
    public final static String pass="userDataname";
    public final static String sex="userDatasex";
    public final static String BirthDate="BirthDate";
    public final static String mobile="userDatamobile";
    public final static String mRequestID="mRequestID";
    public final static String mRequestFrom="mRequestFrom";
    public final static String mRequestDesc="mRequestDesc";
    public final static String mOfferModel="mOfferModel";



    //
    public final static String mCatgrID="mCatgrID";
    public final static String mSubCatgrID="mSubCatgrID";


    //////////--------------URLs-----------------////////////
    public final static String mBase_Url="http://www.anyservice-ksa.com/";
    public final static String mLogin="api/login";
    public final static String mSignup="api/signup";
    public final static String mGet_Category="api/get_category";
    public final static String mGet_SubCategory="api/get_subcategory";
    public final static String mAdd_Request="api/add_request";
    public final static String mGet_requests="api/get_requests";
    public final static String mGet_user="api/get_user";
    public final static String mUser_edit="api/edit";
    public final static String mUpdate_request_status="api/update_request_status";
    public final static String mGet_offers="api/get_offers";
    public final static String mGet_bank_list="api/bank_list";
    public final static String mAdd_deposite="api/add_deposite";
    public final static String mGet_notifications="api/get_notifications";
    public final static String mDelete_request="api/delete_request";
    public final static String mGet_chat_messages="api/get_chat_messages";
    public final static String send_message="api/send_message";
    public final static String send_offer="api/send_offer";
    public final static String get_home_sliders="api/get_home_sliders";
    public final static String upload_image="api/upload_image";
    public final static String get_points="api/get_points";
    public final static String add_favourite="api/add_favourite";
    public final static String send_feedback="api/send_feedback";
    public final static String validateAccount="api/validateAccount";
    public final static String forget_password="api/forget_password";
    public final static String change_password="api/change_password";
    public final static String add_review="api/add_review";
    public final static String get_reviews="api/get_reviews";
    public static final String STUCK ="STUCK" ;
    public static String mRequest="mRequest";


    public static void progressDialoge(Context context){
    ProgressDialog progressDialog=new ProgressDialog(context);
    progressDialog.setMessage(context.getString(R.string.Loading));
    progressDialog.show();
}
}

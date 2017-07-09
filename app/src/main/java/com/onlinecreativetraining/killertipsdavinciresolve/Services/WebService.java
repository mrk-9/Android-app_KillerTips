package com.onlinecreativetraining.killertipsdavinciresolve.Services;

import com.loopj.android.http.*;
import com.onlinecreativetraining.killertipsdavinciresolve.DataModel.Constants;

public class WebService {

    static String signupURL = Constants.baseURL +  "/api/v1/accounts/create";
    static String signinURL = Constants.baseURL + "/api/v1/accounts/sign_in";
    static String setDeviceURL = Constants.baseURL + "/api/v1/accounts/set_device";
    static String setFavouriteURL = Constants.baseURL + "/api/v1/accounts/set_favourite";
    static String setReadURL = Constants.baseURL + "/api/v1/accounts/set_read";
    static String setPurchase = Constants.baseURL + "/api/v1/accounts/set_purchase";
    static String tipURL = Constants.baseURL + "/api/v1/tips";
    static String tipContentURL = Constants.baseURL + "/api/v1/tips/tip_content";
    static String categoryURL = Constants.baseURL + "/api/v1/tips/categories";
    static String searchURL = Constants.baseURL + "/api/v1/tips/find";
    static String purchaseURL = Constants.baseURL + "/api/v1/accounts/get_purchased_packs";
    static String unpurchaseURL = Constants.baseURL + "/api/v1/accounts/get_unpurchased_packs";

    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void SignIn(RequestParams params, AsyncHttpResponseHandler handler){
        client.post(signinURL, params, handler);
    }
    public static void SignUp(RequestParams params, AsyncHttpResponseHandler handler){
        client.post(signupURL, params, handler);
    }
    public static void SignUpNewsletter(RequestParams params, AsyncHttpResponseHandler handler){
        client.post(signupURL, params, handler);
    }
    public static void setDeviceToken(RequestParams params, AsyncHttpResponseHandler handler){
        client.post(setDeviceURL, params, handler);
    }
    public static void setFavourite(RequestParams params, AsyncHttpResponseHandler handler){
        client.post(setFavouriteURL, params, handler);
    }
    public static void setRead(RequestParams params, AsyncHttpResponseHandler handler){
        client.post(setReadURL, params, handler);
    }
    public static void setPurchase(RequestParams params, AsyncHttpResponseHandler handler){
        client.post(setPurchase, params, handler);
    }

    public static void getAllTips(RequestParams params, AsyncHttpResponseHandler handler){
        client.get(tipURL, params, handler);
    }
    public static void getTipData(RequestParams params, AsyncHttpResponseHandler handler){
        client.get(tipContentURL, params, handler);
    }
    public static void getCategories(RequestParams params, AsyncHttpResponseHandler handler){
        client.get(categoryURL, params, handler);
    }
    public static void getPurchase(RequestParams params, AsyncHttpResponseHandler handler){
        client.get(purchaseURL, params, handler);
    }
    public static void getunPurchase(RequestParams params, AsyncHttpResponseHandler handler){
        client.get(unpurchaseURL, params, handler);
    }
    public static void getSearchTip(RequestParams params, AsyncHttpResponseHandler handler){
        client.get(searchURL, params, handler);
    }
}

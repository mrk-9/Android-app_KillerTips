package com.onlinecreativetraining.killertipsdavinciresolve.DataModel;

import com.loopj.android.http.RequestParams;

import java.util.ArrayList;

public class Constants {
    public static String baseURL = "http://50.112.29.21";
    public static String apiKey = "fa07c89f9bcba05aa714cb287a67585f-us14";
    public static String chimpID = "26c04f2976";
    public static String strEmail = "";
    public static String strPassword = "";
    public static String token = "";
    public static String pack_id = "";
    public static boolean purchase = false;
    public static boolean detailwindowflag = false;
    static RequestParams params = new RequestParams();
    static int TipIndex = 0;
    static ArrayList<Tip> TipData = new ArrayList<>();

    public static RequestParams getParams() {
        return params;
    }
    public static void setParams(RequestParams params) {
        Constants.params = params;
    }
    public static void setPageNumber(int pageNumber){
        if(params != null){
            params.remove("page_number");
            params.put("page_number", pageNumber);
        }
    }
    public static void setParamTipID(String tipID){
        if(params != null){
            params.remove("tip_id");
            params.put("tip_id", tipID);
        }
    }
    public static void setParamFavourite(boolean bool){
        if(params != null){
            params.remove("favourite");
            params.put("favourite", bool);
        }
    }

    public static void setReadTipFlag(boolean bool){
        if(params != null){
            params.remove("is_read");
            params.put("is_read", bool);
        }
    }

    public static Tip getCurrentTip(){
        return TipData.get(TipIndex);
    }
    public static void setCurrentTipRead(){
        TipData.get(TipIndex).setRead("true");
    }

    public static int getTipIndex() {
        return TipIndex;
    }

    public static void setTipIndex(int tipIndex) {
        TipIndex = tipIndex;
    }

    public static ArrayList<Tip> getTipData() {
        return TipData;
    }

    public static void setTipData(ArrayList<Tip> tipData) {
        TipData = tipData;
    }
}

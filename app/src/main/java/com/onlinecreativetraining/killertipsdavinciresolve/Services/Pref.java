package com.onlinecreativetraining.killertipsdavinciresolve.Services;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class Pref {

    public String PREFERENCE_NAME = "twitter_oauth";
    public final String OAUTH_TOKEN = "oauth_token";
    public final String OAUTH_SECRET = "oauth_token_secret";
    public final String isLogin = "isLogin";

    public SharedPreferences pref;
    public Editor editor;

    public Pref(Context ctx){
        pref = ctx.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
    }

    public void edit(){
        if(pref != null)
            editor = pref.edit();
    }

    public void commit(){
        if(editor != null)
            editor.commit();
    }
}

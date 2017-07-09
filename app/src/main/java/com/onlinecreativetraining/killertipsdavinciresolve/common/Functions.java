package com.onlinecreativetraining.killertipsdavinciresolve.common;

import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.onlinecreativetraining.killertipsdavinciresolve.Activities.MainActivity;
import com.onlinecreativetraining.killertipsdavinciresolve.Activities.TipsFragment;
import com.onlinecreativetraining.killertipsdavinciresolve.DataModel.Constants;
import com.onlinecreativetraining.killertipsdavinciresolve.DataModel.Tip;
import com.onlinecreativetraining.killertipsdavinciresolve.Services.WebService;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class Functions {

    public final static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }
}

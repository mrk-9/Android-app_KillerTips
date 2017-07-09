package com.onlinecreativetraining.killertipsdavinciresolve.Activities;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.onlinecreativetraining.killertipsdavinciresolve.DataModel.Constants;
import com.onlinecreativetraining.killertipsdavinciresolve.R;
import com.onlinecreativetraining.killertipsdavinciresolve.Services.WebService;
import com.onlinecreativetraining.killertipsdavinciresolve.common.Functions;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class SignupFragment extends Fragment implements View.OnClickListener {

    View view;
    EditText txtEmail;
    EditText txtPassword;
    EditText txtConfirm;
    CheckBox checkBox;
    TextView signup;
    WebService webService = new WebService();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(view != null) return view;
        view = inflater.inflate(R.layout.signup, container, false);

        txtEmail = (EditText)view.findViewById(R.id.sign_email);
        txtPassword = (EditText)view.findViewById(R.id.sigin_password);
        txtConfirm = (EditText)view.findViewById(R.id.confrim_password);
        checkBox = (CheckBox)view.findViewById(R.id.checkBox);
        signup = (TextView)view.findViewById(R.id.signup_sign);

        signup.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.signup_sign:
                Signup();
                break;
            default:
                break;
        }
    }
    void Signup(){
        String strEmail = txtEmail.getText().toString();
        String strPassword = txtPassword.getText().toString();
        String strConfirm = txtConfirm.getText().toString();
        if(Functions.isValidEmail(strEmail) == false) {
            Toast.makeText(getActivity(), "Please input a valid Email!", Toast.LENGTH_SHORT).show();
            return;
        }
        if(strPassword.length() < 1){
            Toast.makeText(getActivity(), "Please input password!", Toast.LENGTH_SHORT).show();
            return;
        }
        if(strConfirm.length() < 1) {
            Toast.makeText(getActivity(), "Please input confirm password!", Toast.LENGTH_SHORT).show();
            return;
        }
        if(strPassword.length() < 6){
            Toast.makeText(getActivity(), "Password is too short, minimum is 6 charactors. please try again", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!strPassword.equals(strConfirm)){
            Toast.makeText(getActivity(), "Password is not matching. please try again!", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!checkBox.isChecked()){
            Toast.makeText(getActivity(), "You have to agree the terms of use.", Toast.LENGTH_SHORT).show();
            return;
        }
        RequestParams params = new RequestParams();
        params.put("email", strEmail);
        params.put("password", strPassword);

        MainActivity.progressBar.setVisibility(View.VISIBLE);
        webService.SignUp(params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONObject response) {
//                super.onSuccess(statusCode, headers, response);
                try {
                    String status = response.getString("status");
                    if(status.equals("0")){
                        String errorString = "";
                        if (response.has("data"))
                            errorString = response.getString("data");
                        if (errorString.equals(""))
                            Toast.makeText(getActivity(), "Failure, Please try again.", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(getActivity(), errorString, Toast.LENGTH_SHORT).show();
                    }else {
                        JSONObject obj = response.getJSONObject("data");
                        Constants.token = obj.getString("token");
                        saveData();
                        MainActivity.openFragment(new MainFragment());
                        MainActivity.imgMenu.setVisibility(View.VISIBLE);
                        MainActivity.searchView.setVisibility(View.VISIBLE);
                    }
                    MainActivity.progressBar.setVisibility(View.GONE);
                }catch (Exception e) {
                    MainActivity.progressBar.setVisibility(View.GONE);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
//                super.onFailure(statusCode, headers, responseString, throwable);
                Toast.makeText(getActivity(), "Server Error", Toast.LENGTH_SHORT).show();
                MainActivity.progressBar.setVisibility(View.GONE);
            }
        });
    }
    void saveData(){
        SharedPreferences pref = getActivity().getSharedPreferences("killerTip", 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("token", Constants.token);
        editor.commit();
    }
}

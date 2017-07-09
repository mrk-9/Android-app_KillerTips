package com.onlinecreativetraining.killertipsdavinciresolve.Activities;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
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

public class LoginFragment extends Fragment implements View.OnClickListener {

    View view;
    EditText txtEmail;
    EditText txtPassword;
    TextView forgot;
    TextView login;
    TextView signup;
    String strEmail;
    String strPassword;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(view != null) return view;
        view = inflater.inflate(R.layout.login, container, false);

        Log.d("status", "login");

        txtEmail = (EditText)view.findViewById(R.id.user_email);
        txtPassword = (EditText)view.findViewById(R.id.password);
        forgot = (TextView)view.findViewById(R.id.txtForgot);
        login = (TextView)view.findViewById(R.id.txtLogin);
        signup = (TextView)view.findViewById(R.id.txtSignup);

        forgot.setOnClickListener(this);
        login.setOnClickListener(this);
        signup.setOnClickListener(this);

        Typeface custom_font1 = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Futura.ttf");
        if (custom_font1 != null)
            Log.d("status", "success");
        forgot.setTypeface(custom_font1);
        login.setTypeface(custom_font1);
        signup.setTypeface(custom_font1);

        if (MainActivity.email != null && !MainActivity.email.isEmpty())
            txtEmail.setText(MainActivity.email);

        if (MainActivity.password != null && !MainActivity.password.isEmpty())
            txtPassword.setText(MainActivity.password);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.txtForgot:
//                Toast.makeText(getActivity(), "forgot clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.txtLogin:
//                Toast.makeText(getActivity(), "login clicked", Toast.LENGTH_SHORT).show();
                Login();
                break;
            case R.id.txtSignup:
//                Toast.makeText(getActivity(), "signup clicked", Toast.LENGTH_SHORT).show();
                Signup();

                break;
        }
    }
    private void Login(){
        strEmail = txtEmail.getText().toString();
        strPassword = txtPassword.getText().toString();

        if(Functions.isValidEmail(strEmail) == false) {
            Toast.makeText(getActivity(), "Please input a valid Email!", Toast.LENGTH_SHORT).show();
            return;
        }
        if(strPassword.length() < 1){
            Toast.makeText(getActivity(), "Please input password!", Toast.LENGTH_SHORT).show();
            return;
        }
        RequestParams params = new RequestParams();
        params.put("email", strEmail);
        params.put("password", strPassword);

        MainActivity.progressBar.setVisibility(View.VISIBLE);
        WebService.SignIn(params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
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
                        MainActivity.logo_img.getLayoutParams().height = 200;
                        MainActivity.logo_img.getLayoutParams().width = 300;
                        MainActivity.logo_img.setScaleType(ImageView.ScaleType.FIT_XY);
                        MainActivity.email = strEmail;
                        MainActivity.password = strPassword;
                        ((MainActivity)getActivity()).popFragment();
                    }
                    MainActivity.progressBar.setVisibility(View.GONE);
                }catch (Exception e) {
                    MainActivity.progressBar.setVisibility(View.GONE);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Toast.makeText(getActivity(), "Server Error", Toast.LENGTH_SHORT).show();
                MainActivity.progressBar.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("emailState", strEmail);
        outState.putString("PasswordState", strPassword);
    }

    void saveData(){
        SharedPreferences pref = getActivity().getSharedPreferences("killerTip", 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("token", Constants.token);
        editor.commit();
    }
    private void Signup(){
        MainActivity.openFragment(new SignupFragment());
        MainActivity.logo_img.getLayoutParams().height = 200;
        MainActivity.logo_img.getLayoutParams().width = 300;
        MainActivity.logo_img.setScaleType(ImageView.ScaleType.FIT_XY);
    }
}

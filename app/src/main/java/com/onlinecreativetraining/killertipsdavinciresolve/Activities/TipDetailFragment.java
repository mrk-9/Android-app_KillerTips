package com.onlinecreativetraining.killertipsdavinciresolve.Activities;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.onlinecreativetraining.killertipsdavinciresolve.DataModel.Constants;
import com.onlinecreativetraining.killertipsdavinciresolve.DataModel.Tip;
import com.onlinecreativetraining.killertipsdavinciresolve.R;
import com.onlinecreativetraining.killertipsdavinciresolve.Services.WebService;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class TipDetailFragment extends Fragment{

    View view;
    Tip tip = new Tip();
    ImageView detailImage;
    TextView detailCategory;
    TextView detailTitle;
    ImageView detailFavourite;
    WebView detailWebview;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        tip = Constants.getCurrentTip();
        Constants.setParamTipID(tip.getTipId());
        setReadFlag();
        MainActivity.searchView.setVisibility(View.VISIBLE);
        MainActivity.txtBack.setText("<");
        MainActivity.txtNext.setText(">");
        if(view != null)
            return view;
        view = inflater.inflate(R.layout.tipdetail_layout, container, false);
        detailImage = (ImageView)view.findViewById(R.id.imgDetail);
        detailCategory = (TextView)view.findViewById(R.id.detail_category);
        detailTitle = (TextView)view.findViewById(R.id.detail_title);
        detailFavourite = (ImageView)view.findViewById(R.id.detail_favourite);
        detailWebview = (WebView)view.findViewById(R.id.detail_content);
        detailWebview.getSettings().setJavaScriptEnabled(true);
        MainActivity.txtNext.setEnabled(false);
        MainActivity.txtBack.setEnabled(false);

        detailFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tip.getFavourited().equals("true")){
                    tip.setFavourited("false");
                    Constants.setParamFavourite(false);
                }else{
                    tip.setFavourited("true");
                    Constants.setParamFavourite(true);
                }
                WebService.setFavourite(Constants.getParams(),new JsonHttpResponseHandler(){
                    @Override
                    public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        try {
                            String status = response.getString("status");
                            if(status.equals("0")){
                                Toast.makeText(getActivity(), "Server Error", Toast.LENGTH_SHORT);
                            }else {
                                if(tip.getFavourited().equals("true")) {
                                    detailFavourite.setImageResource(R.drawable.favourite);
                                }else {
                                    detailFavourite.setImageResource(R.drawable.unfavourite);
                                }
                            }
                        }catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        super.onFailure(statusCode, headers, responseString, throwable);
                        Toast.makeText(getActivity(), "Server Error", Toast.LENGTH_SHORT);
                    }
                });
            }
        });
        MainActivity.progressBar.setVisibility(View.VISIBLE);
        loadData();

        return view;
    }

    void setReadFlag(){
        if(tip.getRead().equals("true"))
            return;
        Constants.setCurrentTipRead();
        Constants.setReadTipFlag(true);
        WebService.setRead(Constants.getParams(), new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    String status = response.getString("status");
                    if(status.equals("0")){
                        Toast.makeText(getActivity(), "Server Error", Toast.LENGTH_SHORT);
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
                Toast.makeText(getActivity(), "Server Error", Toast.LENGTH_SHORT);
                MainActivity.progressBar.setVisibility(View.GONE);
            }
        });
    }

    void loadData(){
        if(!tip.getPhoto().equals("")){
            Picasso.with(getActivity()).load(tip.getPhoto()).placeholder(R.drawable.placeholder).into(detailImage);
        }
        if(tip.getFavourited().equals("true")){
            detailFavourite.setImageResource(R.drawable.favourite);
        }
        detailCategory.setText(tip.getCategory());
        detailTitle.setText(tip.getTitle());
        WebService.getTipData(Constants.getParams(),new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    String status = response.getString("status");
                    if(status.equals("0")){
                        Toast.makeText(getActivity(), "Server Error", Toast.LENGTH_SHORT);
                    }else {
                        JSONObject jsonObject = response.getJSONObject("data");
                        detailWebview.loadDataWithBaseURL("", jsonObject.getString("content"), "text/html", "UTF-8", "");
                    }
                    MainActivity.progressBar.setVisibility(View.GONE);
                    MainActivity.txtNext.setEnabled(true);
                    MainActivity.txtBack.setEnabled(true);
                }catch (Exception e) {
                    MainActivity.progressBar.setVisibility(View.GONE);
                    MainActivity.txtNext.setEnabled(true);
                    MainActivity.txtBack.setEnabled(true);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Toast.makeText(getActivity(), "Server Error", Toast.LENGTH_SHORT);
                MainActivity.progressBar.setVisibility(View.GONE);
                MainActivity.txtNext.setEnabled(true);
                MainActivity.txtBack.setEnabled(true);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        MainActivity.txtBack.setText("");
        MainActivity.txtNext.setText("");
    }
}

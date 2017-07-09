package com.onlinecreativetraining.killertipsdavinciresolve.Activities;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.onlinecreativetraining.killertipsdavinciresolve.DataModel.Constants;
import com.onlinecreativetraining.killertipsdavinciresolve.DataModel.Tip;
import com.onlinecreativetraining.killertipsdavinciresolve.R;
import com.onlinecreativetraining.killertipsdavinciresolve.Services.WebService;
import com.onlinecreativetraining.killertipsdavinciresolve.Utilities.TipAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * Created by dev on 04/09/16.
 */
public class SearchResultFragment extends Fragment{
    View view;
    ArrayList<Tip> dataList = new ArrayList<>();
    private GridView gridview;
    private int pageNumber = 1;
    private String searchKey;

    public static SearchResultFragment newInstance(String searchKey) {
        SearchResultFragment fragment = new SearchResultFragment();
        fragment.searchKey = searchKey;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        MainActivity.searchView.setVisibility(View.VISIBLE);
        MainActivity.imgBack.setVisibility(View.VISIBLE);
        MainActivity.footer.setVisibility(View.VISIBLE);
        if(view != null) return view;
        view = inflater.inflate(R.layout.tips_layout, container, false);
        gridview = (GridView) view.findViewById(R.id.tip_grid);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showDetail(position);
            }
        });
        getAllTips(searchKey);
        gridview.setAdapter(new TipAdapter(getActivity(), dataList));
        return view;
    }
    void showDetail(int position){
        Constants.detailwindowflag = true;
        MainActivity.txtBack.setText("<");
        MainActivity.txtNext.setText(">");
        Constants.setTipIndex(position);
        MainActivity.openTipsFragment(new TipDetailFragment());
    }

    void getAllTips(String searchKey){
        MainActivity.progressBar.setVisibility(View.VISIBLE);
        RequestParams params = new RequestParams();
        params.put("token", Constants.token);
        params.put("key", searchKey);
        WebService.getSearchTip(params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONObject response) {
//                super.onSuccess(statusCode, headers, response);
                try {
                    String status = response.getString("status");
                    if(status.equals("0")){
                        Toast.makeText(getActivity(), "Server Error", Toast.LENGTH_SHORT);
                    }else {
                        JSONArray obj = response.getJSONArray("data");
                        for(int i = 0; i < obj.length(); i++){
                            JSONObject jsonObject = obj.getJSONObject(i);
                            Tip tip = new Tip();
                            tip.setCategory(jsonObject.getString("category").toUpperCase());
                            tip.setDescription(jsonObject.getString("description"));
                            tip.setFavourited(jsonObject.getString("favourited"));
                            tip.setRead(jsonObject.getString("read"));
                            tip.setPack(jsonObject.getString("pack"));
                            tip.setPhoto(jsonObject.getString("photo"));
                            tip.setTipId(jsonObject.getString("id"));
                            tip.setTitle(jsonObject.getString("title"));
                            dataList.add(tip);
                        }
                        Constants.setTipData(dataList);
                        ((BaseAdapter)gridview.getAdapter()).notifyDataSetChanged();
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
                Toast.makeText(getActivity(), "Server Error", Toast.LENGTH_SHORT);
                MainActivity.progressBar.setVisibility(View.GONE);
            }
        });
    }
}

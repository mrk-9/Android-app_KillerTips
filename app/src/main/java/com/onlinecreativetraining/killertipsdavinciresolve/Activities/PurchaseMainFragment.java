package com.onlinecreativetraining.killertipsdavinciresolve.Activities;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.onlinecreativetraining.killertipsdavinciresolve.DataModel.Category;
import com.onlinecreativetraining.killertipsdavinciresolve.DataModel.Constants;
import com.onlinecreativetraining.killertipsdavinciresolve.DataModel.PurchaseTip;
import com.onlinecreativetraining.killertipsdavinciresolve.R;
import com.onlinecreativetraining.killertipsdavinciresolve.Services.WebService;
import com.onlinecreativetraining.killertipsdavinciresolve.Utilities.CategoryAdapter;
import com.onlinecreativetraining.killertipsdavinciresolve.Utilities.PurchaseAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class PurchaseMainFragment extends Fragment {
    View view;
    ArrayList<PurchaseTip> data = new ArrayList<>();
    private GridView gridview;
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        MainActivity.searchView.setVisibility(View.VISIBLE);
        if(view != null) return view;
        view = inflater.inflate(R.layout.purchase_layout, container, false);
        gridview = (GridView) view.findViewById(R.id.gridview);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                selectGridItem(position);
            }
        });
        MainActivity.progressBar.setVisibility(View.VISIBLE);
        getData();
        gridview.setAdapter(new PurchaseAdapter(getActivity(), data));
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        data = new ArrayList<>();
    }

    void selectGridItem(int position){
        Constants.pack_id = data.get(position).getId();
        Constants.purchase = true;
        Toast.makeText(getActivity(), "" + Constants.pack_id + " Clicked",Toast.LENGTH_SHORT).show();
    }
    void getData(){
        RequestParams params = new RequestParams();
        params.put("token", Constants.token);

        WebService.getPurchase(params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    String status = response.getString("status");
                    if(status.equals("0")){
                        Toast.makeText(getActivity(), "Server Error", Toast.LENGTH_SHORT);
                    }else {
                        JSONArray obj = response.getJSONArray("data");
                        for(int i = 0; i < obj.length(); i++){
                            JSONObject jsonObject = obj.getJSONObject(i);
                            PurchaseTip item = new PurchaseTip();
                            item.setId(jsonObject.getString("id"));
                            item.setName(jsonObject.getString("name"));
                            item.setPrice(jsonObject.getString("price"));
                            item.setDetail(jsonObject.getString("detail"));
                            data.add(item);
                        }
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
                super.onFailure(statusCode, headers, responseString, throwable);
                Toast.makeText(getActivity(), "Server Error", Toast.LENGTH_SHORT);
                MainActivity.progressBar.setVisibility(View.GONE);
            }
        });
    }
}

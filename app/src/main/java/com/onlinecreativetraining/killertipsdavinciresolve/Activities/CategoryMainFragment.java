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
import com.onlinecreativetraining.killertipsdavinciresolve.R;
import com.onlinecreativetraining.killertipsdavinciresolve.Services.WebService;
import com.onlinecreativetraining.killertipsdavinciresolve.Utilities.CategoryAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class CategoryMainFragment extends Fragment {
    ArrayList<Category> data = new ArrayList<>();
    private GridView gridview;
    private View view;
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        if(view == null) {
            view = inflater.inflate(R.layout.gridview_layout, container, false);

            gridview = (GridView) view.findViewById(R.id.gridview);

            gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View v,
                                        int position, long id) {
                    selectGridItem(position);
                }
            });
            MainActivity.progressBar.setVisibility(View.VISIBLE);
            getCategorys();
            gridview.setAdapter(new CategoryAdapter(getActivity(), data));
        }
        MainActivity.searchView.setVisibility(View.VISIBLE);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    void selectGridItem(int position){
        if(position == data.size() - 1){
            MainActivity.openFragment(new UnPurchaseMainFragment());
        }else{
            RequestParams params = new RequestParams();
            params.put("token", Constants.token);
            params.put("category_id", data.get(position).getId());
            Constants.setParams(params);
            MainActivity.openFragment(new TipsFragment());
            MainActivity.footer.setVisibility(View.VISIBLE);
        }
    }
    void getCategorys(){
        RequestParams params = new RequestParams();
        params.put("token", Constants.token);

        WebService.getCategories(params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    String status = response.getString("status");
                    if(status.equals("0")){
                        Toast.makeText(getActivity(), "Server Error", Toast.LENGTH_SHORT);
                    }else {
                        JSONArray obj = response.getJSONArray("data");
                        for(int i = 0; i < obj.length(); i++){
                            JSONObject jsonObject = obj.getJSONObject(i);
                            Category item = new Category();
                            item.setId(jsonObject.getString("id"));
                            item.setNameWithIndex(i+1, jsonObject.getString("name").toUpperCase());
                            data.add(item);
                        }
                        Category item = new Category();
                        item.setId("purchase");
                        item.setName("PURCHASE MORE TIPS");
                        data.add(item);
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

package com.onlinecreativetraining.killertipsdavinciresolve.Activities;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import com.loopj.android.http.*;
import com.onlinecreativetraining.killertipsdavinciresolve.DataModel.Constants;
import com.onlinecreativetraining.killertipsdavinciresolve.R;
import com.onlinecreativetraining.killertipsdavinciresolve.Utilities.GridViewAdapter;
import com.onlinecreativetraining.killertipsdavinciresolve.Utilities.TextAdapter;


public class MainFragment extends Fragment{
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        MainActivity.searchView.setVisibility(View.VISIBLE);
        MainActivity.imgBack.setVisibility(View.INVISIBLE);
        MainActivity.footer.setVisibility(View.VISIBLE);
        if(view != null) return view;
        view = inflater.inflate(R.layout.gridview_layout, container, false);
        GridView gridview = (GridView) view.findViewById(R.id.gridview);
        String[] data = {
                "SHOW ALL TIPS", "VIEW CATEGORIES", "SHOW UNREAD", "SHOW FAVOURITES","PURCHASE MORE TIPS","SHOW PURCHASED PACKS"
        };
        gridview.setAdapter(new GridViewAdapter(getActivity(), data));
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                selectGridItem(position);
            }
        });
        return view;
    }

    void selectGridItem(int position){
        RequestParams params = new RequestParams();
        switch (position){
            case 0:
                params.put("token", Constants.token);
                Constants.setParams(params);
                MainActivity.openFragment(new TipsFragment());
                MainActivity.footer.setVisibility(View.VISIBLE);
                break;
            case 1:
                MainActivity.openFragment(new CategoryMainFragment());
                break;
            case 2:
                params.put("token", Constants.token);
                params.put("unread", true);
                Constants.setParams(params);
                MainActivity.openFragment(new TipsFragment());
                break;
            case 3:
                params.put("token", Constants.token);
                params.put("favourite", true);
                Constants.setParams(params);
                MainActivity.openFragment(new TipsFragment());
                break;
            case 4:
                MainActivity.openFragment(new UnPurchaseMainFragment());
                break;
            case 5:
                MainActivity.openFragment(new PurchaseMainFragment());
                break;
            default:
                break;
        }
    }
}

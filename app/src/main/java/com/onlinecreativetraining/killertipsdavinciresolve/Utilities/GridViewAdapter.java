package com.onlinecreativetraining.killertipsdavinciresolve.Utilities;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.onlinecreativetraining.killertipsdavinciresolve.R;

/**
 * Created by RED on 9/7/2016.
 */

public class GridViewAdapter extends BaseAdapter {

    private Context mContext;
    private String[] data;

    public GridViewAdapter(Context context, String[] data)  {
        this.mContext = context;
        this.data = data;
    }

    public int getCount() {
        return data.length;
    }

    public Object getItem(int position) {
        return data[position];
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.gridview_item, null);
        }

        TextView textView = (TextView)convertView.findViewById(R.id.textView);
        Typeface custom_font1 = Typeface.createFromAsset(mContext.getAssets(), "fonts/Futura.ttf");
        if (custom_font1 != null)
            Log.d("status", "success");
        textView.setTypeface(custom_font1);

        textView.setText(data[position]);
        return convertView;
    }
}

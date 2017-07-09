package com.onlinecreativetraining.killertipsdavinciresolve.Utilities;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.onlinecreativetraining.killertipsdavinciresolve.R;

import org.w3c.dom.Text;

public class TextAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private String[] datas = new String[]{};

    public TextAdapter(Context c, String[] data) {
        mContext = c;
        mInflater = (LayoutInflater) c
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        datas = data;
    }

    public int getCount() {
        return datas.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.gridview_item, null);
            holder.textView = (TextView)convertView.findViewById(R.id.textView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.textView.setText(datas[position]);
        return convertView;
    }

    public static class ViewHolder {
        public TextView textView;
    }
}
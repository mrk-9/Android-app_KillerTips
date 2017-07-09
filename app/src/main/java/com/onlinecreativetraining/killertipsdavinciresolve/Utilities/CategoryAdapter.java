package com.onlinecreativetraining.killertipsdavinciresolve.Utilities;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.onlinecreativetraining.killertipsdavinciresolve.DataModel.Category;
import com.onlinecreativetraining.killertipsdavinciresolve.R;

import java.util.ArrayList;

/**
 * Created by Alex on 8/25/2016.
 */
public class CategoryAdapter extends BaseAdapter{
    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<Category> datas = new ArrayList<>();

    public CategoryAdapter(Context c, ArrayList<Category> data) {
        mContext = c;
        mInflater = (LayoutInflater) c
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        datas = data;
    }

    public int getCount() {
        return datas.size();
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
            Typeface custom_font1 = Typeface.createFromAsset(mContext.getAssets(), "fonts/Futura.ttf");
            if (custom_font1 != null)
                Log.d("status", "success");
            holder.textView.setTypeface(custom_font1);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.textView.setText(datas.get(position).getName());
        return convertView;
    }

    public static class ViewHolder {
        public TextView textView;
    }
}

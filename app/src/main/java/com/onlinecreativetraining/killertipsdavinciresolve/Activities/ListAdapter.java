package com.onlinecreativetraining.killertipsdavinciresolve.Activities;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.onlinecreativetraining.killertipsdavinciresolve.R;

/**
 * Created by RED on 9/7/2016.
 */

public class ListAdapter extends BaseAdapter {

    private Context context;
    private String[] data;

    public ListAdapter(Context context,String[] data)
    {
        this.context = context;
        this.data = data;
    }
    @Override
    public int getCount()
    {
        return data.length;
    }

    @Override
    public Object getItem(int position) {
        return data[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.menu_item, parent, false);
        }

        TextView textView = (TextView) convertView.findViewById(R.id.text1);
        Typeface custom_font1 = Typeface.createFromAsset(context.getAssets(), "fonts/Futura.ttf");
        if (custom_font1 != null)
            Log.d("status", "success");
        textView.setTypeface(custom_font1);
        textView.setText(data[position]);

        ImageView imageView = (ImageView) convertView.findViewById(R.id.menu_img);

        if (position == 0)
        {
            imageView.setVisibility(View.VISIBLE);
        } else {
            imageView.setVisibility(View.GONE);
        }
        return convertView;
    }

}

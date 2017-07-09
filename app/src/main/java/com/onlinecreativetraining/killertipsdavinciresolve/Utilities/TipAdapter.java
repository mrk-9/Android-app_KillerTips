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

import com.onlinecreativetraining.killertipsdavinciresolve.DataModel.Constants;
import com.onlinecreativetraining.killertipsdavinciresolve.DataModel.Tip;
import com.onlinecreativetraining.killertipsdavinciresolve.R;
import com.onlinecreativetraining.killertipsdavinciresolve.Services.ImageLoader;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;

public class TipAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<Tip> data = new ArrayList<>();

    public TipAdapter(Context c, ArrayList<Tip> dataList) {
        mContext = c;
        mInflater = (LayoutInflater) c
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        data = dataList;
    }

    public int getCount() {
        return data.size();
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
            convertView = mInflater.inflate(R.layout.tip_gridcell, null);
            holder.txtCaption = (TextView)convertView.findViewById(R.id.txtCaption);
            holder.txtCategory = (TextView)convertView.findViewById(R.id.txtCategory);
            holder.imgTip = (ImageView)convertView.findViewById(R.id.imgTip);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Tip tip = data.get(position);

        holder.txtCategory.setText(tip.getCategory());
        holder.txtCaption.setText(tip.getTitle());
        String photoURL = tip.getPhoto();
        if(!photoURL.equals(""))
            Picasso.with(mContext).load(tip.getPhoto()).placeholder(R.drawable.placeholder).into(holder.imgTip);

//        ImageLoader imgLoader = new ImageLoader(mContext);
//        int loader = R.drawable.placeholder;
//        imgLoader.DisplayImage(photoURL, loader, holder.imgTip);

        return convertView;
    }

    public static class ViewHolder {
        public TextView txtCaption;
        public TextView txtCategory;
        public ImageView imgTip;
    }
}
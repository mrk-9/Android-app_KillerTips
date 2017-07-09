package com.onlinecreativetraining.killertipsdavinciresolve.Utilities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.onlinecreativetraining.killertipsdavinciresolve.DataModel.Category;
import com.onlinecreativetraining.killertipsdavinciresolve.DataModel.PurchaseTip;
import com.onlinecreativetraining.killertipsdavinciresolve.R;

import java.util.ArrayList;
public class PurchaseAdapter extends BaseAdapter{
    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<PurchaseTip> datas = new ArrayList<>();

    public PurchaseAdapter(Context c, ArrayList<PurchaseTip> data) {
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
            convertView = mInflater.inflate(R.layout.purchase_item, null);
            holder.name = (TextView)convertView.findViewById(R.id.txtPackName);
            holder.cost = (TextView)convertView.findViewById(R.id.txtCost);
            holder.detail = (TextView)convertView.findViewById(R.id.txtPackDetail);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.name.setText(datas.get(position).getName());
        holder.cost.setText("$" + datas.get(position).getPrice());
        holder.detail.setText(datas.get(position).getDetail());
        return convertView;
    }

    public static class ViewHolder {
        public TextView name;
        public TextView cost;
        public TextView detail;
    }
}

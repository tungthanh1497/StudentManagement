package com.ttc.tungtt.sm.commons.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ttc.tungtt.sm.R;
import com.ttc.tungtt.sm.models.SimpleModel;

import java.util.List;

/**
 * Created by ttcandroid a.k.a TungTT
 * On Sat, 03 Oct 2020 - 08:27
 */
public class SimpleSpinnerAdapter extends ArrayAdapter<SimpleModel> {

    private Activity mActivity;
    private List<SimpleModel> mList;

    public SimpleSpinnerAdapter(@NonNull Activity activity, @NonNull List<SimpleModel> list) {
        super(activity, R.layout.item_simple_spinner, list);
        this.mActivity = activity;
        this.mList = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            LayoutInflater inflater = mActivity.getLayoutInflater();
            convertView = inflater.inflate(R.layout.item_simple_spinner, parent, false);
            holder = new ViewHolder();
            holder.textView = convertView.findViewById(R.id.tv_name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.textView.setText(mList.get(position).getName());
        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            LayoutInflater inflater = mActivity.getLayoutInflater();
            convertView = inflater.inflate(R.layout.item_simple_spinner, parent, false);
            holder = new ViewHolder();
            holder.textView = convertView.findViewById(R.id.tv_name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.textView.setText(mList.get(position).getName());
        return convertView;
    }

    private class ViewHolder {
        private TextView textView;
    }
}

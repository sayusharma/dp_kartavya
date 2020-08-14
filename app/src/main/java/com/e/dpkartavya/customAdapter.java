package com.e.dpkartavya;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class customAdapter extends ArrayAdapter<CustomItem>{
    public customAdapter(@NonNull Context context, ArrayList<CustomItem> customList) {
        super(context,  0,customList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView==null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.custom_spinner_layout,parent,false);
        }
        CustomItem item = getItem(position);
        TextView sniperTV = convertView.findViewById(R.id.tvsniperlayout);
        if (item!=null) {
            sniperTV.setText(item.getSpinnerItemName());
        }
        return convertView;
    }
    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView==null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.custom_dropdown_layout,parent,false);
        }
        CustomItem item = getItem(position);
        TextView dropTV = convertView.findViewById(R.id.tvdrop);
        if (item!=null) {
            dropTV.setText(item.getSpinnerItemName());
        }
        return convertView;
    }
}


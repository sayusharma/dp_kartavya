package com.e.dpkartavya.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.e.dpkartavya.Interface.ItemClickListener;
import com.e.dpkartavya.R;

public class VerificationHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    public TextView name,mob,date,time,addr;
    private ItemClickListener itemClickListener;
    public ImageView img;
    public VerificationHolder(@NonNull View itemView) {
        super(itemView);
        name = (TextView)itemView.findViewById(R.id.verName);
        mob = (TextView) itemView.findViewById(R.id.verMob);
        img = (ImageView) itemView.findViewById(R.id.verImg);
        date = itemView.findViewById(R.id.verDate);
        time = itemView.findViewById(R.id.verTime);
        addr = itemView.findViewById(R.id.verAddress);
        itemView.setOnClickListener(this);
    }
    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;

    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v, getAdapterPosition(), false);
    }
}

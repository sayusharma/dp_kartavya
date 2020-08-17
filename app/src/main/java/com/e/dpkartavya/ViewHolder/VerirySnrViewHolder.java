package com.e.dpkartavya.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.e.dpkartavya.Interface.ItemClickListener;
import com.e.dpkartavya.R;

public class VerirySnrViewHolder  extends RecyclerView.ViewHolder implements View.OnClickListener{
    public TextView name,mob;
    private ItemClickListener itemClickListener;
    public ImageView img;
    public VerirySnrViewHolder(@NonNull View itemView) {
        super(itemView);
        name = (TextView)itemView.findViewById(R.id.snrCznName);
        mob = (TextView) itemView.findViewById(R.id.snrCznMob);
        img = (ImageView) itemView.findViewById(R.id.snrCznImg);
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

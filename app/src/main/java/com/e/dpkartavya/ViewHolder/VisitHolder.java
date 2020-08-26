package com.e.dpkartavya.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.e.dpkartavya.Interface.ItemClickListener;
import com.e.dpkartavya.R;

public class VisitHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    public TextView name,mob,date,time,addr,notes;
    private ItemClickListener itemClickListener;
    public ImageView img;
    public VisitHolder(@NonNull View itemView) {
        super(itemView);
        name = (TextView)itemView.findViewById(R.id.visitName);
        mob = (TextView) itemView.findViewById(R.id.visitMob);
        img = (ImageView) itemView.findViewById(R.id.visitImg);
        date = itemView.findViewById(R.id.visitDate);
        time = itemView.findViewById(R.id.visitTime);
        addr = itemView.findViewById(R.id.visitAddress);
        notes = itemView.findViewById(R.id.visitNotes);
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

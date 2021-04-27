package com.app.monitoring;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

class AdViewHolder extends RecyclerView.ViewHolder {
    private final TextView adItemView;

    private AdViewHolder(View itemView) {
        super(itemView);
        adItemView = itemView.findViewById(R.id.adView);
    }

    public void bind(String text) {
        adItemView.setText(text);
    }

    static AdViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_ad, parent, false);
        return new AdViewHolder(view);
    }
}

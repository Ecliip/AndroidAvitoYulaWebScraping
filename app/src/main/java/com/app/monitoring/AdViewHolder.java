package com.app.monitoring;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.app.monitoring.databinding.RecyclerviewAdBinding;

class AdViewHolder extends RecyclerView.ViewHolder {
    private RecyclerviewAdBinding binding;
    private final TextView adItemView;
    private final ImageButton deleteBtn;
    private final ImageButton saveBtn;
    private final ImageButton openBtn;

    private AdViewHolder(View itemView) {
        super(itemView);
        binding = RecyclerviewAdBinding.bind(itemView);
        adItemView = binding.adTitle;
        deleteBtn = binding.deleteBtn;
        saveBtn = binding.saveBtn;
        openBtn = binding.openBtn;

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Delete Btn clicked");
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("save Btn clicked");
            }
        });

        openBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Open btn clicked");
            }
        });
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

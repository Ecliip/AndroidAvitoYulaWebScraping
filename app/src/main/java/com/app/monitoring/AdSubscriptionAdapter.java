package com.app.monitoring;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.app.monitoring.databinding.RecyclerviewSubscriptionBinding;

public class AdSubscriptionAdapter extends ListAdapter<AdSubscription, AdSubscriptionAdapter.AdViewHolder> {

    static AdSubscriptionClickInterface adSubscriptionClickInterface;
    private static long subscriptionId;
    private static String subscriptionName;

    public AdSubscriptionAdapter(@NonNull DiffUtil.ItemCallback<AdSubscription> diffCallback, AdSubscriptionClickInterface adSubscriptionClickInterface) {
        super(diffCallback);
        this.adSubscriptionClickInterface = adSubscriptionClickInterface;
    }

    @Override
    public AdViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return AdViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(AdViewHolder holder, int position) {
        AdSubscription current = getItem(position);
        holder.bind(current.getName(), current.getUrl());
        subscriptionId = current.getId();
        subscriptionName = current.getName();
        System.out.println("id."+ subscriptionId);
    }

    static class AdDiff extends DiffUtil.ItemCallback<AdSubscription> {

        @Override
        public boolean areItemsTheSame(@NonNull AdSubscription oldItem, @NonNull AdSubscription newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull AdSubscription oldItem, @NonNull AdSubscription newItem) {
            return oldItem.getId() == newItem.getId();
        }
    }

    static class AdViewHolder extends RecyclerView.ViewHolder {
        private RecyclerviewSubscriptionBinding binding;
        private final TextView subscriptionName;
        private final TextView subscriptionUrl;
        private final ImageButton deleteBtn;
        private final ImageButton settingsBtn;

        private AdViewHolder(View itemView) {
            super(itemView);

            binding = RecyclerviewSubscriptionBinding.bind(itemView);
            subscriptionName = binding.textSubscriptionName;
            subscriptionUrl = binding.textSubscriptionUrl;

            deleteBtn = binding.deleteSubscriptionButton;
            settingsBtn = binding.settingsSubscriptionButton;

            deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    adSubscriptionClickInterface.onDelete(getAdapterPosition());
                }
            });
            settingsBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    adSubscriptionClickInterface.onEdit(getAdapterPosition());
                }
            });
        }

        public void bind(String name, String url) {
            subscriptionName.setText(name);
            subscriptionUrl.setText(url);
        }

        static AdViewHolder create(ViewGroup parent) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recyclerview_subscription, parent, false);
            return new AdViewHolder(view);
        }
    }

    interface AdSubscriptionClickInterface {
        public void onDelete(int position);
        public void onEdit(int position);
    }
}
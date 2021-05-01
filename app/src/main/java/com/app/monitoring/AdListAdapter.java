package com.app.monitoring;

import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

public class AdListAdapter extends ListAdapter<ScrapedAd, AdViewHolder> {



    public AdListAdapter(@NonNull DiffUtil.ItemCallback<ScrapedAd> diffCallback) {
        super(diffCallback);
    }

    @Override
    public AdViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return AdViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(AdViewHolder holder, int position) {
        ScrapedAd current = getItem(position);
        holder.bind(current.getName());
    }

    static class AdDiff extends DiffUtil.ItemCallback<ScrapedAd> {

        @Override
        public boolean areItemsTheSame(@NonNull ScrapedAd oldItem, @NonNull ScrapedAd newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull ScrapedAd oldItem, @NonNull ScrapedAd newItem) {
            return oldItem.getAvito_ad_id().equals(newItem.getAvito_ad_id());
        }
    }
}

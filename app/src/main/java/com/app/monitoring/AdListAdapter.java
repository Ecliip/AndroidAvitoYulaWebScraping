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

import com.app.monitoring.databinding.RecyclerviewAdBinding;

public class AdListAdapter extends ListAdapter<ScrapedAd, AdListAdapter.AdViewHolder> {

    static AdClickInterface adClickInterface;
    private static String adId;
    private static String adName;

    public AdListAdapter(@NonNull DiffUtil.ItemCallback<ScrapedAd> diffCallback, AdClickInterface adClickInterface) {
        super(diffCallback);
        this.adClickInterface = adClickInterface;
    }

    @Override
    public AdViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return AdViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(AdViewHolder holder, int position) {
        ScrapedAd current = getItem(position);
            holder.bind(current.getName());
            adId = current.getAvito_ad_id();
            adName = current.getName();
            System.out.println("id."+adId);
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

    static class AdViewHolder extends RecyclerView.ViewHolder {
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
                    adClickInterface.onDelete(getAdapterPosition(), adId, adName);
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

    interface AdClickInterface {
        public void onDelete(int position, String adId, String adName);
    }
}

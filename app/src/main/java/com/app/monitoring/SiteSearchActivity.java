package com.app.monitoring;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.monitoring.databinding.ActivitySiteSearchBinding;

public class SiteSearchActivity extends AppCompatActivity implements AdListAdapter.AdClickInterface {
    public static final String START_BTN_VISIBILITY = "startBtnVisibility";
    public static final String STOP_BTN_VISIBILITY = "stopBtnVisibility";
    public static final String HEADING_TEXT = "headingText";
    private ActivitySiteSearchBinding binding;

    public static String EXTRA_TARGET_URL = "targetUrl";
    private AdViewModel mAdViewModel;
    private ScrapedAdViewModel mScrapedAdViewModel;
    public static final int NEW_AD_ACTIVITY_REQUEST_CODE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySiteSearchBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        if (savedInstanceState != null) {
            binding.startBtn.setVisibility(savedInstanceState.getInt(START_BTN_VISIBILITY));
            binding.stopBtn.setVisibility(savedInstanceState.getInt(STOP_BTN_VISIBILITY));
            binding.headingText.setText(savedInstanceState.getString(HEADING_TEXT));
        }

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final AdListAdapter adapter = new AdListAdapter(new AdListAdapter.AdDiff(), (AdListAdapter.AdClickInterface) this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mAdViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())).get(AdViewModel.class);
        mScrapedAdViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())).get(ScrapedAdViewModel.class);

        mScrapedAdViewModel.getAllAds().observe(this, words -> {
            // Update the cached copy of the words in the adapter.
            adapter.submitList(words);
        });
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(START_BTN_VISIBILITY, binding.startBtn.getVisibility());
        outState.putInt(STOP_BTN_VISIBILITY, binding.stopBtn.getVisibility());
        outState.putString(HEADING_TEXT, binding.headingText.getText().toString());
    }

    public void onClickStart(View view) {
        startScanning();
        binding.startBtn.setVisibility(View.GONE);
        binding.headingText.setText(R.string.headingSearchingOnStart);
        binding.stopBtn.setVisibility(View.VISIBLE);
    }

    public void onClickStop(View view) {
        binding.stopBtn.setVisibility(View.GONE);
        binding.headingText.setText(R.string.headingSearchingOnStop);
        binding.startBtn.setVisibility(View.VISIBLE);
    }

    public void startScanning() {
        mAdViewModel.removeAllAds();
        mScrapedAdViewModel.removeAllAds();

        final TextView targetUrlView = findViewById(R.id.headingText);
        Intent intent = getIntent();
        String targetUlrText = intent.getStringExtra(EXTRA_TARGET_URL);
        targetUrlView.setText(targetUlrText);

        mAdViewModel.callWorkManager(targetUlrText);
        AdScraper scraper = new AdScraper(getApplicationContext());
        scraper.scan(targetUlrText);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        mAdViewModel.removeAllAds();
//        mScrapedAdViewModel.removeAllAds();
    }


    @Override
    public void onDelete(int position, String adId) {
        System.out.println(String.format("ad-id: %s", adId));
    }
}
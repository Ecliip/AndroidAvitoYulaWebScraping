package com.app.minitoring;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class AvitoSearchActivity extends AppCompatActivity {
    public static String EXTRA_TARGET_URL = "targetUrl";
    private AdViewModel mAdViewModel;
    private ScrapedAdViewModel mScrapedAdViewModel;
    public static final int NEW_AD_ACTIVITY_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avito_search);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final AdListAdapter adapter = new AdListAdapter(new AdListAdapter.AdDiff());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mAdViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())).get(AdViewModel.class);
        mScrapedAdViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())).get(ScrapedAdViewModel.class);

        mScrapedAdViewModel.getAllAds().observe(this, words -> {
            // Update the cached copy of the words in the adapter.
            adapter.submitList(words);
        });
    }



    public void onClickDeleteData(View view) {
                mAdViewModel.removeAllAds();
                mScrapedAdViewModel.removeAllAds();
    }

    public void onClickScan(View view) {
//        startScanning();
        mAdViewModel.removeAllAds();
        mScrapedAdViewModel.removeAllAds();

//        LinearLayout containerLayout = findViewById(R.id.main_container);

        final TextView targetUrlView = findViewById(R.id.current_url);
        Intent intent = getIntent();
        String targetUlrText = intent.getStringExtra(EXTRA_TARGET_URL);
        targetUrlView.setText(targetUlrText);

        // TESTING
        mAdViewModel.callWorkManager(targetUlrText);

        AdScraper scraper = new AdScraper(getApplicationContext());

        scraper.scan(targetUlrText);
        // END-TESTING
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
////                        Log.i("title", titleText);
//                        for (AdModel categoryModel : titleList) {
//                            String adTitle = categoryModel.getName();
//                            String adIdByAvito = categoryModel.getId();
//                            Log.i("title", adTitle);
//                            Log.i("id", adIdByAvito);
//                            TextView nameView = new TextView(AvitoSearchActivity.this);
//                            TextView idView = new TextView(AvitoSearchActivity.this);
//                            nameView.setText(adTitle);
//                            idView.setText(adIdByAvito);
//                            containerLayout.addView(nameView);
//
//                            Ad ad = new Ad(adIdByAvito, adTitle, "unknown", EXTRA_TARGET_URL);
////                            mAdViewModel.insert(ad);
//
////                            containerLayout.addView(idView);
//                        }
//                    }
//                });
//            }
//        }).start();
    }

    public void startScanning() {
//        mAdViewModel.removeAllAds();
//        mScrapedAdViewModel.removeAllAds();
//
//        final TextView targetUrlView = findViewById(R.id.current_url);
//        Intent intent = getIntent();
//        String targetUlrText = intent.getStringExtra(EXTRA_TARGET_URL);
//        targetUrlView.setText(targetUlrText);
//
//        mAdViewModel.callWorkManager(targetUlrText);
//        AdScraper scraper = new AdScraper(getApplicationContext());
//        scraper.scan(targetUlrText);
    }

}
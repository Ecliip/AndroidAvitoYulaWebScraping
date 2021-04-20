package com.app.minitoring;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

public class AvitoSearchActivity extends AppCompatActivity {
    public static String EXTRA_TARGET_URL = "targetUrl";
    private AdViewModel mAdViewModel;
    private ScrapedAdViewModel mScrapedAdViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avito_search);
        mAdViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())).get(AdViewModel.class);
        mScrapedAdViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())).get(ScrapedAdViewModel.class);
    }

    public void onClickDeleteData(View view) {
                mAdViewModel.removeAllAds();
                mScrapedAdViewModel.removeAllAds();
    }

    public void onClickScan(View view) {
        LinearLayout containerLayout = findViewById(R.id.main_container);

        final TextView targetUrlView = findViewById(R.id.viewUrl);
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

}
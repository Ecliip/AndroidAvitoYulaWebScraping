package com.app.monitoring;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class AdWorker extends Worker {
    private static final String TAG = AdWorker.class.getSimpleName();
    private final AdDAO repo;
    private final String avitoBaseUrl;
    private final String CLASS_TAG = "AdWorker.class";

    public AdWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        repo = AppDatabase.getDatabase(getApplicationContext()).adDAO();
        Constants constants = new Constants();
        avitoBaseUrl = constants.getAVITO_BASE_URL();
    }
    @NonNull
    @Override
    public Result doWork() {
        String targetUrl = getInputData().getString("targetUrl");
        System.out.println(targetUrl);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Document doc = Jsoup.connect(targetUrl).get();
                    Elements ads = doc.select(".iva-item-root-G3n7v");
                    for (Element ad : ads) {
                        Element heading = ad.selectFirst(".iva-item-titleStep-2bjuh");
                        String headingHref = heading.selectFirst("a").attr("href");
                        String adName = ad.select("h3").text();
                        // avito ad id
                        String id = ad.attr("id");
                        headingHref = avitoBaseUrl.concat(headingHref);
                        Ad scrapedAd = new Ad(id, adName, headingHref, targetUrl);
                        Log.i("NEW ADD", String.format("%s: %s - %s", TAG, adName, id));
                        repo.insertAd(scrapedAd);
                    }
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        }).start();
        return Result.success();
    }
}
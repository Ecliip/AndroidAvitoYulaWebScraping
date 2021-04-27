package com.app.monitoring;

import android.content.Context;

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

        try {

            Document doc = Jsoup.connect(targetUrl)
                    .userAgent("Chrome/90.0.4430.85")
                    .referrer("http://www.google.com")
//                    .timeout(10000)
                    .get();
            Elements ads = doc.select(".iva-item-root-G3n7v");
            for (Element ad : ads) {
                Element heading = ad.selectFirst(".iva-item-titleStep-2bjuh");
                String headingHref = heading.selectFirst("a").attr("href");

                String adName = ad.select("h3").text();
                String id = ad.attr("id");
                headingHref = avitoBaseUrl.concat(headingHref);
                System.out.println(String.format("%s: %s - %s", CLASS_TAG, adName, id));
                Ad adRecord = new Ad(id, adName, headingHref, targetUrl);
                repo.insertAd(adRecord);
            }

            return Result.success();
        } catch (IOException e) {
            e.printStackTrace();
            return Result.failure();
        }

    }
}

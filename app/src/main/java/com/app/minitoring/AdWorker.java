package com.app.minitoring;

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
//    private long counter = 1;

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
                    .timeout(60000)
                    .get();
            Elements ads = doc.select(".iva-item-root-G3n7v");
            for (Element ad : ads) {
                Element heading = ad.selectFirst(".iva-item-titleStep-2bjuh");
                String headingHref = heading.selectFirst("a").attr("href");

                String adName = ad.select("h3").text();
                String id = ad.attr("id");
                AdModel aCategory = new AdModel(adName, id);
                System.out.println(adName);
                headingHref = avitoBaseUrl.concat(headingHref);
                System.out.println(headingHref);
                Ad adRecord = new Ad(id, adName, headingHref, targetUrl);
                repo.insertAd(adRecord);
            }


            return Result.success();
//                    titleText = title.text();
        } catch (IOException e) {
            e.printStackTrace();
            return Result.failure();
        }

    }

}

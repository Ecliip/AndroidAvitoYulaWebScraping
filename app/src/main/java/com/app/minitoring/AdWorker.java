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
import java.util.ArrayList;
import java.util.List;

public class AdWorker extends Worker {
    private static final String TAG = AdWorker.class.getSimpleName();
    private final AdDAO repo;
//    private long counter = 1;

    public AdWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        repo = AppDatabase.getDatabase(getApplicationContext()).adDAO();
    }

    @NonNull
    @Override
    public Result doWork() {
        Context applicationContext = getApplicationContext();
        String targetUrl = getInputData().getString("targetUrl");
        final List<AdModel> adTitles = new ArrayList<>();
        System.out.println(targetUrl);

        try {
            Document doc = Jsoup.connect(targetUrl)
                    .timeout(60000)
                    .get();
            Elements ads = doc.select(".iva-item-root-G3n7v");
            for (Element ad : ads) {
                String adName = ad.select("h3").text();
                String id = ad.attr("id");
                AdModel aCategory = new AdModel(adName, id);
                adTitles.add(aCategory);
                System.out.println(aCategory.getName());
                Ad adRecord = new Ad(id, adName, "CHANGE", targetUrl);
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

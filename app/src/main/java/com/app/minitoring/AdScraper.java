package com.app.minitoring;


import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class AdScraper {
    private AdRepository repository;
    private boolean run = false;
    private final String avitoBaseUrl;
    private final ScrapedAdDao scrapedAdDao;
    private final AdDAO adDao;
    private final String TAG = "AdScraper.class";

//    public void AdRepository(AdRepository repository) {
//        this.repository = repository;
//    }

    public AdScraper(Context applicationContext) {
        Constants constants = new Constants();
        avitoBaseUrl = constants.getAVITO_BASE_URL();
        scrapedAdDao = AppDatabase.getDatabase(applicationContext).scrapedAdDao();
        adDao = AppDatabase.getDatabase(applicationContext).adDAO();
    }

    public void scan(String targetUrl) {
       final Handler handler = new Handler(Looper.myLooper());
       handler.postDelayed (new Runnable() {
           @Override
           public void run() {
               // TEST SCAN
               new Thread(new Runnable() {
                   @Override
                   public void run() {
                       try {
                           Document doc = Jsoup.connect(targetUrl)
                                   .timeout(60000)
                                   .get();
                           Elements ads = doc.select(".iva-item-root-G3n7v");
                           for (Element ad : ads) {
                               Element heading = ad.selectFirst(".iva-item-titleStep-2bjuh");
                               String headingHref = heading.selectFirst("a").attr("href");
                               String adName = ad.select("h3").text();
                               // avito ad id
                               String id = ad.attr("id");
                               System.out.println(String.format("%s: %s", TAG, adName));
                               headingHref = avitoBaseUrl.concat(headingHref);
                               System.out.println(headingHref);
                               Ad adRecord = new Ad(id, adName, headingHref, targetUrl);
                               Ad result = adDao.checkIfExtists(id);
//                               Log.i("coincidence", result.name);

                           }
                           scrapedAdDao.insertAd(new ScrapedAd("fjdslfjsf", "jfslfjs", "jdslfjsl", "jfdslfjs"));
//                    titleText = title.text();
                       } catch (IOException e) {
                           e.printStackTrace();
                       }
                   }
               }).start();

               // END TEST SCAN
               handler.postDelayed(this, 30000);
           }
       },30000);
    }
    public AdRepository getRepository() {
        return repository;
    }

    public void setRepository(AdRepository repository) {
        this.repository = repository;
    }
}

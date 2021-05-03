package com.app.monitoring;


import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

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
//                                    .userAgent("Chrome/90.0.4430.85")
//                                    .referrer("http://www.google.com")
//                                   .timeout(10000)
                                    .get();
                            Elements ads = doc.select(".iva-item-root-G3n7v");
                            for (Element ad : ads) {
                                Element heading = ad.selectFirst(".iva-item-titleStep-2bjuh");
                                String headingHref = heading.selectFirst("a").attr("href");
                                String adName = ad.select("h3").text();
                                // avito ad id
                                String id = ad.attr("id");
                                headingHref = avitoBaseUrl.concat(headingHref);
                                ScrapedAd scrapedAd = new ScrapedAd(id, adName, headingHref, targetUrl, false);
                                Ad adResult = adDao.checkIfExtists(scrapedAd.getAvito_ad_id());
                                ScrapedAd scrapedAdResult = scrapedAdDao.checkIfExists(id);

                                if (adResult == null && scrapedAdResult == null) {
                                    Log.i("NEW ADD", String.format("%s: %s - %s", TAG, adName, id));
                                    scrapedAdDao.insertAd(scrapedAd);
                                }
                            }
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

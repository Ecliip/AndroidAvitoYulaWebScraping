package com.app.minitoring;


import android.os.Handler;
import android.os.Looper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AdScraper {
    private AdRepository repository;
    private boolean run = false;
    private List<AdModel> adTitles = new ArrayList<>();

//    public void AdRepository(AdRepository repository) {
//        this.repository = repository;
//    }

    public void scan(String targetUrl) {
       final Handler handler = new Handler(Looper.myLooper());
       handler.post(new Runnable() {
           @Override
           public void run() {
//               int random = new Random().nextInt();
//               String counterVal = Integer.toString(random);
//               System.out.println(random);
//
//               Ad ad = new Ad("fakeId-" + counterVal, counterVal, "fakeUrl"+ counterVal, "fakeQuery"+ counterVal);

               // TEST SCAN
               new Thread(new Runnable() {
                   @Override
                   public void run() {
                       try {
                           Document doc = Jsoup.connect(targetUrl).timeout(60000).get();
                           Elements ads = doc.select(".iva-item-root-G3n7v");
                           for (Element ad : ads) {
                               String adName = ad.select("h3").text();
                               String id = ad.attr("id");
                               AdModel aCategory = new AdModel(adName, id);
                               adTitles.add(aCategory);
                               System.out.println(aCategory.getName());
                           }
//                    titleText = title.text();
                       } catch (IOException e) {
                           e.printStackTrace();
                       }
                   }
               }).start();

               // END TEST SCAN
               handler.postDelayed(this, 30000);
           }
       });
    }
    public AdRepository getRepository() {
        return repository;
    }

    public void setRepository(AdRepository repository) {
        this.repository = repository;
    }
}

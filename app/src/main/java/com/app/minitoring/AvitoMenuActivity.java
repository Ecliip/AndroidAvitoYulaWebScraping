package com.app.minitoring;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AvitoMenuActivity extends Activity {
    List<String> titleList = new ArrayList<>();
//    LinearLayout container = findViewById(R.id.container);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avito_menu);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Document doc = Jsoup.connect("https://www.avito.ru/rossiya").timeout(60000).get();
                    Elements titles = doc.select(".footer-rubricator-block-230_v > ul > li > a");
                    for (Element title : titles) {
                        titleList.add(title.text());
                    }
//                    titleText = title.text();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        Log.i("title", titleText);
                        for (String title : titleList) {
                            Log.i("title", title);
                        }
                    }
                });
            }
        }).start();
    }
}
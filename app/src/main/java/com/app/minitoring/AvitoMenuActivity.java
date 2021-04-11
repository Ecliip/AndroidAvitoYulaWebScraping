package com.app.minitoring;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class AvitoMenuActivity extends Activity {
    String titleText = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avito_menu);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Document doc = Jsoup.connect("https://www.avito.ru/rossiya").timeout(60000).get();
                    Elements title = doc.select(".top-banner-text-GCw-N");
                    titleText = title.text();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.i("title", titleText);
                    }
                });
            }
        }).start();
    }
}
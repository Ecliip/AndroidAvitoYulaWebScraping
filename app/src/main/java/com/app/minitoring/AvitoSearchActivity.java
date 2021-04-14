package com.app.minitoring;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.minitoring.data.AdModel;
import com.app.minitoring.data.AppDatabase;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AvitoSearchActivity extends Activity {
    public static String EXTRA_TARGET_URL = "targetUrl";
    List<AdModel> titleList = new ArrayList<>();
    private AppDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avito_search);
        LinearLayout containerLayout = findViewById(R.id.main_container);

        final TextView targetUrlView = findViewById(R.id.viewUrl);
        Intent intent = getIntent();
        String targetUlrText = intent.getStringExtra(EXTRA_TARGET_URL);
        targetUrlView.setText(targetUlrText);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Document doc = Jsoup.connect(targetUlrText).timeout(60000).get();
                    Elements ads = doc.select(".iva-item-root-G3n7v");
                    for (Element ad : ads) {
                        String adName = ad.select("h3").text();
                        String id = ad.attr("id");
                        AdModel aCategory = new AdModel(adName, id);
                        titleList.add(aCategory);
                    }
//                    titleText = title.text();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        Log.i("title", titleText);
                        for (AdModel categoryModel : titleList) {
                            Log.i("title", categoryModel.getName());
                            Log.i("id", categoryModel.getId());
                            TextView nameView = new TextView(AvitoSearchActivity.this);
                            TextView idView = new TextView(AvitoSearchActivity.this);
                            nameView.setText(categoryModel.getName());
                            idView.setText(categoryModel.getId());
                            containerLayout.addView(nameView);
//                            containerLayout.addView(idView);
                        }
//                        containerLayout.addView(linearLayout);
                    }
                });
            }
        }).start();
    }

//    private void saveData() {
//
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                Person person = new Person(mlastname, mfirstname);
//                PersonDAO dao = db.getPersonDAO();
//                dao.insertPerson(person);
//                List<Person> people = dao.listPeople();
//                for(Person p:people) {
//                    System.out.printf("%s , %s\n", p.last_name, p.first_name );
//                }
//            }
//        }).start();
//    }

}
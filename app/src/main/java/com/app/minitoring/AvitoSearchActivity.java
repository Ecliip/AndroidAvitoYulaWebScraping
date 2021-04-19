package com.app.minitoring;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.List;

public class AvitoSearchActivity extends AppCompatActivity {
    public static String EXTRA_TARGET_URL = "targetUrl";
    List<AdModel> titleList = new ArrayList<>();
    private AdViewModel mAdViewModel;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avito_search);
        mAdViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())).get(AdViewModel.class);
    }

    public void onClickDeleteData(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                mAdViewModel.removeAllAds();
            }
        }).start();
    }

    public void onClickScan(View view) {
        LinearLayout containerLayout = findViewById(R.id.main_container);

        final TextView targetUrlView = findViewById(R.id.viewUrl);
        Intent intent = getIntent();
        String targetUlrText = intent.getStringExtra(EXTRA_TARGET_URL);
        targetUrlView.setText(targetUlrText);


        // TESTING
//        AdScraper scraper = new AdScraper();
//        scraper.scan(targetUlrText);
        mAdViewModel.callWorkManager(targetUlrText);
        // END-TESTING


//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Document doc = Jsoup.connect(targetUlrText).timeout(60000).get();
//                    Elements ads = doc.select(".iva-item-root-G3n7v");
//                    for (Element ad : ads) {
//                        String adName = ad.select("h3").text();
//                        String id = ad.attr("id");
//                        AdModel aCategory = new AdModel(adName, id);
//                        titleList.add(aCategory);
//                    }
////                    titleText = title.text();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }





//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
////                        Log.i("title", titleText);
//                        for (AdModel categoryModel : titleList) {
//                            String adTitle = categoryModel.getName();
//                            String adIdByAvito = categoryModel.getId();
//                            Log.i("title", adTitle);
//                            Log.i("id", adIdByAvito);
//                            TextView nameView = new TextView(AvitoSearchActivity.this);
//                            TextView idView = new TextView(AvitoSearchActivity.this);
//                            nameView.setText(adTitle);
//                            idView.setText(adIdByAvito);
//                            containerLayout.addView(nameView);
//
//                            Ad ad = new Ad(adIdByAvito, adTitle, "unknown", EXTRA_TARGET_URL);
////                            mAdViewModel.insert(ad);
//
////                            containerLayout.addView(idView);
//                        }
//                    }
//                });
//            }
//        }).start();
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
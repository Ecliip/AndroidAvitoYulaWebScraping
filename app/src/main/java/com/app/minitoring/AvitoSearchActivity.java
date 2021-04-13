package com.app.minitoring;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.app.minitoring.category.AppDatabase;
import com.app.minitoring.category.CategoryModel;

import java.util.ArrayList;
import java.util.List;

public class AvitoSearchActivity extends Activity {
    public static String EXTRA_TARGET_URL = "targetUrl";
    List<CategoryModel> titleList = new ArrayList<>();
    private AppDatabase db;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avito_search);
//        LinearLayout containerLayout = findViewById(R.id.main_container);

        final TextView targetUrlView = findViewById(R.id.viewUrl);
        Intent intent = getIntent();
        String targetUlrText = intent.getStringExtra(EXTRA_TARGET_URL);
        targetUrlView.setText("https://www.avito.ru/moskva/zapchasti_i_aksessuary/instrumenty-ASgBAgICAUQKxk0?cd=1&s=104");

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Document doc = Jsoup.connect("https://www.avito.ru/rossiya").timeout(60000).get();
//                    Elements titles = doc.select(".footer-rubricator-block-230_v > ul > li > a");
//                    for (Element title : titles) {
//                        String url = String.format("%s%s", "https://www.avito.ru", title.attr("href"));
//                        CategoryModel aCategory = new CategoryModel(title.text(), url);
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
//                        for (CategoryModel categoryModel : titleList) {
//                            Log.i("Category Name", categoryModel.getName());
//                            Log.i("Category Url", categoryModel.getUrl());
//                            Switch aSwitch = new Switch(AvitoSearchActivity.this);
//                            TextView textView = new TextView(AvitoSearchActivity.this);
//                            aSwitch.setText(categoryModel.getName());
//                            textView.setText(categoryModel.getUrl());
//                            containerLayout.addView(aSwitch);
//                            containerLayout.addView(textView);
//                        }
////                        containerLayout.addView(linearLayout);
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
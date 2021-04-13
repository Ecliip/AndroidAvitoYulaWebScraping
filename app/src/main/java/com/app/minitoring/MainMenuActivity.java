package com.app.minitoring;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class MainMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal_menu);
    }

    public void onClickSearch(View view) {
        EditText editViewUrl  = findViewById(R.id.editViewUrl);
        String targetUrl = editViewUrl.getText().toString();
        Intent intent = new Intent(this, AvitoSearchActivity.class);
        intent.putExtra(AvitoSearchActivity.EXTRA_TARGET_URL, targetUrl);
        startActivity(intent);
    }
}
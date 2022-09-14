package com.example.vietlottdatacrawl.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.vietlottdatacrawl.R;
import com.example.vietlottdatacrawl.asynctask.DataCrawlAsyncTask;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.crawl_button);

        button.setOnClickListener((View v) -> crawlData());

    }

    public void crawlData() {
        DataCrawlAsyncTask dataCrawlAsyncTask = new DataCrawlAsyncTask(this);
        dataCrawlAsyncTask.execute();
    }
}
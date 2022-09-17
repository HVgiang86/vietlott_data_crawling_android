package com.example.vietlottdatacrawl.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.vietlottdatacrawl.R;
import com.example.vietlottdatacrawl.asynctask.DataCrawlAsyncTask;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        crawlData();

    }

    public void crawlData() {
        DataCrawlAsyncTask dataCrawlAsyncTask = new DataCrawlAsyncTask(this);
        dataCrawlAsyncTask.execute();
    }
}
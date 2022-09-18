package com.example.vietlottdatacrawl.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;

import com.example.vietlottdatacrawl.asynctask.DataCrawlAsyncTask;

import java.lang.ref.WeakReference;

public class DataLoadingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        crawlData();

    }

    public void crawlData() {
        WeakReference<Context> contextWeakReference = new WeakReference<>(this);
        DataCrawlAsyncTask dataCrawlAsyncTask = new DataCrawlAsyncTask(contextWeakReference);
        dataCrawlAsyncTask.execute();
    }
}
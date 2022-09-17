package com.example.vietlottdatacrawl.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.vietlottdatacrawl.R;
import com.example.vietlottdatacrawl.adapter.Max3DResultAdapter;
import com.example.vietlottdatacrawl.asynctask.DataCrawlAsyncTask;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Max3DResultAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        crawlData();
        recyclerView = findViewById(R.id.recycler_view);
        adapter = new Max3DResultAdapter(this);
        recyclerView.setAdapter(adapter);

    }

    public void crawlData() {
        DataCrawlAsyncTask dataCrawlAsyncTask = new DataCrawlAsyncTask(this);
        dataCrawlAsyncTask.execute();
    }
}
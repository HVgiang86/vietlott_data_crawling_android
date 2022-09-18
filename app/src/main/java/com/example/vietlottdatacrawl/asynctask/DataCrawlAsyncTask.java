package com.example.vietlottdatacrawl.asynctask;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;


import com.example.vietlottdatacrawl.activity.MainActivity;
import com.example.vietlottdatacrawl.model.PrizeDrawSession;
import com.example.vietlottdatacrawl.model.SessionManager;
import com.example.vietlottdatacrawl.utilities.VietlottDataCrawler;
import com.example.vietlottdatacrawl.utilities.VietlottDataCrawlerWithAPI;

import java.lang.ref.WeakReference;
import java.util.List;

public class DataCrawlAsyncTask extends AsyncTask<Void, Void, Void> {
    private final WeakReference<Context> context;
    private List<PrizeDrawSession> sessionList = null;
    private boolean isDataCrawled = false;

    public DataCrawlAsyncTask(WeakReference<Context> context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }


    @Override
    protected Void doInBackground(Void... voids) {
        crawlData();
        isDataCrawled = !(sessionList == null);
        return null;
    }

    private void crawlData() {
        //Crawl with html parser
        /*VietlottDataCrawler crawler = VietlottDataCrawler.getInstance();
        sessionList = crawler.getSessionList(context.get());*/

        //crawl with api
        VietlottDataCrawlerWithAPI crawlerWithAPI = VietlottDataCrawlerWithAPI.getInstance();
        sessionList = crawlerWithAPI.getSessionList(context.get());
    }

    @Override
    protected void onPostExecute(Void unused) {
        super.onPostExecute(unused);

        //Data is loaded successfully
        //display toast to notify
        if (isDataCrawled) {
            Toast.makeText(context.get(), "Data Crawl Successfully!", Toast.LENGTH_SHORT).show();
            SessionManager sessionManager = SessionManager.getInstance();
            sessionManager.setSessionList(sessionList);
            Log.d("DATA CRAWLER", "Got " + sessionList.size() + " sessions!");
        } else {
            Log.d("DATA CRAWLER", "Fail to crawl data!");
            Toast.makeText(context.get(), "Fail to crawl data!", Toast.LENGTH_SHORT).show();
        }

        //start main activity to display result
        Intent intent = new Intent(context.get(), MainActivity.class);
        context.get().startActivity(intent);
    }
}
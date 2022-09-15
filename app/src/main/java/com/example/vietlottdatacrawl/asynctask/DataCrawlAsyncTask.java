package com.example.vietlottdatacrawl.asynctask;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.vietlottdatacrawl.activity.ResultActivity;
import com.example.vietlottdatacrawl.model.PrizeDrawSession;
import com.example.vietlottdatacrawl.model.SessionManager;
import com.example.vietlottdatacrawl.utilities.VietlottDataCrawler;

import java.util.List;

public class DataCrawlAsyncTask extends AsyncTask<Void, Void, Void> {
    private final Context context;
    ProgressDialog progressDialog;
    private List<PrizeDrawSession> sessionList = null;
    private boolean isDataCrawled = false;


    public DataCrawlAsyncTask(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Data Crawling tool");
        progressDialog.setMessage("Crawling Data from website!");
        progressDialog.show();
    }


    @Override
    protected Void doInBackground(Void... voids) {
        long MAX_PROGRESS_TIME = 50*1000;
        long currentTime = System.currentTimeMillis();
        while (!isDataCrawled() && currentTime + MAX_PROGRESS_TIME >= System.currentTimeMillis()) {
            crawlData();
            return null;
        }
        isDataCrawled = true;
        return null;
    }

    private boolean isDataCrawled() {
        return !(sessionList == null);
    }

    private void crawlData() {
        VietlottDataCrawler crawler = VietlottDataCrawler.getInstance();
        sessionList = crawler.getSessionList();
    }


    @Override
    protected void onPostExecute(Void unused) {
        super.onPostExecute(unused);
        progressDialog.dismiss();
        if (isDataCrawled) {
            Toast.makeText(context, "Data Crawl Successfully!", Toast.LENGTH_SHORT).show();
            SessionManager sessionManager = SessionManager.getInstance();
            sessionManager.setSessionList(sessionList);
            Intent intent = new Intent(context, ResultActivity.class);
            context.startActivity(intent);
        }

        else
            Toast.makeText(context, "Timeout,fail to crawl data!", Toast.LENGTH_SHORT).show();
    }


}
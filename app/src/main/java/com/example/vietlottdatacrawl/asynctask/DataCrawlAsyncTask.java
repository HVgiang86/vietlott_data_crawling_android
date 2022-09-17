package com.example.vietlottdatacrawl.asynctask;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.vietlottdatacrawl.activity.MainActivity2;
import com.example.vietlottdatacrawl.adapter.Max3DResultAdapter;
import com.example.vietlottdatacrawl.model.PrizeDrawSession;
import com.example.vietlottdatacrawl.model.SessionManager;
import com.example.vietlottdatacrawl.utilities.VietlottDataCrawler;

import java.util.List;

public class DataCrawlAsyncTask extends AsyncTask<Void, Void, Void> {
    private Context context;
    private List<PrizeDrawSession> sessionList = null;
    private boolean isDataCrawled = false;


    public DataCrawlAsyncTask(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }


    @Override
    protected Void doInBackground(Void... voids) {
        while (!isDataCrawled()) {
            crawlData();
        }

        isDataCrawled = isDataCrawled();
        return null;
    }

    private boolean isDataCrawled() {
        return !(sessionList == null);
    }

    private void crawlData() {
        VietlottDataCrawler crawler = VietlottDataCrawler.getInstance();
        sessionList = crawler.getSessionList(context);
    }


    @Override
    protected void onPostExecute(Void unused) {
        super.onPostExecute(unused);
        Log.d("DATA CRAWLER","Got " + sessionList.size() + " sessions!");
        if (isDataCrawled) {
            Toast.makeText(context, "Data Crawl Successfully!", Toast.LENGTH_SHORT).show();
            SessionManager sessionManager = SessionManager.getInstance();
            sessionManager.setSessionList(sessionList);

            Intent intent = new Intent(context, MainActivity2.class);
            context.startActivity(intent);
        }

        else
            Toast.makeText(context, "Fail to crawl data!", Toast.LENGTH_SHORT).show();
    }

    public boolean getDataStatus() {
        return isDataCrawled;
    }
}
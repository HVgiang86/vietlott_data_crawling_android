package com.example.vietlottdatacrawl.asynctask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vietlottdatacrawl.model.PrizeDrawSession;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ParseUrl extends AsyncTask<String, Void, String> {
    private  Context context;
    ProgressDialog progressDialog;
    private TextView textView;
    private final String TAG = "DATA_CRAWLER";
    private final String URL_PREFIX = "https://vietlott.vn/vi/trung-thuong/ket-qua-trung-thuong/max-3D?id=";
    private final String URL_SUFFIX = "&nocatche=1";
    private final String USER_AGENT_STRING = "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:99.0) Gecko/20100101 Firefox/99.0";
    public ParseUrl(Context context, TextView textView) {
        this.context = context;
        this.textView = textView;
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
    protected String doInBackground(String... strings) {
        StringBuffer resultBuffer = new StringBuffer();
        PrizeDrawSession recentSession = getSessionInfo(strings[0]);

        int recentId = Integer.parseInt(recentSession.getId());
        int currentMonth = recentSession.getDate().getMonth();

        int id = recentId;
        int month = currentMonth;
        while (month == currentMonth) {
            StringBuffer urlBuffer = new StringBuffer();
            urlBuffer.append(URL_PREFIX);
            urlBuffer.append("00");
            urlBuffer.append(id--);
            urlBuffer.append(URL_SUFFIX);
            String url = urlBuffer.toString();
            PrizeDrawSession session = getSessionInfo(url);
            month = session.getDate().getMonth();

            if (month == currentMonth) {
                resultBuffer.append(session.toString());
                resultBuffer.append("\n");
            }
        }

        return resultBuffer.toString();
    }

    private PrizeDrawSession getSessionInfo(String url) {
        try {
            Log.d(TAG, "URL: " + url);
            Connection connection = Jsoup.connect(url);
            connection.userAgent(USER_AGENT_STRING);
            connection.timeout(30*1000);
            Document doc = connection.get();

            Element info = doc.getElementsByTag("h5").first();

            String[] prizeStrings = new String[4];
            Element prize = doc.getElementsByTag("tbody").first();
            Elements elements = prize.getElementsByTag("tr");

            for (int i = 0; i<4; ++i) {
                Elements collection = elements.get(i).getElementsByTag("td");
                prizeStrings[i] = collection.get(1).text();
            }

            return stringToSessionInfo(info.text(),prizeStrings);


        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private PrizeDrawSession stringToSessionInfo(String info, String[] prize) {
        int firstIndexOfId = info.indexOf("#");
        int lastIndexOfId = info.indexOf(" ", firstIndexOfId);
        String id = info.substring(firstIndexOfId+1, lastIndexOfId);

        int dateFirstIndex = info.indexOf("ngày") + 5;
        String dateStr = info.substring(dateFirstIndex);
        Date date = new Date();
        try {
            date = new SimpleDateFormat("dd/MM/yyyy").parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        PrizeDrawSession session = new PrizeDrawSession(date,id,prize[0],prize[1],prize[2],prize[3]);
        Log.d(TAG,session.toString());
        return session;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        progressDialog.dismiss();
        if (s.length() != 0)
            Toast.makeText(context, "Success!", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(context, "Fail!", Toast.LENGTH_SHORT).show();

        textView.setText(s);
    }
}
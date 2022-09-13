package com.example.vietlottdatacrawl.asynctask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toast;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ParseUrl extends AsyncTask<String, Void, String> {
    private final Context context;
    ProgressDialog progressDialog;
    private TextView textView;
    private final String TAG = "DATA_CRAWLER";

    public ParseUrl(Context context, TextView textView) {
        this.context = context;
        this.textView = textView;
    }

    public static class Session {
        Date date;
        String id;

        public Session(Date date, String id) {
            this.date = date;
            this.id = id;
        }
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
        URL url = null;
        try {
            url = new URL("https://vietlott.vn/vi/trung-thuong/ket-qua-trung-thuong/max-3d#00504");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return resultBuffer.toString();
    }

    private Session getSessionInfo(String url) {
        try {
            Connection connection = Jsoup.connect(url);
            connection.userAgent("connection.userAgent(Mozilla/5.0 (Linux; U; Android 4.0.3; ko-kr; LG-L160L Build/IML74K) AppleWebkit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30)\n");
            connection.timeout(50*1000);
            Document doc = connection.get();

            Element info = doc.getElementsByTag("h5").first();
            return stringToSessionInfo(info.text());


        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Session stringToSessionInfo(String s) {
        int firstIndexOfId = s.indexOf("#");
        int lastIndexOfId = s.indexOf(" ", firstIndexOfId);
        String id = s.substring(firstIndexOfId, lastIndexOfId);

        int dateFirstIndex = s.indexOf("ngày") + 5;
        String dateStr = s.substring(dateFirstIndex);
        Date date = new Date();
        try {
            date = new SimpleDateFormat("dd/MM/yyyy").parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Session(date,id);
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

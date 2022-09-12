package com.example.vietlottdatacrawl.asynctask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.util.Date;

public class ParseUrl extends AsyncTask<String, Void, String> {
    private final Context context;
    ProgressDialog progressDialog;
    private TextView textView;

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
        String url = strings[0];
        StringBuffer stringBuffer = new StringBuffer();
        String infoStr = getSessionInfo(url);

        int firstIndexOfId = infoStr.indexOf("#");
        int lastIndexOfId = infoStr.indexOf(" ", firstIndexOfId);
        String id = infoStr.substring(firstIndexOfId, lastIndexOfId);

        int dateFirstIndex = infoStr.indexOf("ngày") + 5;
        String date = infoStr.substring(dateFirstIndex);

        Date currentDate = new Date();
        try {
            currentDate = new SimpleDateFormat("dd/MM/yyyy").parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        int currentMonth = currentDate.getMonth();
        int month = currentMonth;
        int currentID = Integer.valueOf(id.substring(1));
        while (month == currentMonth) {
            StringBuffer urlBuilder = new StringBuffer();
            urlBuilder.append("https://vietlott.vn/vi/trung-thuong/ket-qua-trung-thuong/max-3d");
            String idStr = Integer.toString(currentID--);
            urlBuilder.append("#");
            urlBuilder.append("  ");
            urlBuilder.append(currentID);

            try {
                getSessionInfo(urlBuilder.toString());
            }
        }

        return stringBuffer.toString();
    }

    private String getSessionInfo(String url) {
        try {
            Connection connection = Jsoup.connect(url);
            connection.userAgent("            connection.userAgent(Mozilla/5.0 (Linux; U; Android 4.0.3; ko-kr; LG-L160L Build/IML74K) AppleWebkit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30)\n");
            connection.timeout(50*1000);
            Document doc = connection.get();

            Element info = doc.getElementsByTag("h5").first();
            return info.text();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
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

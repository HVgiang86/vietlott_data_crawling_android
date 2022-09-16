package com.example.vietlottdatacrawl.utilities;

import android.util.Log;

import com.example.vietlottdatacrawl.model.PrizeDrawSession;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class VietlottDataCrawler {
    private final String TAG = "DATA_CRAWLER";
    private final String INITIAL_URL = "https://vietlott.vn/vi/trung-thuong/ket-qua-trung-thuong/max-3d";
    private final String URL_PREFIX = "https://vietlott.vn/vi/trung-thuong/ket-qua-trung-thuong/max-3D?id=";
    private final String URL_SUFFIX = "&nocatche=1";
    private final String USER_AGENT_STRING = "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:99.0) Gecko/20100101 Firefox/99.0";

    private List<PrizeDrawSession> sessionList;

    private final static VietlottDataCrawler INSTANCE = new VietlottDataCrawler();

    private VietlottDataCrawler() {
        sessionList = new ArrayList<>();
    }

    public static VietlottDataCrawler getInstance() {
        return INSTANCE;
    }

    public List<PrizeDrawSession> getSessionList() {
        sessionList.clear();
        PrizeDrawSession recentSession = getSessionInfo(INITIAL_URL);

        if (recentSession == null) {
            return null;
        }
        int recentId = Integer.parseInt(recentSession.getId());
        int currentMonth = recentSession.getDate().getMonth();

        int id = recentId;
        int month = currentMonth;
        while (id >= 1) {
            String url = URL_PREFIX
                        + intIdToStringId(id--)
                        + URL_SUFFIX;

            PrizeDrawSession session = getSessionInfo(url);
            int temp;
            try {
                temp = session.getDate().getMonth();
            } catch (NullPointerException e) {
                e.printStackTrace();
                return null;
            }
            month = temp;

            Log.d(TAG,session.toString());
            sessionList.add(session);

        }

        return sessionList;
    }

    private PrizeDrawSession getSessionInfo(String url) {
        try {
            Log.d(TAG, "URL: " + url);
            Connection connection = Jsoup.connect(url);
            connection.userAgent(USER_AGENT_STRING);
            connection.timeout(30*1000);
            Document doc = connection.get();

            Element info = doc.getElementsByTag("h5").first();

            StringBuilder prizeString = new StringBuilder();
            Element prize = doc.getElementsByTag("tbody").first();
            Elements elements = prize.getElementsByTag("tr");

            for (int i = 0; i<4; ++i) {
                Elements collection = elements.get(i).getElementsByTag("td");
                prizeString.append(collection.get(1).text());
            }

            return stringToSessionInfo(info.text(),prizeString.toString());


        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private PrizeDrawSession stringToSessionInfo(String info, String prize) {
        int firstIndexOfId = info.indexOf("#");
        int lastIndexOfId = info.indexOf(" ", firstIndexOfId);
        String id = info.substring(firstIndexOfId+1, lastIndexOfId);

        int dateFirstIndex = info.indexOf("ngày") + 5;
        String dateStr = info.substring(dateFirstIndex);
        Date date = new Date();
        try {
            date = new SimpleDateFormat("dd/MM/yyyy", Locale.US).parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        PrizeDrawSession session = new PrizeDrawSession(date,id,prize);
        return session;
    }

    private String intIdToStringId(int id) {
        StringBuilder builder = new StringBuilder();
        int numberOfDigits = (int) Math.log10((double) id) + 1;
        for (int i = 0; i < 5-numberOfDigits; ++i)
            builder.append("0");
        builder.append(id);
        return builder.toString();
    }
}

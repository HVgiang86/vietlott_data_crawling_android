package com.example.vietlottdatacrawl.utilities;

import android.content.Context;
import android.util.Log;

import com.example.vietlottdatacrawl.model.PrizeDrawSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class VietlottDataCrawlerWithAPI {
    private final String TAG = "DATA_CRAWLER";
    private final String INITIAL_API_URL = "https://api.vietlott.vn/services/?securitycode=vietlotcmc&jsondata={%22Command%22:%22GetMax3DResult%22,%22JsonData%22:%22{\\%22PageSize\\%22:1,\\%22Segment\\%22:0,\\%22TopN\\%22:0}%22}";
    private final String API_URL_PREFIX = "https://api.vietlott.vn/services/?securitycode=vietlotcmc&jsondata={%22Command%22:%22GetMax3DResult%22,%22JsonData%22:%22{\\%22PageSize\\%22:";
    private final String API_URL_SUFFIX = ",\\\"Segment\\\":0,\\\"TopN\\\":0}\"}";
    private final String USER_AGENT_STRING = "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:99.0) Gecko/20100101 Firefox/99.0";

    private List<PrizeDrawSession> sessionList;

    private final static VietlottDataCrawlerWithAPI INSTANCE = new VietlottDataCrawlerWithAPI();

    private VietlottDataCrawlerWithAPI() {
        sessionList = new ArrayList<>();
    }

    public static VietlottDataCrawlerWithAPI getInstance() {
        return INSTANCE;
    }

    public List<PrizeDrawSession> getSessionList(Context context) {
        Log.d(TAG, "BEGIN CRAWL");
        sessionList.clear();

        //get recent session on web
        PrizeDrawSession recentSession = getRecentSession();

        //if cant get recent session information from internet, finish data crawling and return null
        if (recentSession == null) {
            return null;
        }

        Log.d(TAG, "RECENT SESSION INFO: " + recentSession);
        int recentId = Integer.parseInt(recentSession.getId());

        JsonDataHelper jsonHelper = new JsonDataHelper(context);
        String recentIdSavedStr = jsonHelper.getRecentIdFromFile();
        int recentIdSaved;
        if (recentIdSavedStr == null) {
            recentIdSaved = 0;
        } else
            recentIdSaved = Integer.parseInt(recentIdSavedStr);

        Log.d(TAG, "RECENT SESSION ID SAVED: " + recentIdSaved);
        Log.d(TAG, "RECENT SESSION ID ON WEB: " + recentId);

        sessionList = jsonHelper.getSessionListFromFile();

        int pageSizeToGet = recentId - recentIdSaved;

        if (pageSizeToGet != 0) {
            String url = apiUrlBuilderByPageSize(pageSizeToGet);
            List<PrizeDrawSession> newSessionList = getSessionListByAPI(url);
            if (newSessionList == null)
                return null;

            newSessionList.addAll(sessionList);

            jsonHelper.updateJsonData(newSessionList);
            return newSessionList;
        } else {
            Log.d(TAG, "DO NOT NEED TO UPDATE!");
            return sessionList;
        }


    }

    private PrizeDrawSession getRecentSession() {
        List<PrizeDrawSession> sessionListTemp = getSessionListByAPI(apiUrlBuilderByPageSize(1));
        if (sessionListTemp == null || sessionListTemp.size() == 0)
            return null;

        return sessionListTemp.get(0);
    }

    private List<PrizeDrawSession> getSessionListByAPI(String url) {
        Log.d(TAG, "URL: " + url);
        List<PrizeDrawSession> sessionList = new ArrayList<>();

        //Use JSOUP to parse html document got from internet
        try {
            Log.d(TAG, "URL: " + url);
            Connection connection = Jsoup.connect(url);
            connection.userAgent(USER_AGENT_STRING);
            connection.timeout(20 * 1000);
            Document doc = connection.get();

            Element info = doc.getElementsByTag("body").first();
            JSONArray jsonArray = new JSONArray(info.text());
            for (int i = 0; i < jsonArray.length(); ++i) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                PrizeDrawSession session = getSessionInfo(jsonObject);
                if (session != null)
                    sessionList.add(session);
            }

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return sessionList;
    }


    private PrizeDrawSession getSessionInfo(JSONObject jsonObject) {

        try {
            String idStr;
            String dateStr;
            StringBuilder builder = new StringBuilder();
            idStr = jsonObject.getString("DrawId");
            int id = Integer.parseInt(idStr);

            //get date
            dateStr = jsonObject.getString("DrawDate");
            Date date = new Date();
            try {
                date = new SimpleDateFormat("dd/MM/yyyy", Locale.US).parse(dateStr);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            //get prize number
            builder.append(jsonObject.getString("Number11"));
            builder.append(jsonObject.getString("Number12"));
            builder.append(jsonObject.getString("Number21"));
            builder.append(jsonObject.getString("Number22"));
            builder.append(jsonObject.getString("Number23"));
            builder.append(jsonObject.getString("Number24"));
            builder.append(jsonObject.getString("Number31"));
            builder.append(jsonObject.getString("Number32"));
            builder.append(jsonObject.getString("Number33"));
            builder.append(jsonObject.getString("Number34"));
            builder.append(jsonObject.getString("Number35"));
            builder.append(jsonObject.getString("Number36"));
            builder.append(jsonObject.getString("ConsolationPrize1"));
            builder.append(jsonObject.getString("ConsolationPrize2"));
            builder.append(jsonObject.getString("ConsolationPrize3"));
            builder.append(jsonObject.getString("ConsolationPrize4"));
            builder.append(jsonObject.getString("ConsolationPrize5"));
            builder.append(jsonObject.getString("ConsolationPrize6"));
            builder.append(jsonObject.getString("ConsolationPrize7"));
            builder.append(jsonObject.getString("ConsolationPrize8"));
            String prizeNumber = builder.toString();

            PrizeDrawSession session = new PrizeDrawSession(date, intIdToStringId(id), prizeNumber);
            Log.d(TAG,"Crawled session: " + session.toString());
            return session;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }


    }

    private String intIdToStringId(int id) {
        StringBuilder builder = new StringBuilder();
        int numberOfDigits = (int) Math.log10(id) + 1;
        for (int i = 0; i < 5 - numberOfDigits; ++i)
            builder.append("0");
        builder.append(id);
        return builder.toString();
    }

    private String apiUrlBuilderByPageSize(int pageSize) {
        return API_URL_PREFIX + pageSize + API_URL_SUFFIX;
    }


}

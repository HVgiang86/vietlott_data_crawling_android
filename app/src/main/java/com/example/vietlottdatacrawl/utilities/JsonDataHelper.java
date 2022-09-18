package com.example.vietlottdatacrawl.utilities;

import android.content.Context;

import com.example.vietlottdatacrawl.model.PrizeDrawSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class JsonDataHelper {
    public static final String RECENT_ID_TAG = "recentId";
    public static final String SESSION_TAG = "session";
    public static final String ID_TAG = "id";
    public static final String DATE_TAG = "date";
    public static final String PRIZE_NUMBER_TAG = "number";

    private final Context context;
    private String jsonString;

    public JsonDataHelper(Context context) {
        this.context = context;
    }

    public void updateJsonData(List<PrizeDrawSession> sessionList) {
        if (!(sessionList == null ||sessionList.size() == 0)) {
            String recentId = sessionList.get(0).getId();
            String recentIdFromFile = getRecentIdFromFile();
            if (recentIdFromFile == null || recentIdFromFile.length() == 0) {
                writeSessionListToJsonFile(sessionList);
            }
            if (!recentId.equals(getRecentIdFromFile())) {
                writeSessionListToJsonFile(sessionList);
            }
        }
    }

    public String convertSessionListToJsonString(List<PrizeDrawSession> sessionList) {
        JSONObject jsonRoot = new JSONObject();
        try {
            String recentId = sessionList.get(0).getId();
            jsonRoot.put(RECENT_ID_TAG,recentId);
            JSONArray jsonArray = new JSONArray();

            for (PrizeDrawSession session : sessionList) {
                String id = session.getId();
                String dateStr = DateFormat.getDateInstance(DateFormat.DATE_FIELD, Locale.US)
                                            .format(session.getDate());
                String prizeNumberStr = session.getPrizeNumberString();
                JSONObject jsonObject = new JSONObject();
                jsonObject.put(ID_TAG,id);
                jsonObject.put(DATE_TAG,dateStr);
                jsonObject.put(PRIZE_NUMBER_TAG,prizeNumberStr);

                jsonArray.put(jsonObject);
            }

            jsonRoot.put(SESSION_TAG,jsonArray.toString());

        }catch (JSONException e) {
            e.printStackTrace();
        }
        String jsonString = "";
        try {
            jsonString = jsonRoot.toString(4);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonString;
    }

    public void readData() {
        if (jsonString == null || jsonString.length() == 0) {
            jsonString = DataFileManager.readJsonStringFromFile(context);
        }
    }

    public void writeSessionListToJsonFile(List<PrizeDrawSession> sessionList) {
        try {
            String data = convertSessionListToJsonString(sessionList);
            DataFileManager.writeJsonStringToFile(data, context);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getRecentIdFromFile() {
        String id = null;
        try {
            readData();
            JSONObject jsonRoot = new JSONObject(jsonString);
            id = jsonRoot.getString(RECENT_ID_TAG);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return id;
    }

    public List<PrizeDrawSession> getSessionListFromFile() {
        List<PrizeDrawSession> sessionList = new ArrayList<>();
        try {
            readData();
            JSONObject jsonRoot = new JSONObject(jsonString);
            String jsonArrayString = jsonRoot.getString(SESSION_TAG);
            JSONArray jsonArray = new JSONArray(jsonArrayString);
            for (int i = 0; i < jsonArray.length(); ++i) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String id = jsonObject.getString(ID_TAG);
                String dateStr = jsonObject.getString(DATE_TAG);
                String prizeNumberStr = jsonObject.getString(PRIZE_NUMBER_TAG);

                Date date = null;
                try {
                    date = DateFormat.getDateInstance(DateFormat.DATE_FIELD, Locale.US).parse(dateStr);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                if (date != null) {
                    PrizeDrawSession session = new PrizeDrawSession(date,id,prizeNumberStr);
                    sessionList.add(session);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return sessionList;
    }
}

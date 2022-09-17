package com.example.vietlottdatacrawl.utilities;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import com.example.vietlottdatacrawl.model.PrizeDrawSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class JsonHelper {
    private final Context context;
    private String json = null;
    private final String filename = "result.json";

    public JsonHelper(Context context) {
        this.context = context;
    }

    public void loadJSONFromAsset() {
        File jsonFile = new File(context.getFilesDir() + "/result.json");
        if (!jsonFile.exists()) {
            try {
                jsonFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {

            FileInputStream fis = context.openFileInput(filename);
            InputStreamReader inputStreamReader =
                    new InputStreamReader(fis, "UTF-8");
            StringBuilder stringBuilder = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(inputStreamReader)) {
                String line = reader.readLine();
                while (line != null) {
                    stringBuilder.append(line).append('\n');
                    line = reader.readLine();
                }
            } catch (IOException e) {
                // Error occurred when opening raw file for reading.
                e.printStackTrace();
            } finally {
                json = stringBuilder.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void writeJsonToFile(JSONObject jsonRoot) {
        String fileName = filename;
        String fileContent = jsonRoot.toString();
        FileOutputStream outputStream;
        try {
            outputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            outputStream.write(fileContent.getBytes("UTF-8"));
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public JSONObject convertSessionListToJson(List<PrizeDrawSession> sessionList) {
        JSONObject jsonRoot = new JSONObject();
        try {
            jsonRoot.put("recentId",sessionList.get(0).getId());

            JSONArray jsonArray = new JSONArray();

            for (PrizeDrawSession session : sessionList) {
                String dateStr;
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id",session.getId());
                dateStr = DateFormat.getDateInstance(DateFormat.DATE_FIELD,Locale.US).format(session.getDate());
                jsonObject.put("date",dateStr);
                jsonObject.put("number",session.getPrizeNumberString());

                jsonArray.put(jsonObject);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonRoot;
    }



    public void updateJsonFile(List<PrizeDrawSession> sessionList) {
        if (!sessionList.get(0).getId().equals(readRecentIdFromJsonFile())) {
            writeJsonToFile(convertSessionListToJson(sessionList));
        }
    }

    public String readRecentIdFromJsonFile() {
        String recentId = null;
        if (json == null) {
            loadJSONFromAsset();
        }


        try {
            JSONObject jsonRoot = new JSONObject(json);
            recentId = jsonRoot.getString("recentId");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return recentId;
    }

    public List<PrizeDrawSession> readSessionListFromJsonFile() {
        List<PrizeDrawSession> sessionList = new ArrayList<>();
        if (json == null)
            loadJSONFromAsset();

        try {
            JSONObject jsonRoot = new JSONObject(json);

            JSONArray jsonArray = jsonRoot.getJSONArray("session");
            for (int i = 0; i < jsonArray.length(); ++i) {
                JSONObject object = jsonArray.getJSONObject(i);
                String id = object.getString("id");
                String dateStr = object.getString("date");
                String number = object.getString("number");

                Date date = null;
                try {
                    date = DateFormat.getDateInstance(DateFormat.DATE_FIELD, Locale.US).parse(dateStr);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (date != null) {
                    PrizeDrawSession session = new PrizeDrawSession(date,id,number);
                    sessionList.add(session);
                }
            }
        }catch (JSONException e) {
            e.printStackTrace();
        }

        return sessionList;
    }

    private void copyAssets() {
        AssetManager assetManager = context.getAssets();
        String[] files = null;
        try {
            files = assetManager.list("");
        } catch (IOException e) {
            Log.e("tag", "Failed to get asset file list.", e);
        }
        if (files != null) for (String filename : files) {
            InputStream in = null;
            OutputStream out = null;
            try {
                in = assetManager.open(filename);
                File outFile = new File(context.getFilesDir() + "/result.json");
                out = new FileOutputStream(outFile);
                copyFile(in, out);
            } catch(IOException e) {
                Log.e("tag", "Failed to copy asset file: " + filename, e);
            }
            finally {
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        // NOOP
                    }
                }
                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException e) {
                        // NOOP
                    }
                }
            }
        }
    }
    private void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while((read = in.read(buffer)) != -1){
            out.write(buffer, 0, read);
        }
    }
}

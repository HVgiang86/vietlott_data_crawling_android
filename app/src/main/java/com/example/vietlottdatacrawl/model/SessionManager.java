package com.example.vietlottdatacrawl.model;

import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class SessionManager {
    private final static SessionManager INSTANCE = new SessionManager();
    private List<PrizeDrawSession> sessionList;

    private SessionManager() {
        sessionList = new ArrayList<>();
    }
    public static SessionManager getInstance() {
        return INSTANCE;
    }

    public List<PrizeDrawSession> getSessionList() {
        return sessionList;
    }

    public void addSession(PrizeDrawSession session) {
        if (session != null)
            sessionList.add(session);
    }

    public void setSessionList(List<PrizeDrawSession> sessionList) {
        this.sessionList = sessionList;
    }

    public int getSessionIndex(PrizeDrawSession session) {
        return sessionList.indexOf(session);
    }

    public String[] getMonthArray() {
        List<String> monthList = new ArrayList<>();

        int previousMonth = 0, previousYear = 0;
        for (PrizeDrawSession session : sessionList) {
            Date date = session.getDate();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);

            int month = calendar.get(Calendar.MONTH);
            month++;
            int year = calendar.get(Calendar.YEAR);
            if (!(month == previousMonth && year == previousYear)) {
                String thisMonth = month + "/" + year;
                monthList.add(thisMonth);
                Log.d("Month List", "Month: " + thisMonth);
                previousMonth = month;
                previousYear = year;
            }
        }

        String[] strings = monthList.toArray(new String[0]);
        return strings;
    }

    public List<PrizeDrawSession> getSessionListByMonth(String selectedMonthYear) {
        List<PrizeDrawSession> list = new ArrayList<>();
        for (PrizeDrawSession session : sessionList) {
            Date date = session.getDate();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            int month = calendar.get(Calendar.MONTH);
            month++;
            int year = calendar.get(Calendar.YEAR);
            String monthYear = month + "/" + year;

            if (monthYear.equals(selectedMonthYear)) {
                list.add(session);
            }
        }
        return list;
    }
}

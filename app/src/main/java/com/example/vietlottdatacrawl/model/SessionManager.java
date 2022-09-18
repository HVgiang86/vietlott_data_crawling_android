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

    public void setSessionList(List<PrizeDrawSession> sessionList) {
        this.sessionList = sessionList;
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

        String[] monthArray = new String[monthList.size() + 1];
        for (int i = 0; i < monthList.size(); ++i) {
            monthArray[i + 1] = monthList.get(i);
        }
        monthArray[0] = "Tất Cả";
        return monthArray;
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

    public String getMonthYearStringBySession(PrizeDrawSession session) {
        Date date = session.getDate();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int month = calendar.get(Calendar.MONTH);
        month++;
        int year = calendar.get(Calendar.YEAR);
        return month + "/" + year;
    }

    public PrizeDrawSession getSessionByIndex(int index) {
        return sessionList.get(index);
    }
}

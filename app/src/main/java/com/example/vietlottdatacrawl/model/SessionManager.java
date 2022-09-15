package com.example.vietlottdatacrawl.model;

import java.util.ArrayList;
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
}

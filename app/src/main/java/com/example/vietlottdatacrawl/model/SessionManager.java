package com.example.vietlottdatacrawl.model;

public class SessionManager {
    private final static SessionManager INSTANCE = new SessionManager();

    private SessionManager() {

    }
    public static SessionManager getInstance() {
        return INSTANCE;
    }


}

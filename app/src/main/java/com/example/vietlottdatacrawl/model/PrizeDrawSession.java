package com.example.vietlottdatacrawl.model;

import java.text.DateFormat;
import java.util.Date;

public class PrizeDrawSession {
    private Date date;
    private String id;
    private String giaiDacBiet;
    private String giaiNhat;
    private String giaiNhi;
    private String giaiBa;

    public PrizeDrawSession(Date date, String id) {
        this.date = date;
        this.id = id;
    }

    public PrizeDrawSession(Date date, String id, String giaiDacBiet, String giaiNhat, String giaiNhi, String giaiBa) {
        this.date = date;
        this.id = id;
        this.giaiDacBiet = giaiDacBiet;
        this.giaiNhat = giaiNhat;
        this.giaiNhi = giaiNhi;
        this.giaiBa = giaiBa;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGiaiDacBiet() {
        return giaiDacBiet;
    }

    public void setGiaiDacBiet(String giaiDacBiet) {
        this.giaiDacBiet = giaiDacBiet;
    }

    public String getGiaiNhat() {
        return giaiNhat;
    }

    public void setGiaiNhat(String giaiNhat) {
        this.giaiNhat = giaiNhat;
    }

    public String getGiaiNhi() {
        return giaiNhi;
    }

    public void setGiaiNhi(String giaiNhi) {
        this.giaiNhi = giaiNhi;
    }

    public String getGiaiBa() {
        return giaiBa;
    }

    public void setGiaiBa(String giaiBa) {
        this.giaiBa = giaiBa;
    }

    @Override
    public String toString() {
        return "ID: " + id +
                " Date: " + DateFormat.getDateInstance(DateFormat.DATE_FIELD).format(date)
                + "\n" + giaiDacBiet
                + "\n" + giaiNhat
                + "\n" + giaiNhi
                +"\n" + giaiBa;


    }
}

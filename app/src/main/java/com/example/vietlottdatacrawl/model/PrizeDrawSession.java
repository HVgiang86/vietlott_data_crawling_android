package com.example.vietlottdatacrawl.model;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

public class PrizeDrawSession {
    private final Date date;
    private final String id;
    private final Byte[] prizeNumber = new Byte[60];

    public PrizeDrawSession(Date date, String id, String prizeString) {
        this.date = date;
        this.id = id;
        stringToNumberSet(prizeString);
    }

    private void stringToNumberSet(String s) {
        int j = 0;
        for (int i = 0; i < s.length(); ++i) {
            if (s.charAt(i) <= '9' && s.charAt(i) >= '0') {
                prizeNumber[j++] = (byte) (s.charAt(i) - '0');
            }
        }
    }

    public Date getDate() {
        return date;
    }

    public String getId() {
        return id;
    }

    public Byte[] getPrizeNumber() {
        return prizeNumber;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Session ");
        builder.append("Date: ");
        builder.append(DateFormat.getDateInstance(DateFormat.DATE_FIELD, Locale.US).format(date));
        builder.append(", Id: ");
        builder.append(id);
        builder.append(", Prize number: ");
        for (int i = 1; i <= 60; ++i) {
            builder.append(prizeNumber[i-1]);
            if (i%3 == 0) {
                builder.append(" ");
            }
        }
        return builder.toString();
    }
}

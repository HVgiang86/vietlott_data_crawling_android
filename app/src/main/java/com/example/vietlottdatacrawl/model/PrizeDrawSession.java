package com.example.vietlottdatacrawl.model;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

public class PrizeDrawSession {
    private final Date date;
    private final String id;
    private final TripleNumberSet[] prizeList = new TripleNumberSet[20];

    public PrizeDrawSession(Date date, String id, String prizeString) {
        this.date = date;
        this.id = id;
        stringToNumberSet(prizeString);
    }

    private void stringToNumberSet(String s) {
        int j = 0; int count  = 0;
        byte number1 = 0, number2 = 0, number3;
        for (int i = 0; i < s.length(); ++i) {
            if (s.charAt(i) <= '9' && s.charAt(i) >= '0') {
                byte number = (byte) (s.charAt(i) - '0');
                count++;
                if (count == 1)
                    number1 = number;
                if (count == 2)
                    number2 = number;
                if (count == 3) {
                    number3 = number;
                    TripleNumberSet set = new TripleNumberSet(number1, number2, number3);
                    prizeList[j++] = set;
                    count = 0;
                }
            }
        }
    }

    public Date getDate() {
        return date;
    }

    public String getId() {
        return id;
    }

    public TripleNumberSet[] getPrizeList() {
        return prizeList;
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
        for (TripleNumberSet set : prizeList) {
            builder.append(set.number1);
            builder.append(set.number2);
            builder.append(set.number3);
            builder.append(" ");
        }
        return builder.toString();
    }
}

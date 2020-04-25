package com.sl.zklock;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class OrderNumGenerator {

    private static Integer currentID = 0;

    public String getNumber() {
        currentID++;
        return formatDate(new Date()) + "-" + currentID;
    }

    private String formatDate(Date date) {
        Calendar cal = Calendar.getInstance();
        Date now = cal.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:MM:SS");
        String strDate = format.format(now);
        return strDate;
    }

}

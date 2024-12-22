package org.example;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class SportObject {
    private int id;
    private String name;
    private String subjectRF;
    private String fullAddress;
    private Date date;

    private SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH);

    public SportObject(int id, String name, String subjectRF, String fullAddress, String strDate) {
        this.id = id;
        this.name = name;
        this.subjectRF = subjectRF;
        this.fullAddress = fullAddress;
        if (strDate != null && !strDate.isEmpty()) {
            try {
                this.date = df.parse(strDate);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }
        else this.date = null;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSubjectRF() {
        return subjectRF;
    }

    public String getFullAddress() {
        return fullAddress;
    }

    public Date getDate() {
        return date;
    }

    public String getDateStr() {
        return date == null ? null : df.format(date);
    }

    @Override
    public String toString() {
        return String.format("%d) %s; %s; %s; %s", getId(), getName(), getSubjectRF(), getFullAddress(), getDate() == null ? "null" : df.format(getDate()));
    }
}

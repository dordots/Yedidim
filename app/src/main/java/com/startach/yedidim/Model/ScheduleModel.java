package com.startach.yedidim.Model;

import java.util.Date;

/**
 * Created by user on 13-Sep-17.
 */

public class ScheduleModel {
    private String dayName;
    private boolean active;
    private int dayNumber;
    private Date fromTime;
    private Date toTime;

    public ScheduleModel(String dayName, boolean active, int dayNumber, Date fromTime, Date toTime) {
        this.dayName = dayName;
        this.active = active;
        this.dayNumber = dayNumber;
        this.fromTime = fromTime;
        this.toTime = toTime;
    }

    public String getDayName() {
        return dayName;
    }

    public void setDayName(String dayName) {
        this.dayName = dayName;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getDayNumber() {
        return dayNumber;
    }

    public void setDayNumber(int dayNumber) {
        this.dayNumber = dayNumber;
    }

    public Date getFromTime() {
        return fromTime;
    }

    public void setFromTime(Date fromTime) {
        this.fromTime = fromTime;
    }

    public Date getToTime() {
        return toTime;
    }

    public void setToTime(Date toTime) {
        this.toTime = toTime;
    }
}

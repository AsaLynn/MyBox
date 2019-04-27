package com.zxning.library.entity;

/**
 *
 */
public class AttendanceItemInfo {

    private String holiday;
    private String statusSignin;
    private String statusSignout;
    private String id;

    public void setHoliday(String holiday) {
        this.holiday = holiday;
    }

    public void setStatusSignin(String statusSignin) {
        this.statusSignin = statusSignin;
    }

    public void setStatusSignout(String statusSignout) {
        this.statusSignout = statusSignout;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHoliday() {
        return holiday;
    }

    public String getStatusSignin() {
        return statusSignin;
    }

    public String getStatusSignout() {
        return statusSignout;
    }

    public String getId() {
        return id;
    }
}

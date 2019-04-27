package com.example.mybox.bean;

/**
 *
 */

public class NewsInfo {
    public NewsInfo() {
    }

    public NewsInfo(String cover, String subject, String summary) {
        this.cover = cover;
        this.subject = subject;
        this.summary = summary;
    }

    private String cover;
    private String subject;
    private String summary;

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
}

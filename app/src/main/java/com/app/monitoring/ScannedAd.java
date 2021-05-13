package com.app.monitoring;

import java.util.Date;

public class ScannedAd {
    private final String url;
    private final String name;
    private final String avito_id;
    private final Date addedAt;
    private String status = "NEW";

    public ScannedAd(String url, String name, String avito_id, Date addedAt) {
        this.url = url;
        this.name = name;
        this.avito_id = avito_id;
        this.addedAt = addedAt;
    }

    public String getName() {
        return name;
    }

    public String getAvito_id() {
        return avito_id;
    }

    public Date getAddedAt() {
        return addedAt;
    }

    public String getStatus() {
        return status;
    }

    public String getUrl() {
        return url;
    }
}

package com.example.sunkee.vehicletracker.model;

import java.io.Serializable;

/**
 * @author toluadetuyi
 * <p>
 * Model class for Sms
 */
public class Location implements Serializable {

    private int id;
    private String latitude;
    private String longitude;
    private String timestamp;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getTimestamp() {
        return timestamp;
    }
}

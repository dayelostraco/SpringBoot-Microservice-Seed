package com.dayelostraco.microservice.model.dto;

import java.util.Calendar;

/**
 * Created by Dayel Ostraco
 * 11/20/15.
 */
public class HeartBeat {

    private Calendar systemTime;
    private String ipAddress;
    private String greeting;

    public HeartBeat(Calendar systemTime, String ipAddress) {
        this.systemTime = systemTime;
        this.ipAddress = ipAddress;
    }

    public Calendar getSystemTime() {
        return systemTime;
    }

    public void setSystemTime(Calendar systemTime) {
        this.systemTime = systemTime;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getGreeting() {
        return "Lub Dub - " + systemTime.getTime() + " from IP Address: " + this.ipAddress;
    }
}
